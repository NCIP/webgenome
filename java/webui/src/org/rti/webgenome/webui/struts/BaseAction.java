/*
$Revision: 1.6 $
$Date: 2008-02-22 18:24:44 $

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

package org.rti.webgenome.webui.struts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.service.dao.CytologicalMapDao;
import org.rti.webgenome.service.data.DataSource;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.io.ImageFileManager;
import org.rti.webgenome.service.job.JobManager;
import org.rti.webgenome.service.plot.PlotService;
import org.rti.webgenome.service.session.Authenticator;
import org.rti.webgenome.service.session.SecurityMgr;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.service.session.WebGenomeDbService;
import org.rti.webgenome.service.util.IdGenerator;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.util.PageContext;


/**
 * Abstract base class for webGenome Struts actions.
 * @author dhall
 *
 */
public abstract class BaseAction extends Action {
	
	//
	//  A T T R I B U T E S
	//
	
	/** Service facade for transactional database operations. */
	private WebGenomeDbService dbService = null;
	
	/** Cytological map data access object. */
	private CytologicalMapDao cytologicalMapDao = null;
	
	/**
	 * DAO for getting annotated genome features.  This property
	 * must be set via dependency injection.
	 */
	private AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao = null;
	
	/** Manager of compute-intensive jobs. */
	private JobManager jobManager = null;
	
	/** Service for performing analyses. */
	private AnalysisService analysisService = null;
	
	/** Manages serialized data files. */
	private DataFileManager dataFileManager = null;
		
	/** File I/O service. */
	private IOService ioService = null;
	
    /** Experiment ID generator. */
    private IdGenerator experimentIdGenerator = null;
    
    /** Bioassay ID generator. */
    private IdGenerator bioAssayIdGenerator = null;
    
	/**
	 * Service for creating plots.  This service is
	 * only used directly when session is CLIENT mode
	 * or the size of the data set is sufficiently small
	 * to generate the plot immediately.
	 */
	private PlotService plotService = null;
	
	/** Manager for image files. */
	private ImageFileManager imageFileManager = null;
	
	/** Index of configured external data sources. */
	private Map<String, DataSource> dataSourcesIndex = null;
	
	/** Account manager. This property should be injected. */
	private SecurityMgr securityMgr = null;
	
	/**
	 * Authenticator of user credentials.
	 * This property should be injected.
	 */
	private Authenticator authenticator = null;
	
	
	//
	//  I N J E C T O R S
	//
	
	/**
	 * Inject a data source index.
	 * @param dataSourcesIndex An index to external data sources.
	 */
	public void setDataSourcesIndex(
			final Map<String, DataSource> dataSourcesIndex) {
		this.dataSourcesIndex = dataSourcesIndex;
	}
	
