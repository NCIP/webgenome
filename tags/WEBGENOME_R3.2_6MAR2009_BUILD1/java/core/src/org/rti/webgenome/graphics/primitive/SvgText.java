/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;

import org.rti.webgenome.units.HorizontalAlignment;

/**
 * Implementation of <code>Text</code> interface for
 * an SVG canvas.
 */
public final class SvgText extends Text {
	
	/** Default color. */
	private static final Color DEF_COLOR = Color.BLACK;

	/**
	 * Constructor.
	 */
	public SvgText() {
		super();
	}

	/**
     * Constructor.
	 * @param value Text value
	 */
	public SvgText(final String value) {
		super(value, DEF_COLOR);
	}
	

	/**
     * Constructor.
	 * @param value Text value
	 * @param x X-coordinate position
	 * @param y Y-coordinate position
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color
	 */
	public SvgText(
		final String value,
        final int x,
        final int y,
        final int fontSize,
        final HorizontalAlignment alignment,
        final Color color) {
		super(value, x, y, fontSize, alignment, color);
	}
	
	

	/**
	 * Get width of rendered text.
	 * @return Width of rendered text
	 */
	public int renderedWidth() {
		int len = 0;
		if (this.getValue() != null) {
			int count = 0;
			char lastChar = 'A';
			for (int i = 0; i < this.getValue().length(); i++) {
				char c = this.getValue().charAt(i);
				if (!(Character.isWhitespace(c)
                        && Character.isWhitespace(lastChar))) {
					count++;
                }
				lastChar = c;
			}
			len = (int) ((double) count * (double) this.getFontSize() * 0.6);
		}
		return len;
	}

}
