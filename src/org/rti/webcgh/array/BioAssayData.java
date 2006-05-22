/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/BioAssayData.java,v $
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.graph.DataPoint;
import org.rti.webcgh.graph.widget.DataPlotter;

/**
 * Bio-assay data
 */
public class BioAssayData {
    
    // ====================================
    //        Attributes
    // ====================================
    
    protected Long id = null;
    protected List arrayData = new ArrayList();
    protected boolean sorted = true;
    protected double minValue = Float.NaN;
    protected double maxValue = Float.NaN;
    protected Set quantitationTypes = new HashSet();
    
    protected Map<Reporter, ArrayDatum> arrayDatumIndexByReporter = 
    	new HashMap<Reporter, ArrayDatum>();
    protected Map<String, ArrayDatum> arrayDatumIndexByReporterName = 
    	new HashMap<String, ArrayDatum>();
    
    
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
     * @return Returns the arrayData.
     */
    public List getArrayData() {
        return arrayData;
    }
    
    
    /**
     * @param arrayData The arrayData to set.
     */
    public void setArrayData(List arrayData) {
        this.arrayData = arrayData;
        this.arrayDatumIndexByReporter = new HashMap();
        this.quantitationTypes = new HashSet();
        if (arrayData != null)
	        for (Iterator it = this.arrayData.iterator(); it.hasNext();) {
	            ArrayDatum datum = (ArrayDatum)it.next();
	            this.adjustMinAndMax(datum);
	            this.arrayDatumIndexByReporter.put(datum.getReporter(), datum);
	            this.quantitationTypes.add(datum.quantitationType());
	        }
        this.sorted = false;
    }
    
    // =======================================
    //        Constructors
    // =======================================
    
    /**
     * Constructor
     */
    public BioAssayData() {
    	this.sorted = false;
    }
    
    
    /**
     * Constructor
     * @param data Bioassay data
     */
    public void bulkSet(BioAssayData data, boolean deepCopy) {
        for (Iterator it = data.arrayData.iterator(); it.hasNext();) {
        	if (deepCopy)
        		this.add(new ArrayDatum((ArrayDatum)it.next()));
        	else
        		this.add((ArrayDatum)it.next());
        }
        this.sorted = false;
    }
    
    
    // ===============================
    //      Public methods
    // ===============================
    
    
    /**
     * Return array datum with given reporter name
     * @param reporterName Name of reporter
     * @return An array datum
     */
    public ArrayDatum getArrayDatumByReporterName(String reporterName) {
    	return this.arrayDatumIndexByReporterName.get(reporterName);
    }
    
