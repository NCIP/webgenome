<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="gov.nih.nci.caarray.domain.project.Experiment" %>
<%@ page import="org.rti.webgenome.service.client.SupportedArrayDesigns"%>




<h1 align="center">Select caArray experiment to import</h1>

<p align="center">
	<html:errors property="global"/>
</p>

<center>
	<p>
	  The list of experiments will be restricted to experiments with data and experiments that have the array design we support.<br>
	  To see the list of supported array designs please click <a href="javascript:showSuportedDesigns()"> here</a>.<br>
	  To optimize the performance and avoid retrieving the experiments list from caArray server 
	  the experiments list will be stored in the session. If you wish to refresh the list please click
	  <a href="<%=request.getContextPath()%>/upload/fetchcaArrayExperiments.do?refresh=true">here.</a>	  	   	  	  
	</p>
	<table align="center" border ="1">    
  	<logic:present name="key.caarray.exp.list">
  	  <tr>	      
    	 <td><b>Public Identifier</b></td><td><b>Title</b></td><td><b>Samples</b></td><td><b>Date</b></td>   	
       </tr>
     <logic:iterate name="key.caarray.exp.list" id="rowId" type="Experiment">
	   <tr>	      
    	 <td>
    	 <a href="<%=request.getContextPath()%>/upload/uploadcaArrayData.do?expId=<%=rowId.getPublicIdentifier()%>">    	 
    	 	<%=rowId.getPublicIdentifier() %>   	 
    	 </a>
    	</td>
    	
    	<td>
    	  <%=rowId.getTitle() %>
    	</td>
    	<td>
    	  <%=rowId.getSampleCount() %>
    	</td>
    	<td>
    	  <% if (rowId.getDate() == null){%>
    	    &nbsp&nbsp
    	  <%}else{%>
    	  	<%=rowId.getDate()%>
    	  <%}%>
    	</td>    	
       </tr>
      </logic:iterate>
 	</logic:present>
 	</table>
<form>
  <%     
     String supportedDesigns = SupportedArrayDesigns.getCaArraySupportedDesignsAsString(request);     
  %>   
  
   <input type="hidden" name="supportedDesigns" id="supportedDesigns"  value="<%=supportedDesigns%>"/>  
</form>	

</center>
<script language="JavaScript">
  function showSuportedDesigns( ) {  
    var supportedDesigns = document.getElementById("supportedDesigns"); 
	alert("Supported array designs are: \n\n" + supportedDesigns.value);
  }
  
 </script>
  
