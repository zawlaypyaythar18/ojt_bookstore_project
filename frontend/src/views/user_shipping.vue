<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="3">
          <user_info_sidebar></user_info_sidebar>
        </v-col>
        <v-col cols="9">
          <div>
            <v-alert
              class="mt-3"
              v-model="successAlert"
              dismissible
              dense
              type="success"
            >
              Successfully Added Shipping Address
            </v-alert>

            <v-alert
              class="mt-3"
              v-model="defaultAlert"
              dismissible
              dense
              type="info"
            >
              Default Shipping Address is Now Change
            </v-alert>

            <v-alert
              class="mt-3"
              v-model="updateAlert"
              dismissible
              dense
              type="info"
            >
              Successfully Updated Shipping Address
            </v-alert>
            <v-card>
              <v-toolbar flat color="primary" dark>
                <v-toolbar-title>Shipping Information</v-toolbar-title>
              </v-toolbar>
              <v-tabs v-model="tab">
                <v-tab href="#tab-1">
                  <v-icon left> mdi-list-box </v-icon>
                  List Of Shipping Addresses
                </v-tab>
                <v-tab href="#tab-2">
                  <v-icon left> mdi-map-marker </v-icon>
                  Add(Update) Shipping Address
                </v-tab>

                <v-tab-item value="tab-1">
                  <v-card flat>
                    <v-card-text>
                      <v-data-table
                        :headers="headers"
                        :items="userShippingList"
                        :items-per-page="10"
                      >
                        <template v-slot:item.default="{ item }">
                          <v-radio-group v-model="selected">
                            <v-radio :value="item.userShippingId" />
                          </v-radio-group>
                        </template>
                        <template v-slot:item.userAddress="{ item }">
                          {{ item.userAddress.userAddressStreet1 }}
                          {{ item.userAddress.userAddressStreet2 }},
                          {{ item.userAddress.userAddressCity }},
                          {{ item.userAddress.userAddressState }}
                        </template>
                        <template v-slot:item.actions="{ item }">
                          <v-btn
                            class="mr-2"
                            color="info"
                            fab
                            dark
                            x-small
                            elevation="2"
                            @click="updateShippingAddress(item.userAddress)"
                            ><v-icon>mdi-pencil</v-icon></v-btn
                          >
                          <v-btn
                            color="red"
                            fab
                            dark
                            x-small
                            elevation="2"
                            @click="
                              deleteDialog = true;
                              toDeleteShipAddress = item.userAddress;
                              deleteShippingId = item.userShippingId;
                            "
                            ><v-icon>mdi-delete</v-icon></v-btn
                          >
                        </template>
                      </v-data-table>

                      <v-dialog v-model="deleteDialog" width="400">
                        <v-card>
                          <v-toolbar color="error" dark>
                            <div>Delete This Shipping Address?</div>
                            <v-spacer></v-spacer>
                            <v-btn icon @click="deleteDialog = false">
                              <v-icon>mdi-close</v-icon>
                            </v-btn>
                          </v-toolbar>
                          <v-card-text class="pa-4">
                            <v-row dense>
                              <v-col cols="3" class="font-weight-bold"
                                >ID</v-col
                              >
                              <v-col cols="7">{{
                                toDeleteShipAddress.userAddressId
                              }}</v-col>
                              <v-col cols="3" class="font-weight-bold"
                                >Address</v-col
                              >
                              <v-col cols="7"
                                >{{ toDeleteShipAddress.userAddressStreet1 }}
                                {{ toDeleteShipAddress.userAddressStreet2 }}
                                {{ toDeleteShipAddress.userAddressCity }}
                                {{
                                  toDeleteShipAddress.userAddressState
                                }}</v-col
                              >
                            </v-row>
                          </v-card-text>
                          <v-card-actions class="justify-end">
                            <v-btn
                              color="red"
                              dark
                              @click="deleteShippingAddress()"
                              >Delete</v-btn
                            >
                          </v-card-actions>
                        </v-card>
                      </v-dialog>

                      <v-btn color="primary" @click="saveDefault"
                        >Save Default</v-btn
                      >
                    </v-card-text>
                  </v-card>
                </v-tab-item>
                <v-tab-item value="tab-2">
                  <v-card flat>
                    <v-card-text>
                      <v-form ref="shippingForm" v-model="shippingForm">
                        <v-row>
                          <v-col cols="3"></v-col>
                          <v-col cols="6">
                            <v-text-field
                              label="Name"
                              placeholder="Receiver Name"
                              v-model="userShippingName"
                              outlined
                              dense
                              :rules="[(v) => !!v || 'Required']"
                            ></v-text-field>

                            <v-text-field
                              label="Street Address 1"
                              placeholder="Enter Your Shipping Address"
                              v-model="userShippingStreet1"
                              outlined
                              dense
                              :rules="[(v) => !!v || 'Required']"
                            ></v-text-field>
                            <v-text-field
                              label="Street Address 2"
                              hint="You don't have to fill if you done filled in Street 1"
                              v-model="userShippingStreet2"
                              outlined
                              dense
                            ></v-text-field>

                            <v-text-field
                              label="City"
                              placeholder="Shipping City"
                              v-model="userShippingCity"
                              outlined
                              dense
                              :rules="[(v) => !!v || 'Required']"
                            ></v-text-field>

                            <v-select
                              :items="stateList"
                              v-model="userShippingState"
                              label="Choose State"
                              outlined
                              dense
                              :rules="[(v) => !!v || 'Required']"
                            ></v-select>

                            <v-text-field
                              label="Zipcode"
                              placeholder="Shipping Zipcode"
                              v-model="userShippingZipcode"
                              outlined
                              dense
                              type="number"
                              :rules="[(v) => !!v || 'Required']"
                            ></v-text-field>

                            <v-btn
                              color="primary"
                              @click="addNewShippingAddress"
                              >Save all</v-btn
                            >
                            <v-btn
                              color="error"
                              @click="resetFormData"
                              class="ml-3"
                              >Clear all</v-btn
                            >
                          </v-col>
                          <v-col cols="3"></v-col>
                        </v-row>
                      </v-form>
                    </v-card-text>
                  </v-card>
                </v-tab-item>
              </v-tabs>
            </v-card>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import user_info_sidebar from "../components/user_info_sidebar.vue";
