/*

$Source$
$Revision$
$Date$

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

package org.rti.webcgh.webui.plot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;

import java.io.StringReader;

import org.rti.webcgh.webui.plot.SaveAsForm ;
import org.apache.batik.transcoder.image.*;
import org.apache.batik.transcoder.*;

import org.apache.batik.apps.rasterizer.DestinationType;


/**
 * Performs actions associated with Plot > Save As user options.
 */
public class SaveAsAction extends Action {
    
    private static final String DEFAULT_FILENAME = "plot" ;
	
	public ActionForward execute
	(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		
		// 1. get form
		SaveAsForm sform = (SaveAsForm) form ;
		
		// 2. check if we have all of the information
		if ( ! emptyField ( sform.getImgType() ) )
		{
			//  3. Create an SVG Document
            SVGAbstractTranscoder transcoder = getTranscoder ( sform ) ;
        	
            response.setContentType( "application/octet-stream" );
            // user can change this in the browser's save as dialogue
            response.setHeader( "Content-disposition",
                                "attachment;filename=" + DEFAULT_FILENAME  );
        	TranscoderInput input =
            	new TranscoderInput( new StringReader( sform.getSvgDOM() ) );
        	
        	TranscoderOutput output =
            	new TranscoderOutput(response.getOutputStream());
        	
        	transcoder.transcode(input, output);
            sform.reset( mapping, request ) ;
            return null ;
		}
        else
            return mapping.findForward("success");	
	}
	
    /**
     * Is field empty?
     * @param value Field value
     * @return T/F depending on if field is empty
     */
    private boolean emptyField(String value) {
    	return value == null || value.length() < 1;
    }
    
    /**
     * Quick abstract factory for getting a Transcoder based on the image type
     * the user selected. While we're here, we'll set the image quality and a few other
     * things.
     * @param sform
     * @return Transcoder of the appropriate flavor.
     */
    private SVGAbstractTranscoder getTranscoder ( SaveAsForm sform ) {
        SVGAbstractTranscoder transcoder = null ;
        
        // First of all, let's make the kind of transcoder we want.
        if ( DestinationType.JPEG_STR.equals( sform.getImgType() ) ) {
            transcoder = new JPEGTranscoder () ;
            transcoder.addTranscodingHint( JPEGTranscoder.KEY_QUALITY, new Float ( sform.getQuality() )) ;
        }
        else if ( DestinationType.PNG_STR.equals ( sform.getImgType() ) ) {
            transcoder = new PNGTranscoder () ;
        }
        
        // Now set either its width or height
        if ( sform.getWidth() != SaveAsForm.ASIS && sform.getWidth() != SaveAsForm.RELATIVE ) {
            transcoder.addTranscodingHint( SVGAbstractTranscoder.KEY_WIDTH, new Float( sform.getWidth() ) ) ;
        }
        if ( sform.getHeight() != SaveAsForm.ASIS && sform.getHeight() != SaveAsForm.RELATIVE ) {
            transcoder.addTranscodingHint( SVGAbstractTranscoder.KEY_HEIGHT, new Float( sform.getHeight() ) ) ;
        }
        
        return transcoder ;
    }
}
