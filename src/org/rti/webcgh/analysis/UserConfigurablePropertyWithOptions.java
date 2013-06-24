/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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

package org.rti.webcgh.analysis;

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
