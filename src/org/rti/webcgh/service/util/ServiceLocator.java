/*
$Revision: 1.2 $
$Date: 2006-10-05 03:59:45 $

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

package org.rti.webcgh.service.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.EJBLocalHome;

import org.rti.webcgh.core.WebcghSystemException;

import java.util.Map;
import java.util.HashMap;

/**
 * A class to remotely retrieve the EJB from the client given the JNDI name.
 *
 */
public class ServiceLocator {
	
	// =============================
	//     Attributes
	// =============================
	
	/** Initial context for naming. */
	private InitialContext initialContext = null;
   
   	/** Caches local EJB home references. Keys are JNDI names. */
	private Map<String, EJBLocalHome> localHomeCache =
		new HashMap<String, EJBLocalHome>();
	
	
	// =================================
	//      Constructor
	// =================================
	
	/**
	 * Constructor.
	 */
	public ServiceLocator() {
		try {
			this.initialContext = new InitialContext();
		} catch (NamingException e) {
			throw new WebcghSystemException(
					"Error getting initial context", e);
		}
	}

	
	// =================================
	//      Business methods
	// =================================

	/**
	 * Gets the home class locally.
	 * @param jndiHomeName JNDI name of the local home class
	 * @return Object The home class of the EJB
	 * @throws NamingException if JNDI name lookup fails
	 */
	public final EJBLocalHome getLocalHome(final String jndiHomeName)
		throws NamingException {
		EJBLocalHome localHome = null;
		if (this.localHomeCache.containsKey(jndiHomeName)) {				
			localHome = (EJBLocalHome) this.localHomeCache.get(jndiHomeName);
		} else {
			localHome = (EJBLocalHome) this.initialContext.lookup(jndiHomeName);
			this.localHomeCache.put(jndiHomeName, localHome);
		}
        return localHome;
    }
}
