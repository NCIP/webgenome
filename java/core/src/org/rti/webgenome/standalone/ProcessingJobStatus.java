package org.rti.webgenome.standalone;


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
    private ProcessingJob processingJob;

    /** full constructor */
    public ProcessingJobStatus(String status, Date datetime, Date createdDt, Date modifiedDt, ProcessingJob processingJob) {
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
    public ProcessingJobStatus(String status, ProcessingJob processingJob) {
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

    public ProcessingJob getProcessingJob() {
        return this.processingJob;
    }

    public void setProcessingJob(ProcessingJob processingJob) {
        this.processingJob = processingJob;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("statusId", getStatusId())
            .toString();
    }

}

