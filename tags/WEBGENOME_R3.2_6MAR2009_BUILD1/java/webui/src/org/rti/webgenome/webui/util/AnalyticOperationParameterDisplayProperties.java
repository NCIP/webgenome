/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/util/AnalyticOperationParameterDisplayProperties.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/
package org.rti.webgenome.webui.util;

import java.io.Serializable;

/**
 * Display properties for analytic operation parameter
 */
public class AnalyticOperationParameterDisplayProperties implements Serializable {
    
    
    // ====================================================
    //       Attributes with accessors/mutators
    // ====================================================
    
    private String parameterName = null;
    private String displayName = null;
    private AnalyticOperationParameterValueDisplayProperties[] optionalValueProperties = null;
    

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
     * @return Returns the optionalValueProperties.
     */
    public AnalyticOperationParameterValueDisplayProperties[] getOptionalValueProperties() {
        return optionalValueProperties;
    }
    
    
    /**
     * @param optionalValueProperties The optionalValueProperties to set.
     */
    public void setOptionalValueProperties(
            AnalyticOperationParameterValueDisplayProperties[] optionalValueProperties) {
        this.optionalValueProperties = optionalValueProperties;
    }
    
    
    /**
     * @return Returns the parameterName.
     */
    public String getParameterName() {
        return parameterName;
    }
    
    
    /**
     * @param parameterName The parameterName to set.
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    
    
    // ===========================================================
    //             Constructors
    // ===========================================================
    
    /**
     * Constructor
     */
    public AnalyticOperationParameterDisplayProperties() {
        super();
    }
}
