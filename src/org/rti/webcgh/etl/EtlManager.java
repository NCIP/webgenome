/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/etl/EtlManager.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
package org.rti.webcgh.etl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.rti.webcgh.util.TimeUtils;

/**
 * Base class for ETL managers
 */
public class EtlManager {
    
    // =============================================
    //        Static variables
    // =============================================
    
    protected static String ETL_BEANS_FILE = "org/rti/webcgh/etl/etl-beans.xml";
    
    
    // =============================================
    //       State variables
    // =============================================
    
    protected final Collection etlMilestoneListeners = new ArrayList();
    protected int recordsBetweenMilestones = 100;
    
    
    
    // ==============================================
    //         Public methods
    // ==============================================
    /**
     * Add a listener for ETL milestone events
     * @param listener A listener
     */
    public void addEtlMilestoneListener(EtlMilestoneListener listener) {
    	this.etlMilestoneListeners.add(listener);
    }
    
    
    /**
     * Remove a listener for ETL milestone events
     * @param listener Listener
     */
    public void removeEtlMilestoneListener(EtlMilestoneListener listener) {
        this.etlMilestoneListeners.remove(listener);
    }
    
    
	/**
	 * @return Number of records between ETL milestones
	 */
	public int getRecordsBetweenMilestones() {
		return recordsBetweenMilestones;
	}
	
	
	/**
	 * @param recordsBetweenMilestones Number of records between ETL milestones
	 */
	public void setRecordsBetweenMilestones(int recordsBetweenMilestones) {
		this.recordsBetweenMilestones = recordsBetweenMilestones;
	}
	
	
    protected void notifyListeners(EtlMilestoneEvent evt) {
    	for (Iterator it = this.etlMilestoneListeners.iterator(); it.hasNext();) {
    		EtlMilestoneListener listener = (EtlMilestoneListener)it.next();
    		listener.onEtlMilestoneReached(evt);
    	}
    }
    
    
    
    // ==================================================
    //           Protected methods
    // ==================================================
    protected void milestone(Date startTime, int count) {
    	Date now = new Date();
		String message = count + " records processed in " +
			TimeUtils.formattedElapsedTime(startTime, now) +
			" elapsed time";
		EtlMilestoneEvent evt = new EtlMilestoneEvent(this, message);
		this.notifyListeners(evt);
    }

}
