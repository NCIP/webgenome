/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/persistent/impl/HibernatePersistentBioAssay.java,v $
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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

import org.rti.webcgh.array.persistent.PersistentBioAssay;

/**
 * Implementation of PersistentBioAssay using Hibernate
 */
public class HibernatePersistentBioAssay extends PersistentBioAssay {
    
    
    // =============================
    //    Static attributes
    // =============================
    
    private static HibernatePersistor PERSISTOR = null;
    
    protected static void setPersistor(HibernatePersistor persistor) {
        PERSISTOR = persistor;
    }
    
    
    // ==================================
    //     Constructors
    // ==================================
    
    /**
     * Constructor
     */
    public HibernatePersistentBioAssay() {
        super();
    }
    
    
    /**
     * Constructor
     * @param name Name
     * @param description Description
     * @param databaseName
     */
    protected HibernatePersistentBioAssay(String name, String description,
            String databaseName) {
        super(name, description, databaseName);
    }
    
    
    // ===========================================
    //       Implemented abstract methods
    // ===========================================
    
    
    /**
     * Update
     *
     */
    public void update() {
        if (this.binnedData != null)
            if (this.binnedData instanceof HibernatePersistentBinnedData)
                ((HibernatePersistentBinnedData)this.binnedData).update();
        if (this.bioAssayData != null)
            if (this.bioAssayData instanceof HibernatePersistentBioAssayData)
                ((HibernatePersistentBioAssayData)this.bioAssayData).saveOrUpdate();
        PERSISTOR.getHibernateTemplate().update(this);
    }
    
    
    /**
     * Delete
     *
     */
    public void delete() {
        PERSISTOR.getHibernateTemplate().delete(this);
        HibernatePersistentBinnedData bData = null;
        HibernatePersistentBioAssayData aData = null;
        if (this.binnedData != null) {
            if (this.binnedData instanceof HibernatePersistentBinnedData)
                bData = (HibernatePersistentBinnedData)this.binnedData;
        } else if (this.binnedDataId != null)
            bData = HibernatePersistentBinnedData.load(this.binnedDataId);
        if (this.bioAssayData != null) {
            if (this.bioAssayData instanceof HibernatePersistentBioAssayData)
                aData = (HibernatePersistentBioAssayData)this.bioAssayData;
        } else if (this.bioAssayDataId != null)
            aData = HibernatePersistentBioAssayData.load(new Long(this.bioAssayDataId));
        if (bData != null)
            bData.delete();
        if (aData != null)
            aData.delete();
    }
    
    
    // ==========================================
    //     Other protected methods
    // ==========================================
    
    protected void save() {
        if (this.binnedData != null)
            if (this.binnedData instanceof HibernatePersistentBinnedData)
                ((HibernatePersistentBinnedData)this.binnedData).update();
        if (this.bioAssayData != null)
            if (this.bioAssayData instanceof HibernatePersistentBioAssayData)
                ((HibernatePersistentBioAssayData)this.bioAssayData).saveOrUpdate();
        PERSISTOR.getHibernateTemplate().save(this);
    }

    
    
    // ======================================
    //      Static methods
    // ======================================
    
    protected static HibernatePersistentBioAssay load(Long id) {
        return (HibernatePersistentBioAssay)
        	PERSISTOR.getHibernateTemplate().load(HibernatePersistentBioAssay.class, id);
    }
}