/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/etl/AnnotationEtlManager.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.etl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.Organism;
import org.rti.webcgh.array.persistent.PersistentChromosome;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.array.persistent.PersistentExon;
import org.rti.webcgh.array.persistent.PersistentGenomeAssembly;
import org.rti.webcgh.array.persistent.PersistentGenomeFeature;
import org.rti.webcgh.array.persistent.PersistentGenomeFeatureDataSet;
import org.rti.webcgh.array.persistent.PersistentGenomeFeatureType;
import org.rti.webcgh.array.persistent.PersistentOrganism;
import org.rti.webcgh.core.UserInputException;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.util.CommandLineHelpMessageFormatter;
import org.rti.webcgh.util.CommandLineTableFormatter;
import org.rti.webcgh.util.CommandLineUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Manages ETL of annotation into the embedded database
 */
public class AnnotationEtlManager extends EtlManager {
	
	// =================================================
	//        Static variables
	// =================================================
	
	private static final Logger LOGGER = Logger.getLogger(AnnotationEtlManager.class);
        
    
    // ====================================================
    //     Attributes with accessors and mutators
    // ====================================================
    
	private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
	
    
    
    /**
     * @return Returns the persistentDomainObjectMgr.
     */
    public PersistentDomainObjectMgr getPersistentDomainObjectMgr() {
        return persistentDomainObjectMgr;
    }
    
    
    /**
     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
     */
    public void setPersistentDomainObjectMgr(
            PersistentDomainObjectMgr persistentDomainObjectMgr) {
        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
    }
    
    
    // ===================================================
    //             Constructors
    // ===================================================
    
    
    /**
     * Constructor
     */
    public AnnotationEtlManager() {}
    
    
    // ====================================================
    //            Public methods
    // ====================================================
    
