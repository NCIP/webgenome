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
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

/**
 * Tester for <code>PlotPanel</code>.
 * @author dhall
 *
 */
public final class PlotPanelTester extends TestCase {

	/**
	 * Name of directory where test output files
	 * will be written.  Absolute path will be subdirectory
	 * off main temp directory defined by the property
	 * 'temp.dir' in the properties file 'unit_test.properties.'
	 */
	private static final String TEST_DIR_NAME = "plot-panel-test";
	
	/** Directory where test output files will be written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Width of test background widgets. */
	private static final int WIDTH = 100;
	
	/** Height of test background widgets. */
	private static final int HEIGHT = 100;
	
	/** Color of one of the test background widgets. */
	private static final Color COLOR_1 = Color.BLUE;
	
	/** Color of the other test background widget. */
	private static final Color COLOR_2 = Color.RED;
	
	/**
	 * Test methods creates a single panel and adds
	 * two widgets.
	 *
	 */
	public void testOnePanel() {
		// Instantiate nested panels
		RasterFileTestPlotPanel parent =
			new RasterFileTestPlotPanel(TEST_DIR);
		
		// Add background widgets to panel such that
		// they are top aligned and non-overlapping
		parent.add(new Background(WIDTH, HEIGHT, COLOR_1), true);
		parent.add(new Background(WIDTH, HEIGHT, COLOR_2),
				HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
		
		// Output graphics to file
		parent.toPngFile("one-panel.png");
	}
	
	/**
	 * Test method that creates a series of
	 * nested panels to make sure the resulting
	 * graphical objects are rendered properly.
	 * The depth of nesting is 2 levels.
	 */
	public void testNestedPanelsDepth2() {
		
		// Instantiate nested panels
		RasterFileTestPlotPanel parent =
			new RasterFileTestPlotPanel(TEST_DIR);
		PlotPanel child1 = parent.newChildPlotPanel();
		PlotPanel child2 = parent.newChildPlotPanel();
		
		// Add background widgets to child panels such that
		// they are top aligned and non-overlapping
		child1.add(new Background(WIDTH, HEIGHT, COLOR_1));
		child2.add(new Background(WIDTH, HEIGHT, COLOR_2));
		
		// Nest panels together
		parent.add(child1, true);
		parent.add(child2, HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
		
		// Output graphics to file
		parent.toPngFile("nested_2.png");
	}
	
	
	/**
	 * Test method that creates a series of
	 * nested panels to make sure the resulting
	 * graphical objects are rendered properly.
	 * The depth of nesting is 3 levels.
	 */
	public void testNestedPanelsDepth3() {
		
		// Instantiate nested panels
		RasterFileTestPlotPanel parent =
			new RasterFileTestPlotPanel(TEST_DIR);
		PlotPanel child = parent.newChildPlotPanel();
		PlotPanel grandChild1 = child.newChildPlotPanel();
		PlotPanel grandChild2 = child.newChildPlotPanel();
		
		// Add background widgets to child panels such that
		// they are top aligned and non-overlapping
		grandChild1.add(new Background(WIDTH, HEIGHT, COLOR_1));
		child.add(grandChild1, true);
		grandChild2.add(new Background(WIDTH, HEIGHT, COLOR_2));
		child.add(grandChild2, HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
		
		// Nest panels together
		parent.add(child, true);
		
		// Output graphics to file
		parent.toPngFile("nested_3.png");
	}
}
