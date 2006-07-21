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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.dao.OrganismDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.
    DataFieldMaxValueIncrementer;

/**
 * Implementation of the <code>OrganismDao</code> interface
 * using JDBC.
 * @author dhall
 *
 */
public final class JdbcOrganismDao implements OrganismDao {
    
    // ===============================
    //      Attributes
    // ===============================
    
    /** Spring JDBC template. This property should be injected. */
    private JdbcTemplate jdbcTemplate = null;
    
    /** Incrementer for primary key. This property should be injected. */
    private DataFieldMaxValueIncrementer incrementer = null;
    
    
    // =======================================
    //     Setters for dependency injection
    // =======================================
    
    /**
     * Set the primary key incrementer for inserts.  This method
     * should only be used for dependency injection.
     * @param incrementer Incrementer for primary key values.
     */
    public void setIncrementer(final DataFieldMaxValueIncrementer incrementer) {
        this.incrementer = incrementer;
    }

    /**
     * Set the Spring JDBC template.  This method should only
     * be used for dependency injection.
     * @param jdbcTemplate Spring JDBC template
     */
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    // ==============================
    //    Organism interface
    // ==============================
    
    /**
     * Save to persistent storage.
     * @param organism Organism
     */
    public void save(final Organism organism) {
        
        // Get next primary key value
        long id = this.incrementer.nextLongValue();
        
        // Insert new record
        String sql =
            "insert into organism_2(id, genus, species) "
            + "values (?, ?, ?)";
        Object[] args = new Object[] {id, organism.getGenus(), 
                organism.getSpecies()};
        this.jdbcTemplate.update(sql, args);
        
        // Update organims object
        organism.setId(id);
    }
    
    /**
     * Load organism associated with given identifier
     * from storage.
     * @param id Identifier
     * @return An organism or null if one is not found
     * with given identifier
     */
    public Organism load(final Long id) {
        Organism org = null;
        String sql = "select * from organism_2 where id = ?";
        Object[] args = new Object[]{id};
        List orgs = 
            this.jdbcTemplate.query(sql, args, new OrganismRowMapper());
        if (orgs.size() > 0) {
            org = (Organism) orgs.get(0);
        }
        return org;
    }
    
    
    /**
     * Load organism with given genus and species
     * names from persistent storage.
     * @param genus Genus
     * @param species Species
     * @return An organism or null if one is not found
     * with given identifier
     */
    public Organism load(final String genus, final String species) {
        Organism org = null;
        String sql = "select * from organism_2 where genus = ? and species = ?";
        Object[] args = new Object[]{genus, species};
        List orgs = 
            this.jdbcTemplate.query(sql, args, new OrganismRowMapper());
        if (orgs.size() > 0) {
            org = (Organism) orgs.get(0);
        }
        return org;
    }
    
    
    /**
     * Load default organism from persistent storage.
     * @return An organism
     */
    public Organism loadDefault() {
        Organism org = null;
        String sql = "select * from organism_2 where id = 1";
        List orgs = 
            this.jdbcTemplate.query(sql, new OrganismRowMapper());
        if (orgs.size() > 0) {
            org = (Organism) orgs.get(0);
        }
        return org;
    }
    
    
    /**
     * Delete given organism from persistent storage.
     * @param organism An organism
     */
    public void delete(final Organism organism) {
        
        // Remove from database
        String sql = "delete from organism_2 where id = ?";
        Object[] args = new Object[] {organism.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update organism object
        organism.setId(null);
    }
    
    
    /**
     * Helper class to map rows in a result set to
     * <code>Organism</code> objects.
     * @author dhall
     *
     */
    static final class OrganismRowMapper implements RowMapper {
        
        /**
         * Map a row in given result set to an Organism object.
         * @param rs Result set
         * @param index Row index
         * @return An organism
         * @throws SQLException if there is a database problem
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            Organism org = new Organism();
            org.setId(rs.getLong("id"));
            org.setGenus(rs.getString("genus"));
            org.setSpecies(rs.getString("species"));
            return org;
        }
    }

}
