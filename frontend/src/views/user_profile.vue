<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="3">
          <user_info_sidebar></user_info_sidebar>
        </v-col>
        <v-col cols="9">
          <v-card class="my-card" elevation="6">
            <v-card-title>User Profile</v-card-title>
            <v-divider></v-divider>
            <v-form ref="updateProfileForm">
              <v-alert
                class="mt-3 mx-1"
                v-show="successAlert"
                dense
                type="info"
              >
                Updated Successfully
              </v-alert>

              <v-alert class="mt-3 mx-1" v-show="errorAlert" dense type="error">
                Update Failed! Incorrect Password!
              </v-alert>

              <v-alert
                class="mt-3 mx-1"
                v-show="alreadyExistAlert"
                dense
                type="error"
              >
                Email Already Exist!
              </v-alert>

              <v-alert
                class="mt-3 mx-1"
                v-show="notSameAlert"
                dense
                type="warning"
              >
                New Password and Confirm Password is not Match!
              </v-alert>

              <v-alert
                class="mt-3 mx-1"
                v-show="chaLengthAlert"
                dense
                type="error"
              >
                New Password Must Be Aleast 4 Characters!
              </v-alert>

              <v-row>
                <v-col class="mx-6">
                  <v-text-field
                    class="mt-5"
                    label="FirstName"
                    dense
                    outlined
                    v-model="userData.firstName"
                  ></v-text-field>
                  <v-text-field
                    class="mt-5"
                    label="LastName"
                    dense
                    outlined
                    v-model="userData.lastName"
                  ></v-text-field>
                  <v-text-field
                    class="mt-5"
                    label="Username"
                    dense
                    outlined
                    v-model="userData.username"
                    :rules="[(v) => !!v || 'Required']"
                  ></v-text-field>
                  <v-text-field
                    class="mt-5"
                    label="Phone"
                    placeholder="09-xxxxxxxxx"
                    dense
                    outlined
                    v-model="userData.phone"
                  ></v-text-field>
                </v-col>

                <v-col class="mx-5">
                  <v-text-field
                    class="mt-5"
                    label="Email(disabled)"
                    dense
                    outlined
                    v-model="userData.email"
                    disabled
                  ></v-text-field>

                  <v-text-field
                    class="mt-5"
                    label="Password"
                    placeholder="Enter Your Current Password"
                    dense
                    outlined
                    v-model="currentPassword"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show1 ? 'text' : 'password'"
                    name="currentPassword"
                    @click:append="show1 = !show1"
                    :rules="[(v) => !!v || 'Required']"
                  ></v-text-field>

                  <v-text-field
                    class="mt-5"
                    label="New Password"
                    placeholder="Enter Your New Password"
                    dense
                    outlined
                    v-model="newPassword"
                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show2 ? 'text' : 'password'"
                    name="new Password"
                    hint="At least 4 characters"
                    @click:append="show2 = !show2"
                  ></v-text-field>

                  <v-text-field
                    class="mt-5"
                    label="Confirm Password"
                    placeholder="Enter Your New Password Again"
                    dense
                    outlined
                    v-model="confirmPassword"
                    :append-icon="show3 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show3 ? 'text' : 'password'"
                    name="confirm Password"
                    hint="At least 4 characters"
                    @click:append="show3 = !show3"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-col class="d-flex flex-row-reverse" style="margin-right: 80px">
                <v-btn color="info" @click="updateUserInfo">
                  <span v-if="!loading">Update Profile</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="primary"
                  ></v-progress-circular>
                </v-btn>
              </v-col>
            </v-form>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import utils from "@/utils/utils";
import user_info_sidebar from "@/components/user_info_sidebar.vue";

export default {
  name: "user_profile",
  components: {
    user_info_sidebar,
  },
  data() {
    return {
      loginUser: {},
      userData: {},
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
      alreadyExistAlert: false,
      errorAlert: false,
      successAlert: false,
      notSameAlert: false,
      show1: "",
      show2: "",
      show3: "",
      loading: false,
      chaLengthAlert: false,
    };
  },
  async created() {
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
    await this.getUserData();
  },
  methods: {
    async getUserData() {
      const resp = await utils.http.get(
        "/api/user/profile?userId=" + this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.userData = data;
        }
      }
    },
    async updateUserInfo() {
      if (this.newPassword != "" && this.newPassword.length < 4) {
        this.alreadyExistAlert = false;
        this.errorAlert = false;
        this.successAlert = false;
        this.notSameAlert = false;
        this.chaLengthAlert = true;
      } else {
        if (this.$refs.updateProfileForm.validate()) {
          this.alreadyExistAlert = false;
          this.errorAlert = false;
          this.successAlert = false;
          this.notSameAlert = false;
          this.chaLengthAlert = false;
          if (this.newPassword == this.confirmPassword) {
            this.loading = true;
            const resp = await utils.http.put(
              "/api/user/profile/update?currentPassword=" +
                this.currentPassword +
                "&" +
                "newPassword=" +
                this.newPassword,
              this.userData
            );
            if (resp.status === 200) {
              this.successAlert = true;
            }

            if (resp.status === 404) {
              this.errorAlert = true;
            }

            if (resp.status === 400) {
              this.alreadyExistAlert = true;
            }
            this.loading = false;
          } else {
            this.notSameAlert = true;
          }
        }
      }
    },
  },
};
</script>

<style></style>
