/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/PlotGenerator.java,v $
$Revision: 1.5 $
$Date: 2006-03-29 22:26:30 $

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.ChromosomalAlteration;
import org.rti.webcgh.array.ChromosomalAlterationIterator;
import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.ClassPathPropertiesFileRgbHexidecimalColorMapper;
import org.rti.webcgh.array.CytologicalMap;
import org.rti.webcgh.array.DataSet;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.ExperimentIterator;
import org.rti.webcgh.array.GenomeInterval;
import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.drawing.Direction;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicEvent;
import org.rti.webcgh.drawing.GraphicRect;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.drawing.VerticalAlignment;

/**
 * Generates plots
 */
public class PlotGenerator {
	
	
	// ======================================
	//       Constants
	// ======================================
	
	private static final double DEFAULT_MIN_Y = -0.5;
	private static final double DEFAULT_MAX_Y = 0.5;
	private static final Color CHROMOSOMAL_ALTERATION_COLOR = Color.black;
    
    // ========================================
    //        Attributes
    // ========================================
    
    private int padding = 10;
    private Color backgroundColor = new Color(235, 235, 235);
    private Color matteColor = new Color(255, 255, 205);
    private Color gridColor = Color.white;
    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    private int ideogramFrameThickness = 2;
    private Color ideogramFrameColor = Color.black;
    private int numColorCodeBins = 16;
    
    
    
