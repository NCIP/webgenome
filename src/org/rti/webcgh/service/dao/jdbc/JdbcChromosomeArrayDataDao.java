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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.service.dao.ChromosomeArrayDataDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.
    DataFieldMaxValueIncrementer;

/**
 * Implementation of <code>ChromosomeArrayDataDao</code>
 * using JDBC.
 * @author dhall
 *
 */
public final class JdbcChromosomeArrayDataDao
    implements ChromosomeArrayDataDao {
    
    // ===============================
    //      Attributes
    // ===============================
    
    /** Spring JDBC template. This property should be injected. */
    private JdbcTemplate jdbcTemplate = null;
    
    /**
     * Incrementer for primary key of chromosome_array_data table.
     * This property should be injected.
     * */
    private DataFieldMaxValueIncrementer
        chromosomeArrayDataIncrementer = null;
    
    /**
     * Incrementer for primary key of chromosome_array_data table.
     * This property should be injected.
     */
    private DataFieldMaxValueIncrementer arrayDatumIncrementer = null;
    
    /** Reporter data access object.  This property should be injected. */
    private JdbcReporterDao reporterDao = null;
    
    /**
     * Cache of <code>Reporter</code> objects used to ensure
     * all references to the same reporter by different <code>ArrayDatum</code>
     * objects will actually point to the same <code>Reporter</code> instance.
     * @author dhall
     */
    private final ReporterCache reporterCache = new ReporterCache();
    
    /**
     * Row mapper used to instantiate <code>ArrayDatum</code>
     * objects from database query results.
     */
    private final ArrayDatumRowMapper arrayDatumRowMapper =
        new ArrayDatumRowMapper();
    
    
    // =======================================
    //     Setters for dependency injection
    // =======================================
    
    /**
     * Set the primary key incrementer for inserts into
     * the table chromosome_array_data.  This method
     * should only be used for dependency injection.
     * @param incrementer Incrementer for primary key values.
     */
    public void setChromosomeArrayDataIncrementer(
            final DataFieldMaxValueIncrementer incrementer) {
        this.chromosomeArrayDataIncrementer = incrementer;
    }

    /**
     * Set the primary key incrementer for inserts into
     * the table array_datum.  This method
     * should only be used for dependency injection.
     * @param arrayDatumIncrementer Incrementer for primary key values.
     */
    public void setArrayDatumIncrementer(
            final DataFieldMaxValueIncrementer arrayDatumIncrementer) {
        this.arrayDatumIncrementer = arrayDatumIncrementer;
    }

    /**
     * Set the Spring JDBC template.  This method should only
     * be used for dependency injection.
     * @param jdbcTemplate Spring JDBC template
     */
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Set reporter DAO.  This method should only be used
     * for dependency injection.
     * @param reporterDao Reporter data access object
     */
    public void setReporterDao(final JdbcReporterDao reporterDao) {
        this.reporterDao = reporterDao;
    }

    // ==================================
    //  ChromosomeArrayDataDao interface
    // ==================================
    
    /**
     * Save to persistent storage.
     * @param chromosomeArrayData Chromosome array data
     */
    public void save(final ChromosomeArrayData chromosomeArrayData) {
        
        // Get primary key
        long id = this.chromosomeArrayDataIncrementer.nextLongValue();
        
        // chromosome_array_data table
        String sql =
            "insert into chromosome_array_data_2 "
            + "(id, bio_assay_data_id, chromosome) "
            + "values (?, ?, ?)";
        Object[] args = new Object[]{id,
                chromosomeArrayData.getBioAssayDataId(),
                chromosomeArrayData.getChromosome()};
        this.jdbcTemplate.update(sql, args);
        
        // Save array datum objects
        for (ArrayDatum d : chromosomeArrayData.getArrayData()) {
            this.save(d, id);
        }
        
        // Update chromosome array data object
        chromosomeArrayData.setId(id);
    }
    
    /**
     * Save array datum in database.
     * @param arrayDatum Array datum
     * @param cadId Identifier of parent <code>ChromosomeArrayData</code>
     * object.
     */
    private void save(final ArrayDatum arrayDatum, final long cadId) {
        
        // Get primary key value
        long id = this.arrayDatumIncrementer.nextLongValue();
        
        // Save data to database
        String sql =
            "insert into array_datum_2 "
            + "(id, value, error, chromosome_array_data_id, reporter_id) "
            + "values (?, ?, ?, ?, ?)";
        Object[] args = new Object[] {id, arrayDatum.getValue(),
                arrayDatum.getError(), cadId, arrayDatum.getReporter().getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update array datum object
        arrayDatum.setId(id);
    }
    
    
    /**
     * Load chromosome array data denoted by given identifier.
     * @param id Identifier.
     * @return Chromosome array data or null if no record
     * with given id is in database
     */
    public ChromosomeArrayData load(final Long id) {
        String sql =
            "select * from chromosome_array_data_2 where id = ?";
        Object[] args = new Object[] {id};
        ChromosomeArrayData cad = null;
        List cads = this.jdbcTemplate.query(sql, args, 
                new ChromosomeArrayDataRowMapper());
        if (cads != null && cads.size() > 0) {
            cad = (ChromosomeArrayData) cads.get(0);
            this.addArrayData(cad);
        }
        return cad;
    }
    
    
    /**
     * Method to be called after retrieving data from
     * chromosome_array_data table.  This method adds
     * <code>ArrayDatum</code> objects to the given
     * chromosome array data.
     * @param cad Chromosome array data
     */
    private void addArrayData(final ChromosomeArrayData cad) {
        String sql =
            "select * from array_datum_2 where chromosome_array_data_id = ?";
        Object[] args = new Object[] {cad.getId()};
        List data = this.jdbcTemplate.query(sql, args,
                this.arrayDatumRowMapper);
        for (Iterator it = data.iterator(); it.hasNext();) {
            ArrayDatum ad = (ArrayDatum) it.next();
            cad.add(ad);
        }
    }
    
    
    /**
     * Load chromosom array data denoted by chromosome number
     * and bioassay data identifier.
     * @param chromosome Chromosome number
     * @param bioAssayDataId Bioassay data identifier
     * @return Chromosome array data or null if no record
     * with given arguments in in database
     */
    public ChromosomeArrayData load(final short chromosome,
            final Long bioAssayDataId) {
        ChromosomeArrayData cad = null;
        String sql = "select id from chromosome_array_data_2 "
            + "where chromosome = ? and bio_assay_id = ?";
        Object[] args = new Object[] {chromosome, bioAssayDataId};
        Long id = this.jdbcTemplate.queryForLong(sql, args);
        if (id != null) {
            cad = this.load(id);
        }
        return cad;
    }
    
    
    /**
     * Delete from persistent storage.
     * @param chromosomeArrayData Chromosome array data
     */
    public void delete(final ChromosomeArrayData chromosomeArrayData) {
        
        // First delete array datum records
        String sql =
            "delete from array_datum_2 where chromosome_array_data_id = ?";
        Object[] args = new Object[] {chromosomeArrayData.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Now delete chromosome record
        sql = "delete from chromosome_array_data_2 where id = ?";
        args = new Object[] {chromosomeArrayData.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update array datum objects
        for (ArrayDatum ad : chromosomeArrayData.getArrayData()) {
            ad.setId(null);
        }
        
        // Finally update chromosome array data objects
        chromosomeArrayData.setId(null);
    }
    
    
    // TODO: Not sure this method is actually needed
    /**
     * Update persistently-stored properties for
     * given chromosome array data.
     * @param chromosomeArrayData Chromosome arraydata
     */
    public void update(final ChromosomeArrayData chromosomeArrayData) {
        
    }
    
    
    /**
     * Helper class for caching reporter instances in memory.
     * This cache is used
     * to store instances of <code>Reporter</code> objects so that
     * all references to the same reporter by different <code>ArrayDatum</code>
     * objects will actually point to the same <code>Reporter</code> instance.
     * @author dhall
     */
    class ReporterCache {
        
        /**
         * Map for indexing <code>Reporter</code> objects by
         * <code>id</code> attribute.
         */
        private final Map<Long, Reporter> reporterIndex =
            new HashMap<Long, Reporter>();
        
        /**
         * Get reporter referenced by given identifier.
         * @param id Identifier of reporter.
         * @return A reporter, or null if one cannot be found
         * matching given identifier
         */
        public Reporter getReporter(final Long id) {
            Reporter r = this.reporterIndex.get(id);
            if (r == null) {
                r = reporterDao.load(id);
                if (r != null) {
                    this.reporterIndex.put(id, r);
                }
            }
            return r;
        }
    }
    
    
    /**
     * Helper class for mapping result set rows into
     * <code>ArrayDatum</code> objects.
     * @author dhall
     *
     */
    class ArrayDatumRowMapper implements RowMapper {

        /**
         * Map row in result set to an array datum object.
         * @param rs Result set
         * @param index Index of row in result set
         * @return An array datum object
         * @throws SQLException if something goes wrong in
         * database interaction
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            ArrayDatum ad = new ArrayDatum();
            ad.setId(rs.getLong("id"));
            ad.setValue(rs.getFloat("value"));
            ad.setError(rs.getFloat("value"));
            Long reporterId = rs.getLong("reporter_id");
            ad.setReporter(reporterCache.getReporter(reporterId));
            return ad;
        }
        
    }
    
    
    /**
     * Helper class for mapping result set rows into
     * <code>ChromosomeArrayData</code> objects.
     * @author dhall
     *
     */
    static class ChromosomeArrayDataRowMapper implements RowMapper {

        /**
         * Map row in result set to a chromosome array data
         * object.
         * @param rs Result set
         * @param index Index of row in result set
         * @return An array datum object
         * @throws SQLException if something goes wrong in
         * database interaction
         */
        public Object mapRow(final ResultSet rs, final int index)
            throws SQLException {
            ChromosomeArrayData cad = new ChromosomeArrayData();
            cad.setId(rs.getLong("id"));
            cad.setChromosome(rs.getShort("chromosome"));
            cad.setBioAssayDataId(rs.getLong("bio_assay_data_id"));
            return cad;
        }
        
    }
}
