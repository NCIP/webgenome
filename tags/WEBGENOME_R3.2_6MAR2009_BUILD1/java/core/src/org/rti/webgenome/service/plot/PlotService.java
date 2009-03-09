/*
$Revision: 1.2 $
$Date: 2007-12-17 18:49:04 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.service.plot;

import java.util.Collection;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;
import org.rti.webgenome.service.util.IdGenerator;
import org.rti.webgenome.util.StringUtils;

/**
 * Service to generate plots.  Generally this will be the one
 * and only plot class that clients use to generate plots.
 * @author dhall
 *
 */
public class PlotService {
	
	/** Plot generator. */
	private PlotGenerator plotGenerator = null;
	
	/** Plot ID generator. */
	private IdGenerator plotIdGenerator = null;

	/**
	 * Set generator bean for plots for dependency injection.
	 * @param plotGenerator Plot generator bean.
	 */
	public void setPlotGenerator(final PlotGenerator plotGenerator) {
		this.plotGenerator = plotGenerator;
	}
	
	/**
	 * Set ID generator for plots.  This should be injected.
	 * @param plotIdGenerator Generator of plot IDs
	 */
	public void setPlotIdGenerator(final IdGenerator plotIdGenerator) {
		this.plotIdGenerator = plotIdGenerator;
	}



	/**
	 * Plot given experiments.  If the given {@link Plot} object is
	 * {@code null}, a new plot is generated and saved to the cart.
	 * Otherwise, the given plot is re-plotted.  This operation may
	 * be perfomed immediately (i.e., on the application server),
	 * or offloaded to the analytic server for batch processing if
	 * there are enough data.
	 * @param plot A plot to re-plot.  May be null.
	 * @param experiments Experiments to plot
	 * @param params Plotting parameters
	 * @param cart A shopping cart.  If {@code plot} is null and a new
	 * plot is generated, it will be saved into this cart.
	 * @param chromosomeArrayDataGetter Getter for chromosome
	 * array data
	 * @return Plot that was replotted or newly created plot.
	 */
	public Plot plotExperiments(final Plot plot,
			final Collection<Experiment> experiments,
			final PlotParameters params, final ShoppingCart cart,
			final ChromosomeArrayDataGetter chromosomeArrayDataGetter) {
		Plot returnPlot = plot;
		if (plot != null) {
			this.plotGenerator.replot(plot, experiments, params,
					chromosomeArrayDataGetter);
		} else {
    		Long plotId = this.plotIdGenerator.nextId();
    		Plot newPlot = this.plotGenerator.newPlot(experiments, params,
    				chromosomeArrayDataGetter);
    		newPlot.setId(plotId);
    		if (StringUtils.isEmpty(params.getPlotName())) {
    			String plotName = "Plot " + plotId.toString();
    			params.setPlotName(plotName);
    		}
    		cart.add(newPlot);
    		returnPlot = newPlot;
		}
		return returnPlot;
	}
}
