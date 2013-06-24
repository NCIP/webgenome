<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<p><br></p>
<center>
	<html:errors property="global"/>
</center>
<p><br></p>

<html:form action="/cart/mapProbes">

	<table align="center" border="0">
	<tr>
	<td>

	<p>
		<input type="checkbox" name="useGivenLocations">
		Use probe locations submitted with array data where available.
	</p>
	
	<logic:present name="assemblyMap">
		<logic:iterate id="element" name="assemblyMap">
			<p>
				<bean:write name="element" property="key.displayName"/>
				<select name="org-<bean:write name="element" property="key.id"/>">
					<option value="none">Select Assembly</option>
					<logic:iterate id="assembly" name="element" property="value">
						<option value="<bean:write name="assembly" property="id"/>">
							<bean:write name="assembly" property="name"/>
						</option>
					</logic:iterate>
				</select>
			</p>
		</logic:iterate>
	</logic:present>
	
	</td>
	</tr>
	</table>
	
	<p><br></p>
	<p><br></p>
	<center>
		<html:submit value="Next"/>
		<input type="button" value="Cancel" onclick="window.location='<html:rewrite page="/cart/contents.do"/>'">
	</center>
</html:form>