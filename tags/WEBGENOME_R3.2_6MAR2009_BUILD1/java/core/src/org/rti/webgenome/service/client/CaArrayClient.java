/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-10-23 16:17:07 $


*/

package org.rti.webgenome.service.client;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.RectangularTextFileFormat;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.SystemUtils;



import gov.nih.nci.caarray.domain.array.AbstractDesignElement;
import gov.nih.nci.caarray.domain.array.AbstractProbe;
import gov.nih.nci.caarray.domain.array.ArrayDesign;
import gov.nih.nci.caarray.domain.data.AbstractDataColumn;
import gov.nih.nci.caarray.domain.data.BooleanColumn;
import gov.nih.nci.caarray.domain.data.DataRetrievalRequest;
import gov.nih.nci.caarray.domain.data.DataSet;
import gov.nih.nci.caarray.domain.data.DesignElementList;
import gov.nih.nci.caarray.domain.data.DesignElementType;
import gov.nih.nci.caarray.domain.data.DoubleColumn;
import gov.nih.nci.caarray.domain.data.FloatColumn;
import gov.nih.nci.caarray.domain.data.HybridizationData;
import gov.nih.nci.caarray.domain.data.IntegerColumn;
import gov.nih.nci.caarray.domain.data.LongColumn;
import gov.nih.nci.caarray.domain.data.QuantitationType;
import gov.nih.nci.caarray.domain.data.ShortColumn;
import gov.nih.nci.caarray.domain.data.StringColumn;
import gov.nih.nci.caarray.domain.hybridization.Hybridization;
import gov.nih.nci.caarray.domain.project.Experiment;
import gov.nih.nci.caarray.services.CaArrayServer;
import gov.nih.nci.caarray.services.data.DataRetrievalService;
import gov.nih.nci.caarray.services.search.CaArraySearchService;

/**
 * Implements caArray client methods such as connect to caArray, retrieve experiment
 * data, etc.
 * 
 * @author vbakalov
 *
 */
public class CaArrayClient {
	private static final String REPORTER_NAME_HEADING = "Probe Set ID";
	private static final String REPORTER_VALUE_HEADING = "BioAssay";
	private static final String CHROMOSOME_NUMBER_HEADING = "chr";
	private static final String CHROMOSOME_START_POS = "bp_start";
	private static final String DEFAULT_QUANTITATION_TYPE = "CHPSignal";
	 
	protected CaArrayServer server;
	protected String serverName;
	protected int jndiPort;
	protected CaArraySearchService searchService; 
	protected String arrayDesignName;
	protected String fileUploadDir;
	protected Experiment experiment;	
	protected String userName;
	protected int numBioassays = 0;
	protected int numValuesRetrieved = 0;
	protected UploadDataSourceProperties upload = null;
	
