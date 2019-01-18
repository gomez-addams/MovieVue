#Code Challenge

This challenge was interesting because it introduced me to both Vue.js and Kotlin with the IntelliJ IDE. I decided to develop these projects on Windows 10 64-bit instead of using Mac or Linux. I actually prefer the bash shell and the Unix environment over Windows command or power shells, but it's servicable.

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