    /**
     * @return Returns the numColorCodeBins.
     */
    public int getNumColorCodeBins() {
        return numColorCodeBins;
    }
    
    
    /**
     * @param numColorCodeBins The numColorCodeBins to set.
     */
    public void setNumColorCodeBins(int numColorCodeBins) {
        this.numColorCodeBins = numColorCodeBins;
    }
    
    
    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
    /**
     * @param padding The padding to set.
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }
    
    
	/**
	 * @param backgroundColor The backgroundColor to set.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	
	/**
	 * @param gridColor The gridColor to set.
	 */
	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}
	
	
    /**
     * @param matteColor The matteColor to set.
     */
    public void setMatteColor(Color matteColor) {
        this.matteColor = matteColor;
    }
    
    
    // ========================================
    //      Public methods
    // ========================================
    
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
    	DrawingCanvas tile = canvas.newTile();
        PlotPanel plotPanel = new PlotPanel(tile);
        GenomeInterval[] genomeIntervals = 
        	this.extractGenomeIntervals(plotParameters.getGenomeIntervalDtos(), 
        			dataSet, plotParameters.getXUnits());
        if (plotParameters.getPlotType() == PlotType.SCATTER_PLOT)
            this.createScatterPlot(dataSet, genomeIntervals, quantitationType, 
            		plotParameters, plotPanel, tile);
        else if (plotParameters.getPlotType() == PlotType.IDEOGRAM_PLOT) {
            this.createIdeogramPlot(dataSet, genomeIntervals, plotParameters, plotPanel, tile);
            plotPanel.addExtraPadding(4, Location.RIGHT_OF);
        }
        int width = plotPanel.width() + this.padding * 2;
        int height = plotPanel.height() + this.padding * 2;
        GraphicRect background = new GraphicRect(0, 0, width, height, this.matteColor);
        background.addGraphicEventResponse(GraphicEvent.mouseMoveEvent, "hideToolTip()");
        background.addGraphicEventResponse(GraphicEvent.mouseClickEvent, "noHighlight()");
        canvas.add(background);
        canvas.add(tile, -plotPanel.topLeftPoint().x + this.padding, -plotPanel.topLeftPoint().y + this.padding);
        canvas.setWidth(width);
        canvas.setHeight(height);
    }
    
    
    // ============================================
    //       Private methods
    // ============================================
    
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
    		if (chrom == null)
    		    chrom = this.persistentDomainObjectMgr.getDefaultPersistentChromosome((short)dto.getChromosome());
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
    
    private void createScatterPlot(DataSet dataSet, 
    		GenomeInterval[] genomeIntervals, QuantitationType quantitationType,
            PlotParameters plotParameters, PlotPanel panel, DrawingCanvas canvas) {
        
        // Determine plot boundaries
        double minY = this.minY(dataSet, plotParameters);
        double maxY = this.maxY(dataSet, plotParameters);
        
        // Create Y-axis
        Axis yAxis = new Axis(minY, maxY, plotParameters.getHeight(), 
        		Orientation.VERTICAL, Location.LEFT_OF);
        
        // Create individual scatter plots
        int cols = (genomeIntervals.length < plotParameters.getPlotsPerRow()) ?
                genomeIntervals.length : plotParameters.getPlotsPerRow();
        int totalPadding = (cols - 1) * this.padding;
        double widthScale = 
        	((double)plotParameters.getWidth() - totalPadding) / (double)this.totalBP(genomeIntervals, cols);
        PlotPanel row = panel.newChildPlotPanel();
        for (int i = 0; i < genomeIntervals.length; i++) {
        	int width = (int)((double)genomeIntervals[i].span() * widthScale);
        	Grid grid = yAxis.newGrid(width, plotParameters.getHeight(), this.gridColor, canvas);
            PlotPanel scatterPlotPanel = this.createScatterPlot(dataSet, genomeIntervals[i], 
                    width, plotParameters, panel, minY, maxY, grid);
            if (i % cols == 0 && i > 0) {
                PlotPanel axisPanel = panel.newChildPlotPanel();
                this.addYAxis(axisPanel, yAxis, quantitationType);
                row.add(axisPanel, HorizontalAlignment.LEFT_JUSTIFIED, 
                		VerticalAlignment.BOTTOM_JUSTIFIED);
                panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
                row = panel.newChildPlotPanel();
            }
            row.add(scatterPlotPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.BOTTOM_JUSTIFIED);
        }
        if (genomeIntervals.length % cols >= 0) {
            PlotPanel axisPanel = panel.newChildPlotPanel();
            this.addYAxis(axisPanel, yAxis, quantitationType);
            row.add(axisPanel, HorizontalAlignment.LEFT_JUSTIFIED, 
            		VerticalAlignment.BOTTOM_JUSTIFIED);
            panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
        }
        
        // Add legend
        Legend legend = new Legend(dataSet, plotParameters);
        panel.add(legend, HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
    }
    
    
    private void createIdeogramPlot(DataSet dataSet, 
    		GenomeInterval[] genomeIntervals,
            PlotParameters plotParameters, PlotPanel panel, DrawingCanvas canvas) {
    	genomeIntervals = this.compress(genomeIntervals);
        double minY = this.minY(dataSet, plotParameters);
        double maxY = this.maxY(dataSet, plotParameters);
        ChromosomeIdeogramSize size = plotParameters.getChromosomeIdeogramSize();
        int cols = (genomeIntervals.length < plotParameters.getPlotsPerRow()) ?
                genomeIntervals.length : plotParameters.getPlotsPerRow();
        PlotPanel row = panel.newChildPlotPanel();
        for (int i = 0; i < genomeIntervals.length; i++) {
            GenomeInterval interval = genomeIntervals[i];
            if (i % cols == 0 && i > 0) {
                PlotPanel axisPanel = panel.newChildPlotPanel();
                panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
                panel.add(new HeatMapColorFactory(minY, maxY, this.numColorCodeBins),
                        HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
                row = panel.newChildPlotPanel();
            }
            if (i < cols - 1)
                panel.addExtraPadding(4, Location.RIGHT_OF);
            this.addIdeogram(row, interval, dataSet, size, Orientation.VERTICAL, plotParameters);
        }
        if (genomeIntervals.length % cols >= 0) {
            panel.add(row, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
            panel.add(new HeatMapColorFactory(minY, maxY, this.numColorCodeBins),
                    HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
        }
    }
    
    
    private void addYAxis(PlotPanel panel, Axis yAxis, 
            QuantitationType quantitationType) {
        panel.add(yAxis, HorizontalAlignment.LEFT_JUSTIFIED, 
        		VerticalAlignment.TOP_JUSTIFIED);
        Caption caption = new Caption(quantitationType.getName(), 
        		Orientation.HORIZONTAL, true);
        panel.add(caption, HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
    }
    
    
    private double minY(DataSet dataSet, PlotParameters params) {
    	double min = DEFAULT_MIN_Y;
    	if (! Double.isNaN(params.getMinY()))
    		min = params.getMinY();
    	else {
    		min = dataSet.minY();
    		if (Double.isNaN(min));
    			min = DEFAULT_MIN_Y;
    	}
    	return min;
    }
    
    
    private double maxY(DataSet dataSet, PlotParameters params) {
    	double max = DEFAULT_MAX_Y;
    	if (! Double.isNaN(params.getMaxY()))
    		max = params.getMaxY();
    	else {
    		max = dataSet.maxY();
    		if (Double.isNaN(max))
    			max = DEFAULT_MAX_Y;
    	}
    	return max;
    }
    
    
    private int totalBP(GenomeInterval[] intervals, int cols) {
    	int total = 0;
    	for (int i = 0; i < intervals.length && i < cols; i++)
    		total += intervals[i].span();
    	return total;
    }
    
    
    private PlotPanel createScatterPlot(DataSet dataSet, GenomeInterval genomeInterval,
            int width, PlotParameters plotParameters, PlotPanel parentPanel,
			double minY, double maxY, Grid grid) {
    	PlotPanel panel = parentPanel.newChildPlotPanel();
    	PlotBoundaries plotBoundaries = genomeInterval.newPlotBoundaries(minY, maxY);
    	
    	// Add background
    	Background background = new Background(width, plotParameters.getHeight(), 
    			this.backgroundColor);
    	panel.add(background, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	
    	// Add grid
    	panel.add(grid, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	
    	// Add plot
    	ScatterPlot scatterPlot = new ScatterPlot(plotBoundaries, width, 
    			plotParameters.getHeight());
    	for (ExperimentIterator it = dataSet.experimentIterator(); it.hasNext();)
    		it.next().graph(scatterPlot, genomeInterval.start, 
    				genomeInterval.end, plotParameters);
    	panel.add(scatterPlot, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	
    	// Add x-axis
    	Axis xAxis = plotBoundaries.newXAxis(width, VerticalAlignment.BOTTOM_JUSTIFIED);
    	xAxis.setNumberFormatter(plotParameters.getXUnits().numberFormatter());
    	panel.add(xAxis, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
    	
    	// Add chromosome ideogram
    	if (plotParameters.isShowIdeogram())
    	    this.addIdeogram(panel, genomeInterval, width, Orientation.HORIZONTAL);
    	
    	// Add x-axis caption
    	String captionText = 
    	    "CHR " + genomeInterval.toPrettyString() + " (" + plotParameters.getXUnits().toPrettyString() + ")";
    	Caption caption = new Caption(captionText, Orientation.HORIZONTAL, false);
    	if (caption.width() > panel.width())
    	    captionText = genomeInterval.toPrettyString();
    	panel.add(caption, HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
    	
    	return panel;
    }
    
    
    private void addIdeogram(PlotPanel panel, GenomeInterval interval,
            int length, Orientation orientation) {
        CytologicalMap cmap = this.persistentDomainObjectMgr.getPersistentCytologicalMap(interval.chromosome());
        if (cmap == null)
            throw new WebcghSystemException("Cannot find cytological map in embedded database");
        long mapEnd = (cmap.length() < interval.endBp())? cmap.length() : interval.endBp();
        GenomeFeatureMap map = new GenomeFeatureMap(interval.startBp(), mapEnd, length, orientation);
        Warper warper = new CentromereWarper(map.getFeatureHeight(), map.bpToPixel(cmap.getCentromereStart()), 
                map.bpToPixel(cmap.getCentromereEnd()));
        map.setWarper(warper);
        map.addFrame(Location.ABOVE, this.ideogramFrameThickness, this.ideogramFrameColor);
        map.addFrame(Location.BELOW, this.ideogramFrameThickness, this.ideogramFrameColor);
        cmap.graph(map, new ClassPathPropertiesFileRgbHexidecimalColorMapper("conf/color-mappings.properties"));
        panel.add(map, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
    }
    
    
    
    private void addIdeogram(PlotPanel panel, GenomeInterval interval, DataSet dataSet,
            ChromosomeIdeogramSize size, Orientation orientation, PlotParameters plotParameters) {
        Chromosome chromosome = interval.chromosome();
        PlotPanel idPanel = panel.newChildPlotPanel();
        CytologicalMap cmap = this.persistentDomainObjectMgr.getPersistentCytologicalMap(chromosome);
        if (cmap == null)
            throw new WebcghSystemException("Cannot find cytological map in embedded database");
        int pixels = size.pixels(cmap.length());
        GenomeFeatureMap map = new GenomeFeatureMap((long)0, cmap.length(), pixels, orientation);
        Warper warper = new CentromereWarper(map.getFeatureHeight(), map.bpToPixel(cmap.getCentromereStart()), 
                map.bpToPixel(cmap.getCentromereEnd()));
        map.setWarper(warper);
        map.addFrame(Location.ABOVE, this.ideogramFrameThickness, this.ideogramFrameColor);
        map.addFrame(Location.BELOW, this.ideogramFrameThickness, this.ideogramFrameColor);
        cmap.graph(map, new ClassPathPropertiesFileRgbHexidecimalColorMapper("conf/color-mappings.properties"));
        idPanel.add(map, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        ChromosomeEndCap topCap = new ChromosomeEndCap(map.getFeatureHeight(), this.ideogramFrameColor, Direction.UP);
        ChromosomeEndCap botCap = new ChromosomeEndCap(map.getFeatureHeight(), this.ideogramFrameColor, Direction.DOWN);
        idPanel.add(topCap, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        idPanel.add(botCap, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
        Caption caption = new Caption("CHR " + chromosome.getNumber(), Orientation.HORIZONTAL, false);
        idPanel.add(caption, HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
        
        //int plotLength = (int)((double)interval.endBp() / (double)cmap.length() * (double)pixels);
        double minY = this.minY(dataSet, plotParameters);
        double maxY = this.maxY(dataSet, plotParameters);
        for (ExperimentIterator it = dataSet.experimentIterator(); it.hasNext();) {
            Experiment exp = it.next();
            
            // Bioassays
            for (BioAssayIterator bai = exp.bioAssayIterator(); bai.hasNext();) {
                BioAssay bioAssay = bai.next();
                PlotPanel baPanel = idPanel.newChildPlotPanel();
	            ColorCodePlot plot = new ColorCodePlot(minY, maxY, this.numColorCodeBins, pixels, 
	            		Orientation.VERTICAL, cmap.length());
	            plot.setMinMaskValue(plotParameters.getLowerMaskValue());
	            plot.setMaxMaskValue(plotParameters.getUpperMaskValue());
	            bioAssay.graph(plot, interval.start, interval.end, null);
	            baPanel.add(plot, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
	            Caption bioAssayName = new Caption(bioAssay.getName(), Orientation.VERTICAL, false);
	            baPanel.add(bioAssayName, HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
	            idPanel.add(baPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
            }
            
            // MCAR
            ChromosomalAlterationIterator cai = exp.amplificationIterator();
            if (cai.hasNext()) {
            	PlotPanel ampPanel = idPanel.newChildPlotPanel();
            	GenomeFeatureMap gfmap = new GenomeFeatureMap(0, cmap.length(), pixels, 
            			orientation);
            	while (cai.hasNext()) {
            		ChromosomalAlteration alt = cai.next();
            		if (alt.onChromosome(chromosome)) {
            			String mouseover = alt.startBp()/1000000 + "MB-" + alt.endBp()/1000000 + "MB";
	            		gfmap.plotFeature(alt.startBp(), alt.endBp(), mouseover, null, false, 
	            				PlotGenerator.CHROMOSOMAL_ALTERATION_COLOR);
            		}
            	}
            	ampPanel.add(gfmap, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
            	Caption colName = new Caption("MCAR", Orientation.VERTICAL, false);
            	ampPanel.add(colName, HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
            	idPanel.add(ampPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
            }
            
            // MCDR
            cai = exp.deletionIterator();
            if (cai.hasNext()) {
            	PlotPanel delPanel = idPanel.newChildPlotPanel();
            	GenomeFeatureMap gfmap = new GenomeFeatureMap(0, cmap.length(), pixels, 
            			Orientation.VERTICAL);
            	while (cai.hasNext()) {
            		ChromosomalAlteration alt = cai.next();
            		if (alt.onChromosome(chromosome)) {
            			String mouseover = alt.startBp()/1000000 + "MB-" + alt.endBp()/1000000 + "MB";
	            		gfmap.plotFeature(alt.startBp(), alt.endBp(), mouseover, null, false, 
	            				PlotGenerator.CHROMOSOMAL_ALTERATION_COLOR);
            		}
            	}
            	delPanel.add(gfmap, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
            	Caption colName = new Caption("MCDR", Orientation.VERTICAL, false);
            	delPanel.add(colName, HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
            	idPanel.add(delPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
            }
        }
        panel.add(idPanel, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
    }
    
    
    private GenomeInterval[] compress(GenomeInterval[] intervals) {
    	List iList = new ArrayList();
    	Map chromIndex = new HashMap();
    	for (int i = 0; i < intervals.length; i++) {
    		GenomeInterval ival = intervals[i];
    		Chromosome chrom = ival.chromosome();
    		Object key = chrom.getCacheKey();
    		GenomeInterval ivalInList = (GenomeInterval)chromIndex.get(key);
    		if (ivalInList == null) {
    			iList.add(ival);
    			chromIndex.put(key, ival);
    		} else
    			ivalInList.union(ival);
    	}
    	GenomeInterval[] newIntervals = new GenomeInterval[0];
    	newIntervals = (GenomeInterval[])iList.toArray(newIntervals);
    	return newIntervals;
    }

}
