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

package org.rti.webgenome.graphics.widget;

import java.awt.Point;


/**
 * A plot element with a scale in some units of measurement
 * in 1 or 2 dimensions.
 * @author dhall
 *
 */
public interface ScalePlotElement extends PlotElement {
	
	
	/**
	 * Return point in pixels corresponding to a zero point
	 * in the native units of measurement.
	 * @return A point or <code>null</code> if the element
	 * does not contain a zero point
	 */
	Point zeroPoint();

}