    /**
     * Load annotations
     * @param reader Reader to file containing annotations
     * @param mapping Mapping of columns in data file to data elements
     * @param featureTypeName Name of annotation feature type
     * @param genus Genus
     * @param species Species
     * @param assemblyName Name of genome assembly
     * @param isAGene Is feature type a gene?
     * @throws IllegalArgumentException if any of the arguments are null or if
     * <code>path</code> is not a valid file
     * @throws WebcghSystemException if anything goes wrong during ETL
     */
    public void load
    (
        Reader reader, AnnotationColumnMapping mapping, String featureTypeName, String genus, 
        String species, String assemblyName, boolean isAGene
    ) {
       
        PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, true);
        PersistentGenomeFeatureType featureType = this.persistentDomainObjectMgr.getPersistentGenomeFeatureType(featureTypeName, isAGene, true);
        PersistentGenomeAssembly assembly = this.persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyName, organism, true);
        PersistentGenomeFeatureDataSet genomeFeatureDataSet = this.persistentDomainObjectMgr.newPersistentGenomeFeatureDataSet(assembly, new Date(), featureType);
        try {
	        this.loadFeatures(reader, mapping, featureType, assembly, genomeFeatureDataSet);
        } catch (Exception e) {
            throw new WebcghSystemException("Error performing ETL of annotations", e);
        }
    }
    
    
    /**
     * Delete annotation
     * @param featureTypeName Annotation feature type
     * @param genus Genus
     * @param species Species
     * @param assemblyName Assembly name
     * @throws IllegalArgumentException if any of the parameters or null or if
     * organism (genus, species), feature type (featureType) or
     * genome (assemblyName) database records do not exist
     * @throws UserInputException
     */
    public void delete(String featureTypeName, String genus, String species, String assemblyName) throws UserInputException {
        if (featureTypeName == null || genus == null || species == null || assemblyName == null)
            throw new IllegalArgumentException("One or more arguments are null");
        PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, false);
        if (organism == null)
            throw new UserInputException("Cannot find organism");
        PersistentGenomeAssembly assembly = this.persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyName, organism, false);
        if (assembly == null)
            throw new UserInputException("Cannot find assembly");
        this.delete(featureTypeName, assembly);
    }
    
    
    /**
     * Remove all features
     *
     */
    public void deleteAll() {
        this.persistentDomainObjectMgr.deleteAllPersistentGenomeFeatureDataSets();
    }
    
    
    /**
     * Delete all feature of given type from given assembly
     * @param featureType Feature type name
     * @param genomeAssembly Genome assembly
     */
    public void delete(String featureType, PersistentGenomeAssembly genomeAssembly) {
        PersistentGenomeFeatureType featType = 
            this.persistentDomainObjectMgr.getPersistentGenomeFeatureType(featureType, false);
        if (featType == null)
            throw new IllegalArgumentException("Cannot find feature type '" + featureType + "'");
        PersistentGenomeFeatureDataSet dataSet = this.persistentDomainObjectMgr.getPersistentGenomeFeatureDataSet(genomeAssembly, featType);
        dataSet.delete();
    }
    
	
	
    // ============================================================
    //           Main method
    // ============================================================
    
    
    /**
     * Main methods
     * @param args Command line args
     */
    public static void main(String[] args) {
        try {
	        CommandLineUtil clUtil = new CommandLineUtil(new String[] {"-load", "-remove", "-h", "-help", "-g", "-show"});
	        Properties props = clUtil.commandLineOptionsToProperties(args);
	        if (props.containsKey("-h") || props.containsKey("-help"))
	            printHelpMessageAndExit(0);
	        
	        // Instantiate an AnnotationEtlManager
	        ClassPathXmlApplicationContext ctx = 
	            new ClassPathXmlApplicationContext(ETL_BEANS_FILE);
	        AnnotationEtlManager etlManager = (AnnotationEtlManager)
				ctx.getBean("annotationEtlManager");
	        
	        // Add anonymous ETL milestone event listener
	        etlManager.addEtlMilestoneListener(new EtlMilestoneListener() {
	        	public void onEtlMilestoneReached(EtlMilestoneEvent evt) {
	        		System.out.println(evt.getMessage());
	        	}
	        });
	        
	        if (props.containsKey("-load"))
	            load(props, etlManager);
	        else if (props.containsKey("-remove"))
	            delete(props, etlManager);
	        else if (props.containsKey("-show"))
	            show(etlManager);
	        else {
	            System.err.println("\nMust specify either '-load,' '-remove,' 'show,' or '-help' option");
	            printHelpMessageAndExit(1);
	        }
        } catch (UserInputException e) {
            System.err.println("\nUser input error: " + e.getMessage());
            printHelpMessageAndExit(1);
        }
    }
    
    
    // =============================================================
    //              Private methods
    // =============================================================
    
    
    private static void show(AnnotationEtlManager annotationEtlManager) {
        CommandLineTableFormatter formatter = new CommandLineTableFormatter(4);
        formatter.addRow(new String[] {"GENUS", "SPECIES", "ASSEMBLY", "ANNOTATION TYPE"});
        formatter.addRow(new String[] {"-----", "-------", "--------", "---------------"});
        AnnotationTypeAssemblyPair[] pairs = annotationEtlManager.getAnnotationTypeAssemblyPairs();
        for (int i = 0; i < pairs.length; i++) {
        	AnnotationTypeAssemblyPair pair = pairs[i];
        	GenomeAssembly assembly = pair.getGenomeAssembly();
        	Organism organism = assembly.getOrganism();
        	formatter.addRow(new String[] {organism.getGenus(), organism.getSpecies(),
        		assembly.getName(), pair.getAnnotationType()});
        }
        System.out.println("\n" + formatter.getTable() + "\n");
    }
    
    
    /**
     * Get list of what annotation types are available for each assembly
     * @return Annotation types available for each assembly
     */
    public AnnotationTypeAssemblyPair[] getAnnotationTypeAssemblyPairs() {
    	Collection pairsCol = new ArrayList();

        // Get all organisms
        PersistentOrganism[] organisms = this.persistentDomainObjectMgr.getAllPersistentOrganisms();
        
        // Iterator over organisms
        for (int i = 0; i < organisms.length; i++) {
            PersistentOrganism organism = organisms[i];
            PersistentGenomeAssembly[] assemblies = 
                this.persistentDomainObjectMgr.getAllPersistentGenomeAssemblies(organism);
            
            // Iterate over genome assemblies
            for (int j = 0; j < assemblies.length; j++) {
                PersistentGenomeAssembly assembly = assemblies[j];
                PersistentGenomeFeatureDataSet[] dataSets = 
                    this.persistentDomainObjectMgr.getAllPersistentGenomeFeatureDataSets(assembly);
                
                // Iterate over annotation types
                for (int k = 0; k < dataSets.length; k++) {
                    String annotationTypeName = dataSets[k].getGenomeFeatureType().getName();
                    AnnotationTypeAssemblyPair pair = new AnnotationTypeAssemblyPair(
                    	annotationTypeName, assembly);
                    pairsCol.add(pair);
                }
            }
        }
        
        AnnotationTypeAssemblyPair[] pairs = new AnnotationTypeAssemblyPair[0];
        pairs = (AnnotationTypeAssemblyPair[])pairsCol.toArray(pairs);
        return pairs;
    }
    
    
    private void loadFeatures
    (
        Reader in, AnnotationColumnMapping mapping, PersistentGenomeFeatureType type, PersistentGenomeAssembly assembly,
        PersistentGenomeFeatureDataSet dataSet
    ) throws IOException {
        EtlRecordStream stream = new EtlRecordStream(in);
        int count = 0;
        Date startTime = new Date();
        int currChromNum = -1;
        PersistentChromosome currChrom = null;
        while (stream.hasMoreRecords()) {
        	
        	// Notify listeners if ETL milestone reached
        	if (++count % this.recordsBetweenMilestones == 0)
        		this.milestone(startTime, count);
        	
        	// Load next feature
            String[] record = stream.nextRecord();
            String name = record[mapping.getNameCol()];
            int chromosome = -1;
            try {
            	chromosome = EtlUtils.parseChromosome(record[mapping.getChromCol()]);
            } catch (NumberFormatException e) {
            	LOGGER.warn("Unparsable chromosome format '" + 
            		record[mapping.getChromCol()] + ".'  Record not loaded");
            }
            if (chromosome != currChromNum) {
                currChromNum = chromosome;
                currChrom = this.persistentDomainObjectMgr.getPersistentChromosome(assembly, (short)currChromNum, true);
            }
            long chromStart = Long.parseLong(record[mapping.getStartCol()]);
            long chromEnd = Long.parseLong(record[mapping.getEndCol()]);
            PersistentGenomeFeature feat = this.persistentDomainObjectMgr.newPersistentGenomeFeature(name, chromStart, chromEnd, currChrom, dataSet);
            if (type.isRepresentsGene()) {
                long[] exonStarts = CollectionUtils.splitIntoLongs(record[mapping.getExonStartsCol()], ",");
                long[] exonEnds = CollectionUtils.splitIntoLongs(record[mapping.getExonEndsCol()], ",");
                for (int i = 0; i < exonStarts.length && i < exonEnds.length; i++) {
                    PersistentExon exon = 
                        this.persistentDomainObjectMgr.newPersistentExon(exonStarts[i], exonEnds[i]);
                    feat.addExon(exon);
                }
            }
            feat.update();
        }
        this.milestone(startTime, count);
    }
    
    
    
    private static void printHelpMessageAndExit(int exitCode) {
        CommandLineHelpMessageFormatter formatter = new CommandLineHelpMessageFormatter(
            "annotation_util", "Imports and deletes annotated genome features from " +
            "the embedded database");
        
        // Options
        formatter.addOption("-h", "", "Print this help message");
        formatter.addOption("-help", "", "Print this help message");
        formatter.addOption("-load", "", "Load annotation from delimited file");
        formatter.addOption("-remove", "", "Remove annotation");
        formatter.addOption("-show", "", "Show annotation types");
        formatter.addOption("-file", "PATH", "Source file for annotations");
        formatter.addOption("-g", "", "Annotatated features are genes");
        formatter.addOption("-nameCol", "COLUMN", 
            "Column containing feature names");
        formatter.addOption("-chromCol", "COLUMN", 
        	"Column containing chromosome numbers");
        formatter.addOption("-startCol", "COLUMN", 
        	"Column containing feature start positions");
        formatter.addOption("-endCol", "COLUMN", 
        	"Column containing feature end positions");
        formatter.addOption("-exonStartCol", "COLUMN", 
    		"Column containing exon start positions");
        formatter.addOption("-exonEndCol", "COLUMN", 
			"Column containing exon end positions");
        formatter.addOption("-type", "NAME", "Name of annotation feature type");
        formatter.addOption("-genus", "NAME", "Genus of organism");
        formatter.addOption("-species", "NAME", "Species of organism");
        formatter.addOption("-assembly", "NAME", "Name of genome assembly");
        
        System.out.println("\n" + formatter.getMessage());
        System.exit(exitCode);
    }
    
    
    private static String getOption(Properties options, String optName) {
        String option = options.getProperty(optName);
        if (option == null) {
            System.err.println("\n********************************************************");
            System.err.println("*** Error: You must set '" + optName + "'");
            System.err.println("********************************************************");
            printHelpMessageAndExit(1);
        }
        return option;
    }
    
    
    
    private static void load(Properties options, AnnotationEtlManager etlManager) {
        
        // Get command line options
        String filePath = getOption(options, "-file");
        int nameCol = Integer.parseInt(getOption(options, "-nameCol")) - 1;
        int chromCol = Integer.parseInt(getOption(options, "-chromCol")) - 1;
        int startCol = Integer.parseInt(getOption(options, "-startCol")) - 1;
        int endCol = Integer.parseInt(getOption(options, "-endCol")) - 1;
        int exonStartCol = -1;
        int exonEndCol = -1;
        boolean isGene = options.containsKey("-g");
        if (isGene) {
            exonStartCol = Integer.parseInt(getOption(options, "-exonStartCol")) - 1;
            exonEndCol = Integer.parseInt(getOption(options, "-exonEndCol")) - 1;
        }
        String type = getOption(options, "-type");
        String genus = getOption(options, "-genus");
        String species = getOption(options, "-species");
        String assembly = getOption(options, "-assembly");
        
        // Create annotation column mapping
        AnnotationColumnMapping mapping = 
            new AnnotationColumnMapping(nameCol, chromCol, startCol, endCol, exonStartCol, exonEndCol);
        
        // Get reader
        File file = new File(filePath);
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found");
        }
        if (! file.isFile())
            throw new IllegalArgumentException("Invalid file");

        
        // Perform loading
        System.out.println("\nLoading features...");
        etlManager.load(reader, mapping, type, genus, species, assembly, isGene);
        System.out.println("\nDone");
    }
    
    
    private static void delete(Properties options, AnnotationEtlManager etlManager) throws UserInputException {
        
        // Get command line options
        String type = getOption(options, "-type");
        String genus = getOption(options, "-genus");
        String species = getOption(options, "-species");
        String assembly = getOption(options, "-assembly");
        
        // Perform deletion
        System.out.println("\nDeleting features...");
        etlManager.delete(type, genus, species, assembly);
        System.out.println("\nDone");
    }
    
    


}
