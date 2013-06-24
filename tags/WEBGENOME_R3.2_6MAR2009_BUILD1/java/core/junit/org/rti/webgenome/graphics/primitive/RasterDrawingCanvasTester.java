/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-04-09 22:19:50 $


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
    	UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
    
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
