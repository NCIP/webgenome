/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/etl/EsiParser.java,v $
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;

/**
 * Event-driver parser for SKY/M-FISH&CGH '.esi' files.
 */
public class EsiParser {
	
	private final EsiDataElement[] templateElements;
	
	
	/**
	 * Constructor
	 *
	 */
	public EsiParser() {
		templateElements = new EsiDataElement[]{
			new SkyDataElement(), new SkyCaseElement(), 
			new CGHSampleElement(), new CGHBinElement(),
			new CGHBinFragElement(), new CGHFragElement()
		};
	}
	
	
	// ============================================
	//      Public methods
	// ============================================
	
	
	/**
	 * Parse an '.esi' file.
	 * @param file File to parse
	 * @param handler Event handler
	 * @throws EsiParseException if a parse error occurs
	 */
	public void parse(File file, EsiParserEventHandler handler) 
		throws EsiParseException {
	    Reader reader = null;
	    try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e1) {
            throw new IllegalArgumentException("File '" + file.getName() +
			"' is not a valid file");
        }
        this.parse(reader, handler);
	}
		
	
	/**
	 * Parse an '.esi' file.
	 * @param reader File reader
	 * @param handler Event handler
	 * @throws EsiParseException if a parse error occurs
	 */
	public void parse(Reader reader, EsiParserEventHandler handler)
		throws EsiParseException {
		try {
			EsiDataElement currentElement = null;
			StreamTokenizer stream = new StreamTokenizer(reader);
			stream.eolIsSignificant(true);
			stream.ordinaryChar('"');
			stream.ordinaryChar('(');
			stream.ordinaryChar(')');
			stream.ordinaryChar('/');
			int code = stream.nextToken();
			while (code != StreamTokenizer.TT_EOF) {
				if (currentElement != null) {
					if (currentElement.endOfElement(stream)) {
						handler.onNextElement(currentElement);
						currentElement = null;
					} else
						currentElement.handleStreamParsingEvent(stream);
				}
				if (currentElement == null)
					currentElement = this.newElement(stream);
				code = stream.nextToken();
			}
			handler.onEndOfDocument();
		} catch (Exception e) {
			throw new EsiParseException("Parsing error", e);
		}
	}
	
	
	// =====================================
	//       Private methods
	// =====================================
	
	private EsiDataElement newElement(StreamTokenizer stream) {
		EsiDataElement element = null;
		for (int i = 0; i < this.templateElements.length && element == null; i++) {
			EsiDataElement template = this.templateElements[i];
			if (template.beginningOfElement(stream))
				element = template.deepCopy();
		}
		return element;
	}

}
