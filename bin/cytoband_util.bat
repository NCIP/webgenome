@set WEB-INF=%WEBCGH_HOME%\WEB-INF
@set LIB=%WEB-INF%\lib
@set CP=%WEB-INF%\classes
@for %%X in (%LIB%\*.*) do @call append_cp.bat %%X
@java -classpath %CP% org.rti.webcgh.etl.CytobandEtlManager %*