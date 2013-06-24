<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<p><br></p>

<center>
	<html:form action="/annotationReport">
		<webcgh:beanPropsToHiddenFields name="plotParamsForm" exclusions="chromosome,startPoint,endPoint"/>
		<table cellpadding="3" cellspacing="3" border="0">
			<tr>
    			<td>
        			<html:submit value="Go"/>
    			</td>
    			<td>
    				Chromosome: <html:text size="2" maxlength="2" property="chromosome"/>
    			</td>
        		<td>
    				Start:
    				<html:text property="startPoint" size="6"
    						onblur="setSelectedRegion()"/>
    				<html:select property="startUnits">
   						<html:option value="mb"/>
   						<html:option value="kb"/>
   						<html:option value="bp"/>
   					</html:select>
   				</td>
   				<td>
   					End:
   					<html:text property="endPoint" size="6" property="endPoint" size="6"
    					onblur="setSelectedRegion()"/>
    				<html:select property="endUnits">
    					<html:option value="mb"/>
    					<html:option value="kb"/>
   						<html:option value="bp"/>
   					</html:select>
   				</td>
        		<td>
            		<html:submit value="Go"/>
        		</td>
    		</tr>
		</table>
	</html:form>
</center>
	

<logic:iterate name="annotationMap" property="tracks" id="track">
	<h3 align="center"><bean:write name="track" property="label"/></h3>
	<h5 align="center"><a href="<%= request.getContextPath()%>/fileDownload.do?download_type=annotation&annotation_type=<bean:write name="track" property="label"/>">Download Report</a></h5>
	<p>
		<table border="0" cellpadding="0" cellspacing="0" class="tbl" align="center">
			<tr class="dataHeadTD">
				<td>Feature Name</td>
				<td>Chromosome Start</td>
				<td>Chromosome End</td>
				<td colspan="2" align="center">Additional Information</td>
			</tr>
			
			<%
				int idx = 0;
				String[] styles = {"contentTD", "contentTD-odd"};
			%>
			<logic:iterate name="track" property="features" id="feat">
				<tr class="<%= styles[idx++ %2] %>">
					<td><bean:write name="feat" property="label"/></td>
					<td><bean:write name="feat" property="start"/></td>
					<td><bean:write name="feat" property="end"/></td>
					<td><a href="<bean:write name="feat" property="link"/>">UCSC</a></td>
					<td><a href="http://cgap.nci.nih.gov/Genes/RunUniGeneQuery?PAGE=1&ORG=Hs&TERM=<bean:write name="feat" property="label"/>">
							CGAP
						</a>
					</td>
				</tr>
			</logic:iterate>
		</table>
	</p>
</logic:iterate>