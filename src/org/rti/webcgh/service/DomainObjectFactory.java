/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/DomainObjectFactory.java,v $
$Revision: 1.2 $
$Date: 2006-05-19 22:30:47 $

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

package org.rti.webcgh.service;

import java.util.Collection;
import java.util.Map;

import org.rti.webcgh.array.Array;
import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayType;
import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.Organism;
import org.rti.webcgh.array.Quantitation;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.array.Reporter;
import org.rti.webcgh.array.ReporterMapping;
import org.rti.webgenome.client.BioAssayDTO;
import org.rti.webgenome.client.BioAssayDatumDTO;
import org.rti.webgenome.client.ExperimentDTO;
import org.rti.webgenome.client.ReporterDTO;

/**
 * Domain object factory
 */
public class DomainObjectFactory {
    
    // ====================================
    //      Constants
    // ====================================    
	
    private final static String CLIENT_DB_NAME = "Client Application";
	
	// ====================================
	//      Attributes
	// ====================================
	
	CountingDomainObjectCache cache = new CountingDomainObjectCache();
	
	
	/**
	 * Get reporter
	 * @param name Name
	 * @return Reporter
	 */
	public Reporter getReporter(String name) {
	    Object key = Reporter.cacheKey(name);
	    Reporter reporter = (Reporter)this.cache.load(key);
	    if (reporter == null) {
	        reporter = new Reporter(name);
	        this.cache.save(reporter);
	    }
	    return reporter;
	}
	
	
	/**
	 * Get chromosome
	 * @param assembly Assembly
	 * @param number Chromosome number
	 * @return Chromosome
	 */
	public Chromosome getChromosome(GenomeAssembly assembly, short number) {
	    Object key = Chromosome.cacheKey(assembly, number);
	    Chromosome chromosome = (Chromosome)this.cache.load(key);
	    if (chromosome == null) {
	        chromosome = new Chromosome(assembly, number);
	        this.cache.save(chromosome);
	    }
	    return chromosome;
	}
	
	
	/**
	 * Get organism
	 * @param genus Genus
	 * @param species Species
	 * @return Organism
	 */
	public Organism getOrganism(String genus, String species) {
	    Object key = Organism.cacheKey(genus, species);
	    Organism organism = (Organism)this.cache.load(key);
	    if (organism == null) {
	        organism = new Organism(genus, species);
	        this.cache.save(organism);
	    }
	    return organism;
	}
    

    public Experiment getExperiment(ExperimentDTO dto, String experimentName) {
        Experiment experiment = new Experiment(experimentName, CLIENT_DB_NAME);
        BioAssayDTO[] bioAssayDTOs = dto.getBioAssays();
        experiment.setOrganism(Organism.UNKNOWN);
        for (int i = 0; i < bioAssayDTOs.length; i++) {
            BioAssay bioAssay = new BioAssay(bioAssayDTOs[i].getName());
            BioAssayType bioAssayType = BioAssayType.CGH;
            experiment.add(bioAssay);
            BioAssayDatumDTO[] bioAssayDatumDTOs = bioAssayDTOs[i].getBioAssayData();
            if (bioAssayDatumDTOs.length > 0)
            	bioAssayType = BioAssayType.getBioAssayType(bioAssayDatumDTOs[0].getQuantitationType());
            bioAssay.setBioAssayType(bioAssayType);
            bioAssay.setOrganism(Organism.UNKNOWN);
            bioAssay.setArray(Array.UNKNOWN);
            for (int j = 0; j < bioAssayDatumDTOs.length; j++) {
                BioAssayDatumDTO baddto = bioAssayDatumDTOs[j];
                ReporterDTO reporterdto = baddto.getReporter();
                Reporter reporter = this.getReporter(reporterdto.getName());
                this.synchronizeReporterMapping(reporter, reporterdto);
                reporter.setDisplayProperties(reporterdto.getAnnotations(), reporterdto.getAssociatedGenes(), reporterdto.isSelected().booleanValue());
                QuantitationType type = this.getQuantitationType(baddto.getQuantitationType());
                Quantitation quantitation = new Quantitation(baddto.getValue().floatValue(), type); 
                ArrayDatum arrayDatum = new ArrayDatum(reporter, quantitation);
                bioAssay.add(arrayDatum);
            }
        }
        return experiment;
    }
    
