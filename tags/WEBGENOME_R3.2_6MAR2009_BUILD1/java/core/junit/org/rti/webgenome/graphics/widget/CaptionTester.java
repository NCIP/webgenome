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

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.io.File;

import junit.framework.TestCase;

import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.graphics.widget.Caption;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

/**
 * Tester for <code>Caption</code>.
 * @author dhall
 *
 */
public final class CaptionTester extends TestCase {

	/**
	 * Name of directory that will contain test output files.
	 * The absolute path will be a subdirectory to the
	 * main test directory defined by the property
	 * 'temp.dir' in the file 'unit_test.properties.'
	 */
	private static final String TEST_DIR_NAME = "caption-test";
	
	/** Directory that will hold test output files. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Width of background object in pixels. */
	private static final int BG_WIDTH = 50;
	
	/** Height of background object in pixels. */
	private static final int BG_HEIGHT = 50;
	
	/** Color of background object. */
	private static final Color BG_COLOR = Color.PINK;
	
	/** Test text .*/
	private static final String TEXT = "Yabadabadoo";
	
	/**
	 * Test single caption above
	 * a background object.
	 */
	public void testAbove() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Caption(TEXT, Orientation.HORIZONTAL, false,
				panel.getDrawingCanvas()),
				HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
		panel.toPngFile("above.png");
	}
	
	
	/**
	 * Test single caption below
	 * a background object.
	 */
	public void testBelow() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Caption(TEXT, Orientation.HORIZONTAL, false,
				panel.getDrawingCanvas()),
				HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
		panel.toPngFile("below.png");
	}
	
	
	/**
	 * Test single caption left of
	 * a background object.
	 */
	public void testLeft() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Caption(TEXT, Orientation.HORIZONTAL, false,
				panel.getDrawingCanvas()),
				HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
		panel.toPngFile("left.png");
	}
	
	/**
	 * Test single caption right of
	 * a background object.
	 */
	public void testRight() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Caption(TEXT, Orientation.HORIZONTAL, false,
				panel.getDrawingCanvas()),
				HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
		panel.toPngFile("right.png");
	}
}
