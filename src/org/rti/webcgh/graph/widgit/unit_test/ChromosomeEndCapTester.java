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

import org.rti.webcgh.drawing.Direction;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicLine;
import org.rti.webcgh.graph.unit_test.BasePlottingTester;
import org.rti.webcgh.graph.unit_test.PlotTesterUtils;
import org.rti.webcgh.graph.widgit.ChromosomeEndCap;

/**
 * 
 */
public class ChromosomeEndCapTester extends BasePlottingTester {
    
    
    private DrawingCanvas tile = null;
    
    
    /**
     * 
     */
    public void setUp() {
        super.setUp();
        int x = 200;
        int y = 200;
        tile = this.drawingCanvas.newTile();
        this.drawingCanvas.add(tile, x, y);
        int length = 100;
        this.drawingCanvas.add(new GraphicLine(x - length / 2, y, x + length / 2, 
                y, 2, Color.black));
        this.drawingCanvas.add(new GraphicLine(x, y - length / 2, x, 
                y + length / 2, 2, Color.black));
    }
    
    
    /**
     * 
     *
     */
    public void testUp() {
        ChromosomeEndCap cap = new ChromosomeEndCap(50, Color.red, Direction.UP);
        cap.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "cap-up.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testDown() {
        ChromosomeEndCap cap = new ChromosomeEndCap(50, Color.red, Direction.DOWN);
        cap.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "cap-down.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testLeft() {
        ChromosomeEndCap cap = new ChromosomeEndCap(30, Color.red, Direction.LEFT);
        cap.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "cap-left.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testRight() {
        ChromosomeEndCap cap = new ChromosomeEndCap(30, Color.red, Direction.RIGHT);
        cap.paint(this.tile);
        PlotTesterUtils.writeDocument(this.document, "cap-right.svg");
    }

}
