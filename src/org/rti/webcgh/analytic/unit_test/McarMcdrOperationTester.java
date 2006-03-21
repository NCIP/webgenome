/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/analytic/unit_test/McarMcdrOperationTester.java,v $
$Revision: 1.1 $
$Date: 2006-03-21 15:48:55 $

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


package org.rti.webcgh.analytic.unit_test;

import org.rti.webcgh.analytic.McarMcdrOperation;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.ChromosomalAlteration;
import org.rti.webcgh.array.ChromosomalAlterationIterator;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.graph.PlotParameters;

import junit.framework.TestCase;

public class McarMcdrOperationTester extends TestCase {
	
	
	public void test1() throws Exception {
		
		// Plot parameters
		PlotParameters pp = new PlotParameters();
		pp.setLowerMaskValue(-0.2);
		pp.setUpperMaskValue(0.2);
		
		// Bioassays
		BioAssay b1 = new BioAssay();
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.1, (short)1, (long)0));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)10));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)20));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.1, (short)1, (long)30));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)40));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)50));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)2, (long)0));
		b1.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)2, (long)10));
		
		BioAssay b2 = new BioAssay();
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.1, (short)1, (long)5));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)15));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)25));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)35));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)1, (long)45));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.1, (short)1, (long)55));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)2, (long)0));
		b2.add(ArrayDatum.newUnaffiliatedArrayDatum((float)0.3, (short)2, (long)10));
		
		// Experiment
		Experiment exp = new Experiment();
		exp.add(b1);
		exp.add(b2);
		
		// Perform operation
		McarMcdrOperation op = new McarMcdrOperation();
		Experiment[] exps = op.perform(new Experiment[]{exp}, pp);
		
		// Run basic checks
		assertNotNull(exps);
		assertEquals(exps.length, 2);
		
		// Checks on first interval
		exp = exps[1];
		ChromosomalAlterationIterator it = exp.amplificationIterator();
		assertTrue(it.hasNext());
		ChromosomalAlteration a = it.next();
		assertEquals(a.startBp(), (long)10);
		assertEquals(a.endBp(), (long)25);
		
		// Checks on second interval
		assertTrue(it.hasNext());
		a = it.next();
		assertEquals(a.startBp(), (long)35);
		assertEquals(a.endBp(), (long)50);
		
		// Checks on third interval
		assertTrue(it.hasNext());
		a = it.next();
		assertEquals(a.startBp(), (long)0);
		assertEquals(a.endBp(), (long)10);
		
		// Make sure no more intervals
		assertFalse(it.hasNext());
	}

}
