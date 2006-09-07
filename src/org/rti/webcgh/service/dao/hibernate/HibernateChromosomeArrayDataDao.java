/*
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.service.dao.hibernate;

import java.util.List;

import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.service.dao.ChromosomeArrayDataDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>HibernateDaoSupport</code> using Hibernate.
 * @author dhall
 *
 */
public final class HibernateChromosomeArrayDataDao extends HibernateDaoSupport
        implements ChromosomeArrayDataDao {
    
    /**
     * Save to persistent storage.
     * @param chromosomeArrayData Chromosome array data
     */
    public void save(final ChromosomeArrayData chromosomeArrayData) {
        this.getHibernateTemplate().save(chromosomeArrayData);
    }
    
    
    /**
     * Load chromosome array data denoted by given identifier.
     * @param id Identifier.
     * @return Chromosome array data
     */
    public ChromosomeArrayData load(final Long id) {
        return (ChromosomeArrayData)
            this.getHibernateTemplate().load(ChromosomeArrayData.class, id);
    }
    
    
    /**
     * Load chromosom array data denoted by chromosome number
     * and bioassay data identifier.
     * @param chromosome Chromosome number
     * @param bioAssayDataId Bioassay data identifier
     * @return Chromosome array data
     */
    public ChromosomeArrayData load(final short chromosome,
            final Long bioAssayDataId) {
        List cads = this.getHibernateTemplate().find(
                "from ChromosomeArrayData cad where cad.chromosome = ? "
                + "and cad.bioAssayDataId = ?",
                new Object[] {chromosome, bioAssayDataId});
        ChromosomeArrayData cad = null;
        if (cads != null && cads.size() > 0) {
            cad = (ChromosomeArrayData) cads.get(0);
        }
        return cad;
    }
    
    
    /**
     * Delete from persistent storage.
     * @param chromosomeArrayData Chromosome array data
     */
    public void delete(final ChromosomeArrayData chromosomeArrayData) {
        this.getHibernateTemplate().delete(chromosomeArrayData);
    }
    
    
    /**
     * Update persistently-stored properties for
     * given chromosome array data.
     * @param chromosomeArrayData Chromosome arraydata
     */
    public void update(final ChromosomeArrayData chromosomeArrayData) {
        this.getHibernateTemplate().update(chromosomeArrayData);
    }

}
