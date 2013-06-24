/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.io.File;

import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.RasterDrawingCanvas;
import org.rti.webgenome.graphics.io.GraphicFileUtils;
import org.rti.webgenome.graphics.io.RasterGraphicFileType;
import org.rti.webgenome.graphics.primitive.Circle;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>RasterText</code>.
 * @author dhall
 *
 */
public final class RasterTextTester extends TestCase {
	
	/**
	 * Name of directory where test output files will be written.
	 * This is not an absolute path.
	 */
	private static final String TEST_DIR_NAME = "raster-text-test";
	
	/** Directory where output test files will be written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Test string to render. */
	private static final String TEST_STR = "Test_String";
	
	/** Width of graphic in pixels. */
	private static final int WIDTH = 500;
	
	/** Height of graphic in pixels. */
	private static final int HEIGHT = 500;
	
	/** Radius of reference point in center of graphic. */
	private static final int REFERENCE_POINT_RADIUS = 3;
	
	/** Color of background. */
	private static final Color BG_COLOR = Color.WHITE;
	
	/** Color of reference point. */
	private static final Color REFERENCE_POINT_COLOR = Color.BLUE;
	
	/** Font size. */
	private static final int FONT_SIZE = 12;
	
	/** Color of text. */
	private static final Color TEXT_COLOR = Color.BLACK;
	
	/** Rotation of text for vertical tests in radians. */
	private static final double ROTATION = 1.5 * Math.PI;
	
	/**
	 * Test in horizontal position and left
	 * aligned to reference point.
	 */
	public void testHorizLeft() {
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		this.layoutReferenceShapes(canvas);
		canvas.add(canvas.newText(TEST_STR, WIDTH / 2, HEIGHT / 2,
				FONT_SIZE, HorizontalAlignment.LEFT_JUSTIFIED,
				TEXT_COLOR));
		GraphicFileUtils.write(TEST_DIR, "horiz-left.png",
				canvas.toBufferedImage(), RasterGraphicFileType.PNG);
	}
	
	
	/**
	 * Test in horizontal position and right
	 * aligned to reference point.
	 */
	public void testHorizRight() {
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		this.layoutReferenceShapes(canvas);
		canvas.add(canvas.newText(TEST_STR, WIDTH / 2, HEIGHT / 2,
				FONT_SIZE, HorizontalAlignment.RIGHT_JUSTIFIED,
				TEXT_COLOR));
		GraphicFileUtils.write(TEST_DIR, "horiz-right.png",
				canvas.toBufferedImage(), RasterGraphicFileType.PNG);
	}
	
	
	/**
	 * Test in horizontal position and centered
	 * on reference point.
	 */
	public void testHorizCentered() {
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		this.layoutReferenceShapes(canvas);
		canvas.add(canvas.newText(TEST_STR, WIDTH / 2, HEIGHT / 2,
				FONT_SIZE, HorizontalAlignment.CENTERED,
				TEXT_COLOR));
		GraphicFileUtils.write(TEST_DIR, "horiz-centered.png",
				canvas.toBufferedImage(), RasterGraphicFileType.PNG);
	}
	
	
	/**
	 * Test in vertical position and left aligned
	 * to reference point.
	 */
	public void testVerticalLeft() {
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		this.layoutReferenceShapes(canvas);
		Text text = canvas.newText(TEST_STR, WIDTH / 2, HEIGHT / 2,
				FONT_SIZE, HorizontalAlignment.LEFT_JUSTIFIED,
				TEXT_COLOR);
		text.setRotation(ROTATION);
		canvas.add(text);
		GraphicFileUtils.write(TEST_DIR, "vert-left.png",
				canvas.toBufferedImage(), RasterGraphicFileType.PNG);
	}
	
	
	/**
	 * Test in vertical position and right aligned
	 * to reference point.
	 */
	public void testVerticalRight() {
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		this.layoutReferenceShapes(canvas);
		Text text = canvas.newText(TEST_STR, WIDTH / 2, HEIGHT / 2,
				FONT_SIZE, HorizontalAlignment.RIGHT_JUSTIFIED,
				TEXT_COLOR);
		text.setRotation(ROTATION);
		canvas.add(text);
		GraphicFileUtils.write(TEST_DIR, "vert-right.png",
				canvas.toBufferedImage(), RasterGraphicFileType.PNG);
	}
	
	
	/**
	 * Test in vertical position and centered
	 * on reference point.
	 */
	public void testVerticalCentered() {
		RasterDrawingCanvas canvas = new RasterDrawingCanvas();
		this.layoutReferenceShapes(canvas);
		Text text = canvas.newText(TEST_STR, WIDTH / 2, HEIGHT / 2,
				FONT_SIZE, HorizontalAlignment.CENTERED,
				TEXT_COLOR);
		text.setRotation(ROTATION);
		canvas.add(text);
		GraphicFileUtils.write(TEST_DIR, "vert-center.png",
				canvas.toBufferedImage(), RasterGraphicFileType.PNG);
	}
	
	
	/**
	 * Layout visual reference points.
	 * @param canvas A canvas
	 */
	private void layoutReferenceShapes(final DrawingCanvas canvas) {
		canvas.add(new Rectangle(0, 0, WIDTH, HEIGHT, BG_COLOR));
		canvas.add(new Circle(WIDTH / 2, HEIGHT / 2,
				REFERENCE_POINT_RADIUS, REFERENCE_POINT_COLOR));
		canvas.setWidth(WIDTH);
		canvas.setHeight(HEIGHT);
	}
}
