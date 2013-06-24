/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.8 $
$Date: 2006-12-03 22:23:45 $

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
import javax.servlet.http.HttpSession;

import org.rti.webcgh.domain.Principal;
import org.rti.webcgh.domain.ShoppingCart;
import org.rti.webcgh.graphics.util.ColorChooser;
import org.rti.webcgh.service.client.ClientDataServiceManager;
import org.rti.webcgh.service.util.IdGenerator;
import org.rti.webcgh.webui.SessionTimeoutException;
import org.rti.webcgh.webui.struts.cart.SelectedExperimentsForm;

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
	
	/**
	 * Selected experiments form.
	 * 
	 * NOTE: THE VALUE OF THIS VARIABLES MUST BE EQUAL TO THE
	 * NAME OF A FORM BEAN OF TYPE <code>SelectedExperimentsForm</code>
	 * IN struts-config.xml.
	 */
	private static final String KEY_SELECTED_EXPERIMENTS_FORM =
		"selected.experiments.form";
	
	/** Key of color chooser. */
	private static final String KEY_COLOR_CHOOSER = "key.color.chooser";
	
	/** Key of plot ID generator. */
	private static final String KEY_PLOT_ID_GENERATOR = "key.plot.id.generator";
	
	/** Key of client data service manager. */
	private static final String KEY_CLIENT_DATA_SERVICE_MANAGER =
		"key.client.data.service.manager";
	
	/** Prefix for experiment IDs used in HTML form elements. */
	public static final String EXPERIMENT_ID_PREFIX = "exp_";
	
	
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
	 * @throws SessionTimeoutException If attribute not
	 * found in session.
	 */
	public static Principal getPrincipal(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		return (Principal)
			getSessionAttribute(request, KEY_PRINCIPAL);
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
	 * @throws SessionTimeoutException If attribute not
	 * found in session.
	 */
	public static SessionMode getSessionMode(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		return (SessionMode)
			getSessionAttribute(request, KEY_SESSION_MODE);
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
	 * @throws SessionTimeoutException If attribute not
	 * found in session.
	 */
	public static ShoppingCart getShoppingCart(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		return getShoppingCart(request, false);
	}
	
	
	/**
	 * Get shopping cart associated with session.
	 * @param request Servlet request.
	 * @param createIfMissing Create a new shopping cart if
	 * it cannot be found.
	 * @return Shopping cart associated with session.
	 * @throws SessionTimeoutException If attribute not
	 * found in session and <code>createIfMissing</code>
	 * is <code>false</code>.
	 */
	public static ShoppingCart getShoppingCart(
			final HttpServletRequest request,
			final boolean createIfMissing)
	throws SessionTimeoutException {
		ShoppingCart cart = (ShoppingCart)
			request.getSession().getAttribute(KEY_SHOPPING_CART);
		if (cart == null) {
			if (createIfMissing) {
				cart = new ShoppingCart();
				setShoppingCart(request, cart);
			} else {
				throw new SessionTimeoutException("Session has expired");
			}
		}
		return cart;
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
		
	/**
	 * Get selected experiments form.
	 * @param request Servlet request
	 * @param createIfMissing Create a new form if there is not
	 * one associated with session.
	 * @return Selected experiments form
	 */
	public static SelectedExperimentsForm getSelectedExperimentsForm(
			final HttpServletRequest request, final boolean createIfMissing) {
		SelectedExperimentsForm form = (SelectedExperimentsForm)
			request.getSession().getAttribute(KEY_SELECTED_EXPERIMENTS_FORM);
		if (form == null && createIfMissing) {
			form = new SelectedExperimentsForm();
			setSelectedExperimentsForm(request, form);
		}
		return form;
	}
	
	
	/**
	 * Set selected experiments form.
	 * @param request Servlet request
	 * @param form Selected experiments form.
	 */
	public static void setSelectedExperimentsForm(
			final HttpServletRequest request,
			final SelectedExperimentsForm form) {
		request.getSession().setAttribute(KEY_SELECTED_EXPERIMENTS_FORM, form);
	}
	
	
	/**
	 * Get color chooser from session.
	 * @param request Servlet request
	 * @param createIfMissing Create if missing from session
	 * @return Color chooser
	 * @throws SessionTimeoutException if session has timed
	 * out and cannot find color chooser.
	 */
	public static ColorChooser getColorChooser(
			final HttpServletRequest request,
			final boolean createIfMissing)
	throws SessionTimeoutException {
		HttpSession s = request.getSession();
		ColorChooser cc = (ColorChooser)
			s.getAttribute(KEY_COLOR_CHOOSER);
		if (cc == null) {
			if (createIfMissing) {
				cc = new ColorChooser();
				s.setAttribute(KEY_COLOR_CHOOSER, cc);
			} else {
				throw new SessionTimeoutException("Session timed out");
			}
		}
		return cc;
	}
	
	
	/**
	 * Get plot ID generator from session.
	 * @param request Servlet request
	 * @param createIfMissing Create if missing from session
	 * @return Plot ID generator
	 * @throws SessionTimeoutException if session has timed
	 * out and cannot find plot ID generator.
	 */
	public static IdGenerator getPlotIdGenerator(
			final HttpServletRequest request,
			final boolean createIfMissing)
	throws SessionTimeoutException {
		HttpSession s = request.getSession();
		IdGenerator gen = (IdGenerator)
			request.getSession().getAttribute(KEY_PLOT_ID_GENERATOR);
		if (gen == null) {
			if (createIfMissing) {
				gen = new IdGenerator();
				s.setAttribute(KEY_PLOT_ID_GENERATOR, gen);
			} else {
				throw new SessionTimeoutException("Session timed out");
			}
		}
		return gen;
	}
	
	
	/**
	 * Get client data service manager.
	 * @param request Servlet request
	 * @return Client data service manager
	 */
	public static ClientDataServiceManager getClientDataServiceManager(
			final HttpServletRequest request) {
		HttpSession sess = request.getSession();
		ClientDataServiceManager mgr = (ClientDataServiceManager)
			sess.getAttribute(KEY_CLIENT_DATA_SERVICE_MANAGER);
		if (mgr == null) {
			mgr = new ClientDataServiceManager();
			sess.setAttribute(KEY_CLIENT_DATA_SERVICE_MANAGER, mgr);
		}
		return mgr;
	}
	
	/**
	 * Get an attribute from session and throw a
	 * <code>SessionExpired</code> exception if
	 * attribute not found.
	 * @param request Servlet request
	 * @param attName Attribute name
	 * @return A session attribute
	 * @throws SessionTimeoutException If attribute not
	 * found in session.
	 */
	private static Object getSessionAttribute(
			final HttpServletRequest request, final String attName)
	throws SessionTimeoutException {
		HttpSession s = request.getSession();
		Object obj = s.getAttribute(attName);
		if (obj == null) {
			throw new SessionTimeoutException("Session timed out");
		}
		return obj;
	}
}
