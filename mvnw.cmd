@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF)
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET PN=%__MVNW_ARG0_NAME__%
@SET PDIR=%~dp0

@SET MAVEN_PROJECTBASEDIR=%PDIR%
:findBaseDir
@IF NOT "%MAVEN_PROJECTBASEDIR%"=="" GOTO baseDirFound
@SET MAVEN_PROJECTBASEDIR=%CD%
:baseDirFound

@SET MAVEN_WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET MAVEN_WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties"

@SET DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip

@FOR /F "usebackq tokens=1,2 delims==" %%A IN (%MAVEN_WRAPPER_PROPERTIES%) DO (
    @IF "%%A"=="distributionUrl" SET DOWNLOAD_URL=%%B
)

@SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@SET DOWNLOAD_COMMAND="java -classpath %WRAPPER_JAR% %WRAPPER_LAUNCHER% %MAVEN_PROJECTBASEDIR% %*"

@java -classpath %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
