<%@ page errorPage="/WEB-INF/jsp/exception/jspException.jsp" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<p><br></p>
<p><br></p>

<center>

<html:form action="/cart/uploadExperiment" method="POST" enctype="multipart/form-data">

  <table cellpadding="0" cellspacing="0" class="tbl"> 
      <tr><td>&nbsp&nbsp</td></tr>     
      <tr>
	   <td class="contentTD">QuantitationType:</td>
	   <td class="contentTD">
	      <html:select property="quantitationType"  onchange="onQuantitationType()">
            <html:options collection="quantitationTypes" property="value" labelProperty="label"/>
     	 </html:select>
       </td>
       <td class="contentTD">Other:</td>	   
	   <td class="contentTD"><html:text property="otherQuantitationType"/></td>
	 </tr>	   
	  <tr>
	   <td  class="contentTD" colspan="4">SMD File:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	   &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<html:file property="experimentFile" /></td>
	 </tr>
	 <tr><td>&nbsp&nbsp</td></tr>
	 <tr>
	    <td colspan="4" align="center">
	   	 <html:submit property="methodToCall"><bean:message key="button.upload"/></html:submit>
	   </td>	    
	 </tr>
	 <tr><td>&nbsp&nbsp</td></tr>
  </table>
  
  <html:hidden property="methodToCall" value="onOrganismChanged" styleId="method" />
  
 <script language="JavaScript">
	function onQuantitationType(){     	   
  	    var qType = document.forms[0].quantitationType;  	    
  	    var qTypeValue = qType.value;
  	    if ( qTypeValue == "Other" ){  	    	
  	       document.forms[0].otherQuantitationType.disabled = false;  	         	       
  	    }else{
  	       document.forms[0].otherQuantitationType.disabled = true; 
  	    }     	   
   }
  </script>
    
</html:form>
</center>

 