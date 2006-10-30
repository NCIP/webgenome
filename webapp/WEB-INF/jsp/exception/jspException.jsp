<%@page isErrorPage="true" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>
<%@page import="org.rti.webcgh.util.Email" %>
<%@page import="org.rti.webcgh.util.SystemUtils" %>
<p><br></p>
<p align="center">
	<font color="red">
		<b>
			webCGH was unable to complete this request due to the
			failure of a system component.
		</b>
	</font>
</p>
<%--
  //
  //    D I S P L A Y    T H E    E R R O R    M E S S A G E
  //
  --%>
<center>
<webcgh:errorEmail exceptionMsg="<%= exception.getMessage() %>"/>
<div style="border:1px solid gray;">	
<h3>Error Log</h3>
<p>
	<%= exception.getMessage() %>
</p>
</center>
</div>