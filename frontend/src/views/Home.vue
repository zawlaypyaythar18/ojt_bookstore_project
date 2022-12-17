<template>
  <div>
    <v-container>
      <v-row class="mt-3">
        <v-col class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
          <h2 class="text-center" style="font-size: 50px;">Welcome To Champion's Bookstore</h2>
          <p class="text-center" style="font-size: 30px;">Get Your New Book With The Best Price</p>
          <div class="text-center">
            <v-btn
            class="ma-2"
            outlined
            color="indigo"
            @click="goToRoute('/register')"
          >
            Register
          </v-btn>
          </div>
          <p class="text-center mt-2" style="font-size: 18px;">If you Already Register, You can Login </p>
          <div class="text-center">
            <v-btn
            class="ma-2"
            outlined
            color="indigo"
            @click="goToRoute('/login')"
          >
            Login
          </v-btn>
          </div>
        </v-col>
        <v-col class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
          <v-img
            src="https://www.stoltze.com/wp-content/uploads/2018/09/RISD_Viewbooks_Cover.jpg"
          ></v-img>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
    name: "home",
    
    components: {},

    data() {
      return {
      }
    },
    created() {
    },
    methods: {
      goToRoute(path) {
        this.$router.push({ path: path });
      }
    },

}
</script>

<style scoped>
</style>