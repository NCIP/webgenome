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
import java.util.List;


/**
 * A polyline (i.e. open polygon)
 */
public class Polyline extends GraphicPrimitive {
	
    /** Points in polyine. */
	private final List<Point> points = new ArrayList<Point>();
    
    /** Maximum number of points in polyline. */
	private final int maxSize;
    
    /** Width of line. */
	private int width = 2;
    
    /** Fill color of polyline. */
	private Color fillColor = null;
	
	
	/**
	 * Constructor.
	 * @param color Color of polyline.
	 */
	public Polyline(final Color color) {
		super(color);
		maxSize = Integer.MAX_VALUE;
	}
	
	

	/**
	 * Constructor.
	 * @param width Width of line
	 * @param color Color of line
	 */
	public Polyline(final int width, final Color color) {
		this(color);
		this.width = width;
	}
	
	


	/**
	 * Constructor.
	 * @param width Line width
	 * @param color Line color
	 * @param fillColor Fill color (if polyline closed)
	 */
	public Polyline(final int width, final Color color,
            final Color fillColor) {
		this(width, color);
		this.fillColor = fillColor;
	}
	
	

	/**
	 * Constructor.
	 * @param width Width of line
	 * @param maxSize Max number of points
	 * @param color Color
	 */
	public Polyline(final int width, final int maxSize,
            final Color color) {
		super(color);
		this.width = width;
		this.maxSize = maxSize;
	}
	
	


	/**
	 * Constructor.
	 * @param width Width
	 * @param maxSize Max number of points
	 * @param color Color of line
	 * @param fillColor Fill color (if line is closed)
	 */
	public Polyline(final int width, final int maxSize,
            final Color color, final Color fillColor) {
		this(width, maxSize, color);
		this.fillColor = fillColor;	
	}
	
	
	/**
	 * Add point to line.
	 * @param x X-coordinate of point
	 * @param y Y-coordinate of point
	 * @return <code>true</code> if point successfully
     * added (i.e. if maximum number
	 * of points has not been exceeded), <code>false</code> otherwise
	 */
	public final boolean add(final int x, final int y) {
		return add(new Point(x, y));
	}
	
	
	/**
	 * Add a point.
	 * @param p A point
	 * @return <code>true</code> if successful, <code>false</code>
	 * otherwise
	 */
	public final boolean add(final Point p) {
		boolean success = false;
		if (!this.isFull()) {
			points.add(p);
			success = true;
		}
		return success;
	}
	
	
	/**
	 * Add points.
	 * @param points Points to add
	 * @return <code>true</code> if successful, <code>false</code>
	 * otherwise
	 */
	public final boolean add(final Point[] points) {
		boolean success = true;
		for (int i = 0; i < points.length && success; i++) {
			success = this.add(points[i]);
		}
		return success;
	}
	
	
	/**
	 * Add a line.  This can have two effects:  If the
	 * last point in the list of points is the same as
	 * first point in the line, only the second point in
	 * the line is added.  Otherwise, both line points are
	 * added.
	 * @param line A line
	 * @return <code>true</code> if successful, <code>false</code>
	 * otherwise
	 */
	public final boolean add(final Line line) {
		boolean success = false;
		if (!this.isFull()) {
			if (points.size() > 0) {
				Point temp = (Point) points.get(points.size() - 1);
				if (temp.getX() != line.getX1()
						|| temp.getY() != line.getY1()) {
					this.add(new Point(line.getX1(), line.getY1()));
                }
			} else {
				this.add(new Point(line.getX1(), line.getY1()));
            }
			this.add(new Point(line.getX2(), line.getY2()));
		}
		return success;
	}
	
	
	/**
	 * Is list of points full?
	 * @return T/F
	 */
	public final boolean isFull() {
		return points.size() >= maxSize;
	}
	
	
	/**
	 * Get data points.
	 * @return <code>Point</code> objects
	 */
	public final List<Point> getPoints() {
		return points;
	}
	
	
	/**
	 * Maximum size.
	 * @return Maximum size
	 */
	public final int getMaxSize() {
		return maxSize;
	}

	/**
	 * @return Width of line.
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * @param i Width of line.
	 */
	public final void setWidth(final int i) {
		width = i;
	}

	/**
	 * @return Fill color.
	 */
	public final Color getFillColor() {
		return fillColor;
	}

	/**
	 * @param color Fill color.
	 */
	public final void setFillColor(final Color color) {
		fillColor = color;
	}
	
	
	/**
	 * Add a new line.
	 * @param x1 X-coordinate of first endpoint
	 * @param y1 Y-coordinate of first endpoint
	 * @param x2 X-coordinate of second endpoint
	 * @param y2 Y-coordinate of second endpoint
	 */
	public final void add(final int x1, final int y1,
            final int x2, final int y2) {
	    this.add(new Line(x1, y1, x2, y2));
	}
	
	
	/**
	 * Does polyline have points?
	 * @return T/F
	 */
	public final boolean empty() {
	    return this.points.size() < 1;
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
