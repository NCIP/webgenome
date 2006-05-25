/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/ReporterMappingStagingArea.java,v $
$Revision: 1.6 $
$Date: 2006-05-25 19:41:30 $

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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rti.webcgh.array.persistent.PersistentArray;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.array.persistent.PersistentGenomeAssembly;
import org.rti.webcgh.core.WebcghApplicationException;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.service.AuthenticationException;
import org.rti.webcgh.service.WebcghArrayDataSourceSet;
import org.rti.webcgh.service.UserProfile;
import org.rti.webcgh.util.CollectionUtils;


/**
 * Helper class for mapping probe locations
 */
public class ReporterMappingStagingArea {
	
	// ========================================
	//         Static variables
	// ========================================
	
	private static final Logger LOGGER = Logger.getLogger(ReporterMappingStagingArea.class);
	private static final Map CHROM_SIZES = new HashMap();
	
    
    // ==================================================
    //        State variables
    // ==================================================
    
    private Map dataMap = new HashMap();
    private Experiment[] experiments = null;
    private WebcghArrayDataSourceSet webcghArrayDataSourceSet = null;
    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
    

    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
    /**
     * @param arrayExperimentDaoSet The arrayExperimentDaoSet to set.
     */
    public void setWebcghArrayDataSourceSet(
            WebcghArrayDataSourceSet arrayExperimentDaoSet) {
        this.webcghArrayDataSourceSet = arrayExperimentDaoSet;
    }
    
	
	
    // ====================================================
    //         Public methods
    // ====================================================
    
    /**
     * Initialize staging area
     * @param userProfile User profile
     * @param dbNames Database names
     * @param experimentNames Names of experiments
     * @throws AuthenticationException
     */
    public void initialize
	(
		UserProfile userProfile, String[] dbNames, String[] experimentNames
	) throws AuthenticationException {
        if (dbNames == null || experimentNames == null)
            throw new IllegalArgumentException("Arrays 'dbNames' and 'experimentNames' cannot be null");
        if (dbNames.length != experimentNames.length)
            throw new IllegalArgumentException("Arrays 'dbNames' and 'experimentNames' must be of equal length");
        
        // Retrieve experiments and index
        this.experiments = this.getExperiments(userProfile, dbNames, experimentNames);
        for (int i = 0; i < experiments.length; i++) {
            Experiment experiment = this.experiments[i];
            this.initialize(experiment);
        }
    }
    
    
    public void initialize(Experiment experiment) {
    	Object key = experiment.getCacheKey();
    	
    	// VB added start
    	this.experiments = new Experiment[1];
    	this.experiments[0] = experiment;
    	// VB added stop
    	
        this.dataMap.put(key, experiment);
    }
    
    
    /**
     * Get genome array data sets as a collection object
     * @return Genome array data sets as a collection object
     */
    public Collection getExperimentsAsCollection() {
        return CollectionUtils.arrayToArrayList(this.experiments);
    }
    
    
    /**
     * Get experiments
     * @return Experiments
     */
    public Experiment[] getExperiments() {
        return this.experiments;
    }
    
    
    /**
     * Set probe set for specified genome array data
     * @param dbName Database name
     * @param experimentName Name of genome array data set
     * @param bioAssayName Name of genome array data
     * @param array Probe set
     */
    public void setArray
	(
		String dbName, String experimentName, String bioAssayName,
		Array array
	) {
        Object key = Experiment.cacheKey(dbName, experimentName);
    	Experiment gads = (Experiment)this.dataMap.get(key);
    	BioAssay bioAssay = gads.getBioAssay(bioAssayName);
    	bioAssay.setArray(array);
    	bioAssay.setOrganism(array.getOrganism());
    }
    
    
    /**
     * Set probe set for specified genome array data
     * @param dbName Database name
     * @param experimentName Name of genome array data set
     * @param bioAssayName Name of genome array data
     * @param experimentType CGH or gene expression
     */
    public void setBioAssayType
	(
		String dbName, String experimentName, String bioAssayName,
		BioAssayType experimentType
	) {
        Object key = Experiment.cacheKey(dbName, experimentName);
    	Experiment experiment = (Experiment)this.dataMap.get(key);
    	BioAssay bioAssay = experiment.getBioAssay(bioAssayName);
    	bioAssay.setBioAssayType(experimentType);
    }
    
    
    /**
     * Set probe set for specified genome array data
     * @param dbName Database name
     * @param experimentName Name of genome array data set
     * @param bioAssayName Name of genome array data
     * @param organism Organism
     */
    public void setOrganism
	(
		String dbName, String experimentName, String bioAssayName,
		Organism organism
	) {
        Object key = Experiment.cacheKey(dbName, experimentName);
    	Experiment experiment = 
    		(Experiment)this.dataMap.get(key);
    	BioAssay bioAssay = experiment.getBioAssay(bioAssayName);
    	bioAssay.setOrganism(organism);
    }
    
    
    
