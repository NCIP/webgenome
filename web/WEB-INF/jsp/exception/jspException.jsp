<%@page isErrorPage="true" %>

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
		
<p align="center">
	Please help us to fix this problem by
</p>
	<table align="center">
		<tr>
			<td>
				<ul>
					<li>Selecting the error log below
					<li>Copying and pasting the selection into an email
					message to <webcgh:sysadmin/>
				</ul>
			</td>
		</tr>
	</table>
		
<p><br></p>
<p><br></p>
		

<center>	
<h3>Error Log</h3>
		
<p>
	<%= exception.getMessage() %>
</p>
</center>