<template>
  <div id="app">
    <v-app id="app">
      <div id="nav">
        <!-- <v-footer class="pa-3" color="teal lighten-5"> <v-flex text-xs-center> -->
          <router-link to="/">Movie Search</router-link> |
          <router-link to="/about">About</router-link>
        <!-- </v-flex> </v-footer> -->
      </div>

      <router-view/>

      <v-footer class="pa-3">
        Gary Bringhurst
        <v-spacer />
        <v-avatar size=24 class="pr-3">
          <img
            src="https://avatars1.githubusercontent.com/u/17425260?s=460&v=4"
            alt="Gary"
          >
        </v-avatar>
        https://github.com/gomez-addams
        <v-spacer />
        <div>&copy; {{ new Date().getFullYear() }}</div>
      </v-footer>
    </v-app>
  </div>
</template>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }
</style>
