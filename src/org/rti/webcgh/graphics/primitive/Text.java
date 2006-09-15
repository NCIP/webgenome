/*
$Revision: 1.3 $
$Date: 2006-09-15 21:21:01 $

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


package org.rti.webcgh.graphics.primitive;

import java.awt.Color;

import org.rti.webcgh.units.HorizontalAlignment;

/**
 * Represents text to be rendered graphically.
 */
public abstract class Text extends GraphicPrimitive {
	
	// ========================
	//      Attributes
	// ========================
	
	/** Text string to render. */
	private String value = "";
	
	/** Font size. */
	private int fontSize = 10;
	
	/**
	 * Horizontal alignment relative to the reference point specified
	 * by <code>x</code> and <code>y</code>.
	 */
	private HorizontalAlignment alignment = HorizontalAlignment.CENTERED;
	
	/** X-coordinate for reference point of text. */
	private int x = 0;
	
	/** Y-coordinate for reference point of text. */
	private int y = 0;
	
	/** Rotation to apply on text in radians. */
	private double rotation = 0.0;
	
	
	
	
	// ========================
	//     Getters/setters
	// ========================
	
	/**
	 * Get font size.
	 * @return Font size
	 */
	public final int getFontSize() {
		return fontSize;
	}

	/**
	 * Get text value to render.
	 * @return Text value
	 */
	public final String getValue() {
		return value;
	}

	/**
	 * Set font size.
	 * @param i Font size Font size
	 */
	public final void setFontSize(final int i) {
		fontSize = i;
	}

	/**
	 * Set text value to render.
	 * @param string Text value to render
	 */
	public final void setValue(final String string) {
		value = string;
	}

	/**
	 * Get horizontal alignment relative to the reference point specified
	 * by <code>x</code> and <code>y</code>.
	 * @return Alignment of text to X and Y coordinates
	 * (i.e. START, MIDDLE, END)
	 */
	public final HorizontalAlignment getAlignment() {
		return alignment;
	}

	/**
	 * Set horizontal alignment relative to the reference point specified
	 * by <code>x</code> and <code>y</code>.
	 * @param i Alignment of text to X and Y coordinates
	 * (i.e. START, MIDDLE, END)
	 */
	public final void setAlignment(final HorizontalAlignment i) {
		alignment = i;
	}

	/**
	 * Get x-coordinate of reference point.
	 * @return X-coordinate of reference point
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Get y-coordinate of reference point.
	 * @return Y-coordinate of reference point
	 */
	public final int getY() {
		return y;
	}

	/**
	 * Set X-coordinate of reference point.
	 * @param i X-coordinate of reference poing
	 */
	public final void setX(final int i) {
		x = i;
	}

	/**
	 * Set Y-coordinate of reference point.
	 * @param i Y-coordinate of reference point
	 */
	public final void setY(final int i) {
		y = i;
	}

	/**
	 * Get rotation of text.
	 * @return Rotation in radians
	 */
	public final double getRotation() {
		return rotation;
	}

	/**
	 * Set rotation of text.
	 * @param i Rotation in radians
	 */
	public final void setRotation(final double i) {
		rotation = i;
	}
	
	
	// ==================================
	//        Constructors
	// ==================================
	
	/**
	 * Constructor.
	 *
	 */
	public Text() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param value Text value
	 * @param color Color of text
	 */
	public Text(final String value, final Color color) {
		super(color);
		this.value = value;
	}
	
	
	/**
	 * Constructor.
	 * @param value Text value
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color
	 */
	public Text(final String value, final int x, final int y,
			final int fontSize, final HorizontalAlignment alignment,
			final Color color) {
		this(value, color);
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.alignment = alignment;
	}
	
	// =========================
	//    Abstract methods
	// =========================
	
	/**
	 * Rendered width of text.
	 * @return Rendered width of text
	 */
	public abstract int renderedWidth();
}
