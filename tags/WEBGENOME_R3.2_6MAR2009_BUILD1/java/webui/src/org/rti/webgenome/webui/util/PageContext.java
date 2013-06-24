/*
$Revision: 1.11 $
$Date: 2008-10-23 16:17:18 $


*/

package org.rti.webgenome.webui.util;

import gov.nih.nci.caarray.domain.project.Experiment;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.domain.UploadedData;
import org.rti.webgenome.domain.ZipFileMetaData;
import org.rti.webgenome.service.client.CaArrayClient;
import org.rti.webgenome.service.client.ClientDataServiceManager;
import org.rti.webgenome.service.data.DataSourceSession;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.struts.cart.SelectedExperimentsForm;
import org.rti.webgenome.service.client.SupportedArrayDesigns;

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
	
	/** Key to data source session. */
	private static final String KEY_DATA_SOURCE_SESSION =
		"key.data.source.session";
	
	/** Prefix for experiment IDs used in HTML form elements. */
	public static final String EXPERIMENT_ID_PREFIX = "exp_";
	
	/** Key to retrieve caArray client*/
	public static final String KEY_CAARRAY_CLIENT = "key.caarray.client";
	
	/** Keys to retrieve caArray user name and password follow */
	public static final String KEY_CAARRAY_USER_NAME = "caarr.uname";
	public static final String KEY_CAARRAY_PASSWORD = "caarr.pass";
	
	/** Key to retrieve caArray experiment list*/
	public static final String KEY_CAARRAY_EXP_LIST = "key.caarray.exp.list";
	
	/** Key to retrieve caArray selected experiment list*/
	public static final String KEY_CAARRAY_SEL_EXP_LIST = "key.caarray.sel.exp.list";
	
	/** Key to retrieve supported caArray designs*/
	public static final String KEY_CAARRAY_SUPORTED_DESIGNS = "key.caarray.suported.designs";
	
	
	
	
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
	 * Is the session associated with the given request stand-alone mode?
	 * @param request A request
	 * @return T/F
	 * @throws SessionTimeoutException If the session mode cannot be
	 * determined indicating the session has expired.
	 */
	public static boolean standAloneMode(final HttpServletRequest request)
	throws SessionTimeoutException {
		return getSessionMode(request) == SessionMode.STAND_ALONE;
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
	 * Get data source session associated with web session.
	 * @param request Servlet request
	 * @return Data source session
	 * @throws SessionTimeoutException If data source session
	 * cannot be found,
	 * which would indicate that the session has timed out
	 */
	public static DataSourceSession getDataSourceSession(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		DataSourceSession sess = (DataSourceSession)
			request.getSession().getAttribute(KEY_DATA_SOURCE_SESSION);
		if (sess == null) {
			throw new SessionTimeoutException("Session has expired");
		}
		return sess;
	}
	
	/**
	 * Attach a data source session object to web session.
	 * @param request Servlet request
	 * @param dataSourceSession Data source session to attach
	 */
	public static void setDataSourceSession(
			final HttpServletRequest request,
			final DataSourceSession dataSourceSession) {
		request.getSession().setAttribute(
				KEY_DATA_SOURCE_SESSION, dataSourceSession);
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
	 * Returns caArrayClient instance
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static CaArrayClient getCaArrayClient(final HttpServletRequest request) throws Exception{
		CaArrayClient caArrayClient = (CaArrayClient)request.getSession().getAttribute(KEY_CAARRAY_CLIENT);
	/*	if (caArrayClient == null){
			String caArrayUserName = request.getParameter(PageContext.KEY_CAARRAY_USER_NAME);
			String caArrayPassword = request.getParameter(PageContext.KEY_CAARRAY_PASSWORD);
			
			if (caArrayUserName == null || caArrayUserName.equals(""))
				caArrayUserName = (String)request.getAttribute(PageContext.KEY_CAARRAY_USER_NAME);
			
			if (caArrayPassword == null || caArrayPassword.equals(""))
				caArrayPassword = (String)request.getAttribute(PageContext.KEY_CAARRAY_PASSWORD);
					
			caArrayClient = new CaArrayClient(caArrayUserName, caArrayPassword);			
		}
		setCaArrayClient(caArrayClient, request);*/
		return caArrayClient;
	}
	
	public static void setCaArrayClient(final CaArrayClient caArrayClient, final HttpServletRequest request){
	  HttpSession session = request.getSession();
	  session.setAttribute(KEY_CAARRAY_CLIENT, caArrayClient);
	}
	
	public static void setCaArrayExperimentList(Collection<Experiment> experimentList, final HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute(KEY_CAARRAY_EXP_LIST , experimentList);
	}
	
	public static Collection<Experiment> getCaArrayExperimentList(final HttpServletRequest request){
		HttpSession session = request.getSession();
		return (Collection<Experiment>)session.getAttribute(KEY_CAARRAY_EXP_LIST);
	}
	
	public static Experiment getCaArrayExperimentTitle(final String expPubId, final HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		Collection<Experiment> expList = getCaArrayExperimentList(request);
		if (expList == null)
			return null;
		for (Experiment exp : expList){
			if (exp.getPublicIdentifier().equals(expPubId)){
				return exp;
			}
		}
		return null;
	}
	
	public static String getCaArrayUserName(final HttpServletRequest request){
		return (String)request.getSession().getAttribute(KEY_CAARRAY_USER_NAME);
	}
	
	public static void setCaArrayUserName(final String caArrayUserName, final HttpServletRequest request){
	  HttpSession session = request.getSession();
	  session.setAttribute(KEY_CAARRAY_USER_NAME, caArrayUserName);
	}
	
	public static CaArrayClient getCaArrayPassword(final HttpServletRequest request){
		return (CaArrayClient)request.getSession().getAttribute(KEY_CAARRAY_PASSWORD);
	}
	
	public static void setCaArrayPassword(final String caArrayPassword, final HttpServletRequest request){
	  HttpSession session = request.getSession();
	  session.setAttribute(KEY_CAARRAY_PASSWORD, caArrayPassword);
	}
	
	
	public static void add2SelCaArrayExperiments(final HttpServletRequest request, final String expId){
		HttpSession session = request.getSession();
		Collection<String> selExperiments = (Collection<String>)session.getAttribute(KEY_CAARRAY_SEL_EXP_LIST);
		
		if (selExperiments == null)
			selExperiments = new ArrayList<String>();
		
		// add if does not exist already
		if (!isSelCaArrayExperimentProcessed(request, expId)){
			selExperiments.add(expId);			
		}
		session.setAttribute(KEY_CAARRAY_SEL_EXP_LIST, selExperiments);
	}
	
	public static boolean isSelCaArrayExperimentProcessed(final HttpServletRequest request, final String expId){
		HttpSession session = request.getSession();
		Collection<String> selExperiments = (Collection<String>)session.getAttribute(KEY_CAARRAY_SEL_EXP_LIST);
		
		if (selExperiments == null)
			return false;
		
		for (String eId : selExperiments){
			if (eId.equals(expId))
			  return true;
		}
				
		return false;
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
