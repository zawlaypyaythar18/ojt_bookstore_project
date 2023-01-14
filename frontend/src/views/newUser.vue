<template>
  <div>
    <v-alert class="mt-3" v-show="errorAlert" dense type="error">
      Login Failed!
    </v-alert>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "newUser",
  data() {
    return {
      token: this.$route.query.token,
      errorAlert: false,
    };
  },
  created() {
    this.checkUserToken();
  },
  methods: {
    async checkUserToken() {
      // console.log(this.token)
      const resp = await utils.http.post("/api/user/newUser", {
        token: this.token,
      });
      if (resp && resp.status == 200) {
        const data = await resp.json();
        if (data) {
          this.$store.commit("setLoginUser", data);
          this.$router.push({ path: "/" });
        }
      } else {
        this.errorAlert = true;
      }
    },
  },
};
</script>

<style></style>
