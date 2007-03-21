/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:32 $

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

package org.rti.webcgh.graphics.primitive;

import java.awt.Color;
import java.io.File;

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.RasterDrawingCanvas;
import org.rti.webcgh.graphics.io.GraphicFileUtils;
import org.rti.webcgh.graphics.io.RasterGraphicFileType;
import org.rti.webcgh.graphics.primitive.Circle;
import org.rti.webcgh.graphics.primitive.Rectangle;
import org.rti.webcgh.graphics.primitive.Text;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.util.FileUtils;

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
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
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
