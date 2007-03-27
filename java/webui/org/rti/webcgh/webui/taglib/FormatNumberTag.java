/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/org/rti/webcgh/webui/taglib/FormatNumberTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:08 $

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
package org.rti.webcgh.webui.taglib;

import java.io.IOException;

import java.text.DecimalFormat;

import java.util.Locale;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;



/**
 * Format any numbers within body of tag
 */
public class FormatNumberTag extends BodyTagSupport {
	
	private int sigDigits = 1;
	private DecimalFormat form = 
		(DecimalFormat)DecimalFormat.getInstance(Locale.US);
	private static final Logger LOGGER = Logger.getLogger(FormatNumberTag.class);
	
	
	/**
	 * Setter for property sigDigits
	 * @param sigDigits Number of significant digits to right of decimal
	 */
	public void setSigDigits(String sigDigits) {
		try {
			this.sigDigits = Integer.parseInt(sigDigits);
		} catch (NumberFormatException e) {}
	}
	
	
	/**
	 * Getter for property sigDigits
	 * @return Number of significant digits to right of decimal
	 */
	public String getSigDigits() {
		return String.valueOf(sigDigits);
	}
	
	
	/**
	 * Evaluate tag body
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doAfterBody() throws JspException {
		BodyContent bc = getBodyContent();
		String content = bc.getString();
		String pattern = getPattern();
		form.applyPattern(pattern);
		try {
			JspWriter out = bc.getEnclosingWriter();
			int p = 0;
			while (p < content.length()) {
				
				// Advance to start of next number
				while (p < content.length()) {
					char c = content.charAt(p);
					if (Character.isDigit(c) || c == '.')
						break;
					out.print(c);
					p++;
				}
				
				if (p < content.length()) {
					
					// Find end of number
					int q = p + 1;
					while (q < content.length()) {
						char c = content.charAt(q);
						if (! Character.isDigit(c) && c != '.')
							break;
						q++;
					}
							
					// Output formatted number
					double num = Double.parseDouble(content.substring(p, q));
					out.print(form.format(num));
					
					// Advance p
					p = q;
				}
			}
		} catch (IOException e) {
		    LOGGER.error(e);
			throw new JspException("Error evaluating FormatNumber tag", e);
		}
		return SKIP_BODY;
	}
	
	
	/**
	 * Helper method to get formatting pattern
	 * @return Formatting pattern
	 */
	private String getPattern() {
		StringBuffer buff = new StringBuffer("###,###,###,###,###");
		if (sigDigits > 0) {
			buff.append(".");
			for (int i = 0; i < sigDigits; i++)
				buff.append("#");
		}
		return buff.toString();
	}
}