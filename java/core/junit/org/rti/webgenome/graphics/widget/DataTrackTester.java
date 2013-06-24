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

import java.io.File;

import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.graphics.widget.DataTrack;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>DataTrack</code>.
 * @author dhall
 *
 */
public final class DataTrackTester extends TestCase {

	//
	//     STATICS
	//
	
	/**
	 * Name (not absolute path) of directory where test files
	 * will be written.
	 */
	private static final String TEST_DIR_NAME = "data-track-test";
	
	/** Directory where test files will be written. */
	private static final File TEST_DIR =
		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	/** Width of track in pixels. */
	private static final int WIDTH = 500;
	
	
	//
	//     TEST CASES
	//
	
	/**
	 * Test where there are multiple datum within plot range.
	 */
	public void testPaintMultiDatum() {
		
		// Create test data
		ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
		cad.add(new ArrayDatum((float) 1.0,
				new Reporter(null, (short) 1, 100)));
		cad.add(new ArrayDatum((float) 0.5,
				new Reporter(null, (short) 1, 200)));
		cad.add(new ArrayDatum((float) 0.3,
				new Reporter(null, (short) 1, 220)));
		cad.add(new ArrayDatum((float) 1.2,
				new Reporter(null, (short) 1, 500)));
		cad.add(new ArrayDatum((float) 0.1,
				new Reporter(null, (short) 1, 600)));
		cad.add(new ArrayDatum((float) 0.2,
				new Reporter(null, (short) 1, 900)));
		cad.add(new ArrayDatum((float) 0.5,
				new Reporter(null, (short) 1, 910)));
		cad.add(new ArrayDatum((float) 1.0,
				new Reporter(null, (short) 1, 940)));
		
		// Create plot
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		DataTrack dt = new DataTrack(cad, 200, 1000, (float) 0.0, (float) 1.0,
				WIDTH, "Bioassay 1", panel.getDrawingCanvas());
		panel.add(dt);
		
		// Write graphic to file
		panel.toPngFile("multi.png");
	}
	
	
	/**
	 * Test on special case where 2 data points flank the
	 * genome interval being plotted. 
	 */
	public void testSpanningData() {
		
		// Create test data
		ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
		cad.add(new ArrayDatum((float) 1.0,
				new Reporter(null, (short) 1, 100)));
		cad.add(new ArrayDatum((float) 0.5,
				new Reporter(null, (short) 1, 1100)));
		
		// Create plot
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		DataTrack dt = new DataTrack(cad, 200, 1000, (float) 0.0, (float) 1.0,
				WIDTH, "Bioassay 1", panel.getDrawingCanvas());
		panel.add(dt);
		
		// Write graphic to file
		panel.toPngFile("spanning.png");
	}
}
