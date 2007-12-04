/*
$Revision: 1.8 $
$Date: 2007-12-04 23:06:40 $

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

package org.rti.webgenome.webui.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.UploadedData;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.service.client.ClientDataServiceManager;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.cart.SelectedExperimentsForm;

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
	
	/** Key of client data service manager. */
	private static final String KEY_CLIENT_DATA_SERVICE_MANAGER =
		"key.client.data.service.manager";
	
	/** Key to upload object. */
	private static final String KEY_UPLOAD = "key.upload";
	
	/** Key to a single file that was uploaded. */
	private static final String KEY_UPLOADED_DATA = "key.uploaded.file";
	
	/** Key to metadata on uploaded ZIP file. */
	private static final String KEY_ZIP_FILE_META_DATA =
		"key.zip.file.meta.data";
	
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
		ShoppingCart cart = (ShoppingCart)
		request.getSession().getAttribute(KEY_SHOPPING_CART);
		if (cart == null) {
			throw new SessionTimeoutException("Session has expired");
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
	 * Set upload object.
	 * @param upload Upload object
	 * @param request Servlet request
	 */
	public static void setUpload(final UploadDataSourceProperties upload,
			final HttpServletRequest request) {
		request.getSession().setAttribute(KEY_UPLOAD, upload);
	}
	
	/**
	 * Remove upload object.
	 * @param request A request
	 */
	public static void removeUpload(final HttpServletRequest request) {
		request.getSession().removeAttribute(KEY_UPLOAD);
	}
	
	/**
	 * Set zip file metadata.
	 * @param meta Zip file metadata
	 * @param request Servlet request
	 */
	public static void setZipFileMetaData(final ZipFileMetaData meta,
			final HttpServletRequest request) {
		request.getSession().setAttribute(KEY_ZIP_FILE_META_DATA, meta);
	}
	
	/**
	 * Get upload object.
	 * @param request Servlet request
	 * @return Upload object
	 * @throws SessionTimeoutException If upload object cannot be found,
	 * which would indicate that the session has timed out
	 */
	public static UploadDataSourceProperties getUpload(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		return PageContext.getUpload(request, true);
	}
	
	
	/**
	 * Get upload object.
	 * @param request Servlet request
	 * @param exceptionIfMissing Throw exception if it
	 * cannot be found?
	 * @return Upload object
	 * @throws SessionTimeoutException If upload object cannot be found
	 * and <code>createIfMissing</code> is false,
	 * which would indicate that the session has timed out
	 */
	public static UploadDataSourceProperties getUpload(
			final HttpServletRequest request,
			final boolean exceptionIfMissing)
	throws SessionTimeoutException {
		UploadDataSourceProperties upload = (UploadDataSourceProperties)
		request.getSession().getAttribute(KEY_UPLOAD);
		if (upload == null && exceptionIfMissing) {
			throw new SessionTimeoutException("Session has expired");
		}
		return upload;
	}
	
	/**
	 * Get zip file metadata.
	 * @param request Servlet request
	 * @return Zip file metadata object
	 * @throws SessionTimeoutException If upload object cannot be found,
	 * which would indicate that the session has timed out
	 */
	public static ZipFileMetaData getZipFileMetaData(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		ZipFileMetaData meta = (ZipFileMetaData)
			request.getSession().getAttribute(KEY_ZIP_FILE_META_DATA);
		if (meta == null) {
			throw new SessionTimeoutException("Session has expired");
		}
		return meta;
	}
	
	/**
	 * Get data that were uploaded.
	 * @param request Servlet request
	 * @return Uploaded data
	 * @throws SessionTimeoutException If the uploaded file property is
	 * null, indicating a session timeout.
	 */
	public static UploadedData getUploadedData(final HttpServletRequest request)
	throws SessionTimeoutException {
		UploadedData data = (UploadedData)
			request.getSession().getAttribute(KEY_UPLOADED_DATA);
		if (data == null) {
			throw new SessionTimeoutException("Session has expired");
		}
		return data;
	}
	
	/**
	 * Set data that were uploaded.
	 * @param uploadedData Data that were uploaded
	 * @param request Servlet request
	 */
	public static void setUploadedData(final UploadedData uploadedData,
			final HttpServletRequest request) {
		request.getSession().setAttribute(KEY_UPLOADED_DATA, uploadedData);
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
