/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-06-28 22:12:17 $


*/

package org.rti.webgenome.service.plot;

import org.rti.webgenome.units.ChromosomeIdeogramSize;

/**
 * Plot parameters for ideogram plots.
 * @author dhall
 *
 */
public class IdeogramPlotParameters extends HeatMapPlotParameters {
	
	// ==========================
	//   Constants
	// ==========================
	
	/** Default width of data tracks. */
	public static final int DEF_TRACK_WIDTH = 20;
	
	/** Default ideogram size. */
	public static final ChromosomeIdeogramSize DEF_IDEOGRAM_SIZE =
		ChromosomeIdeogramSize.MEDIUM;
	
	/** Default ideogram thickness in pixels. */
	public static final int DEF_IDEOGRAM_THICKNESS = 20;
	
	
	// ============================
	//     Attributes
	// ============================
	
	/** Ideogram size (length). */
	private ChromosomeIdeogramSize ideogramSize = DEF_IDEOGRAM_SIZE;
	
	/** Width of data tracks in pixels. */
	private int trackWidth = DEF_TRACK_WIDTH;
	

	/** Ideogram thickness in pixels. */
	private int ideogramThickness = DEF_IDEOGRAM_THICKNESS;
	
	
	// ============================
	//       Getters/setters
	// ============================

	/**
	 * Get ideogram thickness in pixels.
	 * @return Ideogram thickness in pixels.
	 */
	public final int getIdeogramThickness() {
		return ideogramThickness;
	}


	/**
	 * Set ideogram thickness.
	 * @param ideogramThickness Ideogram thickness in pixels.
	 */
	public final void setIdeogramThickness(final int ideogramThickness) {
		this.ideogramThickness = ideogramThickness;
	}


	/**
	 * Get width of data tracks in pixels.
	 * @return Width of data tracks in pixels.
	 */
	public final int getTrackWidth() {
		return trackWidth;
	}


	/**
	 * Set width of data tracks in pixels.
	 * @param trackWidth Width of data tracks in pixels.
	 */
	public final void setTrackWidth(final int trackWidth) {
		this.trackWidth = trackWidth;
	}

	/**
	 * Get ideogram size.
	 * @return Ideogram size.
	 */
	public final ChromosomeIdeogramSize getIdeogramSize() {
		return ideogramSize;
	}

	/**
	 * Set ideogram size.
	 * @param ideogramSize Ideogram size.
	 */
	public final void setIdeogramSize(
			final ChromosomeIdeogramSize ideogramSize) {
		this.ideogramSize = ideogramSize;
	}

	
	// ================================
	//       Constructors
	// ================================
	
	/**
	 * Constructor.
	 */
	public IdeogramPlotParameters() {
		super();
	}
	
	
	/**
	 * Constructor.
	 * @param params Ideogram plot parameters
	 */
	public IdeogramPlotParameters(final IdeogramPlotParameters params) {
		super(params);
		this.ideogramSize = params.ideogramSize;
		this.ideogramThickness = params.ideogramThickness;
		this.trackWidth = params.trackWidth;
	}
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Get name of ideogram size.
	 * @return String equivalent of ideogram size.
	 */
	public final String getIdeogramSizeByName() {
		return this.ideogramSize.getName();
	}
	
	
	/**
	 * Set ideogram size by name.
	 * @param name String equivalent of ideogram size.
	 */
	public final void setIdeogramSizeByName(final String name) {
		this.ideogramSize =
			ChromosomeIdeogramSize.getChromosomeIdeogramSize(name);
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
    	return new IdeogramPlotParameters(this);
    }
}
