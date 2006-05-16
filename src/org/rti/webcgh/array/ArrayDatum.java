/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/ArrayDatum.java,v $
$Revision: 1.5 $
$Date: 2006-05-16 12:49:02 $

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

import java.util.Collection;
import java.util.Iterator;

import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.graph.widget.DataPlotter;
import org.rti.webcgh.service.Cacheable;

/**
 * An array data point
 */
public class ArrayDatum implements Comparable, Locatable, Cacheable {
    
    
    // ============================
    //        Attributes
    // ============================
    
    protected Quantitation quantitation = null;
    private Long id = null;
    private Reporter reporter = null;
        
    
    /**
     * @return Returns the reporter.
     */
    public Reporter getReporter() {
        return reporter;
    }
    
    
    /**
     * @param reporter The reporter to set.
     */
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }
    
    
	/**
	 * @return Returns the quantitation.
	 */
	public Quantitation getQuantitation() {
		return quantitation;
	}
	
	
	/**
	 * @param quantitation The quantitation to set.
	 */
	public void setQuantitation(Quantitation quantitation) {
		this.quantitation = quantitation;
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
	
	
    // =================================
    //       Constructors
    // =================================
	
	/**
	 * Constructor
	 */
	public ArrayDatum() {
		super();
	}
	
    
    /**
     * Constructor
     * @param reporter A reporter
     * @param quantitation A quantitation
     */
    public ArrayDatum(Reporter reporter, Quantitation quantitation) {
        this.reporter = reporter;
        this.quantitation = quantitation;
    }
    
    
    /**
     * Constructor performs a shallow copy of all properties exceptio
     * quantitation.
     * @param datum A datum
     */
    public ArrayDatum(ArrayDatum datum) {
    	this.quantitation = new Quantitation(datum.quantitation);
    	this.reporter = datum.reporter;
    }
    
        
    
    // ================================
    //      Public methods
    // ================================
    
    /**
     * Graph datum
     * @param plot A plot
     * @param key Key of group of graphical objects to which data point will be added
     */
    public void graph(DataPlotter plot, Object key) {
        DataPoint dataPoint = new DataPoint();
        this.initializeDataPoint(dataPoint);
        if (plot.inPlot(dataPoint))
            plot.graphPoint(dataPoint, key);
    }
    
    
    /**
     * Set properties in given data point corresponding to location
     * @param dataPoint A data point
     */
    public void initializeDataPointLocation(DataPoint dataPoint) {
    	this.reporter.initializeDataPointLocation(dataPoint);
    }
    
    
    /**
     * Set properties in given data point corresponding to quantitation
     * @param dataPoint A data point
     */
    public void initializeDataPointQuantitation(DataPoint dataPoint) {
    	this.quantitation.transferPropertiesTo(dataPoint);
    }
    
    
    /**
     * Set all relevant properties in given data point
     * @param dataPoint A data point
     */
    public void initializeDataPoint(DataPoint dataPoint) {
    	this.initializeDataPointLocation(dataPoint);
    	this.initializeDataPointQuantitation(dataPoint);
    }
    
    
    // ================================
    //     Cacheable interface
    // ================================
    
	/**
	 * Get a key for the cache
	 * @return A key
	 */
	public Object getCacheKey() {
		return ArrayDatum.cacheKey(this.reporter, this.quantitation);
	}
    
    
    // ===========================
    //   Methods in Comparable
    // ===========================
    
    /**
     * Comparison method
     * @param obj An object
     * @return -1 (less than), 0 (equals), +1 (greater than)
     */
    public int compareTo(Object obj) {
        if (! (obj instanceof Locatable)) {
        	String msg = "Expecting a 'Locatable' object.  " +
				"This is type '" + this.getClass().getName() + "'.  " +
				"Comparison object is type '" + obj.getClass().getName() + "'.";
            throw new IllegalArgumentException(msg);
        }
        return this.reporter.compareTo(((Locatable)obj).getGenomeLocation());
    }
    
    
    // ============================
    //   Methods in Locatable
    // ============================
    
    /**
     * Get genome location
     * @return Genome location
     */
    public GenomeLocation getGenomeLocation() {
    	return this.reporter.getGenomeLocation();
    }
    
    
    /**
     * Is locatable to right of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean rightOf(Locatable locatable) {
    	return this.reporter.rightOf(locatable);
    }
    
    
    /**
     * Is locatable to left of this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean leftOf(Locatable locatable) {
    	return this.reporter.leftOf(locatable);
    }
    
    
    /**
     * Is locatable in same location as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameLocation(Locatable locatable) {
    	return this.reporter.sameChromosome(locatable);
    }
    
    
    /**
     * Is locatable on same chromosome as this on genome map?
     * @param locatable A locatable
     * @return T/F
     */
    public boolean sameChromosome(Locatable locatable) {
    	return this.reporter.sameChromosome(locatable);
    }
    
    
    // =======================================
    //     Other public methods
    // =======================================
    
    /**
     * Does this have a greater value than given datum?
     * @param datum A datum
     * @return T/F
     */
    public boolean greaterThan(ArrayDatum datum) {
        return this.quantitation.greaterThan(datum.quantitation);
    }
    
    
    /**
     * Does this have a lesser value than given datum?
     * @param datum A datum
     * @return T/F
     */
    public boolean lessThan(ArrayDatum datum) {
        return this.quantitation.lessThan(datum.quantitation);
    }
    
    
    /**
     * Minimum value represented by this quantitation
     * @return Minimum value represented by this quantitation
     */
    public double minValue() {
    	return this.quantitation.minValue();
    }
    
    
    /**
     * Maximum value represented by this quantitation
     * @return Maximum value represented by this quantitation
     */
    public double maxValue() {
    	return this.quantitation.maxValue();
    }
    
    
    /**
     * Is quantitation value of this in ranger bounded by given values?
     * @param lower Lower bound
     * @param upper Upper bound
     * @return T/F
     */
    public boolean inQuantitationRange(double lower, double upper) {
        return this.quantitation.inRange(lower, upper);
    }
    
    
    /**
     * Add quantitation value
     * @param datum A datum
     */
    public void add(ArrayDatum datum) {
    	this.quantitation.add(datum.quantitation);
    }
    
    
    /**
     * Add a scalar value
     * @param value Value
     */
    public void add(float value) {
    	this.quantitation.add(value);
    }
    
    
    /**
     * Divide quantition value
     * @param divisor Divisor
     */
    public void divideBy(float divisor) {
    	this.quantitation.divideBy(divisor);
    }
    
    
    /**
     * Get magnitude of datum
     * @return Magnitude
     */
    public float magnitude() {
    	return this.quantitation.getValue();
    }
    
    
    /**
     * Set magnitude
     * @param value
     */
    public void setMagnitude(float value) {
        this.quantitation.setValue(value);
    }
    
    
    /**
     * Does this datum have a position in the genome?
     * @return T/F
     */
    public boolean hasLocation() {
        return this.reporter.hasLocation();
    }
    
    
    /**
     * Quantitation type
     * @return Quantitation type
     */
    public QuantitationType quantitationType() {
        return this.quantitation.getQuantitationType();
    }
    
    
    /**
     * Get chromosome
     * @return Chromosome
     */
    public Chromosome chromosome() {
    	return this.reporter.chromosome();
    }
    
    
    /**
     * Minimum chromosome length possible given data contained within
     * @return Minimum chromosome length possible given data contained within
     */
    public long minChromosomeLength() {
    	return this.reporter.minChromosomeLength();
    }
    
    
    /**
     * Expand range denoted by DTO if this falls outside
     * @param dto A DTO
     */
    public void expand(GenomeIntervalDto dto) {
    	this.reporter.expand(dto);
    }
    
    
    /**
     * Set the genome assembly.  If the reporter has not been mapped to
     * a physical location, the method does nothing.
     * @param genomeAssembly A genome assembly
     */
    public void setGenomeAssembly(GenomeAssembly genomeAssembly) {
    	this.reporter.setGenomeAssembly(genomeAssembly);
    }
    
    // ========================================
    //         Static methods
    // ========================================
    
    
    /**
     * Compute mean of equivalent data
     * @param data Array data
     * @return An array datum
     */
    public static ArrayDatum meanOfEquivalents(ArrayDatum[] data) {
    	if (data.length < 1)
    		return null;
    	if (data.length == 1)
    		return data[0];
    	ArrayDatum datum = new ArrayDatum();
    	Quantitation[] quants = new Quantitation[data.length];
    	for (int i = 0; i < data.length; i++) {
    		if (i < data.length - 1)
    			if (data[i].reporter != data[i + 1].reporter)
    				throw new IllegalArgumentException("All array data must be from same reporter");
    			quants[i] = data[i].quantitation;
    	}
    	datum.quantitation = Quantitation.mean(quants);
    	datum.reporter = data[0].reporter;
    	datum.reporter = data[0].reporter;
    	return datum;
    }
    
    
    /**
     * Compute mean of non equivalent array datum and set the quantitation
     * of given datum with mean
     * @param arrayData ArrayDatum objects to average
     * @param datum Recipent of mean calculation
     */
    public static void meanOfNonEquivalents(Collection arrayData, ArrayDatum datum) {
        Quantitation[] quants = new Quantitation[arrayData.size()];
        int i = 0;
        for (Iterator it = arrayData.iterator(); it.hasNext();)
        	quants[i++] = ((ArrayDatum)it.next()).quantitation;
        datum.quantitation = Quantitation.mean(quants);
    }
    
    
    /**
     * Place on genomic location
     * @param reporterMapping Reporter mapping
     */
    public void locateInGenome(ReporterMapping reporterMapping) {
        this.reporter.locateInGenome(reporterMapping);
    }
    
    /**
     * Generate cache key
     * @param reporter
     * @param quantitation
     * @return A key
     */
    public static Object cacheKey(Reporter reporter, Quantitation quantitation) {
        return "%ad%" + reporter.getCacheKey() + quantitation.getCacheKey();
    }
    
    
    /**
     * Create a new ArrayDatum instance that is unaffiliated to a genome assembly or
     * organism.  This method should normally be used only for testing.  If the
     * generated data are plotted, there is no guarantee that the plots will be
     * correct if they can even be generated without an exception.
     * @param value Value of datum (e.g. expression value)
     * @param chromNum Chromosome number
     * @param chromPos Chromosome position
     * @return An array datum
     */
    public static ArrayDatum newUnaffiliatedArrayDatum(float value, short chromNum, long chromPos) {
    	Reporter reporter = new Reporter();
    	Chromosome chromosome = new Chromosome(GenomeAssembly.DUMMY_GENOME_ASSEMBLY, chromNum);
    	GenomeLocation location = new GenomeLocation(chromosome, chromPos);
    	ReporterMapping rm = new ReporterMapping(reporter, location);
    	reporter.setReporterMapping(rm);
    	Quantitation q = new Quantitation(value, QuantitationType.UNKNOWN);
    	return new ArrayDatum(reporter, q);
    }
}
