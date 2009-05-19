/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/XmlUtils.java,v $
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

package org.rti.webgenome.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.rti.webgenome.core.SaxErrorHandler;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Convenience methods for handling XML
 */
public class XmlUtils {

	/**
	 * Load XML document from a file
	 * @param fname Name of file
	 * @param validate Validate document?
	 * @return
	 * @throws WebGenomeSystemException
	 */
	public static Document loadDocument(String fname, boolean validate) 
		throws WebGenomeSystemException {
		Document doc = null;
		try {
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			fac.setValidating(validate);
			DocumentBuilder builder = fac.newDocumentBuilder();
			if (validate)
				builder.setErrorHandler(new SaxErrorHandler());
			Thread curThread = Thread.currentThread();
			ClassLoader loader = curThread.getContextClassLoader();
			
			// *** This code commented out because it will not
			// *** work properly when document is being validated
			// *** and DTD or schema has relative path
	//		InputStream in = loader.getResourceAsStream(fname);
	//		doc = builder.parse(in);
	
			// *** The following code works for validating where
			// *** the DTD or schema has a relative or absolute
			// *** path
			URL url = loader.getResource(fname);
			String path = url.toString();
			doc = builder.parse(path);
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error loading XML document from file", e);
		}
		
		return doc;
	}
	/**
	 * Create new XML document de novo
	 * @return XML document
	 * @throws WebGenomeSystemException
	 */
	public static Document createDocument() throws WebGenomeSystemException {
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error creating XML document object", e);
		}
		return doc; 
	}
	/**
	 * Create XML document from String encoding
	 * @param encoding String version of XML document
	 * @return XML document
	 * @throws WebGenomeSystemException
	 */
	public static Document createDocument(String encoding) 
		throws WebGenomeSystemException {
		Document doc = null;
		try {
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fac.newDocumentBuilder();
			Thread curThread = Thread.currentThread();
			StringReader in = new StringReader(encoding);
			InputSource source = new InputSource(in);
			doc = builder.parse(source);
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error creating new XML document object", e);
		}
		return doc;
	}
	/**
	 * Create XML document from markup at given URL
	 * @param url URL
	 * @return XML document
	 * @throws WebGenomeSystemException
	 */
	public static Document createDocument(URL url) 
		throws WebGenomeSystemException {
		Document doc = null;
		try {
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = in.readLine();
			StringBuffer buff = new StringBuffer();
			while (line != null) {
				buff.append(line);
				line = in.readLine();
			}
			doc = createDocument(buff.toString());
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error creating XML document", e);
		}
		return doc;
	}
	/**
	 * Forward an XML document to a stream
	 * @param doc XML document
	 * @param out Stream
	 * @throws WebGenomeSystemException
	 */
	public static void forwardDocument(Document doc, OutputStream out) 
		throws WebGenomeSystemException {
		StreamResult res = new StreamResult(out);
		forwardDocument(doc, res);
	}
	/**
	 * Forward an XML document to a stream
	 * @param docName Name of XML document
	 * @param out Stream
	 * @param validate Validate document?
	 * @throws WebGenomeSystemException
	 */
	public static void forwardDocument
	(
		String docName, OutputStream out, boolean validate
	) 
		throws WebGenomeSystemException {
		Document doc = null;
		doc = loadDocument(docName, validate);
		forwardDocument(doc, out);
	}
	/**
	 * Forward an XML document to a writer
	 * @param doc XML document
	 * @param out Stream
	 * @throws WebGenomeSystemException
	 */
	public static void forwardDocument(Document doc, Writer out) 
		throws WebGenomeSystemException {
		StreamResult res = new StreamResult(out);
		forwardDocument(doc, res);
	}
	/**
	 * Forward an XML document to a writer
	 * @param docName Name of XML document
	 * @param out Stream
	 * @param validate Validate document?
	 * @throws WebGenomeSystemException
	 */
	public static void forwardDocument(String docName, Writer out, boolean validate) 
		throws WebGenomeSystemException {
		Document doc = loadDocument(docName, validate);
		forwardDocument(doc, out);
	}
	/**
	 * Forward XML document to stream
	 * @param doc XML document
	 * @param res Stream
	 * @throws WebGenomeSystemException
	 */
	private static void forwardDocument(Document doc, StreamResult res) 
		throws WebGenomeSystemException {
		try {
			TransformerFactory fac = TransformerFactory.newInstance();
			Transformer trans = fac.newTransformer();
			DOMSource source = new DOMSource(doc);
			trans.transform(source, res);
		} catch (Exception e) {
			throw new WebGenomeSystemException("Error forwarding document", e);
		}
	}
	/**
	 * Find element in XML document with given property
	 * @param doc XML document
	 * @param tagName Tag name
	 * @param propName Property name
	 * @param propValue Property value
	 * @return Element or null if not found
	 */
	public static Element findElement
	(
		Document doc, String tagName, String propName, String propValue
	) {
		Properties props = new Properties();
		props.put(propName, propValue);
		return findElement(doc, tagName, props);
	}
	/**
	 * Find an element
	 * @param doc XML document
	 * @param tagName Tag name
	 * @param props Properties corresponding to attributes in tag
	 * @return Element or null if not found
	 */
	public static Element findElement
	(
		Document doc, String tagName, Properties props
	) {
		Element elmt = null;
		NodeList nlist = doc.getElementsByTagName(tagName);
		for (int i = 0; i < nlist.getLength() && elmt == null; i++) {
			Node node = nlist.item(i);
			if (node instanceof Element) {
				Element temp = (Element)node;
				boolean matches = true;
				for (Enumeration en = props.keys(); 
						en.hasMoreElements() && matches;) {
					String name = (String)en.nextElement();
					String value = props.getProperty(name);
					String attValue = temp.getAttribute(name);
					if (attValue == null)
						matches = false;
					else if (! value.equals(attValue))
						matches = false;
				}
				if (matches)
					elmt = temp;
			}
		}
		return elmt;
	}
}