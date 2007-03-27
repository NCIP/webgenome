/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:10 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webcgh.standalone.dao.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.rti.webcgh.domain.Plot;


/**
 * A Processing Job - which is a processing task created when wegGenome
 * operates in Standalone mode.
 * 
 * @author djackman
 * */
public class ProcessingJob implements Serializable {

    /** Unique identifier for a Processing Job */
    private long jobId;

    /** Request Id allocated to the Processing Job by the EJB framework. */
    private String requestId;

    /** User Id of the user associated with the processing job. */
    private String userId;

    /** The Type of Processing Job - there are families of processing jobs.
     * This setting determines what type of processing will be performed for
     * this job. */
    private String type;

    /** How much of the job is completed, percentage-wise. */
    private Integer percentComplete;

    /** Date that the Processing Job was created */
    private Date createdDt;

    /** Date that the Processing Job was modified */
    private Date modifiedDt;

    /** Any Properties that might exist for the job.
     * The usage of this field isn't finalized yet, but I'm thinking that
     * individual property settings for a job could be specified in one String
     * with a delimiter character to separate settings. */
    private String jobProperties;

    /** Status entries that will be created during the Job's Processing.
     * The set of entries will represent the different processing steps performed
     * for the job. The last entry would be the current (of final) status of the Processing Job.
     */
    private Set processingJobStatuses;

    /** full constructor */
    public ProcessingJob(String requestId, String userId, String type, Integer percentComplete, Date createdDt, Date modifiedDt, String jobProperties, Set processingJobStatuses) {
        this.requestId = requestId;
        this.userId = userId;
        this.type = type;
        this.percentComplete = percentComplete;
        this.createdDt = createdDt;
        this.modifiedDt = modifiedDt;
        this.jobProperties = jobProperties;
        this.processingJobStatuses = processingJobStatuses;
    }

    /** default constructor */
    public ProcessingJob() {
    }

    /** minimal constructor */
    public ProcessingJob(String userId, Set processingJobStatuses) {
        this.userId = userId;
        this.processingJobStatuses = processingJobStatuses;
    }
    
    //
    //    S E T T E R S    &    G E T T E R S
    //

    public long getJobId() {
        return this.jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPercentComplete() {
        return this.percentComplete;
    }

    public void setPercentComplete(Integer percentComplete) {
        this.percentComplete = percentComplete;
    }

    public Date getCreatedDt() {
        return this.createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return this.modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public String getJobProperties() {
        return this.jobProperties;
    }

    public void setJobProperties(String jobProperties) {
        this.jobProperties = jobProperties;
    }

    public Set getProcessingJobStatuses() {
        return this.processingJobStatuses;
    }

    public void setProcessingJobStatuses(Set processingJobStatuses) {
        this.processingJobStatuses = processingJobStatuses;
    }
    
    /**
     * Add a Processing Job Status entry to the Processing Job
     * @param status
     */
    public void add ( final ProcessingJobStatus status ) {
        if ( this.processingJobStatuses == null )
            this.processingJobStatuses = new HashSet<ProcessingJobStatus>() ;
        this.processingJobStatuses.add( status ) ;
    }
    

    public String toString() {
        return new ToStringBuilder(this)
            .append("jobId", getJobId())
            .toString();
    }

}
