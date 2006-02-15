/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/PersistentDomainObjectMgr.java,v $
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
package org.rti.webcgh.array.persistent;

import java.util.Date;

import org.rti.webcgh.array.Chromosome;

/**
 * 
 */
public interface PersistentDomainObjectMgr {
    
    /**
     * Get persistent organism
     * @param genus Genus
     * @param species Species
     * @param create Create new instance if not found
     * @return Persistent organism
     */
    public PersistentOrganism getPersistentOrganism(String genus, String species, 
    		boolean create);
    
    
    /**
     * Get persistent organism
     * @param id ID
     * @return Persistent organism
     */
    public PersistentOrganism getPersistentOrganism(Long id);
    
    
    /**
     * Get persistent genome assembly
     * @param name Name
     * @param organism Organism
     * @param create Create new instance if not found
     * @return A persistent genome assembly
     */
    public PersistentGenomeAssembly getPersistentGenomeAssembly(String name, 
    		PersistentOrganism organism, boolean create);
    
    
    /**
     * Get persistent genome assembly
     * @param id ID
     * @return A persistent genome assembly
     */
    public PersistentGenomeAssembly getPersistentGenomeAssembly(Long id);
    
    
    /**
     * Get persistent chromosome
     * @param assembly Assembly
     * @param number Chromosome number
     * @param create Create if not found
     * @return Persistent chromosome
     */
    public PersistentChromosome getPersistentChromosome(
    		PersistentGenomeAssembly assembly, short number, boolean create);
    
    
    /**
     * Get persistent chromosome
     * @param assembly Assembly
     * @param number Chromosome number
     * @param length Chromosome length
     * @return Persistent chromosome
     */
    public PersistentChromosome newPersistentChromosome(
    		PersistentGenomeAssembly assembly, short number, long length);
    
    
    /**
     * Create new persistent genome location
     * @param chromosome Chromosome
     * @param position Position
     * @return Persistent genome location
     */
    public PersistentGenomeLocation newPersistentGenomeLocation(
    		PersistentChromosome chromosome, long position);
    
    
    /**
     * Get persistent reporter
     * @param name Name
     * @param create Create if not found
     * @return Persistent reporter
     */
    public PersistentReporter getPersistentReporter(String name, boolean create);
    
    
    /**
     * Create persistent reporter mapping
     * @param reporter Reporter
     * @param location Genome location
     * @return A reporter mapping
     */
    public PersistentReporterMapping newPersistentReporterMapping(
    		PersistentReporter reporter, PersistentGenomeLocation location);
    
    
    /**
     * Get persistent quantitation type
     * @param name Name of type
     * @param create Create if not found
     * @return PersistentQuantitationType
     */
    public PersistentQuantitationType getPersistentQuantitationType(String name, 
    		boolean create);
    
    
    /**
     * Create new persistent quantitation
     * @param value Value
     * @param type Quantitation type
     * @return Persistent quantitation
     */
    public PersistentQuantitation newPersistentQuantitation(float value, 
    		PersistentQuantitationType type);
    
    
    /**
     * Get persistent genome feature type
     * @param name Name of feature type
     * @param create Create if not found
     * @param representsGene Does type represent a gene?
     * @return Persistent genome feature type
     */
    public PersistentGenomeFeatureType getPersistentGenomeFeatureType(String name, 
    		boolean representsGene, boolean create);
    
    
    /**
     * Get persistent genome feature type
     * @param name Name of feature type
     * @param create Create if not found
     * @return Persistent genome feature type
     */
    public PersistentGenomeFeatureType getPersistentGenomeFeatureType(String name, 
    		boolean create);
    
    
    /**
     * Create new persistent genome feature data set
     * @param assembly Genome assembly
     * @param date Date
     * @param type Genome feature type
     * @return Persistent genome feature data set
     */
    public PersistentGenomeFeatureDataSet newPersistentGenomeFeatureDataSet(
    		PersistentGenomeAssembly assembly, Date date, 
			PersistentGenomeFeatureType type);
    
    
    /**
     * Get persistent genome feature data set
     * @param assembly Assembly
     * @param type Genome feature type
     * @return Persistent genome feature data set
     */
    public PersistentGenomeFeatureDataSet getPersistentGenomeFeatureDataSet(
    		PersistentGenomeAssembly assembly, PersistentGenomeFeatureType type);
    
    
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
			PersistentGenomeFeatureDataSet genomeFeatureDataSet);
    
    
    /**
     * Create persistent exon
     * @param start Start
     * @param end End
     * @return Persistent exon
     */
    public PersistentExon newPersistentExon(long start, long end);
    
    
    /**
     * Create new cytoband
     * @param name Name
     * @param start Start
     * @param end End
     * @param stain Stain
     * @return Persistent cytoband
     */
    public PersistentCytoband newPersistentCytoband(String name, long start, 
    		long end, String stain);
    
    
    /**
     * Create persistent cytological map
     * @param chromosome Chromosome
     * @return Persistent cytological map
     */
    public PersistentCytologicalMap newPersistentCytologicalMap(
    		PersistentChromosome chromosome);
    
    
    /**
     * Create persistent cytological map
     * @param chromosome Chromosome
     * @param centromereStart Centromere start
     * @param centromereEnd Centromere end
     * @return Persistent cytological map
     */
    public PersistentCytologicalMap newPersistentCytologicalMap(
    		PersistentChromosome chromosome, long centromereStart, 
			long centromereEnd);
    
    
    /**
     * Create persistent cytological map set
     * @param assembly Genome assembly
     * @param date Date
     * @return Persistent cytological map set
     */
    public PersistentCytologicalMapSet newPersistentCytologicalMapSet(
    		PersistentGenomeAssembly assembly, Date date);
    
    
    /**
     * Get persistent cytological map set
     * @param assembly Genome assembly
     * @return Persistent cytological map set
     */
    public PersistentCytologicalMapSet getPersistentCytologicalMapSet(
    		PersistentGenomeAssembly assembly);
    
    
    /**
     * Create new persistent chromosome bin
     * @param bin Chromosome bin
     * @param value Value
     * @param chromosomeNum Chromosome number
     * @return Persistent chromosome bin
     */
    public PersistentChromosomeBin newPersistentChromosomeBin(int bin, 
    		float value, int chromosomeNum);
    
    
    /**
     * Create persistent binned data
     * @param type Type
     * @return Persistent binned data
     */
    public PersistentBinnedData newPersistentBinnedData(PersistentQuantitationType type);
    
    
    /**
     * Get persistent binned data
     * @param id ID
     * @return Persistent binned data
     */
    public PersistentBinnedData getPersistentBinnedData(Long id);
    
    
    /**
     * Get persistent array
     * @param vendor Vendor
     * @param name Array name
     * @param organism Organism
     * @param create Create if not found
     * @return A persistent array
     */
    public PersistentArray getPersistentArray(String vendor, 
    		String name, PersistentOrganism organism, boolean create);
    
    
    /**
     * Get persistent array
     * @param id ID
     * @return A persistent array
     */
    public PersistentArray getPersistentArray(Long id);
    
    
    /**
     * Get persistent array mapping
     * @param array Array
     * @param assembly Genome assembly
     * @param create Create if not found
     * @return A persistent array mapping
     */
    public PersistentArrayMapping getPersistentArrayMapping(PersistentArray array, 
    		PersistentGenomeAssembly assembly, boolean create);
    
    
    /**
     * Create new persistent array datum
     * @param reporter A reporter
     * @param quantitation A quantitation
     * @return A persistent array datum
     */
    public PersistentArrayDatum newPersistentArrayDatum(
    		PersistentReporter reporter, PersistentQuantitation quantitation);
    
    
    /**
     * Create persistent bioassay data
     * @return Persistent bioassay data
     */
    public PersistentBioAssayData newPersistentBioAssayData();
    
    
    /**
     * Get persistent bioassay data
     * @param id ID
     * @return Persistent bioassay data
     */ 
    public PersistentBioAssayData getPersistentBioAssayData(Long id);
    
    
    /**
     * Create new persistent bioassay
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @return Persistent bioassay
     */
    public PersistentBioAssay newPersistentBioAssay(String name, String description, String databaseName);
    
    
    /**
     * Get bioassay
     * @param id ID
     * @return Persistent bioassay
     */
    public PersistentBioAssay getPersistentBioAssay(Long id);
    
    
    /**
     * Create persistent experiment
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @return Persistent description
     */
    public PersistentExperiment newPersistentExperiment(String name, String description, 
            String databaseName);
    
    
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
            String databaseName, boolean virtual, String userName);
    
    
    /**
     * Create persistent experiment
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is experiment virtual?
     * @return Persistent description
     */
    public PersistentExperiment newPersistentExperiment(String name, String description, 
            String databaseName, boolean virtual);
    
    
    /**
     * Get all persistent experiments
     * @return All persistent experiments
     */
    public PersistentExperiment[] getAllPersistentPublicExperiments();
    
    
    /**
     * Delete all persistent experiments
     *
     */
    public void deleteAllPersistentPublicExperiments();
    
    
    /**
     * Get persistent experiment
     * @param id ID
     * @return Persistent experiment
     */
    public PersistentExperiment getPersistentExperiment(Long id);
    
    
    /**
     * Delete all persistent genome feature data sets
     *
     */
    public void deleteAllPersistentGenomeFeatureDataSets();
    
    
    /**
     * Get all persistent organisms
     * @return Persistent organisms
     */
    public PersistentOrganism[] getAllPersistentOrganisms();
    
    
    /**
     * Get all persistent genome assemblies associated with given organism
     * @param organism An organism
     * @return Persistent genome assemblies
     */
    public PersistentGenomeAssembly[] getAllPersistentGenomeAssemblies(PersistentOrganism organism);
    
    
    /**
     * Get all persistent genome assemblies
     * @return Persistent genome assemblies
     */
    public PersistentGenomeAssembly[] getAllPersistentGenomeAssemblies();
    
    
    /**
     * Get all persistent genome feature data sets associated with given assembly 
     * @param assembly A genome assembly
     * @return Genome feature data sets
     */
    public PersistentGenomeFeatureDataSet[] getAllPersistentGenomeFeatureDataSets(PersistentGenomeAssembly assembly);
    
    
    /**
     * Delete all cytological map sets
     *
     */
    public void deleteAllPersistentCytologicalMapSets();
    
    
    /**
     * Get all cytological map sets
     * @return Cytological map sets
     */
    public PersistentCytologicalMapSet[] getAllPersistentCytologicalMapSets();
    
    
    /**
     * Delete all persistent arrays
     *
     */
    public void deleteAllPersistentArrays();
    
    
    /**
     * Get all persistent array mappings
     * @return All persistent array mappings
     */
    public PersistentArrayMapping[] getAllPersistentArrayMappings();
    
    
    /**
     * Get all persistent array mappings associated with array
     * @param array Array
     * @return All persistent array mappings
     */
    public PersistentArrayMapping[] getAllPersistentArrayMappings(PersistentArray array);
    
    
    /**
     * Create new persistent pipeline step parameter
     * @param paramName Parameter name
     * @param value Parameter value
     * @return Persistent pipeline step parameter
     */
    public PersistentPipelineStepParameter newPersistentPipelineStepParameter(String paramName, String value);
    
    
    /**
     * Create new persistent pipeline step
     * @param className Class name
     * @param step Step number
     * @return Persistent pipeline step
     */
    public PersistentPipelineStep newPersistentPipelineStep(String className, int step);
    
    
    /**
     * Create new persistent pipeline
     * @param name Pipeline name
     * @param userName User name
     * @return Persistent pipeline
     */
    public PersistentPipeline newPersistentPipeline(String name, String userName);
    
    
    
    /**
     * Get persistent pipeline
     * @param name Pipeline name
     * @param userName User name
     * @return Persistent pipeline
     */
    public PersistentPipeline getPersistentPipeline(String name, String userName);
    
    
    /**
     * Get all persistent pipelines associated with given user
     * @param userName User name
     * @return Persistent pipelines
     */
    public PersistentPipeline[] getAllPersistentPipelines(String userName);
    
    
    /**
     * Get all virtual experiments associated with user name
     * @param userName User name
     * @return Virtual experiments
     */
    public PersistentExperiment[] getAllVirtualExperiments(String userName);
    
    
    /**
     * Get virtual experiment
     * @param name Experiment name
     * @param userName User name
     * @return Virtual experiment
     */
    public PersistentExperiment getVirtualExperiment(String name, String userName);
    
    
    /**
     * Get persistent cytological map
     * @param chromosome Chromosome
     * @return Persistent cytological map
     */
    public PersistentCytologicalMap getPersistentCytologicalMap(Chromosome chromosome);
    
    
    /**
     * Get default persistent chromosome
     * @param number Chromosome number
     * @return Persistent chromosome
     */
    public PersistentChromosome getDefaultPersistentChromosome(short number);
    
}
