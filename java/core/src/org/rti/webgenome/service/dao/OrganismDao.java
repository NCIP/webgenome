/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.dao;

import java.util.List;

import org.rti.webgenome.domain.Organism;

/**
 * Data access interface for <code>Organism</code>.
 * @author dhall
 *
 */
public interface OrganismDao {
    
    /**
     * Save to persistent storage.
     * @param organism Organism
     */
    void save(Organism organism);
    
    /**
     * Load organism associated with given identifier
     * from storage.
     * @param id Identifier
     * @return An organism or null if one is not found
     * with given identifier
     */
    Organism load(Long id);
    
    
    /**
     * Load organism with given genus and species
     * names from persistent storage.
     * @param genus Genus
     * @param species Species
     * @return An organism or null if one is not
     * found with given arguments
     */
    Organism load(String genus, String species);
    
    
    /**
     * Load default organism from persistent storage.
     * @return An organism
     */
    Organism loadDefault();
    
    
    /**
     * Delete given organism from persistent storage.
     * @param organism An organism
     */
    void delete(Organism organism);

    
    /**
     * Load all organisms.
     * @return All organisms.
     */
    List<Organism> loadAll();
}
