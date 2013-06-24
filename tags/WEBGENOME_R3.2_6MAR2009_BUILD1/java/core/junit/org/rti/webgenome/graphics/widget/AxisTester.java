/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-09-10 21:59:20 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Location;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>Axis</code>.
 * @author dhall
 *
 */
public final class AxisTester extends TestCase {
	
	/**
	 * Name (not absolute path) of directory where test files
	 * will be written.
	 */
	private static final String TEST_DIR_NAME = "axis-test";
	
	/** Directory where test files will be written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);

	/** Minimum test value of axis. */
	private static final double MIN = -0.71;
	
	/** Maximum test value of axis. */
	private static final double MAX = 0.52;
	
	/** Length of axis in pixels. */
	private static final int LENGTH = 400;
	
	/** Width of background object. */
	private static final int BG_WIDTH = 100;
	
	/** Height of background object. */
	private static final int BG_HEIGHT = 100;
	
	/** Color of background object. */
	private static final Color BG_COLOR = Color.PINK;
	
//	/**
//	 * Test in horizontal orientation with text above axis.
//	 */
//	public void testHorizAbove() {
//		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
//		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
//		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.HORIZONTAL,
//				Location.ABOVE, panel.getDrawingCanvas()),
//				HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
//		panel.toPngFile("horiz-above.png");
//	}
//	
//	/**
//	 * Test in horizontal orientation with text below axis.
//	 */
//	public void testHorizBelow() {
//		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
//		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
//		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.HORIZONTAL,
//				Location.BELOW, panel.getDrawingCanvas()),
//				HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
//		panel.toPngFile("horiz-below.png");
//	}
//	
//	
//	/**
//	 * Test in vertical orientation with text to left of axis.
//	 */
//	public void testVertLeft() {
//		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
//		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
//		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.VERTICAL,
//				Location.LEFT_OF, panel.getDrawingCanvas()),
//				HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
//		panel.toPngFile("vert-left.png");
//	}
//	
//	
//	/**
//	 * Test in vertical orientation with text to right of axis.
//	 */
//	public void testVertRight() {
//		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
//		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
//		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.VERTICAL,
//				Location.RIGHT_OF, panel.getDrawingCanvas()),
//				HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
//		panel.toPngFile("vert-right.png");
//	}
//	
//	
//	/**
//	 * Test in vertical aligned to zero point.
//	 */
//	public void testVertOnZero() {
//		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
//		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.VERTICAL,
//				Location.LEFT_OF, panel.getDrawingCanvas()),
//				HorizontalAlignment.RIGHT_OF, VerticalAlignment.ON_ZERO);
//		panel.add(new Axis(MIN, MAX + 1.0, LENGTH, Orientation.VERTICAL,
//				Location.RIGHT_OF, panel.getDrawingCanvas()),
//				HorizontalAlignment.RIGHT_OF, VerticalAlignment.ON_ZERO);
//		panel.toPngFile("vert-on-zero.png");
//	}
	
	
	/**
	 * Test on client-specified hatches.
	 */
	public void testClientSpecifiedHatches() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		List<Double> points = new ArrayList<Double>();
		List<String> labels = new ArrayList<String>();
		points.add(new Double(300000));
		points.add(new Double(500000));
		points.add(new Double(600000));
		labels.add("CHR1");
		labels.add("CHR2");
		labels.add("CHR3");
		panel.add(new Axis(LENGTH, Orientation.HORIZONTAL,
				Location.BELOW, panel.getDrawingCanvas(),
				points, labels),
				HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
		panel.toPngFile("user-specified.png");
	}
}
