/*
$Revision: 1.3 $
$Date: 2007-08-20 22:09:37 $

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

import java.util.Date;


/**
 * This interface represents a compute job that
 * can be placed on a queue and run in the background.
 * @author dhall
 *
 */
public interface Job {
	
	/**
	 * Get unique identifier for job.
	 * @return Identifier
	 */
	Long getId();
	
	/**
	 * Set unique identifier for job.
	 * @param id Unique identifier
	 */
	void setId(Long id);

	
	/**
	 * Get user identifier (i.e., user name).
	 * @return User identifier
	 */
	String getUserId();
	
	/**
	 * Set user identifier (i.e., user name).
	 * @param userId User identifier
	 */
	void setUserId(String userId);
	
	/**
	 * Get date/time that the job was instantiated.
	 * @return Date/time that job was instantiated
	 */
	Date getInstantiationDate();
	
	/**
	 * Set date/time that job was instantiated.
	 * @param date Date/time job was instantiated.
	 */
	void setInstantiationDate(Date date);

	
	/**
	 * Get date/time that job was started.
	 * @return Date/time that job was started.
	 */
	Date getStartDate();
	
	/**
	 * Set date/time that job was started.
	 * @param date Date/time job was started.
	 */
	void setStartDate(Date date);
	
	/**
	 * Get date/time that job ended.
	 * @return Date/time that job ended.
	 */
	Date getEndDate();
	
	/**
	 * Set date/time that job ended.
	 * @param date Date/time that job ended.
	 */
	void setEndDate(Date date);
	
	/**
	 * Set the message that describes the
	 * state of the job upon termination
	 * if it finished successfullly or threw
	 * and exception.
	 * @param message A message
	 */
	void setTerminationMessage(String message);
	
	
	/**
	 * Get the message that describes the
	 * state of the job upon termination
	 * if it finished successfullly or threw
	 * and exception.
	 * @return A message
	 */
	String getTerminationMessage();
	
	/**
	 * Set description of job.
	 * @param description Job description.
	 */
	void setDescription(String description);
	
	/**
	 * Get description of job.
	 * @return Description of job.
	 */
	String getDescription();
	
	/**
	 * Set property that indicates user has been notified that
	 * the job is complete.
	 * @param notified Has the user been notified that the job
	 * is complete?  This should only be set to true if the
	 * job is complete and the user has been notified.
	 */
	void setUserNotifiedOfCompletion(boolean notified);
	
	/**
	 * Has the user been notified that the job is complete?
	 * @return {@code true} if the job is complete and the user
	 * has been notified.  Returns {@code false} if the job
	 * is not complete or it is complete but the user has not
	 * been notified.
	 */
	boolean isUserNotifiedOfCompletion();
	
	/**
	 * Execute job.
	 * @param jobServices Services needed by job
	 * to execute
	 */
	void execute(JobServices jobServices);
}
