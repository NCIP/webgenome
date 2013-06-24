/*
$Revision: 1.2 $
$Date: 2007-06-27 15:47:15 $


*/

package org.rti.webgenome.analysis;

/**
 * Abstract base class for a property of an
 * <code>AnalyticOperation</code>
 * that can be configured by the user at run time.
 * @author dhall
 *
 */
public abstract class AbstractUserConfigurableProperty
implements UserConfigurableProperty {
    
    
    // ===============================
    //      Attributes
    // ===============================
    
    /**
     * Name of property.  Corresponds to property name
     * of an <code>AnalyticOperation</code>.
     */
    private String name = null;
    
    /** Name of property that would be displayed to users. */
    private String displayName = null;
    
    
    /** Current value of property. */
    private String currentValue = null;
    
    /** Primary key used for persistence. */
    private Long id = null;
    
    
    // ========================================
    //     UserConfigurableProperty interface
    // ========================================
    
    
    /**
     * Set name of property that is displayed to users.
     * @param displayName Name of property that is displayed
     * to users
     */
    public final void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * Get name of the property.  This should
     * correspond to the actual property
     * in an <code>AnalyticOperation</code>
     * class.
     * @return Name of configurable property
     */
    public final String getName() {
        return this.name;
    }
    
    
    /**
     * Get name of property that would actually
     * be displayed to the user.
     * @return Display name of property
     */
    public final String getDisplayName() {
        return this.displayName;
    }
    
	/**
	 * Create a clone of this object.
	 * @return A clone of this object.
	 */
	public abstract UserConfigurableProperty createClone();
	
	/**
	 * Get current value of property in string format.
	 * @return Current value of property.
	 */
	public final String getCurrentValue() {
		return this.currentValue;
	}
	
    /**
     * Get primary key used for persistence.
     * @return Primary key
     */
    public final Long getId() {
		return id;
	}

    /**
     * Set primary key used for persistence.
     * @param id Primary key
     */
	public final void setId(final Long id) {
		this.id = id;
	}
    
    // =====================================
    //    Additional getters and setters
    // =====================================
    
    /**
     * Set name of the property.  This should
     * correspond to the actual property
     * in an <code>AnalyticOperation</code>
     * class.
     * @param name Name of configurable property
     */
    public final void setName(final String name) {
        this.name = name;
    }
    
    
    /**
     * Set current value of property.
     * @param currentValue Current value in string format.
     */
    public final void setCurrentValue(final String currentValue) {
    	this.currentValue = currentValue;
    }
    
    
    // ============================
    //      Constructors
    // ============================
    

	/**
     * Constructor.
     */
    public AbstractUserConfigurableProperty() {
        
    }
    
    
    /**
     * Constructor.
     * @param name Name of property that corresponds to the
     * actual property within a <code>AnalyticOperation</code>
     * @param displayName Name of property displayed to
     * users
     * @param currentValue Current value of property
     */
    public AbstractUserConfigurableProperty(final String name,
            final String displayName, final String currentValue) {
        this.name = name;
        this.displayName = displayName;
        this.currentValue = currentValue;
    }
}
