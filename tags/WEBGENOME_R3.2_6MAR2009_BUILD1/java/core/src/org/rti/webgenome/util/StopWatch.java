/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/StopWatch.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/

package org.rti.webgenome.util;

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
