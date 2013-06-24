/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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

package org.rti.webcgh.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties ;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * Utility class for sending emails - either as HTML or text or in a MIME Type specified.
 * 
 * @author djackman
 * 
 */
public final class Email
{

    //
    //    C O N S T A N T S
    //
    
    private static final Logger LOGGER = Logger.getLogger(Email.class);

    private static final String DEFAULT_SUBJECT = "(No subject specified)";
    private static final String DEFAULT_MESSAGE = "(No message text specified)";
    private static final String MIME_TYPE_HTML  = "text/html" ;
    private static final String MAIL_SMTP_HOST  = "mail.smtp.host" ;
    private static final String SYSADMIN_EMAIL  = "sysadmin.email" ;      
    
    //
    //    A T T R I B U T E S
    //
    
    /**
     * MIME Type of Email - set to null in Constructor and
     * not used unless explicitly set, i.e. all emails are text based by default. 
     */
    private String m_MIMEType = null ; 

    /**
     * Constructor
     */
    public Email() { }
    
    //
    //    P U B L I C    M E T H O D S
    //

    /**
     * Sends an email message as HTML content.
     * 
     * @param from - Sender of email
     * @param to - Intended recipient of email
     * @param subject - subject of email
     * @param message - content of email
     * @param attachment (Optional file attachment in String form)
     * 
     * @return boolean, true if email send succeeds, false if email send fails.
     * 
     * TODO: Attachment in String form only isn't that great, probably should make it
     * an object or consider passing in a Stream ?
     */
    public boolean sendAsHtml ( String from,
                                String to,
                                String subject,
                                String message,
                                String attachment )
    {
        this.m_MIMEType = MIME_TYPE_HTML ;
        return this.send ( from, to, subject, message, attachment ) ;
    }
    
    public boolean send ( String to,
                          String subject,
                          String message ) {
        return send ( null, to, subject, message, null, null ) ;
    }
    
    public boolean send ( String from,
                          String to,
                          String subject,
                          String message ) {
        return send ( from, to, subject, message, null, null ) ;
    }

    /**
     * Send email in the MIME form provided by the mimeType parameter.
     * Employs the send method below.
     * 
     * @param from - Sender of the email (optional - set to null to use default)
     * @param to - Intended recipient of the email
     * @param subject - Email subject (optional - set to null to use default)
     * @param message - Email body content (optional - set to null to use default)
     * @param attachment - optional attachment (in String form) - set to null to use no attachment
     * @param mimeType - MIME type, e.g. "text/html" for HTML messages ( see sendAsHTML method also, if you
     *                      intend sending emails in HTML form ) - optional - set to null to use plain text
     *                      
     * @return boolean, true if email send succeeds, false otherwise.
     */
    public boolean send (String from,
                         String to,
                         String subject,
                         String message,
                         String attachment,
                         String mimeType )
    {
        this.m_MIMEType = StringUtils.safeTrim( mimeType ) ;
        return this.send ( from, to, subject, message, attachment ) ;
    }

