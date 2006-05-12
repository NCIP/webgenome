/*

$Source$
$Revision$
$Date$

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
"Research Triangle Institute", and "RTI" must not be used to endorse or promote 
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

package org.rti.webcgh.deprecated;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.util.SystemUtils;
import org.rti.webcgh.util.XmlUtils;

/**
 * Utilities for unit testing renderers
 */
public class RendererTesterUtils {
	

	/**
	 * Load "shell" document (i.e. empty XML document)
	 * @return XML document
	 * @throws WebcghSystemException
	 */
	public static Document newTestDocument() throws WebcghSystemException {
		return XmlUtils.loadDocument("testShell.svg", false);
	}
	
	
	/**
	 * Load "shell" document (i.e. empty XML document)
	 * @param fname Name of file
	 * @return XML document
	 * @throws WebcghSystemException
	 */
	public static Document newTestDocument(String fname) throws WebcghSystemException {
		return XmlUtils.loadDocument(fname, false);
	}
	
	
	/**
	 * Get insertion point for adding test elements to document
	 * @param doc XML document
	 * @return Insertion point for test element
	 */
	public static Element getInsertionPoint(Document doc) {
		return doc.getElementById("insertPoint");
	}
	
	
	/**
	 * Get insertion point for adding test elements to document
	 * @param doc XML document
	 * @param tagId Id of tag representing insertion point
	 * @return Insertion point for test element
	 */
	public static Element getInsertionPoint(Document doc, String tagId) {
		return doc.getElementById(tagId);
	}
	
	
	/**
	 * Write document to file
	 * @param doc A document
	 * @param docName Document file name
	 * @throws WebcghSystemException
	 */
	public static void writeDocument(Document doc, String docName) 
		throws WebcghSystemException {
			
		// Get test directory name
		String testDirName = SystemUtils.getApplicationProperty("test.dir");
		if (testDirName == null)
			throw new WebcghSystemException("Cannot find test directory " +
				"in system properties file.  Property name is 'test.dir'");
		
		// Get test directory handle
		File testDir = new File(testDirName);
		if (testDir.exists() && ! testDir.isDirectory())
			throw new WebcghSystemException("File exists" +				" with same name as test directory");
				
		// Create test directory if necessary
		if (! testDir.exists())
			if (! testDir.mkdir())
				throw new WebcghSystemException("Error creating " +
					"test directory '" + testDirName + "'");
				
		// Output document to test directory
		String testFileName = testDir.getAbsolutePath() + "\\" + docName;
		try {
			FileOutputStream out = new FileOutputStream(testFileName);
			XmlUtils.forwardDocument(doc, out);
		} catch (FileNotFoundException e) {
			throw new WebcghSystemException("Error creating output file");
		}
	}
}
