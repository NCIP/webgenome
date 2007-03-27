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
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author djackman */
public class ProcessingJobStatus implements Serializable {

    /** identifier field */
    private long statusId;

    /** persistent field */
    private String status;

    /** nullable persistent field */
    private Date datetime;

    /** nullable persistent field */
    private Date createdDt;

    /** nullable persistent field */
    private Date modifiedDt;

    /** persistent field */
    private org.rti.webcgh.standalone.dao.hibernate.ProcessingJob processingJob;

    /** full constructor */
    public ProcessingJobStatus(String status, Date datetime, Date createdDt, Date modifiedDt, org.rti.webcgh.standalone.dao.hibernate.ProcessingJob processingJob) {
        this.status = status;
        this.datetime = datetime;
        this.createdDt = createdDt;
        this.modifiedDt = modifiedDt;
        this.processingJob = processingJob;
    }

    /** default constructor */
    public ProcessingJobStatus() {
    }

    /** minimal constructor */
    public ProcessingJobStatus(String status, org.rti.webcgh.standalone.dao.hibernate.ProcessingJob processingJob) {
        this.status = status;
        this.processingJob = processingJob;
    }

    public long getStatusId() {
        return this.statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
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

    public org.rti.webcgh.standalone.dao.hibernate.ProcessingJob getProcessingJob() {
        return this.processingJob;
    }

    public void setProcessingJob(org.rti.webcgh.standalone.dao.hibernate.ProcessingJob processingJob) {
        this.processingJob = processingJob;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("statusId", getStatusId())
            .toString();
    }

}

