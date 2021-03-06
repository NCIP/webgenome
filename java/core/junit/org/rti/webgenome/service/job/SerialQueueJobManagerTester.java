/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2008-02-22 18:24:45 $


*/

package org.rti.webgenome.service.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.service.session.DaoWebGenomeDbService;

import junit.framework.TestCase;

/**
 * Tester for {@link SerialQueueJobManager}.
 * @author dhall
 *
 */
public class SerialQueueJobManagerTester extends TestCase {
	
	/** Duration of mock job in milliseconds. */
	private static final long JOB_DURATION = 2000;
	
	/** Number of jobs to instantiate at one time during testing. */
	private static final int NUM_JOBS = 6;
	
	/** User name to associated with jobs. */
	private static final String USER = "user";
	
	/** Domain in which user name is applied. */
	private static final String DOMAIN = "domain";
	
	/**
	 * User name of "wrong" user.  No jobs will be associated
	 * with this user.
	 */
	private static final String WRONG_USER = "wrong.user";
	
	
	/**
	 * Test all methods.
	 * @throws Exception if something goes wrong.
	 */
	public void testAllMethods() throws Exception {
		
		// Instantiate manager
		SerialQueueJobManager man =
			new SerialQueueJobManager(new WebGenomeDbServiceImpl(
					new JobDaoImpl()));
		
		// Add several jobs
		for (int i = 0; i < NUM_JOBS; i++) {
			man.add(new JobImpl(USER, DOMAIN));
		}
		
		// Get jobs from manager and examine
		Collection<Job> jobs = man.getJobs(USER, DOMAIN);
		assertNotNull(jobs);
		assertEquals(NUM_JOBS, jobs.size());
		for (Job job : jobs) {
			assertNotNull(job.getInstantiationDate());
			assertNull(job.getStartDate());
			assertNull(job.getEndDate());
		}
		jobs = man.getJobs(WRONG_USER, DOMAIN);
		assertNotNull(jobs);
		assertEquals(0, jobs.size());
		
		// Let some jobs execute and examine
		Thread.sleep(JOB_DURATION * NUM_JOBS / 2);
		jobs = man.getJobs(USER, DOMAIN);
		assertNotNull(jobs);
		assertEquals(NUM_JOBS, jobs.size());
		boolean someFinished = false;
		boolean someNotFinished = false;
		boolean someStarted = false;
		boolean someNotStarted = false;
		for (Job job : jobs) {
			if (job.getStartDate() != null) {
				someStarted = true;
			} else {
				someNotStarted = true;
			}
			if (job.getEndDate() != null) {
				someFinished = true;
			} else {
				someNotFinished = true;
			}
		}
		assertTrue(someStarted);
		assertTrue(someNotStarted);
		assertTrue(someFinished);
		assertTrue(someNotFinished);
		
		// Let all jobs finish and examine
		Thread.sleep(JOB_DURATION * NUM_JOBS);
		jobs = man.getJobs(USER, DOMAIN);
		assertNotNull(jobs);
		assertEquals(NUM_JOBS, jobs.size());
		boolean allFinished = true;
		for (Job job : jobs) {
			if (job.getEndDate() == null) {
				allFinished = false;
			}
		}
		assertTrue(allFinished);
		
		// Remove job and examine
		Job job = jobs.iterator().next();
		man.remove(job.getId());
		jobs = man.getJobs(USER, DOMAIN);
		assertNotNull(jobs);
		assertEquals(NUM_JOBS - 1, jobs.size());
	}
	
	
	/**
	 * Mock oject class implementing {@link org.rti.webgenome.service.job.Job}.
	 * @author dhall
	 */
	private static final class JobImpl extends AbstractJob {
		
		/**
		 * Constructor.
		 * @param user User id (i.e. user name)
		 * @param domain Domain user ID is applied to
		 */
		private JobImpl(final String user, final String domain) {
			this.setUserId(user);
			this.setUserDomain(domain);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void execute(final JobServices jobServices) {
			try {
				Thread.sleep(JOB_DURATION);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Mock object for testing.
	 * @author dhall
	 *
	 */
	private static final class WebGenomeDbServiceImpl
	extends DaoWebGenomeDbService {
		
		/**
		 * Constructor.
		 * @param jobDao Job data access object
		 */
		public WebGenomeDbServiceImpl(final JobDao jobDao) {
			this.setJobDao(jobDao);
		}
	}

	/**
	 * Mock object class implementing
	 * {@link org.rti.webgenome.service.job.JobDao}.
	 * Jobs are "persisted" in memory.
	 * @author dhall
	 */
	private static final class JobDaoImpl implements JobDao {
		
		/** Map of Job.id to Job. */
		private Map<Long, Job> jobMap = new HashMap<Long, Job>();
		
		/**
		 * Count of objects instantiated so far.  This is used
		 * to assign IDs.
		 */
		private long counter = 0;

		/**
		 * {@inheritDoc}
		 */
		public void delete(final Job job) {
			this.jobMap.remove(job);
		}

		/**
		 * {@inheritDoc}
		 */
		public Collection<Job> loadAll() {
			return new ArrayList<Job>();
		}

		/**
		 * {@inheritDoc}
		 */
		public void saveOrUpdate(final Job job) {
			if (job.getId() == null) {
				job.setId(++this.counter);
			}
			if (!this.jobMap.containsKey(job.getId())) {
				this.jobMap.put(job.getId(), job);
			}
		}
	}
}
