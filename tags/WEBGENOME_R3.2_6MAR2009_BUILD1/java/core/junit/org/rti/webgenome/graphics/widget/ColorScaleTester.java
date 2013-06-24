/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.widget;

import java.io.File;

import org.rti.webgenome.graphics.widget.ColorScale;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>ColorScale</code>.
 * @author dhall
 *
 */
public final class ColorScaleTester extends TestCase {
	
	//
	//       CONSTANTS
	//
	
	/** Width of scale in pixels. */
	private static final int WIDTH = 250;
	
	/** Height of scale in pixels. */
	private static final int HEIGHT = 30;
	
	/** Number of color bins. */
	private static final int NUM_BINS = 16;
	
	/**
	 * Name (not absolute path) of directory where test files
	 * will be written.
	 */
	private static final String TEST_DIR_NAME = "color-scale-test";
	
	/** Directory where test files will be written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	
	//
	//       TEST METHODS
	//
	
	/**
	 * Test drawing this widget alone
	 * with the zero point centered.
	 */
	public void testAloneZeroCentered() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		ColorScale cs = new ColorScale((float) -0.5, (float) 0.5,
				WIDTH, HEIGHT, NUM_BINS, panel.getDrawingCanvas());
		panel.add(cs);
		panel.toPngFile("alone-zero-centered.png");
	}
	
	
	/**
	 * Test drawing this widget alone
	 * with the zero point close to left side.
	 */
	public void testAloneZeroLeft() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		ColorScale cs = new ColorScale((float) -0.005, (float) 0.5,
				WIDTH, HEIGHT, NUM_BINS, panel.getDrawingCanvas());
		panel.add(cs);
		panel.toPngFile("alone-zero-left.png");
	}
	
	
	/**
	 * Test drawing this widget alone
	 * with the zero point close to right side.
	 */
	public void testAloneZeroRight() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		ColorScale cs = new ColorScale((float) -0.5, (float) 0.005,
				WIDTH, HEIGHT, NUM_BINS, panel.getDrawingCanvas());
		panel.add(cs);
		panel.toPngFile("alone-zero-right.png");
	}

}
