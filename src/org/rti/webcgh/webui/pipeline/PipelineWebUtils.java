/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/pipeline/PipelineWebUtils.java,v $
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


package org.rti.webcgh.webui.pipeline;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

import org.rti.webcgh.analytic.AnalyticOperation;
import org.rti.webcgh.analytic.AnalyticPipeline;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;


/**
 * Special utilities used by pipeline actions
 */
public class PipelineWebUtils {
	
	/**
	 * Synchronize pipeline bean with web form
	 * @param pipeline Pipeline bean
	 * @param request Servlet request
	 * @return Errors, if any
	 */
	public static ActionErrors synchronize(
		AnalyticPipeline pipeline, HttpServletRequest request
	) {
		ActionErrors errors = new ActionErrors();
		pipeline.setName(request.getParameter("name"));
		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String paramName = (String)en.nextElement();
			int p = paramName.indexOf("-");
			if (p >= 0) {
				String propName = paramName.substring(0, p);
				String propValue = request.getParameter(paramName);
				int idx = Integer.parseInt(paramName.substring(p + 1)) - 1;
				AnalyticOperation op = 
					(AnalyticOperation)pipeline.getOperations().get(idx);
				Class propType = org.rti.webcgh.util.BeanUtils.getPropertyClass(op, propName);
				if (propType.isPrimitive())
				    org.rti.webcgh.util.BeanUtils.setPrimitiveProperty(
						op, propName, propValue);
				else {
					try {
						Object obj = Class.forName(propValue).newInstance();
						BeanUtils.copyProperty(op, propName, obj);
					} catch (Exception e) {
						errors.add(paramName, new ActionError("invalid.field"));
					}
				}
			}
		}
		if (errors.size() > 0)
			errors.add("global", new ActionError("invalid.pipeline.fields"));
		return errors;
	}
	
	
	private static boolean isClassName
	(
		String property
	) throws NoSuchMethodException {
		boolean isClassName = true;
		try {
			Class objClass = Class.forName(property);
		} catch (ClassNotFoundException e) {
			isClassName = false;
		}
		return isClassName;
	}
}
