/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/Experiment.java,v $
$Revision: 1.4 $
$Date: 2006-03-03 23:23:56 $

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.graph.Plot;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.service.Cacheable;

/**
 * 
 */
public class Experiment implements Cacheable {
    
    
    // ====================================
    //       Attributes
    // ====================================
    
    private Collection bioAssays = new ArrayList();
    private List amplifications = new ArrayList();
    private List deletions = new ArrayList();
    private String name;
    private String description;
    private Long id = null;;
    private boolean virtual;
    private String databaseName;
    private Organism organism;
    private Map bioAssayIndex = new HashMap();
    private String userName = null;
    private String clientId = null;
    private boolean raw = false;
    private GenomeAssembly referenceAssembly = null;
    
    
    public GenomeAssembly getReferenceAssembly() {
		return referenceAssembly;
	}


	public void setReferenceAssembly(GenomeAssembly referenceAssembly) {
		this.referenceAssembly = referenceAssembly;
	}


	public boolean isRaw() {
		return raw;
	}


	/**
     * @return Returns the clientId.
     */
    public String getClientId() {
        return clientId;
    }
    
    
    /**
     * @param clientId The clientId to set.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	
	
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
		
	
	/**
	 * @return Returns the virtual.
	 */
	public boolean isVirtual() {
		return virtual;
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
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	
	
    /**
     * @return Returns the bioAssays.
     */
    public Collection getBioAssays() {
        return bioAssays;
    }
    
    
    /**
     * @param bioAssays The bioAssays to set.
     */
    public void setBioAssays(Collection bioAssays) {
        this.bioAssays = bioAssays;
        this.bioAssayIndex = new HashMap();
        for (Iterator it = bioAssays.iterator(); it.hasNext();) {
            BioAssay bioAssay = (BioAssay)it.next();
            this.bioAssayIndex.put(bioAssay.getName(), bioAssay);
        }
    }
    
    
    /**
     * @return Returns the databaseName.
     */
    public String getDatabaseName() {
        return databaseName;
    }
    
    
    /**
     * @param databaseName The databaseName to set.
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
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
     * @param virtual The virtual to set.
     */
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
    
    
    // ====================================
    //       Constructors
    // ====================================
    
    
    /**
     * Constructor
     */
    public Experiment() {}
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is a virtual experiment
     */
    public Experiment(String name, String description, 
    		String databaseName, boolean virtual) {
        this.name = name;
        this.description = description;
        this.virtual = virtual;
        this.databaseName = databaseName;
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is a virtual experiment
     * @param userName User name
     */
    public Experiment(String name, String description, 
    		String databaseName, boolean virtual, String userName) {
        this(name, description, databaseName, virtual);
        this.userName = userName;
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     */
    public Experiment(String name, String description, 
    		String databaseName) {
    	this(name, description, databaseName, false);
    }
    
    
    /**
     * Constructor
     * @param exp An experiment
     */
    public Experiment(Experiment exp) {
        this.databaseName = exp.databaseName;
        this.description = exp.description;
        this.name = exp.name;
        this.organism = exp.organism;
        this.virtual = exp.virtual;
        for (Iterator it = exp.bioAssays.iterator(); it.hasNext();)
            this.bioAssays.add(new BioAssay((BioAssay)it.next()));
    }
    
    public Experiment(String name, String databaseName) {
        super();
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
		return Experiment.cacheKey(this.databaseName, this.name);
	}
    
    
    // ====================================
    //      Public methods
    // ====================================
	
	
	/**
	 * Is experiment from a client application?
	 * @return T/F
	 */
	public boolean fromClientApp() {
		return this.clientId != null && this.clientId.length() > 0;
	}
    
    /**
     * Graph experiment
     * @param plot A plot
     * @param start Genome start point
     * @param end Genome end point
     * @param plotParameters Plotting parameters
     */
    public void graph(Plot plot, GenomeLocation start, GenomeLocation end, PlotParameters plotParameters) {
    	int count = 0;
    	for (Iterator it = this.bioAssays.iterator(); it.hasNext();) {
    		if (++count == 29)
    			count++;
    		BioAssay bioAssay = (BioAssay)it.next();
        	Color color = plotParameters.color(bioAssay);
            bioAssay.graph(plot, start, end, color);
        }
    }
    
    
    /**
     * Get minimum value represented in bioassay
     * @return Minimum value represented in bioassay
     */
    public double minValue() {
    	double min = Double.NaN;
    	for (Iterator it = this.bioAssays.iterator(); it.hasNext();) {
    		BioAssay bioAssay = (BioAssay)it.next();
    		double candidateMin = bioAssay.minValue();
    		if (Double.isNaN(min))
    			min = candidateMin;
    		else if (candidateMin < min)
    			min = candidateMin;
    	}
    	return min;
    }
    
    
    /**
     * Get maximum value represented in bioassay
     * @return Maximum value represented in bioassay
     */
    public double maxValue() {
    	double max = Double.NaN;
    	for (Iterator it = this.bioAssays.iterator(); it.hasNext();) {
    		BioAssay bioAssay = (BioAssay)it.next();
    		double candidateMax = bioAssay.maxValue();
    		if (Double.isNaN(max))
    			max = candidateMax;
    		else if (candidateMax > max)
    			max = candidateMax;
    	}
    	return max;
    }
    
    
    /**
     * Add bioassay
     * @param bioAssay A bioassay
     */
    public void add(BioAssay bioAssay) {
    	this.bioAssays.add(bioAssay);
    	this.bioAssayIndex.put(bioAssay.getName(), bioAssay);
    }
    
    
    /**
     * Does this experiment contain bioassays?
     * @return T/F
     */
    public boolean containsBioAssays() {
    	return this.getBioAssays() != null && this.getBioAssays().size() > 1;
    }
    
    
    /**
     * Does experiment contain any empty bioassays? (i.e. without array data)
     * @return T/F
     */
    public boolean emptyBioAssays() {
    	boolean empty = false;
    	for (Iterator it = this.bioAssays.iterator(); it.hasNext() && ! empty;) {
    		BioAssay assay = (BioAssay)it.next();
    		empty = ! assay.containsArrayData();
    	}
    	return empty;
    }
    
    
    /**
     * Compute aggregate mean of all underlying array data
     * @return Mean
     */
    public Experiment mean() {
    	BioAssay[] bioAssayArr = new BioAssay[0];
    	bioAssayArr = (BioAssay[])this.bioAssays.toArray(bioAssayArr);
    	BioAssay newBioAssay = BioAssay.mean(bioAssayArr);
    	newBioAssay.setName("Mean over " + this.name);
    	Experiment newExp = new Experiment();
    	newExp.add(newBioAssay);
    	newExp.organism = this.organism;
    	newExp.name = this.name;
    	return newExp;
    }
    
    
    /**
     * Transfer overall metadata to given experiment.  Does not transfer
     * ID or bioassays.
     * @param exp An experiment
     */
    public void transferMetaData(Experiment exp) {
        exp.databaseName = this.databaseName;
        exp.description = this.description;
        exp.name = this.name;
        exp.organism = this.organism;
        exp.virtual = this.virtual;
    }
    
    
    /**
     * Get bioassay iterator
     * @return A bioassay iterator
     */
    public BioAssayIterator bioAssayIterator() {
        return new DefBioAssayIterator(this.bioAssays);
    }
    
    
    /**
     * Get chromosomal alteration iterator
     * @return Chromosomal alteration iterator
     */
    public ChromosomalAlterationIterator amplificationIterator() {
    	return new ExperimentChromosomalAlterationIterator(this.amplifications.iterator());
    }
    
    
    /**
     * Get chromosomal alteration iterator
     * @return Chromosomal alteration iterator
     */
    public ChromosomalAlterationIterator deletionIterator() {
    	return new ExperimentChromosomalAlterationIterator(this.deletions.iterator());
    }
    
    
    /**
     * Number of bioassays
     * @return Number of bioassays
     */
    public int numBioAssays() {
        return this.bioAssays.size();
    }
    
    
    /**
     * Get given bioassay
     * @param name Bioassay name
     * @return A bioassay
     */
    public BioAssay getBioAssay(String name) {
        return (BioAssay)this.bioAssayIndex.get(name);
    }
    
    
    /**
     * Get printable fields
     * @return Printable fields
     */
    public String[] printableFields() {
        return new String[] {this.databaseName, this.name, String.valueOf(this.numBioAssays())};
    }
    
    
    /**
     * Get quantitation types
     * @return Set of QuantitationType objects
     */
    public Set quantitationTypes() {
        Set qTypes = new HashSet();
        for (BioAssayIterator it = this.bioAssayIterator(); it.hasNext();)
            qTypes.addAll(it.next().quantitationTypes());
        return qTypes;
    }
    
    
    /**
     * Get set of chromosomes associated with experiment
     * @return Sorted set of Chromosome objects
     */
    public SortedSet chromosomes() {
    	SortedSet set = new TreeSet();
    	for (BioAssayIterator it = this.bioAssayIterator(); it.hasNext();)
    		set.addAll(it.next().chromosomes());
    	return set;
    }
    
    
    
    /**
     * Expand given DTO to fit all data herein
     * @param dto A DTO
     */
    public void expand(GenomeIntervalDto dto) {
    	if (this.bioAssays != null)
    		for (Iterator it = this.bioAssays.iterator(); it.hasNext();) {
    			BioAssay bioAssay = (BioAssay)it.next();
    			bioAssay.expand(dto);
    		}
    }
    
    
    /**
     * Merge data from given experiment
     * @param experiment Experiment
     */
    public void add(Experiment experiment) {
    	for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();) {
    		BioAssay bioAssay = it.next();
    		BioAssay target = this.getBioAssay(bioAssay.getName());
    		if (target == null)
    			this.add(bioAssay);
    		else
    			target.add(bioAssay);
    	}
    }
    
    
    /**
     * Add an amplification
     * @param chromosomalAlteration A chromosomal alteration
     */
    public void addAmplification(ChromosomalAlteration chromosomalAlteration) {
    	this.amplifications.add(chromosomalAlteration);
    }
    
    
    /**
     * Add an amplification
     * @param chromosomalAlteration A chromosomal alteration
     */
    public void addDeletion(ChromosomalAlteration chromosomalAlteration) {
    	this.deletions.add(chromosomalAlteration);
    }
    

    /**
     * Retruns whether given experiment is logically same as this
     * @param exp Experiment object to be compared with this
     * @return Whether given experiment and this are logically same
     */
    public boolean sameExperiment(Experiment exp) {
    	boolean sameExperiment = false;
    	
    	// experiments are same if name & database name are the same
    	if ((this.name.equals(exp.getName())) && (this.databaseName.equals(exp.getDatabaseName())))
    		sameExperiment = true;
    	return sameExperiment;
    }
    

    /**
     * Get array datum iterator
     * @return Array datum iterator
     */
    public ArrayDatumIterator arrayDatumIterator() {
    	return new ExperimentArrayDatumIterator(this.bioAssayIterator());
    }
    
    
    /**
     * Mark experiment as raw.  This will only affect how the
     * experiment is displayed.
     *
     */
    public void markAsRaw() {
       	String name = (this.name == null)? "" : this.name;
    	this.name = name + " (raw)";
    	this.raw = true;
    	if (this.bioAssays != null)
    		for (BioAssayIterator it = this.bioAssayIterator(); it.hasNext();) {
    			BioAssay ba = it.next();
    			ba.markAsRaw();
    		}
    }


    // =======================================
    //       Static methods
    // =======================================
    
    
    /**
     * Get printable headings
     * @return Printable headings
     */
    public static String[] printableHeadings() {
        return new String[] {"DATABASE", "EXPERIMENT", "NUMBER OF BIOASSYS"};
    }
    
    
    /**
     * Generate cache key
     * @param dbName Database name
     * @param name Experiment name
     * @return Cache key
     */
    public static Object cacheKey(String dbName, String name) {
        return "%ex%" + dbName + name;
    }
    
    
    
    // ================================================
    //          Inner classes
    // ================================================
    
    
    /**
     * Implementation of BioAssayIterator interface
     *
     */
    static class DefBioAssayIterator implements BioAssayIterator {
        
        // ===================================
        //    Attributes
        // ===================================
        
        private Iterator iterator = null;
        
        
        // ===================================
        //     Constructors
        // ===================================
        
        /**
         * Constructor
         * @param bioAssays List of BioAssay objects
         */
        public DefBioAssayIterator(Collection bioAssays) {
        	if (bioAssays != null)
        		this.iterator = bioAssays.iterator();
        }
        
        
        // =======================================
        //      Public methods
        // =======================================
        
        /**
         * Get next datum
         * @return Next datum
         */
        public BioAssay next() {
        	if (this.iterator == null)
        		return null;
            return (BioAssay)this.iterator.next();
        }

        
        /**
         * Does iterator have a next datum?
         * @return T/F
         */
        public boolean hasNext() {
        	if (this.iterator == null)
        		return false;
            return this.iterator.hasNext();
        }
    }
    
    
    static class ExperimentArrayDatumIterator implements ArrayDatumIterator {
    	
    	BioAssayIterator bai = null;
    	ArrayDatumIterator adi = null;
    	
    	// =====================================
    	//        Constructors
    	// =====================================
    	
    	/**
    	 * Constructor
    	 * @param it Bioassay iterator
    	 */
    	public ExperimentArrayDatumIterator(BioAssayIterator it) {
    		this.bai = it;
    		while (bai.hasNext() && this.adi == null) {
    			BioAssay bioAssay = this.bai.next();
    			ArrayDatumIterator tempAdi = bioAssay.arrayDatumIterator();
    			if (tempAdi.hasNext())
    				this.adi = tempAdi;
    		}
    	}
    	
        /**
         * Get next datum
         * @return An array datum
         */
        public ArrayDatum next() {
        	ArrayDatum datum = null;
        	if (this.adi != null) {
        		datum = this.adi.next();
        		while (! this.adi.hasNext()) {
        			this.adi = null;
        			if (! this.bai.hasNext())
        				break;
        			BioAssay bioAssay = this.bai.next();
        			this.adi = bioAssay.arrayDatumIterator();
        		}
        	}
        	return datum;
        }
        
        
        /**
         * Does iterator have additional data?
         * @return T/F
         */
        public boolean hasNext() {
        	return this.adi == null;
        }
    	
    }
    
    
    static class ExperimentChromosomalAlterationIterator implements ChromosomalAlterationIterator {
    	
    	final Iterator it;
    	
    	public ExperimentChromosomalAlterationIterator(Iterator it) {
    		this.it = it;
    	}
    	
    	public boolean hasNext() {
    		return it.hasNext();
    	}
    	
    	public ChromosomalAlteration next() {
    		return (ChromosomalAlteration)it.next();
    	}
    	
    	
    	public void remove() {
    		it.remove();
    	}
    	
    }
}
