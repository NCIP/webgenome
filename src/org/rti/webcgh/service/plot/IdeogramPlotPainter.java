/*
$Revision: 1.4 $
$Date: 2006-09-09 18:41:52 $

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

import java.awt.Color;
import java.util.Collection;

import org.rti.webcgh.domain.Cytoband;
import org.rti.webcgh.domain.CytologicalMap;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.graphics.util.CentromereWarper;
import org.rti.webcgh.graphics.util.ColorMapper;
import org.rti.webcgh.graphics.util.Warper;
import org.rti.webcgh.graphics.widget.GenomeFeaturePlot;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.ChromosomeIdeogramSize;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;


/**
 * Generates ideogram plots by assembling and laying out
 * the necessary widgets.
 * @author dhall
 *
 */
public class IdeogramPlotPainter extends PlotPainter {
	
	// ======================
	//    Constants
	// ======================
	
	/** Thickness of line that frames ideogram. */
	private static final int FRAME_LINE_THICKNESS = 3;
	
	/** Color of line that frames ideogram. */
	private static final Color FRAME_LINE_COLOR = Color.black; 
	
	// ======================
	//       Attributes
	// ======================
	
	/**
	 * Color mapper to map cytoband stain
	 * intensities to colors.
	 */
	private final ColorMapper colorMapper;
	
	// ========================
	//     Constructors
	// ========================
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome array
	 * data getter
	 * @param colorMapper Color mapper to map cytoband stain
	 * intensities to colors.
	 */
	public IdeogramPlotPainter(
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter,
			final ColorMapper colorMapper) {
		super(chromosomeArrayDataGetter);
		this.colorMapper = colorMapper;
	}
	
	
	// =========================
	//    Business methods
	// =========================
	
	/**
	 * Paint ideogram plot on given plot panel.
	 * @param panel Plot panel to paint on
	 * @param experiments Experiments to plot
	 * @param cytologicalMap Cytological map to plot
	 * @param plotParameters Plot parameters
	 */
	public final void paintIdeogramPlot(
			final PlotPanel panel,
			final Collection<Experiment> experiments,
			final CytologicalMap cytologicalMap,
			final IdeogramPlotParameters plotParameters) {
		
		// Paint chromosome ideogram
		this.paintChromosomeIdeogram(panel, cytologicalMap, plotParameters);
	}
	
	
	/**
	 * Paint chromosome ideogram on given plot panel.
	 * @param plotPanel Plot panel to paint on
	 * @param cytologicalMap Cytological map to convert graphically
	 * into an ideogram
	 * @param plotParameters Plot parameters
	 */
	private void paintChromosomeIdeogram(final PlotPanel plotPanel,
			final CytologicalMap cytologicalMap,
			final IdeogramPlotParameters plotParameters) {
		
		// Calculate height of ideogram
		ChromosomeIdeogramSize idSize = plotParameters.getIdeogramSize();
		int height =
			plotParameters.getIdeogramSize().pixels(cytologicalMap.length());
		
		// Instantiate genome feature plot
		GenomeFeaturePlot plot = new GenomeFeaturePlot(1,
				cytologicalMap.length(), height, Orientation.VERTICAL);
		
		// Add warper to give plot hourglass shape around centromere
		int centStartPix = idSize.pixels(cytologicalMap.getCentromereStart());
		int centEndPix = idSize.pixels(cytologicalMap.getCentromereEnd());
		Warper warper = new CentromereWarper(plot.getFeatureHeight(),
				centStartPix, centEndPix);
		plot.setWarper(warper);
		
		// Add border frame around ideogram
		plot.addFrame(Location.ABOVE, FRAME_LINE_THICKNESS, FRAME_LINE_COLOR);
		plot.addFrame(Location.BELOW, FRAME_LINE_THICKNESS, FRAME_LINE_COLOR);
		
		// Add end caps
		
		// Add cytobands to plot
		for (Cytoband c : cytologicalMap.getCytobands()) {
			Color color = this.colorMapper.getColor(c.getStain());
			plot.plotFeature(c.getStart(), c.getEnd(), c.getName(), null,
					false, color);
		}
		
		// Add genome feature plot
		plotPanel.add(plot);
	}
			
}