    /**
     * Add a datum
     * @param arrayDatum Array datum
     */
    public void add(ArrayDatum arrayDatum) {
         this.arrayData.add(arrayDatum);
         this.adjustMinAndMax(arrayDatum);
         this.sorted = false;
         this.arrayDatumIndexByReporter.put(arrayDatum.getReporter(), arrayDatum);
         this.arrayDatumIndexByReporterName.put(arrayDatum.getReporter().getName(), arrayDatum);
         this.quantitationTypes.add(arrayDatum.quantitationType());
    }
    
    
    /**
     * Plot all data between given endpoints.
     * @param plot A plot
     * @param start Start point
     * @param end End point
     * @param key Unique value identifying bioassay data
     * @param color Color
     * @throws IllegalArgumentException if <code>start</code> and
     * <code>end</code> are from different chromosomes
     */
    public void graph(DataPlotter plot, GenomeLocation start, GenomeLocation end, Object key, Color color) {
        if (! start.sameChromosome(end))
            throw new IllegalArgumentException("Genome locations 'start' and 'end' cannot be from different chromosomes");
        this.ensureSorted();
        
        // Get index of first and last datum in range
        int p = this.getInsertionIndex(start);
        int q = this.getInsertionIndex(end);
        if (q < this.arrayData.size() && (! end.sameLocation(this.getArrayDatum(q))))
            q--;
        
        plot.setGroupColor(key, color);
        if (p >= 0 && q >= 0 && p < this.arrayData.size() && q <= this.arrayData.size() && p < q) {
	        this.graphPoints(plot, p, q, key);
	        this.graphConnectingLines(plot, start, end, p, q, key);
        }
    }
    
    
    /**
     * Get minimum value represented in bioassay
     * @return Minimum value represented in bioassay
     */
    public double minValue() {
    	return this.minValue;
    }
    
    
    /**
     * Get maximum value represented in bioassay
     * @return Maximum value represented in bioassay
     */
    public double maxValue() {
    	return this.maxValue;
    }
    
    
    /**
     * Get an iterator
     * @return An iterator
     */
    public ArrayDatumIterator arrayDatumIterator() {
    	return new DefArrayDatumIterator(this.arrayData);
    }
    
    
    /**
     * Does this contain array data?
     * @return T/F
     */
    public boolean containsArrayData() {
    	return this.arrayData != null && this.arrayData.size() > 0;
    }
    
    
    /**
     * Get number of datum
     * @return Number of datum
     */
    public int numArrayDatum() {
    	return this.arrayData.size();
    }
    
    
    /**
     * Get quantitation types
     * @return Set of QuantitationType objects
     */
    public Set quantitationTypes() {
        return this.quantitationTypes;
    }
    
    
    /**
     * Get chromosomes
     * @return Sorted set of Chromosome objects
     */
    public SortedSet chromosomes() {
    	SortedSet chromosomes = new TreeSet();
    	Map chromIndex = new HashMap();
    	for (ArrayDatumIterator it = this.arrayDatumIterator(); it.hasNext();) {
    		ArrayDatum datum = it.next();
    		Chromosome chrom = datum.chromosome();
    		chromosomes.add(chrom);
    	}
    	return chromosomes;
    }
    
        
    // =============================
    //     Static methods
    // =============================
    
    /**
     * Compute mean over all associated array data
     * @param dataSet Bioassay data
     * @return Bio assay data
     */
    public static BioAssayData mean(BioAssayData[] dataSet) {
    	if (dataSet.length < 1)
    		return null;
    	Set checkList = new HashSet();
    	BioAssayData newData = new BioAssayData();
    	for (int i = 0; i < dataSet.length; i++) {
    		BioAssayData data = dataSet[i];
    		for (ArrayDatumIterator it = data.arrayDatumIterator(); it.hasNext();) {
    			ArrayDatum queryDatum = it.next();
    			Object key = queryDatum.getReporter();
    			if (! checkList.contains(key)) {
    				checkList.add(key);
    				List datumList = new ArrayList();
    				for (int j = 0; j < dataSet.length; j++) {
    					ArrayDatum subjectDatum = dataSet[j].equivalentDatum(queryDatum);
    					if (subjectDatum != null)
    						datumList.add(subjectDatum);
    				}
    				ArrayDatum[] datumArr = new ArrayDatum[0];
    				datumArr = (ArrayDatum[])datumList.toArray(datumArr);
    				ArrayDatum newDatum = ArrayDatum.meanOfEquivalents(datumArr);
    				newData.add(newDatum);
    			}
    		}
    	}
    	return newData;
    }
    
    
    /**
     * Get given datum
     * @param idx An index
     * @return An array datum
     */
    public ArrayDatum getArrayDatum(int idx) {
        assert idx >= 0 && idx < this.arrayData.size();
        return (ArrayDatum)this.arrayData.get(idx);
    }
    
    
    /**
     * Find amplified regions (i.e. regions greater than threshold)
     * @param threshold Threshold value
     * @return Amplified regions
     */
    public ChromosomalAlterationSet amplifiedRegions(double threshold) {
    	return alteredRegions(threshold, RelOp.GREATER_THAN);
    }
    
    
    /**
     * Find deleted regions (i.e. regions less than threshold)
     * @param threshold Threshold value
     * @return Deleted regions
     */
    public ChromosomalAlterationSet deletedRegions(double threshold) {
    	return alteredRegions(threshold, RelOp.LESS_THAN);
    }
    	
