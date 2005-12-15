/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/drawing/GraphicLine.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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


package org.rti.webcgh.drawing;

import java.awt.Color;

/**
 * Represents a line
 */
public class GraphicLine extends GraphicPrimitive {
	
	
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	private int width = 2;
	
	
	/**
	 * Constructor
	 *
	 */
	public GraphicLine() {}
	
	
	/**
	 * Constructor
	 * @param x1 X-coordinate of first end
	 * @param y1 Y-coordinate of first end
	 * @param x2 X-coordinate of second end
	 * @param y2 X-coordinate of second end
	 * @param width Width of line
	 * @param color Color of line
	 */
	public GraphicLine(int x1, int y1, int x2, int y2, int width, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
		this.color = color;
	}

	/**
	 * @return Width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return X-coordinate of first endpoint
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * @return X-coordinate of second endpoint
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * @return Y-coordinate of first endpoint
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * @return Y-coordinate of second endpoint
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * @param i Width
	 */
	public void setWidth(int i) {
		width = i;
	}

	/**
	 * @param i X-coordinate of first endpoint
	 */
	public void setX1(int i) {
		x1 = i;
	}

	/**
	 * @param i X-coordinate of second endpoint
	 */
	public void setX2(int i) {
		x2 = i;
	}

	/**
	 * @param i Y-coordinate of first endpoint
	 */
	public void setY1(int i) {
		y1 = i;
	}

	/**
	 * @param i Y-coordinate of second endpoint
	 */
	public void setY2(int i) {
		y2 = i;
	}

}