/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/etl/AnnotationColumnMapping.java,v $
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
package org.rti.webcgh.etl;

/**
 * Mapping of column numbers (indexed from 0 to size - 1) from a delimited
 * file to column names in the annotation database.
 */
public class AnnotationColumnMapping {

    
    // =======================================
    //          Attributes with accessors
    // =======================================
    private int nameCol = -1;
    private int chromCol = -1;
    private int startCol = -1;
    private int endCol = -1;
    private int exonStartsCol = -1;
    private int exonEndsCol = -1;
    private int stainCol = -1;
    
    
	/**
	 * @param chromCol Index of column containing chromosome number
	 */
	public void setChromCol(int chromCol) {
		this.chromCol = chromCol;
	}
	
	
	/**
	 * @param endCol Index of column containing chromosome end point
	 */
	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}
	
	
	/**
	 * @param exonEndsCol Index of column containing exon chromosome end points
	 */
	public void setExonEndsCol(int exonEndsCol) {
		this.exonEndsCol = exonEndsCol;
	}
	
	
	/**
	 * @param exonStartsCol Index of column containing exon chromosome start points
	 */
	public void setExonStartsCol(int exonStartsCol) {
		this.exonStartsCol = exonStartsCol;
	}
	
	
	/**
	 * @param nameCol Index of column containing feature name
	 */
	public void setNameCol(int nameCol) {
		this.nameCol = nameCol;
	}
	
	
	/**
	 * @param startCol Index of column containing chromosome start point
	 */
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	
	
    /**
     * @return Index of column containing chromosome
     */
    public int getChromCol() {
        return chromCol;
    }
    
    
    /**
     * @return Index of column containing chromosome position of feature end
     */
    public int getEndCol() {
        return endCol;
    }
    
    
    /**
     * @return Index of column containing chromosome positions of exon ends
     */
    public int getExonEndsCol() {
        return exonEndsCol;
    }
    
    
    /**
     * @return Index of column containing chromosome positions of exon starts
     */
    public int getExonStartsCol() {
        return exonStartsCol;
    }
    
    
    /**
     * @return Index of column containing feature name
     */
    public int getNameCol() {
        return nameCol;
    }
    
    
    /**
     * @return Index of column containing chromosome position of feature start
     */
    public int getStartCol() {
        return startCol;
    }
    
    
	/**
	 * @return Index of column containing stain intensity
	 */
	public int getStainCol() {
		return stainCol;
	}
	
	
	/**
	 * @param stainCol Index of column containing stain intensity
	 */
	public void setStainCol(int stainCol) {
		this.stainCol = stainCol;
	}
	
	
    // ========================================
    //             Constructors
    // ========================================
    
    
    /**
     * Constructor
     */
    public AnnotationColumnMapping() {}
    
    /**
     * Constructor
     * @param nameCol Index of column containing feature name
     * @param chromCol Index of column containing chromosome
     * @param startCol Index of column containing chromosome position of feature start
     * @param endCol Index of column containing chromosome position of feature end
     * @param exonStartsCol Index of column containing chromosome positions of exon starts
     * @param exonEndsCol Index of column containing chromosome positions of exon ends
     */
    public AnnotationColumnMapping
    (
        int nameCol, int chromCol, int startCol, int endCol,
        int exonStartsCol, int exonEndsCol
    ) {
        this.nameCol = nameCol;
        this.chromCol = chromCol;
        this.startCol = startCol;
        this.endCol = endCol;
        this.exonStartsCol = exonStartsCol;
        this.exonEndsCol = exonEndsCol;
    }
        
        


}
