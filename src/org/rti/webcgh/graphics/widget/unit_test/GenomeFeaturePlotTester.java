/*
$Revision: 1.3 $
$Date: 2006-09-17 20:27:33 $

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

package org.rti.webcgh.graphics.widget.unit_test;

import java.awt.Color;
import junit.framework.TestCase;

import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.util.CentromereWarper;
import org.rti.webcgh.graphics.widget.GenomeFeaturePlot;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.util.FileUtils;

/**
 * Tester for <code>GenomeFeaturePlot</code>.
 * @author dhall
 *
 */
public final class GenomeFeaturePlotTester extends TestCase {
	
	/**
	 * Name of temporary directory for storing
	 * generated ata files.  This is not an absolute
	 * path.
	 */
	private static final String TEMP_DIR_NAME =
		"genome_feature_map_test_dir";
	
	/**
	 * Path to temporary directory for storing data files.
	 * It will be a subdirectory of the main
	 * unit test temporary directory specified
	 * by the property 'temp.dir' in 'unit_test.properties.'
	 */
	private static final String TEMP_DIR_PATH =
		FileUtils.createUnitTestDirectory(TEMP_DIR_NAME).getAbsolutePath();
	
	
	/**
	 * Create plot similar to a vertical ideogram.
	 * @throws Exception if anything bad happens
	 */
	public void testVerticalIdeogram() throws Exception {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		PlotPanel row = panel.newChildPlotPanel();
		
		// Add first map
		GenomeFeaturePlot map1 = new GenomeFeaturePlot((long) 1,
				(long) 50, 400, Orientation.VERTICAL);
		CentromereWarper warper1 =
			new CentromereWarper(map1.getFeatureHeight(), 200, 250);
		map1.setWarper(warper1);
		map1.plotFeature(1, 10, null, null, false, Color.BLACK);
		map1.plotFeature(10, 15, null, null, false, Color.GRAY);
		map1.plotFeature(15, 25, null, null, false, Color.DARK_GRAY);
		map1.plotFeature(25, 40, null, null, false, Color.BLACK);
		map1.plotFeature(40, 50, null, null, false, Color.GRAY);
		map1.addFrame(Location.ABOVE, 1, Color.BLACK);
		map1.addFrame(Location.BELOW, 1, Color.BLACK);
		PlotPanel mapPan1 = row.newChildPlotPanel();
		mapPan1.add(map1, true);
		row.add(mapPan1, true);
		
		// Add second map
		GenomeFeaturePlot map2 = new GenomeFeaturePlot((long) 1,
				(long) 30, 300, Orientation.VERTICAL);
		CentromereWarper warper2 =
			new CentromereWarper(map2.getFeatureHeight(), 100, 150);
		map2.setWarper(warper2);
		map2.plotFeature(1, 5, null, null, false, Color.GRAY);
		map2.plotFeature(5, 15, null, null, false, Color.BLACK);
		map2.plotFeature(15, 25, null, null, false, Color.GRAY);
		map2.plotFeature(25, 30, null, null, false, Color.BLACK);
		map2.addFrame(Location.ABOVE, 1, Color.BLACK);
		map2.addFrame(Location.BELOW, 1, Color.BLACK);
		PlotPanel mapPan2 = row.newChildPlotPanel();
		mapPan2.add(map2, true);
		row.add(mapPan2, HorizontalAlignment.RIGHT_OF,
				VerticalAlignment.TOP_JUSTIFIED);
		
		panel.add(row);
		
		// Output graphic to file
		panel.toPngFile("vert-ideogram.png");
	}

	
	/**
	 * Create plot similar to a horizontal ideogram.
	 * @throws Exception if anything bad happens
	 */
	public void testHorizontalIdeogram() throws Exception {
		GenomeFeaturePlot map = new GenomeFeaturePlot((long) 1,
				(long) 50, 400, Orientation.HORIZONTAL);
		CentromereWarper warper =
			new CentromereWarper(map.getFeatureHeight(), 200, 250);
		map.setWarper(warper);
		map.plotFeature(1, 10, null, null, false, Color.BLACK);
		map.plotFeature(10, 15, null, null, false, Color.GRAY);
		map.plotFeature(15, 25, null, null, false, Color.DARK_GRAY);
		map.plotFeature(25, 40, null, null, false, Color.BLACK);
		map.plotFeature(40, 50, null, null, false, Color.GRAY);
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEMP_DIR_PATH);
		panel.add(map);
		panel.toPngFile("horiz-ideogram.png");
	}
}
