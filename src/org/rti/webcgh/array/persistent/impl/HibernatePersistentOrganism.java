/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/HibernatePersistentOrganism.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 20:17:48 $

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
package org.rti.webcgh.array.persistent.impl;

import java.util.List;

import org.rti.webcgh.array.persistent.PersistentOrganism;


/**
 * 
 */
public class HibernatePersistentOrganism extends PersistentOrganism {
    
    
    // =============================
    //    Static attributes
    // =============================
    
    private static HibernatePersistor PERSISTOR = null;
    
    protected static void setPersistor(HibernatePersistor persistor) {
        PERSISTOR = persistor;
    }
    
    
    // ==================================
    //    Implemented abstract methods
    // ==================================
    
    
    /**
     * Delete
     */
    public void delete() {
        PERSISTOR.getHibernateTemplate().delete(this);
    }
    
    
    // ==================================
    //      Constructors
    // ==================================
    
    /**
     * Constructor
     */
    public HibernatePersistentOrganism() {}
    
    
    /**
     * Constructor
     * @param genus Genus
     * @param species Species
     */
    protected HibernatePersistentOrganism(String genus, String species) {
        super(genus, species);
    }
    
    
    // =============================
    //    Other public methods
    // =============================
    
    protected void save() {
        PERSISTOR.getHibernateTemplate().save(this);
    }
    
    
    // ===============================
    //    Static methods
    // ===============================
    
    protected static HibernatePersistentOrganism load(String genus, String species) {
        String query = "from HibernatePersistentOrganism org where org.genus = ? and org.species = ?";
        List orgs = PERSISTOR.getHibernateTemplate().find(query, new Object[] {genus, species});
        HibernatePersistentOrganism org = null;
        if (orgs.size() > 0)
            org = (HibernatePersistentOrganism)orgs.get(0);
        return org;
    }
    
    
    protected static HibernatePersistentOrganism load(Long id) {
        return (HibernatePersistentOrganism)PERSISTOR.getHibernateTemplate().load(HibernatePersistentOrganism.class, id);
    }
    
    
    protected static HibernatePersistentOrganism[] loadAll() {
        List orgList = PERSISTOR.getHibernateTemplate().loadAll(HibernatePersistentOrganism.class);
        HibernatePersistentOrganism[] orgs = new HibernatePersistentOrganism[0];
        orgs = (HibernatePersistentOrganism[])orgList.toArray(orgs);
        return orgs;
    }
}