    private void synchronizeReporterMapping(Reporter reporter, ReporterDTO reporterdto) {
        ReporterMapping mapping = reporter.getReporterMapping();
        if (mapping == null) {
            short chromosomeNumber = Short.parseShort(reporterdto.getChromosome());
            Object chromsomeKey = Chromosome.cacheKey(GenomeAssembly.DUMMY_GENOME_ASSEMBLY, chromosomeNumber); 
            Chromosome chromosome = this.getChromosome(GenomeAssembly.DUMMY_GENOME_ASSEMBLY, chromosomeNumber);
            GenomeLocation genomeLocation = new GenomeLocation(chromosome, reporterdto.getChromosomeLocation().longValue());
            mapping = new ReporterMapping(reporter, genomeLocation);
            reporter.setReporterMapping(mapping);
        }
        else {
            GenomeLocation loc = mapping.getGenomeLocation(); 
            short mappingChromosomeNumber = loc.getChromosome().getNumber();
            short dtoChromosomeNumber = Short.parseShort(reporterdto.getChromosome());
            if (mappingChromosomeNumber != dtoChromosomeNumber) {
                short chromosomeNumber = Short.parseShort(reporterdto.getChromosome());
                Object chromsomeKey = Chromosome.cacheKey(GenomeAssembly.DUMMY_GENOME_ASSEMBLY, chromosomeNumber); 
                Chromosome chromosome = (Chromosome) this.cache.load(chromsomeKey);
                loc.setChromosome(chromosome);
            }
            long mappingChromsomeLocation = loc.getLocation();
            long dtoChromosomeLocation = reporterdto.getChromosomeLocation().longValue();
            if (mappingChromsomeLocation != dtoChromosomeLocation) {
                loc.setLocation(dtoChromosomeLocation);
            }
        }
    }


    private QuantitationType getQuantitationType(String quantitationTypeName) {
        Object key = QuantitationType.cacheKey(quantitationTypeName);
        QuantitationType quantitationType = (QuantitationType) this.cache.load(key);
        if (quantitationType == null) {
            quantitationType = new QuantitationType(quantitationTypeName);
            this.cache.save(quantitationType);
        }
        return quantitationType;
    }


    /**
     * Get BioAssay
     * @param name A name
     * @param description Description
     * @param databaseName Database
     * @return BioAssay
     */
    private BioAssay getBioAssay(String name, String description, String databaseName) {
        Object key = BioAssay.cacheKey(name, databaseName);
        BioAssay bioAssay = (BioAssay) this.cache.load(key);
        if (bioAssay == null) {
            bioAssay = new BioAssay(name, description, databaseName);
            this.cache.save(bioAssay);
        }
        return bioAssay;
    }
    
    /**
     * Get ArrayDatum
     * @param reporter A reporter
     * @param quantitation A quantitation
     * @return ArrayDatum
     */
    public ArrayDatum getArrayDatum(Reporter reporter, Quantitation quantitation) {
        Object key = ArrayDatum.cacheKey(reporter, quantitation);
        ArrayDatum arrayDatum = (ArrayDatum) this.cache.load(key);
        if (arrayDatum == null) {
            arrayDatum = new ArrayDatum(reporter, quantitation);
            this.cache.save(arrayDatum);
        }
        return arrayDatum;
    }
    
    /**
     * Get ArrayMapping
     * @param reporter A reporter
     * @param genomeLocation Genome location
     * @return ArrayMapping
     */
    public ReporterMapping getReporterMapping(Reporter reporter,
            GenomeLocation genomeLocation) {
        Object key = ReporterMapping.cacheKey(reporter);
        ReporterMapping reporterMapping = (ReporterMapping) this.cache.load(key);
        if (reporterMapping == null) {
            reporterMapping = new ReporterMapping(reporter, genomeLocation);
            this.cache.save(reporterMapping);
        }
        return reporterMapping;
    }
    
    
   
}
