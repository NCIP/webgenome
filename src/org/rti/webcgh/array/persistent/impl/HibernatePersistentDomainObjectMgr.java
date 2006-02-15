/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/HibernatePersistentDomainObjectMgr.java,v $
$Revision: 1.2 $
$Date: 2006-02-15 20:54:47 $

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
package org.rti.webcgh.array.persistent.impl;

import java.util.Date;

import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.Organism;
import org.rti.webcgh.array.persistent.PersistentArray;
import org.rti.webcgh.array.persistent.PersistentArrayDatum;
import org.rti.webcgh.array.persistent.PersistentArrayMapping;
import org.rti.webcgh.array.persistent.PersistentBinnedData;
import org.rti.webcgh.array.persistent.PersistentBioAssay;
import org.rti.webcgh.array.persistent.PersistentBioAssayData;
import org.rti.webcgh.array.persistent.PersistentChromosome;
import org.rti.webcgh.array.persistent.PersistentChromosomeBin;
import org.rti.webcgh.array.persistent.PersistentCytoband;
import org.rti.webcgh.array.persistent.PersistentCytologicalMap;
import org.rti.webcgh.array.persistent.PersistentCytologicalMapSet;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.array.persistent.PersistentExon;
import org.rti.webcgh.array.persistent.PersistentExperiment;
import org.rti.webcgh.array.persistent.PersistentGenomeAssembly;
import org.rti.webcgh.array.persistent.PersistentGenomeFeature;
import org.rti.webcgh.array.persistent.PersistentGenomeFeatureDataSet;
import org.rti.webcgh.array.persistent.PersistentGenomeFeatureType;
import org.rti.webcgh.array.persistent.PersistentGenomeLocation;
import org.rti.webcgh.array.persistent.PersistentOrganism;
import org.rti.webcgh.array.persistent.PersistentPipeline;
import org.rti.webcgh.array.persistent.PersistentPipelineStep;
import org.rti.webcgh.array.persistent.PersistentPipelineStepParameter;
import org.rti.webcgh.array.persistent.PersistentQuantitation;
import org.rti.webcgh.array.persistent.PersistentQuantitationType;
import org.rti.webcgh.array.persistent.PersistentReporter;
import org.rti.webcgh.array.persistent.PersistentReporterMapping;
import org.rti.webcgh.core.WebcghSystemException;

/**
 * Implementation of PersistentDomainObjectMgr using Hibernate
 */
public class HibernatePersistentDomainObjectMgr implements PersistentDomainObjectMgr {
    
    
    
    /**
     * @param hibernatePersistor The hibernatePersistor to set.
     */
    public void setHibernatePersistor(HibernatePersistor hibernatePersistor) {
        HibernatePersistentOrganism.setPersistor(hibernatePersistor);
        HibernatePersistentGenomeAssembly.setPersistor(hibernatePersistor);
        HibernatePersistentChromosome.setPersistor(hibernatePersistor);
        HibernatePersistentQuantitationType.setPersistor(hibernatePersistor);
        HibernatePersistentReporter.setPersistor(hibernatePersistor);
        HibernatePersistentQuantitation.setPersistor(hibernatePersistor);
        HibernatePersistentGenomeLocation.setPersistor(hibernatePersistor);
        HibernatePersistentReporterMapping.setPersistor(hibernatePersistor);
        HibernatePersistentGenomeFeatureType.setPersistor(hibernatePersistor);
        HibernatePersistentGenomeFeatureDataSet.setPersistor(hibernatePersistor);
        HibernatePersistentGenomeFeature.setPersistor(hibernatePersistor);
        HibernatePersistentExon.setPersistor(hibernatePersistor);
        HibernatePersistentCytoband.setPersistor(hibernatePersistor);
        HibernatePersistentCytologicalMap.setPersistor(hibernatePersistor);
        HibernatePersistentCytologicalMapSet.setPersistor(hibernatePersistor);
        HibernatePersistentChromosomeBin.setPersistor(hibernatePersistor);
        HibernatePersistentBinnedData.setPersistor(hibernatePersistor);
        HibernatePersistentArray.setPersistor(hibernatePersistor);
        HibernatePersistentArrayMapping.setPersistor(hibernatePersistor);
        HibernatePersistentBioAssayData.setPersistor(hibernatePersistor);
        HibernatePersistentArrayDatum.setPersistor(hibernatePersistor);
        HibernatePersistentBioAssay.setPersistor(hibernatePersistor);
        HibernatePersistentExperiment.setPersistor(hibernatePersistor);
        HibernatePersistentPipelineStepParameter.setPersistor(hibernatePersistor);
        HibernatePersistentPipelineStep.setPersistor(hibernatePersistor);
        HibernatePersistentPipeline.setPersistor(hibernatePersistor);
    }
    
    
    /**
     * Set JDBC DAO support
     * @param jdbcPersistor JDBC DAO support
     */
    public void setJdbcPersistor(JdbcPersistor jdbcPersistor) {
        HibernatePersistentGenomeFeatureDataSet.setJdbcPersistor(jdbcPersistor);
    }
    
    
    // ===========================================
    //      PersistentDomainObjectMgr interface
    // ===========================================
    
