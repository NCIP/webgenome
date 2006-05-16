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
package org.rti.webcgh.graph.widget.unit_test;

import java.awt.Point;
import java.net.URL;

import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.graph.unit_test.BasePlottingTester;
import org.rti.webcgh.graph.unit_test.PlotTesterUtils;
import org.rti.webcgh.graph.widget.AxisTicMark;



/**
 * 
 */
public class AxisTicMarkTester extends BasePlottingTester {
    
    
    /**
     * 
     *
     */
    public void testHorizLeft() {
       AxisTicMark tic = new AxisTicMark(new Point(100, 100), "Tic", Orientation.HORIZONTAL, 
           Location.LEFT_OF);
       tic.paint(this.drawingCanvas, true);
       PlotTesterUtils.writeDocument(this.document, "tic-horiz-left.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testHorizRight() {
       AxisTicMark tic = new AxisTicMark(new Point(100, 100), "Tic", Orientation.HORIZONTAL, 
           Location.RIGHT_OF);
       tic.paint(this.drawingCanvas, true);
       PlotTesterUtils.writeDocument(this.document, "tic-horiz-right.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertAbove() {
       AxisTicMark tic = new AxisTicMark(new Point(100, 100), "Tic", Orientation.VERTICAL, 
           Location.ABOVE);
       tic.paint(this.drawingCanvas, true);
       PlotTesterUtils.writeDocument(this.document, "tic-vert-above.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testVertBelow() {
       AxisTicMark tic = new AxisTicMark(new Point(100, 100), "Tic", Orientation.VERTICAL, 
           Location.BELOW);
       tic.paint(this.drawingCanvas, true);
       PlotTesterUtils.writeDocument(this.document, "tic-vert-below.svg");
    }
    
    
    /**
     * 
     * @throws Exception
     */
    public void testURL() throws Exception {
       URL url = new URL("http://www.google.com");
       AxisTicMark tic = new AxisTicMark(new Point(100, 100), "Tic", url, Orientation.HORIZONTAL, 
           Location.LEFT_OF);
       tic.paint(this.drawingCanvas, true);
       PlotTesterUtils.writeDocument(this.document, "tic-url.svg");
    }
    
    
    /**
     * 
     *
     */
    public void testNoLabel() {
       AxisTicMark tic = new AxisTicMark(new Point(100, 100), "Tic", Orientation.HORIZONTAL, 
           Location.LEFT_OF);
       tic.paint(this.drawingCanvas, false);
       PlotTesterUtils.writeDocument(this.document, "tic-no-label.svg");
    }

}
