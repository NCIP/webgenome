/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/unit_test/CaptionTester.java,v $
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

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicLine;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.graph.Caption;

/**
 * Tester for Caption
 */
public class CaptionTester extends BasePlottingTester {
    
    
    private String captionText = "Log2 Ratio";
    private DrawingCanvas tile = null;
    
    
    /**
     * 
     */
    public void setUp() {
        super.setUp();
        this.tile = this.drawingCanvas.newTile();
        int x = 0;
        int y = 0;
        int lineLength = 100;
        this.drawingCanvas.add(this.tile, 300, 300);
        tile.add(new GraphicLine(x - lineLength / 2, y, x + lineLength / 2, y, 1, Color.black));
        tile.add(new GraphicLine(x, y - lineLength / 2, x, y + lineLength / 2, 1, Color.black));
    }
    
    
    /**
     * 
     *
     */
    public void testHorizLeftOneLine() {
        Caption caption = new Caption(this.captionText, Orientation.HORIZONTAL, false);
        caption.setTextAlignment(HorizontalAlignment.LEFT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-horiz-left-one-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testHorizCenterOneLine() {
        Caption caption = new Caption(this.captionText, Orientation.HORIZONTAL, false);
        caption.setTextAlignment(HorizontalAlignment.CENTERED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-horiz-center-one-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testHorizRightOneLine() {
        Caption caption = new Caption(this.captionText, Orientation.HORIZONTAL, false);
        caption.setTextAlignment(HorizontalAlignment.RIGHT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-horiz-right-one-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertLeftOneLine() {
        Caption caption = new Caption(this.captionText, Orientation.VERTICAL, false);
        caption.setTextAlignment(HorizontalAlignment.LEFT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-vert-left-one-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertCenterOneLine() {
        Caption caption = new Caption(this.captionText, Orientation.VERTICAL, false);
        caption.setTextAlignment(HorizontalAlignment.CENTERED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-vert-center-one-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertRightOneLine() {
        Caption caption = new Caption(this.captionText, Orientation.VERTICAL, false);
        caption.setTextAlignment(HorizontalAlignment.RIGHT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-vert-right-one-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testHorizLeftMultiLine() {
        Caption caption = new Caption(this.captionText, Orientation.HORIZONTAL, true);
        caption.setTextAlignment(HorizontalAlignment.LEFT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-horiz-left-multi-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testHorizCenterMultiLine() {
        Caption caption = new Caption(this.captionText, Orientation.HORIZONTAL, true);
        caption.setTextAlignment(HorizontalAlignment.CENTERED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-horiz-center-multi-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testHorizRightMultiLine() {
        Caption caption = new Caption(this.captionText, Orientation.HORIZONTAL, true);
        caption.setTextAlignment(HorizontalAlignment.RIGHT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-horiz-right-multi-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertLeftMultiLine() {
        Caption caption = new Caption(this.captionText, Orientation.VERTICAL, true);
        caption.setTextAlignment(HorizontalAlignment.LEFT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-vert-left-multi-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertCenterMultiLine() {
        Caption caption = new Caption(this.captionText, Orientation.VERTICAL, true);
        caption.setTextAlignment(HorizontalAlignment.CENTERED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-vert-center-multi-line.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertRightMultiLine() {
        Caption caption = new Caption(this.captionText, Orientation.VERTICAL, true);
        caption.setTextAlignment(HorizontalAlignment.RIGHT_JUSTIFIED);
        caption.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "caption-vert-right-multi-line.svg");
    }

}
