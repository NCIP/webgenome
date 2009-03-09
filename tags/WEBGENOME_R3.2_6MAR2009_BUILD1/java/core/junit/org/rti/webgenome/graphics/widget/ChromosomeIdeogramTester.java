/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $

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

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import junit.framework.TestCase;

import org.rti.webgenome.domain.Cytoband;
import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.graphics.widget.ChromosomeIdeogram;
import org.rti.webgenome.graphics.widget.PlotPanel;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

/**
 * Tester for <code>ChromosomeIdeogram</code>.
 * @author dhall
 *
 */
public final class ChromosomeIdeogramTester extends TestCase {
	
	/**
	 * Name of temporary directory for storing
	 * generated ata files.  This is not an absolute
	 * path.
	 */
	private static final String TEMP_DIR_NAME =
		"chromosome_ideogram_test_dir";
	
	/**
	 * Path to temporary directory for storing data files.
	 * It will be a subdirectory of the main
	 * unit test temporary directory specified
	 * by the property 'temp.dir' in 'unit_test.properties.'
	 */
	private static final String TEMP_DIR_PATH =
		UnitTestUtils.createUnitTestDirectory(TEMP_DIR_NAME).getAbsolutePath();
	
	/** Thickness of plot in pixels. */
	private static final int THICKNESS = 40;
		
	
	/**
	 * Add some graphic objects around border of
	 * panel for padding.
	 * @param panel A panel
	 */
	private void addPadding(final PlotPanel panel) {
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
	}
	
	
	/**
	 * Test with no cytobands.
	 */
	public void testNoCytobands() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		ChromosomeIdeogram id = new ChromosomeIdeogram(1,
				50, 20, 25, 400, Orientation.VERTICAL, THICKNESS);
		panel.add(id);
		this.addPadding(panel);
		panel.toPngFile("no-cybotands.png");
	}
	
	
	/**
	 * Test with one cytoband above centromere. 
	 */
	public void testCytobandAboveCent() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		ChromosomeIdeogram id = new ChromosomeIdeogram(1,
				50, 20, 25, 400, Orientation.VERTICAL, THICKNESS);
		Cytoband c = new Cytoband("", 10, 15, "gpos25");
		id.add(c);
		panel.add(id);
		this.addPadding(panel);
		panel.toPngFile("cybotand-above.png");
	}
	
	
	/**
	 * Test with one cytoband that enters centromere. 
	 */
	public void testCytobandEnteringCent() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		ChromosomeIdeogram id = new ChromosomeIdeogram(1,
				50, 20, 30, 400, Orientation.VERTICAL, THICKNESS);
		Cytoband c = new Cytoband("", 10, 22, "gpos25");
		id.add(c);
		panel.add(id);
		this.addPadding(panel);
		panel.toPngFile("cybotand-enters.png");
	}
	
	
	/**
	 * Test with one cytoband crossing the mid point
	 * of centromere.
	 */
	public void testCytobandCrossCentMid() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		ChromosomeIdeogram id = new ChromosomeIdeogram(1,
				50, 20, 30, 400, Orientation.VERTICAL, THICKNESS);
		Cytoband c = new Cytoband("", 10, 28, "gpos25");
		id.add(c);
		panel.add(id);
		this.addPadding(panel);
		panel.toPngFile("cybotand-crosses-mid.png");
	}
	
	
	/**
	 * Test with one cytoband exits the centromere.
	 */
	public void testCytobandExitsCent() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		ChromosomeIdeogram id = new ChromosomeIdeogram(1,
				50, 20, 30, 400, Orientation.VERTICAL, THICKNESS);
		Cytoband c = new Cytoband("", 22, 35, "gpos25");
		id.add(c);
		panel.add(id);
		this.addPadding(panel);
		panel.toPngFile("cybotand-exits.png");
	}
	
	
	/**
	 * Test vertical pair.
	 * @throws Exception if anything bad happens
	 */
	public void testVerticalPair() throws Exception {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		panel.setDrawBorder(false);
		panel.setName("panel");
		PlotPanel row = panel.newChildPlotPanel();
		row.setName("row");
		
		// Add first ideogram
		ChromosomeIdeogram id1 = new ChromosomeIdeogram(1,
				50, 20, 25, 400, Orientation.VERTICAL, THICKNESS);
		PlotPanel mapPan1 = row.newChildPlotPanel();
		mapPan1.setName("mapPan1");
		mapPan1.add(id1, true);
		row.add(mapPan1, true);
		
		// Add second ideogram
		ChromosomeIdeogram id2 = new ChromosomeIdeogram(1,
				30, 5, 10, 300, Orientation.VERTICAL, THICKNESS);
		PlotPanel mapPan2 = row.newChildPlotPanel();
		mapPan2.setName("mapPan2");
		mapPan2.add(id2, true);
		row.add(mapPan2, HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
		
		panel.add(row);
		
		// Add padding objects
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
		panel.add(new Background(10, 10, Color.BLACK),
				HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
		
		// Output graphic to file
		panel.toPngFile("vert-pair.png");
	}

	
//	/**
//	 * Create plot similar to a horizontal ideogram.
//	 * @throws Exception if anything bad happens
//	 */
//	public void testHorizontalIdeogram() throws Exception {
//		GenomeFeaturePlot map = new GenomeFeaturePlot((long) 1,
//				(long) 50, 400, Orientation.HORIZONTAL, THICKNESS);
//		CentromereWarper warper =
//			new CentromereWarper(THICKNESS, 200, 250);
//		map.setWarper(warper);
//		map.plotFeature(1, 10, null, null, false, Color.BLACK);
//		map.plotFeature(10, 15, null, null, false, Color.GRAY);
//		map.plotFeature(15, 25, null, null, false, Color.DARK_GRAY);
//		map.plotFeature(25, 40, null, null, false, Color.BLACK);
//		map.plotFeature(40, 50, null, null, false, Color.GRAY);
//		RasterFileTestPlotPanel panel =
//			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
//		panel.setDrawBorder(false);
//		panel.add(map);
//		panel.toPngFile("horiz-ideogram.png");
//	}
}
