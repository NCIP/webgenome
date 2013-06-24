/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.graphics;

/**
 * Type of interpolation to perform on genomic regions
 * between reporters.  Such interpolation is displayed
 * in plots. For scatter plots, lines and/or curves are
 * drawn between data points.  On color coded plots,
 * color segments may change abruptly or gradually fade
 * together.
 * @author dhall
 *
 */
public enum InterpolationType {
	
	/** Do not interpolate between reporters.*/
	NONE,
	
	/** Draw a conceptual straight line between reporters. */
	STRAIGHT_LINE,
	
	/**
	 * Draw conceptual horizontal line out from points
	 * with an abrupt step connect lines at the midpoint
	 * between reporters.
	 */
	STEP,
	
	/** Connect data points with a cubic spline. */
	SPLINE;

}
