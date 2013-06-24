/*
$Revision: 1.8 $
$Date: 2007-10-10 17:47:01 $


*/

package org.rti.webgenome.service.job;

import java.util.Collection;


/**
 * This interface is intended to be used as a singleton
 * that manages all {@link Job} objects within the application.
 * Object implementing this interface are expected to manage the
 * execution of jobs.
 * @author dhall
 *
 */
public interface JobManager {

	/**
	 * Removes {@link Job} with given
	 * {@code jobId} from the manager.
	 * @param jobId Unique identifier of job under management
	 * of this manager.
	 * @return {@code true} if the job was successfully
	 * removed, {@code false} otherwise.  A job cannot be
	 * removed if it is either executing or it is not
	 * under management by this manager.
	 */
	boolean remove(Long jobId);
	
	/**
	 * Add given job to the manager.  The manager will execute
	 * the job when resources are available.
	 * @param job Job to add to management
	 */
	void add(Job job);
	
	/**
	 * Get all current jobs associated with given user.
	 * @param userId Id (i.e., user name) of a user
	 * @param userDomain Domain in which the user ID is valid
	 * @return All current jobs associated with given user
	 */
	Collection<Job> getJobs(Long userId, String userDomain);
	
	/**
	 * Purge all completed job records associated with
	 * given user.
	 * @param userId User login name
	 * @param userDomain Domain in which the user ID is valid
	 */
	void purge(Long userId, String userDomain);
	
	/**
	 * This method returns a list of jobs associated
	 * with given user ID that have completed
	 * since the last call of this method.
	 * @param userId User login name
	 * @param userDomain Domain in which the user ID is valid
	 * @return List of user jobs that have completed since the
	 * last call of this method.
	 */
	Collection<Job> getNewlyCompletedJobs(Long userId, String userDomain);
	
	/**
	 * This method returns a list of jobs associated
	 * with given user ID that have started
	 * since the last call of this method.
	 * @param userId User login name
	 * @param userDomain Domain in which the user ID is valid
	 * @return List of user jobs that have started since the
	 * last call of this method.
	 */
	Collection<Job> getNewlyStartedJobs(Long userId, String userDomain);
}
