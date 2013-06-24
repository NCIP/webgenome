<%--L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>

<%@ page import="org.rti.webcgh.webui.util.Attribute" %>

<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<script language="JavaScript">

	var featurePrefix = "<%= Attribute.PRE_FEAT_TYPE %>";

	function cancel() {
		window.location = "<html:rewrite page="/plot/params.do"/>";
	}
		
	function prepareSubmission() {
		var featTypesStr = "";
		var form = document.plotParamsForm;
		var elements = form.elements;
		var count = 0;
		for (var i = 0; i < elements.length; i++) {
			var name = elements[i].name;
			var p = name.indexOf(featurePrefix);
			if (p >= 0) {
				if (elements[i].checked) {
					count++;
					var featName = name.substring(p + featurePrefix.length);
					if (count > 1)
						featTypesStr += ",";
					featTypesStr += featName;
				}
			}
		}
		var elmt = document.plotParamsForm.selectedFeatureTypes;
		elmt.value = featTypesStr;
		return true;
	}
	
</script>

<p><br></p>

<html:form action="/plot/params" onsubmit="prepareSubmission()">
	<p>    		
		<center>
	    	<webcgh:featureTypesSelector/>
	    	<html:hidden property="selectedFeatureTypes"/>
		</center>
	</p>
	
	<p>
		<center>
			<html:submit value="Update"/>
			<input type="button" value="Cancel" onclick="cancel()">
		</center>
	</p>
</html:form>