	 // Just for testing
    public static void main(String[] args) {
    	try{
    		CaArrayClient client = new CaArrayClient("BakalovV2", "Bojko!050973");    	
    		UploadDataSourceProperties upload = client.downloadExperiment2File("Vessie-test2");
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	/**
	 * 
	 */    
	public CaArrayClient(String userName, String password) throws Exception{
		serverName = SystemUtils.getApplicationProperty("caarray.server.url");
		jndiPort = Integer.parseInt(SystemUtils.getApplicationProperty("caarray.jndi.port"));
		fileUploadDir = SystemUtils.getApplicationProperty("file.uploading.working.dir");
		connect(userName, password);
	}
	
	/**
	 * This will connect the user to caArray server.
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	private void connect(String userName, String password)throws Exception{			
		server = new CaArrayServer(serverName, jndiPort);		
		server.connect(userName, password);
		
		// Need this to use later
		this.userName = userName;
	}	
	
	/**
	 * Will retrieve experiment data and will write them into rectangular format.
	 * The file will have columns "Reporter Name", 
	 * 
	 * @param experimentTitle the experiment name
	 * 
	 * @return number of bioassays retrieved
	 * 
	 * @throws Exception
	 */
	public UploadDataSourceProperties downloadExperiment2File(String expId) throws Exception{
		 upload = new UploadDataSourceProperties();
		
		 //TODO: First check if file already exist not to download again
		 
		 DataRetrievalRequest request = new DataRetrievalRequest();
		 searchService = server.getSearchService();
		 
		 // I realize this is tedious but just keep it the way it was in
		 // case we move to fetching more experiments at once...
		// String[] experimentTitles = new String[1];
		// experimentTitles[0] = experimentTitle;
		 
		 lookupExperiments(searchService, request, expId);
         lookupQuantitationTypes(searchService, request);
         
         
         DataRetrievalService dataService = server.getDataRetrievalService();
         long startTime = System.currentTimeMillis();
         DataSet dataSet = dataService.getDataSet(request);
         
         // Retrieve reporter names; also assume that the order is preserved
         Collection reporterNamesList = getReporters(dataSet);
         String reporterValuesFileName = "";
         
         if (dataSet != null){
        	Collection<float[]> bioassayValues = getBioAssayReporterValues(dataSet);
        	reporterValuesFileName = print2File(reporterNamesList, bioassayValues);
         }else{
        	 System.out.println("No data sets retrieved");
         }
         
         // Some benchmarks
             long endTime = System.currentTimeMillis();
             long totalTime = endTime - startTime;
             System.out.println("Total Time: " + totalTime + " ms.");
             
         //  Initiate upload
             upload.setChromosomeColumnName(CHROMOSOME_NUMBER_HEADING);
             Set<DataFileMetaData> dfmdSet = new HashSet<DataFileMetaData>();
             DataFileMetaData dfmd = new DataFileMetaData();
             
             // Initiate bioassays info
             Set<DataColumnMetaData> dcmdSet = new HashSet<DataColumnMetaData>();
             
             for (int i=1; i <= numBioassays; i++){
            	 DataColumnMetaData dcmd = new DataColumnMetaData();
            	 dcmd.setBioAssayName(REPORTER_VALUE_HEADING + i);
                 dcmd.setColumnName(REPORTER_VALUE_HEADING + i);
                 dcmdSet.add(dcmd);
             }
             dfmd.setDataColumnMetaData(dcmdSet);
             dfmd.setFormat(RectangularTextFileFormat.CSV);
            // dfmd.setFormatName(RectangularTextFileFormat.CSV);
             dfmd.setLocalFileName(reporterValuesFileName);
             //dfmd.getRemoteFileName(reporterValuesFileName);
             dfmd.setReporterNameColumnName(REPORTER_NAME_HEADING);
             dfmdSet.add(dfmd);
             
             upload.setDataFileMetaData(dfmdSet);
             upload.setExperimentName(experiment.getTitle());
             
             // TODO: For now assume it's Homo sapiens but should extract this from caArray
             Organism org = new Organism();
             org.setGenus("Homo");
             org.setSpecies("sapiens");
             org.setId(new Long(1));
             upload.setOrganism(org);
             
             upload.setPositionColumnName(CHROMOSOME_START_POS);
             // TODO: Not sure about this as well
             upload.setPositionUnits(BpUnits.BP);
             // TODO: Leave this empty for now and hopes it will work
             upload.setPositionUnitsName(BpUnits.BP.getName());
             
             // Init this outside this class 
           //  upload.setQuantitationType(QuantitationType.LOG_2_RATIO_COPY_NUMBER);
             
             //not sure so comment for now 
             //upload.setReporterFile();
             upload.setReporterFileFormat(RectangularTextFileFormat.CSV);
             upload.setReporterFileReporterNameColumnName(REPORTER_NAME_HEADING);
             upload.setReporterLocalFileName(arrayDesignName + ".csv");
             upload.setReporterRemoteFileName("none");
		return upload;
	}

	/**
	 * Retrieve list of experiments with samples. No need to show experiments with no samples
	 * since they do not have data.
	 * 
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public Collection<Experiment> getExperimentsWithSamples() throws Exception{
      
        Experiment exampleExperiment = new Experiment();
        searchService = server.getSearchService();
        List<Experiment> experimentList = searchService.search(exampleExperiment);
        List<Experiment> expListWithSamples = new ArrayList<Experiment>(); 
        
        // remove experiments that do not have samples
        for (Experiment e : experimentList){
        	int sampleCnt = e.getSampleCount();
        	
            
        	if (sampleCnt > 0){
        		// check if experiment array design is the one we support
        		Set<ArrayDesign> arrDesigns = e.getArrayDesigns();
        		if (arrDesigns.isEmpty() || arrDesigns == null)
        			continue;
        		ArrayDesign ad = arrDesigns.iterator().next();
        		
        		if (SupportedArrayDesigns.isSupported(ad.getName()))
        			expListWithSamples.add(e);
        	}	
        }
                            
        return expListWithSamples;
    
}


/**
 * Retrieve reporter values and initiate a Collection of float arrays to hold these values.
 * 
 * @param dataSet
 * @return
 * @throws Exception
 */
private Collection<float[]> getBioAssayReporterValues(DataSet dataSet) throws Exception{
	
	
	 // Create Collection of Collections to capture bioassay values. 
	Collection<float[]> bioassayValues = new ArrayList<float[]>(dataSet.getHybridizationDataList().size());
	 System.out.println("dataSet.getHybridizationDataList().size()=" + dataSet.getHybridizationDataList().size());
	 
    // Get each HybridizationData in the DataSet.
    for (HybridizationData oneHybData : dataSet.getHybridizationDataList()) {
        HybridizationData populatedHybData = searchService.search(oneHybData).get(0);
//        System.out.println("**************Hybridization id " + populatedHybData.getId() + " ***********");
        // Get array design name
        
       // Hybridization hbrd = populatedHybData.getHybridization();
       // Array arry = hbrd.getArray();
       // ArrayDesign arrDesign = arry.getDesign();
       // System.out.println("******Array Desing Name is " +   arrDesign.getName());
        // Get each column in the HybridizationData.
        for (AbstractDataColumn column : populatedHybData.getColumns()) {
            AbstractDataColumn populatedColumn = searchService.search(column).get(0);
            // Find the type of the column.
            QuantitationType qType = populatedColumn.getQuantitationType();
  //          System.out.println("***Quantitation type is " + qType.getName());
          
            // From previous tests we know that the reporter value will be the CHPSignal
            if (!qType.getName().equals("CHPSignal")){
            	continue;
            }
            
            Class typeClass = qType.getTypeClass();
            if (typeClass == Float.class) {
            	float[] values = ((FloatColumn) populatedColumn).getValues();
            	
            	bioassayValues.add(values);
            	numBioassays++;
    //            System.out.println("***Float values with size " + values.length);
                
                // add this just first time
                if ( numBioassays == 1 )
                	numValuesRetrieved += values.length;
                break;
            }
          }
      //  System.out.println("Retrieved " + dataSet.getHybridizationDataList().size() + " hybridization data elements, "
        //        + dataSet.getQuantitationTypes().size() + " quantitation types and " + numValuesRetrieved + " values.");
    } 
        return bioassayValues;
    
}    

    /**
     * This will print reporter names and bioassay reporter values to a file.
     * 
     * @param reporterNamesList holds reporter names
     * @param bioassayValues holds the reporter values for every bioassay 
     * 
     * @throws Exception
     */
	private String print2File(Collection reporterNamesList, Collection<float[]> bioassayValues) throws Exception{
		String fileName = experiment.getPublicIdentifier() + "-" + userName + ".csv";
		File f = new File(fileUploadDir + "\\" + fileName);
    	FileOutputStream os = new FileOutputStream(f);
    	byte[] buffer = new byte[1024];
    	StringBuffer sb = new StringBuffer();
    	Iterator<String> iReporterNames = reporterNamesList.iterator();
    	
    	
    	// Write column headings first
    	// Need to generate reporter value headings depending of number of bioassays
    	String reporterHeadings = "";
    	for (int i = 1; i <= numBioassays; i++ )
    		reporterHeadings += REPORTER_VALUE_HEADING + i + ",";
    		
    	sb.append(REPORTER_NAME_HEADING + ',' +  reporterHeadings + "\n");
        
    //	System.out.println("Reporter name list size is " + reporterNamesList.size());
    //	System.out.println("numValuesRetrieved is " + numValuesRetrieved);
    	
    	for (int j = 0; j < numValuesRetrieved; j++){    		    		
    		sb.append(iReporterNames.next() + ",");
    		for(float[] values : bioassayValues){    		    			
    			//sb.append(Math.log(values[j]) + ","); 
    			sb.append(Math.log(values[j])/Math.log(2.0) + ",");
    		}	
    		sb.append("\n");
    	}
    		
       os.write(sb.toString().getBytes(), 0, sb.length());
       os.flush();
       os.close();
       return fileName;
	}
	
	
	/*
	 * This will retrieve reporter names.
	 * 
	 * 
	 */
	private Collection<String> getReporters(DataSet dataSet) throws Exception{
		Collection<String> reporterNameList = new ArrayList<String>();
		
		DesignElementList designElementList = dataSet.getDesignElementList();
		DesignElementList populatedDesignElementList = searchService.search(designElementList).get(0);

		List<AbstractDesignElement> designElements = populatedDesignElementList.getDesignElements();

		String designElementType = populatedDesignElementList.getDesignElementType();

		if (DesignElementType.LOGICAL_PROBE.getValue().equals(designElementType) || DesignElementType.PHYSICAL_PROBE.getValue().equals(designElementType)) {
			for (AbstractDesignElement oneDesignElement : designElements) {
				AbstractProbe probe = (AbstractProbe) oneDesignElement;
				System.out.println("Probe Name: " + probe.getName());                                    	                	
				reporterNameList.add(probe.getName());      
				
			}
		}	
		
		return reporterNameList;
    
    }
    
	private void lookupExperiments(CaArraySearchService service, DataRetrievalRequest request, String[] experimentTitles) {
        
        if (experimentTitles == null) {
            return;
        }

        ArrayDesign ad = null;
        
        // Locate each experiment, and add its hybridizations to the request.
        Experiment exampleExperiment = new Experiment();
        for (int i = 0; i < experimentTitles.length; i++) {
            String experimentTitle = experimentTitles[i];
            exampleExperiment.setTitle(experimentTitle);
            List<Experiment> experimentList = service.search(exampleExperiment);
            
            Experiment exp = experimentList.iterator().next();
            
            // TODO: For now assume it's just one experiment
            experiment = exp;
            Set<ArrayDesign> arrDesigns = exp.getArrayDesigns();
            ad = arrDesigns.iterator().next();
            Set<Hybridization> allHybs = getAllHybridizations(experimentList);
            request.getHybridizations().addAll(allHybs);
        }
        
        arrayDesignName = ad.getName();        
        System.out.println("****Array desing name = " + ad.getName());
    }
	
	private void lookupExperiments(CaArraySearchService service, DataRetrievalRequest request, String expId) {
        ArrayDesign ad = null;

		// Locate each experiment, and add its hybridizations to the request.
		Experiment exampleExperiment = new Experiment();

		exampleExperiment.setPublicIdentifier(expId);
		List<Experiment> experimentList = service.search(exampleExperiment);

		Experiment exp = experimentList.iterator().next();

		// TODO: For now assume it's just one experiment
		experiment = exp;
		Set<ArrayDesign> arrDesigns = exp.getArrayDesigns();
		ad = arrDesigns.iterator().next();
		Set<Hybridization> allHybs = getAllHybridizations(experimentList);
		request.getHybridizations().addAll(allHybs);

		arrayDesignName = ad.getName();
		System.out.println("****Array desing name = " + ad.getName());
    }



    private void lookupQuantitationTypes(CaArraySearchService service, DataRetrievalRequest request) {
        String[] quantitationTypeNames = DEFAULT_QUANTITATION_TYPE.split(",");
        if (quantitationTypeNames == null) {
            return;
        }

        // Locate each quantitation type and add it to the request.
        QuantitationType exampleQuantitationType = new QuantitationType();
        for (int i = 0; i < quantitationTypeNames.length; i++) {
            String quantitationTypeName = quantitationTypeNames[i];
            exampleQuantitationType.setName(quantitationTypeName);
            List<QuantitationType> quantitationTypeList = service.search(exampleQuantitationType);
            //printQuantitationType(quantitationTypeList);
            request.getQuantitationTypes().addAll(quantitationTypeList);
            
        }
    }

    private void printQuantitationType(List<QuantitationType> quantitationTypeList) {
       for (QuantitationType qt : quantitationTypeList){
    	   System.out.println(" *****Qunatitaion type is : " + qt.getName());
       }
    }
    
    private Set<Hybridization> getAllHybridizations(List<Experiment> experimentList) {
        Set<Hybridization> hybridizations = new HashSet<Hybridization>();
        for (Experiment experiment : experimentList) {
            hybridizations.addAll(getAllHybridizations(experiment));
        }
        return hybridizations;
    }

    private Set<Hybridization> getAllHybridizations(Experiment experiment) {
        Set<Hybridization> hybridizations = new HashSet<Hybridization>();
        hybridizations.addAll(experiment.getHybridizations());
        return hybridizations;
    }


   


}