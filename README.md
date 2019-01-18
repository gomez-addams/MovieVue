#Code Challenge

I decided to develop this project on Windows 10 64-bit instead of using Mac or Linux. I actually prefer the bash shell and the Unix environment over the Windows command line, but it's servicable.

I have included Windows batch scripts to automatically run the webserver and webapp deliverables. The webserver requires that you have Java installed with the appropriate PATH entries at the system level. Some additional requirements will be checked for and installed if necessary. Avoid using these scripts if you prefer to avoid installation side effects and manually run things.

To test the project, run the following script from this directory:
* start_evaluation.bat

The webapp is pre-built for convenience, but the webserver may auto-install Kotlin and other requirements before building and running. There is no pre-built binary supplied for the webserver.

This challenge was interesting to me because it was a chance to use both Vue.js with VS Code and Kotlin with the IntelliJ IDE. My most recent daytime work has primarily involved the following development environments and languages, in this order:
* Windows Visual Studio and UWP/Win32 with C++/C#
* Unity 3D on Windows, Mac OS X, iOS and Android with C#
* Microsoft Azure functions and data services with C#
* Mac OS X and Xcode with C++/Objective-C/Swift


#Front-End: webapp

The web app has been built with Visual Studio Code and NPM using Vue cli version 3 and a fairly standard configuration. I stuck with vanilla JavaScript (ignoring the lure of TypeScript). I added the following plugins to the Vue project:
* Axios: for easy async communication with the tmdb.org server
* Jest: for unit testing (with a single test case)
* Vuetify: for easy UI creation (then ignored most of it so I could keep this thing simple)


#Back-End: webserver

The web server has been built with the IntelliJ IDE and Gradle. I added the following to the IntelliJ project:
* Gson: for JSON support (the native support seems a bit weak)
* Ktor: for easy basic server support
* JUnit: for testing (the unit tests are pretty unsophisticated but there are several of them)
