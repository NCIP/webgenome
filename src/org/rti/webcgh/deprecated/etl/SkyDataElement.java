/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/etl/SkyDataElement.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 21:04:54 $

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

package org.rti.webcgh.deprecated.etl;

import java.io.StreamTokenizer;

import org.rti.webcgh.util.StringUtils;

/**
 * 
 */
public class SkyDataElement implements EsiDataElement {
	
	// =============================================
	//     Static variables
	// =============================================
	
	private static final int CONTACT_TOKEN_NUM = 1;
	
	// =============================================
	//     State variables
	// =============================================
	
	private int tokenNumber = 0;
	
	
	// =============================================
	//   Attributes with accessors and mutators
	// =============================================
	
	private String contact = null;
	

	/**
	 * @return Returns the contact.
	 */
	public String getContact() {
		return contact;
	}
	
	/**
	 * @param contact The contact to set.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	// ==========================================
	//        Constructors
	// ==========================================
	
	/**
	 * Constructor
	 */
	public SkyDataElement() {}
	
	
	/**
	 * Constructor
	 * @param contact Contact
	 */
	public SkyDataElement(String contact) {
		this.contact = contact;
	}
	
	
	// =================================================
	//      Methods from EsiDataElement interface
	// =================================================
	
	/**
	 * Does state of stream correspond to beginning of element?
	 * @param tok Stream tokenizer
	 * @return T/F
	 */
	public boolean beginningOfElement(StreamTokenizer tok) {
		boolean beginning = false;
		if (tok.ttype == StreamTokenizer.TT_WORD)
			if ("SkyData".equals(tok.sval))
				beginning = true;
		return beginning;
	}
	
	
	/**
	 * Does end of stream correspond to end of element?
	 * @param tok Stream tokenizer
	 * @return T/F
	 */
	public boolean endOfElement(StreamTokenizer tok) {
		return 
			tok.ttype == StreamTokenizer.TT_EOL ||
			tok.ttype == StreamTokenizer.TT_EOF;
	}
	
	
	/**
	 * Callback method which should be invoked by clients
	 * after each token is obtained from the given tokenizer
	 * between tokens that cause this.beginningOfElement
	 * and this.endOfElement to evaluate to true, respectively.
	 * @param tok Stream tokenizer
	 */
	public void handleStreamParsingEvent(StreamTokenizer tok) {
		if (tok.ttype == StreamTokenizer.TT_WORD) {
			if (this.tokenNumber++ == CONTACT_TOKEN_NUM)
				this.contact = StringUtils.removeQuotes(tok.sval, '"');
		}
	}
	
	
	/**
	 * Deep value-based copy method
	 * @return A cloned object
	 */
	public EsiDataElement deepCopy() {
		return new SkyDataElement(this.contact);
	}
}
