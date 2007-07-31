/*
$Revision: 1.2 $
$Date: 2007-07-31 16:28:14 $

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
import java.util.HashMap;
import java.util.Map;

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
			new SerialQueueJobManager(new JobDaoImpl());
		
		// Add several jobs
		for (int i = 0; i < NUM_JOBS; i++) {
			man.add(new JobImpl(USER));
		}
		
		// Get jobs from manager and examine
		Collection<Job> jobs = man.getJobs(USER);
		assertNotNull(jobs);
		assertEquals(NUM_JOBS, jobs.size());
		for (Job job : jobs) {
			assertNotNull(job.getInstantiationDate());
			assertNull(job.getStartDate());
			assertNull(job.getEndDate());
		}
		jobs = man.getJobs(WRONG_USER);
		assertNotNull(jobs);
		assertEquals(0, jobs.size());
		
		// Let some jobs execute and examine
		Thread.sleep(JOB_DURATION * NUM_JOBS / 2);
		jobs = man.getJobs(USER);
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
		jobs = man.getJobs(USER);
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
		jobs = man.getJobs(USER);
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
		 */
		private JobImpl(final String user) {
			this.setUserId(user);
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