    private ChromosomalAlterationSet alteredRegions(double threshold, RelOp relOp) {
    	ChromosomalAlterationSet altSet = new ChromosomalAlterationSet();
    	if (this.arrayData != null) {
    		this.ensureSorted();
        	int leftIdx = -1, rightIdx = -1;
	    	for (int i = 0; i < this.arrayData.size(); i++) {
	    		ArrayDatum datum = (ArrayDatum)this.arrayData.get(i);
	    		if (leftIdx != -1 && (! ((ArrayDatum)this.arrayData.get(leftIdx)).sameChromosome(datum))) {
	    			altSet.add(this.newChromosomalAlteration(leftIdx, rightIdx));
	    			leftIdx = rightIdx = -1;
	    		}
	    		if ((relOp == RelOp.GREATER_THAN && (double)datum.magnitude() > threshold) ||
	    			(relOp == RelOp.LESS_THAN && (double)datum.magnitude() < threshold)) {
	    			if (leftIdx == -1)
	    				leftIdx = rightIdx = i;
	    			else
	    				rightIdx = i;
	    		} else if (leftIdx != -1 && rightIdx != -1) {
	    			altSet.add(this.newChromosomalAlteration(leftIdx, rightIdx));
	    			leftIdx = rightIdx = -1;
	    		}
	    	}
	    	if (leftIdx != -1 && rightIdx != -1)
	    		altSet.add(this.newChromosomalAlteration(leftIdx, rightIdx));
    	}
    	return altSet;
    }
    
    
    private ChromosomalAlteration newChromosomalAlteration(int p, int q) {
    	
    	// Start point
    	GenomeLocation start = null;
    	ArrayDatum d2 = (ArrayDatum)this.arrayData.get(p);
    	if (p > 0) {
    		ArrayDatum d1 = (ArrayDatum)this.arrayData.get(p - 1);
    		if (d1.sameChromosome(d2))
    			start = GenomeLocation.midpoint(d1.getGenomeLocation(), d2.getGenomeLocation());
    	}
    	if (start == null)
    		start = d2.getGenomeLocation();
    	
    	// End point
    	GenomeLocation end = null;
    	d2 = (ArrayDatum)this.arrayData.get(q);
    	if (q < this.arrayData.size() - 1) {
    		ArrayDatum d1 = (ArrayDatum)this.arrayData.get(q + 1);
    		if (d1.sameChromosome(d2))
    			end = GenomeLocation.midpoint(d1.getGenomeLocation(), d2.getGenomeLocation());
    	}
    	if (end == null)
    		end = d2.getGenomeLocation();
    	return new ChromosomalAlteration(new GenomeInterval(start, end));
    }
    
           
    
    // ===================================
    //      protected methods
    // ===================================
    

    protected ArrayDatum equivalentDatum(ArrayDatum datum) {
    	return (ArrayDatum)this.arrayDatumIndexByReporter.get(datum.getReporter());
    }
    
    protected void ensureSorted() {
        if (! this.sorted) {
            if (this.needsSorting())
                this.sortSelf();
            this.sorted = true;
        }
    }
    
    
    protected boolean needsSorting() {
        boolean needsSorting = false;
        Iterator it = this.arrayData.iterator();
        if (it.hasNext()) {
            ArrayDatum datum1 = (ArrayDatum)it.next();
            while (it.hasNext() && ! needsSorting) {
                ArrayDatum datum2 = (ArrayDatum)it.next();
                if (datum1.compareTo(datum2) > 0)
                    needsSorting = true;
                datum1 = datum2;
            }
        }
        return needsSorting;
    }
    
    
    protected void sortSelf() {
        Collections.sort(this.arrayData);
    }
    
    
    protected ArrayDatum getLastArrayDatum() {
        ArrayDatum datum = null;
        int idx = this.arrayData.size() - 1;
        if (idx >= 0)
            datum = this.getArrayDatum(idx);
        return datum;
    }
    
    
    protected int getInsertionIndex(GenomeLocation location) {
        int idx = -1;
        if (this.arrayData.size() > 0) {
	        idx = Collections.binarySearch(this.arrayData, location);
	        if (idx < 0)
	            idx = - (idx + 1);
        }
        return idx;
    }


