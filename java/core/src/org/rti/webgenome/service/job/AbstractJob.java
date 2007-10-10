/*
$Revision: 1.5 $
$Date: 2007-10-10 17:47:01 $

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
 * Abstract base class providing default implementations
 * for methods in the {@link Job} interface.
 * @author dhall
 *
 */
public abstract class AbstractJob implements Job {
	
	//
	//     A T T R I B U T E S
	//
	
	/** Date/time object was instantiated. */
	private Date instantiationDate = null;
	
	/** Date/time job was started. */
	private Date startDate = null;
	
	/** Date/time job ended. */
	private Date endDate = null;
	
	/** Unique ID of job. */
	private Long id = null;
	
	/** Identifier of user to whom job is associated. */
	private String userId = null;
	
	/** Domain in which the user ID is applied. */
	private String userDomain = null;
	
	/** Message giving state of job upon termination. */
	private String terminationMessage = null;
	
	/** Job description. */
	private String description = null;
	
	/**
	 * Has the user been notified of the jobs completion?  If
	 * the job is not complete, this should be set to false.
	 */
	private boolean userNotifiedOfCompletion = false;
	
	/**
	 * Has the user been notified of the job starting?
	 * If ths job has not started, this should be set to false.
	 */
	private boolean userNotifiedOfStart = false;
	
	
	//
	//     C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	protected AbstractJob() {
		this.instantiationDate = new Date();
	}
	
	
	/**
	 * Constructor.
	 * @param userId User account name.
	 * @param userDomain Domain in which user name is valid
	 */
	protected AbstractJob(final String userId, final String userDomain) {
		this();
		this.userId = userId;
		this.userDomain = userDomain;
	}
	
	
	//
	//     I N T E R F A C E : Job
	//

	/**
	 * {@inheritDoc}
	 */
	public abstract void execute(JobServices jobServices);

	/**
	 * {@inheritDoc}
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getInstantiationDate() {
		return this.instantiationDate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setInstantiationDate(final Date date) {
		this.instantiationDate = date;
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTerminationMessage() {
		return this.terminationMessage;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEndDate(final Date date) {
		this.endDate = date;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setStartDate(final Date date) {
		this.startDate = date;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTerminationMessage(final String message) {
		this.terminationMessage = message;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * Get domain in which user name applies.
	 * @return A domain
	 */
	public String getUserDomain() {
		return userDomain;
	}


	/**
	 * Set domain in which user name applies.
	 * @param userDomain A domain
	 */
	public void setUserDomain(final String userDomain) {
		this.userDomain = userDomain;
	}


	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setDescription(final String description) {
		this.description = description;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isUserNotifiedOfCompletion() {
		return userNotifiedOfCompletion;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setUserNotifiedOfCompletion(
			final boolean userNotifiedOfCompletion) {
		this.userNotifiedOfCompletion = userNotifiedOfCompletion;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isUserNotifiedOfStart() {
		return userNotifiedOfStart;
	}


	/**
	 * {@inheritDoc}
	 */
	public void setUserNotifiedOfStart(
			final boolean userNotifiedOfStart) {
		this.userNotifiedOfStart = userNotifiedOfStart;
	}
	
	
}
