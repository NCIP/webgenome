/*
$Revision: 1.8 $
$Date: 2007-03-01 16:50:23 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.service.plot;

import org.rti.webcgh.units.ChromosomeIdeogramSize;

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
