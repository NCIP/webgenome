/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.6 $
$Date: 2007-09-06 16:48:10 $


*/

package org.rti.webgenome.webui.struts.cart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.domain.AnalysisDataSourceProperties;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.webui.struts.BaseAction;


/**
 * Setup action for screen that enables the user to adjust
 * analysis parameters from within a plot screen.
 * @author dhall
 *
 */
public class AdjustPlotAnalysisParamsSetupAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 */
	@Override
    public ActionForward execute(
            final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request,
            final HttpServletResponse response
        ) throws Exception {
    	
		// Attach map of derived experiments to request
		ShoppingCart cart = this.getShoppingCart(request);
		Long plotId = Long.parseLong(request.getParameter("id"));
		Plot plot = cart.getPlot(plotId);
		if (plot == null) {
			throw new WebGenomeApplicationException(
					"Unable to retrieve plot from shopping cart");
		}
		Collection<Experiment> experiments = plot.getExperiments();
		Collection<QuantitationType> qTypes =
			Experiment.getQuantitationTypes(experiments);
		Map<Experiment, Collection<UserConfigurableProperty>>
			derivedExperiments = new HashMap<Experiment,
				Collection<UserConfigurableProperty>>();
		for (Experiment exp : experiments) {
			if (exp == null) {
				throw new WebGenomeApplicationException(
						"Some experiments no longer in shopping cart");
			}
			if (exp.isDerived()) {
				AnalysisDataSourceProperties props =
					(AnalysisDataSourceProperties)
					exp.getDataSourceProperties();
				derivedExperiments.put(exp,
						props.getSourceAnalyticOperation().
						getUserConfigurableProperties(qTypes));
			}
		}
		request.setAttribute("derived.experiments", derivedExperiments);
		
		// Attach plot to request
		request.setAttribute("plot", plot);
		
    	return mapping.findForward("success");
    }
}
