@echo off
set SCRIPTDIR=%~dp0
cd %SCRIPTDIR%

call install_vue.bat
if errorlevel 0 goto :EOF

@echo NPM, installed with NodeJS, is required for this task.
@echo Please install NPM from https://nodejs.org/en/
