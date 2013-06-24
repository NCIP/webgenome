/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2006-12-05 02:55:16 $

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

import java.io.File;

import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.ColorScale;
import org.rti.webcgh.util.FileUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>ColorScale</code>.
 * @author dhall
 *
 */
public final class ColorScaleTester extends TestCase {
	
	//
	//       CONSTANTS
	//
	
	/** Width of scale in pixels. */
	private static final int WIDTH = 250;
	
	/** Height of scale in pixels. */
	private static final int HEIGHT = 30;
	
	/** Number of color bins. */
	private static final int NUM_BINS = 16;
	
	/**
	 * Name (not absolute path) of directory where test files
	 * will be written.
	 */
	private static final String TEST_DIR_NAME = "color-scale-test";
	
	/** Directory where test files will be written. */
	private static final File TEST_DIR =
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
	
	//
	//       TEST METHODS
	//
	
	/**
	 * Test drawing this widget alone
	 * with the zero point centered.
	 */
	public void testAloneZeroCentered() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		ColorScale cs = new ColorScale((float) -0.5, (float) 0.5,
				WIDTH, HEIGHT, NUM_BINS, panel.getDrawingCanvas());
		panel.add(cs);
		panel.toPngFile("alone-zero-centered.png");
	}
	
	
	/**
	 * Test drawing this widget alone
	 * with the zero point close to left side.
	 */
	public void testAloneZeroLeft() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		ColorScale cs = new ColorScale((float) -0.005, (float) 0.5,
				WIDTH, HEIGHT, NUM_BINS, panel.getDrawingCanvas());
		panel.add(cs);
		panel.toPngFile("alone-zero-left.png");
	}
	
	
	/**
	 * Test drawing this widget alone
	 * with the zero point close to right side.
	 */
	public void testAloneZeroRight() {
		RasterFileTestPlotPanel panel =
			new RasterFileTestPlotPanel(TEST_DIR);
		ColorScale cs = new ColorScale((float) -0.5, (float) 0.005,
				WIDTH, HEIGHT, NUM_BINS, panel.getDrawingCanvas());
		panel.add(cs);
		panel.toPngFile("alone-zero-right.png");
	}

}
