/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/


package org.rti.webgenome.graphics.util;

import java.awt.Color;

/**
 * Maps colors to keys.
 */
public interface ColorMapper {
    
    
    /**
     * Get color associated with key.
     * @param key Key of color
     * @return A color
     */
    Color getColor(Object key);

}
