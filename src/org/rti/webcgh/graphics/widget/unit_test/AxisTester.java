/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2006-09-25 22:04:55 $

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

import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.Axis;
import org.rti.webcgh.graphics.widget.Background;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;
import org.rti.webcgh.util.FileUtils;

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
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);

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
	
	/**
	 * Test in horizontal orientation with text above axis.
	 */
	public void testHorizAbove() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.HORIZONTAL,
				Location.ABOVE, panel.getDrawingCanvas()),
				HorizontalAlignment.CENTERED, VerticalAlignment.ABOVE);
		panel.toPngFile("horiz-above.png");
	}
	
	/**
	 * Test in horizontal orientation with text below axis.
	 */
	public void testHorizBelow() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.HORIZONTAL,
				Location.BELOW, panel.getDrawingCanvas()),
				HorizontalAlignment.CENTERED, VerticalAlignment.BELOW);
		panel.toPngFile("horiz-below.png");
	}
	
	
	/**
	 * Test in vertical orientation with text to left of axis.
	 */
	public void testVertLeft() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.VERTICAL,
				Location.LEFT_OF, panel.getDrawingCanvas()),
				HorizontalAlignment.LEFT_OF, VerticalAlignment.CENTERED);
		panel.toPngFile("vert-left.png");
	}
	
	
	/**
	 * Test in vertical orientation with text to right of axis.
	 */
	public void testVertRight() {
		RasterFileTestPlotPanel panel = new RasterFileTestPlotPanel(TEST_DIR);
		panel.add(new Background(BG_WIDTH, BG_HEIGHT, BG_COLOR));
		panel.add(new Axis(MIN, MAX, LENGTH, Orientation.VERTICAL,
				Location.RIGHT_OF, panel.getDrawingCanvas()),
				HorizontalAlignment.RIGHT_OF, VerticalAlignment.CENTERED);
		panel.toPngFile("vert-right.png");
	}
}
