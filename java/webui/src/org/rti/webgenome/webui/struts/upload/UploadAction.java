/*
F$Revision: 1.12 $
$Date: 2008-12-01 19:41:47 $


*/

package org.rti.webgenome.webui.struts.upload;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.io.RectangularFileReader;
import org.rti.webgenome.service.job.DataImportJob;
import org.rti.webgenome.units.BpUnits;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Performs uploading of data.
 * @author dhall
 */
public class UploadAction extends BaseAction {
	

	/**
	 * {@inheritDoc}
	 */
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		
		UploadForm uForm = (UploadForm) form;
		upload.setChromosomeColumnName(uForm.getChromosomeColumnName());
		upload.setExperimentName(uForm.getExperimentName());
		Long orgId = new Long(uForm.getOrganismId());
		Organism org = this.getDbService().loadOrganism(orgId);
		upload.setOrganism(org);
		upload.setPositionUnits(BpUnits.getUnits(uForm.getUnits()));
		upload.setPositionColumnName(uForm.getPositionColumnName());
		
		upload.setQuantitationType( QuantitationType.getQuantitationType( uForm.getQuantitationTypeId() ));
		upload.setQuantitationTypeLabel(uForm.getQuantitationTypeOther()) ;
		
		//TODO: Delete this, VB added just for test
		//System.out.println(upload.print2Buff());
		
		// if range is selected
		if (uForm.getPositionColumnName().equals("")){
			upload.setStartPositionColumnName(uForm.getStartPositionColumnName());
			upload.setEndPositionColumnName(uForm.getEndPositionColumnName());
		
			generateSinglePosFile(upload);
		}
		
