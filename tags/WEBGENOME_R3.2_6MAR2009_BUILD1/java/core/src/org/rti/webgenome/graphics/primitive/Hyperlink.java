/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/

package org.rti.webgenome.graphics.primitive;

import java.net.URL;

/**
 * Hyperlink properties.
 */
public class Hyperlink {
	
	/** URL. */
	private URL url = null;
	
	/** Target browser window name. */
	private String targetWindow = null;
	
	
	/**
	 * Get target browser window name.
	 * @return Returns the target browser window name
	 */
	public final String getTargetWindow() {
		return targetWindow;
	}
	/**
	 * Set the target browser window name.
	 * @param targetWindow The target browser window name
	 */
	public final void setTargetWindow(final String targetWindow) {
		this.targetWindow = targetWindow;
	}
	/**
	 * Get URL.
	 * @return Returns the url.
	 */
	public final URL getUrl() {
		return url;
	}
	/**
	 * Set URL.
	 * @param url The URL
	 */
	public final void setUrl(final URL url) {
		this.url = url;
	}
	
	
	/**
	 * Constructor.
	 *
	 */
	public Hyperlink() {
		
	}
	
	
	/**
	 * Constructor.
	 * @param url URL
	 */
	public Hyperlink(final URL url) {
		this.url = url;
	}
	
	
	/**
	 * Constructor.
	 * @param url URL
	 * @param targetWindow Target window name
	 */
	public Hyperlink(final URL url, final String targetWindow) {
		this(url);
		this.targetWindow = targetWindow;
	}

}
