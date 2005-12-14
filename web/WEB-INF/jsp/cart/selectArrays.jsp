<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="org.rti.webcgh.array.Experiment" %>
<%@ page import="org.rti.webcgh.array.BioAssay" %>
<%@ page import="org.rti.webcgh.webui.util.DataIdEncoder" %>

<script language="Javascript">
	
	var EXP_ID_TAG = "<%= DataIdEncoder.EXP_ID_TAG %>";
	var BIO_ASSAY_ID_TAG = "<%= DataIdEncoder.BIO_ASSAY_ID_TAG %>";
	
	function decodeExperimentId(encoding) {
		var p = encoding.indexOf(EXP_ID_TAG) + EXP_ID_TAG.length;
		var q = encoding.indexOf(BIO_ASSAY_ID_TAG);
		return encoding.substring(p, q);
	}
		
	function synchronize(encoding) {
		var expId = decodeExperimentId(encoding);
		var form = document.forms[0];
		var elements = form.elements;
		var value = null;
		for (var i = 0; i < elements.length; i++) {
			var element = elements[i];
			var name = element.name;
			if (name.indexOf(encoding) == 0)
				value = element.value;
			else {
				var id = decodeExperimentId(name);
				if (id == expId)
					element.value = value;
			}
		}
	}
	
</script>


<%
String[] style = {"contentTD", "contentTD-odd"};
int styleIdx = 0;
%>


<p><br></p>
<center><html:errors property="global"/></center>
<p><br></p>

<center>

<html:form action="/cart/recordArrays">

	<table cellpadding="0" cellspacing="0" class="tbl">
	
		<!-- Table header -->
		<tr class="dataHeadTD">
    		<td><b>Experiment ID</b></td>
    		<td><b>Bioassays</b></td>
    	</tr>

		<!-- Table body -->
		<logic:iterate id="experiment" name="experiments">
		<%
			Experiment exp = (Experiment)pageContext.findAttribute("experiment");
			String dbName = exp.getDatabaseName();
			String expName = exp.getName();
			String expKey = DataIdEncoder.encode(dbName, expName, null);
		%>
		
			<!-- Experiment -->
       		<tr class="<%=style[styleIdx++ % 2]%>">
        		<td>
        			<select name="<%= expKey %>"
        				onchange="synchronize('<%= expKey %>')">
        				<option value="none">Select probe set</option>
        				<logic:iterate id="arrayMapping" name="arrayMappings">
        					<option value="<bean:write name="arrayMapping" property="id"/>">
        						<bean:write name="arrayMapping" property="array.name"/>&nbsp;
        						<bean:write name="arrayMapping" property="array.organism.displayName"/>
        					</option>
        				</logic:iterate>
        			</select>
        			<bean:write name="experiment" property="name"/>
        		</td>
        	
        		<!-- Bioassays -->
        		<td>
        			<logic:iterate id="bioassay" name="experiment" property="bioAssays">
        				<%
        					BioAssay ba = (BioAssay)pageContext.findAttribute("bioassay");
        					String bioAssayKey = DataIdEncoder.encode(dbName, expName, ba.getName());
        				%>
	        			<bean:write name="bioassay" property="name"/>
	        			<select name="<%= bioAssayKey %>">
	        				<option value="none">Select probe set</option>
	        				<logic:iterate id="arrayMapping" name="arrayMappings">
        						<option value="<bean:write name="arrayMapping" property="id"/>">
        							<bean:write name="arrayMapping" property="array.name"/>&nbsp;
        						<bean:write name="arrayMapping" property="array.organism.displayName"/>
        						</option>
        					</logic:iterate>
						</select>
						<br>
        			</logic:iterate>
        		</td>
        	</tr>
        </logic:iterate>
    </table><p>
    
	<html:submit value="Next"/>
	<input type="button" value="Cancel" onclick="window.location='<html:rewrite page="/cart/contents.do"/>'">
</html:form>

</center>