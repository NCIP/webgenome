/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $


*/

package org.rti.webgenome.service.plot;

import java.util.Collection;

import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;

/**
 * Generates plots.
 * @author dhall
 *
 */
public interface PlotGenerator {
	
	/**
	 * Create new plot.
	 * @param experiments Experiments containing data to plot.
	 * @param plotParameters Plot parameters.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 * @return A plot.
	 */
	Plot newPlot(Collection<Experiment> experiments,
			PlotParameters plotParameters,
			ChromosomeArrayDataGetter chromosomeArrayDataGetter);

	/**
	 * Replot data.
	 * @param plot Plot to redo.
	 * @param experiments Experiments containing data to plot.
	 * @param plotParameters Plot parameters.
	 * @param chromosomeArrayDataGetter Chromosome array data getter
	 */
	void replot(Plot plot, Collection<Experiment> experiments,
			PlotParameters plotParameters,
			ChromosomeArrayDataGetter chromosomeArrayDataGetter);
}
