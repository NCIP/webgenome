/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/DelimitedFileReader.java,v $
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

package org.rti.webcgh.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads delimited files as matrices of Strings
 */
public class DelimitedFileReader {
	
	
	// ==============================
	//     Attributes
	// ==============================
	
	private final char delimiter;
	private final Reader in;
	private boolean endOfFile = false;
	
	
	// ===================================
	//      Constructor
	// ===================================
	
	/**
	 * Constructor
	 * @param file A file
	 * @param delimiter Delimiting character
	 * @throws FileNotFoundException if file not found
	 */
	public DelimitedFileReader(final File file, final char delimiter)
			throws FileNotFoundException {
		this.in = new FileReader(file);
		this.delimiter = delimiter;
	}
	
	
	// ====================================
	//     Public methods
	// ====================================
	
	/**
	 * Get next row as a set of string fields
	 * @return Row as set of string fields
	 * @throws IOException if file error occurs
	 */
	public String[] nextRow() throws IOException {
		String[] row = null;
		if (! this.endOfFile) {
			List fields = new ArrayList();
			boolean inQuote = false;
			StringBuffer buff = new StringBuffer();
			int charInt = this.in.read();
			if (charInt == -1)
				this.endOfFile = true;
			while (charInt != -1) {
				char c = (char)charInt;
				if (c == '\n' || c == '\r') {
					fields.add(buff.toString());
					break;
				}
				if (c == '\"')
					inQuote = ! inQuote;
				else {
					if (! inQuote) {
						if (c == this.delimiter) {
							fields.add(buff.toString());
							buff = new StringBuffer();
						} else
							buff.append(c);
					}
				}
				charInt = this.in.read();
			}
			row = new String[0];
			row = (String[])fields.toArray(row);
		}
		return row;
	}
	
	
	/**
	 * Close file for reading
	 * @throws IOException if file error occurs
	 */
	public void close() throws IOException {
		this.in.close();
	}

}
