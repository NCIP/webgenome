/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/


package org.rti.webgenome.graphics.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.rti.webgenome.util.ColorUtils;
import org.rti.webgenome.util.SystemUtils;

/**
 * Implementation of ColorMapper interface.  Color mapping configurations
 * are read from a properties file on the classpath.  Colors are encoded
 * as RGB hexidecimal values (i.e. 'FF0066' or '#FF0066').
 */
public final class ClassPathPropertiesFileRgbHexidecimalColorMapper
	implements ColorMapper {
    
	/** Logger. */
    private static final Logger LOGGER =
    	Logger.getLogger(
    			ClassPathPropertiesFileRgbHexidecimalColorMapper.class);
    
    
    // ===============================
    //     Attributes
    // ===============================
    
    /** Maps color names to colors. */
    private final Map<String, Color> colorIndex =
    	new HashMap<String, Color>();
    
    
    // ====================================
    //     Constructor
    // ====================================
    
    /**
     * Constructor.
     * @param fileName Properties file name
     */
    public ClassPathPropertiesFileRgbHexidecimalColorMapper(
    		final String fileName) {
        Properties props = SystemUtils.loadProperties(fileName);
        for (Iterator it = props.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            Color value = ColorUtils.getColor(props.getProperty(key));
            this.colorIndex.put(key, value);
        }
    }
    
    
    // =====================================
    //   Methods in ColorMapper interface
    // =====================================
    
    /**
     * Get color associated with key.
     * @param key A key
     * @return A color
     */
    public Color getColor(final Object key) {
        Color color = colorIndex.get(key);
        if (color == null) {
            LOGGER.warn("Color for '" + key.toString() + "' not found");
        }
        return color;
    }

}
