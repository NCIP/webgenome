/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/


package org.rti.webgenome.units;

/**
 * Direction of some graphical element in relation to a reference point.
 */
public final class Direction {
	
	/**
	 * Pointing up relative to reference point.
	 */
	public static final Direction UP = new Direction();
	
	/**
	 * Pointing down relative to reference point.
	 */
	public static final Direction DOWN = new Direction();
	
	
	/**
	 * Left.
	 */
	public static final Direction LEFT = new Direction();
	
	
	/**
	 * Right.
	 */
	public static final Direction RIGHT = new Direction();

	
	/**
	 * Constructor.
	 *
	 */
	private Direction() {
		
	}

}
