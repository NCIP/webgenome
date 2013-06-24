/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/util/AnalyticOperationParameterValueDisplayProperties.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/
package org.rti.webgenome.webui.util;

import java.io.Serializable;

/**
 * Display properties for a value of an analytic operation parameter
 */
public class AnalyticOperationParameterValueDisplayProperties implements Serializable {
    
    
    // =========================================================
    //         Attributes with accessors/mutators
    // =========================================================
    
    private String displayName = null;
    private String value = null;
    private boolean bean = false;
    

    /**
     * @return Returns the bean.
     */
    public boolean isBean() {
        return bean;
    }
    
    
    /**
     * @param bean The bean to set.
     */
    public void setBean(boolean bean) {
        this.bean = bean;
    }
    
    
    /**
     * @return Returns the name.
     */
    public String getDisplayName() {
        return displayName;
    }
    
    
    /**
     * @param name The name to set.
     */
    public void setDisplayName(String name) {
        this.displayName = name;
    }
    
    
    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * @param value The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
    // ===========================================================
    //          Constructors
    // ===========================================================
    
    /**
     * Constructor
     */
    public AnalyticOperationParameterValueDisplayProperties() {
        super();
    }
}
