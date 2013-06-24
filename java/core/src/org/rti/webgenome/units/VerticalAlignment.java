/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.units;

/**
 * Vertical alignment.
 */
public final class VerticalAlignment {
    
    
    /**
     * Top justified.
     */
    public static final VerticalAlignment
    	TOP_JUSTIFIED = new VerticalAlignment();
    
    
    /**
     * Middle justified.
     */
    public static final VerticalAlignment
    	CENTERED = new VerticalAlignment();
    
    
    /**
     * Bottom justified.
     */
    public static final VerticalAlignment
    	BOTTOM_JUSTIFIED = new VerticalAlignment();
    
    
    /**
     * Above.
     */
    public static final VerticalAlignment
    	ABOVE = new VerticalAlignment();
    
    
    /**
     * Below.
     */
    public static final VerticalAlignment
    	BELOW = new VerticalAlignment();
    
    
	/**
	 * On x-cordinates representing 0 in some units of measurement.
	 */
	public static final VerticalAlignment
		ON_ZERO = new VerticalAlignment();
    
    
    // ==================================
    //     Constructors
    // ==================================
    
	/**
	 * Constructor.
	 */
    private VerticalAlignment() {
    	
    }

}
