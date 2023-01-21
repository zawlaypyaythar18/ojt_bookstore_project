<template>
  <div>
    <v-app-bar app color="blue-grey lighten-1" dense dark>
      <v-app-bar-nav-icon
        @click="drawer = true"
        class="d-flex d-sm-none"
      ></v-app-bar-nav-icon>
      <!-- Add this class to show menu icon only on small screen -->
      <router-link class="navtitle" to="/">
        <v-row>
          <v-col cols="4">
            <v-img
              height="25"
              width="40"
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDbtVEylH6FvepaR87nITchzRDazbWzX9cFQ&usqp=CAU"
              style="border-radius: 50%"
              class="text-end"
            ></v-img>
          </v-col>
          <v-col cols="8">Bookstore</v-col>
        </v-row>
      </router-link>
      <v-spacer></v-spacer>
      <div class="d-none d-sm-flex">
        <router-link class="mx-2 navlink" to="/">Home</router-link>
        <span>|</span>
        <router-link class="mx-2 navlink" to="/book/list">Books</router-link>
        <span v-if="this.loginUser.userRole == 'admin'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'admin'"
          class="mx-2 navlink"
          to="/admin"
          >Admin</router-link
        >
        <span v-if="!isLogin">|</span>
        <router-link v-if="!isLogin" class="mx-2 navlink" to="/register"
          >Register</router-link
        >
        <span v-if="this.loginUser.userRole == 'user'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'user'"
          class="mx-2 navlink"
          to="/shopping/cart"
          >ShoppingCart</router-link
        >
        <span v-if="!isLogin">|</span>
        <router-link v-if="!isLogin" class="mx-2 navlink" to="/login"
          >Login</router-link
        >
        <span v-if="this.loginUser.userRole == 'admin'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'admin'"
          class="mx-2 navlink"
          to="/admin/profile"
          >Profile</router-link
        >

        <span v-if="this.loginUser.userRole == 'user'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'user'"
          class="mx-2 navlink"
          to="/user/profile"
          >Profile</router-link
        >

        <span v-if="isLogin">|</span>
        <a v-if="isLogin" class="mx-2 navlink" @click="logout()">Logout</a>
      </div>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-list nav dense>
        <v-list-item-group>
          <v-list-item class="blue-grey lighten-1">
            <router-link class="mx-2 navlink text-decoration-none" to="/"
              >Home</router-link
            >
          </v-list-item>

          <v-list-item class="blue-grey lighten-1">
            <router-link
              class="mx-2 navlink text-decoration-none"
              to="/book/list"
              >Books</router-link
            >
          </v-list-item>

          <v-list-item
            v-if="this.loginUser.userRole == 'user'"
            class="blue-grey lighten-1"
          >
            <router-link
              v-if="this.loginUser.userRole == 'user'"
              class="mx-2 navlink text-decoration-none"
              to="/shopping/cart"
              >ShoppingCart</router-link
            >
          </v-list-item>

          <v-list-item
            v-if="this.loginUser.userRole == 'admin'"
            class="blue-grey lighten-1"
          >
            <router-link
              v-if="this.loginUser.userRole == 'admin'"
              class="mx-2 navlink text-decoration-none"
              to="/admin"
              >Admin</router-link
            >
          </v-list-item>

          <v-list-item v-if="!isLogin" class="blue-grey lighten-1">
            <router-link
              v-if="!isLogin"
              class="mx-2 navlink text-decoration-none"
              to="/register"
              >Register</router-link
            >
          </v-list-item>

          <v-list-item v-if="!isLogin" class="blue-grey lighten-1">
            <router-link
              v-if="!isLogin"
              class="mx-2 navlink text-decoration-none"
              to="/login"
              >Login</router-link
            >
          </v-list-item>

          <v-list-item
            v-if="this.loginUser.userRole == 'admin'"
            class="blue-grey lighten-1"
          >
            <router-link
              v-if="this.loginUser.userRole == 'admin'"
              class="mx-2 navlink text-decoration-none"
              to="/admin/profile"
              >Profile</router-link
            >
          </v-list-item>

          <v-list-item
            v-if="this.loginUser.userRole == 'user'"
            class="blue-grey lighten-1"
          >
            <router-link
              v-if="this.loginUser.userRole == 'user'"
              class="mx-2 navlink text-decoration-none"
              to="/user/profile"
              >Profile</router-link
            >
          </v-list-item>

          <v-list-item v-if="isLogin" class="blue-grey lighten-1">
            <a
              v-if="isLogin"
              class="mx-2 navlink text-decoration-none"
              @click="logout()"
              >Logout</a
            >
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "heading",

  data: () => ({
    loginUser: {},
    isLogin: false,
    drawer: false,
  }),

  created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
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
    logout() {
      this.$store.commit("logout");
      if (this.$route.path != "/") {
        this.$router.push({ path: "/" });
      }
      location.reload();
    },
  },
};
</script>

<style scoped>
.navlink {
  color: rgb(0, 0, 0) !important;
  text-decoration: none;
}

.navlink:hover {
  cursor: pointer;
  text-decoration: none;
  color: rgb(255, 255, 255) !important;
}

.navtitle {
  color: white !important;
  text-decoration: none;
  cursor: pointer !important;
}
</style>
