/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/etl/CGHFragElement.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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

package org.rti.webcgh.etl;

import java.io.StreamTokenizer;

import org.apache.log4j.Logger;
import org.rti.webcgh.util.CollectionUtils;

/**
 * 
 */
public class CGHFragElement implements EsiDataElement {
	
	
	// ============================================
	//        Static variables
	// ============================================
	
	private static final int CHROMOSOME_TOKEN_NUM = 0;
	private static final int VALUE_TOKEN_NUM = 4;
	private static final int START_TOKEN_NUM = 5;
	private static final int END_TOKEN_NUM = 6;
	private static final Logger LOGGER = Logger.getLogger(CGHFragElement.class);
	private static final int[] NUMERIC_TOKENS = {0, 1, 5, 6};
	private static final int[] STRING_TOKENS = {0, 2, 3, 4};
	
	
	// =================================================
	//         Attributes with accessors and mutators
	// =================================================
	
	private int tokenNum = 0;
	private int chromosome = 0;
	private String value = null;
	private double start = Double.NaN;
	private double end = Double.NaN;
	
	
	/**
	 * @return Returns the chromosome.
	 */
	public int getChromosome() {
		return chromosome;
	}
	
	
	/**
	 * @param chromosome The chromosome to set.
	 */
	public void setChromosome(int chromosome) {
		this.chromosome = chromosome;
	}
	
	
	/**
	 * @return Returns the end.
	 */
	public double getEnd() {
		return end;
	}
	
	
	/**
	 * @param end The end to set.
	 */
	public void setEnd(double end) {
		this.end = end;
	}
	
	
	/**
	 * @return Returns the start.
	 */
	public double getStart() {
		return start;
	}
	
	
	/**
	 * @param start The start to set.
	 */
	public void setStart(double start) {
		this.start = start;
	}
	
	
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	
	
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	// ====================================================
	//        Methods in EsiDataElement interface
	// ====================================================
	
	
	/**
	 * Does state of stream correspond to beginning of element?
	 * @param tok Stream tokenizer
	 * @return T/F
	 */
	public boolean beginningOfElement(StreamTokenizer tok) {
		boolean beginning = false;
		if (tok.ttype == StreamTokenizer.TT_WORD)
			if ("CGHFrag".equals(tok.sval))
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
		if (tok.ttype == StreamTokenizer.TT_NUMBER || 
			tok.ttype == StreamTokenizer.TT_WORD) {
			if (this.tokenNum == CHROMOSOME_TOKEN_NUM) {
				if (tok.ttype == StreamTokenizer.TT_NUMBER)
					this.chromosome = (int)tok.nval;
				else if (tok.ttype == StreamTokenizer.TT_WORD) {
	                String chromName = tok.sval;
	                if ("X".equalsIgnoreCase(chromName))
	                    this.chromosome = 23;
	                else if ("Y".equalsIgnoreCase(chromName))
	                    this.chromosome = 24;
	                else
	                    LOGGER.warn("Chromosome '" + chromName + "' not recognized");
	            }
			} else if (this.tokenNum == VALUE_TOKEN_NUM) {
				if (tok.ttype == StreamTokenizer.TT_WORD)
					this.value = tok.sval;
				else
					LOGGER.warn("Expecting a word: '" + tok.sval + "'");
			} else if (this.tokenNum == START_TOKEN_NUM) {
				if (tok.ttype == StreamTokenizer.TT_NUMBER)
					this.start = tok.nval;
				else
					LOGGER.warn("Expecting a number: '" + tok.sval + "'");
			} else if (this.tokenNum == END_TOKEN_NUM) {
				if (tok.ttype == StreamTokenizer.TT_NUMBER)
					this.end = tok.nval;
				else
					LOGGER.warn("Expecting a number: '" + tok.sval + "'");
			} 
			this.incrementTokenNum(tok);
		}
	}
	
	
	/**
	 * Deep value-based copy method
	 * @return A cloned object
	 */
	public EsiDataElement deepCopy() {
		return new CGHFragElement(this.chromosome, this.value, this.start, this.end);
	}
	
	
	// =============================================
	//         Constructors
	// =============================================
	
	
	/**
	 * Constructor
	 */
	public CGHFragElement() {}
	
	
	/**
	 * Constructor
	 * @param chromosome Chromosome number
	 * @param value Value
	 * @param start Fractional start point
	 * @param end Fractional end point
	 */
	public CGHFragElement(int chromosome, String value, double start, double end) {
		this.chromosome = chromosome;
		this.value = value;
		this.start = start;
		this.end = end;
	}
	
	
	// ================================================
	//     Private methods
	// ================================================
	
	private void incrementTokenNum(StreamTokenizer tok) {
	    if (tok.ttype == StreamTokenizer.TT_NUMBER) {
	        if (CollectionUtils.contains(NUMERIC_TOKENS, this.tokenNum))
	            this.tokenNum++;
	    } else if (tok.ttype == StreamTokenizer.TT_WORD) {
	        if (CollectionUtils.contains(STRING_TOKENS, this.tokenNum))
	            this.tokenNum++;
	    }
	}

}
