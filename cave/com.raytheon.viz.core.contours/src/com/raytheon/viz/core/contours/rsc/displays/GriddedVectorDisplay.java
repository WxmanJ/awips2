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
package com.raytheon.viz.core.contours.rsc.displays;

import java.nio.FloatBuffer;

import org.geotools.coverage.grid.GeneralGridGeometry;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.referencing.GeodeticCalculator;

import com.raytheon.uf.common.geospatial.MapUtil;
import com.raytheon.uf.common.geospatial.ReferencedCoordinate;
import com.raytheon.uf.viz.core.IExtent;
import com.raytheon.uf.viz.core.IGraphicsTarget;
import com.raytheon.uf.viz.core.IGraphicsTarget.LineStyle;
import com.raytheon.uf.viz.core.drawables.PaintProperties;
import com.raytheon.uf.viz.core.exception.VizException;
import com.raytheon.uf.viz.core.map.IMapDescriptor;
import com.raytheon.uf.viz.core.rsc.DisplayType;
import com.raytheon.viz.core.contours.util.IVectorGraphicsRenderableFactory;
import com.raytheon.viz.core.contours.util.VectorGraphicsRenderable;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * Performs same functions as the original GriddedVectorDisplay using wireframe
 * shapes instead of svg for much faster performance. This is still slightly
 * experimental but seems to work well. It should also have the drawing code
 * extracted to a class similar to PointWindDisplay so wireframe shape barbs and
 * arrows can be used elsewhere.
 * 
 * <pre>
 * 
 * SOFTWARE HISTORY
 * Date         Ticket#    Engineer    Description
 * ------------ ---------- ----------- --------------------------
 * Jun 22, 2010            bsteffen     Initial creation
 * Feb 07, 2011 7948       bkowal       added a public method to get
 *                                      the direction.
 * Aug 27, 2013 2287       randerso     Added VectorGraphicsRenderable Factory to allow
 *                                      application specific rendering of wind barbs and 
 *                                      arrows.
 *                                      Added densityFactor to allow application specific 
 *                                      adjustment of density.
 *                                      Added gridRelative flag to indicate whether direction
 *                                      data is relative to grid or true north
 * 
 * </pre>
 * 
 * @author bsteffen
 * @version 1.0
 */
public class GriddedVectorDisplay extends AbstractGriddedDisplay<Coordinate> {

    private final FloatBuffer magnitude;

    private final FloatBuffer direction;

    private int lineWidth;

    private LineStyle lineStyle;

    private IExtent lastExtent;

    private VectorGraphicsRenderable vectorRenderable;

    private boolean gridRelative;

    private DisplayType displayType;

    private GeodeticCalculator gc;

    private IVectorGraphicsRenderableFactory factory;

    /**
     * @param magnitude
     * @param direction
     * @param descriptor
     * @param gridGeometryOfGrid
     * @param size
     * @param densityFactor
     *            adjustment factor to make density match A1
     * @param gridRelative
     *            true if direction is grid relative, false if relative to true
     *            north
     * @param displayType
     * @param factory
     */
    public GriddedVectorDisplay(FloatBuffer magnitude, FloatBuffer direction,
            IMapDescriptor descriptor, GeneralGridGeometry gridGeometryOfGrid,
            int size, double densityFactor, boolean gridRelative,
            DisplayType displayType, IVectorGraphicsRenderableFactory factory) {
        super(descriptor, gridGeometryOfGrid, size, densityFactor);
        this.magnitude = magnitude;
        this.direction = direction;
        this.gridRelative = gridRelative;
        this.displayType = displayType;
        this.gc = new GeodeticCalculator(descriptor.getCRS());
        this.factory = factory;
    }

