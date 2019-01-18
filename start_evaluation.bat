@echo off
set SCRIPTDIR=%~dp0

call %SCRIPTDIR%\webserver\start_webserver.bat
call %SCRIPTDIR%\webapp\start_webapp.bat
