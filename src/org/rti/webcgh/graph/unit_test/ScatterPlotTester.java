/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/unit_test/ScatterPlotTester.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.graph.unit_test;

import java.awt.Color;

import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.drawing.SvgDrawingCanvas;
import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.graph.PlotBoundaries;
import org.rti.webcgh.graph.ScatterPlot;

/**
 * 
 */
public class ScatterPlotTester extends BasePlottingTester {
    
    

    ScatterPlot plot = null;
    
    
    /**
     * 
     */
    public void setUp() {
        super.setUp();
        int width = 300;
        int height = 300;
        this.document = PlotTesterUtils.newTestDocument();
        this.drawingCanvas = new SvgDrawingCanvas(document);
        DataPoint bottomLeftPoint = new DataPoint(0.0, -0.6);
        DataPoint topRightPoint = new DataPoint(300000000, 1.0);
        this.plot = new ScatterPlot(new PlotBoundaries(bottomLeftPoint, topRightPoint), 
                width, height);
        PlotTesterUtils.addFrame(this.drawingCanvas, width, height);
    }
    
    /**
     */
    public void testAll1() {
        this.bioAssayData.graph(plot, new GenomeLocation(chrom1, 0), new GenomeLocation(chrom1, 300000000), "plot", Color.black);
        this.plot.paint(this.drawingCanvas);
        PlotTesterUtils.writeDocument(this.document, "scatter-all-1.svg");
    }
    
    /**
     */
    public void testLeftFirstProbe1() {
        int width = 300;
        int height = 300;
        DataPoint bottomLeftPoint = new DataPoint(10000000, -0.6);
        DataPoint topRightPoint = new DataPoint(30000000, 1.0);
        this.plot = new ScatterPlot(new PlotBoundaries(bottomLeftPoint, topRightPoint), 
            width, height);
        this.bioAssayData.graph(plot, new GenomeLocation(chrom1, 10000000), new GenomeLocation(chrom1, 30000000), "plot", Color.black);
        this.plot.paint(this.drawingCanvas);
        PlotTesterUtils.writeDocument(this.document, "scatter-left-first-1.svg");
    }
    
    /**
     */
    public void testRightLastProbe1() {
        int width = 300;
        int height = 300;
        DataPoint bottomLeftPoint = new DataPoint(250000000, -0.6);
        DataPoint topRightPoint = new DataPoint(270000000, 1.0);
        this.plot = new ScatterPlot(new PlotBoundaries(bottomLeftPoint, topRightPoint), 
            width, height);
        this.bioAssayData.graph(plot, new GenomeLocation(chrom1, 250000000), new GenomeLocation(chrom1, 270000000), "plot", Color.black);
        this.plot.paint(this.drawingCanvas);
        PlotTesterUtils.writeDocument(this.document, "scatter-right-first-1.svg");
    }
    
    /**
     */
    public void testLeftFirstProbe2() {
        int width = 300;
        int height = 300;
        DataPoint bottomLeftPoint = new DataPoint(10000000, -0.6);
        DataPoint topRightPoint = new DataPoint(15000000, 1.0);
        this.plot = new ScatterPlot(new PlotBoundaries(bottomLeftPoint, topRightPoint), 
            width, height);
        this.bioAssayData.graph(plot, new GenomeLocation(chrom1, 10000000), new GenomeLocation(chrom1, 15000000), "plot", Color.black);
        this.plot.paint(this.drawingCanvas);
        PlotTesterUtils.writeDocument(this.document, "scatter-left-first-2.svg");
    }
    
    /**
     */
    public void testRightLastProbe2() {
        this.bioAssayData.graph(plot, new GenomeLocation(chrom1, 200000000), new GenomeLocation(chrom1, 210000000), "plot", Color.black);
        this.plot.paint(this.drawingCanvas);
        PlotTesterUtils.writeDocument(this.document, "scatter-right-last-2.svg");
    }
    
    /**
     */
    public void testRightLastProbe3() {
        this.bioAssayData.graph(plot, new GenomeLocation(chrom1, 170000000), new GenomeLocation(chrom1, 180000000), "plot", Color.black);
        this.plot.paint(this.drawingCanvas);
        PlotTesterUtils.writeDocument(this.document, "scatter-right-last-3.svg");
    }

}
