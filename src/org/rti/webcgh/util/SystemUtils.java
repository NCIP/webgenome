/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/SystemUtils.java,v $
$Revision: 1.2 $
$Date: 2006-08-01 19:37:11 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;


import java.util.Properties;

import java.io.InputStream;
import java.io.IOException;

import org.rti.webcgh.core.WebcghSystemException;



/**
 * Contains general utility methods used by multiple classes.
 */
public final class SystemUtils {
    
    // ================================
    //     Constants
    // ================================
	
    /**
     * Properties applying to application. These are read in
     * from file given by SYS_PROPS_FILE.
     */
	private static Properties applicationProperties = null;
    
    /** Classpath-relative application properties file. */
    private static final String SYS_PROPS_FILE = "conf/webcgh.properties";
    
    /** Classpath-relative file containing properties for unit test classes. */
    private static final String UNIT_TEST_PROPS_FILE =
        "conf/unit_test.properties";

    
    // ========================
    //   Constructor
    // ========================
    
    /**
     * Constructor.  This is declared private
     * to protect against instantiation of
     * instances of this class.
     */
    private SystemUtils() {
        
    }
    
    
    // ===============================
    //      Utility methods
    // ===============================
    
    /**
     * Get system properties.
     * @return System properties
     */
    public static synchronized Properties getApplicationProperties() {
    	if (applicationProperties == null) {
    		applicationProperties = loadProperties(SYS_PROPS_FILE);
        }
    	return applicationProperties;
    }
    
    
    /**
     * Get a system property.
     * @param key Name of property
     * @return System property
     */
    public static synchronized String getApplicationProperty(
            final String key) {
    	Properties props = getApplicationProperties();
    	return (String) props.get(key);
    }
    
    
    /**
     * Load properties from a file.
     * @param fname File name
     * @return Properties
     */
    public static synchronized Properties loadProperties(
            final String fname) {
    	Properties props = new Properties();
		try {
			InputStream in = Thread.currentThread().
                getContextClassLoader().getResourceAsStream(fname);
			props.load(in);
		} catch (IOException e) {
			throw new WebcghSystemException(
				"Unable to load properties file '" + fname + "'");
		}
		return props;
    }
    
    
    /**
     * Find a property.  Look first in system properties, then in application
     * properties (i.e. webcgh.properties file).
     * @param propName Property name
     * @return Property
     */
    public static String findProperty(final String propName) {
        String prop = System.getProperty(propName);
        if (prop == null) {
            prop = SystemUtils.getApplicationProperty(propName);
        }
        return prop;
    }
    
    
    /**
     * Get specified unit test property.
     * @param key Name of property
     * @return Property value
     */
    public static String getUnitTestProperty(final String key) {
        Properties props = SystemUtils.loadProperties(
                SystemUtils.UNIT_TEST_PROPS_FILE);
        if (props == null) {
            throw new WebcghSystemException(
                    "Cannot find 'unit_test.properties' file");
        }
        return props.getProperty(key);
    }
}
