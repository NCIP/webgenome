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

package org.rti.webcgh.plot.unit_test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ArrayDatumGenerator;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.VerticalAlignment;
import org.rti.webcgh.graph.PlotBoundaries;
import org.rti.webcgh.graph.unit_test.SvgTestPanel;
import org.rti.webcgh.plot.ScatterPlot;

import junit.framework.TestCase;

/**
 * Tester for <code>ScatterPlot</code>.
 * @author dhall
 *
 */
public final class ScatterPlotTester extends TestCase {
    
    /** A small number of datum. */
    private static final int SMALL_NUM_DATUM = 25;
    
    /** Large number of datum. */
    private static final int LARGE_NUM_DATUM = 10000;
    
    /** Width of graphic in pixels. */
    private static final int WIDTH = 500;
    
    /** Height of graphic in pixels. */
    private static final int HEIGHT = 400;
    
    
    /**
     * Test on a single bioassay with
     * a small data set.
     *
     */
    public void testSingleBioAssaySmall() {
        
        // Generate test data
        ArrayDatumGenerator gen = new ArrayDatumGenerator();
        ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
        for (int i = 0; i < SMALL_NUM_DATUM; i++) {
            ArrayDatum datum = gen.newArrayDatum();
            cad.add(datum);
        }
        
        // Generate input for scatter plot
        List<ChromosomeArrayData> cads = new ArrayList<ChromosomeArrayData>();
        cads.add(cad);
        List<String> names = new ArrayList<String>();
        names.add("Test data");
        List<Color> colors = new ArrayList<Color>();
        colors.add(Color.BLACK);
        
        // Create plot
        PlotBoundaries pb = new PlotBoundaries(0, cad.minValue(true),
                cad.inferredChromosomeSize(), cad.maxValue(true));
        ScatterPlot plot = new ScatterPlot(cads, names, colors,
                WIDTH, HEIGHT, pb);
        SvgTestPanel panel = SvgTestPanel.newSvgTestPanel();
        panel.setDrawBorder(true);
        panel.add(plot, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        panel.toSvgFile("scatter-plot-small.svg");
    }
    
    
    /**
     * Test on a single bioassay with
     * a large data set.
     *
     */
    public void testSingleBioAssayLarge() {
        
        // Generate test data
        ArrayDatumGenerator gen = new ArrayDatumGenerator();
        ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
        for (int i = 0; i < LARGE_NUM_DATUM; i++) {
            ArrayDatum datum = gen.newArrayDatum();
            cad.add(datum);
        }
        
        // Generate input for scatter plot
        List<ChromosomeArrayData> cads = new ArrayList<ChromosomeArrayData>();
        cads.add(cad);
        List<String> names = new ArrayList<String>();
        names.add("Test data");
        List<Color> colors = new ArrayList<Color>();
        colors.add(Color.BLACK);
        
        // Create plot
        PlotBoundaries pb = new PlotBoundaries(0, cad.minValue(true),
                cad.inferredChromosomeSize(), cad.maxValue(true));
        ScatterPlot plot = new ScatterPlot(cads, names, colors,
                WIDTH, HEIGHT, pb);
        SvgTestPanel panel = SvgTestPanel.newSvgTestPanel();
        panel.setDrawBorder(true);
        panel.add(plot, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        panel.toSvgFile("scatter-plot-large.svg");
    }

}
