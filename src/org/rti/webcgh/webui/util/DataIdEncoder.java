/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/util/DataIdEncoder.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.webui.util;

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
