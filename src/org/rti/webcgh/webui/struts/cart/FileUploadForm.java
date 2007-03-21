/*
$Revision: 1.2 $
$Date: 2007-03-21 18:39:24 $

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

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.rti.webcgh.util.SystemUtils;

/**
 * Form for uploading files.
 */
public class FileUploadForm extends ActionForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	// ==============================
	//      Attributes
	// ==============================
    
    /** File to upload. */
    private FormFile uploadFile;

	
	// ===================================
	//      Getters/setters
	// ===================================
	
	/**
	 * Get the file to upload.
	 * @return uploading file
	 */
    public final FormFile getUploadFile() {
	    return uploadFile;
    }

    /**
	 * Set the file to upload.
	 * @param uploadFile uploading file
	 */
    public final void setUploadFile(final FormFile uploadFile) {
 	    this.uploadFile = uploadFile;
    }

	
	// ===================================
	//       Overrides
	// ===================================
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void reset(final ActionMapping mapping,
			final HttpServletRequest request) {
        this.uploadFile = null;
    }
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ActionErrors validate(final ActionMapping actionMappings,
			final HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (this.uploadFile == null) {
          errors.add("uploadFile", new ActionError("invalid.field"));  
        }
        if (errors.size() > 0) {
        	errors.add("global", new ActionError("invalid.fields"));
        }
        return errors;
    }

}
