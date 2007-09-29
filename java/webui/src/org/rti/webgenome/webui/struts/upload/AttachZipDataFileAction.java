/*
$Revision: 1.2 $
$Date: 2007-09-29 05:24:19 $

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

package org.rti.webgenome.webui.struts.upload;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.ZipEntryMetaData;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.PageContext;

/**
 * Completes process of attaching ZIPped data files
 * to an upload form.
 * @author dhall
 *
 */
public class AttachZipDataFileAction extends BaseAction {
	
	/**
	 * Suffix added to a data file column heading by the JSP
	 * 'specifyDataColumn.jsp' to indicate the parameter
	 * is a checkbox for selecting if the corresponding column
	 * contains data.
	 */
	private static final String CHECKBOX_SUFFIX = "_cb";
	
	/**
	 * Suffix added to a data file column heading by the JSP
	 * 'specifyDataColumn.jsp' to indicate the parameter
	 * is a text box for specifying the name of the bioassay
	 * that is derived from data in the column.
	 */
	private static final String BIOASSAY_SUFFIX = "_bioassay";
	
	/**
	 * Suffix added to a parameter by JSP 'specifyDataColumn.jsp'
	 * to indicate the parameter is a select for specifying
	 * reporter column heading.
	 */
	private static final String SELECT_SUFFIX = "_sb";

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(
	        final ActionMapping mapping, final ActionForm form,
	        final HttpServletRequest request,
	        final HttpServletResponse response
	    ) throws Exception {
		
		// Recover zip file metadata
		ZipFileMetaData zipMeta = PageContext.getZipFileMetaData(request);
		
		// Instantiate helper map
		Map<String, DataFileMetaData> metaMap =
			new HashMap<String, DataFileMetaData>();
		
		// Recover upload properties
		UploadDataSourceProperties upload = PageContext.getUpload(request);
		
		// Iterate through request parameters
		Enumeration<String> pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String pName = pNames.nextElement();
			
			// If parameter associated with a checkbox
			if (pName.endsWith(CHECKBOX_SUFFIX)) {
				
				// Parse out 'field name,' i.e. everything but suffix
				String fieldName = pName.substring(0,
						pName.length() - CHECKBOX_SUFFIX.length());
				
				// Get corresponding parameter giving bioassay name
				String bioAssayParamName = fieldName + BIOASSAY_SUFFIX;
				String bioAssayName = request.getParameter(bioAssayParamName);
				if (bioAssayName == null || bioAssayName.length() < 1) {
					ActionErrors errors = new ActionErrors();
					errors.add("global",
							new ActionError("missing.bioassay.name"));
					this.saveErrors(request, errors);
					return mapping.findForward("errors");
				}
				
				// Parse out local file name corresponding to ZIP entry
				int firstUnderscoreIndex = fieldName.indexOf('_');
				String fName = fieldName.substring(0, firstUnderscoreIndex);
								
				// Recover or instantiate ZIP file metadata
				// associated with file
				DataFileMetaData dfMeta = metaMap.get(fName);
				if (dfMeta == null) {
					dfMeta = this.newDataFileMetaData(zipMeta, fName);
					metaMap.put(fName, dfMeta);
				}
				
				// Add new ZIP column metadata entry
				String colName = fieldName.substring(firstUnderscoreIndex + 1);
				DataColumnMetaData colMeta =
					new DataColumnMetaData(colName, bioAssayName);
				dfMeta.add(colMeta);
				
			// If parameter associated with select box
			} else if (pName.endsWith(SELECT_SUFFIX)) {
				String fName = pName.substring(0, pName.length()
						- SELECT_SUFFIX.length());
				DataFileMetaData dfMeta = metaMap.get(fName);
				if (dfMeta == null) {
					dfMeta = this.newDataFileMetaData(zipMeta, fName);
					metaMap.put(fName, dfMeta);
				}
				dfMeta.setReporterNameColumnName(request.getParameter(pName));
			}
		}
		
		// Add all new data file metadata to upload
		for (String key : metaMap.keySet()) {
			DataFileMetaData dfMeta = metaMap.get(key);
			upload.add(dfMeta);
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * Instantiate new data file metadata.
	 * @param zipMeta Metadata for entire ZIP file
	 * @param localFileName Name of local data file unpacked
	 * from the ZIP file
	 * @return New metadata on data file {@code localFileName}
	 */
	private DataFileMetaData newDataFileMetaData(
			final ZipFileMetaData zipMeta, final String localFileName) {
		DataFileMetaData dfMeta = new DataFileMetaData();
		ZipEntryMetaData zeMeta = zipMeta.getZipEntryMetaDataByLocalFileName(
				localFileName);
		dfMeta.setFormat(zipMeta.getFileFormat());
		dfMeta.setLocalFileName(localFileName);
		dfMeta.setRemoteFileName(zeMeta.getRemoteFileName());
		return dfMeta;
	}
}
