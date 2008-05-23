/*
$Revision: 1.5 $
$Date: 2008-05-23 21:08:56 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


*/

package org.rti.webgenome.webui.struts.cart;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataContainingBioAssay;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.DataSourceProperties.BaseDataSourceProperties;
import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.io.RectangularFileWriter;
import org.rti.webgenome.service.job.DownloadDataJob;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;
import org.rti.webgenome.service.util.InMemoryChromosomeArrayDataGetter;
import org.rti.webgenome.service.util.SerializedChromosomeArrayDataGetter;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

public class DownloadRawDataAction extends BaseAction {

	
    /**
     * Execute action.
     * @param mapping Routing information for downstream actions
     * @param form Form data
     * @param request Servlet request object
     * @param response Servlet response object
     * @return Identification of downstream action as configured in the
     * struts-config.xml file
     * @throws Exception All exceptions thrown by classes in
     * the method are passed up to a registered exception
     * handler configured in the struts-config.xml file
     */
    public ActionForward execute(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	ChromosomeArrayDataGetter getter = new SerializedChromosomeArrayDataGetter(
				this.getDataFileManager());
		
    	// Get shopping cart
    	ShoppingCart cart = this.getShoppingCart(request);
    	
    	// Get ID of selected bioassay
    	long bioAssId = Long.parseLong(request.getParameter("bioAssId"));
    	long expId = Long.parseLong(request.getParameter("expId"));
    	
    	// Retrieve experiment
       	Experiment exp = cart.getExperiment(expId);
       	
       	// Retrieve original column headings
    	String headings[] = getColumnHeadings(exp);
    	

    	// Get sellected bioassay id
    	BioAssay ba = cart.getBioAssay(bioAssId);
    	
    	// Retrieve bioassay raw data
    	ChromosomeArrayData chrArryData = getter.getChromosomeArrayData(ba, (short)1);
		List<ArrayDatum> arrDatums = chrArryData.getArrayData();
		
			
		String imagePath = request.getContextPath()+ "/download" + "/" + "fileName";
		
		String absPlotPath = this.getServlet().getServletContext().getRealPath("/download");
		
		// Iinitalize RectangularFileWriter 
		RectangularFileWriter rfw = new RectangularFileWriter(arrDatums, headings);
		
		// set file name
		
		if (ProcessingModeDecider.downloadInBackground(arrDatums, request)) {
			Principal principal = PageContext.getPrincipal(request);
			DownloadDataJob job = new DownloadDataJob(arrDatums, ba, rfw,
					principal.getName(), principal.getDomain());
			this.getJobManager().add(job);
			ActionMessages messages = new ActionMessages();
    		messages.add("global", new ActionMessage("download.job"));
    		this.saveMessages(request, messages);
			mapping.findForward("batch");
			
		}
		
		
	    // Write bioassay raw data to the Output stream   
	    ServletOutputStream sos = response.getOutputStream();
	    
	    // Set response atributes
	    response.setHeader("Content-Disposition", "attachment; filename=" + ba.getName() + ".csv");               
	    response.setContentType("appilication/octet-stream");        
	    response.setBufferSize(1024 * 15);
        
	    // Write data to buffer
	    StringBuffer sbuff = new StringBuffer();
	    
	    rfw.writeData2StringBuffer(sbuff);
	    		   
	    sos.write(sbuff.toString().getBytes());
	    int totalBytes = sbuff.length();             
	    response.setContentLength(totalBytes);   
	        
	    sos.flush(); 
	    sos.close();  
	    
    	// Should not forward anywhere because it will prompt user to save/download file.
    	return null;
       
    }
    
