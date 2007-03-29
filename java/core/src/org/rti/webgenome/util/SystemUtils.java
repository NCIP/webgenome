/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebcghSystemException;

/**
 * Contains general utility methods used by multiple classes.
 */
public final class SystemUtils {
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(SystemUtils.class);
    
    // ================================
    //     Constants
    // ================================
	
    /**
     * Properties applying to application. These are read in
     * from file given by SYS_PROPS_FILE.
     */
	private static Properties applicationProperties = null;

    /** The System property that will indicate the location of an externalized property file,
     * if one exists. */ 
    private static final String EXTERNAL_PROPS_FILE_KEY = "webGenome.configFile" ;
    
    /** Classpath-relative application properties file. */
    private static final String INTERNAL_PROPS_FILE = "webgenome.properties";
    
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
     * Get application properties.
     * @return System properties
     */
    public static Properties getApplicationProperties() {
    	if (applicationProperties == null) {
    		applicationProperties = loadProperties();
        }
    	return applicationProperties;
    }
    
    
    /**
     * Get an application property. Property retrieval is done by checking
     * the System properties first, and if no property is found, then checking the
     * application properties.
     * 
     * @param key Name of property
     * @return property (either System (first) or "Application")
     */
    public static String getApplicationProperty(
            final String key) {
        String propertySetting = System.getProperty ( key ) ;
        if ( propertySetting == null ) { 
            Properties props = getApplicationProperties() ;
            propertySetting = (String) props.get ( key ) ;
        }
    	return propertySetting ;
    }
    
    /**
     * Get long integer format application property defined by
     * the given key. 
     * @param key Property name.
     * @return Long integer format application property.
     */
    public static long getLongApplicationProperty(final String key) {
    	return Long.parseLong(getApplicationProperty(key));
    }
    
    /**
     * Get a system property and determine whether it is set
     * to either 'yes' or 'true'
     * (case insensitive).
     * @param key Property name
     * @return true, if the property exists and
     * is set to yes or true, false otherwise. 
     */
    public static boolean isApplicationPropertySetToTrue(
            final String key) {
        String propertySetting = getApplicationProperty(key);
        return propertySetting != null
             && ("true".equalsIgnoreCase(propertySetting)
               || "yes".equalsIgnoreCase(propertySetting));
    }
    
    /**
     * Handle loading properties from both external and internal properties
     * file. External property settings will override internal property settings.
     * If no external and no internal properties exist, then an Exception will be thrown. 
     * 
     * @return Properties - the full set of internal and external properties.
     * 
     */
    public static Properties loadProperties() {
        
        Properties allProperties = new Properties();
        
        // Load internal properties
        Properties defaultInternalProps = loadProperties ( INTERNAL_PROPS_FILE ) ;
        
        // Load external properties if they have been specified
        String externalPropertiesFile = System.getProperty( EXTERNAL_PROPS_FILE_KEY ) ;
        if ( externalPropertiesFile != null ) {
            LOGGER.info( "Loading from external Properties file '" + externalPropertiesFile + "'" ) ;
            allProperties = loadPropertiesFromFile ( externalPropertiesFile,
                                                     defaultInternalProps ) ;
        }
        else {
            allProperties = defaultInternalProps ;
            String internalPropsInfo = "" ;
            if ( defaultInternalProps.size() > 0 )
                internalPropsInfo = " Using " + defaultInternalProps.size() +
                " internal properties from '" + INTERNAL_PROPS_FILE + "'" ; 

            LOGGER.warn( "No External Properties File specified." +
                          internalPropsInfo ) ;
        }
        
        if ( allProperties.size() == 0 ) {
            throw new WebcghSystemException( "No External or Internal Properties were loaded." ) ;
        }
        else
            LOGGER.info( allProperties.size() + " Properties loaded" ) ;
        
        return allProperties ;
    }
    
    /**
     * Load properties from a file.
     * @param fname File name
     * @param defaultProperties - a list of separate properies which can optionally be
     *        specified as the defaults, in case a property doesn't exist
     *        @see java.util.Properties
     * @return Properties
     */
    public static Properties loadPropertiesFromFile(
            final String fname,
            final Properties defaultProperties ) {
        
        Properties props = null ;
        if ( defaultProperties != null )
            props = new Properties ( defaultProperties ) ; // provide any default, if specified
        else
            props = new Properties();
        
		try {
            FileInputStream in = new FileInputStream ( fname ) ;
            if ( in != null )
                props.load(in);
            else
                LOGGER.warn (
                    "Error creating InputStream. Unable to load properties from file '" +
                    fname + "'." ) ;
		} catch (IOException e) {
            LOGGER.warn ( "Unable to load properties file '" + fname + "'." ) ;
		}
		return props;
    }
    
    /**
     * Load properties from a file.
     * @param fname File name
     * @param defaultProperties - a list of separate properies which can optionally be
     *        specified as the defaults, in case a property doesn't exist
     *        @see java.util.Properties
     * @return Properties
     */
    public static Properties loadProperties(
            final String fname ) {
        
        Properties props = new Properties();
        
        try {
            InputStream in = Thread.currentThread().
                getContextClassLoader().getResourceAsStream(fname);
            if ( in != null )
                props.load(in);
            else
                LOGGER.warn (
                    "Error creating InputStream. Unable to load properties file '" +
                    fname + "'." ) ;
        } catch (IOException e) {
            LOGGER.warn ( "Unable to load properties file '" + fname + "'." ) ;
        }
        return props;
    }
    
    
    /**
     * Find a property.  Look first in system properties, then in application
     * properties (i.e. webgenome.properties file).
     * @param propName Property name
     * @return Property
     * @deprecated
     */
    public static String findProperty(final String propName) {
        String prop = System.getProperty(propName);
        if (prop == null) {
            prop = SystemUtils.getApplicationProperty(propName);
        }
        return prop;
    }
}
