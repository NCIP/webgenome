/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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

package org.rti.webcgh.deprecated;

import java.util.ArrayList;
import java.util.List;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * General utility methods for downloading data dump from UCSC
 */
public class AnnotationUtils {

	private static final String DOWNLOAD_CONFIG_FILE = "download-config.xml";
	private static Document configDoc = null;

	/**
	 * Get document containing annotation configuration information
	 * @return XML document
	 * @throws WebcghSystemException
	 */
	public static Document getDownloadConfigDocument()
		throws WebcghSystemException {
		if (configDoc == null)
			configDoc = XmlUtils.loadDocument(DOWNLOAD_CONFIG_FILE, false);
		return configDoc;
	}

	/**
	 * @return a list of download files
	 * @throws WebcghSystemException
	 */
	public static List getDownloadFileList() throws WebcghSystemException {
		List fileList = new ArrayList();
		getDownloadConfigDocument();
		if (configDoc == null)
			throw new WebcghSystemException("Could not open download configuration file");
		NodeList nlist = configDoc.getElementsByTagName("download-file");
		for (int i = 0; i < nlist.getLength(); i++) {
			Element ele = (Element) nlist.item(i);
			fileList.add(ele.getAttribute("sql"));
			fileList.add(ele.getAttribute("data"));
		}
		return fileList;

	}
	
	/**
	 * Get a list of file names to be downloaded
	 * @return List A list of file names
	 * @throws WebcghSystemException
	 */
	public static List getNames() throws WebcghSystemException {
		List nameList = new ArrayList();
		getDownloadConfigDocument();
		if (configDoc == null)
			throw new WebcghSystemException("Could not open download configuration file");
		NodeList nlist = configDoc.getElementsByTagName("download-file");
		for (int i = 0; i < nlist.getLength(); i++) {
			Element ele = (Element) nlist.item(i);
			nameList.add(ele.getAttribute("name"));	
		}
		return nameList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List list = getNames();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		} catch (WebcghSystemException e) {
			e.printStackTrace();
		}

	}

}
