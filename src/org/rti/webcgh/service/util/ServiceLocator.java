/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/util/ServiceLocator.java,v $
$Revision: 1.1 $
$Date: 2006-09-05 14:06:44 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.service.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.EJBHome;
import javax.rmi.PortableRemoteObject;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * A class to remotely retrieve the EJB from the cilent given the JNDI name
 *
 */
public class ServiceLocator {
   private InitialContext initialContext;
   private Map cache;
   private String deployment ;

   private static ServiceLocator ONLY_INSTANCE;

   static  {
           ONLY_INSTANCE = new ServiceLocator();
   }
   static public ServiceLocator getInstance() {
       return ONLY_INSTANCE;
   }

   /**
    * Gets the home class for the EJB
    * @param environment
    * @param jndiName JDNI name of client's EJB
    * @param narrowTo
    * @return Object Client EJB
    * @throws javax.naming.NamingException
    */
   public  Object locateHome(java.util.Hashtable environment, String jndiName, Class narrowTo) throws javax.naming.NamingException {
          if (environment != null)
             initialContext = new javax.naming.InitialContext(environment);
          try {
             Object objRef ;
             if (cache.containsKey(jndiName)) {
                    objRef  = cache.get(jndiName);
             } else {
                    Object remObjRef = initialContext.lookup(jndiName);
                    // narrow only if necessary
                    if (narrowTo.isInstance(java.rmi.Remote.class)) {
                        objRef = javax.rmi.PortableRemoteObject.narrow(remObjRef , narrowTo);
                    } else {
                        objRef = remObjRef;
                    }
                    cache.put(jndiName, remObjRef);
             }
             return objRef;
          } finally {
             initialContext.close();
          }

       }


   /**
    * Instantiates initial context of the bean
    *
    */
  private ServiceLocator() {
       try {
           initialContext = getInitialContext();
           cache = Collections.synchronizedMap(new HashMap());
       } catch(NamingException ne) {
           ne.printStackTrace();
       } catch(Exception e) {
           e.printStackTrace();
       }
   }

  /**
   * Gets the home class locally
   * @param jndiHomeName JNDI name of the local home class
   * @return Object The home class of the EJB
   * @throws Exception
   */
   public Object getLocalHome(String jndiHomeName) throws Exception{
        Object localHome = null;
		
        try {
            if (cache.containsKey(jndiHomeName)) {				
                localHome = cache.get(jndiHomeName);
            } else {
                localHome = initialContext.lookup(jndiHomeName);				
                cache.put(jndiHomeName, localHome);
            }
        } catch(NamingException ne) {
				ne.printStackTrace();
				throw new Exception(ne.getMessage());											
        } catch(Exception e){
			   // TODO: Figure out later the cause!!! Now added just a work around to try second
			   // time. Very strange problem - first time it crashes and second it's o'right
			   try{
					 localHome = initialContext.lookup(jndiHomeName);
				} catch(NamingException ne1) {
					ne1.printStackTrace();
					throw new Exception(ne1.getMessage());			
				}
		}
        return localHome;
    }

   /**
    * Gets the home class remotely
    * @param jndiHomeName The JNDI name of the remote home class
    * @param homeClassName The JNDI name of the local home class
    * @return EJBHome The remote home class of the EJB
    * @throws Exception
    */
    public EJBHome getRemoteHome(String jndiHomeName, Class homeClassName) throws Exception{
        EJBHome remoteHome = null;
        try {
            if (cache.containsKey(jndiHomeName)) {
                remoteHome  = (EJBHome) cache.get(jndiHomeName);
            } else {
                Object objRef = initialContext.lookup(jndiHomeName);
                Object obj = PortableRemoteObject.narrow(objRef, homeClassName);
                remoteHome = (EJBHome) obj;
                cache.put(jndiHomeName, remoteHome);
            }
        } catch(NamingException ne) {
            ne.printStackTrace();
            throw new Exception(ne.getMessage());
        }
        return remoteHome;
    }
    
    /**
     * Gets the initial context of the EJB
     * @return InitialContext Intial context object
     * @throws Exception
     */
    private InitialContext getInitialContext () throws Exception{
        InitialContext ctx;
        try {
            Properties p = System.getProperties();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/jndi.properties");
            
            p.load(is);
            deployment = p.getProperty("jndi.deployment");
            ctx = new InitialContext(p);
        } catch(IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        return ctx;
    }
    
    /**
     * Gets deployment name
     * @return String Deployment name
     */
    public String getDeployment() {
          return deployment;
    }
}