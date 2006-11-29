/*
$Revision$
$Date$

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

package org.rti.webcgh.webui.taglib;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.rti.webcgh.util.Email;
import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.webui.util.Attribute;

/**
 * JSP Tag to control the sending of an Exception email to configured recipients
 * and also present a suitable display to users.
 *  
 * @author djackman
 *
 */
public class ErrorEmailTag extends TagSupport {
    
    /** Serlialized version ID. */
    private static final long serialVersionUID = 
        SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    private static final Logger LOGGER = Logger.getLogger(ErrorEmailTag.class);
    
    private String hideMessage = null ;
    private String exceptionMsg = null ;
    
    public String getHideMessage ( ) { return this.hideMessage ; }
    public boolean hideMessage ( ) {
        return hideMessage != null ;
    }
    public void    setHideMessage ( String hideMessage ) {
        if ( "true".equalsIgnoreCase( hideMessage ) )
                this.hideMessage = hideMessage;
    }
    
    public String getExceptionMsg ( ) { return this.exceptionMsg ; }
    public void   setExceptionMsg ( String exceptionMsg ) {
        this.exceptionMsg = exceptionMsg ;
    }
    
    /**
     * Start tag
     * @return Action to perform after processing
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        
        Exception ex = (Exception)pageContext.findAttribute(Attribute.EXCEPTION);
        
        if ( ex != null || this.getExceptionMsg() != null ) {
            
            // Get email subject and email recipients
            String subject = SystemUtils.getApplicationProperty( "error.page.email.subject" ) ;
            String to = SystemUtils.getApplicationProperty ( "error.email.distribution.list" ) ;
            
            // Collate Message
            String message = "The following Exception was caught by webGenome:\n\n" ;
            if ( this.getExceptionMsg() != null )
                message += this.getExceptionMsg() ;
            else
                message += ex.getMessage() ;
            
            // Get the Stack Trace, if its available
            if ( ex.getStackTrace() != null && ex.getStackTrace().length > 0 ) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                message += "\n\nStackTrace follows:\n" +
                           sw.toString() ;
            }

            message += "\n" ;
        
            // Dispatch the Exception Email
            Email email = new Email() ;
            boolean emailed = email.send ( to, subject, message ) ;
    
            // Display the results, if no directive to hide this message has
            // been given.
            if ( ! hideMessage() ) {
                //
                //    Show the problem
                //
                PrintWriter out = new PrintWriter(pageContext.getOut());
                
                
                if ( emailed ) {
                    out.println( "<p>The error was emailed to the System Administrator who will " +
                                 "investigate the cause of them problem and rectify it.</p>" ) ;
                }
                else {
                    out.println( "<p>The error was unable to be sent to the System Administrator. " +
                                 "Please help us to rectify the problem by cutting and pasting " + 
                                 "the error message and emailing it to " +
                                 "<a href=\"mailto:" + to + "\">" + to + "</a></p>" ) ;
                }
                out.println ( "<p>We apologize for the inconvenience.</p>" ) ;
                out.flush();
            }
        }

        return SKIP_BODY;
    }
}
