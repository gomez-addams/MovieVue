@echo off
set SCRIPTDIR=%~dp0
cd %SCRIPTDIR%

call validate_vue.bat
if errorlevel 1 goto :EOF

@echo Starting the webapp via the Vue cli UI
vue serve --open

rem @echo Starting webapp via the Vue cli UI
rem start vue ui