    /**
     * This will retrieve column heading information.
     * 
     * @param e - the experiment
     * @return array of Strings that represent uploaded file original column headings.
     * 
     * @throws Exception
     */
    private String[] getColumnHeadings(Experiment e) throws Exception{
    	String headings[] = new String[4];
    	
    	BaseDataSourceProperties udpBase = (BaseDataSourceProperties)e.getDataSourceProperties();
       	UploadDataSourceProperties udp = (UploadDataSourceProperties)udpBase;
       	
       	// It might be that the source is not uploaded that we don't have information
       	// about headings.
       	if (udp == null)
       		return null;
       	
       	Set<DataFileMetaData> dataFileMetaDatas = udp.getDataFileMetaData();
       	
       	// Retrieve reporter name and value column headings.
       	for (DataFileMetaData dfmd : dataFileMetaDatas){
       		//System.out.println("**** Reporterrrr column name is " + dfmd.getReporterNameColumnName());
       		headings[RectangularFileWriter.REPORTER_NAME_HEADING_IDX] = dfmd.getReporterNameColumnName();
       		Set<DataColumnMetaData> dataColumnMetaDatas = dfmd.getDataColumnMetaData();
       		
       		if (dataColumnMetaDatas == null){
       			headings[RectangularFileWriter.REPORTER_VALUE_HEADING_IDX] = RectangularFileWriter.DEFAULT_REPORTER_VALUE_HEADING;
       			break;
       		}
       		
       		for (DataColumnMetaData dcmd : dataColumnMetaDatas){
       			//System.out.println("**** Reporterrrr value column name is " + dcmd.getColumnName());
       			headings[RectangularFileWriter.REPORTER_VALUE_HEADING_IDX] = dcmd.getColumnName();
       		}
       		break;
       	}       	
       	
       	// Initiate chromosome and chromosome position column headings.
       	headings[RectangularFileWriter.CHROMOSOME_HEADING_IDX] = udp.getChromosomeColumnName();
       	headings[RectangularFileWriter.POSITION_HEADING_IDX] = udp.getPositionColumnName();
       	
    	return headings;
    }
    
