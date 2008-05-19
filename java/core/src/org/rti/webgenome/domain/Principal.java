/*
$Revision: 1.3 $
$Date: 2008-05-19 20:11:02 $

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

	/** User name. */
	private String name = null;
	
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
	private String email = null;
	
	
	
	
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
	 * Sets whether a user is an admin.
	 * @param admin Is user an admin?
	 */
	public final void setAdmin(final boolean admin) {
		this.admin = admin;
	}


	/**
	 * Get use name.
	 * @return User name.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Set user name.
	 * @param name User name.
	 */
	public final void setName(final String name) {
		this.name = name;
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
	 * Constructor.
	 */
	public Principal() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param name User name.
	 * @param password Password.
	 * @param domain Authentication domain
	 */
	public Principal(final String name, final String password,
			final String domain) {
		this.name = name;
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
