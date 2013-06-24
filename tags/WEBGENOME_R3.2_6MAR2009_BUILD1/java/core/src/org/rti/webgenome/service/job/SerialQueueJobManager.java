/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.12 $
$Date: 2008-02-22 18:24:44 $


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
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.plot.PlotService;
import org.rti.webgenome.service.session.WebGenomeDbService;

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
	private static final int JOB_EXECUTION_THREAD_PRIORITY = Thread.MIN_PRIORITY;

	/**
	 * Time in milliseconds that the job execution thread
	 * sleeps when the queue is empty before checking again
	 * to see if there are any new jobs.
	 */
	private static final long JOB_EXECUTION_THREAD_SLEEP_TIME = 5000; // TBD / TODO - might be a nice configuration variable

	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger( SerialQueueJobManager.class );

	//
	//     A T T R I B U T E S
	//

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

	/** Facade for transactional webgenome db operations. */
	private WebGenomeDbService webGenomeDbService = null;


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
	 * Set service to perform plots.
	 * @param plotService Service to perform plots.
	 */
	public void setPlotService(final PlotService plotService) {
		this.plotService = plotService;
	}


	/**
	 * Inject a facade object for webgenome database operations.
	 * @param webGenomeDbService Facade object for transactional
	 * database operations
	 */
	public void setWebGenomeDbService(
			final WebGenomeDbService webGenomeDbService) {
		this.webGenomeDbService = webGenomeDbService;
	}


	//
	//     C O N S T R U C T O R S
	//


	/**
	 * Constructor.
	 * @param dbService Facade for transactional database operations
	 */
	public SerialQueueJobManager(final WebGenomeDbService dbService) {
		this.webGenomeDbService = dbService;

		// Get all persisted jobs
		Collection<Job> peristedJobs = this.webGenomeDbService.loadAllJobs();
		this.jobs.addAll(peristedJobs);

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
		this.webGenomeDbService.saveOrUpdateJob(job);
		LOGGER.info("Adding job '" + job.getId() + "' to queue");
		this.jobs.add(job);
		LOGGER.info("Queue size = " + this.jobs.size());
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Job> getJobs(final Long userId, final String userDomain) {
		if (userId == null ) {
			throw new IllegalArgumentException("User ID cannot be empty");
		}
		Collection<Job> userJobs = new ArrayList<Job>();
		for (Job job : this.jobs) {
			if (userId.equals(job.getUserId())
					&& userDomain.equals(job.getUserDomain())) {
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
				this.webGenomeDbService.deleteJob(targetJob);
				this.jobs.remove(targetJob);
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
	public void purge(final Long userId, final String userDomain) {
		Collection<Job> userJobs = this.getJobs(userId, userDomain);
		for (Job job : userJobs) {
			this.remove(job.getId());
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public Collection<Job> getNewlyCompletedJobs(final Long userId,
			final String userDomain) {
		Collection<Job> completedJobs = new ArrayList<Job>();
		Collection<Job> allJobs = this.getJobs(userId, userDomain);
		for (Job job : allJobs) {
			if (job.getEndDate() != null && !job.isUserNotifiedOfCompletion()) {
				completedJobs.add(job);
				job.setUserNotifiedOfCompletion(true);
				this.webGenomeDbService.saveOrUpdateJob(job);
			}
		}
		return completedJobs;
	}


	/**
	 * {@inheritDoc}
	 */
	public Collection<Job> getNewlyStartedJobs(
			final Long userId, final String userDomain) {
		Collection<Job> startedJobs = new ArrayList<Job>();
		Collection<Job> allJobs = this.getJobs(userId, userDomain);
		for (Job job : allJobs) {
			if (job.getStartDate() != null && !job.isUserNotifiedOfStart()) {
				startedJobs.add(job);
				job.setUserNotifiedOfStart(true);
				this.webGenomeDbService.saveOrUpdateJob(job);
			}
		}
		return startedJobs;
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
					webGenomeDbService);
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
				try {
					LOGGER.info("Starting job with id '" + job.getId() + "'");
					job.setStartDate(new Date());
					webGenomeDbService.saveOrUpdateJob(job);
				} catch (Exception e) {
					LOGGER.error("Unable to save job state", e);
				}

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
				try {
					job.setEndDate(new Date());
					webGenomeDbService.saveOrUpdateJob(job);
				} catch (Exception e) {
					LOGGER.error("Unable to save job state", e);
				}
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
