/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.units;

/**
 * Relative size of some graphical element.
 */
public final class Size {
	
	
	/**
	 * Small size.
	 */
	public static final Size SMALL = new Size();
	
	/**
	 * Medium size.
	 */
	public static final Size MEDIUM = new Size();
	
	/**
	 * Large size.
	 */
	public static final Size LARGE = new Size();
	
	
	/**
	 * Constructor.
	 *
	 */
	private Size() {
		
	}
	
	
	/**
	 * Get size object by name.
	 * @param name Name of size object
	 * @return A size
	 */
	public static Size getSize(final String name) {
		String capName = name.toUpperCase();
		Size size = null;
		if ("MEDIUM".equals(capName) || "M".equals(capName)) {
			size = MEDIUM;
		} else if ("SMALL".equals(capName) || "S".equals(capName)) {
			size = SMALL;
		} else if ("LARGE".equals(capName) || "L".equals(capName)) {
			size = LARGE;
		} else {
			throw new IllegalArgumentException(
					"Invalid size: '" + capName + "'");
		}
		return size;
	}

}
