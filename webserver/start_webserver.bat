@echo off
set SCRIPTDIR=%~dp0
cd %SCRIPTDIR%

@echo Starting the webserver on port 8081
gradlew --continue run
