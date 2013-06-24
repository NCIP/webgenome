/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:28 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.IllegalArgumentException;

import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.CytologicalMapDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>CytologicalMapDao</code> using
 * Hibernate.
 * @author dhall
 *
 */
public final class HibernateCytologicalMapDao
extends HibernateDaoSupport implements CytologicalMapDao {

	
	/**
	 * Save given map to persistent storage.
	 * @param cytologicalMap Cytological map.
	 */
	public void save(final CytologicalMap cytologicalMap) {
        if ( cytologicalMap == null )
            throw new IllegalArgumentException ( "cytologicalMap parameter cannot be null" ) ;
		this.getHibernateTemplate().save(cytologicalMap);
	}
	
	/**
	 * Load cytological map from persistent storage.
	 * @param id Primary key identifier of
	 * cytological map.
	 * @return Cytological map corresponding to
	 * given id.  Throws an exception if a cytological
	 * map is not available with given ID.
	 */
	public CytologicalMap load(final Long id) {
		return (CytologicalMap)
			this.getHibernateTemplate().load(CytologicalMap.class, id);
	}
	
	/**
	 * Load cytological map from persistent storage.
	 * @param organism An organism
	 * @param chromosome Chromosome number
	 * @return A cytological map or null if there
	 * is no map from given organism and chromosome.
	 */
	public CytologicalMap load(final Organism organism,
			final short chromosome) {
        
        if ( organism == null )
            throw new IllegalArgumentException ( "Organism parameter cannot be null." ) ;

		String query = "from CytologicalMap m "
			+ "where m.organism = ? and m.chromosome = ?";
		Object[] args = new Object[] {organism, chromosome};
		List maps = this.getHibernateTemplate().find(query, args);
		CytologicalMap map = null;
		if (maps.size() > 0) {
			map = (CytologicalMap) maps.get(0);
		}
		return map;
	}

	/**
	 * Delete given cytological map from persistent storage.
	 * @param cytologicalMap Cytological map to delete.
	 */
	public void delete(final CytologicalMap cytologicalMap) {
        if ( cytologicalMap == null )
            throw new IllegalArgumentException ( "cytologicalMap parameter cannot be null" ) ;
		this.getHibernateTemplate().delete(cytologicalMap);
	}
	
	
	/**
	 * Load all cytological maps.
	 * @return All cytological maps.
	 */
	public List<CytologicalMap> loadAll() {
		List all = this.getHibernateTemplate().loadAll(CytologicalMap.class);
		List<CytologicalMap> checked = new ArrayList<CytologicalMap>();
		for (Iterator it = all.iterator(); it.hasNext();) {
			checked.add((CytologicalMap) it.next());
		}
		return checked;
	}
	
	
	/**
	 * Delete all cytological maps from given organism.
	 * @param organism Organism.
	 */
	public void deleteAll(final Organism organism) {
        if ( organism == null )
            throw new IllegalArgumentException ( "organism parameter cannot be null" ) ;
		String query = "from CytologicalMap m where m.organism = ?";
		Object[] args = new Object[] {organism};
		List maps = this.getHibernateTemplate().find(query, args);
		this.getHibernateTemplate().deleteAll(maps);
	}
}
