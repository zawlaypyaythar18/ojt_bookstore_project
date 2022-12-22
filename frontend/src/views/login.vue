<template>
  <div>
    <h2 class="text-center">LOGIN</h2>
    <v-container>
        <v-row>
            <v-col cols="3"></v-col>
            <v-col cols="6">
                <v-form ref="loginForm" v-model="loginForm">
                    <v-text-field
                    v-model="email"
                    :rules="[
                    (v) => !!v || 'Required',
                    (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',]"
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
                    color="success"
                    class="mr-4 mt-4"
                    @click="login"
                    >
                    <span v-if="!loading">Login</span>
                    <v-progress-circular
                        v-else
                        indeterminate
                        color="primary"
                    ></v-progress-circular>
                    </v-btn>

                    <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                    Login Failed! <br />
                    Email or Password is wrong!
                    </v-alert>
                </v-form>
            </v-col>
            <v-col cols="3"></v-col>
        </v-row>
    </v-container>
  </div>
</template>

<script>
import utils from '@/utils/utils';

export default {
    name: "login",
    data() {
        return {
            loginForm: false,
            email: "admin@gmail.com",
            password: "1111",
            loading: false,
            errorAlert: false,
            show1: "",
        }
    },
    methods: {
        async login() {
            if (this.$refs.loginForm.validate()) {
                this.loading = true;
                this.errorAlert = false;
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
        }
    }

}
</script>

<style>

</style>