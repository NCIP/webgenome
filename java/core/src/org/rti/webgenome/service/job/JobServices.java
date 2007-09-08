/*
$Revision: 1.3 $
$Date: 2007-09-08 17:17:05 $

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

package org.rti.webgenome.service.job;

import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.dao.ArrayDao;
import org.rti.webgenome.service.dao.ExperimentDao;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.plot.PlotService;

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
	
	/** Shopping cart data access object. */
	private final ShoppingCartDao shoppingCartDao;
	
	/** Array data access object. */
	private final ArrayDao arrayDao;
	
	/** Experiment data access object. */
	private final ExperimentDao experimentDao;

	
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
	 * Get shopping cart data access object.
	 * @return Shopping cart data access object
	 */
	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}
	
	
	/**
	 * Get experiment data access object.
	 * @return Experiment data access object
	 */
	public ExperimentDao getExperimentDao() {
		return experimentDao;
	}

	/**
	 * Get array data access object.
	 * @return Array data access object
	 */
	public ArrayDao getArrayDao() {
		return arrayDao;
	}

	//
	//  C O N S T R U C T O R S
	//

	/**
	 * Constructor.
	 * @param ioService Service to perform data file I/O.
	 * @param analytisService Service to run analytic operations.
	 * @param plotService Service to plot data.
	 * @param shoppingCartDao Shopping cart data access object
	 * @param arrayDao Array data access object
	 * @param experimentDao Experiment data access object
	 */
	public JobServices(final IOService ioService,
			final AnalysisService analytisService,
			final PlotService plotService,
			final ShoppingCartDao shoppingCartDao,
			final ArrayDao arrayDao,
			final ExperimentDao experimentDao) {
		super();
		this.ioService = ioService;
		this.analysisService = analytisService;
		this.plotService = plotService;
		this.shoppingCartDao = shoppingCartDao;
		this.arrayDao = arrayDao;
		this.experimentDao = experimentDao;
	}
	
	
}
