/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-12-04 23:06:40 $


*/

package org.rti.webgenome.service.dao;

import java.util.Collection;
import java.util.Set;

import org.rti.webgenome.domain.ShoppingCart;

/**
 * Data access interface for <code>ShoppingCart</code>.
 * @author dhall
 *
 */
public interface ShoppingCartDao {
    
    /**
     * Save to persistent storage.
     * @param shoppingCart A shopping cart
     */
    void save(ShoppingCart shoppingCart);
    
    
    /**
     * Load cart with given identifier from persistent storage.
     * @param id Shopping cart identifier
     * @return A shopping cart or null if there is no cart
     * with given id.
     */
    ShoppingCart load(Long id);
    
    
    /**
     * Load cart associated with given user name from
     * persistent storage.
     * @param userId User Id (unique user identifier from Principal)
     * @param domain The domain in which the user name applies
     * @return A shopping cart or null if there is not one
     * for given user.
     */
    ShoppingCart load(Long userId, String domain);
    
    
    /**
     * Update persistently stored properties of given cart.
     * @param shoppingCart A shopping cart
     */
    void update(ShoppingCart shoppingCart);
    
    
    /**
     * Delete given cart from persistent storage.
     * @param shoppingCart A shopping cart
     */
    void delete(ShoppingCart shoppingCart);

    /**
     * Get names of all image files in shopping
     * cart.
     * @return Names of all image files in shopping
     * cart.
     */
    Collection<String> getAllImageFileNames();
    
    /**
     * Get name of all data files in all shopping carts.
     * @return Names of data files, not absolute paths.
     * These include files containing experimental results,
     * reporters, and plot interactivity object serializations.
     */
    Set<String> getAllDataFileNames();
}
