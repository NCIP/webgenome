<%@page isErrorPage="true" %>
<%@page import="org.rti.webcgh.util.Email" %>
<%@page import="org.rti.webcgh.util.SystemUtils" %>
<%@ taglib uri="/WEB-INF/webcgh.tld" prefix="webcgh" %>
<p><br></p>
<p align="center">
	<font color="red">
		<b>
			webCGH was unable to complete this request due to the
			failure of a system component.
		</b>
	</font>
</p>
<%
	//
	//    E M A I L    T H E    E R R O R    M E S S A G E
	//
	String subject =
	    	SystemUtils.getApplicationProperty( "error.page.email.subject" ) ;
	String to = 
	    	SystemUtils.getApplicationProperty ( "error.page.email.to" ) ;
	String message =
	    	"The following Exception was caught by webGenome:\n\n" +
			exception.getMessage() + "\n" ;
	
	Email email = new Email() ;
	boolean emailed = email.send ( to, subject, message ) ;
	if ( emailed )
	{	    
%>
	<p>The error below was emailed to the webCGH Technical Team who will investigate
	the cause of and rectify the problem.</p>
<%  } else { %>
	<p>The error was unable to be sent to the webCGH Technical Team!</p>
	<p>Please help us to rectify the problem by cutting and pasting the error message
	below and emailing it to <a href="mailto:<%= to %>"><%= to %></a>.</p>
<%  } %>
<%--
  //
  //    D I S P L A Y    T H E    E R R O R    M E S S A G E
  //
  --%>	
<center>
<div style="border:1px solid gray;">	
<h3>Error Log</h3>
<p>
	<%= exception.getMessage() %>
</p>
</center>
</div>