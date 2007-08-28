/*
$Revision: 1.6 $
$Date: 2007-08-28 17:24:13 $

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

package org.rti.webgenome.service.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.service.analysis.AnalysisService;
import org.rti.webgenome.service.dao.ArrayDao;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.plot.PlotService;

/**
 * Implementation of {@link JobManager} where only
 * one job is executed at a time (i.e., serially)
 * in first-in-first-out (FIFO) order.
 * @author dhall
 *
 */
public class SerialQueueJobManager implements JobManager {
	
	
	//
	//     C O N S T A N T S
	//
	
	/** Priority given to thread that executes jobs. */
	private static final int JOB_EXECUTION_THREAD_PRIORITY =
		Thread.MIN_PRIORITY;
	
	/**
	 * Time in milliseconds that the job execution thread
	 * sleeps when the queue is empty before checking again
	 * to see if there are any new jobs.
	 */
	private static final long JOB_EXECUTION_THREAD_SLEEP_TIME = 5000;
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(
			SerialQueueJobManager.class);
	
	//
	//     A T T R I B U T E S
	//
	
	/** Job data access object. This should be injected. */
	private JobDao jobDao = null;
	
	/** Set of current jobs sorted on instantiation date. */
	private SortedSet<Job> jobs = new TreeSet<Job>(
			new InstantiationDateJobComparator());
	
	/** Thread that executes jobs. */
	private JobExecutionThread jobExecutionThread = new JobExecutionThread();
	
	/** Service providing I/O of data files. */
	private IOService ioService = null;
	
	/** Service that runs analytic operations. */
	private AnalysisService analysisService = null;
	
	/** Service that generates plots. */
	private PlotService plotService = null;
	
	/** Shopping cart data access object. */
	private ShoppingCartDao shoppingCartDao = null;
	
	/** Array data access object. */
	private ArrayDao arrayDao = null;
	
	//
	//  S E T T E R S
	//
	
	/**
	 * Set service to perform analytic operations.
	 * @param analysisService Service to perform analytic operations.
	 */
	public void setAnalysisService(final AnalysisService analysisService) {
		this.analysisService = analysisService;
	}


	/**
	 * Set service to perform data file I/O.
	 * @param ioService Service to perform data file I/O.
	 */
	public void setIoService(final IOService ioService) {
		this.ioService = ioService;
	}


	/**
	 * Set job data access object.
	 * @param jobDao Job data access object.
	 */
	public void setJobDao(final JobDao jobDao) {
		this.jobDao = jobDao;
	}


	/**
	 * Set service to perform plots.
	 * @param plotService Service to perform plots.
	 */
	public void setPlotService(final PlotService plotService) {
		this.plotService = plotService;
	}
	
	
	/**
	 * Set array data access object.
	 * @param arrayDao Array data access object
	 */
	public void setArrayDao(final ArrayDao arrayDao) {
		this.arrayDao = arrayDao;
	}


