<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="3">
          <user_info_sidebar></user_info_sidebar>
        </v-col>
        <v-col cols="9">
          <v-alert
            class="mt-3"
            v-model="successAlert"
            dismissible
            dense
            type="success"
          >
            Successfully Added Billing Address
          </v-alert>

          <v-alert
            class="mt-3"
            v-model="defaultAlert"
            dismissible
            dense
            type="info"
          >
            Default Billing Address is Now Change
          </v-alert>

          <v-alert
            class="mt-3"
            v-model="updateAlert"
            dismissible
            dense
            type="info"
          >
            Successfully Updated Billing Address
          </v-alert>
          <v-card>
            <v-toolbar flat color="blue-grey lighten-1" dark>
              <v-toolbar-title>Billing Information</v-toolbar-title>
            </v-toolbar>
            <v-tabs v-model="tab">
              <v-tab href="#tab-1">
                <v-icon left> mdi-credit-card-multiple </v-icon>
                List of Credit Cards
              </v-tab>
              <v-tab href="#tab-2">
                <v-icon left> mdi-credit-card-edit </v-icon>
                Add(Update) Credit Card
              </v-tab>

              <v-tab-item value="tab-1">
                <v-card flat>
                  <v-card-text>
                    <v-data-table
                      :headers="headers"
                      :items="userPaymentList"
                      :items-per-page="10"
                    >
                      <template v-slot:item.default="{ item }">
                        <v-radio-group v-model="selected">
                          <v-radio :value="item.id" />
                        </v-radio-group>
                      </template>
                      <template v-slot:item.userBilling="{ item }">
                        {{ item.userBilling.userAddress.userAddressStreet1 }}
                        {{ item.userBilling.userAddress.userAddressStreet2 }},
                        {{ item.userBilling.userAddress.userAddressCity }},
                        {{ item.userBilling.userAddress.userAddressState }}
                      </template>
                      <template v-slot:item.actions="{ item }">
                        <v-btn
                          class="mr-2"
                          color="info"
                          fab
                          dark
                          x-small
                          elevation="2"
                          @click="updateCreditCard(item)"
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
                            toDeleteCreditCard = item;
                          "
                          ><v-icon>mdi-delete</v-icon></v-btn
                        >
                      </template>
                    </v-data-table>

                    <v-dialog v-model="deleteDialog" width="400">
                      <v-card>
                        <v-toolbar color="error" dark>
                          <div>Delete This Credit Card?</div>
                          <v-spacer></v-spacer>
                          <v-btn icon @click="deleteDialog = false">
                            <v-icon>mdi-close</v-icon>
                          </v-btn>
                        </v-toolbar>
                        <v-card-text class="pa-4">
                          <v-row dense>
                            <v-col cols="3" class="font-weight-bold">ID</v-col>
                            <v-col cols="7">{{ toDeleteCreditCard.id }}</v-col>
                            <v-col cols="3" class="font-weight-bold"
                              >Card Name</v-col
                            >
                            <v-col cols="7">{{
                              toDeleteCreditCard.cardName
                            }}</v-col>
                          </v-row>
                        </v-card-text>
                        <v-card-actions class="justify-end">
                          <v-btn
                            color="red"
                            dark
                            @click="removeCreditCard(toDeleteCreditCard)"
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
                    <v-form ref="billingForm" v-model="billingForm">
                      <v-row>
                        <v-col cols="3"></v-col>
                        <v-col cols="6">
                          <v-text-field
                            label="Card Name"
                            placeholder="Give a Name for Your Card"
                            v-model="cardName"
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="3"></v-col>
                        <v-col cols="6">
                          <div class="text-center mb-3">
                            <h2>Billing Address</h2>
                          </div>
                          <v-text-field
                            label="Name"
                            placeholder="Receiver Name"
                            v-model="userBillingName"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-text-field
                            label="Street Address 1"
                            placeholder="Enter Your Billing Address"
                            v-model="userBillingStreet1"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                          <v-text-field
                            label="Street Address 2"
                            hint="You don't have to fill if you done filled in Street 1"
                            v-model="userBillingStreet2"
                            outlined
                            dense
                          ></v-text-field>

                          <v-text-field
                            label="City"
                            placeholder="Billing City"
                            v-model="userBillingCity"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-select
                            :items="stateList"
                            v-model="userBillingState"
                            label="Choose State"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-select>

                          <v-text-field
                            label="Zipcode"
                            placeholder="Billing Zipcode"
                            v-model="userBillingZipcode"
                            outlined
                            dense
                            type="number"
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="6">
                          <div class="text-center mb-3">
                            <h2>Credit Card Information</h2>
                          </div>
                          <v-select
                            :items="cardTypeList"
                            v-model="cardType"
                            label="Select Card Type"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-select>

                          <v-text-field
                            label="Holder Name"
                            placeholder="Enter Card Holder Name"
                            v-model="holderName"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                          <v-text-field
                            label="Card Number"
                            placeholder="Valid Card Number"
                            v-model="cardNumber"
                            outlined
                            dense
                            type="number"
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-select
                            :items="expiryMonthList"
                            v-model="expiryMonth"
                            label="Choose Expiry Month"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-select>

                          <v-select
                            :items="expiryYearList"
                            v-model="expiryYear"
                            label="Choose Expiry Year"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-select>

                          <v-text-field
                            label="CV Code"
                            placeholder="Card CVC"
                            v-model="cvc"
                            outlined
                            dense
                            type="number"
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12">
                          <v-btn color="primary" @click="addNewCreditCard"
                            >Save all</v-btn
                          >
                          <v-btn color="error" @click="clearAll" class="ml-3"
                            >Clear all</v-btn
                          >
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-card-text>
                </v-card>
              </v-tab-item>
            </v-tabs>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import user_info_sidebar from "@/components/user_info_sidebar.vue";
