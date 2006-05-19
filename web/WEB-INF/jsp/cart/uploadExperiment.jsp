<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<p><br></p>
<p><br></p>

<center>

<html:form action="/cart/uploadExperiment" method="POST" enctype="multipart/form-data">

<table cellpadding="0" cellspacing="0" class="tbl">    
     <tr>
	   <td>Organism:</td>
	   <td>
	      <html:select property="organismId" onchange="onOrganismChanged()">
            <html:options collection="organisms" property="value" labelProperty="label"/>
     	 </html:select>
       </td>
	   <td><html:text property="otherOrganism"/></td>
	 </tr>	 
     <tr>
	   <td>Genome Assembly:</td>
	   <td>
	      <html:select property="genomeAssemblyId">
            <html:options collection="assemblies" property="value" labelProperty="label"/>
     	 </html:select>
       </td>
	   <td><html:text property="otherGenomeAssembly"/></td>
	 </tr>	 
     <tr>
	   <td>QuantitationType:</td>
	   <td>
	      <html:select property="quantitationType">
            <html:options collection="quantitationTypes" property="value" labelProperty="label"/>
     	 </html:select>
       </td>	   
	   <td><html:text property="quantitationTypeId"/></td>
	   <td><html:text property="otherQuantitationType"/></td>
	 </tr>	 
	  <tr>
	   <td colspan="3">Study Design: <html:file property="experimentFile" /></td>
	 </tr>
	 <tr>
	    <td colspan="3" align="center">
	   	 <html:submit property="methodToCall"><bean:message key="button.upload"/></html:submit>
	   </td>	    
	 </tr>
  </table>
  
  <html:hidden property="methodToCall" value="onOrganismChanged" styleId="method" />
  
  <script language="JavaScript">
  	function onOrganismChanged(){    
     	document.forms[0].action = "/cart/upload";
     	document.forms[0].submit();        
   }
  </script>
  
</html:form>
</center>