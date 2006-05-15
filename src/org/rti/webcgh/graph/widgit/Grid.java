/*

$Source$
$Revision$
$Date$

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
package org.rti.webcgh.graph.widgit;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.Line;
import org.rti.webcgh.drawing.Orientation;
import org.rti.webcgh.graph.PlotElement;

/**
 * A graphical grid
 */
public class Grid implements PlotElement {
    
    
    // ==================================
    //       Attributes
    // ==================================
    
    private final Orientation orientation;
    private final List gridMarkLocations = new ArrayList();
    private int zeroPointLocation = 0;
    private boolean zeroPointSet = false;
    private final Color color;
    private final int width;
    private final int height;
    
    private int gridMarkThickness = 3;
    private Color zeroMarkColor = Color.black;
    
    
    /**
     * @param gridMarkThickness The gridMarkThickness to set.
     */
    public void setGridMarkThickness(int gridMarkThickness) {
        this.gridMarkThickness = gridMarkThickness;
    }
    
    
	/**
	 * @param zeroPointLocation The zeroPointLocation to set.
	 */
	public void setZeroPointLocation(int zeroPointLocation) {
		this.zeroPointLocation = zeroPointLocation;
		this.zeroPointSet = true;
	}
	
	
	/**
	 * @param zeroMarkColor The zeroMarkColor to set.
	 */
	public void setZeroMarkColor(Color zeroMarkColor) {
		this.zeroMarkColor = zeroMarkColor;
	}
	
	
    // ===============================
    //       Constructors
    // ===============================
    
    /**
     * Constructor
     * @param width Width in pixels
     * @param height Height in pixels
     * @param orientation Orientation
     * @param color Color
     */
    public Grid(int width, int height, Orientation orientation, Color color) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
        this.color = color;
    }
    
    
    // =====================================
    //    Methods in PlotElement interface
    // =====================================
    
    
    /**
     * Paint element
     * @param canvas A canvas
     */
    public void paint(DrawingCanvas canvas) {
    	
    	// Add all lines except zero point
        for (Iterator it = this.gridMarkLocations.iterator(); it.hasNext();) {
            int pos = ((Integer)it.next()).intValue();
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            if (this.orientation == Orientation.HORIZONTAL) {
                x1 = 0;
                x2 = this.width;
                y1 = y2 = this.height - pos;
            } else if (this.orientation == Orientation.VERTICAL) {
                x1 = x2 = pos;
                y1 = 0;
                y2 = this.height;
            }
            Line line = new Line(x1, y1, x2, y2, this.gridMarkThickness, this.color);
            canvas.add(line);
        }
        
        // Add zero point
        if (this.zeroPointSet) {
        	Line line = null;
        	if (this.orientation == Orientation.HORIZONTAL) {
        		int y = this.height - this.zeroPointLocation;
        		line = new Line(0, y, this.width, y, this.gridMarkThickness, 
        				this.zeroMarkColor);
        	} else if (this.orientation == Orientation.VERTICAL) {
        		line = new Line(this.zeroPointLocation, 0, this.zeroPointLocation,
        				this.height, this.gridMarkThickness, this.zeroMarkColor);
        	}
        	canvas.add(line);
        }
    }
    
    
    /**
     * Point at top left used to align with other plot elements
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return new Point(0, 0);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(0, this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.width, 0);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.width, this.height);
    }
    
    
    /**
     * Width in pixels
     * @return Width in pixels
     */
    public int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels
     * @return Height in pixels
     */
    public int height() {
        return this.height;
    }
    
    
    /**
     * Return point at top left of element
     * @return A point
     */
    public Point topLeftPoint() {
        return new Point(0, 0);
    }
    
    
    // ==============================
    //     Public methods
    // ==============================
    
    /**
     * Add a grid mark position
     * @param pos Grid mark position in pixels from origin
     */
    public void addGridMarkPosition(int pos) {
        this.gridMarkLocations.add(new Integer(pos));
    }

}
