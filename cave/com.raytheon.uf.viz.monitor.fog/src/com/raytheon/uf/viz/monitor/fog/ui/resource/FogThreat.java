/**
 * This software was developed and / or modified by Raytheon Company,
 * pursuant to Contract DG133W-05-CQ-1067 with the US Government.
 * 
 * U.S. EXPORT CONTROLLED TECHNICAL DATA
 * This software product contains export-restricted data whose
 * export/transfer/disclosure is restricted by U.S. law. Dissemination
 * to non-U.S. persons whether in the United States or abroad requires
 * an export license or other authorization.
 * 
 * Contractor Name:        Raytheon Company
 * Contractor Address:     6825 Pine Street, Suite 340
 *                         Mail Stop B8
 *                         Omaha, NE 68106
 *                         402.291.0100
 * 
 * See the AWIPS II Master Rights File ("Master Rights File.pdf") for
 * further licensing information.
 **/
package com.raytheon.uf.viz.monitor.fog.ui.resource;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Display;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import com.raytheon.uf.common.dataplugin.fog.FogRecord;
import com.raytheon.uf.common.dataplugin.fog.FogRecord.FOG_THREAT;
import com.raytheon.uf.common.dataplugin.fog.analysis.FogCell;
import com.raytheon.uf.common.dataplugin.fog.analysis.FogDataCorrector;
import com.raytheon.uf.common.dataplugin.fog.analysis.FogMonitorUtils;
import com.raytheon.uf.common.geospatial.ReferencedCoordinate;
import com.raytheon.uf.common.geospatial.ReferencedObject.Type;
import com.raytheon.uf.viz.monitor.fog.FogMonitor;
import com.raytheon.uf.viz.monitor.fog.xml.FogMonitorAlgorithmXML;
import com.vividsolutions.jts.algorithm.locate.SimplePointInAreaLocator;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class FogThreat {

    /** list of coordinates for each zone **/
    public HashMap<String, ArrayList<Coordinate>> zoneCoordinates = null;

    private final GeometryFactory geoFactory = new GeometryFactory();

    private FogRecord fog = null;

    private FogMonitorAlgorithmXML fogAlgXML = null;

    private Geometry geoAdjAreas = null;

    public FOG_THREAT[] threats = null;

    public ArrayList<FogCell> cells = null;

    public float[] ir_diff_thresholds = null;

    public float[] vis_thresholds = null;

    public int twilight_angle_offset = 0;

    // boolean filter switches
    public boolean isSnowIce = true;

    public boolean isFractalDimension = true;

    public boolean isSmoothness = true;

    public boolean isAdjacency = true;

    private FogMonitor monitor;

    /**
     * serialization constructor
     */
    public FogThreat() {

    }

    /**
     * used constructor
     */
    public FogThreat(FogMonitorAlgorithmXML fogAlgXML) {
        this.fogAlgXML = fogAlgXML;
        setThresholdArrays();
    }

    /**
     * This method will be called with the switches from the GUI front end
     * 
     * Fog Product [T(10.7) - (T(3.9)] or VIS (normalized count) Maximum cloud
     * temperature Daytime Ice/Snow vs Fog Threshold (c) (FogSnowIceFilter) Cool
     * Fog vs Warm Surface threshold Daytime Smoothness Algorithm
     * (FogSmoothnessFilter) Adjacency Threshold Twilight Angle Threshold
     * Fractal Dimension Threshold (FogFractalFilter)
     */
    public FogRecord getFogThreat(FogRecord fog) {
        this.fog = fog;
        System.out.println("FogThreat: Start analyzing Fog Threat...");
        long start = System.currentTimeMillis();
        this.geoAdjAreas = getFogMonitor().getGeoAdjAreas();
        // clear threats array, make gray (SK)i=>x j=>y
        for (int i = 0; i < fog.getNx(); i++) {
            for (int j = 0; j < fog.getNy(); j++) {
                // (SK) getThreats()[fog.getNy() * i + j] = FOG_THREAT.GRAY;
                getThreats()[fog.getNx() * j + i] = FOG_THREAT.GRAY;
            }
        }

        // First execute the data corrector, this divides up the FogRecord field
        // into Day, Night, and Twilight.
        FogDataCorrector fdc = new FogDataCorrector(getFog(),
                fogAlgXML.getTwilightAngle());
        fog = fdc.execute();

        // Second, core processor. Gets you the cells, sets non-filtered values.
        executeCoreProcessor();

        // Third, apply filters
        for (FogCell cell : getCells()) {
            // *********** APPLY FILTERS *******************

            // Area Size filter
            if (fogAlgXML.isAdjacencyThreshOn()
                    && cell.getCellThreat() != FOG_THREAT.GREEN
                    && cell.getCellThreat() != FOG_THREAT.GRAY) {

                if (cell.getArea() < fogAlgXML.getAdjacencyThresh()) {
                    setCellThreats(cell.getPoints(), FOG_THREAT.GREEN);
                }
            }

            // Snow & Ice filter
            if (fogAlgXML.isIceSnowVsFogOn()
                    && cell.getImageGroup() == FogRecord.IMAGE_GROUP.VIS_GROUP
                    && (cell.getCellThreat() != FOG_THREAT.GREEN || cell
                            .getCellThreat() != FOG_THREAT.GRAY)) {

                float tempMean = calAverageTemp(cell);

                if (tempMean < fogAlgXML.getIceSnowVsFog()) {
                    downgradeThreat(cell);
                }
            }

            // Smoothness filter
            if (fogAlgXML.isDaytimeSmoothThreshOn()
                    && cell.getCellThreat() != FOG_THREAT.GREEN
                    || cell.getCellThreat() != FOG_THREAT.GRAY) {

                float tem_smoothness = calSmoothness(cell);

                if (tem_smoothness < fogAlgXML.getDaytimeSmoothThresh()) {
                    downgradeThreat(cell);
                }
            }

            // Fractal filter
            if (fogAlgXML.isFractalDimensionOn()
                    && cell.getCellThreat() != FOG_THREAT.GREEN
                    && cell.getCellThreat() != FOG_THREAT.GRAY) {

                float temFD = calRegularity(cell);

                if (temFD > fogAlgXML.getFractalDimension() && temFD > 0.0f) {
                    setCellThreats(cell.getPoints(), FOG_THREAT.GREEN);
                }
            }

            // We extract the zone threat differently than the GI filter
            // Hence it is not needed.
        }

        setThreats();
        setMonitorAreaThreats();
        System.out.println("FogThreat: Analyzed Fog Threat...Duration:   "
                + (System.currentTimeMillis() - start));
        return fog;
    }

    /**
     * Remember J is X and I is Y, totally against convention! Gets the threat
     * level
     * 
     * @param i
     *            (SK y)
     * @param j
     *            (SK x)
     * @param group
     * @return
     */
    private FOG_THREAT getThreatLevel(int i, int j, FogRecord.IMAGE_GROUP group) {
        // (SK) i=>x, j=>y
        // default return
        FOG_THREAT returnValue = FOG_THREAT.GRAY;
        // (SK) idx = fog.getNy() * i + j
        int idx = fog.getNx() * j + i;
        switch (group) {
        case VIS_GROUP:
            float ir4_temp = bTemp(i, j, FogRecord.CHANNEL.IR10_7);
            int CLOUD_FREE_BRIGHTNESS = 68; // empirical value
            // Too cold
            if (ir4_temp < fogAlgXML.getMaxCloudTemp()) {
                returnValue = FOG_THREAT.GRAY;
            }
            // overlapping high/middle cloud

            else if (fog.getVisArray()[idx] <= vis_thresholds[2]
                    && fog.getVisArray()[idx] >= vis_thresholds[1]) {

                returnValue = FOG_THREAT.RED;
            } else if (fog.getVisArray()[idx] < vis_thresholds[1]
                    && fog.getVisArray()[idx] >= vis_thresholds[0]
                    || fog.getVisArray()[idx] <= vis_thresholds[3]
                    && fog.getVisArray()[idx] > vis_thresholds[2]) {

                returnValue = FOG_THREAT.YELLOW;
            }

            // This is essentially the Cool Fog vs. Warm surface threshold check
            else if (fogAlgXML.isCoolFogVsWarmSurfaceOn()
                    && ir4_temp >= fogAlgXML.getCoolFogVsWarmSurface()) {
                returnValue = FOG_THREAT.GREEN;
            }

            else if (fog.getVisArray()[idx] < CLOUD_FREE_BRIGHTNESS) {
                returnValue = FOG_THREAT.GREEN;
            }

            return returnValue;

        case IR_GROUP:
            // for some odd reason j is x and i is y,
            // not sure why AWIPS 1 does it this way.
            // It is completely against convention.
            // Point tempoint = new Point(j, i);
            // (SK) here i=>x and j=>y
            Point tempoint = new Point(i, j);
            float tempIR10_7 = temp_at_vis_pixel(tempoint,
                    FogRecord.CHANNEL.IR10_7);
            if (tempIR10_7 < fogAlgXML.getMaxCloudTemp()) {
                // Too cold
                returnValue = FOG_THREAT.GRAY;
            }
            // Overlapping high/middle cloud
            float tempIR3_9 = temp_at_vis_pixel(tempoint,
                    FogRecord.CHANNEL.IR3_9);
            float tempDiff = tempIR10_7 - tempIR3_9;

            if (tempDiff <= ir_diff_thresholds[2]
                    && tempDiff >= ir_diff_thresholds[1]) {
                returnValue = FOG_THREAT.RED;
            } else if (tempDiff < ir_diff_thresholds[1]
                    && tempDiff >= ir_diff_thresholds[0]
                    || tempDiff <= ir_diff_thresholds[3]
                    && tempDiff > ir_diff_thresholds[2]) {
                returnValue = FOG_THREAT.YELLOW;
            } else {
                returnValue = FOG_THREAT.GREEN;
            }

        case TWILIGHT_GROUP:
            // do nothing, stays gray
        }

        return returnValue;
    }

    /**
     * Precursor call to get temp at pixel
     * 
     * @param i
     * @param j
     * @param imgtype
     * @return
     */
    private float bTemp(int i, int j, FogRecord.CHANNEL imgtype) {
        // (SK) here i=>x and j=>y
        // for some odd reason j is x and i is y,
        // not sure why AWIPS 1 does it this way.
        // It is completely against convention.

        Point tempoint = new Point(i, j);
        return temp_at_vis_pixel(tempoint, imgtype);
    }

    /**
     * Get the temp at the pixel
     * 
     * @param pointVis
     * @param channel
     * @return
     */
    private float temp_at_vis_pixel(Point pointVis, FogRecord.CHANNEL channel) {
        // The assumption is that Dimensions of IR4 and IR2 are the same.
        // IR4 data will be always needed, so attributes for IR4 shall be
        // always available.
        // **** ATTENTION ***** We have made the vis the same as well for AWIPS
        // II
        int count_value = 255;
        if (channel == FogRecord.CHANNEL.IR3_9) {
            count_value = fog.getIR_3_9Array()[fog.getNx() * pointVis.y
                    + pointVis.x];
            // (SK) count_value = fog.getIR_3_9Array()[fog.getNy() * pointVis.x
            // + pointVis.y];
        } else if (channel == FogRecord.CHANNEL.IR10_7) {
            count_value = fog.getIR_10_7Array()[fog.getNx() * pointVis.y
                    + pointVis.x];
            // (SK) count_value = fog.getIR_10_7Array()[fog.getNy() * pointVis.x
            // + pointVis.y];
        }
        return FogMonitorUtils.count2temp(count_value, channel);
    }

    /**
     * Gets the fog
     * 
     * @return
     */
    public FogRecord getFog() {
        return fog;
    }

    /**
     * Gets the existing array of threats
     * 
     * @return
     */
    public FOG_THREAT[] getThreats() {
        if (threats == null) {
            threats = new FOG_THREAT[getFog().getNx() * getFog().getNy()];
        }
        return threats;
    }

    /**
     * Gets the cells of this analysis
     * 
     * @return
     */
    public ArrayList<FogCell> getCells() {
        return cells;
    }

    /**
     * Gets the existing threat value at that point
     * 
     * @return
     */
    public FOG_THREAT getThreat(int i, int j) {
        return getThreats()[fog.getNx() * j + i];
        // (SK) return getThreats()[fog.getNy() * i + j];
    }

    /**
     * Set fog threat at threat level point
     * 
     * @param i
     * @param j
     * @param threat
     */
    private void setThreat(int i, int j, FOG_THREAT threat) {
        // (SK) getThreats()[fog.getNy() * i + j] = threat;
        getThreats()[fog.getNx() * j + i] = threat;
    }

    /**
     * Downgrade the Fog Threat
     * 
     * @param current
     * @param i
     * @param j
     */
    private void downgradeThreat(FogCell cell) {
        if (cell.getCellThreat() == FOG_THREAT.YELLOW) {
            setCellThreats(cell.getPoints(), FOG_THREAT.GREEN);
        } else if (cell.getCellThreat() == FOG_THREAT.RED) {
            setCellThreats(cell.getPoints(), FOG_THREAT.YELLOW);
        }
    }

    // // *********************** FILTERS **************************** /////

    // -Private--------------------------------------------------------------------
    // NAME: CalRegularity()
    //
    // TYPE: Private Member function
    //
    // Descript:
    // Given a cell, calculate its regularity i.e. the fractal dimension
    // Input Arguments:
    // cell: The Fog_Cell whose fractal dimension is to be measured
    // Output Arguments:
    // Return float: fractal dimension. the value is between 1 to 2
    // smaller means more regular.
    // Here use the perimeter and area relation to represent
    // fractal dimension (one of the simply ways)
    // History:
    // May 2004 Qin Zeng (GDMB/MDL) -- created
    // Dec 2009 D Hladky translated to Java
    // -----------------------------------------------------------------------------

    private float calRegularity(FogCell cell) {

        int nx = cell.right - cell.left + 1;
        int ny = cell.bottom - cell.top + 1;
        int i;
        boolean[] flag = new boolean[nx * ny];

        for (i = 0; i < nx * ny; i++) {
            flag[i] = false;
        }

        int pixels_num = (int) cell.getArea();
        int flagPos;
        for (i = 0; i < pixels_num; i++) {
            Coordinate coor = cell.getPixelCoordinate(i);
            flagPos = (int) ((coor.y - cell.top) * nx + coor.x - cell.left);
            if (flagPos >= 0 && flagPos < nx * ny) {
                flag[flagPos] = true;
            }
        }
        // From here, perimeter of the fog cell will be calculated;
        // |...| 1 |...|
        // | 4 | 0 | 2 |
        // |...| 3 |...|
        //
        // If the any of the 0's surrounding pixels(1,2,3,4) is flagged as
        // false,
        // which means that surrounding pixel(1,2,3,or 4) is not included in
        // the cell, pixel 0 then will be at the edge of the cell cluster.
        // And the edge connecting to the outside pixel will be counted into the
        // perimeter of the cell cluster.

        int pos1, pos2, pos3, pos4;
        float perimeter = 0;
        for (i = 0; i < pixels_num; i++) {
            Coordinate coor = cell.getPixelCoordinate(i);

            pos1 = (int) ((i - cell.top - 1) * nx + coor.y - cell.left);
            if (pos1 >= 0 && pos1 < nx * ny && !flag[pos1]
                    || i - coor.x < cell.top) {
                perimeter++;
            }

            pos2 = (int) ((coor.y - cell.top) * nx + coor.x + 1 - cell.left);
            if (pos2 >= 0 && pos2 < nx * ny && !flag[pos2]
                    || coor.x + 1 > cell.right) {
                perimeter++;
            }

            pos3 = (int) ((coor.y - cell.top + 1) * nx + coor.x - cell.left);
            if (pos3 >= 0 && pos3 < nx * ny && !flag[pos3]
                    || coor.y + 1 > cell.bottom) {
                perimeter++;
            }

            pos4 = (int) ((coor.y - cell.top) * nx + coor.x - 1 - cell.left);
            if (pos4 >= 0 && pos4 < nx * ny && !flag[pos4]
                    || coor.x - 1 < cell.left) {
                perimeter++;
            }
        }

        float fractalDimension = 1.0f;

        if (cell.getArea() != 1) {
            fractalDimension = (float) (2 * Math.log(perimeter / 4.0) / Math
                    .log(cell.getArea()));
        }

        // Note : fractalDimension is a data between 1 and 2 and the larger
        // fractalDimension is , the more irregular the shape of the cell will
        // be.

        return fractalDimension;
    }

    // -Private--------------------------------------------------------------------
    // NAME: CalSmoothness()
    //
    // TYPE: Private Member function
    //
    // Descript:
    // Given a cell, calculate the its smoothness
    // Input Arguments:
    // cell: The Fog_Cell whose smoothness is to be measured
    // Output Arguments:
    // return float: smoothness defined above
    // History:
    // May 2004 Qin Zeng (GDMB/MDL) -- created
    // Dec 2009 D Hladky ported to AWIPS II
    // -----------------------------------------------------------------------------
    private float calSmoothness(FogCell cell) {

        float mean = 0; // mean grayscale value of pixels in the cell from VIS.
        float stdDev = 0; // standard deviation
        int pixels_num = (int) cell.getArea();

        if (pixels_num == 0) {
            return 0;
        }

        int i;
        for (i = 0; i < pixels_num; i++) {
            Coordinate pixr = cell.getPixelRelativeCoordinate(i);
            // (SK) mean += getFog().getVisArray()[(int) (getFog().getNy() *
            // pixr.x + pixr.y)];
            mean += getFog().getVisArray()[(int) (getFog().getNx() * pixr.y + pixr.x)];
        }

        mean /= pixels_num;
        float tem_float;

        for (i = 0; i < pixels_num; i++) {
            Coordinate pixr = cell.getPixelRelativeCoordinate(i);
            // (SK) tem_float = getFog().getVisArray()[(int) (getFog().getNy() *
            // pixr.x + pixr.y)];
            tem_float = getFog().getVisArray()[(int) (getFog().getNx() * pixr.y + pixr.x)];
            stdDev += (tem_float - mean) * (tem_float - mean);
        }

        stdDev /= pixels_num;
        stdDev = (float) Math.sqrt(stdDev);
        // mean should not be zero, so no need to check mean here.
        return (1 - stdDev / mean) * 100; // use percent as unit
    }

    // -Private---------------------------------------------------------------------
    // NAME: Cal()
    //
    // TYPE: Private Member function
    //
    // Descript:
    // Calculate the average temperature for a Fog_Cell in channel 3.9 um
    // History:
    // May 2004 Qin Zeng (GDMB/MDL) -- created
    // -----------------------------------------------------------------------------

    private float calAverageTemp(FogCell cell) {
        float mean = 0; // mean temperature value of pixels in the cell.
        int pixels_num = (int) cell.getArea();
        if (pixels_num == 0) {
            return -999.9f;
        }
        for (int i = 0; i < pixels_num; i++) {
            Coordinate pixc = cell.getPixelRelativeCoordinate(i);
            mean += temp_at_vis_pixel(new Point((int) pixc.x, (int) pixc.y),
                    FogRecord.CHANNEL.IR3_9);
        }

        mean /= pixels_num;
        return mean;
    }

    // -Public---------------------------------------------------------------------
    // NAME: Execute()
    //
    // TYPE: Private Member function
    //
    // Descript:
    // Based on the thresholds of the VIS image data and the IR image data,
    // a number of patches of the contiguous suspected fog areas will be
    // extracted from the satellite image data.
    // This is an implementation of the feature extraction algorithm based
    // on the contiguous gray scale values.
    //
    // Arguments: None
    // History:
    // March 2004 Qin Zeng (GDMB/MDL) -- created
    // Dec 2009 D Hladky ported to Java for AWIPS II
    // -----------------------------------------------------------------------------
    private void executeCoreProcessor() {

        cells = new ArrayList<FogCell>();
        ArrayList<Point> parent = new ArrayList<Point>();
        ArrayList<Point> child = new ArrayList<Point>();
        ArrayList<Point> single_segment = new ArrayList<Point>();

        int x_dim = getFog().getNx();
        int y_dim = getFog().getNy();
        int i; // (SK) x
        int j; // (SK) y
        // To flag whether a pixel has been visited or not
        boolean[][] visited = new boolean[x_dim][y_dim];

        Point[] s = new Point[8]; // 8 surrounding points of one specific point
        for (int z = 0; z < s.length; z++) {
            s[z] = new Point();
        }

        Point tempoint = new Point();
        getCells().clear();

        for (i = 0; i < x_dim; i++) {
            for (j = 0; j < y_dim; j++) {
                visited[i][j] = false;
            }
        }
        for (j = 0; j < y_dim; j++) {
            Point firstone = new Point();
            FOG_THREAT threat_level = FOG_THREAT.BLACK;
            FogRecord.IMAGE_GROUP imggroup;
            for (i = 0; i < x_dim; i++) {
                if (visited[i][j]) {
                    continue;
                }
                Coordinate cellCoor = this.getCellCoor(i, j);
                visited[i][j] = true;
                imggroup = getFog().findGroup(i, j);
                threat_level = getThreatLevel(i, j, imggroup);
                // Sets the initial threat level
                setThreat(i, j, threat_level);
                firstone.x = i;
                firstone.y = j;
                parent.clear();
                tempoint.y = j;
                tempoint.x = i;
                single_segment.add(tempoint);
                parent.add(tempoint);
                while (parent.size() != 0) {
                    // the variables(s[1],s[2]...) below mean surrounding 1,2
                    // | 0 | 1 | 2 |
                    // | 7 | i | 3 |
                    // | 6 | 5 | 4 |
                    // iteratively search the 8-connected surrounding pixels
                    child.clear();
                    for (int k = 0; k < parent.size(); k++) {
                        // y's too small, too big
                        if (parent.get(k).y == 0) {
                            s[0].y = parent.get(k).y;
                            s[1].y = parent.get(k).y;
                            s[2].y = parent.get(k).y;
                        }
                        if (parent.get(k).y == y_dim - 1) {
                            s[4].y = parent.get(k).y;
                            s[5].y = parent.get(k).y;
                            s[6].y = parent.get(k).y;
                        }
                        // x's too small, too big
                        if (parent.get(k).x == 0) {
                            s[0].x = parent.get(k).x;
                            s[6].x = parent.get(k).x;
                            s[7].x = parent.get(k).x;
                        }
                        if (parent.get(k).x == x_dim - 1) {
                            s[2].x = parent.get(k).x;
                            s[3].x = parent.get(k).x;
                            s[4].x = parent.get(k).x;
                        }
                        // all others
                        if (parent.get(k).x > 0 && parent.get(k).x < x_dim - 1
								&& parent.get(k).y < y_dim - 1
                                && parent.get(k).y > 0) {
                            s[0].y = parent.get(k).y - 1;
                            s[0].x = parent.get(k).x - 1;
                            s[1].y = parent.get(k).y - 1;
                            s[1].x = parent.get(k).x;
                            s[2].y = parent.get(k).y - 1;
                            s[2].x = parent.get(k).x + 1;
                            s[3].y = parent.get(k).y;
                            s[3].x = parent.get(k).x + 1;
                            s[4].y = parent.get(k).y + 1;
                            s[4].x = parent.get(k).x + 1;
                            s[5].y = parent.get(k).y + 1;
                            s[5].x = parent.get(k).x;
                            s[6].y = parent.get(k).y + 1;
                            s[6].x = parent.get(k).x - 1;
                            s[7].y = parent.get(k).y;
                            s[7].x = parent.get(k).x - 1;
                        }
                        FogRecord.IMAGE_GROUP temgroup;
                        FOG_THREAT temthreat;

                        for (int m = 0; m < 8; m++) {
                            if (s[m].y >= 0 && s[m].y < y_dim && s[m].x >= 0
                                    && s[m].x < x_dim) {
                                int ii = s[m].x;
                                int jj = s[m].y;
                                // (SK) int ii = s[m].y;
                                // (SK) int jj = s[m].x;

                                if (!visited[ii][jj]) {
                                    temgroup = getFog().findGroup(ii, jj);
                                    temthreat = getThreatLevel(ii, jj, temgroup);
                                    if (temgroup == imggroup
                                            && temthreat == threat_level) {
                                        child.add(s[m]);
                                        visited[ii][jj] = true;
                                        single_segment.add(s[m]);
                                    }
                                }// end of if
                            }// end of if
                        }// end of for m
                    }// end of for k
                    parent = child;
                }// end of while
                 // if (!this.geoAdjAreas
                // .contains(geoFactory.createPoint(cellCoor))) {
                int lct = SimplePointInAreaLocator.locate(cellCoor,
                        this.geoAdjAreas);
                if (lct == 2) {
					threat_level = FOG_THREAT.BLACK;
                    setThreat(i, j, threat_level);
                }
                getCells().add(
                        new FogCell(single_segment, threat_level, imggroup));
                single_segment.clear();
			} // end of for i
		} // end of for j
    }

    /**
     * Sets all points in the cell to the cell filtered value threat
     * 
     * @param cells
     * @param threat
     */
    private void setCellThreats(ArrayList<Point> points, FOG_THREAT threat) {
        for (Point point : points) {
            getThreats()[fog.getNx() * point.y + point.x] = threat;
            // (SK) getThreats()[fog.getNy() * point.x + point.y] = threat;
        }
    }

    /**
     * Gets the drawable float array of threats
     * 
     * @return
     */
    private void setThreats() {
        float[] floats = new float[getThreats().length];
        int i = 0;
        for (FOG_THREAT threat : getThreats()) {
            floats[i] = getThreatValue(threat);
            i++;
        }

        getFog().setThreats(floats);
    }

    /**
     * Sets the thresholds
     */
    private void setThresholdArrays() {
        ir_diff_thresholds = new float[4];
        vis_thresholds = new float[4];

        ir_diff_thresholds[0] = (float) fogAlgXML.getFogProductYLo();
        ir_diff_thresholds[1] = (float) fogAlgXML.getFogProductYHi();
        ir_diff_thresholds[2] = (float) fogAlgXML.getFogProductRLo();
        ir_diff_thresholds[3] = (float) fogAlgXML.getFogProductRHi();

        vis_thresholds[0] = (float) fogAlgXML.getVisYLo();
        vis_thresholds[1] = (float) fogAlgXML.getVisYHi();
        vis_thresholds[2] = (float) fogAlgXML.getVisRLo();
        vis_thresholds[3] = (float) fogAlgXML.getVisRHi();
    }

    /**
     * Quick static method to get the float to threat mapping
     * 
     * @param threat
     * @return
     */
    public static float getThreatValue(FOG_THREAT threat) {
        float value = 0;

        if (threat == FOG_THREAT.BLACK) {
            value = 0.0f;
        } else if (threat == FOG_THREAT.GRAY) {
            value = 15.0f;
        } else if (threat == FOG_THREAT.GREEN) {
            value = 60.0f;
        } else if (threat == FOG_THREAT.YELLOW) {
            value = 110.0f;
        } else if (threat == FOG_THREAT.RED) {
            value = 220.0f;
        }
        return value;
    }

    /**
     * Gets the zone threats (ALG) with worst case for zone area
     * 
     * @return HashMap<String, FOG_THREAT>
     */
    public void setMonitorAreaThreats() {

        final FogRecord ffogRec = getFog();
        final FogMonitor fmonitor = getFogMonitor();

        Display.getDefault().syncExec(new Runnable() {
            public void run() {

                long start = System.currentTimeMillis();
                HashMap<String, Geometry> zoneGeos = fmonitor
                        .getMonitoringAreaGeometries();
                // set to lowest for default
                HashMap<String, FOG_THREAT> zoneThreats = new HashMap<String, FOG_THREAT>();
                for (String zone : zoneGeos.keySet()) {
                    zoneThreats.put(zone, FOG_THREAT.BLACK);
                }

                for (int i = 0; i < ffogRec.getNx(); i++) {
                    for (int j = 0; j < ffogRec.getNy(); j++) {

                        ReferencedCoordinate rc = new ReferencedCoordinate(
                                new Coordinate(i, j),
                                ffogRec.getGridGeometry(), Type.GRID_CENTER);
                        Coordinate coor = null;
                        try {
                            coor = rc.asLatLon();
                        } catch (TransformException e) {
                            e.printStackTrace();
                        } catch (FactoryException e) {
                            e.printStackTrace();
                        }

                        for (String zone : zoneGeos.keySet()) {
                            if (zoneGeos.get(zone) != null) {
                                if (zoneGeos.get(zone).contains(
                                        geoFactory.createPoint(coor))) {
                                    try {
                                        if (getThreatValue(getThreat(i, j)) > getThreatValue(zoneThreats
                                                .get(zone))) {
                                            zoneThreats.put(zone,
                                                    getThreat(i, j));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }

                fmonitor.setAlgorithmData(ffogRec.getRefHour().getTime(),
                        zoneThreats);
                System.out.println("FogThreat: Set algorithm zone values..."
                        + (System.currentTimeMillis() - start));
            }
        });
    }

    private Coordinate getCellCoor(int i, int j) {
        ReferencedCoordinate rc = new ReferencedCoordinate(
                new Coordinate(i, j), this.getFog().getGridGeometry(),
                Type.GRID_CENTER);
        Coordinate crd = null;
        try {
            crd = rc.asLatLon();
        } catch (TransformException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        return crd;
    }

    /** Get the Fog monitor **/
    public FogMonitor getFogMonitor() {
        if (monitor == null) {
            monitor = FogMonitor.getInstance();
        }
        return monitor;
    }
}
