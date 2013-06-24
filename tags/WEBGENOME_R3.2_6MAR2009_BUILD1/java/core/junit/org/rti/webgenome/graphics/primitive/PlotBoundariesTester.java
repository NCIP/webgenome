/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.graphics.primitive;

import org.rti.webgenome.graphics.DataPoint;
import org.rti.webgenome.graphics.PlotBoundaries;

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