	/**
	 * Set authenticator of user credentials.
	 * @param authenticator An authenticator
	 */
	public void setAuthenticator(final Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	
	/**
	 * Set service for creating plots.
	 * @param plotService Plot service
	 */
	public void setPlotService(final PlotService plotService) {
		this.plotService = plotService;
	}
	
	
	/**
	 * Set manager of image files.
	 * @param imageFileManager Image file manager
	 */
	public void setImageFileManager(final ImageFileManager imageFileManager) {
		this.imageFileManager = imageFileManager;
	}


	/**
     * Set bioassay ID generator.
     * @param bioAssayIdGenerator ID generator
     */
	public void setBioAssayIdGenerator(
			final IdGenerator bioAssayIdGenerator) {
		this.bioAssayIdGenerator = bioAssayIdGenerator;
	}


	/**
	 * Set experiment ID generator.
	 * @param experimentIdGenerator ID generator
	 */
	public void setExperimentIdGenerator(
			final IdGenerator experimentIdGenerator) {
		this.experimentIdGenerator = experimentIdGenerator;
	}
	
	
	/**
	 * Set service for performing analysis via injection.
	 * @param analysisService Service for performing analysis.
	 */
	public void setAnalysisService(final AnalysisService analysisService) {
		this.analysisService = analysisService;
	}
	
	/**
	 * Set manager for serialized data files.
	 * @param dataFileManager Data file manager
	 */
	public void setDataFileManager(final DataFileManager dataFileManager) {
		this.dataFileManager = dataFileManager;
	}
	
	
	/**
	 * Inject data file I/O service.
	 * @param ioService Data file I/O service
	 */
	public void setIoService(final IOService ioService) {
		this.ioService = ioService;
	}
	
	
	/**
	 * Set manager of compute-intensive jobs.
	 * @param jobManager Compute-intensive job manager
	 */
	public void setJobManager(final JobManager jobManager) {
		this.jobManager = jobManager;
	}

	/**
	 * Set cytological map data access object.
	 * @param cytologicalMapDao Cytological map data access object.
	 */
	public void setCytologicalMapDao(
			final CytologicalMapDao cytologicalMapDao) {
		this.cytologicalMapDao = cytologicalMapDao;
	}

	/**
	 * Inject service for transactional database operations.
	 * @param dbService Service for transactional database operations
	 */
	public void setDbService(final WebGenomeDbService dbService) {
		this.dbService = dbService;
	}
	
	/**
	 * Set DAO for getting annotated genome features.  This property
	 * must be set via dependency injection.
	 * @param annotatedGenomeFeatureDao DAO for getting annotated
	 * genome features
	 */
	public void setAnnotatedGenomeFeatureDao(
			final AnnotatedGenomeFeatureDao annotatedGenomeFeatureDao) {
		this.annotatedGenomeFeatureDao = annotatedGenomeFeatureDao;
	}
	
	/**
	 * Set account manager.
	 * @param securityMgr Security manager for user accounts.
	 */
	public void setSecurityMgr(final SecurityMgr securityMgr) {
		this.securityMgr = securityMgr;
	}

	//
	//  A C C E S S O R S
	//
	
	/**
	 * Get service for transactional database operations.
	 * @return Service for transactional database operations
	 */
	protected WebGenomeDbService getDbService() {
		return this.dbService;
	}

	/**
	 * Get credentials authenticator.
	 * @return Credentials authenticator
	 */
	protected Authenticator getAuthenticator() {
		return authenticator;
	}

	/**
	 * Get index of data sources keyed on data source name.
	 * @return Index of data sources
	 */
	protected Map<String, DataSource> getDataSourcesIndex() {
		return dataSourcesIndex;
	}


	/**
	 * Get manager of image files.
	 * @return Image file manager
	 */
	protected ImageFileManager getImageFileManager() {
		return imageFileManager;
	}


	/**
	 * Get plotting service.
	 * @return Plotting service
	 */
	protected PlotService getPlotService() {
		return plotService;
	}


	/**
	 * Get manager of data files.
	 * @return Data file manager
	 */
	protected DataFileManager getDataFileManager() {
		return dataFileManager;
	}

	/**
	 * Get file I/O service.
	 * @return File I/O service
	 */
	protected IOService getIoService() {
		return ioService;
	}
	
	/**
	 * Get service for performing analytic operations.
	 * @return Analysis service
	 */
	protected AnalysisService getAnalysisService() {
		return this.analysisService;
	}
	
	/**
	 * Get annotated genome feature data access object.
	 * @return Annotated genome feature data access object
	 */
	protected AnnotatedGenomeFeatureDao getAnnotatedGenomeFeatureDao() {
		return annotatedGenomeFeatureDao;
	}


	/**
	 * Get manager of compute-intensive jobs.
	 * @return Manager of compute-intensive jobs
	 */
	protected JobManager getJobManager() {
		return jobManager;
	}

	/**
	 * Get generator of bioassay IDs.
	 * @return Generator of bioassay IDs
	 */
	protected IdGenerator getBioAssayIdGenerator() {
		return bioAssayIdGenerator;
	}


	/**
	 * Get generator of experiment IDs.
	 * @return Generator of experiment IDs
	 */
	protected IdGenerator getExperimentIdGenerator() {
		return experimentIdGenerator;
	}


	/**
	 * Get cytological map data access object.
	 * @return Cytological map data access object
	 */
	protected CytologicalMapDao getCytologicalMapDao() {
		return cytologicalMapDao;
	}
	
	/**
	 * Get account manager.
	 * @return Account manager.
	 */
	protected SecurityMgr getSecurityMgr() {
		return securityMgr;
	}


	//
	//  B U S I N E S S    M E T H O D S
	//
	
	/**
	 * Get shopping cart.  If the session mode is
	 * {@code CLIENT}, the cart will be cached in the
	 * session object.  If the mode is {@code STAND_ALONE},
	 * the cart will be obtained from persistent storage.
	 * @param request Servlet request
	 * @return Shopping cart
	 * @throws SessionTimeoutException If any necessary
	 * session attributes are not found, which indicates the
	 * session has timed ouit.
	 */
	protected ShoppingCart getShoppingCart(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		ShoppingCart cart = null;
		SessionMode mode = PageContext.getSessionMode(request);
		if (mode == SessionMode.CLIENT) {
			cart = PageContext.getShoppingCart(request);
		} else if (mode == SessionMode.STAND_ALONE) {
			Principal principal = PageContext.getPrincipal(request);
			cart = this.getDbService().loadShoppingCart(principal.getName(),
					principal.getDomain());
		}
		return cart;
	}
	
	
	/**
	 * Are data in memory for this session?
	 * @param request A request
	 * @return T/F
	 * @throws SessionTimeoutException If the method cannot
	 * determine the session mode, which would occur if
	 * for some reason the session timed out.
	 */
	protected boolean dataInMemory(final HttpServletRequest request)
	throws SessionTimeoutException {
		return PageContext.getSessionMode(request) == SessionMode.CLIENT;
	}
}
