/*
$Revision: 1.3 $
$Date: 2007-12-17 23:29:23 $


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
			if (pName.endsWith(BIOASSAY_SUFFIX)) {
				
				// Parse out 'field name,' i.e. everything but suffix
				String fieldName = pName.substring(0,
						pName.length() - BIOASSAY_SUFFIX.length());
				
				// Get corresponding parameter giving bioassay name
				String bioAssayName = request.getParameter(pName);
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
