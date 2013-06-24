/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/etl/EsiEtlManager.java,v $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.ExtensionFilenameFilter;
import org.rti.webcgh.deprecated.array.Experiment;
import org.rti.webcgh.util.CommandLineHelpMessageFormatter;
import org.rti.webcgh.util.CommandLineTableFormatter;
import org.rti.webcgh.util.CommandLineUtil;
import org.rti.webcgh.util.SystemUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Managing class for ETL of SKY/M-FISH&CGH data from '.esi' files.
 */
public class EsiEtlManager extends EtlManager implements EsiParserEventHandler {
//	
//	// ===========================================
//	//    Static variables
//	// ===========================================
//	
//	private static final Logger LOGGER = Logger.getLogger(EsiEtlManager.class);
//	private static final String DEF_QUANTITATION_TYPE_NAME = "Ratio";
//	
//	
//	// =================================================
//	//       Attributes with accessors and mutators
//	// =================================================
//	
//	private PersistentDomainObjectMgr persistentDomainObjectMgr = null;
//	private PersistentExperiment experiment = null;
//	private PersistentBioAssay binBioAssay = null;
//	private PersistentBinnedData binnedData = null;
//	private PersistentQuantitationType quantitationType = null;
//	
//	private SkyCaseElement skyCaseElement = null;
//	private Properties cghMappingsProperties = null;
//	private String dbName = null;
//	private int currChromosomeNum = -1;
//	
//	boolean inCghSection = false;
//	
//	
//    /**
//     * @return Returns the dbName.
//     */
//    public String getDbName() {
//        return dbName;
//    }
//    
//    
//    /**
//     * @param dbName The dbName to set.
//     */
//    public void setDbName(String dbName) {
//        this.dbName = dbName;
//    }
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
//	//      Constructors
//	// ==============================================
//	
//	/**
//	 * Constructor
//	 */
//	public EsiEtlManager() {
//		this.cghMappingsProperties = SystemUtils.loadProperties("conf/cgh-mappings.properties");
//	}
//	
//	
//	/**
//	 * Main class
//	 * @param args Command line arguments
//	 */
//	public static void main(String[] args) {
//		if (args.length < 1)
//			printHelpAndExit(1);
//		CommandLineUtil clu = 
//			new CommandLineUtil(new String[]{"-h", "-load", "-show", "-delete"});
//		Properties props = clu.commandLineOptionsToProperties(args);
//		if (props.getProperty("-h") != null)
//			printHelpAndExit(0);
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(ETL_BEANS_FILE);
//		EsiEtlManager etlManager = (EsiEtlManager)ctx.getBean("esiEtlManager");
//		etlManager.addEtlMilestoneListener(new EtlMilestoneListener() {
//		    public void onEtlMilestoneReached(EtlMilestoneEvent evt) {
//		        System.out.println(evt.getMessage());
//		    }
//		});
//
//		try {
//			if (props.getProperty("-show") != null)
//				show(etlManager);
//			else if (props.getProperty("-load") != null) {
//				if (props.getProperty("-file") != null)
//					loadFile(props, etlManager);
//				else if (props.getProperty("-dir") != null)
//					loadDir(props, etlManager);
//				else {
//					String msg = "You must specify either the '-file' or " +
//						"the '-dir' option.";
//					printErrorMsg(msg);
//					printHelpAndExit(1);
//				}
//			} else if (props.getProperty("-delete") != null)
//				delete(etlManager);
//			else
//				printHelpAndExit(1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	// =============================================
//	//      Public methods
//	// =============================================
//	
//	/**
//	 * Load data from given file path
//	 * @param filePath Path to data file
//	 * @param contact Contact
//	 * @param genus Genus
//	 * @param species Species
//	 * @throws EsiParseException if a parse error occurs
//	 */
//	public void load(String filePath, String contact, String genus, String species)
//		throws EsiParseException {
//	    try {
//            this.load(new FileInputStream(filePath), contact, genus, species);
//        } catch (FileNotFoundException e) {
//            throw new EsiParseException("Could not find file", e);
//        }
//	}
//	
//	
//	/**
//	 * Load data from stream
//	 * @param in A stream
//	 * @param contact Contact
//	 * @param genus Genus
//	 * @param species Species
//	 * @throws EsiParseException if a parse error occurs
//	 */
//	public void load(InputStream in, String contact, String genus, String species) 
//		throws EsiParseException {
//		PersistentOrganism organism = this.persistentDomainObjectMgr.getPersistentOrganism(genus, species, true);
//		String name = "From " + contact;
//		String description = name;
//		this.experiment = this.persistentDomainObjectMgr.newPersistentExperiment(name, description, this.dbName);
//		this.experiment.setOrganism(organism);
//		InputStreamReader reader = new InputStreamReader(in);
//		EsiParser parser = new EsiParser();
//		parser.parse(reader, this);
//		experiment.setDatabaseName(this.dbName);
//		experiment.update();
//	}
//	
//	
//	/**
//	 * Get all array experiment meta data
//	 * @return All arrary experiment meta data
//	 */
//	public PersistentExperiment[] getAllPersistentExperiments() {
//		return this.persistentDomainObjectMgr.getAllPersistentPublicExperiments();
//	}
//	
//	
//	/**
//	 * Delete all data
//	 *
//	 */
//	public void deleteAll() {
//		this.persistentDomainObjectMgr.deleteAllPersistentPublicExperiments();
//	}
//	
//	
//	/**
//	 * Delete given experiment
//	 * @param id Experiment ID
//	 */
//	public void delete(String id) {
//	    Long longId = new Long(id);
//	    PersistentExperiment exp = this.persistentDomainObjectMgr.getPersistentExperiment(longId);
//	    exp.delete();
//	}
//	
//	
//	// =============================================
//	//   Methods in EsiParserEventHandler interface
//	// =============================================
//	
//	/**
//	 * Callback method invoked by parser upon encountering a new
//	 * data element
//	 * @param element Data element
//	 * @throws EsiParseException if parsing error occurs
//	 */
	public void onNextElement(EsiDataElement element) throws EsiParseException {
//		if (element instanceof SkyDataElement) {
//			SkyDataElement el = (SkyDataElement)element;
//			this.onNextSkyDataElement(el);
//		} else if (element instanceof SkyCaseElement) {
//		    SkyCaseElement el = (SkyCaseElement)element;
//		    this.onNextSkyCaseElement(el);
//		} else if (element instanceof CGHSampleElement) {
//			CGHSampleElement el = (CGHSampleElement)element;
//			this.onNextCGHSampleElement(el);
//		} else if (element instanceof CGHBinElement) {
//		    CGHBinElement el = (CGHBinElement)element;
//		    this.onNextCGHBinElement(el);
//		} else if (element instanceof CGHBinFragElement) {
//			CGHBinFragElement el = (CGHBinFragElement)element;
//			this.onNextCGHBinFragElement(el);
//		} else if (element instanceof CGHFragElement) {
//		    CGHFragElement el = (CGHFragElement)element;
//		    this.onNextCGHFragElement(el);
//		}
	}
//	
//	
//	/**
//	 * Callback method invoked by parser on encountering
//	 * end of document
//	 *
//	 */
	public void onEndOfDocument() {
//	    if (this.binBioAssay != null)
//	        this.saveBioAssay();
//	    this.saveExperiment();
	}
//	
//	
//	
//	// =================================================
//	//        Private methods
//	// =================================================
//	
//	
//	private static void printHelpAndExit(int exitCode) {
//		String command = "sky_util";
//		String usage = "Application for managing SKY/M-FISH&CGH data";
//		CommandLineHelpMessageFormatter formatter =
//			new CommandLineHelpMessageFormatter(command, usage);
//		formatter.addOption("-h", "", "Print this help message");
//		formatter.addOption("-load", "", "Load data");
//		formatter.addOption("-show", "", "Show SKY/M-FISH&CGH data sets");
//		formatter.addOption("-delete", "", "Delete all SKY/M-FISH&CGH data");
//		formatter.addOption("-file", "PATH_TO_FILE", "Data file");
//		formatter.addOption("-dir", "PATH_TO_DIRECTORY", "Directory");
//		formatter.addOption("-contact", "NAME", "Individual associated with data");
//		formatter.addOption("-genus", "GENUS", "Genus of associated organism");
//		formatter.addOption("-species", "SPECIES", "Species of associated organism");
//		System.out.println("\n" + formatter.getMessage());
//		System.exit(exitCode);
//	}
//	
//	
//	private static void loadFile(Properties props, EsiEtlManager etlManager) throws EsiParseException {
//		String path = getOption(props, "-file");
//		String contact = getOption(props, "-contact");
//		String genus = getOption(props, "-genus");
//		String species = getOption(props, "-species");
//		etlManager.load(path, contact, genus, species);
//	}
//	
//	
//	private static void loadDir(Properties props, EsiEtlManager etlManager) throws EsiParseException {
//		String genus = getOption(props, "-genus");
//		String species = getOption(props, "-species");
//		
//		// Get directory
//		String dirPath = getOption(props, "-dir");
//		File dir = new File(dirPath);
//		if (! dir.exists() || ! dir.isDirectory()) {
//			String msg = "The path '" + dirPath + "' is not a valid directory";
//			printErrorMsg(msg);
//			printHelpAndExit(1);
//		}
//		
//		// Get list of .esi files
//		ExtensionFilenameFilter filter = new ExtensionFilenameFilter(".esi");
//		File[] files = dir.listFiles(filter);
//		
//		// Load each file
//		for (int i = 0; i < files.length; i++) {
//			File file = files[i];
//			String fileName = file.getName();
//			String filePath = file.getAbsolutePath();
//			System.out.println("Loading '" + fileName + "'");
//			assert fileName.length() > 0;
//			int pointIdx = fileName.indexOf(".");
//			assert pointIdx >= 0;
//			String contact = fileName.substring(0, pointIdx);
//			etlManager.load(filePath, contact, genus, species);
//		}
//	}
//		
//	
//	private static void show(EsiEtlManager etlManager) {
//		try {
//			CommandLineTableFormatter formatter = new CommandLineTableFormatter(3);
//			String[] headings = Experiment.printableHeadings();
//	        formatter.addColumnHeadings(headings);
//			PersistentExperiment[] experiments = etlManager.getAllPersistentExperiments();
//			for (int i = 0; i < experiments.length; i++)
//			    formatter.addRow(experiments[i].printableFields());
//			System.out.println("\n" + formatter.getTable());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	private static void delete(EsiEtlManager etlManager) {
//		System.out.println("\nDeleting all SKY/M-FISH&CGH data");
//		etlManager.deleteAll();
//		System.out.println("Done");
//	}
//	
//	
//	private void onNextSkyDataElement(SkyDataElement element) {
//
//	}
//	
//	
//	private void saveExperiment() {
//	    String message = "Saving experiment '" + this.experiment.getDescription() + "'";
//	    this.notifyListeners(new EtlMilestoneEvent(this, message));
//	    this.experiment.update();
//	}
//	
//	
//	private void onNextSkyCaseElement(SkyCaseElement element) throws EsiParseException{
//		this.skyCaseElement = element;
//	}
//	
//	
//	private void saveBioAssay() {
//	    if (this.binnedData.numBins() > 0) {
//	        LOGGER.info("Saving bioassay '" + this.binBioAssay.getName() + "'");
//	        this.binnedData.update();
//	        this.binBioAssay.setBinnedDataId(this.binnedData.getId());
//	        this.binBioAssay.update();
//	        this.experiment.add(this.binBioAssay);
//	    } 
////	    else if (this.fragGenomeArrayData.containsData()) {
////	        LOGGER.info("Loading fragment CGH data");
////	        id = this.loadGenomeArrayData(this.fragGenomeArrayData);
////	    }
//	    this.binnedData = null;
//	    this.binBioAssay = null;
//	}
//	
//	
//	private void onNextCGHSampleElement(CGHSampleElement element) 
//		throws EsiParseException {
//	    if (this.inCghSection) {
//	        this.saveBioAssay();
//	        this.inCghSection = false;
//	    }
//	}
//	
//	
//	private void initGenomeArrayData() {
//	    String name = this.skyCaseElement.getName().trim();
//	    String description = this.skyCaseElement.getDescription();
//		this.binBioAssay = this.persistentDomainObjectMgr.newPersistentBioAssay(name, description, this.dbName);
//		this.binBioAssay.setOrganism(this.experiment.getOrganism());
//		PersistentQuantitationType qType = this.persistentDomainObjectMgr.getPersistentQuantitationType(DEF_QUANTITATION_TYPE_NAME, true);
//		this.binnedData = this.persistentDomainObjectMgr.newPersistentBinnedData(qType);
////		this.fragBioAssay = new BioAssay();
////		this.fragBioAssay.setName(this.skyCaseElement.getName().trim());
////		this.fragBioAssay.setDescription(this.skyCaseElement.getDescription());
////		this.fragBioAssay.setOrganism(this.experiment.getOrganism());
//	}
//	
//	
//	private void onNextCGHBinElement(CGHBinElement element) throws EsiParseException {
//		if (this.binBioAssay == null)
//		    initGenomeArrayData();
//		this.inCghSection = true;
//	}
//	
//	
//	private void onNextCGHBinFragElement(CGHBinFragElement element) 
//		throws EsiParseException {
//	    PersistentChromosomeBin chromBin = this.persistentDomainObjectMgr.newPersistentChromosomeBin(
//	            element.getBin(), (float)element.getRatio(), element.getChromosomeNum());
//	    this.binnedData.add(chromBin);
//	}
//	
//	
//	private void onNextCGHFragElement(CGHFragElement element) throws EsiParseException {
////	    if (this.missingAttributes(element)) {
////	        LOGGER.warn("CGHFragElement missing attributes.  Not loading");
////	        return;
////	    }
////	    ArrayDatum datum1 = new ArrayDatum();
////	    ArrayDatum datum2 = new ArrayDatum();
////	    ArrayDatum datum3 = new ArrayDatum();
////	    ArrayDatum datum4 = new ArrayDatum();
////	    Reporter reporter1 = new Reporter();
////	    Reporter reporter2 = new Reporter();
////	    Reporter reporter3 = new Reporter();
////	    Reporter reporter4 = new Reporter();
////	    reporter1.setChromosome(element.getChromosome());
////	    reporter2.setChromosome(element.getChromosome());
////	    reporter3.setChromosome(element.getChromosome());
////	    reporter4.setChromosome(element.getChromosome());
////	    String valueStr = this.cghMappingsProperties.getProperty(element.getValue());
////	    if (valueStr != null) {
////	        double value = MathUtils.log2(Double.parseDouble(valueStr));
////	        datum1.setValue(0.0);
////	        datum2.setValue(value);
////	        datum3.setValue(value);
////	        datum4.setValue(0.0);
////	    } else
////	        LOGGER.warn("Cannot find value associated with '" + valueStr + ".'  " +
////	            "Make sure file 'cgh-mappings.properties' contains all possible values.");
////	    reporter1.setName("alteration left end");
////	    reporter2.setName("alteration left end");
////	    reporter3.setName("alteration right end");
////	    reporter4.setName("alteration right end");
////	    RelativePhysicalLocation location = new FractionalDistanceFromPTerm(element.getStart());
////	    reporter1.setRelativePhysicalLocation(location);
////	    reporter2.setRelativePhysicalLocation(location);
////	    location = new FractionalDistanceFromPTerm(element.getEnd());
////	    reporter3.setRelativePhysicalLocation(location);
////	    reporter4.setRelativePhysicalLocation(location);
////	    this.fragBioAssay.addDatum(datum1);
////	    this.fragBioAssay.addDatum(datum2);
////	    this.fragBioAssay.addDatum(datum3);
////	    this.fragBioAssay.addDatum(datum4);
//	}
//	
//	
//    private static String getOption(Properties options, String optName) {
//        String option = options.getProperty(optName);
//        if (option == null) {
//        	String msg = "You must set '" + optName + "'";
//        	printErrorMsg(msg);
//            printHelpAndExit(1);
//        }
//        return option;
//    }
//    
//    
//    private static void printErrorMsg(String msg) {
//    	System.err.println("\n********************************************************");
//        System.err.println("*** Error: " + msg);
//        System.err.println("********************************************************");
//    }
//    
//    
//    private boolean missingAttributes(CGHFragElement element) {
//        return
//        	element.getValue() == null ||
//        	element.getChromosome() < 1 ||
//        	Double.isNaN(element.getStart()) ||
//        	Double.isNaN(element.getEnd());
//    }
}
