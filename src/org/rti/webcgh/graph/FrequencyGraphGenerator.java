/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.DataSet;
import org.rti.webcgh.array.ExperimentIterator;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.GenomeInterval;
import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.QuantifiedIntervals;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicEvent;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.drawing.Rectangle;
import org.rti.webcgh.drawing.VerticalAlignment;
import org.rti.webcgh.graph.widget.Axis;
import org.rti.webcgh.graph.widget.Background;
import org.rti.webcgh.graph.widget.Caption;
import org.rti.webcgh.graph.widget.FrequencyPlot;
import org.rti.webcgh.graph.widget.Legend;
import org.rti.webcgh.graph.widget.PlotPanel;

public class FrequencyGraphGenerator {
	
	private int padding = 10;
    private Color backgroundColor = new Color(235, 235, 235);
    private Color matteColor = new Color(255, 255, 205);
    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    
    
    public PersistentDomainObjectMgr getPersistentDomainObjectMgr() {
		return persistentDomainObjectMgr;
	}


	public void setPersistentDomainObjectMgr(
			PersistentDomainObjectMgr persistentDomainObjectMgr) {
		this.persistentDomainObjectMgr = persistentDomainObjectMgr;
	}


	/**
     * Generate a plot
     * @param dataSet Data set
     * @param plotParameters Plot parameters
     * @param quantitationType Quantitation type
     * @param canvas A drawing canvas
     */
    public void createPlot(DataSet dataSet, 
			PlotParameters plotParameters, QuantitationType quantitationType,
			DrawingCanvas canvas) {
    	if (quantitationType == null)
    		quantitationType = QuantitationType.UNKNOWN;
    	
    	// Create a panel to add graph widgits to
    	DrawingCanvas tile = canvas.newTile();
        PlotPanel plotPanel = new PlotPanel(tile);
        
        // Instantiate intervals of the genome that will be graphed
        GenomeInterval[] genomeIntervals = 
        	this.extractGenomeIntervals(plotParameters.getGenomeIntervalDtos(), 
        			dataSet, plotParameters.getXUnits());
        
        // Generate quantified intervals
        
        // Draw graph on panel
        QuantifiedIntervals qis = dataSet.amplificationFrequencies(plotParameters.getUpperMaskValue());
        this.createFrequencyPlot(qis, genomeIntervals, quantitationType, 
        		plotParameters, plotPanel, tile);
        
        // Add background and matte to drawing canvas and associate event listeners with these
        int width = plotPanel.width() + this.padding * 2;
        int height = plotPanel.height() + this.padding * 2;
        Rectangle background = new Rectangle(0, 0, width, height, this.matteColor);
        background.addGraphicEventResponse(GraphicEvent.mouseMoveEvent, "hideToolTip()");
        background.addGraphicEventResponse(GraphicEvent.mouseClickEvent, "noHighlight()");
        canvas.add(background);
        
        // Add plot panel to drawing canvas so that it is on top of background
        canvas.add(tile, -plotPanel.topLeftPoint().x + this.padding, -plotPanel.topLeftPoint().y + this.padding);
        
        // Set size dimensions on drawing canvas
        canvas.setWidth(width);
        canvas.setHeight(height);
    }
	
	
    private void createFrequencyPlot(QuantifiedIntervals intervals,
    		GenomeInterval[] genomeIntervals, QuantitationType quantitationType,
            PlotParameters plotParameters, PlotPanel panel, DrawingCanvas canvas) {
        
        // Create Y-axis
        Axis yAxis = new Axis(0, 1.0, plotParameters.getHeight(), 
        		Orientation.VERTICAL, Location.LEFT_OF);
        
        // Create individual plots
        int cols = (genomeIntervals.length < plotParameters.getPlotsPerRow()) ?
                genomeIntervals.length : plotParameters.getPlotsPerRow();
        int totalPadding = (cols - 1) * this.padding;
        double widthScale = 
        	((double)plotParameters.getWidth() - totalPadding) / (double)this.totalBP(genomeIntervals, cols);
        PlotPanel row = panel.newChildPlotPanel();
        for (int i = 0; i < genomeIntervals.length; i++) {
        	int width = (int)((double)genomeIntervals[i].span() * widthScale);
            PlotPanel freqPlotPanel = this.createFrequencyPlot(intervals, genomeIntervals[i], 
                    width, plotParameters, panel, 0, 100);
            if (i % cols == 0 && i > 0) {
                PlotPanel axisPanel = panel.newChildPlotPanel();
                this.addYAxis(axisPanel, yAxis, quantitationType);
                row.add(axisPanel, HorizontalAlignment.LEFT_JUSTIFIED, 
                		VerticalAlignment.BOTTOM_JUSTIFIED);
                panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
                row = panel.newChildPlotPanel();
            }
            boolean referencePlot = (i % cols == 0)? true : false;
            row.add(freqPlotPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.BOTTOM_JUSTIFIED, referencePlot);
        }
        if (genomeIntervals.length % cols >= 0) {
            PlotPanel axisPanel = panel.newChildPlotPanel();
            this.addYAxis(axisPanel, yAxis, quantitationType);
            row.add(axisPanel, HorizontalAlignment.LEFT_JUSTIFIED,
            		VerticalAlignment.BOTTOM_JUSTIFIED);
            panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
        }
    }
    
    
    private void addYAxis(PlotPanel panel, Axis yAxis, 
            QuantitationType quantitationType) {
        panel.add(yAxis, HorizontalAlignment.LEFT_JUSTIFIED, 
        		VerticalAlignment.BOTTOM_JUSTIFIED, true);
        Caption caption = new Caption(quantitationType.getName(), 
        		Orientation.HORIZONTAL, true);
        panel.add(caption, HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
    }
    
    
    private double minY(DataSet dataSet, PlotParameters params) {
    	return 0.0;
    }
    
    
    private double maxY(DataSet dataSet, PlotParameters params) {
    	return 1.0;
    }
    
    
    private int totalBP(GenomeInterval[] intervals, int cols) {
    	int total = 0;
    	for (int i = 0; i < intervals.length && i < cols; i++)
    		total += intervals[i].span();
    	return total;
    }
    
    
    private PlotPanel createFrequencyPlot(QuantifiedIntervals intervals, GenomeInterval genomeInterval,
            int width, PlotParameters plotParameters, PlotPanel parentPanel,
			double minY, double maxY) {
    	PlotPanel panel = parentPanel.newChildPlotPanel();
    	PlotBoundaries plotBoundaries = genomeInterval.newPlotBoundaries(minY, maxY);
    	
    	// Add background
    	Background background = new Background(width, plotParameters.getHeight(), 
    			this.backgroundColor);
    	panel.add(background, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	
    	// Add plot
    	FrequencyPlot scatterPlot = new FrequencyPlot(width, 
    			plotParameters.getHeight(), genomeInterval.startBp(),
    			genomeInterval.endBp(), 0, 100);
    	scatterPlot.graphQuantifiedInterval(intervals);
    	panel.add(scatterPlot, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	
    	// Add x-axis
    	Axis xAxis = plotBoundaries.newXAxis(width, VerticalAlignment.BOTTOM_JUSTIFIED);
    	xAxis.setNumberFormatter(plotParameters.getXUnits().numberFormatter());
    	panel.add(xAxis, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED, true);
    	    	
    	// Add x-axis caption
    	String captionText = 
    	    "CHR " + genomeInterval.toPrettyString() + " (" + plotParameters.getXUnits().toPrettyString() + ")";
    	Caption caption = new Caption(captionText, Orientation.HORIZONTAL, false);
    	if (caption.width() > panel.width())
    	    captionText = genomeInterval.toPrettyString();
    	panel.add(caption, HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
    	
    	return panel;
    }
    
    
    private GenomeInterval[] extractGenomeIntervals(GenomeIntervalDto[] dtos, 
    		DataSet dataSet, Units units) {
    	GenomeInterval[] intervals = null;
    	if (dtos == null || dtos.length < 1)
    		intervals = this.extractGenome(dataSet, units);
    	else
    		intervals = this.extractSubGenome(dtos, dataSet, units);
    	return intervals;
    }
    
    
    private GenomeInterval[] extractGenome(DataSet dataSet, Units units) {
    	SortedSet chromosomes = dataSet.chromosomeSet();
    	GenomeIntervalDto[] dtos = new GenomeIntervalDto[chromosomes.size()];
    	int p = 0;
    	for (Iterator it = chromosomes.iterator(); it.hasNext();) {
    		Chromosome chrom = (Chromosome)it.next();
    		dtos[p++] = new GenomeIntervalDto(chrom.getNumber(), -1, -1);
    	}
    	return this.extractSubGenome(dtos, dataSet, units);
    }
    	
    
    private GenomeInterval[] extractSubGenome(GenomeIntervalDto[] dtos, 
    		DataSet dataSet, Units units) {
    	assert dtos != null && dtos.length > 0;
    	List intervalList = new ArrayList();
    	SortedSet chromosomes = dataSet.chromosomeSet();
    	for (int i = 0; i < dtos.length; i++) {
    		GenomeIntervalDto dto = dtos[i];
    		if (! dto.hasPositionInfo())
    			dataSet.setEndPoints(dto);
    		Chromosome chrom = null;
    		for (Iterator it = chromosomes.iterator(); it.hasNext() && chrom == null;) {
    			Chromosome tempChrom = (Chromosome)it.next();
    			if (tempChrom.synonymous((short)dto.getChromosome()))
    				chrom = tempChrom;
    		}
    		if (chrom == null) {
    			
    			// ------------------------->
    			// This property should only be null during unit testing.
    			// It should be set using dependency injection under normal operation.
    			if (this.persistentDomainObjectMgr == null)
    				chrom = new Chromosome(GenomeAssembly.DUMMY_GENOME_ASSEMBLY, (short)dto.getChromosome());
    			// <------------------------
    			else
    				chrom = this.persistentDomainObjectMgr.getDefaultPersistentChromosome((short)dto.getChromosome());
    		}
    		if (chrom != null) {
    			GenomeLocation start = new GenomeLocation(chrom, (long)dto.getStart());
    			GenomeLocation end = new GenomeLocation(chrom, (long)dto.getEnd());
    			intervalList.add(new GenomeInterval(start, end));
    		}
    	}
    	GenomeInterval[] intervals = new GenomeInterval[0];
    	intervals = (GenomeInterval[])intervalList.toArray(intervals);
    	return intervals;
    }

}
