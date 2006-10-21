/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/array/BioAssayProbeMappingResults.java,v $
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

import java.text.DecimalFormat;
import java.util.Locale;


/**
 * Summary statistics for probe mapping operation of an array
 * (i.e. GenomeArrayDataSet object)
 */
public class BioAssayProbeMappingResults {
	
	
	// =========================================
	//    Attributes with accessors and mutators
	// =========================================
	
	private BioAssay bioAssay = null;
	private int numReporters = 0;
	private int numMappedReporters = 0;
	boolean usedExistingLocations = false;
	
	
	/**
	 * @return Returns the genomeArrayData.
	 */
	public BioAssay getBioAssay() {
		return bioAssay;
	}
	
	
	/**
	 * @param bioAssay The genomeArrayData to set.
	 */
	public void setBioAssay(BioAssay bioAssay) {
		this.bioAssay = bioAssay;
	}
	
	
	/**
	 * @return Returns the numMappedProbes.
	 */
	public int getNumMappedReporters() {
		return numMappedReporters;
	}
	
	
	/**
	 * @param numMappedProbes The numMappedProbes to set.
	 */
	public void setNumMappedReporters(int numMappedProbes) {
		this.numMappedReporters = numMappedProbes;
	}
	
	
	/**
	 * @return Returns the numProbes.
	 */
	public int getNumReporters() {
		return numReporters;
	}
	
	
	/**
	 * @param numProbes The numProbes to set.
	 */
	public void setNumReporters(int numProbes) {
		this.numReporters = numProbes;
	}
	
	
	/**
	 * @return Returns the usedExistingLocations.
	 */
	public boolean isUsedExistingLocations() {
		return usedExistingLocations;
	}
	
	
	/**
	 * @param usedExistingLocations The usedExistingLocations to set.
	 */
	public void setUsedExistingLocations(boolean usedExistingLocations) {
		this.usedExistingLocations = usedExistingLocations;
	}
	
	
	// =========================================================
	//         Constructors
	// =========================================================
	
	/**
	 * Constructor
	 */
	public BioAssayProbeMappingResults() {}
	
	
	/**
	 * Constructor
	 * @param bioAssay Genome array data
	 */
	public BioAssayProbeMappingResults(BioAssay bioAssay) {
		this();
		this.bioAssay = bioAssay;
	}
		
	
	// ========================================================
	//         Public methods
	// ========================================================
	
	/**
	 * Percent of probes that were mapped
	 * @return Percent of probes that were mapped
	 */
	public String getFormattedPercentReportersMapped() {
		if (this.numReporters <= 0)
			return "0.0";
		double percent = (double)this.numMappedReporters / (double)this.numReporters * 100.0;
		DecimalFormat dForm = 
        	(DecimalFormat)DecimalFormat.getInstance(Locale.US);
		dForm.applyPattern("###.##");
		String formattedPercent = dForm.format(percent) + "%";
		return formattedPercent;
	}
}
