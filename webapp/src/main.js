import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import './plugins/vuetify'
import './plugins/axios'
import App from './App.vue'
// import App from './AppVuetify.vue' // for playing with the usual Vuetify setup
import router from './router'

Vue.config.productionTip = false

Vue.use(Vuetify)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
