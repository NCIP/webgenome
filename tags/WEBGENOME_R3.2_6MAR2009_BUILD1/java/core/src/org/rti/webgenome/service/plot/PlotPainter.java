/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $


*/

package org.rti.webgenome.service.plot;

import org.rti.webgenome.service.util.ChromosomeArrayDataGetter;

/**
 * Base class for plot painters.
 * @author dhall
 *
 */
public abstract class PlotPainter {
	
	// ==============================
	//      Attributes
	// ==============================
	
	/** Chromosome array data getter. */
	private final ChromosomeArrayDataGetter chromosomeArrayDataGetter;

	
	// ==========================
	//    Getters/setters
	// ==========================
	
	/**
	 * Get chromosome array data getter.
	 * @return Chromosome array data getter
	 */
	protected final ChromosomeArrayDataGetter getChromosomeArrayDataGetter() {
		return chromosomeArrayDataGetter;
	}

	
	// =============================
	//      Constructors
	// =============================
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome
	 * array data getter
	 */
	public PlotPainter(final ChromosomeArrayDataGetter
			chromosomeArrayDataGetter) {
		super();
		this.chromosomeArrayDataGetter = chromosomeArrayDataGetter;
	}
	
	
	// ===============================
	//    Abstract methods
	// ===============================
	
//    /**
//     * Paints a plot on the given plot panel.
//     * @param panel Plot panel to add the scatter plot to
//     * @param experiments Experiments to plot
//     * @param plotParameters Plotting parameters specified
//     * by user
//     */
//    abstract void paintPlot(PlotPanel panel,
//            Collection<Experiment> experiments,
//            PlotParameters plotParameters);
}
