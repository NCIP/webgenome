/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/
package org.rti.webgenome.webui.struts;


import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.rti.webgenome.webui.util.Attribute;

/**
 * Handles exceptions within Struts framework.
 */
public final class WebcghExceptionHandler extends ExceptionHandler {
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(ExceptionHandler.class.getName());
	
	
	/**
	 * Called when an exception has been thrown during the
	 * response to a request within a Struts action.
	 * @param ex Exception
	 * @param ae Exception configuration
	 * @param mapping Mapping of String tags to Struts action handlers
	 * @param formInstance Instance of Struts form class
	 * @param request Servlet request object
	 * @param response Servlet response object
	 * @return Forward
	 * @throws ServletException If something crashes.
	 */
	public ActionForward execute(
		final Exception ex, final ExceptionConfig ae,
		final ActionMapping mapping,
		final ActionForm formInstance,
		final HttpServletRequest request,
		final HttpServletResponse response
	  ) throws ServletException {
	  	
	  	// Attach exception to request
	  	request.setAttribute(Attribute.EXCEPTION, ex);
	  	
	  	// Log exception
	  	LOGGER.error(ex, ex);
	  	
	  	return super.execute(ex, ae, mapping, formInstance, request, response);
	  }
}
