/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.service.dao.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.service.dao.OrganismDao;
import org.rti.webcgh.service.dao.ShoppingCartDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.
    DataFieldMaxValueIncrementer;

/**
 * Implementation of <code>ShoppingCartDao</code>
 * using JDBC.
 * @author dhall
 *
 */
public final class JdbcShoppingCartDao implements ShoppingCartDao {
    
    // ===============================
    //      Attributes
    // ===============================
    
    /** Spring JDBC template. This property should be injected. */
    private JdbcTemplate jdbcTemplate = null;
    
    /**
     * Incrementer for primary key of shopping_cart_table.
     * This property should be injected.
     */
    private DataFieldMaxValueIncrementer shoppingCartIncrementer = null;
    
    /**
     * Incrementer for primary key of experiment table.
     * This property should be injected.
     */
    private DataFieldMaxValueIncrementer experimentIncrementer = null;
    
    /**
     * Incrementer for primary key of bioassay table.
     * This property should be injected.
     */
    private DataFieldMaxValueIncrementer bioAssayIncrementer = null;
    
    
    /**
     * Data access object for organisms.
     * This property should be injected.
     */
    private OrganismDao organismDao = null;
    
    
    // ======================================
    //    Setters for dependency injection
    // ======================================
    
    /**
     * Set data access object for organisms.  This
     * property should always be injected.
     * @param organismDao An organism data access object
     */
    public void setOrganismDao(final OrganismDao organismDao) {
        this.organismDao = organismDao;
    }


    /**
     * Set incrementer used for generating primary key values
     * for the shopping_cart table.
     * This property should always be injected.
     * @param incrementer Primary key value incrementer
     */
    public void setShoppingCartIncrementer(
            final DataFieldMaxValueIncrementer incrementer) {
        this.shoppingCartIncrementer = incrementer;
    }
    
    
    /**
     * Set incrementer used for generating primary key values
     * for the experiment table.
     * This property should always be injected.
     * @param bioAssayIncrementer Primary key value incrementer
     */
    public void setBioAssayIncrementer(
            final DataFieldMaxValueIncrementer bioAssayIncrementer) {
        this.bioAssayIncrementer = bioAssayIncrementer;
    }


    /**
     * Set incrementer used for generating primary key values
     * for the bio_assay table.
     * This property should always be injected.
     * @param experimentIncrementer Primary key value incrementer
     */
    public void setExperimentIncrementer(
            final DataFieldMaxValueIncrementer experimentIncrementer) {
        this.experimentIncrementer = experimentIncrementer;
    }


    /**
     * Set Spring JDBC template.  This property should always be
     * injected.
     * @param jdbcTemplate Spring JDBC template
     */
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // =================================
    //    ShoppingCartDao interface
    // =================================
    

