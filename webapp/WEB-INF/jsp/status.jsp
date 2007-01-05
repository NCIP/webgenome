<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="org.rti.webcgh.util.SystemUtils" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.mail.Session" %>
<%@ page import="javax.mail.Transport" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream,java.io.IOException" %>
<%@ page import="org.rosuda.JRclient.Rconnection" %>
<%!
boolean isEmpty ( String value ) {
    return value == null || value.length() < 1 ? true : false ;
}

String reportBuildProperty ( String label, String propertyKey , Properties properties ) {
	String returnValue = label + ": " ;
	if ( properties != null && properties.getProperty( propertyKey ) != null ) {
	    returnValue += properties.getProperty ( propertyKey ) ;
	}
	else
	    returnValue += "NOT AVAILABLE/NO VALUE DEFINED" ;
	return returnValue ;
}
%>
<h1 align="center">webGenome Status</h1>
<h2>Software Release Information</h2>
<pre>
<%
	String propertiesFile = "buildInformation.properties" ;
	String errorMessage = "Software Release Information not available.\n" +
					      "'" + propertiesFile + "' missing from CLASSPATH.\n" +
	   					  "Uusually this will only be available,\nif you are running this web application packaged as a War file." ;
	try {
		Properties buildProperties = new Properties() ;
		InputStream in = Thread.currentThread().
								getContextClassLoader().getResourceAsStream( propertiesFile );

		if ( in != null ) {
			buildProperties.load( in ) ;
			out.println ( reportBuildProperty ( "Current Build Release", "release.tag", buildProperties ) ) ;
			out.println ( reportBuildProperty ( "           Build Date", "build.date", buildProperties ) ) ;
			out.println ( reportBuildProperty ( "           Build Time", "build.time", buildProperties ) ) ;
			out.println ( reportBuildProperty ( "      Built on (host)", "build.host", buildProperties ) ) ;
			out.println ( reportBuildProperty ( "      Built By (user)", "build.user.name", buildProperties ) ) ;
		}
		else
		    out.println ( errorMessage ) ;
	}
	catch ( IOException e ) {
	    out.println ( errorMessage ) ;
	}
%>

</pre>
<h2>Memory Information</h2>
<pre>
<%

// Get current size of heap in bytes
long heapSize = Runtime.getRuntime().totalMemory();

// Get maximum size of heap in bytes. The heap cannot grow beyond this size.
// Any attempt will result in an OutOfMemoryException.
long heapMaxSize = Runtime.getRuntime().maxMemory();

// Get amount of free memory within the heap in bytes. This size will increase
// after garbage collection and decrease as new objects are created.
long heapFreeSize = Runtime.getRuntime().freeMemory();

float percentUsed = ((float) heapSize) / ((float) heapMaxSize) * (float) 100.00 ;

out.println ( "           Heap Size (bytes): " + heapSize ) ;
out.println ( "       Heap Max Size (bytes): " + heapMaxSize ) ;
out.println ( "    Free Heap Memory (bytes): " + heapFreeSize  ) ;
out.println ( "                 Memory Used: " +  percentUsed + " %" ) ;
%>
</pre>
<%-- DISABLED TEMPORARILY
<h2>SMTP Server Status:</h2>
<pre>
<%
String smtpHost = "not found" ;
try {
	smtpHost = SystemUtils.getApplicationProperty( "mail.smtp.host" ) ;
	Properties props = new Properties() ;
	props.put( "mail.smtp.host", smtpHost ) ;
	
	Session mailSession = Session.getDefaultInstance ( props, null ) ;
	Transport ts = mailSession.getTransport() ;
	ts.connect() ;
	out.println ( "    host=" + smtpHost + " Connected?: " + ts.isConnected() ) ;
}
catch ( Exception e ) {
	out.println ( "    Exception experienced trying to check whether SMTP Host [" + smtpHost +
	              "] is available." ) ;
	out.println ( "    Exception Message: " + e.getMessage() ) ;
}

%>
</pre>
--%>

<h2>RServe</h2>
<pre>
<%
String rserveHost = "not found" ;
String rservePort = "not found" ;
Rconnection c = null ;
try {
    rserveHost = SystemUtils.getApplicationProperty ( "rserve.ipAddress" ) ;
    rservePort = SystemUtils.getApplicationProperty ( "rserve.port" ) ;
    
    c = new Rconnection( rserveHost, Integer.parseInt(rservePort) ); 
    out.println ( "    RServe IP=" + rserveHost + " Connected?: " + c.isConnected() ) ;
}
catch ( Exception e ) {
	out.println ( "    Exception experienced trying to check whether RServe [" + rserveHost +
	              "] is connected." ) ;
	out.println ( "    Exception Message: " + e.getMessage() ) ;
}
finally {
    if ( c != null )
        try { c.close(); } catch ( Exception ignored ) {}
}
%>
</pre>

<h2>Database</h2>
<pre>
<%
Connection conn = null ;
Statement stmt = null ;
try {
    String dbUrl      = SystemUtils.getApplicationProperty ( "db.url" ) ;
    String dbUser     = SystemUtils.getApplicationProperty ( "db.user.name" ) ;
    String dbPassword = SystemUtils.getApplicationProperty ( "db.password" ) ;
    
    out.println ( "        DB URL: " + dbUrl ) ;
	if ( isEmpty ( dbUrl ) )
	    out.println ( "  DB User Name: not specified in Properties Settings" ) ;
	if ( isEmpty ( dbPassword ) )
	    out.println ( "   DB Password: not specified in Properties Settings" ) ;
    
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    conn = DriverManager.getConnection( dbUrl, dbUser, dbPassword );
    stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery( "SELECT COUNT(*) AS rowCount FROM cytoband" ) ;
	rs.next() ;
    int rowCount = rs.getInt( "rowCount" ) ;
	out.println ( "    Test table: CYTOBAND table has " + rowCount + " rows" ) ;
	out.print ( "     DB Access: " ) ;
	if ( rowCount > 0 )
	    out.println ( "Success!" ) ;
	else
	    out.println ( "Failed!" ) ; 
}
catch ( Exception e ) {
    out.println ( "    Exception experienced looking up database table." ) ;
    out.println ( "    Exception Message: " + e.getMessage() ) ;
}
finally {
    if ( stmt != null )
        try { stmt.close(); } catch ( Exception ignored ) {}
    if ( conn != null )
        try { conn.close(); } catch ( Exception ignored ) {}
}
%>
</pre>