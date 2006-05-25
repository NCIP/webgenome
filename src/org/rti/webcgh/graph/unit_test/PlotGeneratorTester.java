/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/unit_test/PlotGeneratorTester.java,v $
$Revision: 1.4 $
$Date: 2006-05-25 23:04:01 $

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

package org.rti.webcgh.graph.unit_test;

import junit.framework.TestCase;

import org.rti.webcgh.array.ArrayDatumFactory;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeInterval;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.graph.PlotGenerator;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.graph.PlotType;

/**
 * Tester for PlotGenerator
 */
public class PlotGeneratorTester extends TestCase {

	
	/**
	 * 
	 *
	 */
	public void testScatterPlot() throws Exception {
		PlotGenerator pg = new PlotGenerator();
		SvgTestPanel panel = SvgTestPanel.newSvgTestPanel();
		Experiment exp = new Experiment();
		exp.setName("Experiment");
		BioAssay ba1 = new BioAssay("Bioassay 1");
		ArrayDatumFactory fac = new ArrayDatumFactory();
		ba1.add(fac.newArrayDatum("r1", (short)1, (long)1000, (float)0.5));
		ba1.add(fac.newArrayDatum("r2", (short)1, (long)2000, (float)0.3));
		ba1.add(fac.newArrayDatum("r3", (short)1, (long)3000, (float)-0.5));
		ba1.add(fac.newArrayDatum("r4", (short)1, (long)4000, (float)0.1));
		ba1.add(fac.newArrayDatum("r5", (short)1, (long)5000, (float)-0.2));
		
//		pg.createPlot(new Experiment[] {this.experiment}, 
//				new GenomeInterval[] {this.genomeInterval1, this.genomeInterval2}, 
//				new QuantitationType("Log2 Ratio"), 
//				new PlotParameters(PlotType.SCATTER_PLOT), this.drawingCanvas);
		panel.toSvgFile("plot-generator-scatter.svg");
	}

}