    /**
     * Get persistent organism
     * @param genus Genus
     * @param species Species
     * @param create Create new instance if not found
     * @return Persistent organism
     */
    public PersistentOrganism getPersistentOrganism(String genus, String species, boolean create) {
        HibernatePersistentOrganism org = HibernatePersistentOrganism.load(genus, species);
        if (org == null && create) {
            org = new HibernatePersistentOrganism(genus, species);
            org.save();
        }
        return org;
    }
    
    
    /**
     * Get persistent organism
     * @param id ID
     * @return Persistent organism
     */
    public PersistentOrganism getPersistentOrganism(Long id) {
        return HibernatePersistentOrganism.load(id);
    }
    
    
    /**
     * Get persistent genome assembly
     * @param name Name
     * @param organism Organism
     * @param create Create new instance if not found
     * @return A persistent genome assembly
     */
    public PersistentGenomeAssembly getPersistentGenomeAssembly(String name, 
            PersistentOrganism organism, boolean create) {
        HibernatePersistentGenomeAssembly asm = HibernatePersistentGenomeAssembly.load(name, organism);
        if (asm == null && create) {
            asm = new HibernatePersistentGenomeAssembly(name, organism);
            asm.save();
        }
        return asm;
    }
    
    
    
    /**
     * Get persistent genome assembly
     * @param id ID
     * @return A persistent genome assembly
     */
    public PersistentGenomeAssembly getPersistentGenomeAssembly(Long id) {
        return HibernatePersistentGenomeAssembly.load(id);
    }
    
    
    /**
     * Get persistent chromosome
     * @param assembly Assembly
     * @param number Chromosome number
     * @param create Create if not found
     * @return Persistent chromosome
     */
    public PersistentChromosome getPersistentChromosome(PersistentGenomeAssembly assembly, 
            short number, boolean create) {
        HibernatePersistentChromosome chrom = HibernatePersistentChromosome.load(assembly, number);
        if (chrom == null && create) {
            chrom = new HibernatePersistentChromosome(assembly, number);
            chrom.save();
        }
        return chrom;
    }
        
    
    /**
     * Get persistent chromosome
     * @param assembly Assembly
     * @param number Chromosome number
     * @param length Chromosome length
     * @return Persistent chromosome
     */
    public PersistentChromosome newPersistentChromosome(PersistentGenomeAssembly assembly, 
            short number, long length) {
        HibernatePersistentChromosome chrom = new HibernatePersistentChromosome(assembly, number, length);
        chrom.save();
        return chrom;
    }
    
    
    /**
     * Create new persistent genome location
     * @param chromosome Chromosome
     * @param position Position
     * @return Persistent genome location
     */
    public PersistentGenomeLocation newPersistentGenomeLocation(PersistentChromosome chromosome, 
    		long position) {
        HibernatePersistentGenomeLocation loc = new HibernatePersistentGenomeLocation(chromosome, position);
        loc.save();
        return loc;
    }
    
    
    /**
     * Get persistent reporter
     * @param name Name
     * @param create Create if not found
     * @return Persistent reporter
     */
    public PersistentReporter getPersistentReporter(String name, boolean create) {
        HibernatePersistentReporter rep = HibernatePersistentReporter.load(name);
        if (rep == null && create) {
            rep = new HibernatePersistentReporter(name);
            rep.saveOrUpdate();
        }
        return rep;
    }
    
    
    /**
     * Create persistent reporter mapping
     * @param reporter Reporter
     * @param location Genome location
     * @return A reporter mapping
     */
    public PersistentReporterMapping newPersistentReporterMapping(
    		PersistentReporter reporter, PersistentGenomeLocation location) {
        HibernatePersistentReporterMapping mapping = new HibernatePersistentReporterMapping(reporter, location);
        mapping.save();
        return mapping;
    }
    
    
    /**
     * Get persistent quantitation type
     * @param name Name of type
     * @param create Create if not found
     * @return PersistentQuantitationType
     */
    public PersistentQuantitationType getPersistentQuantitationType(String name, boolean create) {
        HibernatePersistentQuantitationType type = HibernatePersistentQuantitationType.load(name);
        if (type == null && create) {
            type = new HibernatePersistentQuantitationType(name);
            type.save();
        }
        return type;
    }
    
    
    /**
     * Create new persistent quantitation
     * @param value Value
     * @param type Quantitation type
     * @return Persistent quantitation
     */
    public PersistentQuantitation newPersistentQuantitation(float value, PersistentQuantitationType type) {
        HibernatePersistentQuantitation quant = new HibernatePersistentQuantitation(value, type);
        quant.saveOrUpdate();
        return quant;
    }
    
    
    /**
     * Get persistent genome feature type
     * @param name Name of feature type
     * @param create Create if not found
     * @param representsGene Does type represent a gene?
     * @return Persistent genome feature type
     */
    public PersistentGenomeFeatureType getPersistentGenomeFeatureType(String name, boolean representsGene, boolean create) {
        HibernatePersistentGenomeFeatureType type = HibernatePersistentGenomeFeatureType.load(name);
        if (type == null && create) {
        	type = new HibernatePersistentGenomeFeatureType(name, representsGene);
        	type.save();
        }
        return type;
    }
    
    
    /**
     * Get persistent genome feature type
     * @param name Name of feature type
     * @param create Create if not found
     * @return Persistent genome feature type
     */
    public PersistentGenomeFeatureType getPersistentGenomeFeatureType(String name, 
    		boolean create) {
    	HibernatePersistentGenomeFeatureType type = HibernatePersistentGenomeFeatureType.load(name);
    	if (type == null && create) {
        	type = new HibernatePersistentGenomeFeatureType(name, false);
        	type.save();
        }
        return type;
    }
    
    
    /**
     * Create new persistent genome feature data set
     * @param assembly Genome assembly
     * @param date Date
     * @param type Genome feature type
     * @return Persistent genome feature data set
     */
    public PersistentGenomeFeatureDataSet newPersistentGenomeFeatureDataSet(
    		PersistentGenomeAssembly assembly, Date date, 
			PersistentGenomeFeatureType type) {
        HibernatePersistentGenomeFeatureDataSet set = 
        	new HibernatePersistentGenomeFeatureDataSet(assembly, date, type);
        set.save();
        return set;
    }
    
    
    /**
     * Get persistent genome feature data set
     * @param assembly Assembly
     * @param type Genome feature type
     * @return Persistent genome feature data set
     */
    public PersistentGenomeFeatureDataSet getPersistentGenomeFeatureDataSet(
    		PersistentGenomeAssembly assembly, 
			PersistentGenomeFeatureType type) {
        return HibernatePersistentGenomeFeatureDataSet.load(assembly, type);
    }
    
    
    /**
     * Create new persistent genome feature
     * @param name Name
     * @param start Start
     * @param end End
     * @param chromosome Chromosome
     * @param genomeFeatureDataSet Genome feature data set
     * @return Persistent genome feature
     */
    public PersistentGenomeFeature newPersistentGenomeFeature(String name, 
    		long start, long end, PersistentChromosome chromosome, 
			PersistentGenomeFeatureDataSet genomeFeatureDataSet) {
        HibernatePersistentGenomeFeature feat = new HibernatePersistentGenomeFeature(
        		name, start, end, chromosome, genomeFeatureDataSet);
        feat.save();
        return feat;
    }
    
    
    /**
     * Create persistent exon
     * @param start Start
     * @param end End
     * @return Persistent exon
     */
    public PersistentExon newPersistentExon(long start, long end) {
        HibernatePersistentExon exon = new HibernatePersistentExon(start, end);
        exon.saveOrUpdate();
        return exon;
    }
    
    
    /**
     * Create new cytoband
     * @param name Name
     * @param start Start
     * @param end End
     * @param stain Stain
     * @return Persistent cytoband
     */
    public PersistentCytoband newPersistentCytoband(String name, long start, 
    		long end, String stain) {
        HibernatePersistentCytoband cytoband = new HibernatePersistentCytoband(name, start, end, stain);
        cytoband.saveOrUpdate();
        return cytoband;
    }
    
    
    /**
     * Create persistent cytological map
     * @param chromosome Chromosome
     * @return Persistent cytological map
     */
    public PersistentCytologicalMap newPersistentCytologicalMap(
    		PersistentChromosome chromosome) {
        HibernatePersistentCytologicalMap map =
        	new HibernatePersistentCytologicalMap(chromosome);
        map.save();
        return map;
    }
    
    
    /**
     * Create persistent cytological map
     * @param chromosome Chromosome
     * @param centromereStart Centromere start
     * @param centromereEnd Centromere end
     * @return Persistent cytological map
     */
    public PersistentCytologicalMap newPersistentCytologicalMap(
    		PersistentChromosome chromosome, long centromereStart, 
			long centromereEnd) {
    	HibernatePersistentCytologicalMap map =
        	new HibernatePersistentCytologicalMap(chromosome, 
        			centromereStart, centromereEnd);
        map.save();
        return map;
    }
    
    
    /**
     * Create persistent cytological map set
     * @param assembly Genome assembly
     * @param date Date
     * @return Persistent cytological map set
     */
    public PersistentCytologicalMapSet newPersistentCytologicalMapSet(
    		PersistentGenomeAssembly assembly, Date date) {
        HibernatePersistentCytologicalMapSet set =
        	new HibernatePersistentCytologicalMapSet(assembly, date);
        set.save();
        return set;
    }
    
    
    /**
     * Get persistent cytological map set
     * @param assembly Genome assembly
     * @return Persistent cytological map set
     */
    public PersistentCytologicalMapSet getPersistentCytologicalMapSet(
    		PersistentGenomeAssembly assembly) {
        return HibernatePersistentCytologicalMapSet.load(assembly);
    }
    
    
    /**
     * Create new persistent chromosome bin
     * @param bin Chromosome bin
     * @param value Value
     * @param chromosomeNum Chromosome number
     * @return Persistent chromosome bin
     */
    public PersistentChromosomeBin newPersistentChromosomeBin(int bin, 
    		float value, int chromosomeNum) {
        HibernatePersistentChromosomeBin chromBin =
        	new HibernatePersistentChromosomeBin(bin, value, chromosomeNum);
        chromBin.saveOrUpdate();
        return chromBin;
    }
    
    
    /**
     * Create persistent binned data
     * @param type Type
     * @return Persistent binned data
     */
    public PersistentBinnedData newPersistentBinnedData(
    		PersistentQuantitationType type) {
        HibernatePersistentBinnedData data =
        	new HibernatePersistentBinnedData(type);
        data.save();
        return data;
    }
    
    
    /**
     * Get persistent binned data
     * @param id ID
     * @return Persistent binned data
     */
    public PersistentBinnedData getPersistentBinnedData(Long id) {
        return HibernatePersistentBinnedData.load(id);
    }
    
    
    /**
     * Get persistent array
     * @param vendor Vendor
     * @param name Array name
     * @param organism Organism
     * @param create Create if not found
     * @return A persistent array
     */
    public PersistentArray getPersistentArray(String vendor, String name, 
    		PersistentOrganism organism, boolean create) {
        HibernatePersistentArray array =
        	HibernatePersistentArray.load(vendor, name, organism);
        if (array == null && create) {
        	array = new HibernatePersistentArray(vendor, name, organism);
        	array.save();
        }
        return array;
    }
    
    
    /**
     * Get persistent array
     * @param id ID
     * @return A persistent array
     */
    public PersistentArray getPersistentArray(Long id) {
        return HibernatePersistentArray.load(id);
    }
    
    
    /**
     * Get persistent array mapping
     * @param array Array
     * @param assembly Genome assembly
     * @param create Create if not found
     * @return A persistent array mapping
     */
    public PersistentArrayMapping getPersistentArrayMapping(
    		PersistentArray array, PersistentGenomeAssembly assembly, boolean create) {
        HibernatePersistentArrayMapping mapping = HibernatePersistentArrayMapping.load(array, assembly);
        if (mapping == null && create) {
            mapping = new HibernatePersistentArrayMapping(array, assembly);
            mapping.save();
        }
        return mapping;
    }
    
    
    /**
     * Create new persistent array datum
     * @param reporter A reporter
     * @param quantitation A quantitation
     * @return A persistent array datum
     */
    public PersistentArrayDatum newPersistentArrayDatum(PersistentReporter reporter, 
            PersistentQuantitation quantitation) {
        HibernatePersistentArrayDatum datum = new HibernatePersistentArrayDatum(reporter, quantitation);
        datum.saveOrUpdate();
        return datum;
    }
    
    
    /**
     * Create persistent bioassay data
     * @return Persistent bioassay data
     */
    public PersistentBioAssayData newPersistentBioAssayData() {
        HibernatePersistentBioAssayData bad = new HibernatePersistentBioAssayData();
        bad.saveOrUpdate();
        return bad;
    }
    
    
    /**
     * Get persistent bioassay data
     * @param id ID
     * @return Persistent bioassay data
     */ 
    public PersistentBioAssayData getPersistentBioAssayData(Long id) {
        return HibernatePersistentBioAssayData.load(id);
    }
    
    
    /**
     * Create new persistent bioassay
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @return Persistent bioassay
     */
    public PersistentBioAssay newPersistentBioAssay(String name, String description, String databaseName) {
        HibernatePersistentBioAssay bioAssay = new HibernatePersistentBioAssay(name, description, databaseName);
        bioAssay.save();
        return bioAssay;
    }
    
    
    /**
     * Get bioassay
     * @param id ID
     * @return Persistent bioassay
     */
    public PersistentBioAssay getPersistentBioAssay(Long id) {
        return HibernatePersistentBioAssay.load(id);
    }
    
    
    /**
     * Create persistent experiment
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @return Persistent description
     */
    public PersistentExperiment newPersistentExperiment(String name, String description, String databaseName) {
        HibernatePersistentExperiment exp = new HibernatePersistentExperiment(name, description, databaseName);
        exp.save();
        return exp;
    }
    
    
    /**
     * Create persistent experiment
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is experiment virtual?
     * @return Persistent description
     */
    public PersistentExperiment newPersistentExperiment(String name, String description, String databaseName, boolean virtual) {
        HibernatePersistentExperiment exp = 
            new HibernatePersistentExperiment(name, description, databaseName, virtual);
        exp.save();
        return exp;
    }
    
    
    /**
     * Get all persistent experiments
     * @return All persistent experiments
     */
    public PersistentExperiment[] getAllPersistentPublicExperiments() {
        return HibernatePersistentExperiment.loadAllPublicExperiments();
    }
    
    
    /**
     * Delete all persistent experiments
     *
     */
    public void deleteAllPersistentPublicExperiments() {
        HibernatePersistentExperiment.deleteAllPublicExperiments();
    }
    
    
    /**
     * Get persistent experiment
     * @param id ID
     * @return Persistent experiment
     */
    public PersistentExperiment getPersistentExperiment(Long id) {
        return HibernatePersistentExperiment.load(id);
    }
    
    
    /**
     * Delete all persistent genome feature data sets
     *
     */
    public void deleteAllPersistentGenomeFeatureDataSets() {
        HibernatePersistentGenomeFeatureDataSet.deleteAll();
    }
    
    
    /**
     * Get all persistent organisms
     * @return Persistent organisms
     */
    public PersistentOrganism[] getAllPersistentOrganisms() {
        return HibernatePersistentOrganism.loadAll();
    }
    
    
    /**
     * Get all persistent genome assemblies associated with given organism
     * @param organism An organism
     * @return Persistent genome assemblies
     */
    public PersistentGenomeAssembly[] getAllPersistentGenomeAssemblies(PersistentOrganism organism) {
        return HibernatePersistentGenomeAssembly.loadAll(organism);
    }
    
    
    /**
     * Get all persistent genome assemblies
     * @return Persistent genome assemblies
     */
    public PersistentGenomeAssembly[] getAllPersistentGenomeAssemblies() {
        return HibernatePersistentGenomeAssembly.loadAll();
    }
    
    
    /**
     * Get all persistent genome feature data sets associated with given assembly 
     * @param assembly A genome assembly
     * @return Genome feature data sets
     */
    public PersistentGenomeFeatureDataSet[] getAllPersistentGenomeFeatureDataSets(PersistentGenomeAssembly assembly) {
        return HibernatePersistentGenomeFeatureDataSet.loadAll(assembly);
    }
    
    
    /**
     * Delete all cytological map sets
     *
     */
    public void deleteAllPersistentCytologicalMapSets() {
        HibernatePersistentCytologicalMapSet.deleteAll();
    }
    
    
    /**
     * Get all cytological map sets
     * @return Cytological map sets
     */
    public PersistentCytologicalMapSet[] getAllPersistentCytologicalMapSets() {
        return HibernatePersistentCytologicalMapSet.loadAll();
    }
    
    
    /**
     * Delete all persistent arrays
     *
     */
    public void deleteAllPersistentArrays() {
    	HibernatePersistentArrayMapping.deleteAll();
        HibernatePersistentArray.deleteAll();
    }
    
    
    
