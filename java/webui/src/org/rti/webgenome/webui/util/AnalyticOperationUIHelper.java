/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/util/AnalyticOperationUIHelper.java,v $
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $



*/
package org.rti.webgenome.webui.util;

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Helps in the user interface display of analytic operations
 */
public class AnalyticOperationUIHelper {
    
    // =================================================================
    //       Attributes with accessors/mutators
    // =================================================================
    
    AnalyticOperationDisplayProperties[] analyticOperationDisplayProperties = null;
    

    /**
     * @return Returns the analyticOperationDisplayProperties.
     */
    public AnalyticOperationDisplayProperties[] getAnalyticOperationDisplayProperties() {
        return analyticOperationDisplayProperties;
    }
    
    
    /**
     * @param analyticOperationDisplayProperties The analyticOperationDisplayProperties to set.
     */
    public void setAnalyticOperationDisplayProperties(
            AnalyticOperationDisplayProperties[] analyticOperationDisplayProperties) {
        this.analyticOperationDisplayProperties = analyticOperationDisplayProperties;
    }
    
    
    // =======================================================
    //           Constructors
    // =======================================================
    
    
    /**
     * Constructor
     */
    public AnalyticOperationUIHelper() {
        super();
    }
    
    
    // ======================================================
    //         Public methods
    // ======================================================
    
    /**
     * Get display properties associated with analytic operation with given bean name
     * @param beanName Name of analytic operation bean
     * @return Analytic operation display properties
     */
    public AnalyticOperationDisplayProperties getAnalyticOperationDisplayProperties(String beanName) {
        AnalyticOperationDisplayProperties props = null;
        for (int i = 0; i < this.analyticOperationDisplayProperties.length && props == null; i++) {
            AnalyticOperationDisplayProperties currProps = this.analyticOperationDisplayProperties[i];
            if (beanName.equals(currProps.getBeanName()))
                props = currProps;
        }
        return props;
    }
    
    
    /**
     * Get all analytic operation display properties for analytic operations that are of given
     * class type
     * @param klass A class.  May be an interface or superclass.
     * @return Analytic operations
     */
    public AnalyticOperationDisplayProperties[] getAnalyticOperationDisplayProperties(Class klass) {
        AnalyticOperationDisplayProperties[] props = new AnalyticOperationDisplayProperties[0];
        Collection propsCol = new ArrayList();
        for (int i = 0; i < this.analyticOperationDisplayProperties.length; i++) {
            AnalyticOperationDisplayProperties prop = this.analyticOperationDisplayProperties[i];
            Class currKlass = null;
            try {
                currKlass = Class.forName(prop.getClassName());
            } catch (ClassNotFoundException e) {
                throw new WebGenomeSystemException("Class '" + prop.getClassName() + "' not found", e);
            }
            if (klass.isAssignableFrom(currKlass))
                propsCol.add(prop);
        }
        props = (AnalyticOperationDisplayProperties[])propsCol.toArray(props);
        return props;
    }
    
    
    /**
     * Return analytic operation display properties for exact class indicated 
     * @param klass A class
     * @return Analytic operation display properties
     */
    public AnalyticOperationDisplayProperties getExactAnalyticOperationDisplayProperties(Class klass) {
        AnalyticOperationDisplayProperties props = null;
        for (int i = 0; i < this.analyticOperationDisplayProperties.length && props == null; i++) {
            AnalyticOperationDisplayProperties currProps = this.analyticOperationDisplayProperties[i];
            if (currProps.getClassName().equals(klass.getName()))
                props = currProps;
        }
        return props;
    }
        
}
