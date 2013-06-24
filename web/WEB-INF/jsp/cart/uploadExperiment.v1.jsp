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
     <tr>
	   <td class="contentTD">Organism:</td>
	   <td class="contentTD">
	      <html:select property="organismId" onchange="onOrganismChanged()">
            <html:options collection="organisms" property="value" labelProperty="label"/>
     	 </html:select>
       </td>
       <td class="contentTD">Other:</td>
	   <td class="contentTD"><html:text property="otherOrganism"/></td>
	 </tr>	 
     <tr>
	   <td class="contentTD">Genome Assembly:</td>
	   <td class="contentTD">
	      <html:select property="genomeAssemblyId" onchange="onAssemblyChanged()">
            <html:options collection="assemblies" property="value" labelProperty="label"/>
     	 </html:select>
       </td>
       <td class="contentTD">Other:</td>
	   <td class="contentTD"><html:text property="otherGenomeAssembly"/></td>
	 </tr>	 
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
    init();
    
  	function onOrganismChanged(){    
  	    var organism = document.forms[0].organismId;
  	    var ogrValue = organism.value;
  	    if (ogrValue == "Other"){  	    	
  	       document.forms[0].otherOrganism.disabled = false;  	       
  	       var assembly = document.forms[0].genomeAssemblyId;
  	       for (i = assembly.options.length-1;i >=0;i--){
  	         if ( i == 0 )
  	           assembly.options[i] = new Option("Other", "Other", false, null);
  	         else  
  	          assembly.options[i] = null;
  	       }
  	       document.forms[0].otherGenomeAssembly.disabled = false;  
  	       return;
  	    }   
  	    document.forms[0].otherOrganism.disabled = true;
     	document.forms[0].action = "/webGenome/cart/uploadExperiment.do";
     	document.forms[0].submit();        
   }
   
   	function onAssemblyChanged(){     	   
  	    var assembly = document.forms[0].genomeAssemblyId;  	    
  	    var assemblyValue = assembly.value;
  	    if ( assemblyValue == "Other" ){  	    	
  	       document.forms[0].otherGenomeAssembly.disabled = false;  	         	       
  	    }else{
  	       document.forms[0].otherGenomeAssembly.disabled = true; 
  	    }     	   
   }
   
   	function onQuantitationType(){     	   
  	    var qType = document.forms[0].quantitationType;  	    
  	    var qTypeValue = qType.value;
  	    if ( qTypeValue == "Other" ){  	    	
  	       document.forms[0].otherQuantitationType.disabled = false;  	         	       
  	    }else{
  	       document.forms[0].otherQuantitationType.disabled = true; 
  	    }     	   
   }
   
   function init(){    
  	    var organism = document.forms[0].organismId;  
  	    var ogrValue = organism.value;
  	    if (ogrValue != "Other"){  	    	
  	       document.forms[0].otherOrganism.disabled = true;
  	    } 
  	    onAssemblyChanged();   	 
   }
   
  </script>
  
</html:form>
</center>