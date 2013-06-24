/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/util/DataIdEncoder.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/

package org.rti.webgenome.webui.util;

/**
 * Encodes/decodes database, experiment, and bioassay ids
 * for use in web forms.  Coded identifiers have the following form:
 * 
 * -dbName-DATABASE_NAME-expId-EXPERIMENT_ID-bioId-BIOASSAY_ID
 * 
 * where text in all caps corresponds to encoded values and all
 * other text are field tags.
 */
public class DataIdEncoder {
	
	
    // ==================================================
    //           Field tags
    // ==================================================
    
    /**
     * Database name field tag
     */
    public static final String DB_NAME_TAG = "-dbName-";
    
    /**
     * Experiment id field tag
     */
    public static final String EXP_ID_TAG = "-expId-";
    
    /**
     * Bioassay id field tag
     */
    public static final String BIO_ASSAY_ID_TAG = "-bioId-";
    
    
    // =======================================================
    //          Public methods
    // =======================================================
    
    
    /**
     * Encode IDS
     * @param dbName Database name
     * @param experimentId Experiment ID
     * @param bioAssayId Bioassay ID
     * @return Encoding
     */
    public static String encode(String dbName, String experimentId, String bioAssayId) {
        return
        	DB_NAME_TAG + dbName +
        	EXP_ID_TAG + experimentId +
        	BIO_ASSAY_ID_TAG + bioAssayId;
    }
    
    
    /**
     * Does given string encode IDs?
     * @param encoding Encoding
     * @return T/F
     */
    public static boolean encodesIds(String encoding) {
        return
        	encoding.indexOf(DB_NAME_TAG) >= 0 &&
        	encoding.indexOf(EXP_ID_TAG) >= 0 &&
        	encoding.indexOf(BIO_ASSAY_ID_TAG) >= 0;
    }
    
    
    /**
     * Decode database name
     * @param encoding Encoding
     * @return Database name
     */
    public static String decodeDatabaseName(String encoding) {
        return decodeField(encoding, DB_NAME_TAG, EXP_ID_TAG);
    }
    
    
    /**
     * Decode experiment id
     * @param encoding Encoding
     * @return Experiment id
     */
    public static String decodeExperimentId(String encoding) {
        return decodeField(encoding, EXP_ID_TAG, BIO_ASSAY_ID_TAG);
    }
    
    
    /**
     * Decode bioassay id
     * @param encoding Encoding
     * @return Bioassay id
     */
    public static String decodeBioAssayId(String encoding) {
        return decodeField(encoding, BIO_ASSAY_ID_TAG, null);
    }
    
    
    // =============================================================
    //        Private methods
    // =============================================================
    
    
    private static String decodeField(String encoding, String leftTag, String rightTag) {
        if (! encodesIds(encoding))
            throw new IllegalArgumentException("String '" + encoding + "' does not encode IDs");
        int p = encoding.indexOf(leftTag) + leftTag.length();
        String value = null;
        if (rightTag != null && rightTag.length() > 0) {
            int q = encoding.indexOf(rightTag);
            value = encoding.substring(p, q);
        } else
            value = encoding.substring(p);
        return value;
    }
}
