/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/etl/ArrayEtlManager.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 21:04:54 $

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
package org.rti.webcgh.deprecated.etl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.UserInputException;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.deprecated.array.ArrayMapping;
import org.rti.webcgh.util.CommandLineHelpMessageFormatter;
import org.rti.webcgh.util.CommandLineTableFormatter;
import org.rti.webcgh.util.CommandLineUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 */
public class ArrayEtlManager extends EtlManager {
//    
//    // =========================================================
//    //          Static variables
//    // =========================================================
//    
//    private final Logger LOGGER = Logger.getLogger(ArrayEtlManager.class);
//    
//    
//    // =========================================================
//    //          State variables
//    // =========================================================
//    
//    private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
//    
//    
//    /**
//     * @param persistentDomainObjectMgr The persistentDomainObjectMgr to set.
//     */
//    public void setPersistentDomainObjectMgr(
//            PersistentDomainObjectMgr persistentDomainObjectMgr) {
//        this.persistentDomainObjectMgr = persistentDomainObjectMgr;
//    }
//    
//    
//    // ===========================================================
//    //       Constructors
//    // ===========================================================
//    
//    /**
//     * Constructor
//     */
//    public ArrayEtlManager() {}
//    
//    
//    // =========================================================
//    //          Public methods
//    // =========================================================
//    
//    /**
//     * Load data from a delimited file
//     * @param in A file reader
//     * @param mapping Annotation column mapping
//     * @param arrayName Name of array
//     * @param arrayVendor Array vendor
//     * @param assemblyName Genome assembly name
//     * @param genus Genus
//     * @param species Species
//     * @throws UserInputException
//     */
//    public void load
//    (
//        Reader in, AnnotationColumnMapping mapping, String arrayName, 
//        String arrayVendor, String assemblyName, String genus, String species
//    ) throws UserInputException {
//        
//        PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, true);
//        PersistentGenomeAssembly assembly = this.persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyName, organism, true);
//        PersistentArray array = this.persistentDomainObjectMgr.getPersistentArray(arrayVendor, arrayName, organism, true);
//        PersistentArrayMapping arrayMapping = this.persistentDomainObjectMgr.getPersistentArrayMapping(array, assembly, false);
//        if (arrayMapping != null)
//            throw new UserInputException("System already contains data for this array and genome assembly.  Delete first.");
//        arrayMapping = this.persistentDomainObjectMgr.getPersistentArrayMapping(array, assembly, true);
//        try {
//	        this.loadProbeMappings(in, mapping, array, assembly, arrayMapping);
//        } catch (Exception e) {
//            throw new WebcghSystemException("Error performing ETL of annotations", e);
//        }
//        
//    }
//    
//    
//    /**
//     * Delete all probe mapping records for given array and assembly
//     * @param arrayName Name of array
//     * @param arrayVendor Array vendor
//     * @param assemblyName Name of assembly
//     * @param genus Genus
//     * @param species Species
//     * @throws UserInputException
//     */
//    public void delete
//    (
//        String arrayName, String arrayVendor, String assemblyName, String genus, String species
//    ) throws UserInputException {
//        PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, false);
//        if (organism == null)
//            throw new UserInputException("Cannot find organism");
//        PersistentGenomeAssembly assembly = this.persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyName, organism, false);
//        if (assembly == null)
//            throw new UserInputException("Cannot find genome assembly");
//        PersistentArray array = this.persistentDomainObjectMgr.getPersistentArray(arrayVendor, arrayName, organism, false);
//        if (array == null)
//            throw new UserInputException("Cannot find array");
//        PersistentArrayMapping arrayMapping = this.persistentDomainObjectMgr.getPersistentArrayMapping(array, assembly, false);
//        if (arrayMapping == null)
//            throw new UserInputException("Cannot find reporter mapping data");
//        arrayMapping.delete();
//    }
//    
//    
//    /**
//     * Delte probe set with given id
//     * @param id Probe set id
//     */
//    public void delete(Long id) {
//    	PersistentArray array = this.persistentDomainObjectMgr.getPersistentArray(id);
//    	PersistentArrayMapping[] mappings =
//    		this.persistentDomainObjectMgr.getAllPersistentArrayMappings(array);
//    	for (int i = 0; i < mappings.length; i++)
//    		mappings[i].delete();
//        array.delete();
//    }
//    
//    
//    /**
//     * Delete all probe sets
//     *
//     */
//    public void deleteAll() {
//        this.persistentDomainObjectMgr.deleteAllPersistentArrays();
//    }
//    
//        
//    
//    // ===============================================================
//    //              Main method
//    // ===============================================================
//    
//    /**
//     * Main methods
//     * @param args Command line args
//     */
//    public static void main(String[] args) {
//        try {
//	        CommandLineUtil clUtil = new CommandLineUtil(new String[] {"-load", "-remove", "-h", "-help", "-show"});
//	        Properties props = clUtil.commandLineOptionsToProperties(args);
//	        if (props.containsKey("-h") || props.containsKey("-help"))
//	            printHelpMessageAndExit(0);
//	        
//	        // Instantiate an ETL manager
//	        ApplicationContext ctx = 
//	            new ClassPathXmlApplicationContext(ETL_BEANS_FILE);
//	        ArrayEtlManager etlManager = (ArrayEtlManager)
//	        	ctx.getBean("arrayEtlManager");
//	        
//	        // Add anonymous ETL milestone event listener
//	        etlManager.addEtlMilestoneListener(new EtlMilestoneListener() {
//	        	public void onEtlMilestoneReached(EtlMilestoneEvent evt) {
//	        		System.out.println(evt.getMessage());
//	        	}
//	        });
//	        
//	
//	        if (props.containsKey("-load"))
//	            load(props, etlManager);
//	        else if (props.containsKey("-remove"))
//	            delete(props, etlManager);
//	        else if (props.containsKey("-show"))
//	            show(etlManager);
//	        else {
//	            System.err.println("\nMust specify either '-load,' '-remove,' '-show,' or '-help' option");
//	            printHelpMessageAndExit(1);
//	        }
//        } catch (UserInputException e) {
//            System.err.println("User input exception: " + e.getMessage());
//            printHelpMessageAndExit(1);
//        }
//    }
//    
//    
//    /**
//     * Get all persistent array mappings
//     * @return Persistent array mappings
//     */
//    public PersistentArrayMapping[] getAllPersistentArrayMappings() {
//        return this.persistentDomainObjectMgr.getAllPersistentArrayMappings();
//    }
//    
//    
//    // ===============================================================
//    //             Private methods
//    // ===============================================================
//    
//    private void loadProbeMappings
//    (
//        Reader in, AnnotationColumnMapping mapping, PersistentArray array, PersistentGenomeAssembly assembly,
//        PersistentArrayMapping arrayMapping
//    ) throws IOException {
//        EtlRecordStream stream = new EtlRecordStream(in);
//        int count = 0;
//        int loadedRecs = 0;
//        Date startTime = new Date();
//        int currChromNum = -1;
//        PersistentChromosome currChromosome = null;
//        while (stream.hasMoreRecords()) {
//            
//            // Notify listeners if ETL milestone reached
//        	if (++count % this.recordsBetweenMilestones == 0)
//        		this.milestone(startTime, count);
//        	
//            String[] record = stream.nextRecord();
//            
//            String probeName = record[mapping.getNameCol()];
//            int chromosome = -1;
//            long chromStart = -1, chromEnd = -1;
//            try {
//                chromosome = EtlUtils.parseChromosome(record[mapping.getChromCol()]);
//            } catch (NumberFormatException e) {
//                LOGGER.warn("Unparsable chromosome format '" +
//                    record[mapping.getChromCol()] + "' in record " + count + ".'  Not loading record");
//                continue;
//            }
//            if (currChromNum != chromosome && chromosome > 0) {
//                currChromNum = chromosome;
//                currChromosome = this.persistentDomainObjectMgr.getPersistentChromosome(assembly, (short)currChromNum, true);
//            }
//            
//            
//            try {
//	            chromStart = Long.parseLong(record[mapping.getStartCol()]);
//	            chromEnd = Long.parseLong(record[mapping.getEndCol()]);
//            } catch (NumberFormatException e) {
//                LOGGER.warn("Unparasable chromosome position in record " + count + ": start = '" +
//                    record[mapping.getStartCol()] + "', end = '" +
//                    record[mapping.getEndCol()] + ".'  Not loading record.");
//	            continue;
//            }
//            if (currChromNum > 0 && chromStart >= 0 && chromEnd >= 0) {
//	            long chromPos = (chromStart + chromEnd) / (long)2;
//	            PersistentReporter reporter = this.persistentDomainObjectMgr.getPersistentReporter(probeName, true);
//	            PersistentGenomeLocation location = this.persistentDomainObjectMgr.newPersistentGenomeLocation(currChromosome, chromPos);
//	            PersistentReporterMapping reporterMapping = this.persistentDomainObjectMgr.newPersistentReporterMapping(reporter, location);
//	            reporter.locateInGenome(reporterMapping);
//	            array.add(reporter);
//	            arrayMapping.add(reporterMapping);
//	            loadedRecs++;
//            }
//        }
//        arrayMapping.update();
//        this.milestone(startTime, count);
//        System.out.println(loadedRecs + "/" + count + " records loaded");
//    }
//    
//    
//    private static void printHelpMessageAndExit(int exitCode) {
//        CommandLineHelpMessageFormatter formatter = new CommandLineHelpMessageFormatter(
//            "probe_util", "Imports and deletes probe locations from " +
//            "the embedded database");
//        
//        // Options
//        formatter.addOption("-h", "", "Print this help message");
//        formatter.addOption("-help", "", "Print this help message");
//        formatter.addOption("-load", "", "Load probe locations from delimited file");
//        formatter.addOption("-remove", "", "Remove probe locations");
//        formatter.addOption("-show", "", "Show all probe sets");
//        formatter.addOption("-file", "PATH", "Source file for probe locations");
//        formatter.addOption("-genus", "NAME", "Genus of organism");
//        formatter.addOption("-species", "NAME", "Species of organism");
//        formatter.addOption("-assembly", "NAME", "Name of genome assembly");
//        formatter.addOption("-array", "NAME", "Name of array model");
//        formatter.addOption("-vendor", "NAME", "Name of array vendor");
//        formatter.addOption("-nameCol", "COLUMN", 
//            "Column containing probe names");
//        formatter.addOption("-chromCol", "COLUMN", 
//        	"Column containing chromosome numbers");
//        formatter.addOption("-startCol", "COLUMN", 
//        	"Column containing probe start positions");
//        formatter.addOption("-endCol", "COLUMN", 
//        	"Column containing probe end positions");
//        
//        System.out.println("\n" + formatter.getMessage());
//        System.exit(exitCode);
//    }
//    
//    
//    
//    private static void load(Properties options, ArrayEtlManager etlManager) throws UserInputException {
//    	
//	    // Get command line options
//	    String filePath = getOption(options, "-file");
//	    int nameCol = Integer.parseInt(getOption(options, "-nameCol")) - 1;
//	    int chromCol = Integer.parseInt(getOption(options, "-chromCol")) - 1;
//	    int startCol = Integer.parseInt(getOption(options, "-startCol")) - 1;
//	    int endCol = Integer.parseInt(getOption(options, "-endCol")) - 1;
//	    boolean isGene = options.containsKey("-g");
//	    String genus = getOption(options, "-genus");
//	    String species = getOption(options, "-species");
//	    String assembly = getOption(options, "-assembly");
//	    String arrayName = getOption(options, "-array");
//	    String arrayVendor = options.getProperty("-vendor");
//	    if (arrayVendor == null)
//	    	arrayVendor = "";
//	    
//	    // Create annotation column mapping
//	    AnnotationColumnMapping mapping = 
//	        new AnnotationColumnMapping(nameCol, chromCol, startCol, endCol, -1, -1);
//	            
//        // Load probe locations
//        System.out.println("\nLoading probe locations...");
//        Reader in = null;
//        try {
//            in = new FileReader(filePath);
//        } catch (FileNotFoundException e) {
//            System.err.println("File '" + filePath + "' not found");
//        }
//        etlManager.load(in, mapping, arrayName, arrayVendor, assembly, 
//        	genus, species);
//        System.out.println("\nDone");
//	}
//    
//    
//    private static void delete(Properties options, ArrayEtlManager etlManager) throws UserInputException {
//        
//        // Get command line options
//        String genus = getOption(options, "-genus");
//        String species = getOption(options, "-species");
//        String assembly = getOption(options, "-assembly");
//	    String arrayName = getOption(options, "-array");
//	    String arrayVendor = getOption(options, "-vendor");
//        
//        // Remove probe locations
//        System.out.println("\nRemoving probe locations...");
//        etlManager.delete(arrayName, arrayVendor, assembly, genus, species);
//        System.out.println("\nDone");
//    }
//    
//    
//    private static void show(ArrayEtlManager etlManager) {
//        PersistentArrayMapping[] arrayMappings = etlManager.getAllPersistentArrayMappings();
//        CommandLineTableFormatter formatter = new CommandLineTableFormatter(5);
//        String[] headings = ArrayMapping.printableHeadings();
//        formatter.addColumnHeadings(headings);
//        for (int i = 0; i < arrayMappings.length; i++)
//            formatter.addRow(arrayMappings[i].printableFields());
//        System.out.println("\n" + formatter.getTable() + "\n");
//    }
//        
//    
//    private static String getOption(Properties options, String optName) {
//        String option = options.getProperty(optName);
//        if (option == null) {
//            System.err.println("\n********************************************************");
//            System.err.println("*** Error: You must set '" + optName + "'");
//            System.err.println("********************************************************");
//            printHelpMessageAndExit(1);
//        }
//        return option;
//    }

}
