/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/graph/ChromosomeIdeogramSize.java,v $
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
package org.rti.webcgh.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Size of chromosome ideogram
 */
public class ChromosomeIdeogramSize {
    
    // ========================================
    //         Constants
    // ========================================

    /**
     * Small
     */
    public static final ChromosomeIdeogramSize SMALL = new ChromosomeIdeogramSize("S", 200.0 / 300000000.0);
    
    /**
     * Medium
     */
    public static final ChromosomeIdeogramSize MEDIUM = new ChromosomeIdeogramSize("M", 400.0 / 300000000.0);
    
    /**
     * Large
     */
    public static final ChromosomeIdeogramSize LARGE = new ChromosomeIdeogramSize("L", 600.0 / 300000000.0);
    
    
    private static final Map SIZE_INDEX = new HashMap();
    
    static {
        SIZE_INDEX.put(SMALL.name, SMALL);
        SIZE_INDEX.put(MEDIUM.name, MEDIUM);
        SIZE_INDEX.put(LARGE.name, LARGE);
    }
    
    
    // ============================================
    //        Attributes
    // ============================================
    
    private final String name;
    private final double pixelScale;
    
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    
    
    // ======================================
    //       Constructor
    // ======================================
    
    private ChromosomeIdeogramSize(String name, double pixelScale) {
        this.name = name;
        this.pixelScale = pixelScale;
    }
    
    
    // ==========================================
    //      Public methods
    // ==========================================
    
    /**
     * Number of pixels that rendered
     * @param bp Base pair length
     * @return Number of pixels
     */
    public int pixels(long bp) {
        return (int)((double)bp * pixelScale);
    }
    
    
    // ========================================
    //   Static methods
    // ========================================
    
    /**
     * Get chromosome ideogram size
     * @param name Size name
     * @return Chromosome ideogram size
     */
    public static ChromosomeIdeogramSize getChromosomeIdeogramSize(String name) {
        return (ChromosomeIdeogramSize)SIZE_INDEX.get(name);
    }
    
    
    /**
     * Get all sizes
     * @return All chromosome ideogam sizes
     */
    public static ChromosomeIdeogramSize[] chromosomeIdeogramSizes() {
        Collection sizeCol = SIZE_INDEX.values();
        ChromosomeIdeogramSize[] sizes = new ChromosomeIdeogramSize[0];
        sizes = (ChromosomeIdeogramSize[])sizeCol.toArray(sizes);
        return sizes;
    }
}
