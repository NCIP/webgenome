/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.5 $
$Date: 2008-10-23 16:17:06 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.client.QuantitationTypes;
import org.rti.webgenome.util.SystemUtils;

/**
 * Quantitation type for array experiment values.
 * @author dhall
 *
 */
public final class QuantitationType implements Serializable {
    
    // =============================
    //   Constants
    // =============================
    
    /** Fold change. */
    public static final QuantitationType FOLD_CHANGE =
    	new QuantitationType(QuantitationTypes.FOLD_CHANGE,
    			"Gene Expression Fold Change",
    			true);
    
    /** Log2 ratio fold change. */
    public static final QuantitationType LOG_2_RATIO_FOLD_CHANGE =
        new QuantitationType(QuantitationTypes.FOLD_CHANGE_LOG2_RATIO,
        		"Gene Expression Log2 Ratio Fold Change", true);
    
    /** Copy number. */
    public static final QuantitationType COPY_NUMBER =
    	new QuantitationType(QuantitationTypes.COPY_NUMBER, "Copy Number",
    			false);
    
    /** Lo2 ratio copy number. */
    public static final QuantitationType LOG_2_RATIO_COPY_NUMBER =
    	new QuantitationType(QuantitationTypes.COPY_NUMBER_LOG2_RATION,
    			"Log2 Ratio Copy Number", false);
    
    /** Loss of heterozygosity. */
    public static final QuantitationType LOH =
    	new QuantitationType(QuantitationTypes.LOH, "LOH", false);
    
    /** TODO: Define "Other" in QuantitationTypes class that is part of the 
     * webGenomeClientEJB.jar.
     * */
    public static final QuantitationType Other =
    	new QuantitationType("Other", "Other", false);
    
    
    public String otherValue = "";
    
    /** Maps quantitation type names of quantitation types. */
    private static final Map<String, QuantitationType> INDEX =
    	new HashMap<String, QuantitationType>();
    
    
    
    static {
    	INDEX.put(FOLD_CHANGE.getId(), FOLD_CHANGE);
    	INDEX.put(LOG_2_RATIO_FOLD_CHANGE.getId(), LOG_2_RATIO_FOLD_CHANGE);
    	INDEX.put(COPY_NUMBER.getId(), COPY_NUMBER);
    	INDEX.put(LOG_2_RATIO_COPY_NUMBER.getId(), LOG_2_RATIO_COPY_NUMBER);
    	INDEX.put(LOH.getId(), LOH);
    	INDEX.put(Other.getId(), Other) ;
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
    
    /** Flag indicating the quantitation type is expression data. */
    private final boolean expressionData;
    
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
    
    /**
     * Is the quantitation type indicitive of expression data?
     * @return T/F
     */
    public boolean isExpressionData() {
    	return this.expressionData;
    }
    
    
    // ==========================
    //     Constructors
    // ==========================
    
    /**
     * Constructor.
     * @param id Identifier
     * @param name Name of quantitation type
     * @param isExpressionData Is quantitation type expression data?
     */
    public QuantitationType(final String id, final String name,
    		final boolean isExpressionData) {
    	this.id = id;
        this.name = name;
        this.otherValue = null ;
        this.expressionData = isExpressionData;
    }
    
    
    // ============================
    //    Business methods
    // ============================
    
    /**
     * Get quantitation type that corresponds to given name.
     * @param id ID of quantitation type
     * @return Quantitation type
     */
    public  static QuantitationType getQuantitationType(final String id) {
    	return INDEX.get(id);
    }
    
    
    /**
     * Get index of quantitation types.  Keys are IDs.  Values are
     * quantitation types.
     * @return Index of quantitation types.
     */
    public static Map<String, QuantitationType> getQuantitationTypeIndex() {
    	return INDEX;
    }

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}
}
