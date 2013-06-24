/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-05-19 20:11:02 $


*/

package org.rti.webgenome.domain;

/**
 * Represents a user of the system.
 * @author dhall
 *
 */
public class Principal {
	
	// ======================
	//      Attributes
	// ======================

	
	/** Identifier used as primary key for persistence. */
	private Long id = null;

	/** Email. Users specify their email address as their login name */
	private String email = null;

	/** Password. */
	private String password = null;
	
	/** Authentication domain for principal. */
	private String domain = null;

	/** Is user an administrator? */
	private boolean admin = false;
	
	private String firstName = "";
	private String lastName = "";
	private String institution = "";
	private String department = "";
	private String position = "";
	private String degree = "";
	private String phone = "";
	private String address = "";
	private boolean feedbacks = false;
	
	
	
	
	// ==========================
	//     Getters/setters
	// ==========================
	
	/**
	 * Get authentication domain for principal.
	 * @return Authentication domain.  This will typically be
	 * the name of some credential provider.
	 */
	public String getDomain() {
		return domain;
	}


	/**
	 * Set authentication domain for principal.
	 * @param domain Authentication domain.  This will typically be
	 * the name of some credential provider.
	 */
	public void setDomain(final String domain) {
		this.domain = domain;
	}


	/**
	 * Get ID used as primary key for persistence.
	 * @return ID used as primary key for persistence.
	 */
	public final Long getId() {
		return id;
	}

	
	/**
	 * Set ID used as primary key for persistence.
	 * @param id ID used as primary key for persistence.
	 */
	public final void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Is use an admin?
	 * @return T/F
	 */
	public final boolean isAdmin() {
		return admin;
	}


	/**
	 * Sets whether a user is an administration user.
	 * @param admin Is user an administration user?
	 */
	public final void setAdmin(final boolean admin) {
		this.admin = admin;
	}


	/**
	 * Get password.
	 * @return Password.
	 */
	public final String getPassword() {
		return password;
	}

	
	/**
	 * Set password.
	 * @param password Password.
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}
	
	
	// =========================
	//     Constructors
	// =========================
	
	/**
	 * Empty constructor
	 */
	public Principal () { }
	
	/**
	 * Constructor.
	 * @param email User Email (used at login time)
	 * @param password Password.
	 * @param domain Authentication domain
	 */
	public Principal( final String email,
			          final String password,
			          final String domain) {
		this.email = email;
		this.password = password;
		this.domain = domain;
	}
	

	public String getInstitution() {
		return institution;
	}


	public void setInstitution(String institution) {
		this.institution = institution;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isFeedbacks() {
		return feedbacks;
	}


	public void setFeedbacks(boolean feedbacks) {
		this.feedbacks = feedbacks;
	}
	
}
