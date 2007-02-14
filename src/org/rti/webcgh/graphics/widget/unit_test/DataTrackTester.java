/*
$Revision: 1.1 $
$Date: 2007-02-14 21:41:01 $

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

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.DataTrack;
import org.rti.webcgh.util.FileUtils;

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
		FileUtils.createUnitTestDirectory(TEST_DIR_NAME);
	
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