		ActionForward forward = null;
		if (ProcessingModeDecider.processInBackground(
				upload, request, this.getIoService())) {
			Principal principal = PageContext.getPrincipal(request);
			DataImportJob job = new DataImportJob(upload,
					principal.getId(), principal.getDomain());
			this.getJobManager().add(job);
			ActionMessages messages = new ActionMessages();
    		messages.add("global", new ActionMessage("import.job"));
    		this.saveMessages(request, messages);
			forward = mapping.findForward("batch");
		} else {
			ShoppingCart cart = this.getShoppingCart(request);
			Experiment exp = this.getIoService().loadSmdData(upload, cart);
			this.getDbService().addArraysAndUpdateCart(exp, cart);
			forward = mapping.findForward("non.batch");
		}
		PageContext.removeUpload(request);
		return forward;
	}
	
	
	/**
	 * This is for situation where we have a range for position. The method will generate
	 * a temporary file where range is replaced with derived number of markers rows.
	 * 
	 * Derived number of markers is calculated such as:
	 * 
	 * If number of markers is between 1-999 => derived number of markers = number of markers / 100
	 * If number of markers is between 1000-9999 => derived number of markers = number of markers / 1000   
	 * If number of markers is between 10000-99999 => derived number of markers = number of markers / 10000
	 * If number of markers is between 100000-999999 => derived number of markers = number of markers / 100000
	 * If number of markers is between 1000000-9999999 => derived number of markers = number of markers / 1000000
	 * If number of markers is between 10000000-99999999 => derived number of markers = number of markers / 10000000
	 * If number of markers is between 100000000-999999990 => derived number of markers = number of markers / 100000000
	 * 
	 * Thanks Helen Pan for coming with this algorithm.
	 * 
	 * @param upload
	 * @throws Exception
	 */
	private void generateSinglePosFile(UploadDataSourceProperties upload) throws Exception{
		// retrieve reporter value; assume it's just one column
        Set<DataFileMetaData> dataFileMetaData = upload.getDataFileMetaData();
        String reporterNameColumnName = "";
        String localFileName = "";
        char delimiter = ',';
        String reporterColumValueName = "";
        
        if (!dataFileMetaData.isEmpty()){
        	DataFileMetaData newMetaData = new DataFileMetaData();
			for(DataFileMetaData entry : dataFileMetaData){			
				reporterNameColumnName = entry.getReporterNameColumnName();
				localFileName = entry.getLocalFileName();
				delimiter= entry.getFormat().getDelimiter();
				reporterColumValueName = entry.getDataColumnMetaData().iterator().next().getColumnName();
				newMetaData = entry;
			}
			// reset file name to the new one
			newMetaData.setLocalFileName(localFileName + ".temp");
			Set<DataFileMetaData> newDataFileMetaData = new HashSet();
			newDataFileMetaData.add(newMetaData);
			upload.setDataFileMetaData(newDataFileMetaData);
		}
		// read original file and transform one line to multiple depending of number of markers 
		RectangularFileReader reader = new RectangularFileReader(SystemUtils.getApplicationProperty("file.uploading.working.dir") + "\\" +  localFileName);
		reader.setDelimiter(delimiter);
      
	    
		
        // Get index of reporter-related columns
        List<String> colHeadings = reader.getColumnHeadings();
        
        int nameColIdx = this.indexOfString(colHeadings, reporterNameColumnName, false);        
        int chromColIdx = this.indexOfString(colHeadings, upload.getChromosomeColumnName(), false);
        int startPosColIdx = this.indexOfString(colHeadings,upload.getStartPositionColumnName(), false);
        int endPosColIdx = this.indexOfString(colHeadings,upload.getEndPositionColumnName(), false);
       // int numMarkersIdx = this.indexOfString(colHeadings,upload.getNumMarkersColumnName(), false);
        	
        
        int reporterValueIdx = this.indexOfString(colHeadings,reporterColumValueName, false);
                                
        List<String> reporterNames = reader.getColumn(nameColIdx);
        List<String> chromosomes = reader.getColumn(chromColIdx);
        List<String> startPosValues = reader.getColumn(startPosColIdx);
        List<String> endPosValues = reader.getColumn(endPosColIdx);
      //  List<String> numMarkers = reader.getColumn(numMarkersIdx);
        List<String> reporterValues = reader.getColumn(reporterValueIdx);
        
        // open the new temporary file
        String tempFileName = SystemUtils.getApplicationProperty("file.uploading.working.dir") + "\\" +  localFileName + ".temp";
        
        FileOutputStream fos = new FileOutputStream(tempFileName, true);
		PrintStream ps = new PrintStream(fos);
		
		// write column headings
    	ps.print(reporterNameColumnName + delimiter);
    	ps.print(upload.getChromosomeColumnName()+ delimiter);
    	ps.print("position"+ delimiter);
    	ps.print(reporterColumValueName + "\n");
    	
    	
    	//iterate through the rows and build the new file
    	int idx = 0;
    	boolean isFirstRow = true;
    	for (Iterator iList = reporterNames.iterator(); iList.hasNext();){
    		String reporterName = (String)iList.next();
    		String chromosome = chromosomes.get(idx);
    		long startPos = Long.parseLong(startPosValues.get(idx));
    		long endPos = Long.parseLong(endPosValues.get(idx));
    	//	long numMarker = Long.parseLong(numMarkers.get(idx));
    		String reporterValue = reporterValues.get(idx);
    		
    		// calculate distance
    		long distance = 0;
    		if (startPos < endPos)
    			distance = endPos - startPos;
    		else
    			distance = startPos - endPos;
    		
    		// calculates derived number of markers
    	//	long derivedNumMarkers = getDerivedNumMarker(numMarker);
    		
    		List<Long> positions = new ArrayList(2);
    		
    		// need to see positions for each point
    		if (distance != 0){
    			//first position is the smaller position
    			if (startPos < endPos)
    				positions.add(startPos);
        		else
        			positions.add(endPos);
    			
    			// then add number of position depending of the distance 
    			/*if (derivedNumMarkers != 0){
    				long offset = distance /derivedNumMarkers;
    				for (int i = 1; i < derivedNumMarkers - 1; i++){
    					if (startPos < endPos)
    						positions.add(startPos + (i * offset));
    					else
    						positions.add(endPos + (i * offset));
    				}
    			}*/
    			
    			// the end position is the largest one
    			if (startPos < endPos)
    				positions.add(endPos);
        		else
        			positions.add(startPos);
    			
    		}
    		
    		// the number of lines we need to add is the number of derived markers
    		int j = 0;
    		
    		
    		for (Long pos : positions){
    			String line = reporterName+ delimiter;
    			// need to modify reporter name because it will drop
    			// one of the values for the same reporter when plotting
    			ps.print(reporterName+ "_" + j++ + delimiter); 
    			
    			/*if (isFirstRow){
    				ps.print(reporterName+ "_1"  + delimiter);
    				isFirstRow = false;
    			}else
    				ps.print(reporterName + delimiter);
    				*/
    			
    			line += chromosome+ delimiter;
    			ps.print(chromosome+ delimiter);
    			ps.print(pos);
    			line += pos;
    			ps.print(delimiter);
    			line += delimiter;
    			
    			ps.print(reporterValue  + "\n");
    			line += reporterValue  + "\n";
    			System.out.println("****Line is *****");
    			System.out.println(line);
    		}
    		idx++;
    	}
    	
    	ps.close();
		fos.close();
    	upload.setPositionColumnName("position");
    	
		
	}
	
	/**
	 * Calculates derived number of markers.
	 * 
	 * @param numMarker
	 * @return
	 * @throws Exception
	 */
	private long getDerivedNumMarker(long numMarker) throws Exception{
		
		//define derived number of markers
		long derivedNumMarkers = 0;
		if (numMarker < 99)
			derivedNumMarkers = numMarker;
		else if (numMarker < 999)
			derivedNumMarkers = numMarker / 100;
		else if (numMarker < 9999)
			derivedNumMarkers = numMarker / 1000;
		else if (numMarker < 99999)
			derivedNumMarkers = numMarker / 10000;
		else if (numMarker < 999999)
			derivedNumMarkers = numMarker / 100000;
		else if (numMarker < 99999999)
			derivedNumMarkers = numMarker / 1000000;
		else if (numMarker < 999999999)
			derivedNumMarkers = numMarker / 10000000;
		
		
		return derivedNumMarkers;
		
	}
	  /**
     * Find index of search string within list of strings.
     * If the <code>prefix</code> argument is set to <code>false</code>,
     * then the seach string must exactly match the target string.
     * If this argument is set to <code>true</code>, then
     * the search string must only be a prefix of the target.
     * string. Comparison is case-insensitive.
     * @param strings List of strings to search through
     * @param searchString String to search for
     * @param prefix If set to <code>false</code>,
     * then the seach string must exactly match the target string.
     * If this argument is set to <code>true</code>, then
     * the search string must only be a prefix of the target.
     * string. 
     * @return Index of search string within list of strings, or -1
     * if search string not found.
     */
    private int indexOfString(final List<String> strings,
            final String searchString, final boolean prefix) {
        assert searchString != null && searchString.length() > 0;
        String searchStringLc = searchString.toLowerCase();
        int idx = -1;
        for (int i = 0; i < strings.size() && idx < 0; i++) {
            String target = strings.get(i);
            if (prefix) {
                if (target.toLowerCase().indexOf(searchStringLc) == 0) {
                    idx = i;
                }
            } else {
                if (searchString.equalsIgnoreCase(target)) {
                    idx = i;
                }
            }
        }
        return idx;
    }
}
