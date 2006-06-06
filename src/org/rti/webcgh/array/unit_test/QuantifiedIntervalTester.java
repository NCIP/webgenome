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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.rti.webcgh.array.QuantifiedInterval;


/**
 * Tester for <code>QuantifiedInterval</code>
 *
 */
public class QuantifiedIntervalTester extends TestCase {
	
	
	public void testCompareTo() {
		List<QuantifiedInterval> l = new ArrayList<QuantifiedInterval>();
		l.add(new QuantifiedInterval((long)10, (long)15, 1.0));
		l.add(new QuantifiedInterval((long)8, (long)12, 1.0));
		l.add(new QuantifiedInterval((long)6, (long)7, 1.0));
		Collections.sort(l);
		QuantifiedInterval i = l.get(0);
		assertEquals(6, i.getStart());
		i = l.get(1);
		assertEquals(8, i.getStart());
		i = l.get(2);
		assertEquals(10, i.getStart());
	}

	public void testEquals() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi3 = new QuantifiedInterval((long)5, (long)11, 1.0);
		assertEquals(qi1, qi2);
		assertFalse(qi1.equals(qi3));
	}
	
	
	public void testOverlaps() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)7, (long)15, 1.0);
		QuantifiedInterval qi3 = new QuantifiedInterval((long)11, (long)20, 1.0);
		assertTrue(qi1.overlaps(qi2));
		assertTrue(qi2.overlaps(qi1));
		assertFalse(qi1.overlaps(qi3));
		assertFalse(qi3.overlaps(qi1));
	}
	
	
	public void testProperlyContains() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)7, (long)10, 1.0);
		QuantifiedInterval qi3 = new QuantifiedInterval((long)11, (long)20, 1.0);
		assertTrue(qi1.properlyContains(qi2));
		assertFalse(qi2.properlyContains(qi1));
		assertFalse(qi1.properlyContains(qi3));
	}
	
	
	public void testAdd() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)5, (long)10, 1.0);
		qi1.add(qi2);
		assertEquals(qi1.getValue(), 2.0);
	}
	
	
	public void testIntersection() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)7, (long)12, 1.0);
		QuantifiedInterval qi3 = new QuantifiedInterval((long)11, (long)20, 1.0);
		QuantifiedInterval i = qi1.intersection(qi2);
		assertNotNull(i);
		assertEquals(7, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(2.0, i.getValue());
		i = qi2.intersection(qi1);
		assertNotNull(i);
		assertEquals(7, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(2.0, i.getValue());
		i = qi1.intersection(qi3);
		assertNull(i);
	}
	
	
	public void testChop() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)7, (long)12, 10.0);
		QuantifiedInterval qi3 = new QuantifiedInterval((long)7, (long)8, 10.0);
		QuantifiedInterval qi4 = new QuantifiedInterval((long)11, (long)20, 10.0);
		
		// Everything chopped off
		assertEquals(qi1.chop(qi1).size(), 0);
		
		// Overlapping on one side
		List<QuantifiedInterval> l = qi1.chop(qi2);
		assertEquals(1, l.size());
		QuantifiedInterval i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(7, i.getEnd());
		assertEquals(1.0, i.getValue());
		
		// Overlapping on both sides
		l = qi1.chop(qi3);
		assertEquals(2, l.size());
		i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(7, i.getEnd());
		assertEquals(1.0, i.getValue());
		i = l.get(1);
		assertEquals(8, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(1.0, i.getValue());
		
		// Non-overlapping
		l = qi1.chop(qi4);
		assertEquals(1, l.size());
		i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(1.0, i.getValue());
	}
	
	
	public void testMerge() {
		QuantifiedInterval qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		
		// Equals
		assertEquals(qi1.chop(qi1).size(), 0);
		List<QuantifiedInterval> l = qi1.merge(qi1);
		assertEquals(1, l.size());
		QuantifiedInterval i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(2.0, i.getValue());
		
		// Overlapping on one side
		qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi2 = new QuantifiedInterval((long)7, (long)12, 10.0);
		l = qi1.merge(qi2);
		assertEquals(3, l.size());
		i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(7, i.getEnd());
		assertEquals(1.0, i.getValue());
		i = l.get(1);
		assertEquals(7, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(11.0, i.getValue());
		i = l.get(2);
		assertEquals(10, i.getStart());
		assertEquals(12, i.getEnd());
		assertEquals(10.0, i.getValue());
		
		// Overlapping on both sides
		qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi3 = new QuantifiedInterval((long)7, (long)8, 10.0);
		l = qi1.merge(qi3);
		assertEquals(3, l.size());
		i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(7, i.getEnd());
		assertEquals(1.0, i.getValue());
		i = l.get(1);
		assertEquals(7, i.getStart());
		assertEquals(8, i.getEnd());
		assertEquals(11.0, i.getValue());
		i = l.get(2);
		assertEquals(8, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(1.0, i.getValue());
		
		// Non-overlapping
		qi1 = new QuantifiedInterval((long)5, (long)10, 1.0);
		QuantifiedInterval qi4 = new QuantifiedInterval((long)11, (long)20, 10.0);
		l = qi1.merge(qi4);
		assertEquals(2, l.size());
		i = l.get(0);
		assertEquals(5, i.getStart());
		assertEquals(10, i.getEnd());
		assertEquals(1.0, i.getValue());
		i = l.get(1);
		assertEquals(11, i.getStart());
		assertEquals(20, i.getEnd());
		assertEquals(10.0, i.getValue());
	}
}