	/**
	 * Set shopping cart data access object.
	 * @param shoppingCartDao Shopping cart data access object.
	 */
	public void setShoppingCartDao(
			final ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	
	//
	//     C O N S T R U C T O R S
	//


	/**
	 * Constructor.
	 * @param jobDao Job data access object
	 */
	public SerialQueueJobManager(final JobDao jobDao) {
		this.jobDao = jobDao;
		
		// Get all persisted jobs
		Collection<Job> peristedJobs = this.jobDao.loadAll();
		this.jobs.addAll(peristedJobs);
		LOGGER.info("Retrieved " + peristedJobs.size() + " jobs from database");
		
		// For any jobs with no end date,
		// set start date/time to null since
		// none of the jobs have started.  Any jobs
		// executing when application was terminated
		// should be restarted.
		for (Job job : peristedJobs) {
			if (job.getEndDate() == null) {
				job.setStartDate(null);
			}
		}
		
		// Start job execution thread
		this.jobExecutionThread.start();
	}
	
	
	//
	//     JobManager I N T E R F A C E
	//

	/**
	 * {@inheritDoc}
	 */
	public void add(final Job job) {
		this.jobDao.saveOrUpdate(job);
		LOGGER.info("Adding job '" + job.getId() + "' to queue");
		this.jobs.add(job);
		LOGGER.info("Queue size = " + this.jobs.size());
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Job> getJobs(final String userId) {
		if (userId == null || userId.length() == 0) {
			throw new IllegalArgumentException("User ID cannot be empty");
		}
		Collection<Job> userJobs = new ArrayList<Job>();
		for (Job job : this.jobs) {
			if (userId.equals(job.getUserId())) {
				userJobs.add(job);
			}
		}
		return userJobs;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(final Long jobId) {
		if (jobId == null) {
			throw new IllegalArgumentException("Job ID cannot be null");
		}
		boolean success = false;
		Job targetJob = null;
		for (Job job : this.jobs) {
			if (jobId.equals(job.getId())) {
				targetJob = job;
				break;
			}
		}
		if (targetJob != null) {
			if (!this.jobRunning(targetJob)) {
				LOGGER.info("Removing job '" + targetJob.getId() + "'");
				this.jobs.remove(targetJob);
				this.jobDao.delete(targetJob);
				success = true;
			} else {
				LOGGER.info("Could not remove job '" + targetJob.getId()
						+ "'.  It is running.");
			}
		} else {
			LOGGER.info("Could not remove job '" + targetJob.getId()
					+ "'.  Job not found.");
		}
		return success;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void purge(final String userId) {
		Collection<Job> userJobs = this.getJobs(userId);
		for (Job job : userJobs) {
			this.remove(job.getId());
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public Collection<Job> getNewlyCompletedJobs(final String userId) {
		Collection<Job> completedJobs = new ArrayList<Job>();
		Collection<Job> allJobs = this.getJobs(userId);
		for (Job job : allJobs) {
			if (job.getEndDate() != null && !job.isUserNotifiedOfCompletion()) {
				completedJobs.add(job);
				job.setUserNotifiedOfCompletion(true);
				this.jobDao.saveOrUpdate(job);
			}
		}
		return completedJobs;
	}


	/**
	 * Is given job running?
	 * @param job A job
	 * @return {@code true} if job is executing, {@code false} otherwise
	 */
	private boolean jobRunning(final Job job) {
		return job.getStartDate() != null && job.getEndDate() == null;
	}

	
	/**
	 * Comparator for sorting {@link Job} objects on the
	 * attribute {@code instantiationDate}.  If jobs have same
	 * instantiation date, tie is broken by
	 * attribute {@code id}.
	 * @author dhall
	 *
	 */
	private static class InstantiationDateJobComparator
	implements Comparator<Job> {

		/**
		 * {@inheritDoc}
		 */
		public int compare(final Job job1, final Job job2) {
			int val = job1.getInstantiationDate().
			compareTo(job2.getInstantiationDate());
			if (val == 0) {
				val = job1.getId().compareTo(job2.getId());
			}
			return val;
		}
	}
	
	
	/**
	 * A thread that executes jobs by looping over queue
	 * of jobs and grabbing the next one on the queue whenever
	 * the previous job has completed.
	 * @author dhall
	 *
	 */
	private class JobExecutionThread extends Thread {
		
		/**
		 * Constructor.
		 */
		public JobExecutionThread() {
			super();
			this.setPriority(JOB_EXECUTION_THREAD_PRIORITY);
		}
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized void run() {
			LOGGER.info("Starting job execution queue");
			this.monitorJobQueue();
		}



		/**
		 * Monitor the job queue by executing jobs
		 * in the order that they were added to the queue
		 * and waiting if there are no jobs until the next
		 * one is added.
		 */
		private void monitorJobQueue() {
			JobServices jobServices = new JobServices(
					ioService, analysisService, plotService,
					shoppingCartDao, arrayDao);
			while (true) {
				
				// Get next job from queue
				Job job = this.next();
				while (job == null) {
					try {
						Thread.sleep(JOB_EXECUTION_THREAD_SLEEP_TIME);
					} catch (InterruptedException e) {
						throw new WebGenomeSystemException(
								"Error putting job execution "
								+ "thread to sleep", e);
					}
					job = this.next();
				}
				
				// Set start time and persist
				LOGGER.info("Starting job with id '" + job.getId() + "'");
				job.setStartDate(new Date());
				jobDao.saveOrUpdate(job);
				
				// Execute job
				try {
					job.execute(jobServices);
					LOGGER.info("Job '" + job.getId()
							+ "' successfully completed");
				} catch (Exception e) {
					LOGGER.warn("Job '" + job.getId() + "' failed");
					LOGGER.warn(e);
					Throwable throwable = e;
					while (throwable != null) {
						e.printStackTrace(System.err);
						throwable = throwable.getCause();
					}
					String exceptionMsg = e.getMessage();
					String msg = Job.JOB_EXECUTION_FAILURE_MESSAGE;
					if (exceptionMsg != null && exceptionMsg.length() > 0) {
						msg += ": " + exceptionMsg;
					}
					job.setTerminationMessage(msg);
				}
				
				// Set end time and persist
				job.setEndDate(new Date());
				jobDao.saveOrUpdate(job);
			}
		}
		
		
		/**
		 * Get next job on queue.  This will be the first
		 * job in the iteration which has a start date
		 * of null.
		 * @return Next job on queue.
		 */
		private Job next() {
			Job nextJob = null;
			for (Job job : jobs) {
				if (job.getStartDate() == null) {
					nextJob = job;
					break;
				}
			}
			return nextJob;
		}
	}
}
