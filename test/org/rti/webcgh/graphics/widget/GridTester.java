/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:33 $

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

package org.rti.webcgh.graphics.widget;

import java.awt.Color;

import junit.framework.TestCase;

import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.Axis;
import org.rti.webcgh.graphics.widget.Background;
import org.rti.webcgh.graphics.widget.Grid;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.util.FileUtils;

/**
 * Test class for <code>Grid</code>.
 * @author dhall
 *
 */
public final class GridTester extends TestCase {
    
    // ===============================
    //     Constants
    // ===============================
    
    /**
     * Name of directory holding graphic files produced
     * during tests.  The absolute path will be a
     * concatenation of the 'test.dir' property in
     * the file 'unit_test.properties' and this
     * constant.
     */
    private static final String TEST_DIR_NAME = "grid-tester";
    
    /**
     * Draw horizontal grid.
     */
    public void testHorizontal() {
        RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		FileUtils.createUnitTestDirectory(TEST_DIR_NAME));
        Axis axis = new Axis(0, 10, 400, Orientation.VERTICAL,
                Location.LEFT_OF, panel.getDrawingCanvas());
        Grid grid = axis.newGrid(400, 400, Color.white,
                panel);
        Background background = new Background(400, 400, Color.yellow);
        panel.add(background, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        panel.add(grid, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BOTTOM_JUSTIFIED);
        panel.add(axis, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BOTTOM_JUSTIFIED);
        panel.toPngFile("horizontal.png");
    }
    
    
    /**
     * Draw vertical grid.
     */
    public void testVertical() {
        RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		FileUtils.createUnitTestDirectory(TEST_DIR_NAME));
        Axis axis = new Axis(0, 10, 400, Orientation.HORIZONTAL,
                Location.ABOVE, panel.getDrawingCanvas());
        Grid grid = axis.newGrid(400, 400, Color.white,
                panel);
        Background background = new Background(400, 400, Color.yellow);
        panel.add(background, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        panel.add(grid, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.TOP_JUSTIFIED);
        panel.add(axis, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.TOP_JUSTIFIED);
        panel.toPngFile("vertical.png");
    }
}
