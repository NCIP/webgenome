/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/util/Attribute.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/


package org.rti.webgenome.webui.util;


/**
 * Class contains constants used as session and request attributes.  Also
 * contains parameter name prefixes.
 */
public class Attribute {
    
    // -----------------------------------------------------------------
	//      Constants for attribute keys (e.g. Session attributes)
	// -----------------------------------------------------------------
	
    /**
     * SVG graphic
     */
    public static final String SVG = "attSVG";
    
    /**
     * Exception
     */
    public static final String EXCEPTION = "attException";
    
    
	/**
	 * Annotated feature types
	 */
	public static final String FEATURES = "attFeatures";
	
	/**
	 * New analytical pipeline
	 */
	public static final String NEW_PIPELINE = "attNewPipeline";
	
	/**
	 * Data set
	 */
	public static final String DATA_SET = "attDataSet";
	
	/**
	 * Data set for downloading
	 */
	public static final String DOWNLOAD_TYPE = "download_type";
	
	/**
	 * Annotation report
	 */
	public static final String ANNOTATION_REPORT = "annotation_plot";
	
	/**
	 * Annotated feature type
	 */
	public static final String ANNOTATION_TYPE = "annotation_type";
	
    // ----------------------------------------------------
	//             Constants used as prefixes
	// ----------------------------------------------------
	
	/**
	 * Feature type
	 */
    public static final String PRE_FEAT_TYPE = "preFeatType-";    
}
