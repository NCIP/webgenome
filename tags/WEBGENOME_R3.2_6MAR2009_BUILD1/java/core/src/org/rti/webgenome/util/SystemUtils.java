/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $


*/

package org.rti.webgenome.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;

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
            throw new WebGenomeSystemException( "No External or Internal Properties were loaded." ) ;
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
