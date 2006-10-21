/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/etl/CytobandEtlManager.java,v $
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
import org.rti.webcgh.deprecated.array.CytologicalMapSet;
import org.rti.webcgh.util.CommandLineHelpMessageFormatter;
import org.rti.webcgh.util.CommandLineTableFormatter;
import org.rti.webcgh.util.CommandLineUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Manages cytoband ETL process
 */
public class CytobandEtlManager extends EtlManager {
//    
//    
//    // =============================================
//    //        Static variables
//    // =============================================
//    
//    private static final Logger LOGGER = Logger.getLogger(CytobandEtlManager.class);
//
//	// =============================================
//	//      Attributes with accessors and mutators
//	// =============================================
//	
//	private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
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
//	// ==============================================
//	//          Constructors
//	// ==============================================
//	
//	
//	/**
//	 * Constructor
//	 */
//	public CytobandEtlManager() {}
//	
//	
//	// =====================================================
//	//          Public methods
//	// =====================================================
//	
//	/**
//	 * Load cytobands from a file
//	 * @param reader Reader to delimited file containing cytoband data
//	 * @param mapping Maps columns in file to table columns
//	 * @param assemblyName Name of corresponding genome assembly
//	 * @param genus Genus
//	 * @param species
//	 */
//	public void load
//	(
//		Reader reader, AnnotationColumnMapping mapping, String assemblyName,
//		String genus, String species
//	) {
//		PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, true);
//		PersistentGenomeAssembly assembly = this.persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyName, organism, true);
//		try {
//			loadCytobands(reader, mapping, assembly);
//		} catch (Exception e) {
//			throw new WebcghSystemException("Error performing ETL", e);
//		}
//	}
//	
//	
//	/**
//	 * Delete all cytobands from given assembly
//	 * @param assemblyName Assembly name
//	 * @param genus Genus
//	 * @param species Species
//	 * @throws UserInputException
//	 */
//	public void delete(String assemblyName, String genus, String species) throws UserInputException {
//		PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, false);
//		if (organism == null)
//		    throw new UserInputException("Cannot find organism");
//		PersistentGenomeAssembly assembly = this.persistentDomainObjectMgr.getPersistentGenomeAssembly(assemblyName, organism, false);
//		if (assembly == null)
//		    throw new UserInputException("Cannot find genome assembly");
//		this.delete(assembly);
//	}
//	
//	
//	/**
//	 * Remove all cytobands mapped to given assembly
//	 * @param assembly Genome assembly
//	 */
//	public void delete(PersistentGenomeAssembly assembly) {
//	    PersistentCytologicalMapSet mapSet = this.persistentDomainObjectMgr.getPersistentCytologicalMapSet(assembly);
//	    mapSet.delete();
//	}
//	
//	
//	/**
//	 * Delete all cytobands
//	 *
//	 */
//	public void deleteAll() {
//	    this.persistentDomainObjectMgr.deleteAllPersistentCytologicalMapSets();
//	}
//	
//	
//	/**
//	 * Get all persistent cytological map sets
//	 * @return Persistent cytological map sets
//	 */
//	public PersistentCytologicalMapSet[] getAllPersistentCytologicalMapSets() {
//	    return this.persistentDomainObjectMgr.getAllPersistentCytologicalMapSets();
//	}
//	
//	// ====================================================
//	//            Main methods
//	// ====================================================
//	
//	/**
//	 * Main methods
//	 * @param args Command line args
//	 */
//	public static void main(String[] args) {
//	    try {
//	        CommandLineUtil clUtil = new CommandLineUtil(new String[] {"-load", "-remove", "-h", "-show", "-help"});
//	        Properties props = clUtil.commandLineOptionsToProperties(args);
//	        if (props.containsKey("-h") || props.containsKey("-help"))
//	            printHelpMessageAndExit(0);
//	        
//	        // Instantiate a CytobandEtlManager
//	        ClassPathXmlApplicationContext ctx = 
//	            new ClassPathXmlApplicationContext(ETL_BEANS_FILE);
//	        CytobandEtlManager etlManager = (CytobandEtlManager)
//	        	ctx.getBean("cytobandEtlManager");
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
//	    } catch (UserInputException e) {
//	        System.err.println("User input error: " + e.getMessage());
//	        printHelpMessageAndExit(1);
//	    }
//	}
//	
//	
//	// ==========================================
//	//        Private methods
//	// ==========================================
//	
//	
//	private static void show(CytobandEtlManager etlManager) {
//	    PersistentCytologicalMapSet[] mapSets = etlManager.getAllPersistentCytologicalMapSets();
//	    String[] headings = CytologicalMapSet.printableHeadings();
//        CommandLineTableFormatter formatter = new CommandLineTableFormatter(headings.length);
//        formatter.addColumnHeadings(headings);
//        for (int i = 0; i < mapSets.length; i++) {
//            CytologicalMapSet mapSet = mapSets[i];
//            formatter.addRow(mapSet.printableFields());
//        }
//        System.out.println("\n" + formatter.getTable() + "\n");
//	}
//
//	
//	private void loadCytobands
//	(
//	    Reader in, AnnotationColumnMapping mapping, PersistentGenomeAssembly assembly
//	) throws IOException {
//	    PersistentCytologicalMapSet mapSet = this.persistentDomainObjectMgr.newPersistentCytologicalMapSet(assembly, new Date());
//		EtlRecordStream stream = new EtlRecordStream(in);
//        int count = 0;
//        Date startTime = new Date();
//        int currChromNum = -1;
//        PersistentChromosome currChrom = null;
//        PersistentCytologicalMap currMap = null;
//		while (stream.hasMoreRecords()) {
//		    
//		    // Notify listeners if ETL milestone reached
//        	if (++count % this.recordsBetweenMilestones == 0)
//        		this.milestone(startTime, count);
//        	
//        	// Get fields
//			String[] record = stream.nextRecord();
//			String name = record[mapping.getNameCol()];
//			String stain = record[mapping.getStainCol()];
//			int chromosome = EtlUtils.parseChromosome(record[mapping.getChromCol()]);
//			int chromStart = Integer.parseInt(record[mapping.getStartCol()]);
//			int chromEnd = Integer.parseInt(record[mapping.getEndCol()]);
//			
//			// Get chromosome
//			if (chromosome != currChromNum) {
//			    currChromNum = chromosome;
//			    currChrom = this.persistentDomainObjectMgr.getPersistentChromosome(assembly, (short)currChromNum, true);
//			    currMap = this.persistentDomainObjectMgr.newPersistentCytologicalMap(currChrom);
//			    mapSet.add(currMap);
//			}
//			PersistentCytoband cytoband = this.persistentDomainObjectMgr.newPersistentCytoband(name, chromStart, chromEnd, stain);
//			currMap.addCytoband(cytoband);
//		}
//		mapSet.update();
//		this.milestone(startTime, count);
//	}
//	
//	
//    private static void printHelpMessageAndExit(int exitCode) {
//        CommandLineHelpMessageFormatter formatter = new CommandLineHelpMessageFormatter(
//            "cytoband_util", "Imports and deletes cytobands from " +
//            "the embedded database");
//        
//        // Options
//        formatter.addOption("-h", "", "Print this help message");
//        formatter.addOption("-help", "", "Print this help message");
//        formatter.addOption("-load", "", "Load cytobands from delimited file");
//        formatter.addOption("-remove", "", "Remove cytobands");
//        formatter.addOption("-show", "", "Show organisms and assemblies with cytoband data");
//        formatter.addOption("-file", "PATH", "Source file for cytobands");
//        formatter.addOption("-genus", "NAME", "Genus of organism");
//        formatter.addOption("-species", "NAME", "Species of organism");
//        formatter.addOption("-assembly", "NAME", "Name of genome assembly");
//        formatter.addOption("-nameCol", "COLUMN", 
//            "Column containing probe names");
//        formatter.addOption("-chromCol", "COLUMN", 
//        	"Column containing chromosome numbers");
//        formatter.addOption("-startCol", "COLUMN", 
//        	"Column containing probe start positions");
//        formatter.addOption("-endCol", "COLUMN", 
//        	"Column containing probe end positions");
//        formatter.addOption("-stainCol", "COLUMN", 
//    		"Column containing stain intensities");
//        
//        System.out.println("\n" + formatter.getMessage());
//        System.exit(exitCode);
//    }
//    
//    
//    private static void load(Properties options, CytobandEtlManager etlManager) {
//    	
//	    // Get command line options
//	    String filePath = getOption(options, "-file");
//	    int nameCol = Integer.parseInt(getOption(options, "-nameCol")) - 1;
//	    int chromCol = Integer.parseInt(getOption(options, "-chromCol")) - 1;
//	    int startCol = Integer.parseInt(getOption(options, "-startCol")) - 1;
//	    int endCol = Integer.parseInt(getOption(options, "-endCol")) - 1;
//	    int stainCol = Integer.parseInt(getOption(options, "-stainCol")) - 1;
//	    boolean isGene = options.containsKey("-g");
//	    String genus = getOption(options, "-genus");
//	    String species = getOption(options, "-species");
//	    String assembly = getOption(options, "-assembly");
//	    
//	    // Create annotation column mapping
//	    AnnotationColumnMapping mapping = 
//	        new AnnotationColumnMapping(nameCol, chromCol, startCol, endCol, -1, -1);
//	    mapping.setStainCol(stainCol);
//	    
//        // Perform load
//        Reader reader = null;
//        try {
//            reader = new FileReader(filePath);
//        } catch (FileNotFoundException e) {
//            System.err.println("File '" + filePath + "' not found");
//        }
//        System.out.println("\nLoading cytobands...");
//        etlManager.load(reader, mapping, assembly, genus, species);
//        System.out.println("\nDone");
//    }
//    
//    
//    private static void delete(Properties options, CytobandEtlManager etlManager) throws UserInputException {
//        
//        // Get command line options
//        String genus = getOption(options, "-genus");
//        String species = getOption(options, "-species");
//        String assembly = getOption(options, "-assembly");
//        
//        // Remove cytobands
//        System.out.println("\nRemoving cytobands...");
//        etlManager.delete(assembly, genus, species);
//        System.out.println("\nDone");
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
//	
}