    /**
     * Get all persistent array mappings
     * @return All persistent array mappings
     */
    public PersistentArrayMapping[] getAllPersistentArrayMappings() {
        return HibernatePersistentArrayMapping.loadAll();
    }
    
    
    /**
     * Get all persistent array mappings associated with array
     * @param array Array
     * @return All persistent array mappings
     */
    public PersistentArrayMapping[] getAllPersistentArrayMappings(PersistentArray array) {
    	return HibernatePersistentArrayMapping.loadAll(array);
    }
    
    
    /**
     * Create new persistent pipeline step parameter
     * @param paramName Parameter name
     * @param value Parameter value
     * @return Persistent pipeline step parameter
     */
    public PersistentPipelineStepParameter newPersistentPipelineStepParameter(String paramName, String value) {
        HibernatePersistentPipelineStepParameter param = new HibernatePersistentPipelineStepParameter(paramName, value);
        param.save();
        return param;
    }
    
    
    /**
     * Create new persistent pipeline step
     * @param className Class name
     * @param step Step number
     * @return Persistent pipeline step
     */
    public PersistentPipelineStep newPersistentPipelineStep(String className, int step) {
        HibernatePersistentPipelineStep pipeStep = new HibernatePersistentPipelineStep(className, step);
        pipeStep.save();
        return pipeStep;
    }
    
    
    /**
     * Create new persistent pipeline
     * @param name Pipeline name
     * @param userName User name
     * @return Persistent pipeline
     */
    public PersistentPipeline newPersistentPipeline(String name, String userName) {
        HibernatePersistentPipeline pipe = new HibernatePersistentPipeline(name, userName);
        pipe.save();
        return pipe;
    }
    
    
    /**
     * Get persistent pipeline
     * @param name Pipeline name
     * @param userName User name
     * @return Persistent pipeline
     */
    public PersistentPipeline getPersistentPipeline(String name, String userName) {
        return HibernatePersistentPipeline.load(name, userName);
    }
    
    
    /**
     * Get all persistent pipelines associated with given user
     * @param userName User name
     * @return Persistent pipelines
     */
    public PersistentPipeline[] getAllPersistentPipelines(String userName) {
        return HibernatePersistentPipeline.loadAll(userName);
    }
    
    
    /**
     * Create persistent experiment
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is experiment virtual?
     * @param userName User name
     * @return Persistent description
     */
    public PersistentExperiment newPersistentExperiment(String name, String description, 
            String databaseName, boolean virtual, String userName) {
    	HibernatePersistentExperiment exp = new HibernatePersistentExperiment(
    			name, description, databaseName, virtual, userName);
    	exp.save();
    	return exp;
    }
    
    
    /**
     * Get all virtual experiments associated with given user name
     * @param userName User name
     * @return Virtual experiments
     */
    public PersistentExperiment[] getAllVirtualExperiments(String userName) {
        return HibernatePersistentExperiment.loadAllVirtualExperiments(userName);
    }
    
    
    /**
     * Get virtual experiment
     * @param name Experiment name
     * @param userName User name
     * @return Virtual experiment
     */
    public PersistentExperiment getVirtualExperiment(String name, String userName) {
        return HibernatePersistentExperiment.loadVirtualExperiment(name, userName);
    }
    
    
    /**
     * Get persistent cytological map
     * @param chromosome Chromosome
     * @return Persistent cytological map
     */
    public PersistentCytologicalMap getPersistentCytologicalMap(Chromosome chromosome) {
        if (! (chromosome instanceof HibernatePersistentChromosome)) {
            PersistentOrganism org =
                HibernatePersistentOrganism.load(Organism.DEFAULT_GENUS, Organism.DEFAULT_SPECIES);
            if (org == null)
                throw new WebcghSystemException("Default organism not found in embedded database");
            PersistentGenomeAssembly asm = HibernatePersistentGenomeAssembly.loadLatest(org);
            if (asm == null)
                throw new WebcghSystemException("No genome assemblies found in embedded database for " +
                        org.toPrintableString());
            chromosome = this.getPersistentChromosome(asm, chromosome.getNumber(), false);
            if (chromosome == null)
                throw new WebcghSystemException("Cannot find persistent chromosome");
        }
        return HibernatePersistentCytologicalMap.load(chromosome);
    }
    
    
    /**
     * Get default persistent chromosome
     * @param number Chromosome number
     * @return Persistent chromosome
     */
    public PersistentChromosome getDefaultPersistentChromosome(short number) {
        PersistentOrganism org =
            HibernatePersistentOrganism.load(Organism.DEFAULT_GENUS, Organism.DEFAULT_SPECIES);
        if (org == null)
            throw new WebcghSystemException("Default organism not found in embedded database");
        PersistentGenomeAssembly asm = HibernatePersistentGenomeAssembly.loadLatest(org);
        if (asm == null)
            throw new WebcghSystemException("No genome assemblies found in embedded database for " +
                    org.toPrintableString());
        return this.getPersistentChromosome(asm, number, false);
    }
}
