/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2006-09-09 18:41:52 $

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

package org.rti.webcgh.graphics.widget;

import java.awt.Color;
import java.awt.Point;

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.event.GraphicEvent;
import org.rti.webcgh.graphics.primitive.Rectangle;

/**
 * Widget that provides a background for
 * other widgets.
 */
public class Background implements PlotElement {
    
    
    // ==============================
    //     Attributes
    // ==============================
    
	/** Width of background in pixels. */
    private final int width;
    
    /** Height of background in pixels. */
    private final int height;
    
    /** Mimimum X-coordinate. */
    private int minX = 0;
    
    /** Minimum Y-coordinate. */
    private int minY = 0;
    
    /** Color of background. */
    private final Color color;
    
    
    // ==============================
    //    Constructors
    // ==============================
    
    /**
     * Constructor.
     * @param width Width in pixels
     * @param height Height in pixels
     * @param color Color
     */
    public Background(final int width, final int height,
    		final Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    
    // =======================================
    //    Methods in PlotElement interface
    // =======================================
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    }
    
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
        Rectangle rect = new Rectangle(this.minX,
        		this.minY, width, height, this.color);
        rect.addGraphicEventResponse(
        		GraphicEvent.mouseMoveEvent, "hideToolTip()");
        canvas.add(rect);
    }
    
    
    /**
     * Point at top left used to align with other plot
     * elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other
     * plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
        return new Point(this.minX, this.minY + height);
    }
    
    
    /**
     * Point at top right used to align with other plot
     * elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
        return new Point(this.minX + width, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other
     * plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
        return new Point(this.minX + width, this.minY + height);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public final int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public final int height() {
        return this.height;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
        return new Point(this.minX, this.minY);
    }
}
