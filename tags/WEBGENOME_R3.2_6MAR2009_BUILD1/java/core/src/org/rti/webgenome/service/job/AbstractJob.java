/*
$Revision: 1.6 $
$Date: 2008-05-28 19:39:39 $


*/

package org.rti.webgenome.service.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;


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
	private Long userId = null;
	
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
	
	/**
	 * 
	 * Contains key-value pair in format key1@value1@key2@value2. The purpose is
	 * to store parameters that need to propagate back to the user when job status
	 * is finished.
	 * 
	 */
	private String params = null;
	
	
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
	 * @param userId User account id (from Principal).
	 * @param userDomain Domain in which user name is valid
	 */
	protected AbstractJob(final Long userId, final String userDomain) {
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
	public Long getUserId() {
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
	public void setUserId(final Long userId) {
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


	public String getParams() {
		return params;
	}


	public void setParams(String params) {
		this.params = params;
	}
	
	/**
	 * Will create parameter string that is in key1@value1@key2@value2 format from 
	 * parameters map.
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	public void setParamsMap(Map<String, String> paramsMap) throws Exception{
		Set<Entry<String, String>> entries = paramsMap.entrySet();
		
		// prepare params as string
		for (Entry e:entries){
			params += e.getKey() + Job.PARAMS_DELIMITER;
			params += e.getValue() + Job.PARAMS_DELIMITER;			
		}
	}
	
	/**
	 * Will parse parameters string that is in key1@value1@key2@value2 format to 
	 * create parameters map.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getParamsMap() throws Exception{
		StringTokenizer st = new StringTokenizer(params, Job.PARAMS_DELIMITER);
		String key = "";
		String value = "";
		Map paramsMap = new HashMap();
		
		while (st.hasMoreElements()){
			key = st.nextToken();
			value = st.nextToken();
			paramsMap.put(key, value);
		}
		
		return paramsMap;
	}
}
