<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.webui.util.Attribute" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>


<p><br></p>

<center>
	<html:errors property="global"/>
</center>


<html:form action="/recordPlotParams">

			<table>
				<tr class="contentTD">
					<td rowspan="2">
						<html:img styleClass="pointer" page="/images/helpicon.gif" align="absmiddle" onclick="help('param-pipeline')"/>
					</td>
					<td width="150">Pipeline</td>
		    		<td>
		    			<html:select property="pipelineName">
							<html:options collection="pipelines" property="name" labelProperty="name"/>
		    			</html:select>
		    		</td>
					
		    	</tr>
		    </table>

<p align="center">
    <html:submit value="Save Changes"/>
</p>

<input type="hidden" name="update" value="yes">
 
</html:form>

