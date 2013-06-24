/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/XmlUtils.java,v $
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $



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
