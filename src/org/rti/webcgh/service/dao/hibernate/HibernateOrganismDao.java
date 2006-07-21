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

package org.rti.webcgh.service.dao.hibernate;

import java.util.List;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.dao.OrganismDao;
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
    public void save(Organism organism) {
        this.getHibernateTemplate().save(organism);
    }
    
    /**
     * Load organism associated with given identifier
     * from storage.
     * @param id Identifier
     * @return An organism
     */
    public Organism load(final Long id) {
        return (Organism)
            this.getHibernateTemplate().load(Organism.class, id);
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
            throw new WebcghSystemException(
                    "Default organism not loaded in database");
        }
        return org;
    }
    
    
    /**
     * Delete given organism from persistent storage.
     * @param organism An organism
     */
    public void delete(Organism organism) {
        this.getHibernateTemplate().delete(organism);
    }

}
