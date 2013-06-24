/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.service.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.EJBHome;

import org.rti.webgenome.core.WebGenomeSystemException;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

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
	private Map<String, EJBHome> localHomeCache =
		new HashMap<String, EJBHome>();
	
	
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
			throw new WebGenomeSystemException(
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
	public final EJBHome getLocalHome(final String jndiHomeName, final String jndiProviderURL )
		throws NamingException {
		EJBHome localHome = null;
        
        Properties properties = new Properties();
        // INITIAL_CONTEXT_FACTORY and URL_PKG_PREFIXES could be placed into the properties settings
        // and dependency injected either here, or just passed in as method parameters,
        // but to keep things simple, they're hard-coded in here.
        // TODO: might move these settings into properties file later (?)
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "org.jnp.interfaces");
        properties.put(Context.PROVIDER_URL,  jndiProviderURL );
        
        this.initialContext = new InitialContext( properties );

		if (this.localHomeCache.containsKey(jndiHomeName)) {
			localHome = (EJBHome) this.localHomeCache.get(jndiHomeName);
		} else {
			localHome = (EJBHome) this.initialContext.lookup( jndiHomeName);
			this.localHomeCache.put(jndiHomeName, localHome);
		}
        return localHome;
    }
}
