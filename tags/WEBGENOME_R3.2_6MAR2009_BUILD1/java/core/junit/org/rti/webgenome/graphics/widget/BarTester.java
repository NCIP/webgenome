/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.widget;

import java.io.File;

import org.rti.webgenome.graphics.widget.Bar;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>Bar</code>.
 * @author dhall
 *
 */
public final class BarTester extends TestCase {
	
	/**
	 * Name of directory that will contain test output files.
	 * The absolute path will be a subdirectory to the
	 * main test directory defined by the property
	 * 'temp.dir' in the file 'unit_test.properties.'
	 */
	private static final String TEST_DIR_NAME = "bar-test";
	
	/** Directory that will hold test output files. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);

	
	/**
	 * Test on a positive value with no error.
	 */
	public void testPositiveNoError() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) 3.0, "Bioassay 1",
				(float) 4.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar);
		panel.toPngFile("positive-no-error.png");
	}
	
	
	/**
	 * Test on a positive value with error.
	 */
	public void testPositiveWithError() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) 3.0, (float) 0.5, "Bioassay 1",
				(float) 4.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar);
		panel.toPngFile("positive-with-error.png");
	}
	
	
	/**
	 * Test on a negative value with no error.
	 */
	public void testNegativeNoError() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) -3.0, "Bioassay 1",
				(float) 0.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar);
		panel.toPngFile("negative-no-error.png");
	}
	
	
	/**
	 * Test on a negative value with error.
	 */
	public void testNegativeWithError() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) -3.0, (float) 0.5, "Bioassay 1",
				(float) 0.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar);
		panel.toPngFile("negative-with-error.png");
	}
	
	
	/**
	 * Test two positives.
	 */
	public void testTwoPositives() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) 3.0, (float) 0.5, "Bioassay 1",
				(float) 6.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar, HorizontalAlignment.LEFT_OF, VerticalAlignment.ON_ZERO);
		bar = new Bar((float) 5.0, (float) 0.5, "Bioassay 1",
				(float) 6.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar, HorizontalAlignment.LEFT_OF, VerticalAlignment.ON_ZERO);
		panel.toPngFile("two-positives.png");
	}
	
	/**
	 * Test two negatives.
	 */
	public void testTwoNegatives() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) -3.0, (float) 0.5, "Bioassay 1",
				(float) 0.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar, HorizontalAlignment.LEFT_OF, VerticalAlignment.ON_ZERO);
		bar = new Bar((float) -5.0, (float) 0.5, "Bioassay 1",
				(float) 0.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar, HorizontalAlignment.LEFT_OF, VerticalAlignment.ON_ZERO);
		panel.toPngFile("two-negatives.png");
	}
	
	
	/**
	 * Test negative and positive.
	 */
	public void testNegativeAndPositive() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		Bar bar = new Bar((float) 3.0, (float) 0.5, "Bioassay 1",
				(float) 6.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar, HorizontalAlignment.LEFT_OF, VerticalAlignment.ON_ZERO);
		bar = new Bar((float) -5.0, (float) 0.5, "Bioassay 1",
				(float) 6.0, (float) 50, panel.getDrawingCanvas());
		panel.add(bar, HorizontalAlignment.LEFT_OF, VerticalAlignment.ON_ZERO);
		panel.toPngFile("negative-and-positive.png");
	}
}
