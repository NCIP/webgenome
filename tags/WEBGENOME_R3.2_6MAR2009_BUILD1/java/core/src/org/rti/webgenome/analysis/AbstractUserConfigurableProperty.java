/*
$Revision: 1.2 $
$Date: 2007-06-27 15:47:15 $

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
