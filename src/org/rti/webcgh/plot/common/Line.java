/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/plot/common/Line.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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

package org.rti.webcgh.plot.common;

import java.awt.Point;
import java.awt.geom.Line2D;


/**
 * Adds extended functionality to <code>Line2D</code>
 */
public class Line extends Line2D.Double {

	/**
	 * Constructor
	 */
	public Line() {
		super();
	}
	
	
	/**
	 * Constructor
	 * @param x1 X-coordinate of first endpoint
	 * @param y1 Y-coordinate of first endpoint
	 * @param x2 X-coordinate of second endpoint
	 * @param y2 Y-coordinate of second endpoint
	 */
	public Line(double x1, double y1, double x2, double y2) {
		this.setLine(x1, y1, x2, y2);
	}
	
	
	/**
	 * Constructor
	 * @param p1 First endpoint
	 * @param p2 Second endpoint
	 */
	public Line(Point p1, Point p2) {
		this.setLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(),
			(int)p2.getY());
	}
	
	
	/**
	 * Slope of line
	 * @return Slope
	 */
	public double slope() {
		return
			(this.getY2() - this.getY1()) /
			(this.getX2() - this.getX1());
	}
	
	
	/**
	 * Is line horizontal?
	 * @return T/F
	 */
	public boolean isHorizontal() {
		return this.getY1() == this.getY2();
	}
	
	
	/**
	 * Is line vertical?
	 * @return T/F
	 */
	public boolean isVertical() {
		return this.getX1() == this.getX2();
	}
	
	
	/**
	 * Return intercept point.  This <code>Line</code> must be
	 * horizontal or vertical
	 * @param line A line
	 * @return Intercepting point.  If this <code>Line</code>
	 * is not horizontal or vertical, null is returned
	 */
	public Point intercept(Line line) {
		Point p = null;
		if (this.isHorizontal()) {
			int x = (int)(line.getX1() +
				(this.getY1() - line.getY1()) / line.slope());
			int y = (int)this.getY1();
			p = new Point(x, y);
		} else if (this.isVertical()) {
			int x = (int)this.getX1();
			int y = (int)(line.getY1() + 
				line.slope() * (this.getX1() - line.getX1()));
			p = new Point(x, y);
		}
		return p;
	}
	
	
	/**
	 * Set first endpoint
	 * @param p1 Endpoint
	 */
	public void setP1(Point p1) {
		this.setLine(p1.getX(), p1.getY(), this.getX2(), this.getY2());
	}
	
	
	/**
	 * Set second endpoint
	 * @param p2 Endpoint
	 */
	public void setP2(Point p2) {
		this.setLine(this.getX1(), this.getY1(), p2.getX(), p2.getY());
	}
}