    /**
     * Sends an email message, intelligently providing defaults for the
     * From and Subject fields.
     *
     * @param from  A well-formed from email address (default, if null or blank string)
     * @param to    A well-formed To email address (default, if null or blank string)
     * @param message   Email subject (default, if null or blank string)
     * @param attachment Optional file attachment in java.lang.String form - which will
     * be included in the email as an attachment. If <b>null</b> value is supplied,
     * will simply not include an attachment. Note, that this field is pretty much just the path and filename
     * specification for a file which will be attached.
     *
     * @return true, if email send worked, false otherwise
     */
    public boolean send (String from,
                         String to,
                         String subject,
                         String message,
                         String attachment )
    {
        boolean emailSent = false;

        if ( to != null ) {
            //
            // Use some defaults, if their values weren't provided
            //
            if ( StringUtils.isEmpty ( from ) ) {
                from = SystemUtils.getApplicationProperty( SYSADMIN_EMAIL ) ;
            }
            if ( StringUtils.isEmpty ( subject ) ) subject = DEFAULT_SUBJECT;
            if ( StringUtils.isEmpty ( message ) ) message = DEFAULT_MESSAGE;

            try
            {
                //
                // Determine SMTP Host
                //
                String smtpHost = SystemUtils.getApplicationProperty( MAIL_SMTP_HOST ) ;
                Properties props = new Properties() ;
                props.put( MAIL_SMTP_HOST, smtpHost ) ;
                
                LOGGER.debug ( "to [" + to + "]") ;
                LOGGER.debug ( "from [" + from + "]" ) ;
                LOGGER.debug ( "host [" + smtpHost + "]") ;
                LOGGER.debug ( "subject [" + subject + "]" ) ; 
    
                //
                // Get Session needed for MimeMessage
                //
                Session session = Session.getDefaultInstance ( props, null ) ;
                
                //
                // Create MimeMessage
                //
                MimeMessage mimeMessage = new MimeMessage( session );
                mimeMessage.setFrom(new InternetAddress( from ));  // From
                ArrayList toAddresses = getAddresses ( to ) ;      // To
                for ( int i = 0 ; i < toAddresses.size() ; i++ )
                    mimeMessage.addRecipient( javax.mail.Message.RecipientType.TO,
                                          new InternetAddress( (String) toAddresses.get( i )));
                mimeMessage.setSubject( subject );                 // Subject
    
                MimeBodyPart messageBodyPart = new MimeBodyPart(); // Message
                if ( this.m_MIMEType == null )
                    // Send email in plain text form
                    messageBodyPart.setText( message );
                else
                    // Send email in MIME Type specified
                    messageBodyPart.setContent( message,
                                                this.m_MIMEType ) ;
    
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
    
                //
                // Handle optional attachment
                //
                if ( ! StringUtils.isEmpty ( attachment ) )
                {
                    messageBodyPart = new MimeBodyPart();
                    javax.activation.DataSource source = new FileDataSource(attachment);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(attachment);
                    multipart.addBodyPart(messageBodyPart);
                }
    
                // Put parts into message
                mimeMessage.setContent(multipart);
                mimeMessage.setHeader("X-Mailer", "LOTONtechEmail");
                mimeMessage.setSentDate(new Date());

    
                // Send the message
                Transport.send( mimeMessage );
                emailSent = true ;
            }
            catch ( Exception e ) {
                LOGGER.error( "Email Send failed. From: " + from + " To: " + to + " Subject: " + subject +
                              "Message [" + message + "] Exception caught: " + e.getMessage() ) ; 
            }
        }
        else {
            
        }
        return emailSent ;
    }
    
    //
    //    P R I V A T E    M E T H O D S
    //
    
    /**
     * Take a String and separate any email addresses into separate ArrayList entries
     * so they can be individually added as recipients for an email.
     * 
     * TODO: I think the Java Mail API might handle a list of emails automatically, but
     * I've never taken the time to find out. If you have the time, test this and eliminate
     * this method.  
     */
    private ArrayList getAddresses ( String addressValue ) {
       ArrayList addresses = new ArrayList() ;
       
       if ( addressValue != null ) {
           String separator = null ;
           if ( addressValue.indexOf( ";" ) != -1 )
               separator = ";" ;
           else if ( addressValue.indexOf( "," ) != -1 ) 
               separator = "," ;
           
           if ( separator == null )
               // its just a single address
               addresses.add( addressValue ) ;
           else {
               // process multiple addresses separator by 'separator' value
               StringTokenizer st = new StringTokenizer ( addressValue, separator ) ;
               while ( st.hasMoreTokens() ) {
                   String singleEmailAddress = st.nextToken() ;
                   addresses.add( singleEmailAddress ) ;
               }
           }
       }
       
       return addresses ;
    }
}

