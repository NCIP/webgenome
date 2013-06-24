/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/TagUtils.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/

package org.rti.webgenome.webui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

/**
 * Utility methods for custom tags
 */
public class TagUtils {
    
    private static final Logger LOGGER = Logger.getLogger(TagUtils.class);
	
	/**
	 * Get parent of given tag
	 * @param tag A tag
	 * @param klass Class of parent
	 * @return Parent tag
	 * @throws JspException
	 */
	public static Tag getParentTag
	(
		Tag tag, Class klass
	) throws JspException {
		Tag parent = tag.getParent();
		if (parent == null || ! (parent.getClass().equals(klass))) {
		    String msg = "Must be nested within '" + klass.getName() + "' tags";
		    LOGGER.error(msg);
			throw new JspException(msg);
		}
		return parent;
	}
	
	
	/**
	 * Get immediate ancestor of given tag with given class.
	 * @param tag A tag
	 * @param klass Ancestor class
	 * @return Ancestor tag
	 * @throws JspException
	 */
	public static Tag getAncestorTag
	(
		Tag tag, Class klass
	) throws JspException {
		Tag ancestor = null;
		Tag temp = tag;
		while (ancestor == null) {
			temp = temp.getParent();
			if (temp == null)
				break;
			if (temp.getClass().equals(klass))
				ancestor = temp;
		}
		if (ancestor == null) {
		    String msg = "Must be nested within '" + klass.getName() + "' tags";
		    LOGGER.error(msg);
			throw new JspException(msg);
		}
		return ancestor;
	}

}
