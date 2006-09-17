/*
$Revision: 1.1 $
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
import java.io.File;
import junit.framework.TestCase;

import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.Background;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.util.FileUtils;

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
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
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
}
