echo Using WEBCGH_HOME and HELP_DIR environment variables
@set WEB-INF=%WEBCGH_HOME%\WEB-INF
@set LIB=%WEB-INF%\lib
@set CP=%WEB-INF%\classes
@for %%X in (%LIB%\*.*) do @call append_cp.bat %%X
@set XML_FILE=%HELP_DIR%\help.xml
@set HTML_FILE=%HELP_DIR%\help.htm
@set XSL_FILE=%HELP_DIR%\help.xsl
@java -classpath %CP% org.apache.xalan.xslt.Process -in %XML_FILE% -xsl %XSL_FILE% -out %HTML_FILE%