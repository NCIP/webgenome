/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.OrganismDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>OrganismDao</code> using Hibernate.
 * @author dhall
 *
 */
public final class HibernateOrganismDao extends HibernateDaoSupport
    implements OrganismDao {
    
    /**
     * Save to persistent storage.
     * @param organism Organism
     */
    public void save(final Organism organism) {
        this.getHibernateTemplate().save(organism);
    }
    
    /**
     * Load organism associated with given identifier
     * from storage.
     * @param id Identifier
     * @return An organism
     */
    public Organism load(final Long id) {
    	
    	// The following code is commented out due to an
    	// apparent defect in HibernateTemplate whereby
    	// the attributes of Organism are not properly set.
    	// The setter methods for these properies are never
    	// invoked.
    	
//        return (Organism)
//            this.getHibernateTemplate().load(Organism.class, id);
    	
    	// The following is a workaround for the issue described
    	// with the above code
    	Organism org = null;
    	String query = 
            "from Organism org where id = ?";
        Object[] args = new Object[]{id};
        List results = this.getHibernateTemplate().find(query, args);
        if (results != null && results.size() > 0) {
            org = (Organism) results.get(0);
        }
    	return org;
    }
    
    
    /**
     * Load organism with given genus and species
     * names from persistent storage.
     * @param genus Genus
     * @param species Species
     * @return An organism
     */
    public Organism load(final String genus, final String species) {
        Organism org = null;
        String query = 
            "from Organism org where genus = ? and species = ?";
        Object[] args = new Object[]{genus, species};
        List results = this.getHibernateTemplate().find(query, args);
        if (results != null && results.size() > 0) {
            org = (Organism) results.get(0);
        }
        return org;
    }
    
    
    /**
     * Load default organism from persistent storage.  If
     * the organism, defined by id (e.g., primary key) value
     * of 1, is not in database, throw 
     * <code>WebcghSystemException</code>.
     * @return An organism
     */
    public Organism loadDefault() {
        Organism org = null;
        String query = 
            "from Organism org where id = 1";
        List results = this.getHibernateTemplate().find(query);
        if (results != null && results.size() > 0) {
            org = (Organism) results.get(0);
        } else {
            throw new WebGenomeSystemException(
                    "Default organism not loaded in database");
        }
        return org;
    }
    
    
    /**
     * Delete given organism from persistent storage.
     * @param organism An organism
     */
    public void delete(final Organism organism) {
        this.getHibernateTemplate().delete(organism);
    }

    
    /**
     * Load all organisms.
     * @return All organisms.
     */
    public List<Organism> loadAll() {
    	List allOrgs = this.getHibernateTemplate().loadAll(Organism.class);
    	List<Organism> checkedAllOrgs = new ArrayList<Organism>();
    	for (Iterator it = allOrgs.iterator(); it.hasNext();) {
    		checkedAllOrgs.add((Organism) it.next());
    	}
    	return checkedAllOrgs;
    }
}
