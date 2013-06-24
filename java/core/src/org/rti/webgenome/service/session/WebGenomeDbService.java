/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-05-19 20:11:02 $


*/

package org.rti.webgenome.service.session;

import java.util.Collection;
import java.util.List;

import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.job.Job;

/**
 * This interface is a session facade to transactional
 * operations involving the WebGenome database.
 * @author dhall
 *
 */
public interface WebGenomeDbService {

	/**
	 * Add array object to database and update the shopping
	 * cart.
	 * @param array Array object to save
	 * @param cart Cart to update
	 */
	void saveArrayAndUpdateCart(Array array, ShoppingCart cart);
	
	/**
	 * Get shopping cart associated with given user and security domain
	 * that authenticated that user.
	 * @param user User account name
	 * @param domain Security domain that authenticated user
	 * @return A shopping cart object
	 */
	ShoppingCart loadShoppingCart(Long user, String domain);
	
	/**
	 * Save given cart to persistent storage.
	 * @param cart Cart to save
	 */
	void saveShoppingCart(ShoppingCart cart);
	
	/**
	 * Update given shopping cart.
	 * @param cart Cart to update.
	 */
	void updateShoppingCart(ShoppingCart cart);
	
	/**
	 * Remove arrays associated from given experiment from database if they
	 * are not referenced by other experiments
	 * and persist shopping cart updates.
	 * @param experiment Experiment containing arrays to remove if not
	 * referenced by other experiments
	 * @param cart Cart to update
	 */
	void deleteArraysAndUpdateCart(Experiment experiment, ShoppingCart cart);
	
	/**
	 * Add arrays from given experiment to database and persist
	 * shopping cart changes.
	 * @param experiment Experiment containing arrays to persist.
	 * @param cart Shopping cart to update
	 */
	void addArraysAndUpdateCart(Experiment experiment, ShoppingCart cart);
	
	/**
	 * Update persistent state of given experiment.
	 * @param experiment Experiment whose persistent state is to be
	 * updated.
	 */
	void updateExperiment(Experiment experiment);
	
	/**
	 * Update given experiments and shopping cart.
	 * @param experiments Experiments to update.
	 * @param cart Cart to update.
	 */
	void updateExperimentsAndCart(
			Collection<Experiment> experiments, ShoppingCart cart);
	
	/**
	 * Is <code>Experiment</code> object with given ID referenced
	 * by another persistent object?
	 * @param id Experiment ID
	 * @return T/F
	 */
	boolean isExperimentReferenced(Long id);
	
	/**
	 * Load default organism.
	 * @return Default organism
	 */
	Organism loadDefaultOrganism();
	
	/**
	 * Load all organisms.
	 * @return All organisms
	 */
	List<Organism> loadAllOrganisms();
	
	/**
	 * Load organism with given ID.
	 * @param id Organism ID
	 * @return An organism
	 */
	Organism loadOrganism(Long id);
	
	/**
	 * Is <code>Plot</code> object with given ID
	 * referenced by another persistent object?
	 * @param id Plot ID
	 * @return T/F
	 */
	boolean isPlotReferenced(Long id);
	
	/**
	 * Save given principal to persistent storage.
	 * @param principal Principal to persist
	 */
	void savePrincipal(Principal principal);
	
	/**
	 * Load principal with given account name.
	 * @param email Email
	 * @return A principal or null if there is not
	 * one with given name
	 */
	Principal loadPrincipal(String email);
	
	/**
	 * Update persistent state of given principal.
	 * @param principal Principal whose persistent state will
	 * be updated
	 */
	void updatePrincipal(Principal principal);
	
	/**
	 * Delete given principal from persistent storage.
	 * @param principal Principal to delete
	 */
	void deletePrincipal(Principal principal);
	
	/**
	 * Load principal from given domain with given username and password.
	 * @param email Email
	 * @param password Password
	 * @param domain Security domain
	 * @return A principal or null if there is no account
	 * with the given email and password pairing in the given
	 * security domain
	 */
	Principal loadPrincipal(String email, String password, String domain);
	
	/**
	 * Gets the names of all image files that are referenced
	 * by persistent objects.  Clients are expected to be
	 * able to convert the file names into absolute paths.
	 * @return Image file names (not absolute paths)
	 */
	Collection<String> getAllValidImageFileNames();
	
	/**
	 * Gets the names of all data files that are referenced
	 * by persistent objects.  Clients are expected to be
	 * able to convert the file names into absolute paths.
	 * @return Data file names (not absolute paths)
	 */
	Collection<String> getAllDataFileNames();
	
	/**
	 * Load all compute jobs from persistent storage.
	 * @return All persisted compute jobs
	 */
	Collection<Job> loadAllJobs();
	
	/**
	 * Save or update the given job.
	 * @param job Compute job
	 */
	void saveOrUpdateJob(Job job);
	
	/**
	 * Remove given job from persistent storage.
	 * @param job Job to remove
	 */
	void deleteJob(Job job);
	
	/**
	 * Deletes plot object.
	 * 
	 * @param plot
	 */
	public void deletePlot(final Plot plot);
		
	
}
