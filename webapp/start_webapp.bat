@echo off
set SCRIPTDIR=%~dp0
cd %SCRIPTDIR%

rem A variant of the idea that requires more explanation for a demo.
rem call validate_vue.bat
rem if errorlevel 1 goto :EOF

rem @echo Starting webapp via the Vue cli UI
rem start vue ui

@echo Starting the pre-built webapp in the default system browser
start dist\index.html