import utils from "@/utils/utils";

export default {
  name: "user_billing",
  components: {
    user_info_sidebar,
  },
  data() {
    return {
      loginUser: {},
      tab: "tab-1",
      successAlert: false,
      updateAlert: false,
      defaultAlert: false,
      headers: [
        { text: "Default", value: "default", sortable: false },
        { text: "Credit Cards", value: "cardName", sortable: false },
        { text: "Billing Addresses", value: "userBilling", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      deleteDialog: false,
      toDeleteCreditCard: {},
      selected: 0,
      userPaymentList: [],

      billingForm: false,
      stateList: [],

      userBillingId: null,
      userBillingName: "",
      userBillingStreet1: "",
      userBillingStreet2: "",
      userBillingCity: "",
      userBillingState: "",
      userBillingZipcode: null,

      id: null,
      cardName: "",
      cardTypeList: ["Visa", "MasterCard", "Discover", "American Express"],
      cardType: "",
      holderName: "",
      cardNumber: null,
      expiryMonthList: [
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
      ],
      expiryMonth: "",
      expiryYearList: [
        "2022",
        "2023",
        "2024",
        "2025",
        "2026",
        "2027",
        "2028",
        "2029",
        "2030",
      ],
      expiryYear: "",
      cvc: null,
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    await this.listOfCreditCards();
  },
  methods: {
    async listOfCreditCards() {
      const resp = await utils.http.get(
        "/api/user/card/list?currentUserId=" + this.loginUser.id
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.stateList = data.stateList;
          this.userPaymentList = data.userPaymentMethodList;
          //   console.log(data)
          this.userPaymentList.forEach((item, index) => {
            if (item.defaultPayment == true) {
              this.selected = item.id;
            }
          });
        }
      }
    },
    async addNewCreditCard() {
      if (this.$refs.billingForm.validate()) {
        this.successAlert = false;
        this.updateAlert = false;
        this.defaultAlert = false;
        if (this.userBillingId == null) {
          const resp = await utils.http.post(
            "/api/user/card/add?currentUserId=" + this.loginUser.id,
            {
              userAddress: {
                userAddressId: this.userBillingId,
                userAddressName: this.userBillingName,
                userAddressStreet1: this.userBillingStreet1,
                userAddressStreet2: this.userBillingStreet2,
                userAddressCity: this.userBillingCity,
                userAddressState: this.userBillingState,
                userAddressZipcode: this.userBillingZipcode,
              },

              userPaymentMethod: {
                id: this.id,
                cardName: this.cardName,
                cardNumber: this.cardNumber,
                cardType: this.cardType,
                cvc: this.cvc,
                holderName: this.holderName,
                expiryMonth: this.expiryMonth,
                expiryYear: this.expiryYear,
              },
            }
          );
          if (resp.status === 200) {
            const data = await resp.json();
            if (data) {
              await this.listOfCreditCards();
            }
            if (this.id == null) {
              this.successAlert = true;
            }
          }
        } else {
          const resp = await utils.http.put(
            "/api/user/card/update?currentUserId=" + this.loginUser.id,
            {
              userAddress: {
                userAddressId: this.userBillingId,
                userAddressName: this.userBillingName,
                userAddressStreet1: this.userBillingStreet1,
                userAddressStreet2: this.userBillingStreet2,
                userAddressCity: this.userBillingCity,
                userAddressState: this.userBillingState,
                userAddressZipcode: this.userBillingZipcode,
              },

              userPaymentMethod: {
                id: this.id,
                cardName: this.cardName,
                cardNumber: this.cardNumber,
                cardType: this.cardType,
                cvc: this.cvc,
                holderName: this.holderName,
                expiryMonth: this.expiryMonth,
                expiryYear: this.expiryYear,
              },
            }
          );
          if (resp.status === 200) {
            const data = await resp.json();
            if (data) {
              await this.listOfCreditCards();
              this.updateAlert = true;
            }
          }
        }
        // console.log("Success")
        this.id = null;
        this.cardName = "";
        this.cardNumber = null;
        this.cardType = "";
        this.cvc = null;
        this.holderName = "";
        this.expiryMonth = "";
        this.expiryYear = "";

        this.userBillingId = null;
        this.userBillingName = "";
        this.userBillingStreet1 = "";
        this.userBillingStreet2 = "";
        this.userBillingCity = "";
        this.userBillingState = "";
        this.userBillingZipcode = null;
        this.tab = "tab-1";
      }
    },
    updateCreditCard(item) {
      this.successAlert = false;
      this.updateAlert = false;
      this.defaultAlert = false;
      //   console.log(item)
      this.id = item.id;
      this.cardName = item.cardName;
      this.cardNumber = item.cardNumber;
      this.cardType = item.cardType;
      this.cvc = item.cvc;
      this.holderName = item.holderName;
      this.expiryMonth = String(item.expiryMonth);
      this.expiryYear = String(item.expiryYear);

      this.userBillingId = item.userBilling.userAddress.userAddressId;
      this.userBillingName = item.userBilling.userAddress.userAddressName;
      this.userBillingStreet1 = item.userBilling.userAddress.userAddressStreet1;
      this.userBillingStreet2 = item.userBilling.userAddress.userAddressStreet2;
      this.userBillingCity = item.userBilling.userAddress.userAddressCity;
      this.userBillingState = item.userBilling.userAddress.userAddressState;
      this.userBillingZipcode = item.userBilling.userAddress.userAddressZipcode;

      this.tab = "tab-2";
    },
    async removeCreditCard(item) {
      this.successAlert = false;
      this.updateAlert = false;
      this.defaultAlert = false;
      const resp = await utils.http.del(
        "/api/user/card/remove?id=" +
          item.id +
          "&currentUserId=" +
          this.loginUser.id
      );
      if (resp.status === 200) {
        await this.listOfCreditCards();
        this.deleteDialog = false;
      }
    },
    async saveDefault() {
      this.successAlert = false;
      this.updateAlert = false;
      const resp = await utils.http.post(
        "/api/user/card/default?userPaymentId=" +
          this.selected +
          "&currentUserId=" +
          this.loginUser.id
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.userPaymentList = data;
        }
        this.defaultAlert = true;
      }
    },
    clearAll() {
      this.id = null;
      this.cardName = "";
      this.cardNumber = null;
      this.cardType = "";
      this.cvc = null;
      this.holderName = "";
      this.expiryMonth = "";
      this.expiryYear = "";

      this.userBillingId = null;
      this.userBillingName = "";
      this.userBillingStreet1 = "";
      this.userBillingStreet2 = "";
      this.userBillingCity = "";
      this.userBillingState = "";
      this.userBillingZipcode = null;
    },
  },
};
</script>

<style></style>
