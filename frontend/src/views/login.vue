<template>
  <div class="bg-img">
    <h2 class="text-center pt-12 font-play">LOGIN</h2>
    <v-container>
      <v-row>
        <v-col cols="3"></v-col>
        <v-col cols="6">
          <v-form ref="loginForm" v-model="loginForm">
            <v-text-field
              v-model="email"
              :rules="[
                (v) => !!v || 'Required',
                (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
              ]"
              label="E-mail"
              required
              placeholder="text@example.com"
              class="mt-3"
            ></v-text-field>

            <v-text-field
              v-model="password"
              :counter="20"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length >= 4) ||
                  'Password must be aleast 4 characters',
              ]"
              label="Password"
              required
              class="mt-3"
              :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show1 ? 'text' : 'password'"
              name="password"
              @click:append="show1 = !show1"
            ></v-text-field>

            <v-btn
              :disabled="!loginForm"
              color="primary"
              class="mr-4 mt-4"
              @click="login"
            >
              <span v-if="!loading">Login</span>
              <v-progress-circular
                v-else
                indeterminate
                color="default"
              ></v-progress-circular>
            </v-btn>
          </v-form>
        </v-col>
        <v-col cols="3"></v-col>
        <v-col cols="3"></v-col>
        <v-col cols="6">
          <v-dialog v-model="dialog" width="500">
            <template v-slot:activator="{ on, attrs }">
              <a
                v-bind="attrs"
                v-on="on"
                class=""
                style="text-decoration: underline;color: red;font-size: 15px;"
              >
                Forget Password
              </a>
            </template>

            <v-card>
              <v-card-title class="text-h5 grey lighten-2">
                Forget Password
              </v-card-title>

              <v-card-text class="text-h12 mt-3">
                Please Enter Your Email
              </v-card-text>

              <v-card-text>
                <v-form ref="forgetPasswordForm" v-model="forgetPasswordForm">
                  <v-row>
                    <v-col cols="3"></v-col>
                    <v-col cols="6">
                      <v-text-field
                        v-model="forgetEmail"
                        :rules="[
                          (v) => !!v || 'Required',
                          (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                        ]"
                        label="E-mail"
                        required
                        placeholder="text@example.com"
                        class="mt-3"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="3"></v-col>
                  </v-row>
                </v-form>
              </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="primary"
                  class="mr-4 mt-4"
                  @click="forgetPassword"
                >
                  <span v-if="!forgetLoading">Send</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="default"
                  ></v-progress-circular>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
        <v-col cols="3"></v-col>
        <v-col cols="3"></v-col>
        <v-col cols="6">
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Login Failed! <br />
          Email or Password is wrong!
        </v-alert>

        <v-alert
          class="mt-3 mb-3"
          v-show="forgetPasswordAlert"
          dense
          type="info"
        >
          Email is Send
        </v-alert>
        <v-alert
          class="mt-3 mb-3"
          v-show="emailNotFoundAlert"
          dense
          type="error"
        >
          Email is not Found
        </v-alert>
        </v-col>
        <v-col cols="3"></v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "login",
  data() {
    return {
      loginForm: false,
      email: "",
      password: "",
      loading: false,
      errorAlert: false,
      show1: "",

      forgetPasswordForm: false,
      sheet: false,
      forgetEmail: "",
      forgetPasswordAlert: false,
      forgetLoading: false,
      emailNotFoundAlert: false,
      dialog: false,
    };
  },
  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.loading = true;
        this.errorAlert = false;
        this.emailNotFoundAlert = false;
        this.forgetPasswordAlert = false;
        const resp = await utils.http.post("/api/user/login", {
          email: this.email,
          password: this.password,
        });
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            // console.log(data)
            this.$store.commit("setLoginUser", data);
            if (data.userRole == "admin") {
              this.$router.push({ path: "/admin" }).catch(() => {});
            } else {
              this.$router.push({ path: "/" });
            }
          }
        } else {
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },
    async forgetPassword() {
      if (this.$refs.forgetPasswordForm.validate()) {
        this.forgetLoading = true;
        this.errorAlert = false;
        this.emailNotFoundAlert = false;
        this.forgetPasswordAlert = false;
        const resp = await utils.http.post(
          "/api/user/forget/password?email=" + this.forgetEmail
        );
        if (resp && resp.status == 200) {
          this.forgetPasswordAlert = true;
        } else {
          this.emailNotFoundAlert = true;
        }
        this.forgetLoading = false;
        this.dialog = false;
      }
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
