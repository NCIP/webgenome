/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/etl/SkyCaseElement.java,v $
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

import org.apache.log4j.Logger;
import org.rti.webcgh.util.CollectionUtils;
import org.rti.webcgh.util.StringUtils;

/**
 * SkyCase data element from SKY/M-FISH&CGH data '.esi' data files.
 */
public class SkyCaseElement implements EsiDataElement {
	
	// ========================================
	//        Static variables
	// ========================================
	
    private static final int STATE_INIT = 0;
    private static final int STATE_IN_NAME = 1;
    private static final int STATE_IN_DESCRIPTION = 2;
	private static final Logger LOGGER = Logger.getLogger(SkyCaseElement.class);
	private static final String[] END_OF_ELEMENT_TOKENS = new String[] {"diagnosis", "site", "casename"};
	private static final char[] PERMISSABLE_CHARS = {'(', ')', '/', '_'};
    
    
    // ===========================================
    //   Attributes with accessors and mutators
    // ===========================================
    
	private int parseState = STATE_INIT;
    private StringBuffer description = new StringBuffer();
    private StringBuffer name = new StringBuffer();
    private boolean firstNameQuoteEncountered = false;
    private boolean secondNameQuoteEncountered = false;
    private int nameTokenNumber = 0;
    
    
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name.toString().trim();
	}
	
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = new StringBuffer(name);
	}
	
	
    /**
     * Set the description
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = new StringBuffer(description);
    }
    
    
    /**
     * Get the description 
     * @return Description
     */
    public String getDescription() {
        return this.description.toString();
    }
    
    
    // =====================================================
    //       Constructors
    // =====================================================
    
    
    /**
     * Constructor
     */
    public SkyCaseElement() {}
    
    
    /**
     * Constructor
     * @param description Description
     */
    public SkyCaseElement(String description) {
        this.description = new StringBuffer(description);
    }
    
    
    // ==================================================
    //      Methods in SkyDataElement interface
    // ==================================================
    
	/**
	 * Does state of stream correspond to beginning of element?
	 * @param tok Stream tokenizer
	 * @return T/F
	 */
	public boolean beginningOfElement(StreamTokenizer tok) {
		boolean beginning = false;
		if (tok.ttype == StreamTokenizer.TT_WORD)
			if ("SkyCase".equals(tok.sval))
				beginning = true;
		return beginning;
	}
	
	
	/**
	 * Does end of stream correspond to end of element?
	 * @param tok Stream tokenizer
	 * @return T/F
	 */
	public boolean endOfElement(StreamTokenizer tok) {
	    boolean end = false;
	    if (tok.ttype == StreamTokenizer.TT_WORD)
	        for (int i = 0; i < END_OF_ELEMENT_TOKENS.length && ! end; i++)
	            if (END_OF_ELEMENT_TOKENS[i].equals(tok.sval))
	                end = true;
	    return end;
	}
	
	
	/**
	 * Callback method which should be invoked by clients
	 * after each token is obtained from the given tokenizer
	 * between tokens that cause this.beginningOfElement
	 * and this.endOfElement to evaluate to true, respectively.
	 * @param tok Stream tokenizer
	 */
	public void handleStreamParsingEvent(StreamTokenizer tok) {
	    this.setParseState(tok);
	    if (tok.ttype == StreamTokenizer.TT_NUMBER || tok.ttype == StreamTokenizer.TT_WORD ||
	            this.permissableChar(tok.ttype)) {
	    	if (this.parseState == STATE_IN_NAME) {
	    	    if (this.nameTokenNumber++ > 0)
	    	        this.name.append(" ");
	    	    String token = "";
	    		if (tok.ttype == StreamTokenizer.TT_WORD)
	    			token = StringUtils.removeQuotes(tok.sval, '"');
	    		else if (tok.ttype == StreamTokenizer.TT_NUMBER)
	    			token = StringUtils.removeQuotes(tok.nval + "", '"');
	    		else if (tok.ttype >= 0)
	    		    token = String.valueOf((char)tok.ttype);
	    		this.name.append(token);
	    	} else if (this.parseState == STATE_IN_DESCRIPTION){
			    if (this.description.length() > 0)
			        this.description.append(" ");
			    if (tok.ttype == StreamTokenizer.TT_NUMBER)
			        this.description.append(tok.nval);
			    else if (tok.ttype == StreamTokenizer.TT_WORD)
			        this.description.append(tok.sval);
			    else if (tok.ttype >= 0)
			        this.description.append(tok.ttype);
	    	}
	    }
	}
	
	
	/**
	 * Deep value-based copy method
	 * @return A cloned object
	 */
	public EsiDataElement deepCopy() {
	    return new SkyCaseElement(this.getDescription());
	}
	
	
	// ==================================================================
	//         Private methods
	// ==================================================================
	
	
	private void setParseState(StreamTokenizer tok) {
	    if (tok.ttype == 34) {
	        if (this.firstNameQuoteEncountered == false) {
	            this.firstNameQuoteEncountered = true;
	            this.parseState = STATE_IN_NAME;
	        } else if (this.secondNameQuoteEncountered == false) {
	            this.secondNameQuoteEncountered = true;
	            this.parseState = STATE_IN_DESCRIPTION;
	        }
	    }
	}
	
	
	private boolean permissableChar(int charCode) {
	    char c = (char)charCode;
	    return CollectionUtils.contains(PERMISSABLE_CHARS, c);
	}

}
