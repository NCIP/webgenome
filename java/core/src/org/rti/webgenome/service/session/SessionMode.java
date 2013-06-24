/*
$Revision: 1.1 $
$Date: 2007-04-09 22:19:49 $


*/

package org.rti.webgenome.service.session;

/**
 * Mode of user session, which is either stand-alone
 * or plotting client.
 * @author dhall
 *
 */
public enum SessionMode {
	
	/**
	 * Indicates the user is using the system stand-alone.
	 * In other words, they went directly to webGenome
	 * and uploaded data.
	 */
	STAND_ALONE,
	
	/**
	 * Indicates the users is using webGenome as a plotting
	 * client for another application.
	 */
	CLIENT;

}
