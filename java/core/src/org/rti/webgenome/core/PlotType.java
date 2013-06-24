/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-09-13 23:42:17 $


*/

package org.rti.webgenome.core;


/**
 * Plot type.
 * @author dhall
 *
 */
public enum PlotType {

	/** Scatter plot. */
	SCATTER,
	
	/** Ideogram plot. */
	IDEOGRAM,
	
	/** Annotation plot. */
	ANNOTATION,
	
	/** Bar plot. */
	BAR,
	
	/** Frequency plot. */
	FREQUENCY,
	
	/** Genomic snapshot. */
	GENOME_SNAPSHOT;
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Is given plot type a heat map plot?
	 * @param plotType Plot type
	 * @return T/F
	 */
	public static boolean isHeatMapPlot(final PlotType plotType) {
		return plotType == ANNOTATION || plotType == IDEOGRAM;
	}
	
	
	/**
	 * Is given plot a genomic plot?
	 * @param plotType Plot type
	 * @return T/F
	 */
	public static boolean isGenomePlot(final PlotType plotType) {
		return plotType == ANNOTATION || plotType == IDEOGRAM
		|| plotType == SCATTER;
	}
}
