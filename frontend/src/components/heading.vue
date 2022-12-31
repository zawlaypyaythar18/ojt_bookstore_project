<template>
  <div>
    <v-app-bar app color="blue-grey lighten-1" dense dark>
      <v-app-bar-nav-icon
        @click="drawer = true"
        class="d-flex d-sm-none"
      ></v-app-bar-nav-icon>
      <!-- Add this class to show menu icon only on small screen -->
      <router-link class="navtitle" to="/">書店</router-link>
      <v-spacer></v-spacer>
      <div class="d-none d-sm-flex">
        <router-link class="mx-2 navlink" to="/">ホーム</router-link>
        <span>|</span>
        <router-link class="mx-2 navlink" to="/book/list">本一覧</router-link>
        <span v-if="this.loginUser.userRole == 'admin'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'admin'"
          class="mx-2 navlink"
          to="/admin"
          >管理者</router-link
        >
        <span v-if="!isLogin">|</span>
        <router-link v-if="!isLogin" class="mx-2 navlink" to="/register"
          >アカウント登録</router-link
        >
        <span v-if="this.loginUser.userRole == 'user'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'user'"
          class="mx-2 navlink"
          to="/shopping/cart"
          >ショッピングカート</router-link
        >
        <span v-if="!isLogin">|</span>
        <router-link v-if="!isLogin" class="mx-2 navlink" to="/login"
          >ログイン</router-link
        >
        <span v-if="this.loginUser.userRole == 'admin'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'admin'"
          class="mx-2 navlink"
          to="/admin/profile"
          >プロフィール</router-link
        >

        <span v-if="this.loginUser.userRole == 'user'">|</span>
        <router-link
          v-if="this.loginUser.userRole == 'user'"
          class="mx-2 navlink"
          to="/user/profile"
          >プロフィール</router-link
        >

        <span v-if="isLogin">|</span>
        <a v-if="isLogin" class="mx-2 navlink" @click="logout()">ログアウト</a>
      </div>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-list nav dense>
        <v-list-item-group>
          <v-list-item class="blue-grey lighten-1">
            <router-link class="mx-2 navlink text-decoration-none" to="/"
              >ホーム</router-link
            >
          </v-list-item>

          <v-list-item class="blue-grey lighten-1">
            <router-link
              class="mx-2 navlink text-decoration-none"
              to="/book/list"
              >本一覧</router-link
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
              >ショッピングカート</router-link
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
              >管理者</router-link
            >
          </v-list-item>

          <v-list-item v-if="!isLogin" class="blue-grey lighten-1">
            <router-link
              v-if="!isLogin"
              class="mx-2 navlink text-decoration-none"
              to="/register"
              >アカウント登録</router-link
            >
          </v-list-item>

          <v-list-item v-if="!isLogin" class="blue-grey lighten-1">
            <router-link
              v-if="!isLogin"
              class="mx-2 navlink text-decoration-none"
              to="/login"
              >ログイン</router-link
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
              >プロフィール</router-link
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
              >プロフィール</router-link
            >
          </v-list-item>

          <v-list-item v-if="isLogin" class="blue-grey lighten-1">
            <a
              v-if="isLogin"
              class="mx-2 navlink text-decoration-none"
              @click="logout()"
              >ログアウト</a
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
