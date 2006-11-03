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
import org.rti.webcgh.util.SystemUtils;
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
 * @author djackman
 *
 */
public class SysPropsFileSystemResource extends FileSystemResource {
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(SysPropsFileSystemResource.class);


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
        LOGGER.info ( "Getting Properties from file reference specified in the System Property '" +
                      systemProperty + "'" ) ;
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
        FileInputStream in = null ;
        if ( isEmpty ( this.systemProperty ) )
            LOGGER.error ( "Required String argument not specified - " +
                           "You need to specify a System Property whose value " +
                           "points to an external properties file." ) ;
        else {
            String filename = System.getProperty ( this.systemProperty ) ;
            if ( isEmpty ( filename ) ) {
                LOGGER.error ( "System.getProperty() has no value. " +
                               "You need to set a Ssytem Property whose value points to an " +
                               "external properties file." )  ;
            }
            else {
                in = new FileInputStream( System.getProperty ( this.systemProperty ));
                LOGGER.info ( "Getting Properties from file '" + filename + "'" ) ;
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
