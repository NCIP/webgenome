/*
$Revision: 1.2 $
$Date: 2006-09-11 18:36:50 $

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

import org.rti.webcgh.graphics.RasterFileTestPlotPanel;
import org.rti.webcgh.graphics.widget.Background;
import org.rti.webcgh.graphics.widget.ChromosomeEndCap;
import org.rti.webcgh.graphics.widget.GenomeFeaturePlot;
import org.rti.webcgh.units.Direction;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;

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
	 * Test where cap is on top of a pseudo chromosome.
	 *
	 */
	public void testUp() {
		int width = 25;
		int height = 100;
		GenomeFeaturePlot plot = new GenomeFeaturePlot(0, 100000000, height,
				Orientation.VERTICAL);
		plot.setFeatureHeight(width);
		plot.plotFeature(0, 10000000, "feat", null, false, Color.GRAY);
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
