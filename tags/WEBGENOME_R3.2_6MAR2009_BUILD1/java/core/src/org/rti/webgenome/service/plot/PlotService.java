/*
$Revision: 1.2 $
$Date: 2007-12-17 18:49:04 $


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
