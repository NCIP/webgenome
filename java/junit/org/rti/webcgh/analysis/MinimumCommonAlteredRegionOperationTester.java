/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:09 $

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

package org.rti.webcgh.analysis;

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.analysis.MinimumCommonAlteredRegionOperation;
import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotationType;
import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ArrayDatumGenerator;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.QuantitationType;

import junit.framework.TestCase;

/**
 * Tester for <code>MinimumCommonAlteredRegionOperation</code>.
 * @author dhall
 *
 */
public final class MinimumCommonAlteredRegionOperationTester
extends TestCase {

	
	/**
	 * Test perform() method.
	 * @throws Exception if something bad happens
	 */
	public void testPerform() throws Exception {
		
		// Create test data
		long[] locations = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
		float[][] values = {
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
				{1, 1, 1, 1, 0, 0, 0, 1, 1, 0},
				{0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
				{0, 1, 1, 1, 0, 1, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 1, 1, 0, 0, 0}};
		ArrayDatum[][] data = ArrayDatumGenerator.newArrayData(
				(short) 1, locations, values);
		List<ChromosomeArrayData> cads =
			new ArrayList<ChromosomeArrayData>();
		for (int i = 0; i < data.length; i++) {
			ChromosomeArrayData cad = new ChromosomeArrayData((short) 1);
			cads.add(cad);
			for (int j = 0; j < data[i].length; j++) {
				cad.add(data[i][j]);
			}
		}
		
		// Instantiate operation
		MinimumCommonAlteredRegionOperation op =
			new MinimumCommonAlteredRegionOperation();
		op.setProperty("interpolate", "NO");
		op.setProperty("threshold", "0.5");
		op.setProperty("minPercent", "0.5");
		op.setQuantitationType(QuantitationType.LOH);
		
		// Run test
		List<ChromosomeArrayData> output = op.perform(cads);
		assertNotNull(output);
		assertEquals(1, output.size());
		ChromosomeArrayData cad = output.get(0);
		List<AnnotatedGenomeFeature> alts =
			cad.getChromosomeAlterations();
		assertNotNull(alts);
		assertEquals(2, alts.size());
		AnnotatedGenomeFeature f = alts.get(0);
		assertEquals(20, f.getStartLocation());
		assertEquals(40, f.getEndLocation());
		assertTrue(AnnotationType.LOH_SEGMENT == f.getAnnotationType());
		assertEquals((float) 1.0, f.getQuantitation());
		f = alts.get(1);
		assertEquals(80, f.getStartLocation());
		assertEquals(90, f.getEndLocation());
		assertTrue(AnnotationType.LOH_SEGMENT == f.getAnnotationType());
		assertEquals((float) 1.0, f.getQuantitation());
	}
}
