#Code Challenge

I have included Windows batch scripts to automatically run the webserver and webapp deliverables. These require that you have both NPM and Java installed with the appropriate PATH entries at the system level. Some additional requirements will be checked for and installed if necessary. Avoid using these scripts and manually execute the deliverables if you prefer to avoid installation side effects.

Run start_evaluation.bat from the root directory of this project.

This challenge was interesting to me because it gave me a chance to use both Vue.js and Kotlin with the IntelliJ IDE. I decided to develop these projects on Windows 10 64-bit instead of using Mac or Linux. I actually prefer the bash shell and the Unix environment over the Windows command line, but it's servicable.

My most recent work involved the following development environments and languages, in this order:
* Windows Visual Studio and UWP/Win32 with C++/C#
* Unity 3D on Windows, Mac OS X, iOS and Android with C#
* Microsoft Azure functions and data services with C#
* Mac OS X and Xcode with C++/Objective-C/Swift

#Front-End: webapp

The web app has been built with Visual Studio Code and NPM using Vue cli version 3 and a fairly standard configuration. I stuck with vanilla JavaScript (ignoring the lure of TypeScript). I added the following plugins to the Vue project:

* Axios: for easy async communication with the tmdb.org server
* Jest: for unit testing (with a single test case)
* Vuetify: for easy UI creation (then ignored most of it so I could keep this thing simple)

Run the front end

#Back-End: webserver

The web server has been built with the IntelliJ IDE and gradle. I added the following to the IntelliJ project:

* Gson: for JSON support (the native support seems a bit weak)
* Ktor: for easy basic server support
* JUnit: for testing (the unit tests are pretty unsophisticated but there are several of them)

Run the back end
