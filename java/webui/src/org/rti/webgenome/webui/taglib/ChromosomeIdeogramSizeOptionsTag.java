/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/ChromosomeIdeogramSizeOptionsTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/

package org.rti.webgenome.webui.taglib;

import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.rti.webgenome.units.ChromosomeIdeogramSize;

/**
 * Prints list of chromosome ideogram sizes as options for enclosing
 * <select> tag, or equivalent.
 */
public class ChromosomeIdeogramSizeOptionsTag extends TagSupport {
    
    
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
                    throw new JspException("Error evaluating ChromosomeIdeogramSizeOptionsTag", e);
                }
	    }
		ChromosomeIdeogramSize[] sizes = ChromosomeIdeogramSize.chromosomeIdeogramSizes();
		PrintWriter out = new PrintWriter(pageContext.getOut());
		for (int i = 0; i < sizes.length; i++) {
			ChromosomeIdeogramSize size = sizes[i];
			out.print("<option");
			if (value != null && size.getName().equals(value))
			    out.print(" selected");
			out.print(">" + size.getName() + "</option>");
		}
		out.flush();
		return SKIP_BODY;
	}

}
