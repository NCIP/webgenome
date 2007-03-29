/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $

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

package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.awt.Point;
import java.io.File;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.RasterDrawingCanvas;
import org.rti.webgenome.graphics.io.GraphicFileUtils;
import org.rti.webgenome.graphics.io.RasterGraphicFileType;
import org.rti.webgenome.graphics.primitive.Arc;
import org.rti.webgenome.graphics.primitive.Circle;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.primitive.Polygon;
import org.rti.webgenome.graphics.primitive.Polyline;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.units.Direction;
import org.rti.webgenome.util.FileUtils;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>RasterDrawingCanvas</code>.
 * @author dhall
 *
 */
public final class RasterDrawingCanvasTester extends TestCase {
    
    /** Name of directory holding test output files. */
    private static final String TEST_DIR_NAME =
    	"raster-drawing-canvas-tests";
    
    /** Directory where test output files will be written. */
    private static final File TEST_DIR =
    	FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
    
    /** Width in pixels of graphics generated during tests. */
    private static final int WIDTH = 500;
    
    /** Height in pixels of graphics generated during tests. */
    private static final int HEIGHT = 500;
    
    /** A drawing canvas using in all tests. */
    private RasterDrawingCanvas canvas = null;
    
    
    /**
     * Constructor.
     *
     */
    public RasterDrawingCanvasTester() {
        String testDirPath = UnitTestUtils.getUnitTestProperty("temp.dir");
        if (testDirPath == null) {
            throw new WebGenomeSystemException("Property 'temp.dir' missing from "
                    + "'unit_test.properties' file");
        }
        String dirPath = testDirPath + "/" + TEST_DIR_NAME;
        FileUtils.createDirectory(dirPath);
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
        GraphicFileUtils.write(TEST_DIR, "circle.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
    
    
    /**
     * Test drawing a curve pointing up.
     *
     */
    public void testArcUp() {
    	this.canvas.add(new Arc(200, 200, 100, 100, Direction.UP, Color.GREEN));
    	this.canvas.add(new Circle(200, 200, 5, Color.RED));
    	GraphicFileUtils.write(TEST_DIR, "arc-up.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
    
    
    /**
     * Test drawing a curve pointing down.
     *
     */
    public void testArcDown() {
    	this.canvas.add(new Arc(200, 200, 100, 100, Direction.DOWN,
    			Color.GREEN));
    	this.canvas.add(new Circle(200, 200, 5, Color.RED));
    	GraphicFileUtils.write(TEST_DIR, "arc-down.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
    
    
    /**
     * Test drawing a curve pointing left.
     *
     */
    public void testArcLeft() {
    	this.canvas.add(new Arc(200, 200, 100, 100, Direction.LEFT,
    			Color.GREEN));
    	this.canvas.add(new Circle(200, 200, 5, Color.RED));
    	GraphicFileUtils.write(TEST_DIR, "arc-left.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
    
    
    /**
     * Test drawing a curve pointing right.
     *
     */
    public void testArcRight() {
    	this.canvas.add(new Arc(200, 200, 100, 100, Direction.RIGHT,
    			Color.GREEN));
    	this.canvas.add(new Circle(200, 200, 5, Color.RED));
    	GraphicFileUtils.write(TEST_DIR, "arc-right.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
    
    
    /**
     * Test drawing a line.
     */
    public void testLine() {
        this.canvas.add(new Line(10, 10, 250, 250, 4, Color.RED));
        GraphicFileUtils.write(TEST_DIR, "line.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
    
    /**
     * Test drawing a rectangle.
     *
     */
    public void testRect() {
        this.canvas.add(new Rectangle(40, 40, 100, 100, Color.CYAN));
        GraphicFileUtils.write(TEST_DIR, "rect.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
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
        GraphicFileUtils.write(TEST_DIR, "polyline.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
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
        GraphicFileUtils.write(TEST_DIR, "polygon.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
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
        GraphicFileUtils.write(TEST_DIR, "add.png",
        		this.canvas.toBufferedImage(), RasterGraphicFileType.PNG);
    }
}