    /**
     * Get all organisms represented in data
     * @return Organisms
     */
    public Organism[] getOrganisms() {
    	Map orgMap = new HashMap();
    	for (int i = 0; i < this.experiments.length; i++) {
    		Experiment experiment = this.experiments[i];
    		for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();) {
    			BioAssay bioAssay = it.next();
				Organism org = bioAssay.getOrganism();
				if (! orgMap.containsKey(org.getDisplayName()))
					orgMap.put(org.getDisplayName(), org);
    		}
    	}
    	Organism[] orgs = new Organism[0];
    	orgs = (Organism[])orgMap.values().toArray(orgs);
    	return orgs;
    }
    
    
    /**
     * If any genome array data sets contains genome array data
     * objects associated with multiple organisms, split on
     * organism
     * @return Names of all genome array data sets that were split
     */
    public String[] splitExperimentsOnOrganism() {
    	return this.split(new OrganismExperimentDataSetSplitHelper());
    }
    
    
    /**
     * If any genome array data sets contains genome array data
     * objects associated with multiple organisms, split on
     * organism
     * @return Names of all genome array data sets that were split
     */
    public String[] splitExperimentsOnBioAssayType() {
    	return this.split(new ExperimentTypeExperimentSplitHelper());
    }
    
    
    /**
     * Map locations of probes
     * @param assemblies Genome assemblies.
     * @param useExistingLocations Use existing probe locations if available
     * (i.e. probe locations the were provided at upload)
     * @return Probe mapping results for each experiment
     */
    public ExperimentProbeMappingResults[] mapProbes
	(
		GenomeAssembly[] assemblies, boolean useExistingLocations
	) {
    	
    	// Instantiate experiment probe mapping results object
    	ExperimentProbeMappingResults[] results = 
    		new ExperimentProbeMappingResults[this.experiments.length];
    	
    	// Index of ArrayMapping objects index on Array
    	Map arrayMappingIndex = new HashMap();
    	
    	// Index of quasi-reporters derived from chromosome bins
    	Map binReporterIndex = new HashMap();
    	
    	// Iterate over genome array data sets and map all probes
    	for (int i = 0; i < this.experiments.length; i++) {
    		Experiment experiment = this.experiments[i];
    		GenomeAssembly assembly = this.selectAppropriateGenomeAssembly(assemblies, experiment.getOrganism());
    		experiment.setReferenceAssembly(assembly);
    		results[i] = new ExperimentProbeMappingResults(experiment);
    		for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();) {
    			BioAssay bioAssay = it.next();
    			BioAssayProbeMappingResults bioAssayResults = null;
    			if (! bioAssay.hasBinnedData())
	    			bioAssayResults = this.mapNormalProbes(bioAssay, arrayMappingIndex, 
	    				assemblies, useExistingLocations);
    			else 
    				bioAssayResults = this.mapBinnedProbes(bioAssay, assemblies, binReporterIndex);
    			results[i].add(bioAssayResults);
    		}
    	}
    	
    	return results;
    }

      
    
    // ================================================
    //          Private methods
    // ================================================
    
    private Experiment[] getExperiments
    (
        UserProfile userProfile, String[] dbNames, String[] experimentNames
    ) throws AuthenticationException {
        assert dbNames.length == experimentNames.length;
        Experiment[] experiments = new Experiment[experimentNames.length];
        for (int i = 0; i < experimentNames.length && i < dbNames.length; i++) {
        	LOGGER.info("Loading experiment '" + experimentNames[i] + "'");
            experiments[i] = 
                this.webcghArrayDataSourceSet.loadExperiment(experimentNames[i], 
                		dbNames[i], userProfile);
            experiments[i].setDatabaseName(dbNames[i]);
            for (BioAssayIterator it = experiments[i].bioAssayIterator(); it.hasNext();) {
            	BioAssay bioAssay = it.next();
            	if (! bioAssay.hasBinnedData()) {
            		String bioAssayDataId = bioAssay.getBioAssayDataId();
            		String dbName = bioAssay.getDatabaseName();
            		bioAssay.add(this.webcghArrayDataSourceSet.loadBioAssayData(
            				bioAssayDataId, dbName, userProfile));
            	}
            }
        }
        return experiments;
    }
    
    
    private String[] split(ExperimentSplitHelper splitHelper) {
    	Collection splitList = new ArrayList();
    	Collection newExperimentsCol = new ArrayList();
    	for (int i = 0; i < this.experiments.length; i++) {
    		Experiment experiment = this.experiments[i];
    		Map groupings = splitHelper.group(experiment);
    		if (groupings.keySet().size() > 1) {
    			LOGGER.info("Split data from experiment '" + experiment.getName() + "'");
    			Collection splitDataSets = splitHelper.newSplitExperiments(experiment, groupings);
    			newExperimentsCol.addAll(splitDataSets);
    			splitList.add(experiment.getDatabaseName() + " " + experiment.getName());
    		} else {
    			newExperimentsCol.add(experiment);
    		}
    	}
    	this.experiments = new Experiment[0];
    	this.experiments = (Experiment[])
			newExperimentsCol.toArray(this.experiments);
    	String[] splits = new String[0];
    	splits = (String[])splitList.toArray(splits);
    	this.dataMap = new HashMap();
    	for (Iterator it = newExperimentsCol.iterator(); it.hasNext();) {
    		Experiment experiment = (Experiment)it.next();
    		Object key = experiment.getCacheKey();
    		this.dataMap.put(key, experiment);
    	}
    	return splits;
    }
    
    

    private BioAssayProbeMappingResults mapNormalProbes
	(
		BioAssay bioAssay, Map probeSetMappingIndex,
		GenomeAssembly[] assemblies, boolean useExistingLocations
	) {
		BioAssayProbeMappingResults arrayResults = new BioAssayProbeMappingResults(bioAssay);
		
		// Get array mapping
		GenomeAssembly assembly = this.selectAppropriateGenomeAssembly(assemblies, bioAssay.getOrganism());
		Array array = bioAssay.getArray();
		ArrayMapping arrayMapping = (ArrayMapping)probeSetMappingIndex.get(array);
		if (arrayMapping == null) {
		    arrayMapping = this.persistentDomainObjectMgr.getPersistentArrayMapping((PersistentArray)array, 
		            (PersistentGenomeAssembly)assembly, true);
			if (arrayMapping != null)
				probeSetMappingIndex.put(array, arrayMapping);
		}
		
		// Set probe locations
		int reporterCount = 0;
		int mappedReporterCount = 0;
		for (ArrayDatumIterator it = bioAssay.arrayDatumIterator(); it.hasNext();) {
			reporterCount++;
			ArrayDatum datum = it.next();
			if (datum.hasLocation() && useExistingLocations) {
				mappedReporterCount++;
				datum.setGenomeAssembly(assembly);
				continue;
			}
			if (arrayMapping == null)
				continue;
			ReporterMapping reporterMapping = arrayMapping.reporterMapping(datum.getReporter());
			if (reporterMapping != null) {
				mappedReporterCount++;
				datum.locateInGenome(reporterMapping);
			}
		}
		
		arrayResults.setNumReporters(reporterCount);
		arrayResults.setNumMappedReporters(mappedReporterCount);
		return arrayResults;
	}
    
    
    private BioAssayProbeMappingResults mapBinnedProbes
	(
		BioAssay bioAssay, GenomeAssembly[] assemblies, Map binReporterIndex
	) {
    	BioAssayProbeMappingResults results = new BioAssayProbeMappingResults(bioAssay);
    	GenomeAssembly assembly = 
    		this.selectAppropriateGenomeAssembly(assemblies, bioAssay.getOrganism());
    	int numReporters = 0;
    	int numMappedReporters = 0;
    	Chromosome currChromosome = null;
    	int currChromosomeNum = -1;
    	long binSize = 0;
    	int numBinsOfCurrChromosome = 0;
    	for (ChromosomeBinIterator it = bioAssay.chromosomeBinIterator(); it.hasNext();) {
    	    ChromosomeBin bin = it.next();
    	    if (bin.getChromosomeNum() != currChromosomeNum) {
    	        currChromosome = this.persistentDomainObjectMgr.getPersistentChromosome(
    	                (PersistentGenomeAssembly)assembly, (short)currChromosomeNum, true);
    	    	if (currChromosome == null)
    	    		throw new WebcghSystemException("No information on chromosome  " + currChromosomeNum);
    	    	numBinsOfCurrChromosome = bioAssay.numChromosomeBins(currChromosomeNum);
    	    	binSize = currChromosome.getLength() / 
					(long)numBinsOfCurrChromosome;
    	    }
    	    String reporterName = "bin " + currChromosomeNum + "-" + bin.getBin() +
    	    	"-" + numBinsOfCurrChromosome;
    	    Reporter reporter = (Reporter)binReporterIndex.get(reporterName);
    	    if (reporter == null) {
    	    	reporter = new Reporter(reporterName);
    	    	long position = (bin.getBin() + 1) * binSize - binSize / 2;
    	    	GenomeLocation genomeLocation = new GenomeLocation(currChromosome, position);
    	    	ReporterMapping reporterMapping = new ReporterMapping(reporter, genomeLocation);
    	    	reporter.setReporterMapping(reporterMapping);
    	    }
    	    Quantitation quant = new Quantitation(bin.getValue(), bioAssay.binnedQuantitationType());
    	    ArrayDatum datum = new ArrayDatum(reporter, quant);
    	    
    	    // Wrap checked exception as unchecked,
			// as it would not be expected to occur
    		try {
				bioAssay.add(datum);
			} catch (WebcghApplicationException e) {
				throw new WebcghSystemException(e);
			}
			numReporters++;
			numMappedReporters++;
    	}
    	results.setNumReporters(numReporters);
    	results.setNumMappedReporters(numMappedReporters);
    	return results;
    }
    
    
