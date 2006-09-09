/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graphics/primitive/Text.java,v $
$Revision: 1.2 $
$Date: 2006-09-09 18:41:52 $

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


package org.rti.webcgh.graphics.primitive;

import java.awt.Color;

import org.rti.webcgh.units.HorizontalAlignment;

/**
 * Graphically rendered text
 */
public abstract class Text extends GraphicPrimitive {
	
	protected String value = "";
	protected int fontSize = 10;
	private HorizontalAlignment alignment = HorizontalAlignment.CENTERED;
	private int x = 0;
	private int y = 0;
	private int rotation = 0;
	
	
	/**
	 * Constructor
	 *
	 */
	public Text() {}
	
	
	/**
	 * Constructor.
	 * @param value Text value
	 * @param color Color of text
	 */
	public Text(String value, Color color) {
		super(color);
		this.value = value;
	}
	
	
	/**
	 * Constructor
	 * @param value Text value
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color
	 */
	public Text(String value, int x, int y, int fontSize, HorizontalAlignment alignment, Color color) {
		this(value, color);
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.alignment = alignment;
	}
	
	
	/**
	 * Rendered width of text
	 * @return Rendered width of text
	 */
	public abstract int renderedWidth();
	
	
	/**
	 * @return Font size
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @return Text value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param i Font size
	 */
	public void setFontSize(int i) {
		fontSize = i;
	}

	/**
	 * @param string Text value
	 */
	public void setValue(String string) {
		value = string;
	}

	/**
	 * @return Alignment of text to X and Y coordinates (i.e. START, MIDDLE, END)
	 */
	public HorizontalAlignment getAlignment() {
		return alignment;
	}

	/**
	 * @param i Alignment of text to X and Y coordinates (i.e. START, MIDDLE, END)
	 */
	public void setAlignment(HorizontalAlignment i) {
		alignment = i;
	}

	/**
	 * @return X-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return Y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param i X-coordinate
	 */
	public void setX(int i) {
		x = i;
	}

	/**
	 * @param i Y-coordinate
	 */
	public void setY(int i) {
		y = i;
	}

	/**
	 * @return Degrees of rotation
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * @param i Degrees of rotation
	 */
	public void setRotation(int i) {
		rotation = i;
	}

}