    protected void graphPoints(DataPlotter plot, int p, int q, Object key) {
        int numReporters = this.arrayData.size();
        for (int i = p; i <= q && i < numReporters; i++) {
            ArrayDatum datum = this.getArrayDatum(i);
            datum.graph(plot, key);
        }
    }
    
    
    protected void graphConnectingLines(DataPlotter plot, GenomeLocation start, GenomeLocation end,
        int p, int q, Object key) {
        if (p <= this.arrayData.size()) {
	        List dataPoints = new ArrayList();
	        this.addLeftDataPoint(dataPoints, start, end, p);
	        this.addInteriorDataPoints(dataPoints, start, end, p, q);
	        this.addRightDataPoint(dataPoints, start, end, q);
	        DataPoint[] points = new DataPoint[0];
	        points = (DataPoint[])dataPoints.toArray(points);
	        plot.graphLines(points, key);
        }
    }
    
    
    protected void addLeftDataPoint(List dataPoints, GenomeLocation leftEnd, 
    	GenomeLocation rightEnd, int leftDatumIndex) {
        ArrayDatum leftDatum = this.getArrayDatum(leftDatumIndex);
        if (! leftEnd.sameLocation(leftDatum) && 
            leftDatum.sameChromosome(leftEnd)) {
        	DataPoint newDataPoint = new DataPoint();
        	dataPoints.add(newDataPoint);
        	
        	// Datum first on chromosome
	        if (this.firstArrayDatumOnChromosome(leftDatumIndex, leftEnd)) {
	            leftDatum.initializeDataPointQuantitation(newDataPoint);
	            leftEnd.initalizeDataPointLocation(newDataPoint);
	            if (! leftDatum.leftOf(rightEnd)) {
	                DataPoint rightDataPoint = new DataPoint(newDataPoint);
	                rightEnd.initalizeDataPointLocation(rightDataPoint);
	                dataPoints.add(rightDataPoint);
	            }
	        } 
	        
	        // Datum not first on chromosome
	        else {
	            ArrayDatum firstDatumLeftOfRange = this.getArrayDatum(leftDatumIndex - 1);
	            DataPoint firstDataPointLeftOfRange = new DataPoint();
	            DataPoint firstDataPointInRange = new DataPoint();
	            firstDatumLeftOfRange.initializeDataPoint(firstDataPointLeftOfRange);
	            leftDatum.initializeDataPoint(firstDataPointInRange);
	            leftEnd.initalizeDataPointLocation(newDataPoint);
	            newDataPoint.fitValue2ToLine(firstDataPointLeftOfRange, firstDataPointInRange);
	            dataPoints.add(newDataPoint);
	        }
        }
    }
    
    
    protected boolean firstArrayDatumOnChromosome(int p, GenomeLocation location) {
    	boolean isFirst = true;
    	if (p > 0) {
    		ArrayDatum datumLeft = this.getArrayDatum(p - 1);
    		ArrayDatum datumRight = this.getArrayDatum(p);
    		isFirst = ! datumLeft.sameChromosome(datumRight);
    	}
    	return isFirst;
    }
    
    
    protected void addInteriorDataPoints(List dataPoints, GenomeLocation start, GenomeLocation end, int p, int q) {
    	int numDataPoints = this.numArrayDatum();
        for (int i = p; i <= q && i < numDataPoints; i++) {
            if (i < this.arrayData.size()) {
                ArrayDatum datum = this.getArrayDatum(i);
                if (! datum.leftOf(start) && ! datum.rightOf(end)) {
                    DataPoint dataPoint = new DataPoint();
                    datum.initializeDataPoint(dataPoint);
                    dataPoints.add(dataPoint);
                }
            }
        }
    }
    