//    private BioAssayProbeMappingResults mapFragmentedProbes
//    (
//		GenomeArrayData gad, GenomeAssembly[] assemblies
//	) {
//    	BioAssayProbeMappingResults results = new BioAssayProbeMappingResults(gad);
//    	CytobandDao cytDao = this.annotationDaoFactory.getCytobandDao();
//    	GenomeAssembly assembly = 
//    		this.selectAppropriateGenomeAssembly(assemblies, gad);
//    	int numProbes = 0;
//    	int numMappedProbes = 0;
//    	Map chromosomeDataSets = gad.getChromosomeDataSets();
//    	for (Iterator chromIt = chromosomeDataSets.keySet().iterator(); 
//    		chromIt.hasNext();) {
//    		Integer chromInt = (Integer)chromIt.next();
//    		int chromosome = chromInt.intValue();
//    		ChromosomeArrayData chromData = (ChromosomeArrayData)
//				chromosomeDataSets.get(chromInt);
//    		int sizeChrom = getCachedChromosomeLength(assembly, chromosome);
//    		if (sizeChrom < 0) {
//	    		Cytoband cytoband = cytDao.getLastCytoband(assembly, chromosome);
//	    		if (cytoband == null)
//	    			throw new WebcghSystemException("Cannot determine length of " +
//	    				"chromosome '" + chromosome + "' " +
//	    				"for assembly '" + assembly.getDisplayName() + "'");
//	    		sizeChrom = cytoband.getChromEnd();
//	    		cacheChromosomeLength(assembly, chromosome, sizeChrom);
//    		}
//    		ArrayDatum[] arrayData = chromData.getArrayData();
//			for (int i = 0; i < arrayData.length; i++) {
//				ArrayDatum datum = arrayData[i];
//				Reporter reporter = datum.getReporter();
//				RelativePhysicalLocation location = reporter.getRelativePhysicalLocation();
//				FractionalDistanceFromPTerm dist = (FractionalDistanceFromPTerm)location;
//				int pos = (int)((double)sizeChrom * dist.getDistance());
//				reporter.setChromosome(chromosome);
//				reporter.setChromosomeLocation(pos);
//				numProbes++;
//				numMappedProbes++;
//			}
//    	}
//    	results.setNumReporters(numProbes);
//    	results.setNumMappedReporters(numMappedProbes);
//    	return results;
//    }
    
    
    private GenomeAssembly selectAppropriateGenomeAssembly(GenomeAssembly[] assemblies, Organism organism) {
		GenomeAssembly assembly = null;
		for (int i = 0; i < assemblies.length && assembly == null; i++)
			if (assemblies[i].getOrganism().equals(organism))
				assembly = assemblies[i];
		return assembly;
    }
    
    
    private static int getCachedChromosomeLength
	(
		GenomeAssembly assembly, int chromosome
	) {
    	int length = -1;
    	String key = assembly.getId() + "" + chromosome;
    	Integer lengthInt = (Integer)CHROM_SIZES.get(key);
    	if (lengthInt != null)
    		length = lengthInt.intValue();
    	return length;
    }
    
    
    private static void cacheChromosomeLength
    (
    	GenomeAssembly assembly, int chromosome, int length
    ) {
    	String key = assembly.getId() + "" + chromosome;
    	CHROM_SIZES.put(key, new Integer(length));
    }
    
    // ====================================================
    //        Inner interfaces
    // ====================================================
    
    /**
     * Helps in the splitting of genome array data sets
     *
     */
    interface ExperimentSplitHelper {
    	
    	/**
    	 * Group genome array data objects by some criteria
    	 * @param experiment A genome array data set to split
    	 * @return Map where keys are category names and values
    	 * collections of genome array data objects
    	 */
    	public abstract Map group(Experiment experiment);
    	
    	/**
    	 * Create split genome array data sets
    	 * @param experiment Parent genome array data set
    	 * @param orgGroupings Groupings of genome array data objects
    	 * @return Collection of new genome array data sets
    	 */
    	public abstract Collection newSplitExperiments
		(
			Experiment experiment, Map orgGroupings
		);
    }
    
    
    // ==================================================
    //        Inner classes
    // ==================================================
    
    
    abstract class DefExperimentSplitHelper implements ExperimentSplitHelper {
        
        
    	/**
    	 * Group genome array data objects by some criteria
    	 * @param experiment A genome array data set to split
    	 * @return Map where keys are category names and values
    	 * collections of genome array data objects
    	 */
    	public abstract Map group(Experiment experiment);
    	
    	
    	/**
    	 * Create split genome array data sets
    	 * @param experiment Parent genome array data set
    	 * @param orgGroupings Groupings of genome array data objects
    	 * @return Collection of new genome array data sets
    	 */
    	public Collection newSplitExperiments
		(
			Experiment experiment, Map orgGroupings
		) {
        	Collection splits = new ArrayList();
        	Set keys = orgGroupings.keySet();
        	for (Iterator it = keys.iterator(); it.hasNext();) {
        		String key = (String)it.next();
        		Collection grouping = (Collection)orgGroupings.get(key);
        		Experiment newExperiment = new Experiment();
        		newExperiment.bulkSetMetadata(experiment);
        		for (Iterator bioAssayIt = grouping.iterator(); bioAssayIt.hasNext();)
        		    newExperiment.add((BioAssay)bioAssayIt.next());
        		splits.add(newExperiment);
        	}
        	return splits;
    	}
        
    }
    
    class OrganismExperimentDataSetSplitHelper extends DefExperimentSplitHelper {
    	
    	/**
    	 * Group genome array data objects by some criteria
    	 * @param experiment A genome array data set to split
    	 * @return Map where keys are category names and values
    	 * collections of genome array data objects
    	 */
    	public Map group(Experiment experiment) {
        	Map orgGroupings = new HashMap();
        	for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();) {
				BioAssay bioAssay = it.next();
				Organism org = bioAssay.getOrganism();
				String key = org.getDisplayName();
				Collection grouping = (Collection)orgGroupings.get(key);
				if (grouping == null) {
					grouping = new ArrayList();
					orgGroupings.put(key, grouping);
				}
				grouping.add(bioAssay);
    		}
    		return orgGroupings;	
    	}
    }
    
    
    class ExperimentTypeExperimentSplitHelper extends DefExperimentSplitHelper {
    	
    	/**
    	 * Group genome array data objects by some criteria
    	 * @param experiment A genome array data set to split
    	 * @return Map where keys are category names and values
    	 * collections of genome array data objects
    	 */
    	public Map group(Experiment experiment) {
        	Map typeGroupings = new HashMap();
    		for (BioAssayIterator it = experiment.bioAssayIterator(); it.hasNext();) {
				BioAssay bioAssay = it.next();
				BioAssayType type = bioAssay.getBioAssayType();
				String key = type.getName();
				Collection grouping = (Collection)typeGroupings.get(key);
				if (grouping == null) {
					grouping = new ArrayList();
					typeGroupings.put(key, grouping);
				}
				grouping.add(bioAssay);
			}
    		return typeGroupings;	
    	}
    }
}
