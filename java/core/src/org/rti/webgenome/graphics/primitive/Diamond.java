/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-09-06 16:48:11 $


*/

package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.awt.Point;

/**
 * Represents a diamond shape.
 * @author dhall
 *
 */
public class Diamond extends GraphicPrimitive {

	/** Point in center of diamond. */
	private Point centerPoint = new Point(0, 0);
	
	/**
	 * Size in pixels of diamond at widest and highest point along
	 * Y- and X-axes, respectively.
	 */
	private int size = 0;

	/**
	 * Get point in center of diamond.
	 * @return Point in center of diamond.
	 */
	public Point getCenterPoint() {
		return centerPoint;
	}

	/**
	 * Get size in pixels of diamond at widest and highest point along
	 * Y- and X-axes, respectively.
	 * @return Size in pixels of diamond at widest and highest point along
	 * Y- and X-axes, respectively.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Constructor.
	 * @param x X-coordinate of center of diamond in pixels.
	 * @param y Y-coordinate of center of diamond in pixels.
	 * @param size Size in pixels of diamond at widest and highest point along
	 * Y- and X-axes, respectively.
	 * @param color Color of diamond
	 */
	public Diamond(final int x, final int y, final int size,
			final Color color) {
		this.centerPoint = new Point(x, y);
		this.size = size;
		this.setColor(color);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void move(final int deltaX, final int deltaY) {
		this.centerPoint.x += deltaX;
		this.centerPoint.y += deltaY;
	}
}
