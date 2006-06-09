/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/util/WebUtils.java,v $
$Revision: 1.2 $
$Date: 2006-06-09 20:01:27 $

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

package org.rti.webcgh.webui.util;


import java.util.Properties;
import java.util.Iterator;
import java.util.Collection;

import org.rti.webcgh.analytic.AnalyticException;
import org.rti.webcgh.analytic.AnalyticPipeline;
import org.rti.webcgh.analytic.DataSetInvalidationException;
import org.rti.webcgh.array.Experiment;
import org.rti.webcgh.array.Pipeline;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.array.persistent.PersistentDomainObjectMgr;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.graph.PlotParameters;
import org.rti.webcgh.service.AuthenticationException;
import org.rti.webcgh.service.UserProfile;
import org.rti.webcgh.service.WebcghDatabaseException;
import org.rti.webcgh.util.BeanUtils;
import org.rti.webcgh.webui.plot.PlotParamsForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * General utilities requried by the web tier
 */
public class WebUtils {
	
	
	
	/**
	 * Construct an '&' separated parameter list of name/value pairs 
	 * from the properties of given object
	 * @param obj An object
	 * @param excludedProps Names of properties that should be excluded
	 * from parameter string
	 * @return '&' separated parameter list of name/value pairs 
	 * from the properties of given object
	 */
	public static String paramListFromProps
	(
		Object obj, Collection excludedProps
	) {
		StringBuffer paramList = new StringBuffer();
		Properties map = BeanUtils.getSringProperties(obj);
		paramList.append("?version=" + Math.random());
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String name = (String)it.next();
			if (! excluded(name, excludedProps)) {
				String value = (String)map.get(name);
				paramList.append("&");
				paramList.append(name + "=" + value);
			}
		}
		return paramList.toString();
	}
	
	
	/**
	 * Construct an '&' separated parameter list of name/value pairs 
	 * from the properties of given object
	 * @param obj An object
	 * from parameter string
	 * @return '&' separated parameter list of name/value pairs 
	 * from the properties of given object
	 */
	public static String paramListFromProps
	(
		Object obj
	) {
		return WebUtils.paramListFromProps(obj, null);
	}
	
	
	/**
	 * Helper method to determine if a given property name is to be
	 * excluded from parameter list
	 * @param propName Property name
	 * @param excludedProps List of excluded properties
	 * @return T/F
	 */
	private static boolean excluded(String propName, Collection excludedProps) {
		boolean excluded = false;
		if (excludedProps != null) {
			for (Iterator it = excludedProps.iterator(); 
				it.hasNext() && ! excluded;) {
				String excludedProp = (String)it.next();
				if (excludedProp.equals(propName))
					excluded = true;
			}
		}
		return excluded;
	}
	
	
	/**
	 * Recovers a GenomeArrayDataSet object from session.
	 * Data set is assembled in PlotSetupAction so that data can be
	 * validated properly.  When hitting the back
	 * button PlotSetupAction will not be invoked.  To force
	 * re-generation of the data set after the back button
	 * is pressed we set the DATA_SET session attribute
	 * to a nonsensical value after the data set is
	 * retrieved from the PlotSetupAction.
	 * If the nonsensical value is subsequently encountered, we re-generate the
	 * data set.
	 * @param form Plot parameters form
	 * @param request Request
	 * @param objMgr Persistent object manager
	 * @param params Plotting parameters
	 * @return GenomeArrayDataSet objects
	 * @throws WebcghDatabaseException
	 * @throws AuthenticationException
	 * @throws AnalyticException
	 * @throws DataSetInvalidationException
	 * @throws WebcghSystemException
	 */
	public static Experiment[] recoverDataSet
	(
		PlotParamsForm form, HttpServletRequest request,
		PersistentDomainObjectMgr objMgr, PlotParameters params
	) throws WebcghDatabaseException, AuthenticationException, AnalyticException,
		DataSetInvalidationException, WebcghSystemException {
	    UserProfile profile = AttributeManager.getUserProfile(request);
		Experiment[] dataSets = null;
		AnalyticPipeline analyticPipeline = WebUtils.getPipeline(form.getPipelineName(), objMgr, profile);
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Attribute.DATA_SET);
		if (obj instanceof java.lang.String) {
			ShoppingCart cart = AttributeManager.getShoppingCart(request);
			Experiment[] gads = cart.getExperiments();
			DataAssembler assembler = new DataAssembler();
			dataSets = assembler.assembleExperiments(gads, analyticPipeline, params);
		} else
			dataSets = (Experiment[])obj;
		session.setAttribute(Attribute.DATA_SET, new String("null"));
		return dataSets;
	}
	
	
	/**
	 * Get analytic pipeline with given name
	 * @param pipelineName Pipeline name
	 * @param objMgr Object manager
	 * @param profile User profile
	 * @return An analytic pipeline
	 */
	public static AnalyticPipeline getPipeline(String pipelineName, 
	        PersistentDomainObjectMgr objMgr, UserProfile profile) {
	    AnalyticPipeline aPipe = null;
		aPipe = AnalyticPipeline.loadDefaultPipeline(pipelineName);
		if (aPipe == null && profile != null) {
			Pipeline pipeline = objMgr.getPersistentPipeline(pipelineName, profile.getName());
			aPipe = pipeline.toAnalyticPipeline();
		}
	    return aPipe;
	}
}
