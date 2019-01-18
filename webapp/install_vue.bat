@echo off
set SCRIPTDIR=%~dp0
cd %SCRIPTDIR%

@echo Checking prerequisites and installing as necessary...
rem npm install -g @vue/cli
npm install -g @vue/cli-service-global
