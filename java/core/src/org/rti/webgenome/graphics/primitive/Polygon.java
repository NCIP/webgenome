/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A polygon.
 */
public class Polygon extends GraphicPrimitive {
    
    
    // ==============================
    //      Attributes
    // ==============================
    
	/** Points in polygon. */
    private List<Point> points = new ArrayList<Point>();
    
    
    /**
     * Get points.
     * @return Points in polygon.
     */
    public final Point[] getPoints() {
    	Point[] newPoints = new Point[0];
    	newPoints = (Point[]) this.points.toArray(newPoints);
        return newPoints;
    }
    
    
    /**
     * Get points.
     * @param points Points
     */
    public final void setPoints(final Point[] points) {
        this.points = new ArrayList<Point>();
        for (int i = 0; i < points.length; i++) {
        	this.points.add(points[i]);
        }
    }
    
    
    // ==================================
    //      Constructors
    // ==================================
    
    /**
     * Constructor.
     * @param points Points
     * @param color Color
     */
    public Polygon(final Point[] points, final Color color) {
    	super(color);
        this.setPoints(points);
    }
    
    
    /**
     * Constructor.
     * @param color Color
     */
    public Polygon(final Color color) {
    	super(color);
    }
    
    // =================================================
    //        Public methods
    // =================================================
    
    /**
     * Add a point.
     * @param point A point
     */
    public final void addPoint(final Point point) {
        this.points.add(point);
    }
    
    
    /**
     * Add a point.
     * @param x X-coordinate of point
     * @param y Y-coordinate of point
     */
    public final void addPoint(final int x, final int y) {
    	this.addPoint(new Point(x, y));
    }
    
    
    /**
     * Convert polygon to a pretty string for printing.
     * @return Pretty string
     */
    public final String toPrettyString() {
    	StringBuffer buff = new StringBuffer("[");
    	if (this.points != null) {
    		for (Iterator it = this.points.iterator(); it.hasNext();) {
    			Point point = (Point) it.next();
    			buff.append("(" + point.x + ", " + point.y + ")");
    		}
    	}
    	buff.append("]");
    	return buff.toString();
    }
    
	// ==================================
	//     Implemented abstract methods
	// ==================================
	
	/**
	 * Move graphic primitive.
	 * @param deltaX Change in X-coordinates in pixels
	 * @param deltaY Change in Y-coordinates in pixels
	 */
	public final void move(final int deltaX, final int deltaY) {
		for (Point p : this.points) {
			p.x += deltaX;
			p.y += deltaY;
		}
	}
}
