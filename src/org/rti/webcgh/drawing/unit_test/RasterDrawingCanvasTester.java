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

package org.rti.webcgh.drawing.unit_test;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.drawing.Circle;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Polygon;
import org.rti.webcgh.drawing.Polyline;
import org.rti.webcgh.drawing.RasterDrawingCanvas;
import org.rti.webcgh.drawing.Rectangle;
import org.rti.webcgh.util.FileUtils;
import org.rti.webcgh.util.SystemUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>RasterDrawingCanvas</code>.
 * @author dhall
 *
 */
public final class RasterDrawingCanvasTester extends TestCase {
    
    /** Name of directory holding test output files. */
    private static final String TEST_DIR_NAME = "raster-drawing-canvas-tests";
    
    /** Width in pixels of graphics generated during tests. */
    private static final int WIDTH = 500;
    
    /** Height in pixels of graphics generated during tests. */
    private static final int HEIGHT = 500;
    
    /** Directory holding test output files. */
    private File testDir = null;
    
    /** A drawing canvas using in all tests. */
    private RasterDrawingCanvas canvas = null;
    
    
    /**
     * Constructor.
     *
     */
    public RasterDrawingCanvasTester() {
        Properties props = SystemUtils.loadProperties(
                "conf/unit_test.properties");
        if (props == null) {
            throw new WebcghSystemException(
                    "Could not load unit test properties");
        }
        String testDirPath = props.getProperty("temp.dir");
        if (testDirPath == null) {
            throw new WebcghSystemException("Property 'temp.dir' missing from "
                    + "'unit_test.properties' file");
        }
        String dirPath = testDirPath + "/" + TEST_DIR_NAME;
        FileUtils.createDirectory(dirPath);
        this.testDir = new File(dirPath);
    }
    
    /**
     * Setup method.
     */
    public void setUp() {
        this.canvas = new RasterDrawingCanvas();
        this.canvas.setWidth(WIDTH);
        this.canvas.setHeight(HEIGHT);
    }
    
    /**
     * Test drawing a circle.
     */
    public void testCircle() {
        this.canvas.add(new Circle(100, 100, 20, Color.GREEN));
        this.outputCanvas("circle.png");
    }
    
    
    /**
     * Test drawing a line.
     */
    public void testLine() {
        this.canvas.add(new Line(10, 10, 250, 250, 4, Color.RED));
        this.outputCanvas("line.png");
    }
    
    /**
     * Test drawing a rectangle.
     *
     */
    public void testRect() {
        this.canvas.add(new Rectangle(40, 40, 100, 100, Color.CYAN));
        this.outputCanvas("rect.png");
    }
    
    
    /**
     * Test drawing a polyline.
     *
     */
    public void testPolyline() {
        Polyline p = new Polyline(3, Color.GREEN);
        p.add(10, 200);
        p.add(100, 5);
        p.add(300, 250);
        this.canvas.add(p);
        this.outputCanvas("polyline.png");
    }
    
    
    /**
     * Test drawing a polygon.
     *
     */
    public void testPolygon() {
        Point[] points = new Point[]{new Point(50, 50), new Point(100, 100),
                                   new Point(100, 200), new Point(20, 150)};
        Polygon p = new Polygon(points, Color.RED);
        this.canvas.add(p);
        this.outputCanvas("polygon.png");
    }
    
    
    /**
     * Tests adding a child to a parent canvas.
     *
     */
    public void testAddCanvas() {
        DrawingCanvas canvas2 = this.canvas.newTile();
        DrawingCanvas canvas3 = canvas2.newTile();
        Rectangle r1 = new Rectangle(100, 100, 100, 100, Color.BLUE);
        Rectangle r2 = new Rectangle(100, 100, 100, 100, Color.RED);
        Rectangle r3 = new Rectangle(100, 100, 100, 100, Color.GREEN);
        canvas3.add(r3);
        canvas2.add(canvas3);
        canvas2.add(r2);
        this.canvas.add(r1);
        this.canvas.add(canvas2, 100, 100);
        this.outputCanvas("add.png");
    }
    
    
    /**
     * Write canvas contents to given file name
     * in test directory in PNG format.
     * @param fileName Name of file (not full path)
     * image to file
     */
    private void outputCanvas(final String fileName)  {
        File file = new File(this.testDir + "/" + fileName);
        BufferedImage img = this.canvas.toBufferedImage();
        try {
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            throw new WebcghSystemException("Error writing image to file", e);
        }
    }
}
