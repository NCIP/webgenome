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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rti.webcgh.domain.BioAssayData;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.service.dao.BioAssayDataDao;
import org.rti.webcgh.service.dao.ChromosomeArrayDataDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.
    DataFieldMaxValueIncrementer;

/**
 * Implementation of <code>BioAssayDataDao</code> using JDBC.
 * @author dhall
 *
 */
public final class JdbcBioAssayDataDao implements BioAssayDataDao {
    
    // ===============================
    //      Attributes
    // ===============================
    
    /** Spring JDBC template. This property should be injected. */
    private JdbcTemplate jdbcTemplate = null;
    
    /**
     * Incrementer for primary key of bio_assay_data table.
     * This property should be injected.
     * */
    private DataFieldMaxValueIncrementer
        incrementer = null;
    
    /**
     * Data access object for chromosome array data.
     * This property should be injected.
     */
    private ChromosomeArrayDataDao chromosomeArrayDataDao = null;
    
    // =======================================
    //     Setters for dependency injection
    // =======================================
    
    /**
     * Set the primary key incrementer for inserts into
     * the table bio_assay_data.  This method
     * should only be used for dependency injection.
     * @param incrementer Incrementer for primary key values.
     */
    public void setIncrementer(
            final DataFieldMaxValueIncrementer incrementer) {
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
    
    /**
     * Set chromosome array data access object.  This method should only
     * be used for dependency injection.
     * @param chromosomeArrayDataDao Chromosome array data access object
     */
    public void setChromosomeArrayDataDao(
            final ChromosomeArrayDataDao chromosomeArrayDataDao) {
        this.chromosomeArrayDataDao = chromosomeArrayDataDao;
    }

    
    // ==================================
    //     BioAssayDataDao interface
    // ==================================
    
    /**
     * Persist data.
     * @param bioAssayData Bioassay data
     */
    public void save(final BioAssayData bioAssayData) {
        
        // Get next primary key value
        long id = this.incrementer.nextLongValue();
        
        // Save changes to database
        String sql = "insert into bio_assay_data_2 (id) values (?)";
        Object[] args = new Object[] {id};
        this.jdbcTemplate.update(sql, args);
        for (ChromosomeArrayData cad
                : bioAssayData.getChromosomeArrayData().values()) {
            cad.setBioAssayDataId(id);
            this.chromosomeArrayDataDao.save(cad);
        }
        
        // Update bioassay data properties
        bioAssayData.setId(id);
    }
    
    
    /**
     * Load bioassay data associated with given identifier.
     * from persistent storage.
     * @param id Identifier
     * @return Bioassay data or null if none found with given
     * identifier.
     */
    public BioAssayData load(final Long id) {
        
        // Create new bioassay data instance
        BioAssayData bad = null;
        String sql = "select * from bio_assay_data_2 where id = ?";
        Object[] args = new Object[] {id};
        if (this.jdbcTemplate.queryForList(sql, args).size() > 0) {
            bad = new BioAssayData();
            bad.setId(id);
        
            // Get ids of embedded chromosome array data
            sql = 
                "select id from chromosome_array_data_2 where "
                + "bio_assay_data_id = ?";
            args = new Object[] {id};
            List ids = this.jdbcTemplate.queryForList(sql, args);
            
            // Add chromosome array data
            for (Iterator it = ids.iterator(); it.hasNext();) {
                Map map = (Map) it.next();
                Object obj = map.get("id");
                Long cadId = ((BigDecimal) obj).longValue();
                ChromosomeArrayData cad =
                    this.chromosomeArrayDataDao.load(cadId);
                bad.add(cad);
            }
        }
        
        return bad;
    }
    
    
    // TODO: Not sure this is needed
    /**
     * Update persisted properties of given bioassay data.
     * @param bioAssayData Bioassay data
     */
    public void update(final BioAssayData bioAssayData) {
        
    }
    
    
    /**
     * Delete given bioassay data from persistent storage.
     * @param bioAssayData Bioassay data
     */
    public void delete(final BioAssayData bioAssayData) {
        
        // Delete chromosome array data
        for (ChromosomeArrayData cad
                : bioAssayData.getChromosomeArrayData().values()) {
            this.chromosomeArrayDataDao.delete(cad);
        }
        
        // Remove record from bio_assay_data table
        String sql = "delete from bio_assay_data_2 where id = ?";
        Object[] args = new Object[] {bioAssayData.getId()};
        this.jdbcTemplate.update(sql, args);
        
        // Update properties of bioassay data
        bioAssayData.setId(null);
    }
}
