/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/util/AnalyticOperationDisplayProperties.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/
package org.rti.webgenome.webui.util;

import java.io.Serializable;

/**
 * Display properties for an analytic operation
 */
public class AnalyticOperationDisplayProperties implements Serializable {
    
    
    // ======================================================
    //         Attributes with accessors/mutators
    // ======================================================
    
    private String beanName = null;
    private String displayName = null;
    private AnalyticOperationParameterDisplayProperties[] parameterDisplayProperties = null;
    private String className = null;
    

    /**
     * @return Returns the beanName.
     */
    public String getBeanName() {
        return beanName;
    }
    
    
    /**
     * @param beanName The beanName to set.
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    
    /**
     * @return Returns the displayName.
     */
    public String getDisplayName() {
        return displayName;
    }
    
    
    /**
     * @param displayName The displayName to set.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
    /**
     * @return Returns the parameterDisplayProperties.
     */
    public AnalyticOperationParameterDisplayProperties[] getParameterDisplayProperties() {
        return parameterDisplayProperties;
    }
    
    
    /**
     * @param parameterDisplayProperties The parameterDisplayProperties to set.
     */
    public void setParameterDisplayProperties(
            AnalyticOperationParameterDisplayProperties[] parameterDisplayProperties) {
        this.parameterDisplayProperties = parameterDisplayProperties;
    }
        
    
    /**
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
    }
    
    
    /**
     * @param className The className to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }
    
    
    // ========================================================
    //         Constructors
    // ========================================================
    
    
    /**
     * Constructor
     */
    public AnalyticOperationDisplayProperties() {
        super();
    }
}
