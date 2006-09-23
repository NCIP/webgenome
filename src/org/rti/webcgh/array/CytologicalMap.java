/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/CytologicalMap.java,v $
$Revision: 1.9 $
$Date: 2006-09-23 05:02:23 $

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
package org.rti.webcgh.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.graphics.util.ColorMapper;
import org.rti.webcgh.graphics.widget.ChromosomeIdeogram;

/**
 * Map of cytobands
 */
public class CytologicalMap {
    
    
    // ============================================
    //         Attributes
    // ============================================
    
    private Long id = null;
    protected List cytobands = new ArrayList();
    private Chromosome chromosome;
    private long centromereStart = -1;
    private long centromereEnd = -1;
    private long length = 0;
    
    
    /**
     * @return Returns the centromereEnd.
     */
    public long getCentromereEnd() {
        return centromereEnd;
    }
    
    
    /**
     * @param centromereEnd The centromereEnd to set.
     */
    public void setCentromereEnd(long centromereEnd) {
        this.centromereEnd = centromereEnd;
    }
    
    
    /**
     * @return Returns the centromereStart.
     */
    public long getCentromereStart() {
        return centromereStart;
    }
    
    
    /**
     * @param centromereStart The centromereStart to set.
     */
    public void setCentromereStart(long centromereStart) {
        this.centromereStart = centromereStart;
    }
    
    
    /**
     * @return Returns the chromosome.
     */
    public Chromosome getChromosome() {
        return chromosome;
    }
    
    
    /**
     * @param chromosome The chromosome to set.
     */
    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }
    
    
    /**
     * @return Returns the cytobands.
     */
    public List getCytobands() {
        return cytobands;
    }
    
    
    /**
     * @param cytobands The cytobands to set.
     */
    public void setCytobands(List cytobands) {
        this.cytobands = cytobands;
        for (Iterator it = this.cytobands.iterator(); it.hasNext();) {
            Cytoband cytoband = (Cytoband)it.next();
            if (cytoband.getEnd() > this.length)
                this.length = cytoband.getEnd();
        }
    }
    
    
    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }
    
    
    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    // ============================================
    //         Constructors
    // ============================================
    
    /**
     * Constructor
     */
    public CytologicalMap() {}
    
    /**
     * Constructor
     * @param chromosome Chromosome
     * @param centromereStart Centromere start point
     * @param centromereEnd Centromere end point
     */
    public CytologicalMap(Chromosome chromosome, long centromereStart, long centromereEnd) {
        this(chromosome);
        this.centromereStart = centromereStart;
        this.centromereEnd = centromereEnd;
    }
    
    
    /**
     * Constructor
     * @param chromosome Chromosome
     */
    public CytologicalMap(Chromosome chromosome) {
        this.chromosome = chromosome;
    }
    
    
    // ==================================================
    //      Public methods
    // ==================================================
    
    /**
     * Add a cytoband
     * @param cytoband A cytoband
     */
    public void addCytoband(Cytoband cytoband) {
    	this.cytobands.add(cytoband);
    	if (cytoband.inCentromere()) {
	    	if (this.centromereStart < 0)
	    	    this.centromereStart = cytoband.getStart();
	    	else if (cytoband.getStart() < this.centromereStart)
	    	    this.centromereStart = cytoband.getStart();
	    	if (this.centromereEnd < 0)
	    	    this.centromereEnd = cytoband.getEnd();
	    	else if (cytoband.getEnd() > this.centromereEnd)
	    	    this.centromereEnd = cytoband.getEnd();
    	}
    	if (cytoband.getEnd() > this.length)
    	    this.length = cytoband.getEnd();
    }
    
    
    /**
     * Graph this
     * @param map A map
     * @param colorMapper A color mapper
     */
    public void graph(ChromosomeIdeogram map, ColorMapper colorMapper) {
    	for (Iterator it = this.cytobands.iterator(); it.hasNext();) {
    		Cytoband cytoband = (Cytoband)it.next();
    		cytoband.graph(map, colorMapper);
    	}
//    	if (this.centromereStart >= 0 && this.centromereEnd >= 0) {
//	    	Warper warper = new CentromereWarper(map.getLength(), 
//	    			(int)(this.centromereStart * map.getScale()), 
//					(int)(this.centromereEnd * map.getScale()));
//	    	map.setWarper(warper);
//    	}
    }

    
    /**
     * Length in BP
     * @return Length in BP
     */
    public long length() {
        return this.length;
    }
}
