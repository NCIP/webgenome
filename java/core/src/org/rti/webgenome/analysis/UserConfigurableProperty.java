/*
$Revision: 1.2 $
$Date: 2007-06-27 15:47:15 $


*/

package org.rti.webgenome.analysis;

/**
 * Represents a property of an
 * <code>AnalyticOperation</code>
 * that can be configured by the user at run time.
 * @author dhall
 *
 */
public interface UserConfigurableProperty {

	/**
	 * Get name of property.
	 * @return Name of property.
	 */
	String getName();
	
	/**
	 * Set name of property.
	 * @param name Name of property.
	 */
	void setName(String name);
	
	/**
	 * Get name that should be displayed to users.
	 * @return Name that should be displayed to users.
	 */
	String getDisplayName();
	
	
	/**
	 * Set name that should be displayed to users.
	 * @param displayName Name that should be displayed
	 * to users.
	 */
	void setDisplayName(String displayName);
	
	/**
	 * Create a clone of this object.
	 * @return A clone of this object.
	 */
	UserConfigurableProperty createClone();
	
	/**
	 * Get current value of property in string format.
	 * @return Current value of property.
	 */
	String getCurrentValue();
	
	/**
	 * Set current value of property.
	 * @param value Current value
	 */
	void setCurrentValue(String value);
	
	/**
	 * Set primary key used for persistence.
	 * @param id Primary key
	 */
	void setId(Long id);
	
	/**
	 * Get primary key used for persistence.
	 * @return Primary key
	 */
	Long getId();
}
