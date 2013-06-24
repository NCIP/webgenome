/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $


*/


package org.rti.webgenome.core;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;


/**
 * Error handler for SAX parse exceptions.
 */
public final class SaxErrorHandler implements ErrorHandler {
	
	
	/**
	 * Constructor.
	 *
	 */
	public SaxErrorHandler() {
		
	}
	
	/**
	 * Invoked when a SAX warning is encountered.
	 * @param exception Parse exception
	 * @throws SAXException if there is a parse error
	 */
	public void warning(final SAXParseException exception) throws SAXException {
		throw new SAXException(exception);
	}
	
	
	/**
	 * Invoked when a SAX error is encountered.
	 * @param exception Parse exception
	 * @throws SAXException if there is a parse error
	 */
	public void error(final SAXParseException exception)throws SAXException {
		throw new SAXException(exception);
	}
	
	
	/**
	 * Invoked when a SAX fatal error is encountered.
	 * @param exception Parse exception
	 * @throws SAXException if there is a parse error
	 */
	public void fatalError(final SAXParseException exception)
		throws SAXException {
		throw new SAXException(exception);
	}

}