    @Override
    public void paint(IGraphicsTarget target, PaintProperties paintProps)
            throws VizException {
        if (lastExtent == null
                || !lastExtent.equals(paintProps.getView().getExtent())) {
            disposeResources();
            lastExtent = paintProps.getView().getExtent().clone();
        }
        if (vectorRenderable == null) {
            vectorRenderable = factory.createRenderable(descriptor, target,
                    this.size);
            super.paint(target, paintProps);
        }
        vectorRenderable.setColor(this.color);
        vectorRenderable.setLineWidth(lineWidth);
        vectorRenderable.setLineStyle(lineStyle);
        vectorRenderable.paint(target);
    }

    @Override
    protected void issueRefresh() {
        lastExtent = null;
        super.issueRefresh();
    }

    @Override
    protected void paint(Coordinate ijcoord, PaintProperties paintProps,
            Coordinate plotLoc, double adjSize) throws VizException {
        int idx = (int) (ijcoord.x + (ijcoord.y * this.gridDims[0]));

        float spd = this.magnitude.get(idx);
        float dir = this.direction.get(idx);

        if (dir < -999999 || dir > 9999999) {
            // perhaps this check should limit +/- 180
            return;
        }

        if (Float.isNaN(spd) || Float.isNaN(dir)) {
            return;
        }

        try {
            ReferencedCoordinate rCoord = new ReferencedCoordinate(
                    gridGeometryOfGrid, ijcoord);
            Coordinate latLon = rCoord.asLatLon();
            latLon.x = MapUtil.correctLon(latLon.x);
            double[] stationLocation = { latLon.x, latLon.y };
            double[] stationPixelLocation = this.descriptor
                    .worldToPixel(stationLocation);

            if (stationPixelLocation != null) {
                stationPixelLocation[1]--;
                double[] newWorldLocation = this.descriptor
                        .pixelToWorld(stationPixelLocation);
                this.gc.setStartingGeographicPoint(stationLocation[0],
                        stationLocation[1]);
                this.gc.setDestinationGeographicPoint(newWorldLocation[0],
                        newWorldLocation[1]);
            }

            if (gridRelative) {
                // rotate data from grid up to true north
                dir += (float) MapUtil.rotation(latLon,
                        GridGeometry2D.wrap(gridGeometryOfGrid));
            }

            // rotate dir from true north to display up
            dir -= this.gc.getAzimuth();
        } catch (Exception e) {
            throw new VizException(e);
        }

        dir = (float) Math.toRadians(dir);
        switch (displayType) {
        case ARROW:
            vectorRenderable.paintArrow(plotLoc, adjSize, spd, dir);
            break;
        case BARB:
            vectorRenderable.paintBarb(plotLoc, adjSize, spd, dir);
            break;
        case DUALARROW:
            vectorRenderable.paintDualArrow(plotLoc, adjSize, spd, dir);
            break;
        default:
            throw new VizException("Unsupported disply type: " + displayType);
        }
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * @param lineStyle
     */
    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    /**
     * @param density
     *            the density to set
     */
    @Override
    public boolean setDensity(double density) {
        if (super.setDensity(density)) {
            disposeResources();
            if (this.target != null) {
                this.target.setNeedsRefresh(true);
            }
            return true;
        }
        return false;
    }

    /**
     * @param magnification
     *            the magnification to set
     */
    @Override
    public boolean setMagnification(double magnification) {
        if (super.setMagnification(magnification)) {
            disposeResources();
            if (this.target != null) {
                this.target.setNeedsRefresh(true);
            }
            return true;
        }
        return false;
    }

    /**
     * @return the magnitude
     */
    public FloatBuffer getMagnitude() {
        return magnitude;
    }

    public FloatBuffer getDirection() {
        return direction;
    }

    @Override
    protected void disposeResources() {
        if (vectorRenderable != null) {
            vectorRenderable.dispose();
            vectorRenderable = null;
        }
    }

    @Override
    protected Coordinate createResource(Coordinate coord) throws VizException {
        return coord;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.raytheon.viz.core.contours.rsc.displays.AbstractGriddedImageDisplay
     * #getImage(com.raytheon.uf.common.geospatial.ReferencedCoordinate)
     */
    @Override
    protected Coordinate getResource(Coordinate coord) {
        return coord;
    }

}
