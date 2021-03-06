/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/webui/src/org/rti/webgenome/webui/taglib/PipelineBodyTag.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $



*/
package org.rti.webgenome.webui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.rti.webgenome.webui.util.AnalyticOperationUIHelper;

/**
 * Base class for analytic pipeline tags that have bodies
 */
public class PipelineBodyTag extends BodyTagSupport {
    
    protected AnalyticOperationUIHelper analyticOperationUIHelper = null;
    private static final Logger LOGGER = Logger.getLogger(PipelineTag.class);
    

    /**
     * Start tag
     * @throws JspException if something goes wrong
     * @return Return condition
     */
    public int doStartTag() throws JspException {
        this.analyticOperationUIHelper = (AnalyticOperationUIHelper)
        	this.pageContext.findAttribute("analyticOperationUIHelper");
        if (this.analyticOperationUIHelper == null) {
            String msg = "Cannot find attribute 'analyticOperationUIHelper' in any scope";
            LOGGER.error(msg);
            throw new JspException(msg);
        }
        return 0;
    }

}
