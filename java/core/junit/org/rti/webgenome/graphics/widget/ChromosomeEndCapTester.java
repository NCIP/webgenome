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

import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.graphics.widget.ChromosomeEndCap;
import org.rti.webgenome.graphics.widget.ChromosomeIdeogram;
import org.rti.webgenome.units.Direction;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;

import junit.framework.TestCase;

/**
 * Tester for <code>ChromosomeEndCap</code>.
 * @author dhall
 *
 */
public final class ChromosomeEndCapTester extends TestCase {
	
	/**
	 * Name of directory containing output test files.
	 * The absolute path will be the concatenation
	 * of the default <code>RasterFileTestPanel</code>
	 * ouput directory and this.
	 */
	private static final String OUTPUT_DIR_NAME = "chromosome-end-cap";
	
	/**
	 * Thickness of cap.  If horizontal, this is width.
	 * If vertical, this is height.
	 */
	private static final int THICKNESS = 20;

	/**
	 * Test where cap is on top of a pseudo chromosome.
	 *
	 */
	public void testUp() {
		int width = 25;
		int height = 100;
		ChromosomeIdeogram plot = new ChromosomeIdeogram(0, 100000000,
				20000000, 30000000, height,
				Orientation.VERTICAL, THICKNESS);
		ChromosomeEndCap cap =
			new ChromosomeEndCap(width, Color.RED, Direction.UP);
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel();
		panel.add(plot);
		panel.add(cap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.TOP_JUSTIFIED);
		panel.toPngFile(OUTPUT_DIR_NAME + "/top.png");
	}
	
	
	/**
	 * Test where cap is on bottom of a pseudo chromosome.
	 *
	 */
	public void testBottom() {
		int width = 25;
		int height = 100;
		Background bg = new Background(width, height, Color.BLUE);
		ChromosomeEndCap cap =
			new ChromosomeEndCap(width, Color.RED, Direction.DOWN);
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel();
		panel.add(bg);
		panel.add(cap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.BOTTOM_JUSTIFIED);
		panel.toPngFile(OUTPUT_DIR_NAME + "/bottom.png");
	}
	
	
	/**
	 * Test where cap is to the left of a pseudo chromosome.
	 *
	 */
	public void testLeft() {
		int width = 100;
		int height = 25;
		Background bg = new Background(width, height, Color.BLUE);
		ChromosomeEndCap cap =
			new ChromosomeEndCap(height, Color.RED, Direction.LEFT);
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel();
		panel.add(bg);
		panel.add(cap, HorizontalAlignment.LEFT_JUSTIFIED,
				VerticalAlignment.TOP_JUSTIFIED);
		panel.toPngFile(OUTPUT_DIR_NAME + "/left.png");
	}
	
	
	/**
	 * Test where cap is to the left of a pseudo chromosome.
	 *
	 */
	public void testRight() {
		int width = 100;
		int height = 25;
		Background bg = new Background(width, height, Color.BLUE);
		ChromosomeEndCap cap =
			new ChromosomeEndCap(height, Color.RED, Direction.RIGHT);
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel();
		panel.add(bg);
		panel.add(cap, HorizontalAlignment.RIGHT_JUSTIFIED,
				VerticalAlignment.TOP_JUSTIFIED);
		panel.toPngFile(OUTPUT_DIR_NAME + "/right.png");
	}
}
