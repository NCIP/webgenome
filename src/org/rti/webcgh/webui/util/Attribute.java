/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/util/Attribute.java,v $
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
