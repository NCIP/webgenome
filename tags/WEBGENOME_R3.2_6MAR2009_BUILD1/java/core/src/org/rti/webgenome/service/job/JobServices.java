/*
$Revision: 1.4 $
$Date: 2008-02-22 03:54:09 $


*/

package org.rti.webgenome.service.job;

import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.plot.PlotService;
import org.rti.webgenome.service.session.WebGenomeDbService;

/**
 * Provides a set of services to jobs that they
 * need to execute.
 * @author dhall
 */
public class JobServices {

	//
	//  A T T R I B U T E S
	//
	
	/** Provides file input/output. */
	private final IOService ioService;
	
	/** Performs analytic operations. */
	private final AnalysisService analysisService;
	
	/** Plots data. */
	private final PlotService plotService;
	
	/** Facade service for operations on the WebGenome database. */
	private final WebGenomeDbService webGenomeDbService;

	
	//
	//  G E T T E R S
	//
	
	/**
	 * Get service to perform analytic operations.
	 * @return Serive to perform analytic operations.
	 */
	public AnalysisService getAnalysisService() {
		return analysisService;
	}

	/**
	 * Get service to perform data file I/O.
	 * @return Service to perform data file I/O.
	 */
	public IOService getIoService() {
		return ioService;
	}

	/**
	 * Get service to perform data plotting.
	 * @return Service to perform data plotting.
	 */
	public PlotService getPlotService() {
		return plotService;
	}
	
	/**
	 * Get facade service for performing operations on
	 * the WebGenome database.
	 * @return A session facade for database operations
	 */
	public WebGenomeDbService getWebGenomeDbService() {
		return webGenomeDbService;
	}

	//
	//  C O N S T R U C T O R S
	//

	/**
	 * Constructor.
	 * @param ioService Service to perform data file I/O.
	 * @param analytisService Service to run analytic operations.
	 * @param plotService Service to plot data.
	 * @param webGenomeDbService Facade for transactional
	 * operations on the WebGenome database
	 */
	public JobServices(final IOService ioService,
			final AnalysisService analytisService,
			final PlotService plotService,
			final WebGenomeDbService webGenomeDbService) {
		super();
		this.ioService = ioService;
		this.analysisService = analytisService;
		this.plotService = plotService;
		this.webGenomeDbService = webGenomeDbService;
	}
	
	
}