    protected void addRightDataPoint(List dataPoints, GenomeLocation leftEnd,
        GenomeLocation rightEnd, int lastDatumIndex) {
    	if (lastDatumIndex >= this.numArrayDatum())
    		lastDatumIndex = this.numArrayDatum() - 1;
        
        if (lastDatumIndex >= 0) {
	    	ArrayDatum lastDatum = this.getArrayDatum(lastDatumIndex);
	    	if (! rightEnd.sameLocation(lastDatum) && rightEnd.sameChromosome(lastDatum)) {
	        	DataPoint newDataPoint = new DataPoint();
	        	
	        	// Datum first on chromosome
		        if (this.lastArrayDatumOnChromosome(lastDatumIndex, rightEnd)) {
		            lastDatum.initializeDataPointQuantitation(newDataPoint);
		            rightEnd.initalizeDataPointLocation(newDataPoint);
		            if (! lastDatum.rightOf(leftEnd)) {
		                DataPoint leftDataPoint = new DataPoint(newDataPoint);
		                leftEnd.initalizeDataPointLocation(leftDataPoint);
		                dataPoints.add(leftDataPoint);
		            }
		        } 
		        
		        // Datum not first on chromosome
		        else {
		            ArrayDatum firstDatumRightOfRange = this.getArrayDatum(lastDatumIndex + 1);
		            DataPoint firstDataPointRightOfRange = new DataPoint();
		            DataPoint lastDataPointInRange = new DataPoint();
		            firstDatumRightOfRange.initializeDataPoint(firstDataPointRightOfRange);
		            lastDatum.initializeDataPoint(lastDataPointInRange);
		            rightEnd.initalizeDataPointLocation(newDataPoint);
		            newDataPoint.fitValue2ToLine(lastDataPointInRange, firstDataPointRightOfRange);
		        }
		        dataPoints.add(newDataPoint);
	    	}
        }
    }
    
    
    protected boolean lastArrayDatumOnChromosome(int p, GenomeLocation location) {
    	boolean isLast = true;
    	if (p < this.arrayData.size() - 1) {
    		ArrayDatum datumLeft = this.getArrayDatum(p);
    		ArrayDatum datumRight = this.getArrayDatum(p + 1);
    		isLast = ! datumLeft.sameChromosome(datumRight);
    	}
    	return isLast;
    }
    
    
    protected void adjustMinAndMax(ArrayDatum arrayDatum) {
    	if (Double.isNaN(this.minValue))
    		this.minValue = arrayDatum.minValue();
    	else if (arrayDatum.minValue() < this.minValue)
    		this.minValue = arrayDatum.minValue();
    	if (Double.isNaN(this.maxValue))
    		this.maxValue = arrayDatum.maxValue();
    	else if (arrayDatum.maxValue() > this.maxValue)
    		this.maxValue = arrayDatum.maxValue();
    }
    
    
    // ==========================================
    //         Inner classes
    // ==========================================
    
    
    static class DefArrayDatumIterator implements ArrayDatumIterator {
        
        
    	// ==============================
    	//      Attributes
    	// ==============================
    	
    	protected Iterator it = null;
    	protected List list = null;
    	
    	// =============================
    	//      Constructors
    	// =============================
    	
    	/**
    	 * Constructor
    	 * @param datumList Data
    	 */
    	public DefArrayDatumIterator(List datumList) {
    		this.list = datumList;
    	}
    	
    	
    	// ========================================
    	//      Public methods
    	// ========================================
    	
    	/**
    	 * Get next datum
    	 * @return Datum
    	 */
    	public ArrayDatum next() {
    		if (it == null)
    			it = this.list.iterator();
    		ArrayDatum datum = null;
    		if (this.hasNext())
    			datum = (ArrayDatum)this.it.next();
    		return datum;
    	}
    	
    	
    	/**
    	 * Are there any datum remaining?
    	 * @return T/F
    	 */
    	public boolean hasNext() {
    		if (it == null)
    			it = this.list.iterator();
    		return this.it.hasNext();
    	}
    	
    	
    	/**
    	 * Add another iterator
    	 * @param it An iterator
    	 */
    	public void add(ArrayDatumIterator it) {
    	    if (! (it instanceof BioAssayData.DefArrayDatumIterator))
    	        throw new IllegalArgumentException("Expecting class BioAssayData.DefArrayDatumIterator");
    		this.list.add(((BioAssayData.DefArrayDatumIterator)it).list);
    	}
    	
    }
    
    
    static class RelOp {
    	public static final RelOp GREATER_THAN = new RelOp();
    	public static final RelOp LESS_THAN = new RelOp();
    }

}
