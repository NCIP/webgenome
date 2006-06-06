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

import org.rti.webcgh.array.ArrayDatumFactory;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.GenomeLocationFactory;
import org.rti.webcgh.drawing.HorizontalAlignment;
import org.rti.webcgh.drawing.VerticalAlignment;
import org.rti.webcgh.graph.PlotGenerator;
import org.rti.webcgh.graph.unit_test.SvgTestPanel;
import org.rti.webcgh.graph.widget.FrequencyPlot;

import junit.framework.TestCase;

public class FrequencyPlotTester extends TestCase {
	
	
//	public void testEmpty() throws Exception {
//		SvgTestPanel panel = SvgTestPanel.newSvgTestPanel();
//		panel.setDrawBorder(true);
//		Experiment exp = new Experiment();
//		exp.setName("Experiment");
//		BioAssay ba1 = new BioAssay("Bioassay 1");
//		exp.add(ba1);
//		FrequencyPlot plot = new FrequencyPlot(400, 400, 0, 5000, 0, 100);
//		GenomeLocationFactory fact = new GenomeLocationFactory();
//		GenomeLocation start = fact.newGenomeLocation((short)1, (long)0);
//		GenomeLocation end = fact.newGenomeLocation((short)1, (long)5000);
//		ba1.graph(plot, start, end, Color.BLACK);
//		panel.add(plot, HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
//		panel.toSvgFile("freq-plot-empty.svg");
//	}
	
	
//	public void testOneDatum() throws Exception {
//		SvgTestPanel panel = SvgTestPanel.newSvgTestPanel();
//		panel.setDrawBorder(true);
//		Experiment exp = new Experiment();
//		exp.setName("Experiment");
//		BioAssay ba1 = new BioAssay("Bioassay 1");
//		exp.add(ba1);
//		ArrayDatumFactory fac = new ArrayDatumFactory();
//		ba1.add(fac.newArrayDatum("r1", (short)1, (long)3000, (float)50));
//		FrequencyPlot plot = new FrequencyPlot(400, 400, 0, 4000, 0, 100);
//		GenomeLocationFactory fact = new GenomeLocationFactory();
//		GenomeLocation start = fact.newGenomeLocation((short)1, (long)0);
//		GenomeLocation end = fact.newGenomeLocation((short)1, (long)4000);
//		ba1.graph(plot, start, end, Color.BLACK);
//		panel.add(plot, HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
//		panel.toSvgFile("freq-plot-one-datum.svg");
//	}
	
	
	public void testTwoDatum() throws Exception {
		SvgTestPanel panel = SvgTestPanel.newSvgTestPanel();
		panel.setDrawBorder(true);
		Experiment exp = new Experiment();
		exp.setName("Experiment");
		BioAssay ba1 = new BioAssay("Bioassay 1");
		exp.add(ba1);
		ArrayDatumFactory fac = new ArrayDatumFactory();
		ba1.add(fac.newArrayDatum("r1", (short)1, (long)2000, (float)30));
		ba1.add(fac.newArrayDatum("r2", (short)1, (long)3000, (float)50));
		FrequencyPlot plot = new FrequencyPlot(400, 400, 0, 4000, 0, 100);
		GenomeLocationFactory fact = new GenomeLocationFactory();
		GenomeLocation start = fact.newGenomeLocation((short)1, (long)0);
		GenomeLocation end = fact.newGenomeLocation((short)1, (long)4000);
		ba1.graph(plot, start, end, Color.BLACK);
		panel.add(plot, HorizontalAlignment.CENTERED, VerticalAlignment.CENTERED);
		panel.toSvgFile("freq-plot-two-datum.svg");
	}

}
