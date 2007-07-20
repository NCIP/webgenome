/*
$Revision: 1.3 $
$Date: 2007-07-20 22:07:14 $

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
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.OrganismDao;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.webui.struts.BaseAction;
import org.rti.webgenome.webui.util.ProcessingModeDecider;

/**
 * Action for uploading files to a working directory.
 */
public final class FileUploadAction extends BaseAction {
	
	//
	//     ATTRIBUTES
	//
	
	/** Service for performing file IO. */
	private IOService ioService = null;
	
	/** Organism data access object. */
	private OrganismDao organismDao = null;
	
	
	//
	//     SETTERS
	//
	
	/**
	 * Sets service for performing IO.
	 * @param ioService File IO service.
	 */
	public void setIoService(final IOService ioService) {
		this.ioService = ioService;
	}
	
	/**
	 * Set organism data access object.
	 * @param organismDao Organism data access object
	 */
	public void setOrganismDao(final OrganismDao organismDao) {
		this.organismDao = organismDao;
	}
	
	//
	//     OVERRIDES
	//

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward execute(
		final ActionMapping mapping, final ActionForm form,
		final HttpServletRequest request,
		final HttpServletResponse response
	) throws Exception {
		
		// Get organism selected by user
		Long orgId = Long.parseLong(((FileUploadForm) form).getOrganismId());
		Organism organism = this.organismDao.load(orgId);
		
		// Stream bits to temporary file
		FormFile formFile = ((FileUploadForm) form).getUploadFile();
		InputStream in = formFile.getInputStream();
		File tempFile = this.ioService.upload(in);
		in.close();
		
		ShoppingCart cart = this.getShoppingCart(request);
		ActionForward forward = null;
		
		// If file small enough, go ahead an parse
		if (!ProcessingModeDecider.processInBackground(tempFile)) {
			this.ioService.loadSmdData(tempFile.getName(),
					organism, cart);
			forward = mapping.findForward("non.batch");
		}
		
		this.persistShoppingCartChanges(cart, request);
		
		return forward;
	}
}
