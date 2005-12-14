/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/HibernatePersistentGenomeAssembly.java,v $
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

import org.rti.webcgh.array.persistent.PersistentGenomeAssembly;
import org.rti.webcgh.array.persistent.PersistentOrganism;

/**
 * Implementation of PersistentGenomeAssembly using Hibernate
 */
public class HibernatePersistentGenomeAssembly extends PersistentGenomeAssembly {
    
    
    // =============================
    //    Static attributes
    // =============================
    
    private static HibernatePersistor PERSISTOR = null;
    
    protected static void setPersistor(HibernatePersistor persistor) {
        PERSISTOR = persistor;
    }
    
    
    // =========================================
    //      Constructors
    // =========================================
    
    
    /**
     * 
     */
    public HibernatePersistentGenomeAssembly() {
        super();
    }
    
    
    /**
     * @param name
     * @param organism
     */
    protected HibernatePersistentGenomeAssembly(String name, PersistentOrganism organism) {
        super(name, organism);
    }
    
    
    // ===============================
    //   Implemented abstract methods
    // ===============================
    
    /**
     * Delete
     */
    public void delete() {
        PERSISTOR.getHibernateTemplate().delete(this);
    }
    
    
    // =============================
    //    Other protected methods
    // =============================
    
    protected void save() {
        PERSISTOR.getHibernateTemplate().save(this);
    }
    
    
    // ===============================
    //     Static methods
    // ===============================
    
    
    protected static HibernatePersistentGenomeAssembly load(Long id) {
        return (HibernatePersistentGenomeAssembly)
        	PERSISTOR.getHibernateTemplate().load(HibernatePersistentGenomeAssembly.class, id);
    }
    
    
    protected static HibernatePersistentGenomeAssembly load(String name, PersistentOrganism organism) {
        String query = "from HibernatePersistentGenomeAssembly asm where asm.name = ? and asm.organism = ?";
        List asms = PERSISTOR.getHibernateTemplate().find(query, new Object[] {name, organism});
        HibernatePersistentGenomeAssembly asm = null;
        if (asms.size() > 0)
            asm = (HibernatePersistentGenomeAssembly)asms.get(0);
        return asm;
    }
    
    
    protected static HibernatePersistentGenomeAssembly[] loadAll(PersistentOrganism org) {
        String query = "from HibernatePersistentGenomeAssembly asm where asm.organism = ?";
        List asmList = PERSISTOR.getHibernateTemplate().find(query, new Object[] {org});
        HibernatePersistentGenomeAssembly[] asms = new HibernatePersistentGenomeAssembly[0];
        asms = (HibernatePersistentGenomeAssembly[])asmList.toArray(asms);
        return asms;
    }
    
    
    protected static HibernatePersistentGenomeAssembly[] loadAll() {
        List asmCol = PERSISTOR.getHibernateTemplate().loadAll(HibernatePersistentGenomeAssembly.class);
        HibernatePersistentGenomeAssembly[] asms = new HibernatePersistentGenomeAssembly[0];
        asms = (HibernatePersistentGenomeAssembly[])asmCol.toArray(asms);
        return asms;
    }
    
    
    protected static HibernatePersistentGenomeAssembly loadLatest(PersistentOrganism org) {
        HibernatePersistentGenomeAssembly[] asms = loadAll(org);
        HibernatePersistentGenomeAssembly asm = null;
        if (asms.length > 0) {
            asm = asms[0];
            for (int i = 1; i < asms.length; i++) {
                if (asms[i].id.compareTo(asm.id) > 0)
                    asm = asms[i];
            }
        }
        return asm;
    }
}
