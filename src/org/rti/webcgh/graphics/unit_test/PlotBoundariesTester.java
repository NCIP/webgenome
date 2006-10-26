/*
$Revision: 1.1 $
$Date: 2006-10-26 03:50:16 $

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

package org.rti.webcgh.graphics.unit_test;

import org.rti.webcgh.graphics.DataPoint;
import org.rti.webcgh.graphics.PlotBoundaries;

import junit.framework.TestCase;

/**
 * Tester for <code>PlotBoundaries</code>.
 * @author dhall
 *
 */
public final class PlotBoundariesTester extends TestCase {
	
	// Test objects
	
	/** Test plot boundaries. */
	private PlotBoundaries pb = null;
	
	/** Left. */
	private DataPoint l = null;
	
	/** Left and above. */
	private DataPoint la = null;
	
	/** Left and below. */
	private DataPoint lb = null;
	
	/** Above. */
	private DataPoint a = null;
	
	/** Below. */
	private DataPoint b = null;
	
	/** Right. */
	private DataPoint r = null;
	
	/** Right and above. */
	private DataPoint ra = null;
	
	/** Right and below. */
	private DataPoint rb = null;
	
	/** First point inside. */
	private DataPoint i1 = null;
	
	/** Second point inside. */
	private DataPoint i2 = null;
	
	// =========================
	//      Helper methods
	// =========================
	
	/**
	 * Setup.
	 */
	public void setUp() {
		this.pb = new PlotBoundaries(
				new DataPoint(100, 100), new DataPoint(200, 200));
		this.a = new DataPoint(145, 250);
		this.b = new DataPoint(130, 50);
		this.l = new DataPoint(50, 150);
		this.r = new DataPoint(220, 160);
		this.la = new DataPoint(55, 210);
		this.lb = new DataPoint(20, 50);
		this.ra = new DataPoint(230, 300);
		this.rb = new DataPoint(300, 25);
		this.i1 = new DataPoint(150, 150);
		this.i2 = new DataPoint(160, 120);
	}
	
	
	// =========================
	//     Unit tests
	// =========================
	
	/**
	 * Test atLeastPartelyOnLine() method.
	 */
	public void testAtLeastPartlyOnPlot() {
		
		// Completely outside and not overlapping
		assertFalse(this.pb.atLeastPartlyOnPlot(this.l, this.la));
		assertFalse(this.pb.atLeastPartlyOnPlot(this.r, this.ra));
		assertFalse(this.pb.atLeastPartlyOnPlot(this.a, this.ra));
		assertFalse(this.pb.atLeastPartlyOnPlot(this.b, this.rb));
		
		// Completely inside
		assertTrue(this.pb.atLeastPartlyOnPlot(this.i1, this.i2));
		
		// Out to in
		assertTrue(this.pb.atLeastPartlyOnPlot(this.l, this.i1));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.la, this.i1));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.lb, this.i1));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.a, this.i1));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.b, this.i1));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.i1, this.r));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.i1, this.ra));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.i1, this.rb));
		
		// Completely outside and overlapping
		assertTrue(this.pb.atLeastPartlyOnPlot(this.l, this.r));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.b, this.a));
		assertTrue(this.pb.atLeastPartlyOnPlot(this.ra, this.b));
	}
	
	
	/**
	 * Test truncateToFitOnPlot() method.
	 */
	public void testTruncateToFitOnPlot() {
		
		// Illegal arguments
		try {
			this.pb.truncateToFitOnPlot(this.l, this.la);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			this.pb.truncateToFitOnPlot(this.a, this.la);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			this.pb.truncateToFitOnPlot(this.r, this.ra);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			this.pb.truncateToFitOnPlot(this.b, this.lb);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			this.pb.truncateToFitOnPlot(this.i1, this.i2);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		
		// Left to inside
		DataPoint p1 = new DataPoint(this.l);
		this.pb.truncateToFitOnPlot(p1, this.i1);
		assertEquals(100.0, p1.getValue1());
		assertTrue(p1.getValue2() <= 200.0);
		assertTrue(p1.getValue2() >= 100.0);
		assertEquals(150.0, this.i1.getValue1());
		assertEquals(150.0, this.i1.getValue2());
		
		// Right to inside
		p1 = new DataPoint(this.r);
		this.pb.truncateToFitOnPlot(this.i1, p1);
		assertEquals(200.0, p1.getValue1());
		assertTrue(p1.getValue2() <= 200.0);
		assertTrue(p1.getValue2() >= 100.0);
		assertEquals(150.0, this.i1.getValue1());
		assertEquals(150.0, this.i1.getValue2());
		
		// Top to inside
		p1 = new DataPoint(this.a);
		this.pb.truncateToFitOnPlot(p1, this.i1);
		assertEquals(200.0, p1.getValue2());
		assertTrue(p1.getValue1() <= 200.0);
		assertTrue(p1.getValue1() >= 100.0);
		assertEquals(150.0, this.i1.getValue1());
		assertEquals(150.0, this.i1.getValue2());
		
		// Bottom to inside
		p1 = new DataPoint(this.b);
		this.pb.truncateToFitOnPlot(p1, this.i1);
		assertEquals(100.0, p1.getValue2());
		assertTrue(p1.getValue1() <= 200.0);
		assertTrue(p1.getValue1() >= 100.0);
		assertEquals(150.0, this.i1.getValue1());
		assertEquals(150.0, this.i1.getValue2());
		
		// Across entire plot left to right
		p1 = new DataPoint(this.l);
		DataPoint p2 = new DataPoint(this.r);
		this.pb.truncateToFitOnPlot(p1, p2);
		assertEquals(100.0, p1.getValue1());
		assertTrue(p1.getValue2() <= 200.0);
		assertTrue(p1.getValue2() >= 100.0);
		assertEquals(200.0, p2.getValue1());
		assertTrue(p2.getValue2() <= 200.0);
		assertTrue(p2.getValue2() >= 100.0);
		
		// Across entire plot top to bottom
		p1 = new DataPoint(this.b);
		p2 = new DataPoint(this.a);
		this.pb.truncateToFitOnPlot(p1, p2);
		assertEquals(100.0, p1.getValue2());
		assertTrue(p1.getValue1() <= 200.0);
		assertTrue(p1.getValue1() >= 100.0);
		assertEquals(200.0, p2.getValue2());
		assertTrue(p2.getValue1() <= 200.0);
		assertTrue(p2.getValue1() >= 100.0);
		
		// Across a corner
		p1 = new DataPoint(90, 180);
		p2 = new DataPoint(150, 210);
		this.pb.truncateToFitOnPlot(p1, p2);
		assertEquals(100.0, p1.getValue1());
		assertTrue(p1.getValue2() <= 200.0);
		assertTrue(p1.getValue2() >= 100.0);
		assertEquals(200.0, p2.getValue2());
		assertTrue(p2.getValue1() <= 200.0);
		assertTrue(p2.getValue1() >= 100.0);
	}

}
