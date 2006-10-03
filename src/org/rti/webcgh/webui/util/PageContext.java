/*
$Revision: 1.1 $
$Date: 2006-10-03 14:55:41 $

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

package org.rti.webcgh.webui.util;

import javax.servlet.http.HttpServletRequest;

import org.rti.webcgh.domain.Principal;
import org.rti.webcgh.domain.ShoppingCart;

/**
 * Provides access to objects cached as attributes in the request
 * and session scope of a page request.
 * @author dhall
 *
 */
public final class PageContext {
	
	// =================================
	//     Key definitions
	// =================================
	
	/** Key for session attribute for <code>Principal</code>. */
	private static final String KEY_PRINCIPAL = "key.principal";
	
	/**
	 * Key for session attribute for <code>SessionMode</code>.
	 * This defines whether a particular session is stand-alone
	 * or client mode
	 */
	private static final String KEY_SESSION_MODE = "key.session.mode";
	
	/** Key for shopping cart associated with session. */
	private static final String KEY_SHOPPING_CART = "key.shopping.cart";
	
	
	// ==========================
	//       Constructors
	// ==========================
	
	/**
	 * Constructor.
	 */
	private PageContext() {
		
	}

	
	// =============================
	//     Business methods
	// =============================
	
	/**
	 * Get principal associated with current session.
	 * @param request Servlet request
	 * @return Principal associated with current session
	 */
	public static Principal getPrincipal(final HttpServletRequest request) {
		return (Principal)
			request.getSession().getAttribute(KEY_PRINCIPAL);
	}
	
	
	/**
	 * Set principal associated with current session.
	 * @param request Servlet request
	 * @param principal Principal
	 */
	public static void setPrincipal(final HttpServletRequest request,
			final Principal principal) {
		if (principal == null) {
			throw new IllegalArgumentException("Principal is null");
		}
		request.getSession().setAttribute(KEY_PRINCIPAL, principal);
	}
	
	/**
	 * Get session mode.
	 * @param request Servlet request.
	 * @return Session mode of a particular session.
	 */
	public static SessionMode getSessionMode(final HttpServletRequest request) {
		return (SessionMode)
			request.getSession().getAttribute(KEY_SESSION_MODE);
	}
	
	
	/**
	 * Set session mode.
	 * @param request Servlet request.
	 * @param sessionMode Session mode of a particular session.
	 */
	public static void setSessionMode(final HttpServletRequest request,
			final SessionMode sessionMode) {
		request.getSession().setAttribute(KEY_SESSION_MODE, sessionMode);
	}
	
	
	/**
	 * Get shopping cart associated with session.
	 * @param request Servlet request.
	 * @return Shopping cart associated with session.
	 */
	public static ShoppingCart getShoppingCart(
			final HttpServletRequest request) {
		return (ShoppingCart)
			request.getSession().getAttribute(KEY_SHOPPING_CART);
	}
	
	
	/**
	 * Set shopping cart associated with session.
	 * @param request Servlet request.
	 * @param shoppingCart Shopping cart
	 */
	public static void setShoppingCart(final HttpServletRequest request,
			final ShoppingCart shoppingCart) {
		request.getSession().setAttribute(KEY_SHOPPING_CART, shoppingCart);
	}
}
