/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/GenomeFeature.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:34:38 $

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
package org.rti.webcgh.deprecated.array;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.rti.webcgh.core.WebcghSystemException;

/**
 * Feature of genome with start and end points
 */
public class GenomeFeature {
	
	private static final String[] annotationColNames = {
			"Feature Name", "Chromosome Start", "Chromosome End",
			"Additional Information 1", "Additional Information 2"
	 };
    
    
    // =======================================
    //       Attributes
    // =======================================
    
    protected Long id = null;
    protected String name = null;
    protected long start = -1;
    protected long end = -1;
    protected Chromosome chromosome = null;
    protected GenomeFeatureDataSet genomeFeatureDataSet = null;
    protected List exons = new ArrayList();
    

	/**
	 * @return Returns the exons.
	 */
	public List getExons() {
		return exons;
	}
	
	
	/**
	 * @param exons The exons to set.
	 */
	public void setExons(List exons) {
		this.exons = exons;
	}
	
	
	/**
	 * @return Returns the genomeFeatureDataSet.
	 */
	public GenomeFeatureDataSet getGenomeFeatureDataSet() {
		return genomeFeatureDataSet;
	}
	
	
	/**
	 * @param genomeFeatureDataSet The genomeFeatureDataSet to set.
	 */
	public void setGenomeFeatureDataSet(
			GenomeFeatureDataSet genomeFeatureDataSet) {
		this.genomeFeatureDataSet = genomeFeatureDataSet;
	}
	
	
    /**
     * @return Returns the chomosome.
     */
    public Chromosome getChromosome() {
        return chromosome;
    }
    
    
    /**
     * @param chomosome The chomosome to set.
     */
    public void setChromosome(Chromosome chomosome) {
        this.chromosome = chomosome;
    }
    
    
    /**
     * @return Returns the end.
     */
    public long getEnd() {
        return end;
    }
    
    
    /**
     * @param end The end to set.
     */
    public void setEnd(long end) {
        this.end = end;
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
    
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @return Returns the start.
     */
    public long getStart() {
        return start;
    }
    
    
    /**
     * @param start The start to set.
     */
    public void setStart(long start) {
        this.start = start;
    }
    

    
    // =========================================
    //        Constructors
    // =========================================
    
    
    /**
     * Constructor
     */
    public GenomeFeature() {}
    
    
    /**
     * Constructor
     * @param name Name
     * @param start Start point on chromosome
     * @param end End point on chromosome
     * @param chromosome Chromosome
     * @param genomeFeatureDataSet Genome feature data set
     */
    public GenomeFeature(String name, long start, long end,
            Chromosome chromosome, GenomeFeatureDataSet genomeFeatureDataSet) {
        super();
        this.name = name;
        this.start = start;
        this.end = end;
        this.chromosome = chromosome;
        this.genomeFeatureDataSet = genomeFeatureDataSet;
    }
    
    
    // =========================================
    //      Public methods
    // =========================================
    
    /**
     * Add exon
     * @param exon An exon
     */
    public void addExon(Exon exon) {
    	if (this.exons == null)
    		this.exons = new ArrayList();
    	this.exons.add(exon);
    }
    
    
    /**
     * Add field values
     * @param row Row
     * @param cellStyle Style
     */
    public void addFieldValues(HSSFRow row, HSSFCellStyle cellStyle) {
		  HSSFCell cell = row.createCell((short)0);
		  cell.setCellValue(this.name);
		  cell.setCellStyle(cellStyle);

		  cell = row.createCell((short)1);
		  cell.setCellValue(this.start);
		  cell.setCellStyle(cellStyle);

		  cell = row.createCell((short)2);
		  cell.setCellValue(this.end);
		  cell.setCellStyle(cellStyle);  

		  cell = row.createCell((short)3);
		  String url = this.ucscUrl().toString();
		  String hyperlink = "HYPERLINK(\"" + url + "\",\"UCSC\")"; 
		  cell.setCellFormula(hyperlink);
		  cell.setCellStyle(cellStyle);
		  
		  cell = row.createCell((short)4);
		  url = this.cgapUrl().toString();
		  hyperlink = "HYPERLINK(\"" + url + "\",\"CGAP\")"; 
		  cell.setCellFormula(hyperlink);
		  cell.setCellStyle(cellStyle);		
    }
    
    
    /**
     * Get URL of feature in UCSC genome browser
     * @return URL
     */
    public URL ucscUrl() {
    	return null;
    }
    
    
    /**
     * Get URL of feature in CGAP
     * @return URL
     */
    public URL cgapUrl() {
    	URL url = null;
    	try {
			url = new URL(
				"http://cgap.nci.nih.gov/Genes/RunUniGeneQuery?PAGE=1&ORG=Hs&TERM=" +
				this.name);
		} catch (MalformedURLException e) {
			throw new WebcghSystemException("Error creating URL to CGAP");
		}
    	return url;
    }
    
    
    // ======================================
    //    Static methods
    // ======================================
    
    
    /**
     * Add column headings
     * @param row Row
     * @param cellStyle Stykle
     */
    public static void addHeadings(HSSFRow row, HSSFCellStyle cellStyle) {
    	for (short i = 0; i < annotationColNames.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(annotationColNames[i]);
		}
    }
}
