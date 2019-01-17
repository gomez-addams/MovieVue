<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <MovieVue msg="Welcome to the MovieVue webapp"/>
  </div>
</template>

<script>
// @ is an alias to /src
import MovieVue from '@/components/MovieVue.vue'

export default {
  name: 'home',
  components: {
    MovieVue
  }
}
</script>
