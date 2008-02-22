/*
$Revision: 1.5 $
$Date: 2008-02-22 03:54:09 $

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

package org.rti.webgenome.webui.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.Action;
import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.service.session.WebGenomeDbService;
import org.rti.webgenome.webui.SessionTimeoutException;
import org.rti.webgenome.webui.util.PageContext;


/**
 * Abstract base class for webGenome Struts actions.
 * @author dhall
 *
 */
public abstract class BaseAction extends Action {
	
	/** Data access object for shopping carts. */
	private ShoppingCartDao shoppingCartDao = null;
	
	/** Service facade for transactional database operations. */
	private WebGenomeDbService dbService = null;


	/**
	 * Set shopping cart data access object.  This method
	 * will be used primarily for dependency injection.
	 * @param shoppingCartDao Shopping cart data access object.
	 */
	public void setShoppingCartDao(final ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	
	
	/**
	 * Inject service for transactional database operations.
	 * @param dbService Service for transactional database operations
	 */
	public void setDbService(final WebGenomeDbService dbService) {
		this.dbService = dbService;
	}

	/**
	 * Get service for transactional database operations.
	 * @return Service for transactional database operations
	 */
	protected WebGenomeDbService getDbService() {
		return this.dbService;
	}


	/**
	 * Get shopping cart.  If the session mode is
	 * {@code CLIENT}, the cart will be cached in the
	 * session object.  If the mode is {@code STAND_ALONE},
	 * the cart will be obtained from persistent storage.
	 * @param request Servlet request
	 * @return Shopping cart
	 * @throws SessionTimeoutException If any necessary
	 * session attributes are not found, which indicates the
	 * session has timed ouit.
	 */
	protected ShoppingCart getShoppingCart(
			final HttpServletRequest request)
	throws SessionTimeoutException {
		ShoppingCart cart = null;
		SessionMode mode = PageContext.getSessionMode(request);
		if (mode == SessionMode.CLIENT) {
			cart = PageContext.getShoppingCart(request);
		} else if (mode == SessionMode.STAND_ALONE) {
			Principal principal = PageContext.getPrincipal(request);
			cart = this.shoppingCartDao.load(principal.getName(),
					principal.getDomain());
		}
		return cart;
	}
	
	/**
	 * Persist changes made to given shopping cart.  If the
	 * session mode is CLIENT, no action will actually be
	 * performed.
	 * @param cart Shopping cart
	 * @param request Servlet request
	 * @throws SessionTimeoutException If the method cannot
	 * determine the session mode, which would occur if
	 * for some reason the session timed out.
	 */
	protected void persistShoppingCartChanges(final ShoppingCart cart,
			final HttpServletRequest request)
	throws SessionTimeoutException {
		if (PageContext.getSessionMode(request) == SessionMode.STAND_ALONE) {
			this.shoppingCartDao.update(cart);
		}
	}
	
	
	/**
	 * Are data in memory for this session?
	 * @param request A request
	 * @return T/F
	 * @throws SessionTimeoutException If the method cannot
	 * determine the session mode, which would occur if
	 * for some reason the session timed out.
	 */
	protected boolean dataInMemory(final HttpServletRequest request)
	throws SessionTimeoutException {
		return PageContext.getSessionMode(request) == SessionMode.CLIENT;
	}
}
