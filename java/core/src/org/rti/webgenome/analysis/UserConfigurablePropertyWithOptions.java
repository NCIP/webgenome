/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a property of an <code>AnalyticOperation</code>
 * with a discrete set of options that can be set by a user at runtime.  
 * @author dhall
 *
 */
public class UserConfigurablePropertyWithOptions
extends AbstractUserConfigurableProperty {

    // ====================================
    //      Attributes
    // ====================================
    
    /**
     * Options available for property.  Map keys represent
     * coded option values.  Map values represent option
     * display names.
     */
    private Map<String, String> options = new HashMap<String, String>();
    
    // ===============================
    //      Getters/setters
    // ===============================

    /**
     * Get options available for property.
     * @return Map of options.  Map keys represent
     * coded option values.  Map values represent option
     * display names.
     */
    public final Map<String, String> getOptions() {
        return options;
    }

    
    /**
     * Set options available for property.
     * @param options Map of options.  Map keys represent
     * coded option values.  Map values represent option
     * display names.
     */
    public final void setOptions(final Map<String, String> options) {
        this.options = options;
    }

    
    // ==============================
    //     Constructors
    // ==============================

    /**
     * Constructor.
     */
    public UserConfigurablePropertyWithOptions() {
        super();
    }


    /**
     * Constructor.
     * @param name Name of property that corresponds to the
     * actual property within a <code>AnalyticOperation</code>
     * @param displayName Name of property displayed to
     * users
     * @param currentValue Current value of property
     */
    public UserConfigurablePropertyWithOptions(final String name,
            final String displayName, final String currentValue) {
        super(name, displayName, currentValue);
    }
    
    
    /**
     * Constructor.
     * @param name Name of property that corresponds to the
     * actual property within a <code>AnalyticOperation</code>
     * @param displayName Name of property displayed to
     * users
     * @param options Map of options.  Map keys represent
     * coded option values.  Map values represent option
     * display names.
     * @param currentValue Current value of property
     */
    public UserConfigurablePropertyWithOptions(final String name,
            final String displayName,
            final Map<String, String> options,
            final String currentValue) {
        this(name, displayName, currentValue);
        this.options = options;
    }
    
    
    // ===============================
    //     Business methods
    // ===============================
    
    /**
     * Add an optional value.
     * @param code Coded value of option
     * @param displayName Name of option displayed
     * to users
     */
    public final void addOption(final String code,
            final String displayName) {
        this.options.put(code, displayName);
    }
    
    
    // ======================================
    //   UserConfigurableProperty interface
    // ======================================
    
	/**
	 * Create a clone of this object.
	 * @return A clone of this object.
	 */
	public final UserConfigurableProperty createClone() {
		UserConfigurablePropertyWithOptions clone =
			new UserConfigurablePropertyWithOptions(this.getName(),
					this.getDisplayName(), this.getCurrentValue());
		clone.setOptions(this.getOptions());
		return clone;
	}
}
