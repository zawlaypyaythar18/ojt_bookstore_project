<template>
  <div class="bg-color">
    <v-container>
      <v-row class="mt-3">
        <v-col cols="1">
          <div>
            <v-img
              height="50"
              width="100"
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDbtVEylH6FvepaR87nITchzRDazbWzX9cFQ&usqp=CAU"
              style="border-radius: 50%"
              class="text-center"
            ></v-img>
          </div>
        </v-col>
        <v-col cols="11">
          <h2 class="text-center font-de" style="font-size: 40px">
            Welcome To Champion's Bookstore
          </h2>
        </v-col>
        <v-col
          class="col-lg-6 col-md-12 col-sm-12 col-xs-12"
          style="margin-top: 80px"
          v-if="!isLogin"
        >
          <p
            class="text-center font-play animate__heartBeat"
            style="font-size: 25px"
          >
            Get Your New Book With The Best Price
          </p>
          <div class="text-center" v-if="!isLogin">
            <v-btn class="ma-2" color="primary" @click="goToRoute('/register')">
              Register
            </v-btn>
          </div>
          <p class="text-center mt-2" style="font-size: 15px" v-if="!isLogin">
            If you Already Register, You can Login
          </p>
          <div class="text-center" v-if="!isLogin">
            <v-btn class="ma-2" color="primary" @click="goToRoute('/login')">
              Login
            </v-btn>
          </div>
        </v-col>
        <v-col class="col-lg-6 col-md-12 col-sm-12 col-xs-12" v-if="!isLogin">
          <!-- <v-img
            src="https://www.stoltze.com/wp-content/uploads/2018/09/RISD_Viewbooks_Cover.jpg"
          ></v-img> -->
          <v-carousel
            cycle
            height="400"
            hide-delimiter-background
            show-arrows-on-hover
            class="mt-3"
          >
            <v-carousel-item
              v-for="(item, i) in items"
              :key="i"
              :src="item.src"
            ></v-carousel-item>
          </v-carousel>
        </v-col>
        <v-col class="col-lg-12 col-md-12 col-sm-12 col-xs-12" v-if="isLogin">
          <!-- <v-img
            src="https://www.stoltze.com/wp-content/uploads/2018/09/RISD_Viewbooks_Cover.jpg"
          ></v-img> -->
          <v-carousel
            cycle
            height="400"
            hide-delimiter-background
            show-arrows-on-hover
            class="mt-3"
          >
            <v-carousel-item
              v-for="(item, i) in items"
              :key="i"
              :src="item.src"
            ></v-carousel-item>
          </v-carousel>
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
      isLogin: false,
      items: [
        {
          src: "https://foodtank.com/wp-content/uploads/2021/07/alfons-morales-YLSwjSy7stw-unsplash.jpg",
        },
        {
          src: "https://images.adsttc.com/media/images/573c/90c0/e58e/ce1e/1600/0007/large_jpg/Here_is_a_theater_to_unfold_an_outstanding_drama__and_the_characters_are_book_lovers_sitting_on_the_soft_couch_or_standing_beside_the_bookshelves._0004.jpg?1463587001",
        },
        {
          src: "https://miro.medium.com/max/1400/1*HY09Xt_KMPnk1CAaSuHX5g.jpeg",
        },
        {
          src: "https://media.istockphoto.com/id/1217338527/photo/open-book-close-up-photo-blurred-background.jpg?s=612x612&w=0&k=20&c=5MNgrD_A1YesGnyk1k4UJlvwyGsIwd5hJmyaVmTsub8=",
        },
      ],
    };
  },
  created() {
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal, oldVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );
  },
  methods: {
    goToRoute(path) {
      this.$router.push({ path: path });
    },
  },
};
</script>

<style scoped>
</style>
