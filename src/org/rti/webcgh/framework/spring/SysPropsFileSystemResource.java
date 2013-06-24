/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision$
$Date$

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

package org.rti.webcgh.framework.spring;

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
 * THE INTERNAL PROPERTIES FILE IN conf/webcgh.properties WILL BE USED INSTEAD!</p>
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
    private static final String INTERNAL_PROPERTIES_FILE = "conf/webcgh.properties" ; 

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
