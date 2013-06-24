/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.awt.Point;

import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.primitive.Polyline;

import junit.framework.TestCase;

/**
 * Tester for <code>Polyline</code> class.
 */
public final class PolylineTester extends TestCase {
	
	/** A test point. */
	private Point p1 = null;
	
	/** A test point. */
	private Point p2 = null;
	
	/** A test point. */
	private Point p3 = null;
	
	/** A test point. */
	private Point p4 = null;
	
	
	/**
	 * Setup method.
	 */
	public void setUp() {
		p1 = new Point(5, 5);
		p2 = new Point(10, 10);
		p3 = new Point(15, 15);
		p4 = new Point(20, 20);
	}
	
	
	/**
	 * Test add point.
	 */
	public void testAddPoint() {
		Polyline poly = new Polyline(Color.BLACK);
		poly.add(p1);
		poly.add(p2);
		poly.add(p3);
		assertTrue(poly.getPoints().size() == 3);
	}
	
	
	/**
	 * Test add line.
	 */
	public void testAddLine() {
		Line l1 = new Line(p1, p2);
		Line l2 = new Line(p2, p3);
		Line l3 = new Line(p3, p4);
		Polyline poly1 = new Polyline(Color.BLACK);
		poly1.add(l1);
		poly1.add(l2);
		assertTrue(poly1.getPoints().size() == 3);
		Polyline poly2 = new Polyline(Color.BLACK);
		poly2.add(l1);
		poly2.add(l3);
		assertTrue(poly2.getPoints().size() == 4);
	}
	

}
