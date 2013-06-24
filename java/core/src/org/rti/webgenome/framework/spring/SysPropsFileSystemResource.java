/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.framework.spring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import org.springframework.core.io.FileSystemResource;

/**
 * <p>Utility class which tailors the Spring FileSystemResource to load a properties file which
 * is loaded by specifying a System property whose value is a file specification.
 * This class would be called by the Spring Framework as part of its parsing/processing of the
 * <em>applicationContext.xml</em> file.</p>
 * <p>An example of using this:</p>
 * <pre>
 *  <bean
 *      id="propertyConfigurer"
 *      class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
 *      <property name="location">
 *          <!-- PROPERTY LOADING FROM EXTERNALIZED PROPERTIES FILE -->
 *          <bean class="org.rti.webcgh.framework.spring.SysPropsFileSystemResource">
 *              <constructor-arg type="java.lang.String">
 *              <value>webGenome.configFile</value>
 *              </constructor-arg>
 *          </bean>
 *       </property>
 *   </bean>
 * </pre>
 * <p>
 * In the above example, the webGenome.configFile would be a System property defined/loaded by the
 * JVM or application server, e.g. </em>-DwegGenome.configFile=c:/wegcgh-external.properties</em>.
 * </p>
 * <p>IF THE SYSTEM PROPERTY FOR THE EXTERNAL PROPERTIES FILE IS NOT SET, THEN
 * THE INTERNAL PROPERTIES FILE IN webgenome.properties WILL BE USED INSTEAD!</p>
 * @author djackman
 *
 */
public class SysPropsFileSystemResource extends FileSystemResource {
    
    //
    //    S T A T I C S
    //
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(SysPropsFileSystemResource.class);

    /**
     * The name of the internal properties file, to be used in case we can't find the
     * extenal one (which is specified as a System Property as a file reference).
     */
    private static final String INTERNAL_PROPERTIES_FILE = "webgenome.properties" ; 

    //
    //    A T T R I B U T E S
    //
    
    /**
     * The name of the System Property to get the external properties file location from.
     */
    private String systemProperty ;
    
    //
    //    C O N S T R U C T O R
    //
    
    SysPropsFileSystemResource ( String systemProperty ) {
        super( systemProperty ) ;
        this.systemProperty = systemProperty ;
    }
    
    //
    //    P U B L I C    M E T H O D S
    //
    
    /**
     * Overridden getInputStream - takes value from System property from systemProperty
     * and uses this to create and return an InputStream.
     */
    public InputStream getInputStream ( ) throws IOException {
        InputStream in = null ;
        if ( isEmpty ( this.systemProperty ) )
            LOGGER.error ( "You need to specify a System Property whose value " +
                           "points to an external properties file." ) ;
        else {
            String filename = System.getProperty ( this.systemProperty ) ;
            if ( isEmpty ( filename) ) {
                //
                //    The System Property has not been specified, so we'll
                //    throw a warning, but just see if the internal configuration file
                //    is available - and use it instead.
                //
                LOGGER.warn ( "You should set a System Property whose value points to an " +
                              "external properties file." ) ;
                
                in = this.getClass().getClassLoader().getResourceAsStream ( INTERNAL_PROPERTIES_FILE ) ;

                if ( in == null )
                    LOGGER.error( "Tried using internal properties '" + INTERNAL_PROPERTIES_FILE +
                                  "' but couldn't find this in the search path used to load classes. " +
                                  " There aren't any application properties at this point!" ) ;
                else
                    LOGGER.warn( "Using internal properties settings specified in '" +
                                  INTERNAL_PROPERTIES_FILE + "' - not using an external properties file." ) ;
            }
            else {
                in = new FileInputStream( filename );
                LOGGER.info ( "Getting Properties from file '" + filename + "' (specified in System Property '" + this.systemProperty + "')" ) ;
            }
        }
        return in ;
    }
    
    //
    //    P R I V A T E    M E T H O D S
    //
    
    private boolean isEmpty ( String value ) {
        return value == null || value.length() < 1 ;
    }
}
