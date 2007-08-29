/*
$Revision: 1.7 $
$Date: 2007-08-29 19:29:20 $

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
	 * @return All current jobs associated with given user
	 */
	Collection<Job> getJobs(String userId);
	
	/**
	 * Purge all copleted job records associated with
	 * given user.
	 * @param userId User login name
	 */
	void purge(String userId);
	
	/**
	 * This method returns a list of jobs associated
	 * with given user ID that have completed
	 * since the last call of this method.
	 * @param userId User login name
	 * @return List of user jobs that have completed since the
	 * last call of this method.
	 */
	Collection<Job> getNewlyCompletedJobs(String userId);
	
	/**
	 * This method returns a list of jobs associated
	 * with given user ID that have started
	 * since the last call of this method.
	 * @param userId User login name
	 * @return List of user jobs that have started since the
	 * last call of this method.
	 */
	Collection<Job> getNewlyStartedJobs(String userId);
}
