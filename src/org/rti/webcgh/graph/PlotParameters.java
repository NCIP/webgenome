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
import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.deprecated.ColorChooser;
import org.rti.webcgh.graphics.PlotType;
import org.rti.webcgh.units.BpUnits;
import org.rti.webcgh.units.ChromosomeIdeogramSize;

/**
 * Parameters for a plot
 */
public class PlotParameters {
    
    
    private final Map colorIndex = new HashMap();
    private final ColorChooser colorChooser = new ColorChooser();
    private PlotType plotType;
    private int width = 400;
    private int height = 400;
    private double minY = Double.NaN;
    private double maxY = Double.NaN;
    private BpUnits xUnits = BpUnits.KB;
    private GenomeIntervalDto[] genomeIntervalDtos = null;
    private int plotsPerRow = 5;
    private boolean showIdeogram = false;
    private ChromosomeIdeogramSize chromosomeIdeogramSize = null;
    private double lowerMaskValue = Double.NaN;
    private double upperMaskValue = Double.NaN;
    
    
    
    /**
     * @return Returns the lowerMaskValue.
     */
    public double getLowerMaskValue() {
        return lowerMaskValue;
    }
    
    
    /**
     * @param lowerMaskValue The lowerMaskValue to set.
     */
    public void setLowerMaskValue(double lowerMaskValue) {
        this.lowerMaskValue = lowerMaskValue;
    }
    
    
    /**
     * @return Returns the upperMaskValue.
     */
    public double getUpperMaskValue() {
        return upperMaskValue;
    }
    
    
    /**
     * @param upperMaskValue The upperMaskValue to set.
     */
    public void setUpperMaskValue(double upperMaskValue) {
        this.upperMaskValue = upperMaskValue;
    }
    
    
    /**
     * @return Returns the chromosomeIdeogramSize.
     */
    public ChromosomeIdeogramSize getChromosomeIdeogramSize() {
        return chromosomeIdeogramSize;
    }
    
    
    /**
     * @param chromosomeIdeogramSize The chromosomeIdeogramSize to set.
     */
    public void setChromosomeIdeogramSize(
            ChromosomeIdeogramSize chromosomeIdeogramSize) {
        this.chromosomeIdeogramSize = chromosomeIdeogramSize;
    }
    
    
    /**
     * @return Returns the showIdeogram.
     */
    public boolean isShowIdeogram() {
        return showIdeogram;
    }
    
    
    /**
     * @param showIdeogram The showIdeogram to set.
     */
    public void setShowIdeogram(boolean showIdeogram) {
        this.showIdeogram = showIdeogram;
    }
    
    
    /**
     * @return Returns the plotsPerRow.
     */
    public int getPlotsPerRow() {
        return plotsPerRow;
    }
    
    
    /**
     * @param plotsPerRow The plotsPerRow to set.
     */
    public void setPlotsPerRow(int plotsPerRow) {
        this.plotsPerRow = plotsPerRow;
    }
    
    
    /**
     * @param plotType The plotType to set.
     */
    public void setPlotType(PlotType plotType) {
        this.plotType = plotType;
    }
    
    
    /**
     * @return Returns the genomeIntervalDtos.
     */
    public GenomeIntervalDto[] getGenomeIntervalDtos() {
        return genomeIntervalDtos;
    }
    
    
    /**
     * @param genomeIntervalDtos The genomeIntervalDtos to set.
     */
    public void setGenomeIntervalDtos(GenomeIntervalDto[] genomeIntervalDtos) {
        this.genomeIntervalDtos = genomeIntervalDtos;
    }
    
    
    /**
     * @return Returns the xUnits.
     */
    public BpUnits getXUnits() {
        return xUnits;
    }
    
    
    /**
     * @param units The xUnits to set.
     */
    public void setXUnits(BpUnits units) {
        xUnits = units;
    }
    
    
    /**
     * @return Returns the colorIndex.
     */
    public Map getColorIndex() {
        return colorIndex;
    }
    
    
    /**
     * @return Returns the plotType.
     */
    public PlotType getPlotType() {
        return plotType;
    }
    
    
    /**
     * @return Returns the height.
     */
    public int getHeight() {
        return height;
    }
    
    
    /**
     * @return Returns the width.
     */
    public int getWidth() {
        return width;
    }
    
    
    /**
     * @return Returns the maxY.
     */
    public double getMaxY() {
        return maxY;
    }
    
    
    /**
     * @param maxY The maxY to set.
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
    
    
    /**
     * @return Returns the minY.
     */
    public double getMinY() {
        return minY;
    }
    
    
    /**
     * @param minY The minY to set.
     */
    public void setMinY(double minY) {
        this.minY = minY;
    }
    
    
    /**
     * @param height The height to set.
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    
    /**
     * @param width The width to set.
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    
    // ====================================
    //     Constructors
    // ====================================
    
    
    /**
     * Consturctor
     */
    public PlotParameters() {
    }
    
    
    /**
     * Consturctor
     * @param plotType Plot type
     */
    public PlotParameters(PlotType plotType) {
        this.plotType = plotType;
    }
    
    
    // ===========================================
    //        Public methods
    // ===========================================
    
    
    /**
     * Get color
     * @param bioAssay A bioassay
     * @return A color
     */
    public Color color(BioAssay bioAssay) {
        String key = bioAssay.getName();
        Color color = (Color)this.colorIndex.get(bioAssay);
        if (color == null) {
            color = this.colorChooser.nextColor();
            this.colorIndex.put(bioAssay, color);
        }
        return color;
    }
}
