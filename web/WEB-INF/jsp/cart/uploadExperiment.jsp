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
<p><br></p>

<center>

<html:form action="/cart/uploadExperiment" method="POST" enctype="multipart/form-data">

  <table cellpadding="0" cellspacing="0" class="tbl"> 
      <tr><td>&nbsp;</td></tr>     
      <tr>
	   <td class="contentTD">Quantitation Type:</td>
	   <td class="contentTD">
	     <html:select property="quantitationType"  onchange="onQuantitationType()">
           <html:options collection="quantitationTypes" property="value" labelProperty="label"/>
     	 </html:select>
       </td>
       <td class="contentTD">Other:</td>	   
	   <td class="contentTD"><html:text property="otherQuantitationType"/></td>
	 </tr>	   
	  <tr>
	   <td class="contentTD" valign="top" align="right">SMD File:</td>
	   <td class="contentTD" colspan="3"><html:file property="experimentFile" />
         <div style="font-size: 75%; margin-top: 7px; margin-bottom: 0px; padding: 0 0 0 0;">
           (.txt files will be processed as tab-delimited data,</br>
            &nbsp;.csv files will be processed as comma-delimited data)
         </div>
	   </td>
	 </tr>
	 <tr><td>&nbsp;</td></tr>
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

 