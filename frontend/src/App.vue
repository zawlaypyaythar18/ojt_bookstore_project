<template>
  <v-app>

    <v-main>
      <heading></heading>
      <router-view class="" style="height: 100%;" />
      <footing></footing>
    </v-main>
  </v-app>
</template>

<script>
import heading from "./components/heading.vue"
import footing from "./components/footing.vue"
export default {
  name: 'App',
<<<<<<< HEAD
  components: {
    heading,
    footing,
  }
=======

  data: () => ({
    //
  }),
>>>>>>> a02a108b4c17875e79c5edd1b8d86399a1c28e78
};
</script>
