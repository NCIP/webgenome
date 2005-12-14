/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/drawing/SvgGraphicText.java,v $
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


package org.rti.webcgh.drawing;

import java.awt.Color;

/**
 * Implementation of GraphicText interface for
 * an SVG canvas
 */
public class SvgGraphicText extends GraphicText {
	
	

	/**
	 * 
	 */
	public SvgGraphicText() {
		super();
	}

	/**
	 * @param value
	 */
	public SvgGraphicText(String value) {
		super(value);
	}
	

	/**
	 * @param value
	 * @param x
	 * @param y
	 * @param fontSize
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color
	 */
	public SvgGraphicText(
		String value,
		int x,
		int y,
		int fontSize,
		HorizontalAlignment alignment,
		Color color) {
		super(value, x, y, fontSize, alignment, color);
	}
	
	

	/**
	 * Get width of rendered text
	 * @return Width of rendered text
	 */
	public int renderedWidth() {
		int len = 0;
		if (value != null) {
			int count = 0;
			char lastChar = 'A';
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				if (!(Character.isWhitespace(c) && Character.isWhitespace(lastChar)))
					count++;
				lastChar = c;
			}
			len = (int)((double)count * (double)fontSize * 0.6);
		}
		return len;
	}

}
