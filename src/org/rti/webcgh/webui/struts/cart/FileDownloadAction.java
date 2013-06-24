/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2006-10-23 02:20:39 $

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


package org.rti.webcgh.webui.struts.cart;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;

import org.rti.webcgh.deprecated.array.GenomeFeatureSearchResults;
import org.rti.webcgh.util.DownloadUtil;
import org.rti.webcgh.webui.util.Attribute;


/**
 * Action for downloading files.
 */
public final class FileDownloadAction extends Action {
	
	/**
	 * Performs action of retrieving group properties.  These are then
	 * associated with the servlet request object.
	 *
	 * @param mapping Routing information for downstream actions
	 * @param form Data from calling form
	 * @param request Servlet request object
	 * @param response Servlet response object
	 * @return Identification of downstream action as configured in the
	 * struts-config.xml file
	 * @throws Exception if something crashes.
	 */
	public ActionForward execute(
		final ActionMapping mapping, final ActionForm form,
		final HttpServletRequest request,
		final HttpServletResponse response
	) throws Exception {
		HttpSession session = request.getSession();
		String dtype = request.getParameter(Attribute.DOWNLOAD_TYPE);
		String atype = request.getParameter(Attribute.ANNOTATION_TYPE);
		if ("annotation".equals(dtype)) {
			doAnnotation(session, response, atype);
		}
		return mapping.findForward("success");	
	}
	
	/**
	 * Create Excel file from a collection of features.
	 * @param session HttpSession
	 * @param response HttpServletResponse
	 * @param type Annotation type
	 * @throws Exception if something crashes.
	 */
	private void doAnnotation(final HttpSession session,
			final HttpServletResponse response, final String type) 
		throws Exception {
		  ServletOutputStream sos = response.getOutputStream();
		  GenomeFeatureSearchResults[] maps = 
		      (GenomeFeatureSearchResults[]) session.getAttribute(
		    		  Attribute.ANNOTATION_REPORT);
    
		  response.setHeader("Content-Disposition", "attachment; filename="
				  + "annotation.xls");
		  response.setHeader("Pragma", "Public");        
		  response.setContentType("appilication/vnd.ms-excel");        
     
		  int bytes = DownloadUtil.annotation2Excel(maps, type, sos);   
		  response.setContentLength(bytes);
    
		  sos.flush(); 
		  sos.close();  
	}	
}
