/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/IOUtils.java,v $
$Revision: 1.3 $
$Date: 2006-06-13 16:31:46 $

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.rti.webcgh.core.WebcghSystemException;

/**
 * I/O utility methods
 */
public class IOUtils {

	/**
	 * Get input reader to given file
	 * @param fileName File name
	 * @return Reader
	 */
	public static BufferedReader getReader(String fileName) {
		InputStream stream = getInputStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader;
	}
	
	
	/**
	 * Get input stream for specified file name
	 * @param fileName Relative file name (not full path)
	 * @return An input stream
	 */
	public static InputStream getInputStream(String fileName) {
		Thread curThread = Thread.currentThread();
		ClassLoader loader = curThread.getContextClassLoader();
		return loader.getResourceAsStream(fileName);
	}
	
	
	/**
	 * Get URL associated with given file name
	 * @param fileName Name of file
	 * @return URL to file
	 */
	public static URL getFileUrl(String fileName) {
		Thread curThread = Thread.currentThread();
		ClassLoader loader = curThread.getContextClassLoader();
		return loader.getResource(fileName);
	}
	
	
	/**
	 * Load contents of a text file into memory
	 * @param in A reader
	 * @return String containing file contents
	 * @throws IOException
	 */
	public static String loadFileContents(Reader in) throws IOException {
		StringBuffer buff = new StringBuffer();
		int nextChar = in.read();
		while (nextChar != -1) {
			buff.append((char)nextChar);
			nextChar = in.read();
		}
		return buff.toString();
	}
	
	
	/**
	 * Null tolerant method for closing a writer.  Also catches
	 * checked exceptions and rethrows as unchecked.
	 * @param writer
	 */
	public static void close(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				throw new WebcghSystemException("Error closing writer", e);
			}
		}
	}
	
	
	/**
	 * Close input stream re-throwing any exceptions as unchecked
	 * @param in Input stream
	 */
	public static void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				throw new WebcghSystemException("Error closing input stream", e);
			}
		}
	}
	
	
	/**
	 * Close input stream re-throwing any exceptions as unchecked
	 * @param out Output stream
	 */
	public static void close(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				throw new WebcghSystemException("Error closing output stream", e);
			}
		}
	}
}
