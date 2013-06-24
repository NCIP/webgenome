/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.units.HorizontalAlignment;


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

    /** Selected font size modifier. */
    private static final int SELECTED_FONT_SIZE_MODIFIER = 2;

    /** Text color. */
    private static final Color TEXT_COLOR = Color.BLACK;
    
    /** Width of line around border of legend. */
    private static final int BORDER_LINE_WIDTH = 1;
    
    /** Color of line around border of legend. */
    private static final Color BORDER_LINE_COLOR = Color.DARK_GRAY;
    
    /** Width of line around groups of bioassays. */
    private static final int GROUP_LINE_WIDTH = 1;

    /** Color of line around groups of bioassays. */
    private static final Color GROUP_LINE_COLOR = Color.GRAY;
    
    /** Padding around graphical elements in pixels. */
    private static final int PADDING = 10;
    
//    /** Background color. */
//    private static final Color BG_COLOR = new Color(255, 205, 255);
    
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
    }
    
    
    /**
     * Lay out graphic primitives derived from given experiments.
     * @param canvas A drawing canvas
     * @return Bottom-most y-coordinate of generated
     * graphic primitives
     */
    private int layoutGraphicPrimitives(final DrawingCanvas canvas) {
        this.graphicPrimitives.clear();
        
        // Top border line
        this.graphicPrimitives.add(new Line(this.origin.x, this.origin.y,
                this.origin.x + this.width, this.origin.y,
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        // Bioassay groupings
        int topY = PADDING + this.origin.y;
        int count = 0;
        for (Experiment exp : this.experiments) {
            if (count++ > 0) {
                topY += PADDING;
            }
            int bottomY = this.layoutGraphicPrimitives(exp, topY, canvas);
            topY = bottomY + PADDING;
        }
        
        // Left border line
        this.graphicPrimitives.add(new Line(this.origin.x, this.origin.y,
                this.origin.x, topY, 
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        // Right border line
        this.graphicPrimitives.add(new Line(this.origin.x + this.width,
                this.origin.y, this.origin.x + this.width, topY,
                Legend.BORDER_LINE_WIDTH, Legend.BORDER_LINE_COLOR));
        
        // Bottom border line
        this.graphicPrimitives.add(new Line(this.origin.x, topY,
                this.origin.x + this.width, topY,
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
     * @param canvas A drawing canvas
     * @return Bottom-most y-coordinate of generated
     * graphic primitives
     */
    private int layoutGraphicPrimitives(final Experiment experiment,
            final int topY, final DrawingCanvas canvas) {
        
        // Draw experiment name
        int textWidth = canvas.renderedWidth(experiment.getName(), FONT_SIZE);
        int textStartX = this.origin.x + this.width / 2 - textWidth / 2;
        int textY = topY + FONT_SIZE / 2;
        Text text = canvas.newText(experiment.getName(), textStartX, textY,
                FONT_SIZE, HorizontalAlignment.LEFT_JUSTIFIED, TEXT_COLOR);
        canvas.add(text);
        
        // Common drawing coordinates
        int leftBorder = this.origin.x + PADDING;
        int rightBorder = this.origin.x + this.width - PADDING;
        
        // Left flanking border line
        int textEndX = textStartX + textWidth;
        canvas.add(new Line(leftBorder, topY, textStartX - PADDING,
                topY, Legend.GROUP_LINE_WIDTH, Legend.GROUP_LINE_COLOR));
        
        // Right flanking border line
        canvas.add(new Line(textEndX + PADDING, topY,
                rightBorder, topY,
                Legend.GROUP_LINE_WIDTH, Legend.GROUP_LINE_COLOR));
        
        // Add bioassay names
        int bioAssayTopY = topY + FONT_SIZE / 2 + PADDING;
        int bottomY = this.layoutGraphicPrimitives(experiment.getBioAssays(),
                bioAssayTopY, canvas);
        bottomY += PADDING;
        
        // Left border line
        canvas.add(new Line(leftBorder, topY, leftBorder, bottomY,
                Legend.GROUP_LINE_WIDTH, Legend.GROUP_LINE_COLOR));
        
        // Right border line
        canvas.add(new Line(rightBorder, topY, rightBorder, bottomY,
                Legend.GROUP_LINE_WIDTH, Legend.GROUP_LINE_COLOR));
        
        // Bottom border line
        canvas.add(new Line(leftBorder, bottomY, rightBorder, bottomY,
                Legend.GROUP_LINE_WIDTH, Legend.GROUP_LINE_COLOR));
        
        return bottomY;
    }
    
    
    /**
     * Layout graphic primitives associated with given bioassays.
     * @param bioAssays Bioassays
     * @param topY Top-most Y-coordinate for graphic primitives
     * generated by this method.  All primitives will be below
     * this coordinate.
     * @param canvas Drawing canvas
     * @return Bottom-most Y-coordinate of generated graphic
     * primitives
     */
    private int layoutGraphicPrimitives(final Collection<BioAssay> bioAssays,
            final int topY, final DrawingCanvas canvas) {
        int textY = topY + FONT_SIZE;
        int boxY = topY;
        if (bioAssays != null) {
            int leftBoundary = this.origin.x + PADDING * 2;
            int rightBoundary = this.origin.x + this.width - PADDING * 2;
            int boxX = leftBoundary;
            for (BioAssay ba : bioAssays) {

            	// Account for selected bioassays
            	int fontSize = FONT_SIZE;
            	Color textColor = Legend.TEXT_COLOR;
            	int selectedBoxXYModifier = 0;
            	if(ba.isSelected()) {
            		fontSize += SELECTED_FONT_SIZE_MODIFIER;
            		textColor = ba.getColor().darker().darker();
            		selectedBoxXYModifier = Math.round(SELECTED_FONT_SIZE_MODIFIER / 2);
            	}

                // Set drawing coordinates
                int textX = boxX + FONT_SIZE + PADDING;
                int textEndX = textX + canvas.renderedWidth(ba.getName(),
                        FONT_SIZE);
                if (textEndX > rightBoundary) {
                    boxX = leftBoundary;
                    textX = boxX + FONT_SIZE + PADDING;
                    textY += FONT_SIZE + PADDING;
                    boxY += FONT_SIZE + PADDING;
                }
                
                // Add box
                Rectangle rect = new Rectangle(boxX - selectedBoxXYModifier,
                		boxY - selectedBoxXYModifier, fontSize, fontSize,
                		ba.getColor());
                canvas.add(rect);

                // Add text
                Text text = canvas.newText(ba.getName(), textX, textY,
                        fontSize, HorizontalAlignment.LEFT_JUSTIFIED, textColor);
                canvas.add(text);

                // If selected, draw border around selected bioassay
                if(ba.isSelected()) {
                	// coordinates
                	int selBoxX1 = boxX - selectedBoxXYModifier - 2;
                	int selBoxY1 = boxY - selectedBoxXYModifier - 2;
                	int selBoxX2 = textX + text.renderedWidth() + selectedBoxXYModifier + 2;
                	int selBoxY2 = textY + selectedBoxXYModifier + 2;
                	// top line
                	canvas.add(new Line(selBoxX1, selBoxY1, selBoxX2, selBoxY1,
                			1, ba.getColor()));
                	// bottom line
                	canvas.add(new Line(selBoxX1, selBoxY2, selBoxX2, selBoxY2,
                			1, ba.getColor()));
                	// left line
                	canvas.add(new Line(selBoxX1, selBoxY1, selBoxX1, selBoxY2,
                			1, ba.getColor()));
                	// right line
                	canvas.add(new Line(selBoxX2, selBoxY1, selBoxX2, selBoxY2,
                			1, ba.getColor()));
                }

                // Advance drawing coordinates
                boxX = textX + canvas.renderedWidth(ba.getName(), FONT_SIZE)
                    + PADDING * 2;
            }
        }
        return textY;
    }
    
    
    // ================================
    //     PlotElement interface
    // ================================


    /**
     * Paint element.
     * @param canvas A canvas
     */
    public void paint(final DrawingCanvas canvas) {
        int endY = this.layoutGraphicPrimitives(canvas);
        this.height = endY - this.origin.y;
        
        // Background
//        Rectangle rect = new Rectangle(this.origin.x, this.origin.y,
//        		this.width(), this.height(), BG_COLOR);
//        canvas.add(rect);
        
        // Graphic primitives
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
    }

}
