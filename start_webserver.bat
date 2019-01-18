@echo off
set SCRIPTDIR=%~dp0

cd %SCRIPTDIR%
cd webserver
gradlew run
