/*
$Revision: 1.1 $
$Date: 2006-10-02 18:42:21 $

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

/**
 * Represents a plot of data.  A plot is an
 * aggregate of image files with some additional
 * metadata.  If there are N bioassays in the
 * plot, there will be N + 1 separate image files.
 * In one file, no bioassays will be highlighted.
 * In the remainder, one bioassay will be highlighted.
 */
public class Plot implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    // =============================
    //      Attributes
    // =============================
    
    /** Primary key identifier for persistence. */
    private Long id = null;
    
    /** Plot name. */
    private String name = null;
    
    /**
     * Map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
     */
    private Map<String, String> imageFileMap = new HashMap<String, String>();
    
    // ================================
    //       Getters/setters
    // ================================

    /**
     * Get primary key identifier.
     * @return Primary key identifier.
     */
	public final Long getId() {
		return id;
	}

	/**
	 * Set primary key identifier.
	 * @param id Primary key identifier.
	 */
	public final void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Get map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
	 * @return Map of image names to image file names.
	 */
	public final Map<String, String> getImageFileMap() {
		return imageFileMap;
	}

	
	/**
	 * Set map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
	 * @param imageFileMap Map of image names to image file
	 * names.
	 */
	public final void setImageFileMap(final Map<String, String> imageFileMap) {
		this.imageFileMap = imageFileMap;
	}

	/**
	 * Get name of plot.
	 * @return Plot name.
	 */
	public final String getName() {
		return name;
	}

	
	/**
	 * Set name of plot.
	 * @param name Plot name.
	 */
	public final void setName(final String name) {
		this.name = name;
	}
	
	
	// ============================
	//     Constructors
	// ============================
	
	/**
	 * Constructor.
	 */
	public Plot() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param name Name of plot.
	 */
	public Plot(final String name) {
		this.name = name;
	}
	
	
	// ===================================
	//        Business methods
	// ===================================
	
	/**
	 * Add image file.
	 * @param imageName Name of image.
	 * @param fileName Name of image file.  Note this
	 * is not an absolute path; clients should know how
	 * to generate an absolute path.
	 */
	public final void addImageFile(final String imageName,
			final String fileName) {
		this.imageFileMap.put(imageName, fileName);
	}
}
