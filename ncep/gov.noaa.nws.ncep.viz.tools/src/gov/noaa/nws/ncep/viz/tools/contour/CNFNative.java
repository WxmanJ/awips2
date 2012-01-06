/*
 * CNFNative
 * 
 * Date created 14 JUNE 2010
 *
 * This code has been developed by the SIB for use in the AWIPS2 system.
 */
package gov.noaa.nws.ncep.viz.tools.contour;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Library;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateList;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.operation.linemerge.LineMerger;

/**
 * a Java interface to the grid contour generating algorithm in the native cnflib
 * using JNA.
 * 
 * To properly use the library, users will need to invoke all of the following static methods
 * in order.
 * 
 * CNFNative.setContourValues() - Initializes the cnflib data structures and specifies all of the 
 *                                contour values that should be generated.
 * CNFNative.generateContours() - Provides the grid data values and grid dimensions to the cnflib.
 *                                The contours are generated at this point for this grid, and for the
 *                                contour values supplied in the call to setContourValues().
 * CNFNative.getContour() -       Retrieves all the contour lines and polygons for the given contour value
 *                                from the data structures in the native cnflib.
 * CNFNative.getEdges() -         Retrieves polygons marking the edges of the grid and areas with missing
 *                                data from the data structures in the native cnflib.           
 * CNFNative.cleanup() -          Releases the memory held by the data structures in the native cnflib. 
 *                                Users should call this method after all desired data is retrieved from 
 *                                cnflib.  Note: If users wish to contour another grid after this method is
 *                                invoked, they must start over and call the setContourValues() method first.                                                               
 *                        
 * NOTES:
 * 1)  The first grid point is defined to be (1,1) on the grid.  ( does not start at (0,0) )
 * 2)  Missing data values are assumed to be -999999.f
 * 3)  The ordering of the points in the contour lines and closed polygons that are generated 
 *     by cnflib is meaningful.  For grids that start in the lower left corner and traverse across
 *     rows from bottom to top, the direction of the contours indicate that grid data values to 
 *     the right of the contour lines are greater that the value of the contour.  Grid data values
 *     on the left of the line are less than the contour.
 *     However, if the grid is ordered so that the upper left corner is the first point and traversing 
 *     across rows from top to bottom, then the opposite is true.  Higher grid values are to the left of 
 *     the lines and lower values to the right.
 *     
 *                        
 * @author sgilbert
 *
 */
public class CNFNative {

	/** 
	 * Defines the interface to each of the "C" routines in cnflib.. 
	 */
	public interface cnflib extends Library {
		cnflib INSTANCE = (cnflib) Native.loadLibrary("cnflib", cnflib.class);

		/* Initializes the data structures used in cnflib */
		void cnf_init(int[] iret);
		
		/* Releases memory used by the data structures in cnflib */
		void cnf_done(int[] iret);
		
		/* Used to dump contour and edge information in cnflib for debugging purposes. */
		void cnf_dump(int[] type, int[] iret);

		/* Sets the values for which contours will be generated */
		void cnf_vals(int[] nclvl, float[] clvl, int[] icolrs, int[] lintyp,
                int[] linwid, int[] linlbl, int[] nflvl, float[] flvl,
                int[] ifcolr, int[] iftype, int[] iret);
		
		/*  Calculates the contours for the given grid data values.*/
		void cnf_comp(int[] xdim, int[] ydim, float[] grid, int[] xoff, int[] yoff,
				      int[] skip, int[] iret );
		
		/*  Returns the number of contour segments/polygons created for the given contour value */
		void cnf_getnumcntrs ( float cval, int[] numcntrs );
		
		/*  
		 * Returns the number of contour segments/polygons (and the number of points in each)
		 *  created for the given contour value
		 */
		void cnf_getcntrinfo ( float cval, int[] numpolys, int[] numpoints );
		
		/*  Retrieves the contour segments/polygons for the given contour value */
		void cnf_getcontour ( float cval, float[] xcoord, float[] ycord,
				               int[] numpolys, int[] numpoints );
		
		/*  Returns the number of polygon edges generated for the grid  */
		void cnf_getnumedges ( int[] numedges );
		
		/*  Returns the number of points in each polygon edge  */
		void cnf_getedgeinfo ( int[] numedges, int[] numpoints );
		
		/*  Retrieves the polygon edges for the grid used in the contour generation  */
		void cnf_getedges ( float[] xcoord, float[] ycoord, int[] numedges, int[] numpoints );

		/*  Returns the contour value at the given index  */
		void cnf_gval(int[] index, float[] value, int[] iret);

		
	}

	/**
	 * Initializes the data structures used in cnflib, and
	 * supplies the data values for which to generate contours.
	 * @param cvalues Array of contour values
	 */
	public static void setContourValues(float [] cvalues ) {
		
		int[] iret = new int[1];
		int[] idummy = new int[cvalues.length];
		int[] noFill = new int[] {0};
		
		cnflib.INSTANCE.cnf_init(iret);
		//System.out.println("cnf_init returns: "+iret[0]);
		cnflib.INSTANCE.cnf_vals( new int[] {cvalues.length}, cvalues, idummy, idummy, idummy, idummy,
				                  noFill, cvalues, idummy, idummy, iret);
		//System.out.println("cnf_vals returns: "+iret[0]);
	}
	
