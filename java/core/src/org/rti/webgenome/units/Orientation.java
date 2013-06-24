/*
$Revision: 1.2 $
$Date: 2007-07-03 17:44:00 $


*/


package org.rti.webgenome.units;

import java.io.Serializable;

import org.rti.webgenome.util.SystemUtils;

/**
 * Represents orientation of some graphic element.
 */
public final class Orientation implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    /** Name of orientation. */
    private final String name;
	
	/**
	 * Constructor.
	 * @param name Name of orientation
	 */
	private Orientation(final String name) {
		this.name = name;
	}

	/**
	 * Horizontal orientation.
	 */
	public static final Orientation
		HORIZONTAL = new Orientation("HORIZONTAL");
	
	/**
	 * Vertical orientation.
	 */
	public static final Orientation
		VERTICAL = new Orientation("VERTICAL");
	
	
	/**
	 * Return opposite orientation.
	 * @param orientation An orientation
	 * @return The opposite orientation
	 */
	public static Orientation opposite(final Orientation orientation) {
	    Orientation opposite = null;
	    if (orientation == HORIZONTAL) {
	        opposite = VERTICAL;
	    } else if (orientation == VERTICAL) {
	        opposite = HORIZONTAL;
	    }
	    return opposite;
	}
	
	
	/**
	 * Get orientation associated with given name.
	 * @param name Name of orientation.
	 * @return An orientation corresponding to given
	 * name.
	 */
	public static Orientation valueOf(final String name) {
		Orientation orientation = null;
		if ("VERTICAL".equals(name)) {
			orientation = VERTICAL;
		} else if ("HORIZONTAL".equals(name)) {
			orientation = HORIZONTAL;
		}
		return orientation;
	}
	
	
	/**
	 * Get name of orientation.
	 * @return Name of orientation.
	 */
	public String name() {
		return this.name;
	}
}
