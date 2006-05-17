/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.graph.widget.unit_test;

import java.awt.Color;
import java.net.URL;

import junit.framework.TestCase;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.Location;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.drawing.VerticalAlignment;
import org.rti.webcgh.graph.unit_test.SvgTestPanel;
import org.rti.webcgh.graph.util.CentromereWarper;
import org.rti.webcgh.graph.util.Warper;
import org.rti.webcgh.graph.widget.Caption;
import org.rti.webcgh.graph.widget.GenomeFeatureMap;
import org.rti.webcgh.graph.widget.PlotPanel;


/**
 * Tester for GenomeFeatureMap
 */
public class GenomeFeatureMapTester extends TestCase {
	
	private SvgTestPanel panel = null;
	
	/**
	 * 
	 */
	public void setUp() {
		this.panel = SvgTestPanel.newSvgTestPanel();
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testBasicHoriz() throws Exception {
		GenomeFeatureMap map = new GenomeFeatureMap(100, 200, 400, Orientation.HORIZONTAL);
		map.plotFeature(110, 120, "Feat 1", new URL("http://www.google.com"), true, 
			Color.green);
		map.plotFeatures(new long[] {150, 165, 180}, new long[] {151, 172, 190}, "Feat 2",
			null, true, Color.red);
		map.addFrame(Location.ABOVE, 2, Color.black);
		map.addFrame(Location.BELOW, 2, Color.black);
		panel.add(map, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        panel.toSvgFile("feat-map-basic-horiz.svg");
	}
	
	
	public void testIdeogramPlot() throws Exception {
		PlotPanel childPanel = this.panel.newChildPlotPanel();
		GenomeFeatureMap map1 = new GenomeFeatureMap(0, 200, 400, Orientation.VERTICAL);
		map1.plotFeature(0, 200, "Feature 1", null, false, Color.blue);
		GenomeFeatureMap map2 = new GenomeFeatureMap(0, 200, 400, Orientation.VERTICAL);
		map2.plotFeature(0, 200, "Feature 2", null, false, Color.blue);
		Caption c1 = new Caption("Feature 1", Orientation.VERTICAL, false);
		Caption c2 = new Caption("Feature 2", Orientation.VERTICAL, false);
		PlotPanel p1 = childPanel.newChildPlotPanel();
		PlotPanel p2 = childPanel.newChildPlotPanel();
		p1.add(map1, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
		p1.add(c1, HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
		childPanel.add(p1, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
		p2.add(map2, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
		p2.add(c2, HorizontalAlignment.RIGHT_JUSTIFIED, VerticalAlignment.ABOVE);
		childPanel.add(p2, HorizontalAlignment.RIGHT_OF, VerticalAlignment.TOP_JUSTIFIED);
		panel.add(childPanel, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        panel.toSvgFile("feat-map-ideogram.svg");
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testBasicVert() throws Exception {
		GenomeFeatureMap map = new GenomeFeatureMap(100, 200, 400, Orientation.VERTICAL);
		map.plotFeature(110, 120, "Feat 1", new URL("http://www.google.com"), true, 
			Color.green);
		map.plotFeatures(new long[] {150, 165, 180}, new long[] {151, 172, 190}, "Feat 2",
			null, true, Color.red);
		map.addFrame(Location.ABOVE, 2, Color.black);
		map.addFrame(Location.BELOW, 2, Color.black);
		panel.add(map, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        panel.toSvgFile("feat-map-basic-vert.svg");
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testChromosomeHoriz() throws Exception {
		GenomeFeatureMap map = new GenomeFeatureMap(100, 200, 400, Orientation.HORIZONTAL);
		Warper warper = new CentromereWarper(map.getFeatureHeight(), map.bpToPixel(110), 
			map.bpToPixel(150));
		map.setWarper(warper);
		
//		map.plotFeature(100, 140, "Feat 1", new URL("http://www.google.com"), false, 
//				Color.gray);
//		map.plotFeature(140, 200, "Feat 1", new URL("http://www.google.com"), false, 
//				Color.red);
		
		map.plotFeature(100, 120, "Feat 1", new URL("http://www.google.com"), false, 
			Color.gray);
		map.plotFeature(120, 125, "Feat 1", new URL("http://www.google.com"), false, 
				Color.red);
		map.plotFeature(125, 166, "Feat 1", new URL("http://www.google.com"), false, 
				Color.green);
		map.plotFeature(166, 172, "Feat 1", new URL("http://www.google.com"), false, 
				Color.gray);
		map.plotFeature(172, 191, "Feat 1", new URL("http://www.google.com"), false, 
				Color.darkGray);
		map.plotFeature(191, 200, "Feat 1", new URL("http://www.google.com"), false, 
				Color.blue);
		map.addFrame(Location.ABOVE, 2, Color.black);
		map.addFrame(Location.BELOW, 2, Color.black);
		panel.add(map, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        panel.toSvgFile("feat-map-chrom-horiz.svg");
	}
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testChromosomeVert() throws Exception {
		GenomeFeatureMap map = new GenomeFeatureMap(100, 200, 400, Orientation.VERTICAL);
		Warper warper = new CentromereWarper(map.getFeatureHeight(), map.bpToPixel(110), 
			map.bpToPixel(150));
		map.setWarper(warper);
		
//		map.plotFeature(100, 140, "Feat 1", new URL("http://www.google.com"), false, 
//				Color.gray);
//		map.plotFeature(140, 200, "Feat 1", new URL("http://www.google.com"), false, 
//				Color.red);
		
		map.plotFeature(100, 120, "Feat 1", new URL("http://www.google.com"), false, 
			Color.gray);
		map.plotFeature(120, 125, "Feat 1", new URL("http://www.google.com"), false, 
				Color.red);
		map.plotFeature(125, 166, "Feat 1", new URL("http://www.google.com"), false, 
				Color.green);
		map.plotFeature(166, 172, "Feat 1", new URL("http://www.google.com"), false, 
				Color.gray);
		map.plotFeature(172, 191, "Feat 1", new URL("http://www.google.com"), false, 
				Color.darkGray);
		map.plotFeature(191, 200, "Feat 1", new URL("http://www.google.com"), false, 
				Color.blue);
		map.addFrame(Location.ABOVE, 2, Color.black);
		map.addFrame(Location.BELOW, 2, Color.black);
		panel.add(map, HorizontalAlignment.LEFT_JUSTIFIED, VerticalAlignment.TOP_JUSTIFIED);
        panel.toSvgFile("feat-map-chrom-vert.svg");
	}

}