	/**
	 * Generates contour lines for the given grid for the values that were specified in setContourValues().
	 * @param grid grid data values
	 * @param szX X dimension of the grid
	 * @param szY Y dimension of the grid
	 */
	public static void generateContours(float[] grid, int szX, int szY) {
		
		int[] iret = new int[1];
		int[] izero = new int[] {0};
		
		cnflib.INSTANCE.cnf_comp( new int[] {szX}, new int[] {szY}, grid, izero, izero, izero, iret );
		//System.out.println("cnf_comp returns: "+iret[0]);
		
	}

	/*
	public static void generateFillPolys( ) {
		
		int[] iret = new int[1];
		//int[] izero = new int[] {0};
		
		cnflib.INSTANCE.cnf_prep( iret );
		System.out.println("cnf_prep returns: "+iret[0]);
		
		if ( iret[0] == 0 ) cnflib.INSTANCE.cnf_fill( iret );
		System.out.println("cnf_fill returns: "+iret[0]);
		
	}
	*/

	/**
	 * Returns the LineStrings and Polygons representing the contours for the given value
	 * in a GeometryCollection 
	 */
	public static Geometry getContour(float cval) {
		
		int maxPts = Integer.MIN_VALUE;
		int[] numCntrs = new int[1];
		
		GeometryFactory gf = new GeometryFactory();

		// query number of contours for array allocation
		cnflib.INSTANCE.cnf_getnumcntrs(cval, numCntrs);
		int[] numPoints = new int[ numCntrs[0] ];
		
	//	Native.setProtected(true);
		try {
			// query number of points in each contour for array allocation
			cnflib.INSTANCE.cnf_getcntrinfo(cval, numCntrs, numPoints);
        }
        catch (Exception e) {     // does not catch probs in native code
        	System.out.println("cnflib Exception "+e);
        }
        catch (Error e) {      // does not catch probs in native code
        	System.out.println("cnflib Error: "+e);
        }
		//System.out.println("CONTOUR INFO for "+cval+": "+numCntrs[0] +"    :   "+ Arrays.toString(numPoints) );
		
		for ( int j=0; j<numPoints.length; j++ ) maxPts = Math.max( maxPts, numPoints[j]);
		float[] xc = new float[maxPts*numCntrs[0]];
        float[] yc = new float[maxPts*numCntrs[0]];
        
        try {
        	// retrieve all contour points for all contours of cval from cnflib
        	cnflib.INSTANCE.cnf_getcontour(cval, xc, yc, numCntrs, numPoints);
        }
        catch (Exception e) {       // does not catch probs in native code
        	System.out.println("cnflib Exception "+e);
        }
        catch (Error e) {      // does not catch probs in native code
        	System.out.println("cnflib Error: "+e);
        }
        int numpoly = numCntrs[0];
        
        List<Geometry> geoms = new ArrayList<Geometry>();
        
        /*
         * All contour points are returned from cnflib in one long array.
         * Loop through the number of contours and number of points in each contour
         * to separate the contour segments and polygons.
         */
        int num = 0;
        for (int n=0; n < numpoly; n++ ) {
        	
        	Coordinate[] coords = new Coordinate[numPoints[n]];
        	for ( int i=0; i < numPoints[n]; i++ ) {
        		coords[i] = new Coordinate( xc[num], yc[num] );
        		num++;
        	}

        	/*
        	 * if contour is closed create a Polygon.  Otherwise, usa a LineString
        	 */
        	if ( coords[0].equals2D( coords[coords.length-1]) && (coords.length > 3) ) {
        		LinearRing lr = gf.createLinearRing(coords);
        		Polygon poly = gf.createPolygon(lr, null);
        		geoms.add(poly);
        	}
        	else {
        		LineString ls = gf.createLineString(coords);
       			geoms.add(ls);
        	}
        	
	}
		              
		return gf.createGeometryCollection( geoms.toArray(new Geometry[] {}) );
	}
	
