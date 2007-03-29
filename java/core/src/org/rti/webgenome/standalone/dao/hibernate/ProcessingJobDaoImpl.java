package org.rti.webgenome.standalone.dao.hibernate;


import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.rti.webgenome.standalone.ProcessingJob;
import org.rti.webgenome.standalone.ProcessingJobStatus;
import org.rti.webgenome.standalone.dao.ProcessingJobDao;


/**
 * Implementation of <code>ProcessingJobDao</code> using Hibernate.
 * @author djackman
 *
 */
public final class ProcessingJobDaoImpl extends HibernateDaoSupport
    implements ProcessingJobDao {

    // ============================
    //     Static members
    // ============================

    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(ProcessingJobDaoImpl.class);

    // ============================
    // Attributes
    // ============================

    private Session _session ;

    // ============================
    // Public Methods
    // ============================

    /**
     * Add to persistent storage.
     * @param ProcessingJob
     */
    public Long add (final ProcessingJob processingJob ) {
        if ( processingJob.getCreatedDt() == null ) 
            processingJob.setCreatedDt( new Date() ) ;
        Long id = (Long) this.getHibernateTemplate().save ( processingJob ) ;
        
        Set<ProcessingJobStatus> statusList = processingJob.getProcessingJobStatuses() ;
        if ( statusList != null && ! statusList.isEmpty() ) {
            for ( ProcessingJobStatus status : statusList ) {
                status.setProcessingJob( processingJob ) ;
                if ( status.getCreatedDt() == null )
                    status.setCreatedDt( new Date() ) ;
                if ( status.getDatetime() == null )
                    status.setDatetime( new Date() ) ;
                this.getHibernateTemplate().save ( status ) ;
            }
        }
        
        return id ;
    }

    /**
     * Update to persistent storage.
     * @param ProcessingJob
     */
    public void update (final ProcessingJob processingJob ) {
        
        this.getHibernateTemplate().update( processingJob ) ;
        
        Set<ProcessingJobStatus> statusList = processingJob.getProcessingJobStatuses() ;
        if ( statusList != null && ! statusList.isEmpty() ) {
            for ( ProcessingJobStatus status : statusList ) {
                status.setProcessingJob( processingJob ) ;
                if ( status.getCreatedDt() == null )
                    status.setCreatedDt( new Date() ) ;
                if ( status.getModifiedDt() == null )
                    status.setModifiedDt( new Date() ) ;
                if ( status.getDatetime() == null )
                    status.setDatetime( new Date() ) ;
                this.getHibernateTemplate().saveOrUpdate ( status ) ;
            }
        }
    }

    public void updatePercentComplete ( Long jobId, long percentComplete ) {
        ProcessingJob processingJob = getByPrimaryKey ( jobId ) ;
        if ( processingJob != null ) {
            Long percent = new Long ( percentComplete ) ;
            processingJob.setPercentComplete( percent.intValue() ) ;
            this.getHibernateTemplate().saveOrUpdate( processingJob ) ;
        }
    }

    public void delete(final ProcessingJob processingJob ) {
        Set<ProcessingJobStatus> statusList = processingJob.getProcessingJobStatuses() ;
        if ( statusList != null && ! statusList.isEmpty() ) {
            for ( ProcessingJobStatus status : statusList ) {
                status.setProcessingJob( processingJob ) ;
                this.getHibernateTemplate().delete ( status ) ;
            }
        }
        this.getHibernateTemplate().delete( processingJob );
    }

    /**
     * Load ProcessingJob associated with given identifier
     * from storage.
     * @param id Identifier
     * @return ProcessingJob
     */
    public ProcessingJob getByPrimaryKey(final Long jobId ) {

        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                ProcessingJob job = null ;
                job = (ProcessingJob) session.get ( ProcessingJob.class, jobId ) ;
                if ( job != null ) {
                    // Load stuff manually - rather than relying on lazy loading settings
                    job.getProcessingJobStatuses().size() ;
                }
                return job ;
            }
        };

        // get hibernate template, pass in our callback
        return (ProcessingJob) getHibernateTemplate().execute( callback ) ;
    }

    public ProcessingJob getByRequestId ( String requestId ) {
        ProcessingJob job = null;
        String query =
            "from ProcessingJob job where requestId = ?";
        Object[] args = new Object[]{requestId};
        List results = this.getHibernateTemplate().find(query, args);
        if (results != null && results.size() > 0) {
            job = (ProcessingJob) results.get(0);
            if ( job != null ) {
                // Load stuff manually - rather than relying on lazy loading settings
                job.getProcessingJobStatuses().size() ;
            }
        }
        return job;
    }

    public List getProcessingJobsByUserName ( String userName ) {
        List processingJobs = null ;
        if ( userName != null && userName.trim().length() > 0 ) {
            processingJobs =
            getHibernateTemplate().find ( "from ProcessingJob p where UPPER(p.userId) = ? order by p.jobId asc", userName.toUpperCase() ) ;

            Iterator iter = processingJobs.iterator() ;
            while ( iter.hasNext() ) {
                ProcessingJob pj = (ProcessingJob) iter.next() ;
                Set<ProcessingJobStatus> statusList = pj.getProcessingJobStatuses() ;
                if ( statusList != null && ! statusList.isEmpty() )
                    statusList.size() ;
            }
        }
        return processingJobs ;
    }

    private String toString( ProcessingJob job ) {
        StringBuffer sb = new StringBuffer ( ) ;

        sb.append( "JobId=" + job.getJobId() + "\n" +
                   "Type="  + job.getType()  + "\n" +
                   "PercentComplete=" + job.getPercentComplete() + "\n" +
                   "JobStatuses=\n" ) ;

        Set<ProcessingJobStatus> statusList = job.getProcessingJobStatuses() ;
        for ( ProcessingJobStatus status : statusList ) {
            sb.append ( "    statusId=" + status.getStatusId() + " status: " + status.getStatus() + " datetime: " + status.getDatetime() + "\n" ) ;
        }

        return sb.toString() ;
    }


/*
    private List getStatuses ( Long jobId ) {
        Set statuses = new HashSet() ;
        String query = "from ProcessingJobStatus status where jobId = ? order by datetime asc" ;
        Object[] args = new Object[]{jobId} ;
        List results = this.getHibernateTemplate().find(query, args) ;
        return results ;
    }
*/
}
