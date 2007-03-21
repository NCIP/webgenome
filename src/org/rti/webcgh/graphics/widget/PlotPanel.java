/*
$Revision: 1.8 $
$Date: 2007-03-21 21:59:13 $

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

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.graphics.DrawingCanvas;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Location;
import org.rti.webcgh.units.VerticalAlignment;

/**
 * A container and layout manager for plot widgets.
 */
public class PlotPanel implements ScalePlotElement {
    
    
    // =========================
    //       Constants
    // =========================
    
    /** Default padding around widgets in pixels. */
    private static final int DEF_PADDING = 10;
        
    
    // ===================================
    //        Attributes
    // ===================================
	
    /** Widget occupying top-most position in panel. */
	private PlotElement topElement = null;
    
    /** Widget occupying bottom-most position in panel. */
	private PlotElement bottomElement = null;
    
    /** Widget occupying left-most position in panel. */
	private PlotElement leftElement = null;
    
    /** Widget occupying right-most position in panel. */
	private PlotElement rightElement = null;
	
	/**
	 * Reference scale plot element.  Used when aligning
	 * on zero.
	 */
	private ScalePlotElement referenceScalePlotElement = null;
    
    /**
     * Widget that is reference point for laying out
     * other widgets.  This may or may not be set.
     */
	private PlotElement referenceElement = null;
    
    /** Padding between widgets in pixels. */
    private int padding = DEF_PADDING;
    
    /** Drawing canvas that actually renderes widgets. */
    private DrawingCanvas drawingCanvas = null;
    
    /** Name of plot panel.  This is used primarily in debugging. */
    private String name = null;
    
    /** Nested plot widgets. */
    private List<PlotElement> nestedWidets =
    	new ArrayList<PlotElement>();
   
    
    // =========================
    //    Getters/setters
    // =========================
    
    /**
     * Get name of plot panel.  Name is
     * used primarily in debugging.
     * @return Name of plot panel
     */
	public final String getName() {
		return name;
	}


	/**
	 * Set name of plot panel.  Name is
     * used primarily in debugging.
	 * @param name Name of plot panel
	 */
	public final void setName(final String name) {
		this.name = name;
	}


	/**
     * @param padding The padding to set.
     */
    public final void setPadding(final int padding) {
        this.padding = padding;
    }
    
    
    /**
     * Get underlying drawing canvas.
     * @return Drawing canvas
     */
    public final DrawingCanvas getDrawingCanvas() {
        return drawingCanvas;
    }
    
    
    // ================================
    //        Constructors
    // ================================


    /**
     * Constructor.
     * @param drawingCanvas A drawing canvas
     */
    public PlotPanel(final DrawingCanvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
    }
    
    
    // ======================================
    //     PlotElement interface
    // ======================================
    
    /**
     * Paint element.
     * @param canvas A canvas
     */
    public final void paint(final DrawingCanvas canvas) {
        for (PlotElement e : this.nestedWidets) {
        	e.paint(canvas);
        }
    }
    
