/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/BioAssay.java,v $
$Revision: 1.6 $
$Date: 2006-05-22 22:15:13 $

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

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.graph.widget.DataPlotter;
import org.rti.webcgh.service.Cacheable;


/**
 * A bioassay
 */
public class BioAssay implements Cacheable {
	
	private static final String RAW_SUFFIX = " (raw)";
    
    
    // =================================
    //    Attributes
    // =================================
    
    protected Long id;
    protected String name;
    protected String description;
    protected BioAssayData bioAssayData = null;
    protected Organism organism = null;
    protected String bioAssayDataId = null;
    protected String databaseName = null;
    protected BinnedData binnedData = null;
    protected Long binnedDataId = null;
    protected Array array = null;
    protected BioAssayType bioAssayType = null;
    protected boolean raw = false;
    
    
	/**
	 * @return Returns the raw.
	 */
	public boolean isRaw() {
		return raw;
	}


	/**
	 * @return Returns the bioAssayType.
	 */
	public BioAssayType getBioAssayType() {
		return bioAssayType;
	}
	
	
	/**
	 * @param bioAssayType The bioAssayType to set.
	 */
	public void setBioAssayType(BioAssayType bioAssayType) {
		this.bioAssayType = bioAssayType;
	}
	
	
    /**
     * @return Returns the array.
     */
    public Array getArray() {
        return array;
    }
    
    
    /**
     * @param array The array to set.
     */
    public void setArray(Array array) {
        this.array = array;
    }
    
    
    /**
     * @return Returns the binnedData.
     */
    public BinnedData getBinnedData() {
        return binnedData;
    }
    
    
    /**
     * @param binnedData The binnedData to set.
     */
    public void setBinnedData(BinnedData binnedData) {
        this.binnedData = binnedData;
    }
    
    
    /**
     * @return Returns the binnedDataId.
     */
    public Long getBinnedDataId() {
        return binnedDataId;
    }
    
    
    /**
     * @param binnedDataId The binnedDataId to set.
     */
    public void setBinnedDataId(Long binnedDataId) {
        this.binnedDataId = binnedDataId;
    }
    
    
    /**
     * @return Returns the database.
     */
    public String getDatabaseName() {
        return databaseName;
    }
    
    
    /**
     * @param database The database to set.
     */
    public void setDatabaseName(String database) {
        this.databaseName = database;
    }
    
    
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		String s = (this.name == null)? "no name" : this.name;
		if (this.raw)
			s += " " + RAW_SUFFIX;
		return s;
	}
	
	
    /**
     * @param bioAssayData The bioAssayData to set.
     */
    public void setBioAssayData(BioAssayData bioAssayData) {
        this.bioAssayData = bioAssayData;
    }
    
    
	/**
	 * @return Returns the organism.
	 */
	public Organism getOrganism() {
		return organism;
	}
	
	
	/**
	 * @param organism The organism to set.
	 */
	public void setOrganism(Organism organism) {
		this.organism = organism;
	}
	
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	
	
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
    /**
     * @return Returns the bioAssayData.
     */
    public BioAssayData getBioAssayData() {
        return bioAssayData;
    }
    
    
    
    /**
     * @return Returns the bioAssayDataId.
     */
    public String getBioAssayDataId() {
        return bioAssayDataId;
    }
    
    
    /**
     * @param bioAssayDataId The bioAssayDataId to set.
     */
    public void setBioAssayDataId(String bioAssayDataId) {
        this.bioAssayDataId = bioAssayDataId;
    }
    
    
    // ==========================
    //      Constructors
    // ==========================
    
    /**
     * Constructor
     */
    public BioAssay() {}
    
    
    /**
     * Constructor
     * @param name A name
     * @param description Description
     * @param bioAssayDataId Bioassay data id
     * @param databaseName Database
     */
    public BioAssay(String name, String description, String bioAssayDataId, String databaseName) {
        this(name, description, databaseName);
        this.bioAssayDataId = bioAssayDataId;
    }
    
    
    /**
     * Constructor
     * @param name Name
     */
    public BioAssay(String name) {
        super();
        this.name = name;
    }


    /**
     * Constructor
     * @param name A name
     * @param description Description
     * @param databaseName Database
     */
    public BioAssay(String name, String description, String databaseName) {
        this.description = description;
        this.name = name;
        this.databaseName = databaseName;
    }
    
    
    
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return BioAssay.cacheKey(this.name, this.databaseName);
	}
	
	
    
    // ===============================
    //    Public methods
    // ===============================
	
    /**
     * Return array datum with given reporter name
     * @param reporterName Name of reporter
     * @return An array datum
     */
    public ArrayDatum getArrayDatumByReporterName(String reporterName) {
    	ArrayDatum datum = null;
    	if (this.bioAssayData != null)
    		return this.bioAssayData.getArrayDatumByReporterName(reporterName);
    	return datum;
    }
    
    /**
     * Graph bio assay
     * @param plot A plot
     * @param start Starting point of plot
     * @param end Ending point of plot
     * @param color Color
     */
    public void graph(DataPlotter plot, GenomeLocation start, GenomeLocation end, 
    		Color color) {
        if (this.bioAssayData != null)
            this.bioAssayData.graph(plot, start, end, this.getName(), color);
    }
    

    /**
     * Get minimum value represented in bioassay
     * @return Minimum value represented in bioassay
     */
    public double minValue() {
        double min = Double.NaN;
        if (this.bioAssayData != null)
            min = this.bioAssayData.minValue();
    	return min;
    }
    
    
    /**
     * Get maximum value represented in bioassay
     * @return Maximum value represented in bioassay
     */
    public double maxValue() {
        double max = Double.NaN;
        if (this.bioAssayData != null)
            max = this.bioAssayData.maxValue();
    	return max;
    }
    
    
    /**
     * Get an iterator
     * @return An iterator
     */
    public ArrayDatumIterator arrayDatumIterator() {
    	ArrayDatumIterator it = null;
    	if (this.bioAssayData == null)
    		it = new ArrayDatumIterator() {
	    	    public ArrayDatum next() {
	    	        return null;
	    	    }
	    	    public boolean hasNext() {
	    	        return false;
	    	    }
    	};
    	else
    		it = this.bioAssayData.arrayDatumIterator();
    	return it;
    }
    
    
    /**
     * Does this contain array data?
     * @return T/F
     */
    public boolean containsArrayData() {
    	return this.getBioAssayData().containsArrayData() || this.binnedData.numBins() > 0;
    }
    
    
    /**
     * Add an array datum
     * @param datum Array datum
     */
    public void add(ArrayDatum datum) {
        if (this.bioAssayData == null)
            this.bioAssayData = new BioAssayData();
        this.bioAssayData.add(datum);
    }
    
    
    /**
     * Number of array datum
     * @return Number of array datum
     */
    public int numArrayDatum() {
    	int num = 0;
    	if (this.bioAssayData != null)
    		num = this.bioAssayData.numArrayDatum();
    	return num;
    }
    
    
    /**
     * Get an array datum
     * @param p Index
     * @return An array datum
     */
    public ArrayDatum getArrayDatum(int p) {
    	ArrayDatum datum = null;
    	if (this.bioAssayData != null)
    		datum = this.bioAssayData.getArrayDatum(p);
    	return datum;
    }
    
    
    /**
     * Does this bioassay contain binned data?
     * @return T/F
     */
    public boolean hasBinnedData() {
        return this.binnedData != null;
    }
    
    
    /**
     * Get iterator for binned data
     * @return Chromosome bin iterator
     */
    public ChromosomeBinIterator chromosomeBinIterator() {
        return this.binnedData.chromosomeBinIterator();
    }
    
    
    /**
     * Number of chromosome bins
     * @return Number of chromosome bins
     */
    public int numChromosomeBins() {
        int num = 0;
        if (this.binnedData != null)
            num = this.binnedData.numBins();
        return num;
    }
    
    
    /**
     * Number of chromosome bins
     * @param chromosomeNumber Chromosome number
     * @return Number of chromosome bins
     */
    public int numChromosomeBins(int chromosomeNumber) {
        int num = 0;
        if (this.binnedData != null)
            num = this.binnedData.numBins(chromosomeNumber);
        return num;
    }
    
    
    /**
     * Get quantitation type of binned data
     * @return Quantitation type
     */
    public QuantitationType binnedQuantitationType() {
    	QuantitationType type = null;
    	if (this.binnedData != null)
    		type = this.binnedData.getQuantitationType();
    	return type;
    }
    
    
    /**
     * Add bioassay data
     * @param bioAssayData Bioassay data
     */
    public void add(BioAssayData bioAssayData) {
    	if (this.bioAssayData == null)
    		this.bioAssayData = bioAssayData;
    }
    
    
    /**
     * Get quantitation types
     * @return Set of QuantitationType objects
     */
    public Set quantitationTypes() {
        if (this.bioAssayData == null)
            return new HashSet();
        return this.bioAssayData.quantitationTypes();
    }
    
    
    /**
     * Get set of chromosomes associated with bioassay
     * @return Sorted set of Chromosome objects
     */
    public SortedSet chromosomes() {
    	if (this.bioAssayData == null)
    		return new TreeSet();
    	return this.bioAssayData.chromosomes();
    }
    
    
    /**
     * Expand given DTO to contain all data herein
     * @param dto A DTO
     */
    public void expand(GenomeIntervalDto dto) {
    	for (ArrayDatumIterator it = this.arrayDatumIterator(); it.hasNext();) {
    		ArrayDatum datum = it.next();
    		datum.expand(dto);
    	}
    }
    
    
    /**
     * Add data from given bioassay
     * @param bioAssay A bioassay
     */
    public void add(BioAssay bioAssay) {
    	for (ArrayDatumIterator it = bioAssay.arrayDatumIterator(); it.hasNext();) {
    		ArrayDatum datum = it.next();
    		this.add(datum);
    	}
    }
    
    
    /**
     * Mark bioassay as raw.  This will only affect how the
     * bioassay is displayed.
     *
     */
    public void markAsRaw() {
    	this.raw = true;
    }
    
    
    /**
     * Get set of amplified chromosomal regions given threshold
     * @param threshold Threshold
     * @return Amplified regions
     */
    public ChromosomalAlterationSet amplifiedRegions(double threshold) {
    	if (this.bioAssayData == null)
    		return new ChromosomalAlterationSet();
    	return this.bioAssayData.amplifiedRegions(threshold);
    }
    
    
    /**
     * Get set of deleted chromosomal regions given threshold
     * @param threshold Threshold
     * @return Amplified regions
     */
    public ChromosomalAlterationSet deletedRegions(double threshold) {
    	if (this.bioAssayData == null)
    		return new ChromosomalAlterationSet();
    	return this.bioAssayData.deletedRegions(threshold);
    }
    
    
    public void bulkSet(BioAssay bioAssay, boolean deepCopy) {
    	this.bulkSetMetadata(bioAssay);
    	if (deepCopy)
    		this.deepCopyCollections(bioAssay);
    	else
    		this.shallowCopyCollections(bioAssay);
    }
    
    
    public void bulkSetMetadata(BioAssay bioAssay) {
    	
    	// Note: raw property not copied.  The act of copying makes
    	// target object not raw by definition
    	this.array = bioAssay.array;
    	this.binnedDataId = bioAssay.binnedDataId;
    	this.bioAssayDataId = bioAssay.bioAssayDataId;
    	this.bioAssayType = bioAssay.bioAssayType;
    	this.databaseName = bioAssay.databaseName;
    	this.description = bioAssay.description;
    	this.name = bioAssay.name;
    	this.organism = bioAssay.organism;
    }
    
    private void deepCopyCollections(BioAssay bioAssay) {
    	
    	// Binned data
    	this.binnedData = null;
    	if (bioAssay.binnedData != null) {
    		this.binnedData = new BinnedData();
    		this.binnedData.bulkSet(bioAssay.binnedData, true);
    	}
    	
    	// Bioassay data
    	this.bioAssayData = null;
    	if (bioAssay.bioAssayData != null) {
    		this.bioAssayData = new BioAssayData();
    		this.bioAssayData.bulkSet(bioAssay.bioAssayData, true);
    	}
    }
    
    private void shallowCopyCollections(BioAssay bioAssay) {
    	this.binnedData = bioAssay.binnedData;
    	this.bioAssayData = bioAssay.bioAssayData;
    }
    
    // ====================================
    //      Static methods
    // ====================================
    
    /**
     * Compute aggregate mean of all underlying data
     * @param bioAssays Bio assays
     * @return A bio assay
     */
    public static BioAssay mean(BioAssay[] bioAssays) {
    	if (bioAssays.length < 1)
    		return null;
    	if (bioAssays.length == 1) {
    		BioAssay bioAssay = new BioAssay();
    		bioAssay.bulkSet(bioAssays[0], true);
    		return bioAssay;
    	}
    	BioAssay newBioAssay = new BioAssay();
    	newBioAssay.organism = bioAssays[0].organism;
    	BioAssayData[] data = new BioAssayData[bioAssays.length];
    	for (int i = 0; i < bioAssays.length; i++) {
    		if (i < bioAssays.length - 1)
    			if (! (bioAssays[i].organism.equals(bioAssays[i + 1].organism)))
    				throw new IllegalArgumentException("Cannot compute means over data from different organisms");
    		data[i] = bioAssays[i].bioAssayData;
    	}
    	newBioAssay.bioAssayData = BioAssayData.mean(data);
    	return newBioAssay;
    }
    
    /**
     * Generate cache key
     * @param name A name
     * @param databaseName Database name
     * @return Cache key
     */
    public static Object cacheKey(String name, String databaseName) {
        return "%ba%" + databaseName + name;
    }
}
