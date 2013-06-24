/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/FormatNumberTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/
package org.rti.webgenome.webui.taglib;

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