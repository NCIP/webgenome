/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/HibernatePersistentExperiment.java,v $
$Revision: 1.2 $
$Date: 2006-03-29 22:26:30 $

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

import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.array.BioAssay;
import org.rti.webcgh.array.BioAssayIterator;
import org.rti.webcgh.array.persistent.PersistentExperiment;

/**
 * 
 */
public class HibernatePersistentExperiment extends PersistentExperiment {
    
    // =============================
    //    Static attributes
    // =============================
    
    private static HibernatePersistor PERSISTOR = null;
    
    protected static void setPersistor(HibernatePersistor persistor) {
        PERSISTOR = persistor;
    }
    
    
    // ====================================
    //        Constructors
    // ====================================
    
    /**
     * Constructor
     */
    public HibernatePersistentExperiment() {
        super();
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     */
    protected HibernatePersistentExperiment(String name, String description,
            String databaseName) {
        super(name, description, databaseName);
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is experiment virtual?
     */
    protected HibernatePersistentExperiment(String name, String description,
            String databaseName, boolean virtual) {
        super(name, description, databaseName, virtual);
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName Database name
     * @param virtual Is experiment virtual?
     * @param userName User name
     */
	protected HibernatePersistentExperiment(String name, String description,
			String databaseName, boolean virtual, String userName) {
		super(name, description, databaseName, virtual, userName);
	}
    
    
    // ===================================
    //   Implemented bstract methods
    // ===================================
    
    
    /**
     * Delete.
     *
     */
    public void delete() {
    	if (! this.isVirtual()) {
	        for (BioAssayIterator it = this.bioAssayIterator(); it.hasNext();) {
	            BioAssay bioAssay = it.next();
	            if (bioAssay instanceof HibernatePersistentBioAssay)
	                ((HibernatePersistentBioAssay)bioAssay).delete();
	        }
    	}
        PERSISTOR.getHibernateTemplate().delete(this);
    }
    
    
    /**
     * Update
     *
     */
    public void update() {
        PERSISTOR.getHibernateTemplate().update(this);
    }
    
    
    // =================================
    //    Other protected methods
    // =================================
    
    protected void save() {
        PERSISTOR.getHibernateTemplate().save(this);
    }
    
    
    // ============================
    //    Static methods
    // ============================
    
    
    protected static HibernatePersistentExperiment load(Long id) {
        return (HibernatePersistentExperiment)
        	PERSISTOR.getHibernateTemplate().load(HibernatePersistentExperiment.class, id);
    }

    
    protected static HibernatePersistentExperiment[] loadAllPublicExperiments() {
        List exps = PERSISTOR.getHibernateTemplate().loadAll(HibernatePersistentExperiment.class);
        for (Iterator it = exps.iterator(); it.hasNext();) {
            HibernatePersistentExperiment exp = (HibernatePersistentExperiment)it.next();
            if (exp.isVirtual())
                it.remove();
        }
        HibernatePersistentExperiment[] expA = new HibernatePersistentExperiment[0];
        expA = (HibernatePersistentExperiment[])exps.toArray(expA);
        return expA;
    }
    
    
    protected static void deleteAllPublicExperiments() {
        List exps = PERSISTOR.getHibernateTemplate().loadAll(HibernatePersistentExperiment.class);
        for (Iterator it = exps.iterator(); it.hasNext();) {
            HibernatePersistentExperiment exp = (HibernatePersistentExperiment)it.next();
            if (! exp.isVirtual())
                exp.delete();
        }
    }
    
    
    protected static HibernatePersistentExperiment[] loadAllVirtualExperiments(String userName) {
        String query = "from HibernatePersistentExperiment exp where exp.userName = ?";
        List exps = PERSISTOR.getHibernateTemplate().find(query, new Object[]{userName});
        HibernatePersistentExperiment[] expA = new HibernatePersistentExperiment[0];
        expA = (HibernatePersistentExperiment[])exps.toArray(expA);
        return expA;
    }
    
    
    protected static HibernatePersistentExperiment loadVirtualExperiment(String expName, String userName) {
        String query = "from HibernatePersistentExperiment exp where exp.name = ? and exp.userName = ?";
        List exps = PERSISTOR.getHibernateTemplate().find(query, new Object[]{expName, userName});
        HibernatePersistentExperiment exp = null;
        if (exps.size() > 0)
            exp = (HibernatePersistentExperiment)exps.get(0);
        return exp;
    }
}