    /**
     * Save to persistent storage.
     * @param shoppingCart A shopping cart
     */
    public void save(final ShoppingCart shoppingCart) {
        
        // Insert new shopping cart record
        long id = this.shoppingCartIncrementer.nextLongValue();
        String sql =
            "insert into shopping_cart_2 (id, user_name) values (?, ?)";
        Object[] args = new Object[] {id, shoppingCart.getUserName()};
        this.jdbcTemplate.update(sql, args);
        
        // Insert new experiment records
        for (Experiment exp : shoppingCart.getExperiments()) {
            this.save(exp, id);
        }
        
        // Update shopping cart properties
        shoppingCart.setId(id);
    }
    
    
    /**
     * Save experiment which is in cart.
     * @param exp An experiment
     * @param shoppingCartId Identifier of shopping cart
     * containing this experiment.
     */
    private void save(final Experiment exp, final long shoppingCartId) {
        
        // Insert new experiment record
        long id = this.experimentIncrementer.nextLongValue();
        String sql = "insert into experiment_2 (id, name, shopping_cart_id) "
            + "values (?, ?, ?)";
        Object[] args = new Object[] {id, exp.getName(), shoppingCartId};
        this.jdbcTemplate.update(sql, args);
        
        // Insert bioassays
        for (BioAssay ba : exp.getBioAssays()) {
            this.save(ba, id);
        }
        
        // Update experiment properties
        exp.setId(id);
    }
    
    
    /**
     * Save bioassay which is in cart.
     * @param bioAssay Bioassay to save
     * @param experimentId Identifier of experiment containing
     * this bioassay.
     */
    public void save(final BioAssay bioAssay, final long experimentId) {
        
        // Create new bioassay record
        long id = this.bioAssayIncrementer.nextLongValue();
        String sql = "insert into bio_assay_2(id, name, bio_assay_data_id, "
            + "experiment_id, organism_id) values (?, ?, ?, ?, ?)";
        Object[] args = new Object[] {id, bioAssay.getName(),
                bioAssay.getBioAssayDataId(), experimentId,
                bioAssay.getOrganism().getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update bioassay properties
        bioAssay.setId(id);
    }
    
    
    /**
     * Load cart with given identifier from persistent storage.
     * @param id Shopping cart identifier
     * @return A shopping cart or null if there is no cart
     * with given id.
     */
    public ShoppingCart load(final Long id) {
        
        // Load cart
        String sql = "select * from shopping_cart_2 where id = ?";
        Object[] args = new Object[] {id};
        ShoppingCart cart = (ShoppingCart)
            this.jdbcTemplate.queryForObject(sql, args,
                    new ShoppingCartRowMapper());
        
        // Load associated experiments
        this.loadExperiments(cart);
        
        return cart;
    }
    
    
    /**
     * Load experiments associated with given shopping
     * cart.  Experiments are added to given shopping cart.
     * @param cart A shopping cart
     */
    private void loadExperiments(final ShoppingCart cart) {
        String sql = "select * from experiment_2 where shopping_cart_id = ?";
        Object[] args = new Object[] {cart.getId()};
        List exps = this.jdbcTemplate.query(sql, args,
                new ExperimentRowMapper());
        for (Iterator it = exps.iterator(); it.hasNext();) {
            Experiment exp = (Experiment) it.next();
            cart.add(exp);
            this.loadBioAssays(exp);
        }
    }
    
    
    /**
     * Load bioassays associated with given experiment.
     * Bioassays are added to given experiment.
     * @param exp An experiment
     */
    private void loadBioAssays(final Experiment exp) {
        String sql = "select * from bio_assay_2 where experiment_id = ?";
        Object[] args = new Object[] {exp.getId()};
        List bas = this.jdbcTemplate.query(sql, args, new BioAssayRowMapper());
        for (Iterator it = bas.iterator(); it.hasNext();) {
            BioAssay ba = (BioAssay) it.next();
            exp.add(ba);
        }
    }
    
    
    /**
     * Load cart associated with given user name from
     * persistent storage.
     * @param userName User name
     * @return A shopping cart or null if there is not one
     * for given user.
     */
    public ShoppingCart load(final String userName) {
        String sql = "select * from shopping_cart_2 where user_name = ?";
        Object[] args = new Object[] {userName};
        List carts =
            this.jdbcTemplate.query(sql, args, new ShoppingCartRowMapper());
        ShoppingCart cart = null;
        if (carts.size() > 0) {
            cart = (ShoppingCart) carts.get(0);
            this.loadExperiments(cart);
        }
        return cart;
    }
    
    
    /**
     * Update persistently stored properties of given cart.
     * Note: the only changes to be expected are the
     * addition and deletion of experiments.  These are
     * the only changes that the method looks for.
     * @param shoppingCart A shopping cart
     */
    public void update(final ShoppingCart shoppingCart) {
        
        // Make sure shopping cart has been previously persisted
        if (shoppingCart.getId() == null) {
            throw new IllegalArgumentException(
                    "ShoppingCart instance has not been persisted");
        }
        
        // Get previous state of cart
        ShoppingCart oldCart = this.load(shoppingCart.getId());
        
        // Purge experiment records not in the current shopping
        // cart from database
        this.purgeExperiments(shoppingCart, oldCart);
        
        // Add new experiment records not in database
        this.addExperiments(shoppingCart, oldCart);
    }
    
    
    /**
     * Synchronize database with state of given shopping
     * cart by purging experiments no longer in cart
     * from database.
     * @param cart A shopping cart representing the current
     * state of the cart
     * @param oldCart A shopping cart reprsenting the previous
     * (and persisted) state of the cart
     */
    private void purgeExperiments(final ShoppingCart cart,
            final ShoppingCart oldCart) {
        for (Experiment exp1 : oldCart.getExperiments()) {
            boolean deleted = true;
            for (Experiment exp2 : cart.getExperiments()) {
                if (exp1.synonymousWith(exp2)) {
                    deleted = false;
                    break;
                }
            }
            if (deleted) {
                this.delete(exp1);
            }
        }
    }
    
    
    /**
     * Synchronize database with state of given shopping
     * cart by adding new experimetn records to the
     * database.
     * @param cart A shopping cart representing the current
     * state of the cart
     * @param oldCart A shopping cart that represents the previous
     * (persisted) state of the cart.
     */
    private void addExperiments(final ShoppingCart cart,
            final ShoppingCart oldCart) {
        for (Experiment exp1 : cart.getExperiments()) {
            boolean inserted = true;
            for (Experiment exp2 : oldCart.getExperiments()) {
                if (exp1.synonymousWith(exp2)) {
                    inserted = false;
                    break;
                }
            }
            if (inserted) {
                this.save(exp1, cart.getId());
            }
        }
    }
    
    
    /**
     * Delete given cart from persistent storage.
     * @param shoppingCart A shopping cart
     */
    public void delete(final ShoppingCart shoppingCart) {
        
        // Delete experiments
        for (Experiment exp : shoppingCart.getExperiments()) {
            this.delete(exp);
        }
        
        // Delete shopping cart record
        String sql = "delete from shopping_cart_2 where id = ?";
        Object[] args = new Object[] {shoppingCart.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update shopping cart properties
        shoppingCart.setId(null);
    }
    
    
    /**
     * Delete experiment that is in shopping cart.
     * @param exp An experiment
     */
    private void delete(final Experiment exp) {
        
        // Delete bioassays
        for (BioAssay ba : exp.getBioAssays()) {
            this.delete(ba);
        }
        
        // Delete experiment record
        String sql = "delete from experiment_2 where id = ?";
        Object[] args = new Object[] {exp.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update experiment properties
        exp.setId(null);
    }
    
    
    /**
     * Delete bioassay that is in shopping cart.
     * @param ba A bioassay
     */
    private void delete(final BioAssay ba) {
        
        // Remove record
        String sql = "delete from bio_assay_2 where id = ?";
        Object[] args = new Object[] {ba.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update bioassay properties
        ba.setId(null);
    }
    
    
    /**
     * Helper class to map rows returned from database query
     * to <code>ShoppingCart</code> objects.
     * @author dhall
     *
     */
    static class ShoppingCartRowMapper implements RowMapper {

        /**
         * Map row in result set to single shopping cart object.
         * @param rs Result set
         * @param index Index of result set row to map
         * @return A <code>ShoppingCart</code> object
         * @throws SQLException if a database error occurs
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            ShoppingCart cart = new ShoppingCart();
            cart.setId(rs.getLong("id"));
            cart.setUserName(rs.getString("user_name"));
            return cart;
        }
    }

    
    /**
     * Helper class to map rows returned from database query
     * to <code>Experiment</code> objects.
     * @author dhall
     *
     */
    static class ExperimentRowMapper implements RowMapper {

        /**
         * Map row in result set to single shopping cart object.
         * @param rs Result set
         * @param index Index of result set row to map
         * @return An <code>Experiment</code> object
         * @throws SQLException if a database error occurs
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            Experiment exp = new Experiment();
            exp.setId(rs.getLong("id"));
            exp.setName(rs.getString("name"));
            return exp;
        }
    }
    
    
    /**
     * Helper class to map rows returned from database query
     * to <code>BioAssay</code> objects.
     * @author dhall
     *
     */
    class BioAssayRowMapper implements RowMapper {

        /**
         * Map row in result set to single shopping cart object.
         * @param rs Result set
         * @param index Index of result set row to map
         * @return An <code>BioAssay</code> object
         * @throws SQLException if a database error occurs
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            BioAssay ba = new BioAssay();
            ba.setId(rs.getLong("id"));
            ba.setName(rs.getString("name"));
            ba.setBioAssayDataId(rs.getLong("bio_assay_data_id"));
            ba.setOrganism(organismDao.load(rs.getLong("organism_id")));
            return ba;
        }
    }
}