    /**
     * Return width of panel.
     * @return Width of panel
     */
    public final int width() {
        return this.maxX() - this.minX();
    }
    
    
    /**
     * Return height of panel.
     * @return Height of panel
     */
    public final int height() {
        return this.maxY() - this.minY();
    }

    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public final Point topLeftAlignmentPoint() {
        if (this.referenceElement != null) {
        	return this.referenceElement.topLeftAlignmentPoint();
        }
        int x = 0, y = 0;
        if (this.topElement != null) {
        	y = this.topElement.topLeftAlignmentPoint().y;
        }
        if (this.leftElement != null) {
        	x = this.leftElement.topLeftAlignmentPoint().x;
        }
        return new Point(x, y);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public final Point bottomLeftAlignmentPoint() {
        if (this.referenceElement != null) {
        	return this.referenceElement.bottomLeftAlignmentPoint();
        }
        int x = 0, y = 0;
        if (this.bottomElement != null) {
        	y = this.bottomElement.bottomLeftAlignmentPoint().y;
        }
        if (this.leftElement != null) {
        	x = this.leftElement.topLeftAlignmentPoint().x;
        }
        return new Point(x, y);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public final Point topRightAlignmentPoint() {
        if (this.referenceElement != null) {
        	return this.referenceElement.topRightAlignmentPoint();
        }
        int x = 0, y = 0;
        if (this.topElement != null) {
        	y = this.topElement.topRightAlignmentPoint().y;
        }
        if (this.rightElement != null) {
        	x = this.rightElement.topRightAlignmentPoint().x;
        }
        return new Point(x, y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public final Point bottomRightAlignmentPoint() {
        if (this.referenceElement != null) {
        	return this.referenceElement.bottomRightAlignmentPoint();
        }
        int x = 0, y = 0;
        if (this.bottomElement != null) {
        	y = this.bottomElement.bottomRightAlignmentPoint().y;
        }
        if (this.rightElement != null) {
        	x = this.rightElement.bottomRightAlignmentPoint().x;
        }
        return new Point(x, y);
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public final Point topLeftPoint() {
    	return new Point(this.minX(), this.minY());
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public final void move(final int deltaX, final int deltaY) {
    	for (PlotElement e : this.nestedWidets) {
    		e.move(deltaX, deltaY);
    	}
    }
    
    
    // =================================
    //      ScalePlotElement interface
    // =================================
    
	/**
	 * Return point in pixels corresponding to the zero point
	 * in the native units of measurement represented by
	 * element.
	 * @return A point or <code>null</code> if the element
	 * does not contain a zero point
	 */
	public final Point zeroPoint() {
		Point point = new Point(0, 0);
		if (this.referenceScalePlotElement != null) {
			point = this.referenceScalePlotElement.zeroPoint();
		}
		return point;
	}
    
    
    // ========================================
    //        Public methods
    // ========================================
	
	
	/**
	 * Move given plot element to the back (i.e. render first).
	 * @param element Element to move to the back.  This
	 * element must have already been added or an
	 * <code>IllegalArgumentException</code> will be thrown.
	 */
	public final void moveToBack(final PlotElement element) {
		if (!this.nestedWidets.contains(element)) {
			throw new IllegalArgumentException(
					"Cannot move element to back of panel if "
					+ "it has not first been added");
		}
		this.nestedWidets.remove(element);
		this.nestedWidets.add(0, element);
	}
    
    /**
     * Add an element.
     * @param element An element
     * @param hAlign Horizontal alignment of element relative to
     * aggregate of elements already added
     * @param vAlign Vertical alignment of element relative to
     * aggregate of elements already added
     * element already added or off to one side?
     * @param makeReferenceElement Make this element the reference
     * for justifying subsequently added elements?
     */
    public final void add(final PlotElement element,
            final HorizontalAlignment hAlign,
            final VerticalAlignment vAlign,
            final boolean makeReferenceElement) {
    	
    	// Add element to list of nested widgets
    	this.nestedWidets.add(element);
    	
    	// Compute the X and Y coordinates of new widget
        int x = computeInsertionXCoord(element, hAlign);
        int y = computeInsertionYCoord(element, vAlign);
        
        // Move element's points to parent's coordinate system
        int deltaX = x;
        int deltaY = y;
        element.move(deltaX, deltaY);
        
        // If X or Y coordinate less than O, adjust coordinates
        // of all nexted elements so that they are all positive
        if (x < 0 || y < 0) {
        	deltaX = 0;
        	deltaY = 0;
        	if (x < 0) {
        		deltaX = -x;
        	}
        	if (y < 0) {
        		deltaY = -y;
        	}
        	for (PlotElement e : this.nestedWidets) {
        		e.move(deltaX, deltaY);
        	}
        }
        
        if (makeReferenceElement) {
        	this.referenceElement = element;
        	if (element instanceof ScalePlotElement) {
        		this.referenceScalePlotElement = (ScalePlotElement) element;
        	}
        }
        if (element instanceof ScalePlotElement
        		&& this.referenceScalePlotElement == null) {
        	this.referenceScalePlotElement = (ScalePlotElement) element;
        }
        if (this.topElement == null) {
        	this.topElement = element;
            this.bottomElement = element;
            this.leftElement = element;
            this.rightElement = element;  
        } else {
        	if (element.topLeftPoint().x < this.minX()) {
        		this.leftElement = element;
            }
        	if (element.topLeftPoint().x + element.width() > this.maxX()) {
        		this.rightElement = element;
            }
        	if (element.topLeftPoint().y < this.minY()) {
        		this.topElement = element;
            }
        	if (element.topLeftPoint().y + element.height() > this.maxY()) {
        		this.bottomElement = element;
            }
        }
    }
    
    
    /**
     * Get minimum X-axis coordinate.
     * @return Minimum X-axis coordinate
     */
    protected final int minX() {
    	int min = 0;
    	if (this.leftElement != null) {
    		min = this.leftElement.topLeftPoint().x - this.padding;
        }
    	return min;
    }
    
    
    /**
     * Get maximum X-axis coordinate.
     * @return Maximum X-axis coordinate
     */
    protected final int maxX() {
    	int max = 0;
    	if (this.rightElement != null) {
    		max = this.rightElement.topLeftPoint().x
            + this.rightElement.width() + this.padding;
        }
    	return max;
    }
    
    
    /**
     * Get minimum Y-axis coordinate.
     * @return Minimum Y-axis coordinate
     */
    protected final int minY() {
    	int min = 0;
    	if (this.topElement != null) {
    		min = this.topElement.topLeftPoint().y - this.padding;
        }
    	return min;
    }
    
    
    /**
     * Get maximum X-axis coordinate.
     * @return Maximum X-axis coordinate
     */
    protected final int maxY() {
    	int max = 0;
    	if (this.bottomElement != null) {
    		max = this.bottomElement.topLeftPoint().y
            + this.bottomElement.height() + this.padding;
        }
    	return max;
    }
    
    
    /**
     * Add an element.
     * @param element An element
     * @param hAlign Horizontal alignment of element relative to
     * aggregate of elements already added
     * @param vAlign Vertical alignment of element relative to
     * aggregate of elements already added
     * element already added or off to one side?
     * for justifying subsequently added elements?
     */
    public final void add(final PlotElement element,
            final HorizontalAlignment hAlign,
            final VerticalAlignment vAlign) {
    	this.add(element, hAlign, vAlign, false);
    }
    
    
    /**
     * Add given widget centered relative to existing widgets.
     * @param element Widget to add
     */
    public final void add(final PlotElement element) {
        this.add(element, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
    }
    
    
    /**
     * Add given widget centered relative to existing widgets.
     * @param element Widget to add
     * @param makeReferenceElement Make this element the reference
     * for justifying subsequently added elements?
     */
    public final void add(final PlotElement element,
    		final boolean makeReferenceElement) {
        this.add(element, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED, makeReferenceElement);
    }
    
    
    /**
     * Create new plot panel as child of this.
     * @return Plot panel
     */
    public final PlotPanel newChildPlotPanel() {
    	return new PlotPanel(this.drawingCanvas.newTile());
    }
    
    
    /**
     * Add extra padding.
     * @param numPaddingUnits Num padding units
     * @param location Location
     */
    public final void addExtraPadding(final int numPaddingUnits,
            final Location location) {
        int delta = this.padding * numPaddingUnits;
        int width = 0, height = 0;
        if (location == Location.ABOVE || location == Location.BELOW) {
        	width = this.width();
        	height = delta;
        } else if (location == Location.LEFT_OF
                || location == Location.RIGHT_OF) {
        	width = delta;
        	height = this.height();
        }
        EmptySpace es = new EmptySpace(width, height);
        if (location == Location.ABOVE) {
        	this.add(es, HorizontalAlignment.CENTERED,
                    VerticalAlignment.ABOVE);
        } else if (location == Location.BELOW) {
        	this.add(es, HorizontalAlignment.CENTERED,
                    VerticalAlignment.BELOW);
        } else if (location == Location.LEFT_OF) {
        	this.add(es, HorizontalAlignment.LEFT_OF,
                    VerticalAlignment.CENTERED);
        } else if (location == Location.RIGHT_OF) {
        	this.add(es, HorizontalAlignment.RIGHT_OF,
                    VerticalAlignment.CENTERED);
        }
    }
    
    
    /**
     * Find rendered width of given text on this panel.
     * @param text Text
     * @param fontSize Font size
     * @return Width of rendered text in pixels
     */
    public final int renderedWidth(final String text,
            final int fontSize) {
    	return this.drawingCanvas.renderedWidth(text, fontSize);
    }
    
            
    /**
     * Compute x-coordinate of insertion point for given widget.
     * @param element Widget being added
     * @param hAlign Horizontal alignment with existing widgets
     * @return Insertion point x-coordinate
     */
    private int computeInsertionXCoord(final PlotElement element, 
            final HorizontalAlignment hAlign) {
    	if (this.topElement == null) {
    		return this.padding;
        }
    	int coord = 0;
    	if (hAlign == HorizontalAlignment.LEFT_JUSTIFIED) {
    	    coord = this.bottomLeftAlignmentPoint().x
            - element.bottomLeftAlignmentPoint().x;
        } else if (hAlign == HorizontalAlignment.LEFT_OF) {
    	    coord = this.minX() - this.padding - element.width();
        } else if (hAlign == HorizontalAlignment.CENTERED) {
    	    coord = (this.maxX() + this.minX()) / 2 - element.width() / 2;
        } else if (hAlign == HorizontalAlignment.RIGHT_JUSTIFIED) {
    	    coord = this.bottomRightAlignmentPoint().x
            - element.bottomRightAlignmentPoint().x;
        } else if (hAlign == HorizontalAlignment.RIGHT_OF) {
    	    coord = this.maxX() + this.padding;
        } else if (hAlign == HorizontalAlignment.ON_ZERO) {
    		int alignmentX = (element instanceof ScalePlotElement)
                ? ((ScalePlotElement) element).zeroPoint().x
                : (element.width() + element.topLeftAlignmentPoint().x) / 2; 
    		coord = this.zeroPoint().x - alignmentX;
    	}
    	return coord;
    }
    
    
    /**
     * Compute y-coordinate of insertion point for given widget.
     * @param element Widget being added
     * @param vAlign Vertical alignment relative to existing widgets
     * @return Y-coordinate of insertion point
     */
    private int computeInsertionYCoord(final PlotElement element, 
            final VerticalAlignment vAlign) {
    	if (this.topElement == null) {
    		return this.padding;
        }
        int coord = 0;
        if (vAlign == VerticalAlignment.ABOVE) {
            coord = this.minY() - this.padding - element.height()
            - element.topLeftPoint().y;
        } else if (vAlign == VerticalAlignment.BELOW) {
            coord = this.maxY() + this.padding - element.topLeftPoint().y;
        } else if (vAlign == VerticalAlignment.BOTTOM_JUSTIFIED) {
            coord = this.bottomLeftAlignmentPoint().y
            - element.bottomLeftAlignmentPoint().y;
        } else if (vAlign == VerticalAlignment.CENTERED) {
            coord = (this.maxY() + this.minY()) / 2
            - element.height() / 2;
        } else if (vAlign == VerticalAlignment.TOP_JUSTIFIED) {
            coord = this.topLeftAlignmentPoint().y
            - element.topLeftAlignmentPoint().y;
        } else if (vAlign == VerticalAlignment.ON_ZERO) {
        	int alignY = (element instanceof ScalePlotElement)
                ? ((ScalePlotElement) element).zeroPoint().y
                : (element.height()
                        + element.bottomLeftAlignmentPoint().y) / 2;
        	coord = this.zeroPoint().y - alignY;
        }
    	return coord;
    }
}
