/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/webui/taglib/ChromosomeIdeogramSizeOptionsTag.java,v $
$Revision: 1.3 $
$Date: 2006-09-07 15:15:31 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

package org.rti.webcgh.webui.taglib;

import java.io.PrintWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.rti.webcgh.units.ChromosomeIdeogramSize;

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
