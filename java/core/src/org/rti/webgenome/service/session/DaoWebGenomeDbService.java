/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2008-05-19 20:11:02 $


*/

package org.rti.webgenome.service.session;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.ArrayDao;
import org.rti.webgenome.service.dao.ExperimentDao;
import org.rti.webgenome.service.dao.OrganismDao;
import org.rti.webgenome.service.dao.PlotDao;
import org.rti.webgenome.service.dao.PrincipalDao;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.job.Job;
import org.rti.webgenome.service.job.JobDao;

/**
 * Implementation of <code>WebGenomeDbSerivce</code>
 * using injected DAO objects.
 * @author dhall
 *
 */
public class DaoWebGenomeDbService implements WebGenomeDbService {
	
	//
	//  A T T R I B U T E S
	//
	
	/** Array data access object. */
	private ArrayDao arrayDao = null;
	
	/** ShoppingCart data access object. */
	private ShoppingCartDao shoppingCartDao = null;
	
	/** File I/O service. */
	private IOService ioService = null;
	
	/** Experiment data access object. */
	private ExperimentDao experimentDao = null;
	
	/** Organism data access object. */
	private OrganismDao organismDao = null;
	
	/** Plot data access object. */
	private PlotDao plotDao = null;
	
	/** Principal data access object. */
	private PrincipalDao principalDao = null;
	
	/** Job data access object. */
	private JobDao jobDao = null;
	
	
	//
	//  I N J E C T O R S
	//
	
	/**
	 * Inject array data access object.
	 * @param arrayDao Array data access object
	 */
	public void setArrayDao(final ArrayDao arrayDao) {
		this.arrayDao = arrayDao;
	}
	
	
	/**
	 * Inject experiment data access object.
	 * @param experimentDao Experiment data access object
	 */
	public void setExperimentDao(final ExperimentDao experimentDao) {
		this.experimentDao = experimentDao;
	}


	/**
	 * Inject data file I/O service.
	 * @param ioService Data file I/O service
	 */
	public void setIoService(final IOService ioService) {
		this.ioService = ioService;
	}


	/**
	 * Inject job data access object.
	 * @param jobDao Job data access object
	 */
	public void setJobDao(final JobDao jobDao) {
		this.jobDao = jobDao;
	}


	/**
	 * Inject principal data access object.
	 * @param principalDao Principal data access object
	 */
	public void setPrincipalDao(final PrincipalDao principalDao) {
		this.principalDao = principalDao;
	}


	/**
	 * Inject organism data access object.
	 * @param organismDao Organism data access object
	 */
	public void setOrganismDao(final OrganismDao organismDao) {
		this.organismDao = organismDao;
	}


	/**
	 * Inject plot data access object.
	 * @param plotDao Plot data access object
	 */
	public void setPlotDao(final PlotDao plotDao) {
		this.plotDao = plotDao;
	}


	/**
	 * Inject shopping cart data access object.
	 * @param shoppingCartDao Shopping cart data access object
	 */
	public void setShoppingCartDao(final ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	
	//
	//  I N T E R F A C E : WebGenomeDbService
	//

	/**
	 * {@inheritDoc}
	 */
	public void saveArrayAndUpdateCart(final Array array,
			final ShoppingCart cart) {
		this.arrayDao.saveOrUpdate(array);
		this.shoppingCartDao.update(cart);
	}

	/**
	 * {@inheritDoc}
	 */
	public ShoppingCart loadShoppingCart(
			final Long user, final String domain) {
		return this.shoppingCartDao.load(user, domain);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void saveShoppingCart(final ShoppingCart cart) {
		this.shoppingCartDao.save(cart);
	}


	/**
	 * {@inheritDoc}
	 */
	public void updateShoppingCart(final ShoppingCart cart) {
		this.shoppingCartDao.update(cart);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteArraysAndUpdateCart(
			final Experiment experiment, final ShoppingCart cart) {
		Set<Array> removeList = new HashSet<Array>();
    	for (BioAssay ba : experiment.getBioAssays()) {
    		Array array = ba.getArray();
    		if (array != null) {
	    		if (array.isDisposable()
	    				&& !this.arrayDao.isReferenced(array)) {
	    			removeList.add(array);
	    		}
    		}
    	}
    	for (Array a : removeList) {
    		this.arrayDao.delete(a);
    	}
    	this.shoppingCartDao.update(cart);
    	for (Array a : removeList) {
    		this.ioService.deleteDataFiles(a);
    	}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addArraysAndUpdateCart(final Experiment experiment,
			final ShoppingCart cart) {
		if (experiment.getBioAssays().size() > 0) {
			Array array =
				experiment.getBioAssays().iterator().next().getArray();
			this.arrayDao.saveOrUpdate(array);
		}
		this.shoppingCartDao.update(cart);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateExperiment(final Experiment experiment) {
		this.experimentDao.update(experiment);
	}


	/**
	 * {@inheritDoc}
	 */
	public void updateExperimentsAndCart(
			final Collection<Experiment> experiments,
			final ShoppingCart cart) {
		for (Experiment exp : experiments) {
			this.experimentDao.update(exp);
		}
		this.shoppingCartDao.update(cart);
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isExperimentReferenced(final Long id) {
		return this.experimentDao.isReferenced(id);
	}


	/**
	 * {@inheritDoc}
	 */
	public Organism loadDefaultOrganism() {
		return this.organismDao.loadDefault();
	}


	/**
	 * {@inheritDoc}
	 */
	public List<Organism> loadAllOrganisms() {
		return this.organismDao.loadAll();
	}


	/**
	 * {@inheritDoc}
	 */
	public Organism loadOrganism(final Long id) {
		return this.organismDao.load(id);
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isPlotReferenced(final Long id) {
		return this.plotDao.isReferenced(id);
	}


	/**
	 * {@inheritDoc}
	 */
	public void savePrincipal(final Principal principal) {
		this.principalDao.save(principal);
	}


	/**
	 * {@inheritDoc}
	 */
	public Principal loadPrincipal(final String email) {
		return this.principalDao.load(email);
	}


	/**
	 * {@inheritDoc}
	 */
	public void updatePrincipal(final Principal principal) {
		this.principalDao.update(principal);
	}


	/**
	 * {@inheritDoc}
	 */
	public void deletePrincipal(final Principal principal) {
		this.principalDao.delete(principal);
	}


	/**
	 * {@inheritDoc}
	 */
	public Principal loadPrincipal(final String email, final String password,
			final String domain) {
		return this.principalDao.load(email, password, domain);
	}


	/**
	 * {@inheritDoc}
	 */
	public Collection<String> getAllValidImageFileNames() {
		return this.shoppingCartDao.getAllImageFileNames();
	}


	/**
	 * {@inheritDoc}
	 */
	public Collection<String> getAllDataFileNames() {
		return this.shoppingCartDao.getAllDataFileNames();
	}


	/**
	 * {@inheritDoc}
	 */
	public Collection<Job> loadAllJobs() {
		return this.jobDao.loadAll();
	}


	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdateJob(final Job job) {
		this.jobDao.saveOrUpdate(job);
	}


	/**
	 * {@inheritDoc}
	 */
	public void deleteJob(final Job job) {
		this.jobDao.delete(job);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deletePlot(final Plot plot) {
		this.plotDao.delete(plot);
	}
	
}
