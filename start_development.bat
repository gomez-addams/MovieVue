@echo off
set SCRIPTDIR=%~dp0

start vue ui

call %SCRIPTDIR%\start_webserver.bat
