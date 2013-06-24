/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/


package org.rti.webgenome.units;

/**
 * Relative alignment of some graphical element to a reference point.
 */
public final class HorizontalAlignment {

	/**
	 * Constructor.
	 *
	 */
	private HorizontalAlignment() {
	}

	/**
	 * Left justified.
	 */
	public static final HorizontalAlignment
		LEFT_JUSTIFIED = new HorizontalAlignment();

	
	/**
	 * Middle justified.
	 */
	public static final HorizontalAlignment
		CENTERED = new HorizontalAlignment();

	
	/**
	 * Right justified.
	 */
	public static final HorizontalAlignment
		RIGHT_JUSTIFIED = new HorizontalAlignment();
	
	
	/**
	 * Completely left of.
	 */
	public static final HorizontalAlignment
		LEFT_OF = new HorizontalAlignment();
	
	
	/**
	 * Completely right of.
	 */
	public static final HorizontalAlignment
		RIGHT_OF = new HorizontalAlignment();
	
	
	/**
	 * On x-cordinates representing 0 in some units of measurement.
	 */
	public static final HorizontalAlignment
		ON_ZERO = new HorizontalAlignment();
}
