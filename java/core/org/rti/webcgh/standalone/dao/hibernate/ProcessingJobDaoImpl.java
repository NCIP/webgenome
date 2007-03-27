/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/org/rti/webcgh/standalone/dao/hibernate/ProcessingJobDaoImpl.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:10 $

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

package org.rti.webcgh.standalone.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.rti.webcgh.standalone.dao.hibernate.ProcessingJob;
import org.rti.webcgh.standalone.dao.hibernate.ProcessingJobStatus;
import org.rti.webcgh.standalone.dao.ProcessingJobDao;


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
