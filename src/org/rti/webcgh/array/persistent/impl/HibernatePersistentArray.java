/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/HibernatePersistentArray.java,v $
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

import org.rti.webcgh.array.persistent.PersistentArray;
import org.rti.webcgh.array.persistent.PersistentOrganism;

/**
 * 
 */
public class HibernatePersistentArray extends PersistentArray {
	
    // =============================
    //    Static attributes
    // =============================
    
    private static HibernatePersistor PERSISTOR = null;
    
    protected static void setPersistor(HibernatePersistor persistor) {
        PERSISTOR = persistor;
    }
	
	
	// ==============================
	//    Constructors
	// ==============================
	
	/**
	 * Constructor
	 */
	public HibernatePersistentArray() {
		super();
	}
	
	
	/**
	 * Constructor
	 * @param vendor Vendor
	 * @param name Array name
	 * @param organism Organism
	 */
	protected HibernatePersistentArray(String vendor, String name, PersistentOrganism organism) {
		super(vendor, name, organism);
	}
	
	
	// ================================
	//    Abstract methods
	// ================================
	
    /**
     * Delete persistent instance.
     *
     */
    public void delete() {
    	PERSISTOR.getHibernateTemplate().delete(this);
    }
    
    
    /**
     * Update persistent copy.
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
    
    
    // =================================
    //     Static methods
    // =================================
    
    protected static HibernatePersistentArray load(Long id) {
    	return (HibernatePersistentArray)
			PERSISTOR.getHibernateTemplate().load(HibernatePersistentArray.class, id);
    }
    
    
    protected static HibernatePersistentArray load(String vendor, String name, 
    		PersistentOrganism organism) {
    	String query = "from HibernatePersistentArray hpa where hpa.vendor = ? " +
			"and hpa.name = ? and hpa.organism = ?";
    	List arrays = PERSISTOR.getHibernateTemplate().find(query, 
    			new Object[] {vendor, name, organism});
    	HibernatePersistentArray array = null;
    	if (arrays.size() > 0)
    		array = (HibernatePersistentArray)arrays.get(0);
    	return array;
    }
    
    
    protected static void deleteAll() {
        List all = PERSISTOR.getHibernateTemplate().loadAll(HibernatePersistentArray.class);
        PERSISTOR.getHibernateTemplate().deleteAll(all);
    }

}
