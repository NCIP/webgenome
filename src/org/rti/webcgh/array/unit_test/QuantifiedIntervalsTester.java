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
import java.util.Collection;

import junit.framework.TestCase;

import org.rti.webcgh.array.QuantifiedInterval;
import org.rti.webcgh.array.QuantifiedIntervals;


/**
 * Tester for <code>QuantifiedIntervals</code>
 */
public class QuantifiedIntervalsTester extends TestCase {
	
	
	public void testBothEmpty() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.merge(qi2);
		assertEquals(0, qi1.getIntervals().size());
	}
	
	
	public void testFirstEmpty() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi1.merge(qi2);
		assertEquals(2, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
	}
	
	
	public void testSecondEmpty() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi1.merge(qi2);
		assertEquals(2, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
	}
	
	
	public void testFirstAllLeft() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi2.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(4, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 25);
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testFirstAllRight() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi1.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi1.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(4, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 25);
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testFirstInCenter() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi1.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(4, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 25);
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testSecondInCenter() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi2.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi1.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(4, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 25);
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testInterlaced() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)15, (long)20, 1.0));
		qi1.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(4, qi1.getIntervals().size());
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 25);
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testFirstAllLeftWithOverlap() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)15, (long)22, 1.0));
		qi2.add(new QuantifiedInterval((long)18, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(5, qi1.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		
		// Second interval
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		assertEquals(i.getEnd(), 18);
		assertEquals(i.getValue(), 1.0);
		
		// Third interval
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 18);
		assertEquals(i.getEnd(), 22);
		assertEquals(i.getValue(), 2.0);
		
		// Fourth interval
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 22);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 1.0);
		
		// Fifth interval
		i = qi1.getIntervals().get(4);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testFirstAllRightWithOverlap() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)15, (long)22, 1.0));
		qi1.add(new QuantifiedInterval((long)18, (long)30, 1.0));
		qi1.add(new QuantifiedInterval((long)35, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(5, qi1.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		
		// Second interval
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 15);
		assertEquals(i.getEnd(), 18);
		assertEquals(i.getValue(), 1.0);
		
		// Third interval
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 18);
		assertEquals(i.getEnd(), 22);
		assertEquals(i.getValue(), 2.0);
		
		// Fourth interval
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 22);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 1.0);
		
		// Fifth interval
		i = qi1.getIntervals().get(4);
		assertEquals(i.getStart(), 35);
	}
	
	
	public void testFirstCenterWith2Overlaps() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)8, (long)20, 1.0));
		qi1.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)28, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(6, qi1.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		assertEquals(i.getEnd(), 8);
		assertEquals(i.getValue(), 1.0);
		
		// Second interval
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 8);
		assertEquals(i.getEnd(), 10);
		assertEquals(i.getValue(), 2.0);
		
		// Third interval
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 10);
		assertEquals(i.getEnd(), 20);
		assertEquals(i.getValue(), 1.0);
		
		// Fourth interval
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 25);
		assertEquals(i.getEnd(), 28);
		assertEquals(i.getValue(), 1.0);
		
		// Fifth interval
		i = qi1.getIntervals().get(4);
		assertEquals(i.getStart(), 28);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 2.0);
		
		// Sixth interval
		i = qi1.getIntervals().get(5);
		assertEquals(i.getStart(), 30);
		assertEquals(i.getEnd(), 40);
		assertEquals(i.getValue(), 1.0);
	}
	
	
	public void testSecondCenterWith2Overlaps() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)8, (long)20, 1.0));
		qi2.add(new QuantifiedInterval((long)25, (long)30, 1.0));
		qi1.add(new QuantifiedInterval((long)28, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(6, qi1.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		assertEquals(i.getEnd(), 8);
		assertEquals(i.getValue(), 1.0);
		
		// Second interval
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 8);
		assertEquals(i.getEnd(), 10);
		assertEquals(i.getValue(), 2.0);
		
		// Third interval
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 10);
		assertEquals(i.getEnd(), 20);
		assertEquals(i.getValue(), 1.0);
		
		// Fourth interval
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 25);
		assertEquals(i.getEnd(), 28);
		assertEquals(i.getValue(), 1.0);
		
		// Fifth interval
		i = qi1.getIntervals().get(4);
		assertEquals(i.getStart(), 28);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 2.0);
		
		// Sixth interval
		i = qi1.getIntervals().get(5);
		assertEquals(i.getStart(), 30);
		assertEquals(i.getEnd(), 40);
		assertEquals(i.getValue(), 1.0);
	}
	
	
	public void testInterlacedWithOverlaps() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)8, (long)20, 1.0));
		qi1.add(new QuantifiedInterval((long)18, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)28, (long)40, 1.0));
		qi1.merge(qi2);
		assertEquals(7, qi1.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		assertEquals(i.getEnd(), 8);
		assertEquals(i.getValue(), 1.0);
		
		// Second interval
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 8);
		assertEquals(i.getEnd(), 10);
		assertEquals(i.getValue(), 2.0);
		
		// Third interval
		i = qi1.getIntervals().get(2);
		assertEquals(i.getStart(), 10);
		assertEquals(i.getEnd(), 18);
		assertEquals(i.getValue(), 1.0);
		
		// Fourth interval
		i = qi1.getIntervals().get(3);
		assertEquals(i.getStart(), 18);
		assertEquals(i.getEnd(), 20);
		assertEquals(i.getValue(), 2.0);
		
		// Fifth interval
		i = qi1.getIntervals().get(4);
		assertEquals(i.getStart(), 20);
		assertEquals(i.getEnd(), 28);
		assertEquals(i.getValue(), 1.0);
		
		// Sixth interval
		i = qi1.getIntervals().get(5);
		assertEquals(i.getStart(), 28);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 2.0);
		
		// Seventy interval
		i = qi1.getIntervals().get(6);
		assertEquals(i.getStart(), 30);
		assertEquals(i.getEnd(), 40);
		assertEquals(i.getValue(), 1.0);
	}
	
	
	public void testEquals() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)20, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)20, (long)30, 1.0));
		qi1.merge(qi2);
		assertEquals(2, qi1.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi1.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		assertEquals(i.getEnd(), 10);
		assertEquals(i.getValue(), 2.0);
		
		// Second interval
		i = qi1.getIntervals().get(1);
		assertEquals(i.getStart(), 20);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 2.0);
	}
	
	
	public void testStaticMergeAmongEquals() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		QuantifiedIntervals qi3 = new QuantifiedIntervals();
		Collection<QuantifiedIntervals> intervals = new ArrayList<QuantifiedIntervals>();
		intervals.add(qi1);
		intervals.add(qi2);
		intervals.add(qi3);
		qi1.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi1.add(new QuantifiedInterval((long)20, (long)30, 1.0));
		qi2.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi2.add(new QuantifiedInterval((long)20, (long)30, 1.0));
		qi3.add(new QuantifiedInterval((long)5, (long)10, 1.0));
		qi3.add(new QuantifiedInterval((long)20, (long)30, 1.0));
		QuantifiedIntervals qi4 = QuantifiedIntervals.merge(intervals);
		
		assertEquals(2, qi4.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi4.getIntervals().get(0);
		assertEquals(i.getStart(), 5);
		assertEquals(i.getEnd(), 10);
		assertEquals(i.getValue(), 3.0);
		
		// Second interval
		i = qi4.getIntervals().get(1);
		assertEquals(i.getStart(), 20);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 3.0);
	}
	
	
	public void testStaticMergeAmongOverlapping() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		QuantifiedIntervals qi3 = new QuantifiedIntervals();
		Collection<QuantifiedIntervals> intervals = new ArrayList<QuantifiedIntervals>();
		intervals.add(qi1);
		intervals.add(qi2);
		intervals.add(qi3);
		qi1.add(new QuantifiedInterval((long)30, (long)40, 1.0));
		qi2.add(new QuantifiedInterval((long)20, (long)50, 1.0));
		qi3.add(new QuantifiedInterval((long)10, (long)60, 1.0));
		QuantifiedIntervals qi4 = QuantifiedIntervals.merge(intervals);
		
		assertEquals(5, qi4.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi4.getIntervals().get(0);
		assertEquals(i.getStart(), 10);
		assertEquals(i.getEnd(), 20);
		assertEquals(i.getValue(), 1.0);
		
		// Second interval
		i = qi4.getIntervals().get(1);
		assertEquals(i.getStart(), 20);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 2.0);
		
		// Third interval
		i = qi4.getIntervals().get(2);
		assertEquals(i.getStart(), 30);
		assertEquals(i.getEnd(), 40);
		assertEquals(i.getValue(), 3.0);
		
		// Fourth interval
		i = qi4.getIntervals().get(3);
		assertEquals(i.getStart(), 40);
		assertEquals(i.getEnd(), 50);
		assertEquals(i.getValue(), 2.0);
		
		// Fifth interval
		i = qi4.getIntervals().get(4);
		assertEquals(i.getStart(), 50);
		assertEquals(i.getEnd(), 60);
		assertEquals(i.getValue(), 1.0);
	}
	
	
	public void testStaticMergeAmongOverlappingReversed() {
		QuantifiedIntervals qi1 = new QuantifiedIntervals();
		QuantifiedIntervals qi2 = new QuantifiedIntervals();
		QuantifiedIntervals qi3 = new QuantifiedIntervals();
		Collection<QuantifiedIntervals> intervals = new ArrayList<QuantifiedIntervals>();
		intervals.add(qi1);
		intervals.add(qi2);
		intervals.add(qi3);
		qi3.add(new QuantifiedInterval((long)30, (long)40, 1.0));
		qi2.add(new QuantifiedInterval((long)20, (long)50, 1.0));
		qi1.add(new QuantifiedInterval((long)10, (long)60, 1.0));
		QuantifiedIntervals qi4 = QuantifiedIntervals.merge(intervals);
		
		assertEquals(5, qi4.getIntervals().size());
		
		// First interval
		QuantifiedInterval i = qi4.getIntervals().get(0);
		assertEquals(i.getStart(), 10);
		assertEquals(i.getEnd(), 20);
		assertEquals(i.getValue(), 1.0);
		
		// Second interval
		i = qi4.getIntervals().get(1);
		assertEquals(i.getStart(), 20);
		assertEquals(i.getEnd(), 30);
		assertEquals(i.getValue(), 2.0);
		
		// Third interval
		i = qi4.getIntervals().get(2);
		assertEquals(i.getStart(), 30);
		assertEquals(i.getEnd(), 40);
		assertEquals(i.getValue(), 3.0);
		
		// Fourth interval
		i = qi4.getIntervals().get(3);
		assertEquals(i.getStart(), 40);
		assertEquals(i.getEnd(), 50);
		assertEquals(i.getValue(), 2.0);
		
		// Fifth interval
		i = qi4.getIntervals().get(4);
		assertEquals(i.getStart(), 50);
		assertEquals(i.getEnd(), 60);
		assertEquals(i.getValue(), 1.0);
	}
}
