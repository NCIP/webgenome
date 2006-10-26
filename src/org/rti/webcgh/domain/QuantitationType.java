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

package org.rti.webcgh.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.util.SystemUtils;

/**
 * Quantitation type for array experiment values.
 * @author dhall
 *
 */
public final class QuantitationType implements Serializable {
    
    // =============================
    //   Constants
    // =============================
    
    /** Log2 ratio. */
    public static final QuantitationType LOG_2_RATIO =
        new QuantitationType("log2ratio", "Log2 Ratio");
    
    /** Loss of heterozygosity. */
    public static final QuantitationType LOH =
    	new QuantitationType("loh", "LOH");
    
    /** Maps quantitation type names of quantitation types. */
    private static final Map<String, QuantitationType> INDEX =
    	new HashMap<String, QuantitationType>();
    
    static {
    	INDEX.put(LOG_2_RATIO.getId(), LOG_2_RATIO);
    	INDEX.put(LOH.getId(), LOH);
    }
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // =====================
    //      Attributes
    // =====================
    
    /** Name of quantitation type. */
    private final String name;
    
    /** Identifier. */
    private final String id;
    
    // =======================
    //     Getters/setters
    // =======================
    
    /**
     * Get name of quantitation type.
     * @return Name of quantitation type
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get identifier.
     * @return Identifier.
     */
    public String getId() {
    	return this.id;
    }
    
    
    // ==========================
    //     Constructors
    // ==========================
    
    /**
     * Constructor.
     * @param id Identifier
     * @param name Name of quantitation type
     */
    public QuantitationType(final String id, final String name) {
    	this.id = id;
        this.name = name;
    }
    
    
    // ============================
    //    Business methods
    // ============================
    
    /**
     * Get quantitation type that corresponds to given name.
     * @param name Name of quantitation type
     * @return Quantitation type
     */
    public static QuantitationType getQuantitationType(final String name) {
    	return INDEX.get(name);
    }
}
