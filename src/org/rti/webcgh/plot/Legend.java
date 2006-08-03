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

package org.rti.webcgh.plot;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.GraphicPrimitive;
import org.rti.webcgh.drawing.Line;


/**
 * Represents a legend matching colors of sets of data points
 * in plots against bioassay names.
 * @author dhall
 *
 */
public final class Legend implements PlotElement {
    
    
    // =======================
    //    Constants
    // =======================
    
    /** Font size. */
    private static final int FONT_SIZE = 12;
    
    /** Text color. */
    private static final Color TEXT_COLOR = Color.BLACK;
    
    /** Width of line around border of legend. */
    private static final int BORDER_LINE_WIDTH = 3;
    
    /** Color of line around border of legend. */
    private static final Color BORDER_LINE_COLOR = Color.BLACK;
    
    /** Width of line around groups of bioassays. */
    private static final int GROUP_LINE_WIDTH = 1;
    
    /** Color of line around groups of bioassays. */
    private static final Color GROUP_LINE_COLOR = Color.GRAY;
    
    /** Padding around graphical elements in pixels. */
    private static final int PADDING = 10;
    
    
    // ===========================
    //       Attributes
    // ===========================
    
    /** Experiments in legend. */
    private final Collection<Experiment> experiments;

    /**
     * List of graphic primitives that are rendered when
     * paint() method called.
     */
    private final List<GraphicPrimitive> graphicPrimitives =
        new ArrayList<GraphicPrimitive>();
    
    
    /** Width of legend in pixels. */
    private int width = 0;
    
    
    /** Height of legend in pixels. */
    private int height = 0;
    
    
    /** Origin point of coordinates. */
    private Point origin = new Point(0, 0);
    
    
    // ==========================
    //     Constructors
    // ==========================
    
    /**
     * Constructor.
     * @param experiments Experiments in legend
     * @param width Width of legend in pixels
     */
    public Legend(final Collection<Experiment> experiments, final int width) {
        
        // Check args
        if (experiments == null) {
            throw new IllegalArgumentException("Experiments cannot be null");
        }
        if (width < PADDING) {
            throw new IllegalArgumentException("Width cannot be less than "
                    + PADDING);
        }
        
        // Set properties
        this.experiments = experiments;
        this.width = width;
        
        // Layout graphic primitives
        this.height = this.layoutGraphicPrimitives(experiments);
    }
    
    
    /**
     * Lay out graphic primitives derived from given experiments.
     * @param experiments Experiments in legend
     * @return Bottom-most y-coordinate of generated
     * graphic primitives
     */
    private int layoutGraphicPrimitives(
            final Collection<Experiment>experiments) {
        
        // Top border line
        this.graphicPrimitives.add(new Line(this.origin.x, this.origin.y,
                this.origin.x + this.width, this.origin.y,
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        // Bioassay groupings
        int topY = PADDING + this.origin.y;
        for (Experiment exp : experiments) {
            int bottomY = this.layoutGraphicPrimitives(exp, topY);
            topY = bottomY + PADDING;
        }
        
        // Left border line
        this.graphicPrimitives.add(new Line(this.origin.x, this.origin.y,
                this.origin.x, topY, 
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        // Right border line
        this.graphicPrimitives.add(new Line(this.origin.x + this.width
                - BORDER_LINE_WIDTH / 2,
                this.origin.y, this.origin.x + this.width
                - BORDER_LINE_WIDTH / 2, topY,
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        // Bottom border line
        this.graphicPrimitives.add(new Line(this.origin.x, topY
                - Legend.BORDER_LINE_WIDTH / 2,
                this.origin.x + this.width, topY
                - Legend.BORDER_LINE_WIDTH / 2,
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        return topY;
    }
    
    
    /**
     * Lay out graphic primitives derived from given experiment.
     * @param experiment Experiment
     * @param topY Top-most y-coordinate for this group
     * of generated graphic primitives.  i.e., All generated
     * graphic primitives will have a y-coordinate that places
     * them below this number.
     * @return Bottom-most y-coordinate of generated
     * graphic primitives
     */
    private int layoutGraphicPrimitives(final Experiment experiment,
            final int topY) {
        return topY;
    }
    
    
    // ================================
    //     PlotElement interface
    // ================================


    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
        for (GraphicPrimitive prim : this.graphicPrimitives) {
            canvas.add(prim);
        }
    }
    
    
    /**
     * Point at top left used to align with other plot elements.
     * @return A point
     */
    public Point topLeftAlignmentPoint() {
        return this.origin;
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(this.origin.x, this.origin.y + this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.origin.x + this.width, this.origin.y);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.origin.x + this.width,
                this.origin.y + this.height);
    }
    
    
    /**
     * Width in pixels.
     * @return Width in pixels
     */
    public int width() {
        return this.width;
    }
    
    
    /**
     * Height in pixels.
     * @return Height in pixels
     */
    public int height() {
        return this.height;
    }
    
    
    /**
     * Return point at top left of element.
     * @return A point
     */
    public Point topLeftPoint() {
        return this.origin;
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
        this.origin.x += deltaX;
        this.origin.y += deltaY;
        this.graphicPrimitives.clear();
        this.layoutGraphicPrimitives(this.experiments);
    }

}
