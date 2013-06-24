@REM * -------------------------------------------------------
@REM * This script generates online help files.  The source
@REM * of help text is 'help.xml.'  This script generates
@REM * HTML versions of help text using an XSLT transformation.
@REM * This script requires an installed Java development kit
@REM * (JDK) of version 1.4 or higher.  This 'bin' directory
@REM * of the JDK must be in the PATH.
@REM * -------------------------------------------------------
@
@REM * -------------------------------------------------------
@REM * Help directory and files
@REM * -------------------------------------------------------
@
@set HELP_DIR=.\webapp\help
@set HELP_XML=%HELP_DIR%\help.xml
@set HELP_XSLT=%HELP_DIR%\help.xsl
@set HELP_HTM=%HELP_DIR%\help.htm
@
@REM * ------------------------------------------------------
@REM * Xalan (Java xslt program) configuration
@REM * ------------------------------------------------------
@
@set JAVA_LIB=.\webapp\WEB-INF\lib
@set XALAN_JAR=%JAVA_LIB%\xalan.jar
@set XALAN_COMMAND=org.apache.xalan.xslt.Process
@
@REM * ------------------------------------------------------
@REM * Perform XSLT transformation
@REM * ------------------------------------------------------
@
@echo Performing XSLT transformation
@java -classpath %XALAN_JAR% %XALAN_COMMAND% -IN %HELP_XML% -XSL %HELP_XSLT% -OUT %HELP_HTM%
@echo Done

