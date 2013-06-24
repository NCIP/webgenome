/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.dao;

import java.util.List;

import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;

/**
 * Data access class for <code>CytologicalMap</code>.
 * @author dhall
 *
 */
public interface CytologicalMapDao {
	
	/**
	 * Save given map to persistent storage.
	 * @param cytologicalMap Cytological map.
	 */
	void save(CytologicalMap cytologicalMap);
	
	/**
	 * Load cytological map from persistent storage.
	 * @param id Primary key identifier of
	 * cytological map.
	 * @return Cytological map corresponding to
	 * given id.  Throws an exception if a cytological
	 * map is not available with given ID.
	 */
	CytologicalMap load(Long id);
	
	/**
	 * Load cytological map from persistent storage.
	 * @param organism An organism
	 * @param chromosome Chromosome number
	 * @return A cytological map or null if there
	 * is no map from given organism and chromosome.
	 */
	CytologicalMap load(Organism organism, short chromosome);

	/**
	 * Delete given cytological map from persistent storage.
	 * @param cytologicalMap Cytological map to delete.
	 */
	void delete(CytologicalMap cytologicalMap);
	
	/**
	 * Load all cytological maps.
	 * @return All cytological maps.
	 */
	List<CytologicalMap> loadAll();
	
	/**
	 * Delete all cytological maps from given organism.
	 * @param organism Organism.
	 */
	void deleteAll(Organism organism);
}
