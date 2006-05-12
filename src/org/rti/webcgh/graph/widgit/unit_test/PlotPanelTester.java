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
package org.rti.webcgh.graph.widgit.unit_test;

import java.awt.Color;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicLine;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.VerticalAlignment;
import org.rti.webcgh.graph.unit_test.BasePlottingTester;
import org.rti.webcgh.graph.unit_test.PlotTesterUtils;
import org.rti.webcgh.graph.widgit.Background;
import org.rti.webcgh.graph.widgit.PlotPanel;


/**
 * 
 */
public class PlotPanelTester extends BasePlottingTester {
    
    
    private PlotPanel panel = null;
    
    
    /**
     * 
     */
    public void setUp() {
        super.setUp();
        int width = 800;
        int height = 800;
        PlotTesterUtils.addFrame(this.drawingCanvas, width, height);
        this.drawingCanvas.add(new GraphicLine(width / 2, 0, width / 2, height, 2, Color.black));
        this.drawingCanvas.add(new GraphicLine(0, height / 2, width, height / 2, 2, Color.black));
        DrawingCanvas tile1 = this.drawingCanvas.newTile();
        this.drawingCanvas.add(tile1, width / 2, height / 2);
        Background bigBg = new Background(200, 200, Color.red);
        this.panel = new PlotPanel(tile1);
        panel.add(bigBg, HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
    }
    

    
    /**
     * 
     *
     */
    public void testLeftAbove() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.ABOVE);
        PlotTesterUtils.writeDocument(this.document, "panel-left-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftTopJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.TOP_JUSTIFIED);
        PlotTesterUtils.writeDocument(this.document, "panel-left-top.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
        PlotTesterUtils.writeDocument(this.document, "panel-left-middle.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftBottomJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.BOTTOM_JUSTIFIED);
        PlotTesterUtils.writeDocument(this.document, "panel-left-bottom.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeftBelow() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_OF, VerticalAlignment.BELOW);
        PlotTesterUtils.writeDocument(this.document, "panel-left-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAboveLeftJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.ABOVE);
        PlotTesterUtils.writeDocument(this.document, "panel-left-justified-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAboveCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
        PlotTesterUtils.writeDocument(this.document, "panel-middle-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAboveRightJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
        PlotTesterUtils.writeDocument(this.document, "panel-right-justified-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testBelowLeftJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BELOW);
        PlotTesterUtils.writeDocument(this.document, "panel-left-justified-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testBelowCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
        PlotTesterUtils.writeDocument(this.document, "panel-middle-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testBelowRightJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.BELOW);
        PlotTesterUtils.writeDocument(this.document, "panel-right-justified-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightAbove() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.ABOVE);
        PlotTesterUtils.writeDocument(this.document, "panel-right-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightTopJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
        PlotTesterUtils.writeDocument(this.document, "panel-right-top.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightCenter() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
        PlotTesterUtils.writeDocument(this.document, "panel-right-center.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightBottomJustified() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.BOTTOM_JUSTIFIED);
        PlotTesterUtils.writeDocument(this.document, "panel-right-bottom.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRightBelow() {
        this.panel.add(new Background(30, 30, Color.blue), 
            HorizontalAlignment.RIGHT_OF, VerticalAlignment.BELOW);
        PlotTesterUtils.writeDocument(this.document, "panel-right-below.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testInside() {
        
        // Left
        this.panel.add(new Background(30, 30, Color.blue), 
              HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        this.panel.add(new Background(30, 30, Color.blue), 
                HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.CENTERED);
        this.panel.add(new Background(30, 30, Color.blue), 
                HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
        
        // Center
        this.panel.add(new Background(30, 30, Color.blue), 
                HorizontalAlignment.CENTERED, VerticalAlignment.TOP_JUSTIFIED);
	      this.panel.add(new Background(30, 30, Color.blue), 
	              HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
	      this.panel.add(new Background(30, 30, Color.blue), 
	              HorizontalAlignment.CENTERED, VerticalAlignment.BOTTOM_JUSTIFIED);
          
        // Right
	      this.panel.add(new Background(30, 30, Color.blue), 
	              HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
	        this.panel.add(new Background(30, 30, Color.blue), 
	                HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.CENTERED);
	        this.panel.add(new Background(30, 30, Color.blue), 
	                HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.BOTTOM_JUSTIFIED);
        
        PlotTesterUtils.writeDocument(this.document, "panel-inside.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testAddPanel() {
	    PlotPanel panel2 = new PlotPanel(this.drawingCanvas.newTile());
	    panel2.add(new Background(100, 100, Color.blue), 
	        HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
	    this.panel.add(panel2, HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
        PlotTesterUtils.writeDocument(this.document, "panel-add-panel.svg");
    }
}
