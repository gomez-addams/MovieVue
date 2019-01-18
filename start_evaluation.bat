@echo off
set SCRIPTDIR=%~dp0
rem cd %SCRIPTDIR%

start call "%SCRIPTDIR%"\webserver\start_webserver.bat
call "%SCRIPTDIR%"\webapp\start_webapp.bat
