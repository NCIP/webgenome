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
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.service.dao.ReporterDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

/**
 * Implementation of <code>ReporterDao</code> using
 * JDBC.
 * @author dhall
 *
 */
public final class JdbcReporterDao implements ReporterDao {
    
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
    
    
    // ==================================
    //     ReporterDao interface
    // ==================================
    
    /**
     * Save reporter.
     * @param reporter A reporter
     */
    public void save(final Reporter reporter) {
        
        // Get primary key value
        long id = this.incrementer.nextLongValue();
        
        // Insert new record
        String sql =
            "insert into reporter_2 (id, name, chromosome, location) "
            + "values (?, ?, ?, ?)";
        Object[] args = new Object[] {id, reporter.getName(),
                reporter.getChromosome(), reporter.getLocation()};
        this.jdbcTemplate.update(sql, args);
        
        // Update reporter object
        reporter.setId(id);
    }
    
    /**
     * Load reporter with given id.
     * @param id Reporter id
     * @return A reporter, or null if no reporter has
     * given id.
     */
    public Reporter load(final Long id) {
        Reporter rep = null;
        String sql = "select * from reporter_2 where id = ?";
        Object[] args = new Object[] {id};
        List reporters = 
            this.jdbcTemplate.query(sql, args, new ReporterRowMapper());
        if (reporters != null && reporters.size() > 0) {
            rep = (Reporter) reporters.get(0);
        }
        return rep;
    }
    
    
    /**
     * Delete given reporter from persistent store.
     * @param reporter A reporter
     */
    public void delete(final Reporter reporter) {
        
        // Remove record from database
        String sql = "delete from reporter_2 where id = ?";
        Object[] args = new Object[] {reporter.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update reporter object
        reporter.setId(null);
    }
    
    
    /**
     * Helper class to map rows in a result set to
     * <code>Reporter</code> objects.
     * @author dhall
     *
     */
    static final class ReporterRowMapper implements RowMapper {
        
        /**
         * Map a row in given result set to a Reporter object.
         * @param rs Result set
         * @param index Row index
         * @return An organism
         * @throws SQLException if there is a database problem
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            Reporter r = new Reporter();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setChromosome(rs.getShort("chromosome"));
            r.setLocation(rs.getLong("location"));
            return r;
        }
    }
}
