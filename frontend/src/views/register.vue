<template>
  <div class="bg-img">
    <h2 class="text-center pt-16 font-play">REGISTER</h2>
    <v-container>
      <v-row>
        <v-col cols="3"></v-col>
        <v-col cols="6">
          <v-form ref="registerForm" v-model="registerForm">
            <v-text-field
              v-model="username"
              :counter="30"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length > 2) || 'Username must be aleast 3 characters',
                (v) =>
                  (v && v.length <= 30) ||
                  'Username must be less than 30 characters',
              ]"
              label="Username"
              required
              placeholder="Enter Your Username"
              class="mt-3"
            ></v-text-field>

            <v-text-field
              v-model="email"
              :rules="[
                (v) => !!v || 'Required',
                (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
              ]"
              label="E-mail"
              required
              placeholder="test@example.com"
              class="mt-3"
            ></v-text-field>

            <v-btn
              :disabled="!registerForm || clickRegister"
              color="primary"
              class="mr-4 mt-4"
              @click="register"
            >
              <span v-if="!loading">Register</span>
              <v-progress-circular
                v-else
                indeterminate
                color="default"
              ></v-progress-circular>
            </v-btn>

            <v-alert
              shaped
              dark
              color="blue"
              v-show="emailAlert"
              type="info"
              dense
              class="mt-3"
              >Email is Send.</v-alert
            >

            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Register Failed!
              <br />
              Email Already Exist!
            </v-alert>
          </v-form>
        </v-col>
        <v-col cols="3"></v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "register",
  data() {
    return {
      registerForm: false,
      username: "",
      email: "",
      loading: false,
      emailAlert: false,
      errorAlert: false,
      clickRegister: false,
    };
  },
  methods: {
    async register() {
      this.emailAlert = false;
      this.errorAlert = false;
      this.clickRegister = true;
      if (this.$refs.registerForm.validate()) {
        this.loading = true;
        const resp = await utils.http.post("/api/user/register", {
          username: this.username,
          email: this.email,
          password: "1111",
        });
        if (resp && resp.status === 200) {
          this.emailAlert = true;
        } else {
          this.errorAlert = true;
        }
        this.loading = false;

        // console.log(this.email + this.username)
      }

      this.clickRegister = false;
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Playfair+Display:ital@1&display=swap");

.bg-img {
  background-attachment: fixed;
  background-image: url("https://miro.medium.com/max/1400/1*HY09Xt_KMPnk1CAaSuHX5g.jpeg");
}

.font-play {
  font-family: "Playfair Display", serif;
}
</style>
