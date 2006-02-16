/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/drawing/DrawingCanvas.java,v $
$Revision: 1.2 $
$Date: 2006-02-16 14:05:43 $

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
 * Canvas that supports basic drawing functionality.
 */
public interface DrawingCanvas {
	
	
	/**
	 * Add a graphic primitive
	 * @param element Primitive element
	 */
	public abstract void add(GraphicPrimitive element);
	
	
	/**
	 * Rotate canvas
	 * @param degrees Degrees of rotation
	 * @param x X-coordinate of rotation point
	 * @param y Y-coordinate of rotation point
	 */
	public abstract void rotate(int degrees, int x, int y);
	
	
	/**
	 * Add a graphic primitive
	 * @param graphic Graphic primitive
	 * @param overwriteCanvasProperties Overwrite global properties
	 * for graphic primitives set within canvas?
	 */
	public void add(GraphicPrimitive graphic, boolean overwriteCanvasProperties);
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas)
	 * @return A new drawing tile
	 */
	public abstract DrawingCanvas newTile();
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas)
	 * @param tileName Name of tile
	 * @return A new drawing tile
	 */
	public abstract DrawingCanvas newTile(String tileName);
	
	
	/**
	 * Add a canvas
	 * @param canvas A canvas
	 */
	public abstract void add(DrawingCanvas canvas);
	
	
	/**
	 * Add a canvas at specified location
	 * @param canvas A canvas
	 * @param x X-coordinate of insertion point
	 * @param y Y-coordinate of insertion point
	 */
	public abstract void add(DrawingCanvas canvas, int x, int y);
	
	
	/**
	 * Set global line width property that applies to all lines
	 * withiin this canvas
	 * @param width Line width
	 */
	public abstract void setLineWidth(int width);
	
	
	/**
	 * Set id of canvas
	 * @param id Canvas id
	 */
	public abstract void setId(String id);
	
	
	/**
	 * Return new text element
	 * @param value String value of text
	 * @param x X-coordinate of text
	 * @param y Y-coordinate of text
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color of text
	 * @return New text element
	 */
	public abstract GraphicText newGraphicText
	(
		String value, int x, int y, int fontSize, HorizontalAlignment alignment, Color color
	);
	
	
	/**
	 * Set global property for data set names.  This is a list of all
	 * data sets represented in graphic
	 * @param names Data set names
	 */
	public abstract void setDataSetNames(String[] names);
	
	
	/**
	 * Rendered with of given text
	 * @param text Text element
	 * @param fontSize Font size
	 * @return Rendered with of given text
	 */
	public abstract int renderedWidth(String text, int fontSize);
	
	
	/**
	 * Set width of canvas
	 * @param width Width in pixels
	 */
	public abstract void setWidth(int width);
	
	
	/**
	 * Set height of canvas
	 * @param height Height in pixels
	 */
	public abstract void setHeight(int height);
	
	
	/**
	 * Set artibrary attribute
	 * @param name Attribute name
	 * @param value Attribute value
	 */
	public abstract void setAttribute(String name, String value);
	
	/**
	 * Add a response to an event
	 * @param event An event
	 * @param response A response
	 */
	public void addGraphicEventResponse(GraphicEvent event, String response);
}
