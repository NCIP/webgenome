/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.analysis;

/**
 * A user configurable property with no business methods
 * beyond what is defined in <code>UserConfigurableProperty</code>
 * except getters and setters.
 * @author dhall
 *
 */
public class SimpleUserConfigurableProperty
extends AbstractUserConfigurableProperty {
	
	
	/**
	 * Constructor.
	 */
	public SimpleUserConfigurableProperty() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param name Name
	 * @param displayName Name displayed to users
	 * @param currentValue Current value of property
	 */
	public SimpleUserConfigurableProperty(final String name,
			final String displayName, final String currentValue) {
		super(name, displayName, currentValue);
	}

	/**
	 * Create a clone of this object.
	 * @return A clone of this object.
	 */
	public final UserConfigurableProperty createClone() {
		SimpleUserConfigurableProperty prop =
			new SimpleUserConfigurableProperty(this.getName(),
					this.getDisplayName(), this.getCurrentValue());
		return prop;
	}
}
