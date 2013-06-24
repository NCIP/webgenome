/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;

import org.rti.webgenome.units.HorizontalAlignment;

/**
 * Represents text to be rendered graphically.
 */
public abstract class Text extends GraphicPrimitive {
	
	// ========================
	//      Attributes
	// ========================
	
	/** Text string to render. */
	private String value = "";
	
	/** Font size. */
	private int fontSize = 10;
	
	/**
	 * Horizontal alignment relative to the reference point specified
	 * by <code>x</code> and <code>y</code>.
	 */
	private HorizontalAlignment alignment = HorizontalAlignment.CENTERED;
	
	/** X-coordinate for reference point of text. */
	private int x = 0;
	
	/** Y-coordinate for reference point of text. */
	private int y = 0;
	
	/** Rotation to apply on text in radians. */
	private double rotation = 0.0;
	
	
	
	
	// ========================
	//     Getters/setters
	// ========================
	
	/**
	 * Get font size.
	 * @return Font size
	 */
	public final int getFontSize() {
		return fontSize;
	}

	/**
	 * Get text value to render.
	 * @return Text value
	 */
	public final String getValue() {
		return value;
	}

	/**
	 * Set font size.
	 * @param i Font size Font size
	 */
	public final void setFontSize(final int i) {
		fontSize = i;
	}

	/**
	 * Set text value to render.
	 * @param string Text value to render
	 */
	public final void setValue(final String string) {
		value = string;
	}

	/**
	 * Get horizontal alignment relative to the reference point specified
	 * by <code>x</code> and <code>y</code>.
	 * @return Alignment of text to X and Y coordinates
	 * (i.e. START, MIDDLE, END)
	 */
	public final HorizontalAlignment getAlignment() {
		return alignment;
	}

	/**
	 * Set horizontal alignment relative to the reference point specified
	 * by <code>x</code> and <code>y</code>.
	 * @param i Alignment of text to X and Y coordinates
	 * (i.e. START, MIDDLE, END)
	 */
	public final void setAlignment(final HorizontalAlignment i) {
		alignment = i;
	}

	/**
	 * Get x-coordinate of reference point.
	 * @return X-coordinate of reference point
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Get y-coordinate of reference point.
	 * @return Y-coordinate of reference point
	 */
	public final int getY() {
		return y;
	}

	/**
	 * Set X-coordinate of reference point.
	 * @param i X-coordinate of reference poing
	 */
	public final void setX(final int i) {
		x = i;
	}

	/**
	 * Set Y-coordinate of reference point.
	 * @param i Y-coordinate of reference point
	 */
	public final void setY(final int i) {
		y = i;
	}

	/**
	 * Get rotation of text.
	 * @return Rotation in radians
	 */
	public final double getRotation() {
		return rotation;
	}

	/**
	 * Set rotation of text.
	 * @param i Rotation in radians
	 */
	public final void setRotation(final double i) {
		rotation = i;
	}
	
	
	// ==================================
	//        Constructors
	// ==================================
	
	/**
	 * Constructor.
	 *
	 */
	public Text() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param value Text value
	 * @param color Color of text
	 */
	public Text(final String value, final Color color) {
		super(color);
		this.value = value;
	}
	
	
	/**
	 * Constructor.
	 * @param value Text value
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color
	 */
	public Text(final String value, final int x, final int y,
			final int fontSize, final HorizontalAlignment alignment,
			final Color color) {
		this(value, color);
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.alignment = alignment;
	}
	
	// =========================
	//    Abstract methods
	// =========================
	
	/**
	 * Rendered width of text.
	 * @return Rendered width of text
	 */
	public abstract int renderedWidth();
	
	// ==================================
	//     Implemented abstract methods
	// ==================================
	
	/**
	 * Move graphic primitive.
	 * @param deltaX Change in X-coordinates in pixels
	 * @param deltaY Change in Y-coordinates in pixels
	 */
	public final void move(final int deltaX, final int deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
}
