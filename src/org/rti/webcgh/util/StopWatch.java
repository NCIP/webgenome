/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

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

package org.rti.webcgh.util;

import org.apache.log4j.Logger;

/**
 * This is a utility class for performance testing code.  It
 * calculates elapsed time from the time the stopwatch is
 * activated.
 * @author dhall
 *
 */
public final class StopWatch {
    
    // =================================
    //     Constants
    // =================================
    
    /** Milliseconds in a second. */
    private static final long MSEC_IN_SEC = 1000;
    
    /** Milliseconds in a minute. */
    private static final long MSEC_IN_MIN = 60000;
    
    /** Milliseconds in an hour. */
    private static final long MSEC_IN_HOUR = 3600000;
    
    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(StopWatch.class);
    
    // =============================
    //       Attributes
    // =============================
    
    /**
     * Starting system time which is set
     * when the stopwatch is activated.
     */
    private long startTime = (long) -1;
    
    /**
     * System time which is set when the
     * stop watch is stopped.
     */
    private long stopTime = (long) -1;
    
    /**
     * System time that is set when
     * one of the 'lap' methods is called.
     * Currently the only lap method
     * is getFormattedLapTime().
     */
    private long lapTime = (long) -1;
    
    
    // ========================
    //       Constructors
    // ========================
    
    /**
     * Default constructor.
     */
    public StopWatch() {
        
    }
    
    // ======================
    //   Business methods
    // ======================
    
    /**
     * Is stopwatch running?
     * @return T/F
     */
    public boolean running() {
        return this.startTime >= 0 && this.stopTime < 0;
    }
    
    /**
     * Start the stop watch.
     */
    public void start() {
        if (!this.running()) {
            this.startTime = System.currentTimeMillis();
            this.lapTime = this.startTime;
            this.stopTime = (long) -1;
        } else {
            LOGGER.warn("Stop watch is currently running and cannot "
                    + "be started");
        }
    }
    
    
    /**
     * Stop the stop watch.
     *
     */
    public void stop() {
        if (this.running()) {
            this.stopTime = System.currentTimeMillis();
            this.lapTime = this.stopTime;
        } else {
            LOGGER.warn("Stop watch is not running and cannot be stopped");
        }
    }
    
    /**
     * Reset the stop watch.  If it is currently running,
     * it will be stopped.
     *
     */
    public void reset() {
        this.startTime = (long) -1;
        this.stopTime = (long) -1;
    }
    
    /**
     * Return elapsed time formatted as
     * x HR : y MIN : z SEC : w MSEC.
     * @return Formatted elapsed time
     */
    public String getFormattedElapsedTime() {
        long currTime = (this.running())
                ? System.currentTimeMillis() : this.stopTime;
        return this.formatTime(currTime - this.startTime);
    }
    
    
    /**
     * Get 'lap' time formatted as
     * x HR : y MIN : z SEC : w MSEC.
     * When this method is first called,
     * it acts like <code>getElapsedTime</code>.
     * Upon subsequent calls, it returns the
     * formatted elapsed time since the last
     * call to <code>getFormattedLapTime</code>.
     * @return Formatted lap time.
     */
    public String getFormattedLapTime() {
        long currTime = System.currentTimeMillis();
        String formattedTime = this.formatTime(currTime - this.lapTime);
        this.lapTime = currTime;
        return formattedTime;
    }
    
    /**
     * Format time interval as
     * x HR : y MIN : z SEC : w MSEC.
     * @param time Time interval in milliseconds
     * @return Formatted time.
     */
    private String formatTime(final long time) {
        long remainingTime = time;
        long hrs = (long) Math.floor((double) remainingTime
                / (double) MSEC_IN_HOUR);
        remainingTime %= MSEC_IN_HOUR;
        long mins = (long) Math.floor((double) remainingTime
                / (double) MSEC_IN_MIN);
        remainingTime %= MSEC_IN_MIN;
        long secs = (long) Math.floor((double) remainingTime
                / (double) MSEC_IN_SEC);
        remainingTime %= MSEC_IN_SEC;
        long msecs = remainingTime;
        StringBuffer buff = new StringBuffer();
        if (hrs > 0) {
            buff.append(hrs + " HR");
        }
        if (mins > 0 || hrs > 0) {
            if (hrs > 0) {
                buff.append(" : ");
            }
            buff.append(mins + " MIN");
        }
        if (secs > 0 || mins > 0 || hrs > 0) {
            if (mins > 0 || hrs > 0) {
                buff.append(" : ");
            }
            buff.append(secs + " SEC");
        }
        if (secs > 0 || mins > 0 || hrs > 0) {
            buff.append(" : ");
        }
        buff.append(msecs + " MSEC");
        return buff.toString();
    }
}
