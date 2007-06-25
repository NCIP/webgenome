/*
$Revision: 1.2 $
$Date: 2007-06-25 18:41:54 $

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

package org.rti.webgenome.job;

import java.util.Date;


/**
 * This interface represents a compute job that
 * can be placed on a queue and run in the background.
 * @author dhall
 *
 */
public interface Job {
	
	/**
	 * Get unique identifier for job.
	 * @return Identifier
	 */
	Long getId();
	
	/**
	 * Set unique identifier for job.
	 * @param id Unique identifier
	 */
	void setId(Long id);

	
	/**
	 * Get user identifier (i.e., user name).
	 * @return User identifier
	 */
	String getUserId();
	
	/**
	 * Set user identifier (i.e., user name).
	 * @param userId User identifier
	 */
	void setUserId(String userId);
	
	/**
	 * Get date/time that the job was instantiated.
	 * @return Date/time that job was instantiated
	 */
	Date getInstantiationDate();
	
	/**
	 * Set date/time that job was instantiated.
	 * @param date Date/time job was instantiated.
	 */
	void setInstantiationDate(Date date);

	
	/**
	 * Get date/time that job was started.
	 * @return Date/time that job was started.
	 */
	Date getStartDate();
	
	/**
	 * Set date/time that job was started.
	 * @param date Date/time job was started.
	 */
	void setStartDate(Date date);
	
	/**
	 * Get date/time that job ended.
	 * @return Date/time that job ended.
	 */
	Date getEndDate();
	
	/**
	 * Set date/time that job ended.
	 * @param date Date/time that job ended.
	 */
	void setEndDate(Date date);
	
	/**
	 * Set the message that describes the
	 * state of the job upon termination
	 * if it finished successfullly or threw
	 * and exception.
	 * @param message A message
	 */
	void setTerminationMessage(String message);
	
	
	/**
	 * Get the message that describes the
	 * state of the job upon termination
	 * if it finished successfullly or threw
	 * and exception.
	 * @return A message
	 */
	String getTerminationMessage();
	
	/**
	 * Set ID of shopping cart.
	 * @param id Shopping cart ID
	 */
	void setShoppingCartId(Long id);
	
	/**
	 * Get ID of shopping cart.
	 * @return ID of shopping cart
	 */
	Long getShoppingCartId();
	
	
	/**
	 * Execute job.
	 */
	void execute();
}
