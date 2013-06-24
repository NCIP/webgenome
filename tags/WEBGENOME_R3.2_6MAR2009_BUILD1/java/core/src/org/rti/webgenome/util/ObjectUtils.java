/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/ObjectUtils.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/
package org.rti.webgenome.util;

import java.lang.reflect.Method;

/**
 * Utility methods for objects
 */
public class ObjectUtils {

    /**
     * Null-tolerant equals method.
     * @param o1 An object
     * @param o2 Another object
     * @return T/F
     */
    public static boolean equal(Object o1, Object o2) {
    	if (o1 == null && o2 != null)
    	    return false;
    	if (o1 != null && o2 == null)
    	    return false;
    	if (o1 != null && o2 != null) {
    		if (! o1.getClass().equals(o2.getClass()))
    			return false;
    		try {
    			Method method = o1.getClass().getMethod("equals", 
    				new Class[]{o1.getClass()});
    			Boolean rval = (Boolean)method.invoke(o1, new Object[]{o2});
    			if (! rval.booleanValue())
    				return false;
    		} catch (Exception e) {
    			return false;
    		}
    	}
    	return true;
    }

}
