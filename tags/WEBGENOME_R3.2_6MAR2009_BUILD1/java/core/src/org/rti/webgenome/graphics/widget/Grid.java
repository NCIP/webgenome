/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.widget;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.graphics.DrawingCanvas;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.units.Orientation;

/**
 * Class that represents a grid of lines drawn
 * on plot.  Grids will typically be aligned
 * to axes; hatch marks for the axis will
 * connect to grid lines.
 */
public final class Grid implements PlotElement {
    
    // =======================
    //       Constants
    // =======================
    
    /** Default thickness of grid line. */
    private static final int DEF_GRID_MARK_THICKNESS = 3;
    
    
    // ==================================
    //       Attributes
    // ==================================
    
    /** Orientation of grid. */
    private final Orientation orientation;
    
    /** Color of grid. */
    private final Color color;
    
    /** Width of grid in pixels. */
    private final int width;
    
    /** Height of grid in pixels. */
    private final int height;
    
    /** Thickness of grid lines in pixels. */
    private int gridMarkThickness = DEF_GRID_MARK_THICKNESS;
    
    /** Color of grid line corresponding to "0". */
    private Color zeroMarkColor = Color.black;
    
    /** Minimum X-coordinate in grid. */
    private int minX = 0;
    
    /** Minimum Y-coordinate in grid. */
    private int minY = 0;
    
    /**
     * Graphic primitives, which are instantiated by the
     * constructor and business methods and later rendered.
     */
    private final Collection<GraphicPrimitive> graphicPrimitives =
    	new ArrayList<GraphicPrimitive>();
    
    
    // =================================
    //      Getters/setters
    // =================================
    
    /**
     * Set thickness of grid lines in pixels.
     * @param gridMarkThickness The gridMarkThickness to set.
     */
    public void setGridMarkThickness(final int gridMarkThickness) {
        this.gridMarkThickness = gridMarkThickness;
    }
    
    
	/**
     * Set pixel location of line representing "0".
	 * @param zeroPointLocation The zeroPointLocation to set.
	 */
	public void setZeroPointLocation(final int zeroPointLocation) {
    	Line line = null;
    	if (this.orientation == Orientation.HORIZONTAL) {
    		int y = this.minY + this.height - zeroPointLocation;
    		line = new Line(minX, y, minX + this.width, y,
    				this.gridMarkThickness, 
    				this.zeroMarkColor);
    		this.graphicPrimitives.add(line);
    	}
	}
	
	
	/**
     * Set grid line color.
	 * @param zeroMarkColor The zeroMarkColor to set.
	 */
	public void setZeroMarkColor(final Color zeroMarkColor) {
		this.zeroMarkColor = zeroMarkColor;
	}
	
	
    // ===============================
    //       Constructors
    // ===============================
    
    /**
     * Constructor.
     * @param width Width in pixels
     * @param height Height in pixels
     * @param orientation Orientation
     * @param color Color
     */
    public Grid(final int width, final int height,
            final Orientation orientation, final Color color) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
        this.color = color;
    }
    
    
    // =====================================
    //    Methods in PlotElement interface
    // =====================================
    
    
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
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Point at bottom left used to align with other plot elements.
     * @return A point
     */
    public Point bottomLeftAlignmentPoint() {
        return new Point(this.minX, this.minY + this.height);
    }
    
    
    /**
     * Point at top right used to align with other plot elements.
     * @return A point
     */
    public Point topRightAlignmentPoint() {
        return new Point(this.minX + this.width, this.minY);
    }
    
    
    /**
     * Point at bottom right used to align with other plot elements.
     * @return A point
     */
    public Point bottomRightAlignmentPoint() {
        return new Point(this.minX + this.width, this.minY + this.height);
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
        return new Point(this.minX, this.minY);
    }
    
    
    /**
     * Move element.
     * @param deltaX Number of pixels horizontally
     * @param deltaY Number of pixels vertically
     */
    public void move(final int deltaX, final int deltaY) {
    	this.minX += deltaX;
    	this.minY += deltaY;
    	for (GraphicPrimitive prim : this.graphicPrimitives) {
    		prim.move(deltaX, deltaY);
    	}
    }
    
    // ==============================
    //     Business methods
    // ==============================
    
    /**
     * Add a grid mark position.
     * @param pos Grid mark position in pixels from origin
     */
    public void addGridMarkPosition(final int pos) {
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        if (this.orientation == Orientation.HORIZONTAL) {
            x1 = this.minX;
            x2 = this.minX + this.width;
            y1 = this.minY + this.height - pos;
            y2 = y1;
        } else if (this.orientation == Orientation.VERTICAL) {
            x1 = this.minX + pos;
            x2 = x1;
            y1 = this.minY;
            y2 = this.minY + this.height;
        }
        Line line = new Line(x1, y1, x2, y2,
                this.gridMarkThickness, this.color);
        this.graphicPrimitives.add(line);
    }

}
