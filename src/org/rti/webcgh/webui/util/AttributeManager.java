/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/util/AttributeManager.java,v $
$Revision: 1.2 $
$Date: 2006-09-05 14:06:45 $

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
package org.rti.webcgh.webui.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.array.ReporterMappingStagingArea;
import org.rti.webcgh.array.ShoppingCart;
import org.rti.webcgh.service.authentication.UserProfile;

/**
 * Manages request and session attributes
 */
public class AttributeManager {
    
    
    // =====================================================
    //           Static variables
    // =====================================================
    
    /**
     * User profile
     */
    public static final String USER_PROFILE = "userProfile";
    
    private static final String SHOPPING_CART = "shoppingCart";
    private static final String REPORTER_MAPPING_STAGING_AREA = "reporterMappingStagingArea";
    private static final String GENOME_INTERVAL_DTOS = "genomeIntervalDtos";
    
    
    /**
     * Set user profile bean
     * @param request A request
     * @param userProfile A user profile
     */
    public static void setUserProfile(HttpServletRequest request, UserProfile userProfile) {
        if (userProfile != null) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_PROFILE, userProfile);
        }
    }
    
    
    /**
     * Get user profile bean
     * @param request A request
     * @return User profile bean
     */
    public static UserProfile getUserProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserProfile)session.getAttribute(USER_PROFILE);
    }
    
    
    /**
     * Remove user profile from session
     * @param request Request
     */
    public static void removeUserProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_PROFILE);
    }
    
    
    /**
     * Get shopping cart.  If not found, instantiate a new one
     * and attach to session
     * @param request A request
     * @return A shopping cart
     */
    public static ShoppingCart getShoppingCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart)session.getAttribute(SHOPPING_CART);
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(SHOPPING_CART, cart);
        }
        return cart;
    }
    
    
    /**
     * Set the probe mapping staging area
     * @param request Request
     * @param area Probe mapping staging area
     */
    public static void setProbeMappingStagingArea(HttpServletRequest request, ReporterMappingStagingArea area) {
        HttpSession session = request.getSession();
        session.setAttribute(REPORTER_MAPPING_STAGING_AREA, area);
    }
    
    
    /**
     * Get the probe mapping staging area
     * @param request Request
     * @return Probe mapping staging area
     */
    public static ReporterMappingStagingArea getProbeMappingStagingArea(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (ReporterMappingStagingArea)session.getAttribute(REPORTER_MAPPING_STAGING_AREA);
    }
    
    
    /**
     * Get genome interval DTOs
     * @param request A request
     * @return Genome interval DTOs
     */
    public static GenomeIntervalDto[] getGenomeIntervalDtos(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	return (GenomeIntervalDto[])session.getAttribute(GENOME_INTERVAL_DTOS);
    }
    
    
    /**
     * Set genome interval DTOs
     * @param request A request
     * @param dtos Genome interval DTOs
     */
    public static void setGenomeIntervalDtos(HttpServletRequest request, GenomeIntervalDto[] dtos) {
    	HttpSession session = request.getSession();
    	session.setAttribute(GENOME_INTERVAL_DTOS, dtos);
    }
}
