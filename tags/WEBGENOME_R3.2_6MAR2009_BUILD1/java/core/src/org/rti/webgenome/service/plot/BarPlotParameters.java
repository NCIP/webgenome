/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-09-09 17:16:05 $


*/

package org.rti.webgenome.service.plot;


/**
 * Parameters for bar plots.
 * @author dhall
 */
public class BarPlotParameters extends PlotParameters {
	
	//
	//  C O N S T A N T S
	//
	
	/** Default bar width of pixels. */
	public static final int DEF_BAR_WIDTH = 10;
	
	//
	//     ATTRIBUTES
	//
	
	/** Height of plot row in pixels. */
	private int rowHeight = 300;
	
	/** Width of each individual bar in pixels. */
	private int barWidth = DEF_BAR_WIDTH;
	
	
	//
	//     GETTERS/SETTERS
	//

	/**
	 * Get width of individual bars.
	 * @return Width in pixels
	 */
	public final int getBarWidth() {
		return barWidth;
	}

	/**
	 * Set width of individual bars.
	 * @param barWidth Width in pixels
	 */
	public final void setBarWidth(final int barWidth) {
		this.barWidth = barWidth;
	}

	/**
	 * Get height of plot row.
	 * @return Height in pixels
	 */
	public final int getRowHeight() {
		return rowHeight;
	}

	/**
	 * Set height of plot row.
	 * @param height Height in pixels
	 */
	public final void setRowHeight(final int height) {
		this.rowHeight = height;
	}
	
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	public BarPlotParameters() {
		
	}
	
	
	/**
	 * Constructor.  A clone will be created by
	 * deep copy.
	 * @param params Bar plot parameters from which to
	 * initialize properties.
	 */
	public BarPlotParameters(final BarPlotParameters params) {
		this.barWidth = params.barWidth;
		this.rowHeight = params.rowHeight;
	}
	
	//
	//     ABSTRACTS
	//
	
    /**
     * Return clone of this object derived by deep copy of
     * all attributes.
     * @return Clone of this object
     */
    public PlotParameters deepCopy() {
    	return new BarPlotParameters(this);
    }
}
