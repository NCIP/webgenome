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

package org.rti.webcgh.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.File;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.BioAssayData;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.io.SmdFileReader;
import org.rti.webcgh.io.SmdFormatException;
import org.rti.webcgh.service.dao.BioAssayDataDao;
import org.rti.webcgh.service.dao.ReporterDao;
import org.rti.webcgh.service.dao.ShoppingCartDao;
import org.rti.webcgh.util.StopWatch;

/**
 * Implementation of <code>ShoppingCartMgr</code>.
 * @author dhall
 *
 */
public final class ShoppingCartMgrImpl implements ShoppingCartMgr {
    
    // ============================
    //     Static members
    // ============================
    
    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(ShoppingCartMgrImpl.class);
    
    // ==============================
    //      Attributes
    // ==============================
    
    /**
     * Shopping cart data access object.  This property
     * should be set using dependency injection.
     */
    private ShoppingCartDao shoppingCartDao = null;
    
    /**
     * Bioassay data access object.  This property
     * should be set using dependency injection.
     */
    private BioAssayDataDao bioAssayDataDao = null;
    
    
    /**
     * Reporter data access object.  This property
     * should be set using dependency injection.
     */
    private ReporterDao reporterDao = null;
    
    // ==============================
    //     Getters and setters
    // ==============================
    
    
    /**
     * Get reporter data access object.  This property
     * should be set using dependency injection.
     * @return Reporter data access object
     */
    public ReporterDao getReporterDao() {
        return reporterDao;
    }


    /**
     * Set reporter data access object.  This property
     * should be set using dependency injection.
     * @param reporterDao Reporter data access object
     */
    public void setReporterDao(final ReporterDao reporterDao) {
        this.reporterDao = reporterDao;
    }


    /**
     * Get shopping cart data access object.  This property
     * should be set using dependency injection.
     * @return Shopping cart data access object
     */
    public ShoppingCartDao getShoppingCartDao() {
        return shoppingCartDao;
    }


    /**
     * Set shopping cart data access object.  This property
     * should be set using dependency injection.
     * @param shoppingCartDao Shopping cart data access object
     */
    public void setShoppingCartDao(final ShoppingCartDao shoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
    }


    /**
     * Get bioassay data access object.  This property
     * should be set using dependency injection.
     * @return Bioassay data access object
     */
    public BioAssayDataDao getBioAssayDataDao() {
        return bioAssayDataDao;
    }


    /**
     * Set bioassay data access object.  This property
     * should be set using dependency injection.
     * @param bioAssayDataDao Bioassay data access object.
     */
    public void setBioAssayDataDao(final BioAssayDataDao bioAssayDataDao) {
        this.bioAssayDataDao = bioAssayDataDao;
    }
    
    
    // ===================================
    //     ShoppingCartMgr interface
    // ===================================


    /**
     * Load data from SMD-format file into the shopping
     * cart belonging to given user.  The result is that
     * a new experiment is created and persisted in the
     * shopping cart> (i.e., All data are added to a new
     * experiment object)  Bioassay data are also persisted
     * (outside of the shopping cart).
     * @param file SMD-format data file
     * @param userName User name
     * @param organism Organism that experiment was run on.
     * @throws SmdFormatException if file does not comply with
     * the SMD file format specification
     * @throws WebcghSystemException if there is no persistent
     * shopping cart associated with given user
     */
    public void loadSmdFile(final File file, final String userName,
            final Organism organism)
        throws SmdFormatException {
        LOGGER.info("Loading SMD data file '" + file.getName()
                + "' into shopping cart of '" + userName + "'");
        SmdFileReader reader = new SmdFileReader(file);
        
        // Get shopping cart
        ShoppingCart shoppingCart = this.shoppingCartDao.load(userName);
        if (shoppingCart == null) {
            throw new WebcghSystemException(
                    "No persistent shopping cart found for user '"
                    + userName + "'");
        }
        
        // Persist reporters
        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        List<Reporter> reporters = reader.getReporters();
//        LOGGER.info("Persisting " + reporters.size() + " reporters");
//        for (Reporter r : reporters) {
//            this.reporterDao.save(r);
//        }
//        LOGGER.info("Persisted in " + stopWatch.getFormattedLapTime());
        
        // Add new experiment to shopping cart and persist bioassay data
        Experiment exp = new Experiment(file.getName());
        List<String> bioAssayNames = reader.getBioAssayNames();
        for (String name : bioAssayNames) {
            BioAssay ba = new BioAssay(name, organism);
            exp.add(ba);
            BioAssayData bad = reader.getBioAssayData(name);
            LOGGER.info("Persisting bioassay data from '" + name + "'");
            this.bioAssayDataDao.save(bad);
            ba.setBioAssayDataId(bad.getId());
        }
        LOGGER.info("Bioassays persisted in "
                + stopWatch.getFormattedLapTime());
        
        shoppingCart.add(exp);
        LOGGER.info("Persisting experiment metadata");
        this.shoppingCartDao.update(shoppingCart);
        LOGGER.info("Completed SMD data load");
        LOGGER.info("Total elapsed time: "
                + stopWatch.getFormattedElapsedTime());
    }
    
    
    /**
     * Clear contents of shopping cart associated with
     * given user.  Bioassay data associated with experiments
     * in the cart will also be deleted.
     * @param userName User name
     */
    public void clear(final String userName) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.info("Clearing shopping cart of user '" + userName + "'");
        ShoppingCart cart = this.shoppingCartDao.load(userName);
        Set<Experiment> experiments = cart.getExperiments();
        Set<Reporter> reporters = new HashSet<Reporter>();
        for (Iterator<Experiment> it = experiments.iterator(); it.hasNext();) {
            Experiment exp = it.next();
            
            // Remove bioassay data and collect reporters
            Set<BioAssay> bioAssays = exp.getBioAssays();
            for (BioAssay ba : bioAssays) {
                BioAssayData bad = 
                    this.bioAssayDataDao.load(ba.getBioAssayDataId());
                reporters.addAll(bad.getReporters());
                
                // TODO: Implement method that will delete a
                // BioAssayData object from persistent storage
                // without first loading it into memory.
                LOGGER.info("Removing bioassay data for '"
                        + ba.getName() + "' from persistent store");
                this.bioAssayDataDao.delete(bad);
            }
            
            // Remove experiment
            it.remove();
        }
        LOGGER.info("Reporters removed in " + stopWatch.getFormattedLapTime());
        LOGGER.info("Removing experiment metadata from persistent store");
        this.shoppingCartDao.update(cart);
        
        // Remove reporters
        LOGGER.info("Deleting reporters from persistent store");
        for (Reporter r : reporters) {
            this.reporterDao.delete(r);
        }
        LOGGER.info("Reporters deleted in " + stopWatch.getFormattedLapTime());
        LOGGER.info("Shopping cart cleared");
        LOGGER.info("Total elapsed time: "
                + stopWatch.getFormattedElapsedTime());
    }

}