import utils from "@/utils/utils";

export default {
  name: "user_shipping",
  components: {
    user_info_sidebar,
  },
  data() {
    return {
      headers: [
        { text: "Default", value: "default", sortable: false },
        { text: "Shipping Address", value: "userAddress", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      deleteDialog: false,
      toDeleteShipAddress: {},
      deleteShippingId: null,

      shippingForm: false,
      successAlert: false,
      defaultAlert: false,
      updateAlert: false,
      tab: "tab-1",
      selected: 0,
      loginUser: {},

      stateList: [],
      userShippingList: [],

      userShippingId: null,
      userShippingName: "",
      userShippingStreet1: "",
      userShippingStreet2: "",
      userShippingCity: "",
      userShippingState: "",
      userShippingZipcode: null,
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    await this.listOfShippingAddresses();
  },
  methods: {
    async listOfShippingAddresses() {
      const resp = await utils.http.get(
        "/api/user/shipping/list?currentUserId=" + this.loginUser.id
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.stateList = data.stateList;
          this.userShippingList = data.userShippingList;
          //   console.log(data);
          this.userShippingList.forEach((item, index) => {
            // console.log(item.userShippingId)
            if (item.userShippingDefault == true) {
              // console.log(item.userShippingId)
              this.selected = item.userShippingId;
            }
          });
        }
      }
    },

    async addNewShippingAddress() {
      this.successAlert = false;
      this.defaultAlert = false;
      this.updateAlert = false;
      if (this.$refs.shippingForm.validate()) {
        if (this.userShippingId == null) {
          const resp = await utils.http.post(
            "/api/user/shipping/add?currentUserId=" + this.loginUser.id,
            {
              userAddressId: this.userShippingId,
              userAddressName: this.userShippingName,
              userAddressStreet1: this.userShippingStreet1,
              userAddressStreet2: this.userShippingStreet2,
              userAddressCity: this.userShippingCity,
              userAddressState: this.userShippingState,
              userAddressZipcode: this.userShippingZipcode,
            }
          );
          if (resp.status === 200) {
            if (this.userShippingId == null) {
              this.successAlert = true;
            }

            this.userShippingId = null;
            this.userShippingName = "";
            this.userShippingStreet1 = "";
            this.userShippingStreet2 = "";
            this.userShippingCity = "";
            this.userShippingState = "";
            this.userShippingZipcode = null;

            await this.listOfShippingAddresses();
            this.tab = "tab-1";
          }
        } else {
          const resp = await utils.http.put(
            "/api/user/shipping/update?currentUserId=" + this.loginUser.id,
            {
              userAddressId: this.userShippingId,
              userAddressName: this.userShippingName,
              userAddressStreet1: this.userShippingStreet1,
              userAddressStreet2: this.userShippingStreet2,
              userAddressCity: this.userShippingCity,
              userAddressState: this.userShippingState,
              userAddressZipcode: this.userShippingZipcode,
            }
          );
          if (resp.status === 200) {
            if (this.userShippingId != null) {
              this.updateAlert = true;
            }

            this.userShippingId = null;
            this.userShippingName = "";
            this.userShippingStreet1 = "";
            this.userShippingStreet2 = "";
            this.userShippingCity = "";
            this.userShippingState = "";
            this.userShippingZipcode = null;

            await this.listOfShippingAddresses();
            this.tab = "tab-1";
          }
        }
      }
    },
    updateShippingAddress(item) {
      this.successAlert = false;
      this.updateAlert = false;
      this.defaultAlert = false;
      this.userShippingId = item.userAddressId;
      this.userShippingName = item.userAddressName;
      this.userShippingStreet1 = item.userAddressStreet1;
      this.userShippingStreet2 = item.userAddressStreet2;
      this.userShippingCity = item.userAddressCity;
      this.userShippingState = item.userAddressState;
      this.userShippingZipcode = item.userAddressZipcode;
      this.tab = "tab-2";
    },
    async deleteShippingAddress() {
      this.successAlert = false;
      this.updateAlert = false;
      this.defaultAlert = false;
      // console.log(this.deleteShippingId)
      const resp = await utils.http.del(
        "/api/user/shipping/remove?userShippingId=" +
          this.deleteShippingId +
          "&currentUserId=" +
          this.loginUser.id
      );
      if (resp.status === 200) {
        await this.listOfShippingAddresses();
        this.deleteDialog = false;
      }
    },
    async saveDefault() {
      // console.log(this.selected);
      this.successAlert = false;
      this.updateAlert = false;
      this.defaultAlert = false;
      const resp = await utils.http.post(
        "/api/user/shipping/default?userShippingId=" +
          this.selected +
          "&currentUserId=" +
          this.loginUser.id
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.userShippingList = data;
          this.defaultAlert = true;
        }
      }
    },
    resetFormData() {},
  },
};
</script>

<style></style>
