<template>
  <v-content>
    <v-layout row mb-3>
      <v-flex xs12 sm8 offset-sm2>
        <v-toolbar color="teal" dark>
          <v-toolbar-title>Search TMDB for Movies</v-toolbar-title>
          <v-spacer/>
        </v-toolbar>

        <v-card color="grey lighten-5">
          <v-container fluid pb-0>
            <v-layout row>
              <v-flex grow pl-1>
                <v-text-field
                  placeholder="Enter the movie title search term"
                  outline
                  solo
                  clearable
                  v-model="searchTerm"
                  v-on:keyup.enter="doSearch"
                />
              </v-flex>
              <v-flex shrink pl-2 pt-3>
                <v-progress-circular indeterminate color="grey lighten-5" v-show="!searching"/>
                <v-progress-circular indeterminate color="grey" v-show="searching"/>
              </v-flex>
              <v-flex shrink pt-2>
                <v-btn icon v-on:click="doSearch">
                  <v-icon>search</v-icon>
                </v-btn>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card>

        <v-card v-if="!hadError">
          <v-list two-line>
            <template v-for="(item, index) in items">
              <v-list-tile :key="index" avatar>
                <v-list-tile-content>
                  <v-list-tile-title v-html="item.title"></v-list-tile-title>
                  <v-list-tile-sub-title v-html="item.popularity_summary"></v-list-tile-sub-title>
                </v-list-tile-content>

                <v-list-tile-avatar v-if="item.poster_image_url">
                  <img :src="item.poster_image_url">
                </v-list-tile-avatar>
              </v-list-tile>
            </template>
          </v-list>
        </v-card>

        <v-alert :value="hadError" type="error" color="red lighten-2">
          <h2 class="text-sm-left">Is the webserver process running?</h2>
          <p class="text-sm-left">
            <b>Received response:</b>
            {{ hadError }}
          </p>
        </v-alert>
      </v-flex>
    </v-layout>

    <MovieVue msg/>
    <!-- ^^^ not really making use of this component now except for a unit test -->
  </v-content>
</template>

<script>
import MovieVue from '../components/MovieVue.vue'
import axios from 'axios'

const localPort = 8081
const localServerUrl = `http://127.0.0.1:${localPort}`

const doLog = function (msg, error) {
  try {
    if (error) {
      // eslint-disable-next-line
      console.error(msg);
    } else {
      // eslint-disable-next-line
      console.log(msg);
    }
  } catch (ignore) {
    // no console support?
  }
}

const emptySearchTerm = function (term) {
  return !term || /^\s*$/.test(term)
}

export default {
  name: 'home',

  components: {
    MovieVue
  },

  methods: {
    doSearch () {
      let term = this.searchTerm
      this.hadError = false
      if (!emptySearchTerm(term)) {
        // non-empty string?
        this.searching = true
        try {
          let pending = axios.get(`${localServerUrl}/movies?search=${term}`)
          pending.then(
            this.handleSuccess.bind(this),
            this.handleRejection.bind(this)
          )
        } catch (err) {
          this.setError(err)
        }
      } else {
        this.items = []
      }
    },

    handleSuccess (response) {
      this.items = response.data
      this.searching = false
      // doLog(`set items ${JSON.stringify(this.items)}`);
    },

    handleRejection (reason) {
      this.setError(reason)
    },

    setError (err) {
      doLog(`setting error ${err}`)
      this.items = [
        {
          title: 'Error communicating with the server.',
          popularity_summary: `Please check that it's running locally on port ${localPort}.`
        }
      ]
      this.searching = false
      this.hadError = err
      doLog(err, true)
    }
  },

  data () {
    return {
      searchTerm: '',
      searching: false,
      hadError: false,
      items: []
    }
  }
}
</script>
