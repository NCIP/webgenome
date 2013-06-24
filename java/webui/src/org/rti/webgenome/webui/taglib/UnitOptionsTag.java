/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/UnitOptionsTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/

package org.rti.webgenome.webui.taglib;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.rti.webgenome.units.BpUnits;

/**
 * Prints list of units as options for enclosing
 * <select> tag, or equivalent.
 */
public class UnitOptionsTag extends TagSupport {
    
    
    private String name = null;
    private String property = null;
    
	
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @param property The property to set.
     */
    public void setProperty(String property) {
        this.property = property;
    }
    
    
	/**
	 * @return Action to perform after processing
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
	    String value = null;
	    if (this.name != null) {
	        Object bean = this.pageContext.findAttribute(this.name);
	        if (property == null)
	            value = bean.toString();
            else
                try {
                    value = BeanUtils.getProperty(bean, property);
                } catch (Exception e) {
                    throw new JspException("Error evaluating UnitOptionsTag", e);
                }
	    }
		List<BpUnits> allUnits = BpUnits.getUnits();
		PrintWriter out = new PrintWriter(pageContext.getOut());
		for (BpUnits units : allUnits) {
			out.print("<option");
			if (value != null && units.getName().equals(value))
			    out.print(" selected");
			out.print(">" + units.getName() + "</option>");
		}
		out.flush();
		return SKIP_BODY;
	}

}
