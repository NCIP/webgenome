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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;
import org.apache.batik.apps.rasterizer.DestinationType;

/**
 * Form containing all of the settings a user wants for saving their plot.
 * TODO: Probably should extend a webgenome Action Form - which might
 * provide a common ActionForm across the entire project/application.
 * This could potentially contain common convenience routines, such
 * as empty field checking and converstion routines.
 * 
 */
public class SaveAsForm extends ActionForm {
	
	private static final String INVALID_FIELD = "invalid.field" ; // TODO, this should exist somewhere more common
	private static final double DEFAULT_QUALITY = 0.99 ;
    public static String ASIS_STRING = "asis" ;
    public static int    ASIS = -1 ;
    public static String RELATIVE_STRING = "relative" ;
    public static int    RELATIVE = 0 ;
	
    private String svgDOM ;
    private float  height = ASIS ; // don't change - keep dimensions as they are currently
    private float  width =  ASIS ;
    private double quality ;
    private String imgType ; // deliberately not initialised
    
    //
    //    D E F I N E    L O O K U P   V A L U E S
    //
    // I'm actually not sure what the 'best practice' is for Struts html:select pulldown prepopulation
    // but I like my options in the form, as opposed to in the jsp page. If they were in a JSP, someone would
    // be tempted to change them, perhaps without checking right here in this code - least by having
    // them here, I've forced you to come and take a gander at the code.
    //
    private static ArrayList<LabelValueBean> imgTypes = new ArrayList<LabelValueBean>();
    private static ArrayList<LabelValueBean> heights = new ArrayList<LabelValueBean>();
    private static ArrayList<LabelValueBean> widths  = new ArrayList<LabelValueBean>();
    private static ArrayList<LabelValueBean> qualities = new ArrayList<LabelValueBean>();
    static {
    	imgTypes.add ( new LabelValueBean ( DestinationType.JPEG_STR, DestinationType.JPEG_STR ) ) ;
    	imgTypes.add ( new LabelValueBean ( DestinationType.PNG_STR,  DestinationType.PNG_STR  ) ) ;

        heights.add ( new LabelValueBean ( "leave asis", ASIS_STRING ) ) ;
        heights.add ( new LabelValueBean ( "set relative to width", RELATIVE_STRING ) ) ;
    	heights.add ( new LabelValueBean (  "100",  "100" ) ) ;
        heights.add ( new LabelValueBean (  "150",  "150" ) ) ;
        heights.add ( new LabelValueBean (  "175",  "175" ) ) ;
        heights.add ( new LabelValueBean (  "200",  "200" ) ) ;
        heights.add ( new LabelValueBean (  "225",  "225" ) ) ;
        heights.add ( new LabelValueBean (  "256",  "256" ) ) ;
        heights.add ( new LabelValueBean (  "512",  "512" ) ) ;
        heights.add ( new LabelValueBean (  "768",  "768" ) ) ;
        heights.add ( new LabelValueBean ( "1024", "1024" ) ) ; // anything more than 1024 crashes my tomcat!

        widths.add ( new LabelValueBean ( "leave asis", ASIS_STRING ) ) ;
        widths.add ( new LabelValueBean ( "set relative to height", RELATIVE_STRING ) ) ;
        widths.add ( new LabelValueBean (  "100",  "100" ) ) ;
        widths.add ( new LabelValueBean (  "150",  "150" ) ) ;
        widths.add ( new LabelValueBean (  "175",  "175" ) ) ;
        widths.add ( new LabelValueBean (  "200",  "200" ) ) ;
        widths.add ( new LabelValueBean (  "225",  "225" ) ) ;
        widths.add ( new LabelValueBean (  "256",  "256" ) ) ;
        widths.add ( new LabelValueBean (  "512",  "512" ) ) ;
        widths.add ( new LabelValueBean (  "768",  "768" ) ) ;
        widths.add ( new LabelValueBean ( "1024", "1024" ) ) ;
        
        qualities.add ( new LabelValueBean (  "10%", "0.10" ) ) ;
        qualities.add ( new LabelValueBean (  "20%", "0.20" ) ) ;
        qualities.add ( new LabelValueBean (  "30%", "0.30" ) ) ;
        qualities.add ( new LabelValueBean (  "40%", "0.40" ) ) ;
        qualities.add ( new LabelValueBean (  "50%", "0.50" ) ) ;
        qualities.add ( new LabelValueBean (  "60%", "0.60" ) ) ;
        qualities.add ( new LabelValueBean (  "70%", "0.70" ) ) ;
        qualities.add ( new LabelValueBean (  "80%", "0.80" ) ) ;
        qualities.add ( new LabelValueBean (  "90%", "0.90" ) ) ;
        qualities.add ( new LabelValueBean ( "100%", "0.99" ) ) ;
    }
    
    //
    //    S E L E C T    R E L A T E D    G E T T E R S
    //
    
    /**
     * Getter for the collection of image types. Nota bena: Stuts barfs when you
     * make this getter static, like you might be tempted to! The:
     * Struts markup in the jsp, e.g. <pre>
         <html:select property="imgType">
            <html:optionsCollection property="imgTypes"/>
        </html:select>
        </pre> would be suitable for using in conjunction with this getter.
     * @return ArrayList of image types
     */
    public ArrayList getImgTypes () { return imgTypes ; }
    public ArrayList getHeights()   { return heights ; }
    public ArrayList getWidths()    { return widths ; }
    public ArrayList getQualities() { return qualities ; }
    
    //
    //    G E T T E R S    &    S E T T E R S
    //
    
    public String getSvgDOM ( ) {
    	return this.svgDOM ;
    }
    
    public void setSvgDOM( String svgDOM ) {
    	this.svgDOM = svgDOM ;
    }
    
    public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
    
    
    public void setHeight ( String height ) {
        this.height = setDimension ( height ) ;
    }

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

	public float getWidth() {
		return this.width ;
	}

	public void setWidth (float width ) {
		this.width= width;
	}

	/**
     * Reset form fields
     * @param mapping Routing information
     * @param request Servlet request object
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        quality = DEFAULT_QUALITY ;
        imgType = null ;
        height = ASIS ;
        width = ASIS ;
    }
        
    /**
     * Validate form fields
     * @param mapping Routing information
     * @param request Servlet request object
     * @return Errors
     */
    public ActionErrors validate
    (
        ActionMapping mapping, HttpServletRequest request
    ) {
        ActionErrors errors= new ActionErrors();
        
		if ( emptyField ( getSvgDOM () ) )   
			errors.add("svgDOM", new ActionError( INVALID_FIELD ));
		
		if ( getQuality() != DEFAULT_QUALITY &&
		     ( getQuality() <= 0 || getQuality() >= 1 ) )
			errors.add( "quality", new ActionError ( INVALID_FIELD ) ) ;
		
		// Indicate overall validation failure
        if (errors.size() > 0 )
        	errors.add("global", new ActionError( INVALID_FIELD ));
        return errors;
    }
    
    /**
     * Is field empty?
     * @param value Field value
     * @return T/F depending on if field is empty
     * TODO: this should really belong some other place, because we've repeated this method
     * lots of other times.
     */
    private boolean emptyField(String value) {
    	return value == null || value.length() < 1;
    }
    
    private int setDimension ( String dimStr ) {
        int dim = 0 ;
        if ( ASIS_STRING.equals ( dimStr ) ) {
            dim = ASIS ;
        }
        else if ( RELATIVE_STRING.equals( ( dimStr ) )) {
            dim = RELATIVE ;
        }
    
        return dim ;
    }
}
