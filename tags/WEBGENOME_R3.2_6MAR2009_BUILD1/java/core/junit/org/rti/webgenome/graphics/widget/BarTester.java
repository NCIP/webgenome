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