	/**
	 * Returns geometrys for grid edges and areas of missing data
	 * @return
	 */
	public static Geometry getEdges() {
		
		int maxPts = Integer.MIN_VALUE;
		int[] numEdges = new int[1];
		
		GeometryFactory gf = new GeometryFactory();
		
		//  query number of edges for array allocation
		cnflib.INSTANCE.cnf_getnumedges( numEdges );
		int[] numPoints = new int[ numEdges[0] ];

		//  query number of points in each edge for array allocation
		cnflib.INSTANCE.cnf_getedgeinfo( numEdges, numPoints);
		//System.out.println("EDGE INFO : "+numEdges[0] +"    :   "+ Arrays.toString(numPoints) );
		
		for ( int j=0; j<numPoints.length; j++ ) maxPts = Math.max( maxPts, numPoints[j]);
		float[] xc = new float[maxPts*numEdges[0]];
        float[] yc = new float[maxPts*numEdges[0]];
        
        try {
        	// get edges from cnflib
        	cnflib.INSTANCE.cnf_getedges( xc, yc, numEdges, numPoints);
        }
        catch (Exception e) {      // does not catch probs in native code
        	System.out.println("cnflib Exception: "+e);
        }
        catch (Error e) {         // does not catch probs in native code
        	System.out.println("cnflib Error: "+e);
        }
        int numpoly = numEdges[0];
        
        List<Geometry> geoms = new ArrayList<Geometry>();
        
        /*
         * All Edge points are returned from cnflib in one long array.
         * Loop through the number of edges and number of points in each edge poly
         * to separate the edge polygons.
         */
        int num = 0;
        for (int n=0; n < numpoly; n++ ) {

        	//System.out.println("EDGE "+n+": "+numPoints[n]+" points");
        	Coordinate[] coords = new Coordinate[numPoints[n]];
        	for ( int i=0; i < numPoints[n]; i++ ) {
        		coords[i] = new Coordinate( Math.rint(xc[num]), Math.rint(yc[num]) );
        		num++;
        	}

        	//System.out.println("Coords:"+coords[0]+"  "+coords[coords.length-1]);
        	if ( coords[0].equals2D( coords[coords.length-1]) && (coords.length > 3) ) {
        		//System.out.println("CLOSED EDGE!!!!!!!!!!!!");       // For inner missing areas maybe?
        		LinearRing lr = gf.createLinearRing(coords);
        	//	Polygon poly = gf.createPolygon(lr, null);
        		geoms.add(lr);
        	}
        	else {
        		//System.out.println("OPEN EDGE!!!!!!!!!!!!");     // For missing areas around edge of grid?
        		//LineString ls = gf.createLineString(coords);
        		CoordinateList cl = new CoordinateList(coords, false);
        		LineString ls = gf.createLineString(cl.toCoordinateArray());
        		geoms.add(ls);
        	}
        }

        /*
         * merge open edges together
         */
		LineMerger lm = new LineMerger();
		lm.add(geoms);

		geoms.clear();
		/*
		 * make merged linestring into LinearRing
		 */
		for ( Object obj : lm.getMergedLineStrings() ) {
			LineString ls = (LineString)obj;
			//System.out.println("GOT... "+ls.getClass().getCanonicalName()+((LineString)ls).getBoundary()+((LineString)ls).getNumPoints());
			CoordinateList cl = new CoordinateList(ls.getCoordinates(), true);
			cl.closeRing();
			LinearRing lr = gf.createLinearRing(cl.toCoordinateArray());
			lr.normalize();
			geoms.add(lr);
		}
			
        return  geoms.get(0);
		
	}

	/**
	 * Releases memory resources held by the data structures in cnflib
	 */
	public static void cleanUp() {
		int[] iret = new int[1];
		//int[] type = new int[] {2};
		//cnflib.INSTANCE.cnf_dump(type, iret);
		
		cnflib.INSTANCE.cnf_done(iret);
		//System.out.println("cnf_done returns: "+iret[0]);
	}

	/*
	 * For testing purposes...
	 */
	public static void main(String[] args) {

		int[] iret = new int[] {77,88,66,99,33};
		int[] inputi = new int[] {5,6,7,8,9};
		float[] inputf = new float[] {5.f,6.f,7.f,8.f,9.f};
		
		float[] cval = new float[1];
		
		cnflib.INSTANCE.cnf_init(iret);
		System.out.println("init returning "+iret[0] +" "+iret[1]);

		cnflib.INSTANCE.cnf_done(iret);
		System.out.println("done returning "+iret[0]+" "+iret[1]);

		cnflib.INSTANCE.cnf_vals(inputi, inputf, inputi, inputi, inputi, inputi, inputi,
				inputf, inputi, inputi, iret );
		System.out.println("vals returning "+iret[0]+" "+iret[1]);
		
		for ( int i=0; i <= inputf.length; i++ ) {
			cnflib.INSTANCE.cnf_gval( new int[] {i}, cval, iret);
			System.out.println("contour "+i+": "+cval[0]+"    "+iret[0]);
		}
		
		Coordinate start = new Coordinate( 1, 5 );
		Coordinate end = new Coordinate( 1, 6 );
		
		System.out.println( CGAlgorithms.isOnLine( new Coordinate(2, 5.5), new Coordinate[]{start,end}));
		System.out.println( CGAlgorithms.isOnLine( new Coordinate(1, 4), new Coordinate[]{start,end}));
		System.out.println( CGAlgorithms.isOnLine( new Coordinate(1, 5.5), new Coordinate[]{start,end}));
		
		Coordinate[] ring1 = new Coordinate[] { new Coordinate(1,1), new Coordinate(10,1),
				                                new Coordinate(10,10), new Coordinate(1,10),
				                                new Coordinate(1,1)  };
		System.out.println("ring1 is CCW? " + CGAlgorithms.isCCW(ring1));
		
		Coordinate[] ring2 = new Coordinate[] { new Coordinate(1,1), new Coordinate(1,10),
                new Coordinate(10,10), new Coordinate(10,1),
                new Coordinate(1,1)  };
		System.out.println("ring2 is CCW? " + CGAlgorithms.isCCW(ring2));

		//}
	}

}
