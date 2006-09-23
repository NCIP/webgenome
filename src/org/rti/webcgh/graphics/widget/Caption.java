/*
$Revision: 1.3 $
$Date: 2006-09-23 05:02:23 $

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.graphics.primitive.Text;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;

/**
 * Represents a text caption in a plot.
 */
public class Caption implements PlotElement {
	
	
	// ===============================
	//       Attributes
	// ===============================
	
	/** Drawing canvas caption will be rendered to. */
	private final DrawingCanvas canvas;
	
	/** Font size. */
	private int fontSize = 16;
	
	/** Text color. */
	private Color color = Color.BLACK;
	
	/** Horizontal alignment of the lines of text, if multiline. */
	private HorizontalAlignment textAlignment =
		HorizontalAlignment.LEFT_JUSTIFIED;
	
	/** Padding between text lines. */
	private int padding = 5;
	
	/** The text. */
	private final String text;
	
	/** URL associated with text. */
	private final URL url;
	
	/** Orientation of text. */
	private final Orientation orientation;
	
	/** Place line breaks between tokens? */
	private final boolean breakLinesBetweenTokens;
	
	/** Minimum X-coordinate in caption. */
	private int minX = 0;
	
	/** Maximum X-coordinate in caption. */
	private int maxX = 0;
	
	/** Minimum Y-coordinate in caption. */
	private int minY = 0;
	
	/** Maximum Y-coordinate in caption. */
	private int maxY = 0;
	
	
	// ====================================
	//      Getters/setters
	// ====================================
	
	/**
	 * @param color The color to set.
	 */
	public final void setColor(final Color color) {
		this.color = color;
	}
	
	
	/**
	 * @param fontSize The fontSize to set.
	 */
	public final void setFontSize(final int fontSize) {
		this.fontSize = fontSize;
	}
	
	
	/**
	 * @param textAlignment The textAlignment to set.
	 */
	public final void setTextAlignment(
			final HorizontalAlignment textAlignment) {
		this.textAlignment = textAlignment;
	}
	
	/**
	 * @param padding The padding to set.
	 */
	public final void setPadding(final int padding) {
		this.padding = padding;
	}
	
	
	// ====================================
	//      Constructors
	// ====================================
	
	/**
	 * Constructor.
	 * @param text Text
	 * @param url URL
	 * @param orientation Orientation
	 * @param breakLinesBetweenTokens Put line breaks between tokens
	 * @param canvas Canvas caption will be rendered to
	 */
	public Caption(final String text, final URL url,
			final Orientation orientation,
			final boolean breakLinesBetweenTokens, final DrawingCanvas canvas) {
		this.text = text;
		this.url = url;
		this.orientation = orientation;
		this.breakLinesBetweenTokens = breakLinesBetweenTokens;
		this.canvas = canvas;
		this.setMinAndMaxCoordinates();
	}
	
	
	/**
	 * Constructor.
	 * @param text Text
	 * @param orientation Orientation
	 * @param breakLinesBetweenTokens Put line breaks between tokens
	 * @param canvas Canvas caption will be rendered to
	 */
	public Caption(final String text, final Orientation orientation, 
			final boolean breakLinesBetweenTokens, final DrawingCanvas canvas) {
		this.text = text;
		this.url = null;
		this.orientation = orientation;
		this.breakLinesBetweenTokens = breakLinesBetweenTokens;
		this.canvas = canvas;
		this.setMinAndMaxCoordinates();
	}
	
	
	/**
	 * Set min and max X and Y coordinates.
	 */
	private void setMinAndMaxCoordinates() {
    	if (this.text == null || this.text.length() < 1) {
    		return;
    	}
    	
    	// Break text up into separate lines, if necessary
    	List<String> textLines = new ArrayList<String>();
    	if (this.breakLinesBetweenTokens) {
    		StringTokenizer tok = new StringTokenizer(this.text);
    		while (tok.hasMoreTokens()) {
    			textLines.add(tok.nextToken());
    		}
    	} else {
    		textLines.add(this.text);
    	}
    	
    	// Find width of longest line
    	int maxWidth = 0;
    	for (String line : textLines) {
    	    int candidateMax = this.canvas.renderedWidth(line, this.fontSize);
    	    if (candidateMax > maxWidth) {
    	    	maxWidth = candidateMax;
    	    }
    	}
    	this.minX = 0;
    	this.maxY = 0;
    	this.maxX = maxWidth;
    	this.maxY = textLines.size() * this.fontSize
    		+ (textLines.size() - 1) * this.padding;
	}
	
	
	// ======================================
	//     Methods in PlotElement interface
	// ======================================
	
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
    	if (this.text == null || this.text.length() < 1) {
    		return;
    	}
    	
    	// Break text up into separate lines, if necessary
    	List<String> textLines = new ArrayList<String>();
    	if (this.breakLinesBetweenTokens) {
    		StringTokenizer tok = new StringTokenizer(this.text);
    		while (tok.hasMoreTokens()) {
    			textLines.add(tok.nextToken());
    		}
    	} else {
    		textLines.add(this.text);
    	}
    	
