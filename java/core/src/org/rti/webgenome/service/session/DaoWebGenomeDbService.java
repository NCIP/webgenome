/*
$Revision: 1.1 $
$Date: 2008-02-22 03:54:09 $

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

package org.rti.webgenome.service.session;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.ArrayDao;
import org.rti.webgenome.service.dao.ExperimentDao;
import org.rti.webgenome.service.dao.OrganismDao;
import org.rti.webgenome.service.dao.PlotDao;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.IOService;

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
		this.arrayDao.save(array);
		this.shoppingCartDao.update(cart);
	}

	/**
	 * {@inheritDoc}
	 */
	public ShoppingCart getShoppingCart(
			final String user, final String domain) {
		return this.shoppingCartDao.load(user, domain);
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
	public void removeArraysAndUpdateCart(
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
			this.arrayDao.save(array);
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
	

}
