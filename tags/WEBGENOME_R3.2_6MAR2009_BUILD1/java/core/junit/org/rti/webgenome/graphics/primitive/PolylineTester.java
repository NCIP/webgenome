/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $

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