    	// Initialize x and y coordinates
    	int x = this.minX;
    	int y = this.minY;
    	
    	// Paint text
    	for (String line : textLines) {
    	    
    	    // Draw line
    	    this.paintLine(line, x, y, canvas);
    	    
    	    // Increment x and y
    	    x = this.nextX(x, line, canvas);
    	    y = this.nextY(y, line, canvas);
    	}
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
    	return new Point(this.minX, this.maxY);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
    	return new Point(this.maxX, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
    	return new Point(this.maxX, this.maxY);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public final int width() {
    	return maxX - minX;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public final int height() {
    	return maxY - minY;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
    	return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.maxX += deltaX;
    	this.maxY += deltaY;
    	this.minY += deltaY;
    }
    
    
    // ==========================================================
    //         Private methods
    // ==========================================================
    
    /**
     * Compute starting X-coordinate for a line of text.
     * @param x Referece X-coordinate for line
     * @param line Line of text
     * @return Starting X-coordinate for a line of text.
     */
    private int startX(final int x, final String line) {
        int startX = 0;
        int width = this.canvas.renderedWidth(line, this.fontSize);
        if (this.orientation == Orientation.HORIZONTAL) {
            if (this.textAlignment == HorizontalAlignment.LEFT_JUSTIFIED) {
                startX = this.minX;
            } else if (this.textAlignment == HorizontalAlignment.CENTERED) {
                startX = (this.maxX + this.minX) / 2 - width / 2;
            } else if (this.textAlignment
            		== HorizontalAlignment.RIGHT_JUSTIFIED) {
                startX = this.maxX - width;
            } else {
                throw new IllegalArgumentException("Illegal text alignment");
            }
        } else if (this.orientation == Orientation.VERTICAL) {
            startX = x - this.fontSize;
        }
        return startX;
    }
    
    
    /**
     * Compute starting Y-coordinate for a line of text.
     * @param y Referece Y-coordinate for line
     * @param line Line of text
     * @return Starting Y-coordinate for a line of text.
     */
    private int startY(final int y, final String line) {
        int startY = 0;
        int width = this.canvas.renderedWidth(line, this.fontSize);
        if (this.orientation == Orientation.HORIZONTAL) {
            startY = y + this.fontSize;
        } else if (this.orientation == Orientation.VERTICAL) {
            if (this.textAlignment == HorizontalAlignment.LEFT_JUSTIFIED) {
                startY = -width;
            } else if (this.textAlignment == HorizontalAlignment.CENTERED) {
                startY = -width / 2;
            } else if (this.textAlignment
            		== HorizontalAlignment.RIGHT_JUSTIFIED) {
                startY = 0;
            } else {
                throw new IllegalArgumentException("Illegal text alignment");
            }
        }
        return startY;
    }
    

    /**
     * Get reference X-coordinate for next line of text.
     * @param x Reference X-coordinate for current line of text
     * @param line A line of text
     * @param canvas A drawing canvas
     * @return Reference X-coordinate for next line of text.
     */
    private int nextX(final int x, final String line,
    		final DrawingCanvas canvas) {
        int nextX = 0;
        if (this.orientation == Orientation.HORIZONTAL) {
            nextX = this.minX;
        } else if (this.orientation == Orientation.VERTICAL) {
            nextX = x + this.padding + this.fontSize;
        }
        return nextX;
    }
    
    
    /**
     * Get reference Y-coordinate for next line of text.
     * @param y Reference Y-coordinate for current line of text
     * @param line A line of text
     * @param canvas A drawing canvas
     * @return Reference Y-coordinate for next line of text.
     */
    private int nextY(final int y, final String line,
    		final DrawingCanvas canvas) {
        int nextY = 0;
        if (this.orientation == Orientation.HORIZONTAL) {
            nextY = y + this.padding + this.fontSize;
        } else if (this.orientation == Orientation.VERTICAL) {
            nextY = this.minY;
        }
        return nextY;
    }
    
    
    /**
     * Paint a line of text on the given drawing canvas.
     * @param line Line of text to paint
     * @param x Reference X-coordinate of text
     * @param y Reference Y-coordinate of text
     * @param drawingCanvas Drawing canvas
     */
    private void paintLine(final String line, final int x,
    		final int y, final DrawingCanvas drawingCanvas) {
    	int textX = this.startX(x, line);
    	int textY = this.startY(y, line);
        Text text = drawingCanvas.newText(line, textX, textY, this.fontSize,
        		HorizontalAlignment.LEFT_JUSTIFIED, this.color);
        if (this.orientation == Orientation.VERTICAL) {
            text.setRotation(270);
        }
        if (this.url != null) {
            text.setUrl(this.url);
        }
        drawingCanvas.add(text);
    }

}
