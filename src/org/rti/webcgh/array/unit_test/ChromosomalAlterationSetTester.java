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

package org.rti.webcgh.array.unit_test;

import org.rti.webcgh.array.ChromosomalAlteration;
import org.rti.webcgh.array.ChromosomalAlterationSet;
import org.rti.webcgh.array.GenomeInterval;
import org.rti.webcgh.array.GenomeLocationFactory;
import org.rti.webcgh.array.QuantifiedInterval;
import org.rti.webcgh.array.QuantifiedIntervals;

import junit.framework.TestCase;


/**
 * Tester for <code>ChromosomalAlterationSet</code>
 *
 */
public class ChromosomalAlterationSetTester extends TestCase {
	
	
	public void testGetQuantifiedIntervals() {
		ChromosomalAlterationSet cas = new ChromosomalAlterationSet();
		GenomeLocationFactory fac = new GenomeLocationFactory();
		cas.add(new ChromosomalAlteration(new GenomeInterval(
				fac.newGenomeLocation((short)1, (long)10), fac.newGenomeLocation((short)1, (long)10))));
		cas.add(new ChromosomalAlteration(new GenomeInterval(
				fac.newGenomeLocation((short)1, (long)20), fac.newGenomeLocation((short)1, (long)20))));
		cas.add(new ChromosomalAlteration(new GenomeInterval(
				fac.newGenomeLocation((short)1, (long)30), fac.newGenomeLocation((short)1, (long)30))));
		cas.add(new ChromosomalAlteration(new GenomeInterval(
				fac.newGenomeLocation((short)2, (long)10), fac.newGenomeLocation((short)2, (long)10))));
		cas.add(new ChromosomalAlteration(new GenomeInterval(
				fac.newGenomeLocation((short)2, (long)20), fac.newGenomeLocation((short)2, (long)20))));
		cas.add(new ChromosomalAlteration(new GenomeInterval(
				fac.newGenomeLocation((short)2, (long)30), fac.newGenomeLocation((short)2, (long)30))));
		QuantifiedIntervals qis = cas.getQuantifiedIntervals(10);
		assertEquals(6, qis.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qis.getIntervals().get(0);
		assertEquals(10, i.getStart());
		assertEquals(15, i.getEnd());
		
		// Second interval
		i = qis.getIntervals().get(1);
		assertEquals(15, i.getStart());
		assertEquals(25, i.getEnd());
		
		// Third interval
		i = qis.getIntervals().get(2);
		assertEquals(25, i.getStart());
		assertEquals(30, i.getEnd());
		
		// Fourth interval
		i = qis.getIntervals().get(3);
		assertEquals(10, i.getStart());
		assertEquals(15, i.getEnd());
		
		// Fifth interval
		i = qis.getIntervals().get(4);
		assertEquals(15, i.getStart());
		assertEquals(25, i.getEnd());
		
		// Sixth interval
		i = qis.getIntervals().get(5);
		assertEquals(25, i.getStart());
		assertEquals(30, i.getEnd());
	}

}