    /**
     * Execute action.
     * @param mapping Routing information for downstream actions
     * @param form Form data
     * @param request Servlet request object
     * @param response Servlet response object
     * @return Identification of downstream action as configured in the
     * struts-config.xml file
     * @throws Exception All exceptions thrown by classes in
     * the method are passed up to a registered exception
     * handler configured in the struts-config.xml file
     */
    public ActionForward execute1(
        final ActionMapping mapping, final ActionForm form,
        final HttpServletRequest request,
        final HttpServletResponse response
    ) throws Exception {
    	ChromosomeArrayDataGetter getter = new SerializedChromosomeArrayDataGetter(
				this.getDataFileManager());
		
    	// Get shopping cart
    	ShoppingCart cart = this.getShoppingCart(request);
    	
    	// Get ID of selected bioassay
    	long id = Long.parseLong(request.getParameter("id"));

    	
	    	   
	    	   
	    	   
    	// Retrieve experiment
   	Experiment exp = cart.getExperiment(id);
   	
   	//UploadDataSourceProperties udp = (BaseDataSourceProperties)exp.getDataSourceProperties();
   	
   	BaseDataSourceProperties udpBase = (BaseDataSourceProperties)exp.getDataSourceProperties();
   	UploadDataSourceProperties udp = (UploadDataSourceProperties)udpBase;
   	
   	Set<DataFileMetaData> dataFileMetaDatas = udp.getDataFileMetaData();
   	
   	for (DataFileMetaData dfmd : dataFileMetaDatas){
   		System.out.println("**** Reporterrrr column name is " + dfmd.getReporterNameColumnName());
   		Set<DataColumnMetaData> dataColumnMetaDatas = dfmd.getDataColumnMetaData();
   		for (DataColumnMetaData dcmd : dataColumnMetaDatas){
   			System.out.println("**** Reporterrrr value column name is " + dcmd.getColumnName());
   		}	
   	}
   	
   	
   	if (udp != null){
   		System.out.println("****Chrom column name is "  + udp.getChromosomeColumnName());
   		System.out.println("**** Position column name is " + udp.getPositionColumnName());
   		System.out.println("**** Reporter column name is " + udp.getReporterFileReporterNameColumnName());
   	}
   		
   	
   	/*
    	Set<BioAssay> bioassyas = exp.getBioAssays();
    	
    	if (exp.dataInMemory())
    		System.out.println("Data are in memory....");
    	
    	// for now assume it's loading values in memory
		//ChromosomeArrayDataGetter getter = new InMemoryChromosomeArrayDataGetter();
    	ChromosomeArrayDataGetter getter = new SerializedChromosomeArrayDataGetter(
				this.getDataFileManager());
		
    	//iterate through bioasssays
    	for (BioAssay ba : bioassyas){
    		System.out.println("Bioassay name is " + ba.getName());
    		
    		SortedSet<Short> chromosomes = ba.getChromosomes();
    		
    		System.out.println("****Chromosome values ******");
    		// print chromosomes
    		for (Short chr : chromosomes){
    			System.out.println(chr.intValue());
    		}
    		
    		Array arr = ba.getArray();
    		System.out.println("***** Array name is " + arr.getName());
    		
    		DataFileManager dfManager = getDataFileManager();
    		List<Reporter> reporters = dfManager.recoverReporters(arr);
    		
    		System.out.println("***** Printing reporters....");
    		
    		for (Reporter r : reporters){
    			Collection<String> annotations = r.getAnnotations();
    			
    			System.out.println("***** Annotations...*****");
    			for (String ann : annotations){
    				System.out.println(ann);    				
    			}
    			
    			System.out.println("Chromosome is " + r.getChromosome());
    			
    			System.out.println("Location is " + r.getLocation());
    			
    			System.out.println("Name is " + r.getName());
    			
    			ChromosomeArrayData chrArryData = getter.getChromosomeArrayData(ba, (short)1);
    			List<ArrayDatum> arrDatums = chrArryData.getArrayData();
    			
    			for (ArrayDatum datum : arrDatums){
    				System.out.println("Datum value is " + datum.getValue());    				
    			}
    			
    		}
    		
    		
  */  		
    		
    		/*
    		   // Case: Plot immediately
    	    if (!(ProcessingModeDecider.plotInBackground(experiments,
    	    		params.getGenomeIntervals(), request)
    	    		|| ProcessingModeDecider.plotInBackground(params, request))) {
    	    	ChromosomeArrayDataGetter getter = null;
    	    	if (this.dataInMemory(request)) {
    	    		getter = new InMemoryChromosomeArrayDataGetter();
    	    	} else {
    	    		getter = new SerializedChromosomeArrayDataGetter(
    	    				this.getDataFileManager());
    	    	}
    	    	plot = this.getPlotService().plotExperiments(
    	    			plot, experiments, params, cart, getter);
    	    	if (PageContext.standAloneMode(request)) {
    	    		this.getDbService().updateShoppingCart(cart);
    	    	}
    	    	request.setAttribute("plot", plot);
    	    	forward = mapping.findForward("non.batch");
    	    	
    	    // Case: Generate plot in background
    	    } else {
    	    	Principal principal = PageContext.getPrincipal(request);
    	    	PlotJob job = new PlotJob(plotId,
    	    			new HashSet<Experiment>(experiments), params,
    	    			principal.getName(), principal.getDomain());
    	    	this.getJobManager().add(job);
    	    	ActionMessages messages = new ActionMessages();
    	    	messages.add("global", new ActionMessage("plot.job"));
    	    	this.saveMessages(request, messages);
    	    	forward = mapping.findForward("batch");
    	    }
    		*/
    		
    		/*SortedMap<Short, String> chrRepFileNames = arr.getChromosomeReportersFileNames();
    		Set<Short> chrNumbers = chrRepFileNames.keySet();
    		
    		for (Short chrNum : chrNumbers){
    			System.out.println("Chromosome number =" + chrNum);
    			System.out.println("File is" + chrRepFileNames.get(chrNum));
    		}*/
    		
    	
    	/*}*/
    	
    	
    	
    	
    	return null;
       // return mapping.findForward("success");
    }
    
}
