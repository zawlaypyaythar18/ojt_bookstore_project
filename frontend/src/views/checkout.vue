<template>
  <div>
    <v-container>
      <!-- <h2 class="text-center">Checkout</h2> -->
      <v-row>
        <v-col cols="4">
          <v-card class="pa-3">
            <!-- <v-card-actions>
                          <v-btn color="error" dark>Place Your Order</v-btn>
                      </v-card-actions> -->
            <v-card-subtitle class="text-h6 text--primary"
              >By placing your order, you agree to Champion's Bookstore
              <a href="#">privacy</a> notice and <a href="#">conditions</a> of
              use.</v-card-subtitle
            >
            <v-divider></v-divider>
            <h3 class="mt-3 text-center">Order Summary</h3>
            <v-row class="ml-3 mt-3">
              <v-col cols="6" class="text-center">
                <div>Total before tax :</div>
              </v-col>
              <v-col cols="6">
                <div>${{ grandTotal }}</div>
              </v-col>
            </v-row>
            <v-row class="ml-3 mt-3">
              <v-col cols="6" class="text-center">
                <div>Estimated tax :</div>
              </v-col>
              <v-col cols="6">
                <div>${{ estimatedTax }}</div>
              </v-col>
            </v-row>
            <v-row class="ml-3 mt-3">
              <v-col cols="6" style="color: darkred" class="text-center">
                <div>
                  <h3><b>Order Total :</b></h3>
                </div>
              </v-col>
              <v-col cols="6" style="color: darkred">
                <div>
                  <h3>
                    <b>${{ orderTotal }}</b>
                  </h3>
                </div>
              </v-col>
            </v-row>
            <v-card-subtitle
              >Shipping and handling haven't applied.</v-card-subtitle
            >
          </v-card>
        </v-col>
        <v-col cols="8">
          <v-stepper v-model="e1">
            <v-stepper-header>
              <v-stepper-step :complete="e1 > 1" step="1">
                Billing Information
              </v-stepper-step>

              <v-divider></v-divider>

              <v-stepper-step :complete="e1 > 2" step="2">
                Shipping Information
              </v-stepper-step>

              <v-divider></v-divider>

              <v-stepper-step step="3"> Order Details </v-stepper-step>
            </v-stepper-header>

            <v-stepper-items>
              <v-stepper-content step="1">
                <v-data-table
                  :headers="headers"
                  :items="userPaymentList"
                  :items-per-page="10"
                >
                  <template v-slot:item.userBillingAddress="{ item }">
                    {{ item.userBilling.userAddress.userAddressStreet1 }}
                    {{ item.userBilling.userAddress.userAddressStreet2 }},
                    {{ item.userBilling.userAddress.userAddressCity }},
                    {{ item.userBilling.userAddress.userAddressState }}
                  </template>
                  <template v-slot:item.actions="{ item }">
                    <v-btn
                      color="primary"
                      dense
                      small
                      @click="useThisBillingAddress(item)"
                      >Use This Address</v-btn
                    >
                  </template>
                </v-data-table>
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
                            v-model="billingAddressName"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-text-field
                            label="Street Address 1"
                            placeholder="Enter Your Billing Address"
                            v-model="billingAddressStreet1"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                          <v-text-field
                            label="Street Address 2"
                            hint="You don't have to fill if you done filled in Street 1"
                            v-model="billingAddressStreet2"
                            outlined
                            dense
                          ></v-text-field>

                          <v-text-field
                            label="City"
                            placeholder="Billing City"
                            v-model="billingAddressCity"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-select
                            :items="stateList"
                            v-model="billingAddressState"
                            label="Choose State"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-select>

                          <v-text-field
                            label="Zipcode"
                            placeholder="Billing Zipcode"
                            v-model="billingAddressZipcode"
                            outlined
                            dense
                            type="number"
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <!-- <v-btn color="primary" @click="addNewCreditCard">Save all</v-btn> -->
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
                      </v-row>
                    </v-form>
                  </v-card-text>
                </v-card>

                <v-btn color="primary" @click="stepOne"> Next </v-btn>

                <!-- <v-btn text>
                          Cancel
                          </v-btn> -->
              </v-stepper-content>

              <v-stepper-content step="2">
                <v-data-table
                  :headers="shippingHeaders"
                  :items="userShippingList"
                  :items-per-page="10"
                >
                  <template v-slot:item.userShippingAddress="{ item }">
                    {{ item.userAddress.userAddressStreet1 }}
                    {{ item.userAddress.userAddressStreet2 }},
                    {{ item.userAddress.userAddressCity }},
                    {{ item.userAddress.userAddressState }}
                  </template>
                  <template v-slot:item.actions="{ item }">
                    <v-btn
                      color="primary"
                      dense
                      small
                      @click="useThisShippingAddress(item)"
                      >Use This Address</v-btn
                    >
                  </template>
                </v-data-table>
                <v-card flat>
                  <v-card-text>
                    <v-form ref="shippingForm" v-model="shippingForm">
                      <v-row>
                        <v-col cols="3"></v-col>
                        <v-col cols="6">
                          <v-text-field
                            label="Name"
                            placeholder="Receiver Name"
                            v-model="shippingAddressName"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-text-field
                            label="Street Address 1"
                            placeholder="Enter Your Shipping Address"
                            v-model="shippingAddressStreet1"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>
                          <v-text-field
                            label="Street Address 2"
                            hint="You don't have to fill if you done filled in Street 1"
                            v-model="shippingAddressStreet2"
                            outlined
                            dense
                          ></v-text-field>

                          <v-text-field
                            label="City"
                            placeholder="Shipping City"
                            v-model="shippingAddressCity"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <v-select
                            :items="stateList"
                            v-model="shippingAddressState"
                            label="Choose State"
                            outlined
                            dense
                            :rules="[(v) => !!v || 'Required']"
                          ></v-select>

                          <v-text-field
                            label="Zipcode"
                            placeholder="Shipping Zipcode"
                            v-model="shippingAddressZipcode"
                            outlined
                            dense
                            type="number"
                            :rules="[(v) => !!v || 'Required']"
                          ></v-text-field>

                          <!-- <v-btn color="primary" @click="addNewShippingAddress">Save all</v-btn> -->
                        </v-col>
                        <v-col cols="3"></v-col>
                      </v-row>
                    </v-form>
                  </v-card-text>
                </v-card>

                <v-btn color="primary" @click="stepTwo"> Next </v-btn>

                <v-btn text color="info" @click="e1 = 1"> Go Back </v-btn>
              </v-stepper-content>

              <v-stepper-content step="3">
                <v-card flat :disabled="orderDisabled">
                  <v-card-title>Review Items and Shipping</v-card-title>
                  <v-card-text>
                    <h3>Choose Shipping Method</h3>
                    <v-radio-group v-model="shippingMethod">
                      <v-radio
                        value="groundShipping"
                        label="Ground Shipping (5 days)"
                      />
                      <v-radio
                        value="premiumShipping"
                        label="Premium Shipping (3 days)"
                      />
                    </v-radio-group>

                    <div>
                      <v-row class="mt-3">
                        <v-col cols="2">Book Image</v-col>
                        <v-col cols="4">Title</v-col>
                        <v-col cols="2">Price</v-col>
                        <v-col cols="2">Quantity</v-col>
                        <v-col cols="2">SubTotal</v-col>
                      </v-row>
                      <v-divider></v-divider>
                      <div v-for="cartItem in cartItemList" :key="cartItem.id">
                        <v-row class="mt-3">
                          <v-col cols="2">
                            <router-link
                              :to="'/book/details/' + cartItem.book.id"
                            >
                              <v-img
                                :src="localDomain + cartItem.book.posterPath"
                                width="80"
                                height="100"
                                contain
                              ></v-img>
                            </router-link>
                          </v-col>
                          <v-col cols="4">
                            <router-link
                              :to="'/book/details/' + cartItem.book.id"
                              class="text-decoration-none"
                            >
                              <h3>{{ cartItem.book.title }}</h3>
                            </router-link>
                            <p
                              v-if="cartItem.book.inStockNumber >= 10"
                              style="color: green"
                              class="mt-3"
                            >
                              In Stock
                            </p>
                            <p
                              v-if="
                                cartItem.book.inStockNumber < 10 &&
                                cartItem.book.inStockNumber > 0
                              "
                              class="mt-3"
                              style="color: darkred"
                            >
                              Only {{ cartItem.book.inStockNumber }} is left.
                            </p>
                            <p
                              v-if="cartItem.book.inStockNumber == 0"
                              style="color: red"
                              class="mt-3"
                            >
                              Product Unavaliable
                            </p>
                          </v-col>
                          <v-col cols="2">
                            ${{ cartItem.book.ourPrice }}
                          </v-col>
                          <v-col cols="2">
                            {{ cartItem.qty }}
                          </v-col>
                          <v-col cols="2"> ${{ cartItem.subTotal }} </v-col>
                        </v-row>
                        <v-divider></v-divider>
                      </div>
                      <v-row class="text-end mr-6 mt-6">
                        <v-col cols="12">
                          <h3>
                            Total Price ({{ cartItemList.length }} items):
                            <span
                              style="font-size: 30px; color: rgb(221, 44, 34)"
                              >${{ orderTotal }}</span
                            >
                          </h3>
                          Tax included.
                        </v-col>
                      </v-row>
                    </div>
                  </v-card-text>
                </v-card>

                <v-btn
                  color="error"
                  :disabled="cartItemList.length == 0"
                  @click="checkout"
                >
                  <span v-if="!loading">Place Your Order</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="primary"
                  ></v-progress-circular>
                </v-btn>

                <v-btn text color="info" @click="e1 = 2" :disabled="orderDisabled"> Go Back </v-btn>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog v-model="orderDialog" persistent width="1000">
      <v-card>
        <v-toolbar color="success" dark>
          <div>Thank you, Your Order has been Placed :Order Email is Send</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="goToHome">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-title
          >The estimated Delivery Date is
          {{ estimatedDeliveryDate }}.</v-card-title
        >
        <v-card-text class="pa-4">
          <h2>List Of CartItems</h2>
          <div>
            <v-row class="mt-3">
              <v-col cols="2">Book Image</v-col>
              <v-col cols="6">Title</v-col>
              <v-col cols="2">Price</v-col>
              <v-col cols="2">Quantity</v-col>
            </v-row>
            <v-divider></v-divider>
            <div v-for="cartItem in cartItemList" :key="cartItem.id">
              <v-row class="mt-3">
                <v-col cols="2">
                  <v-img
                    :src="localDomain + cartItem.book.posterPath"
                    width="80"
                    height="100"
                    contain
                  ></v-img>
                </v-col>
                <v-col cols="6">
                  <h3>{{ cartItem.book.title }}</h3>
                </v-col>
                <v-col cols="2"> ${{ cartItem.book.ourPrice }} </v-col>
                <v-col cols="2">
                  {{ cartItem.qty }}
                </v-col>
              </v-row>
              <v-divider></v-divider>
            </div>
          </div>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="success" dark @click="goToHome">Ok</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "checkout",
  data() {
    return {
      localDomain: utils.constant.localDomain,
      loginUser: {},
      shoppingCartId: this.$route.params.id,
      e1: 1,
      grandTotal: 0,
      estimatedTax: 0,
      orderTotal: 0,

      headers: [
        {
          text: "Avaliable Billing Address",
          value: "userBillingAddress",
          sortable: false,
        },
        { text: "Actions", value: "actions", sortable: false },
      ],
      billingForm: false,
      stateList: [],
      userPaymentList: [],

      billingAddressId: null,
      billingAddressName: "",
      billingAddressStreet1: "",
      billingAddressStreet2: "",
      billingAddressCity: "",
      billingAddressState: "",
      billingAddressZipcode: null,
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

      shippingHeaders: [
        {
          text: "Avaliable Shipping Address",
          value: "userShippingAddress",
          sortable: false,
        },
        { text: "Actions", value: "actions", sortable: false },
      ],
      shippingForm: false,
      stateList: [],
      userShippingList: [],

      shippingAddressId: null,
      shippingAddressName: "",
      shippingAddressStreet1: "",
      shippingAddressStreet2: "",
      shippingAddressCity: "",
      shippingAddressState: "",
      shippingAddressZipcode: null,

      shippingMethod: "groundShipping",
      cartItemList: [],

      loading: false,
      orderDialog: false,
      estimatedDeliveryDate: "",

      orderDisabled: false,
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    // const value = 100.3232;
    // console.log(value.toFixed(2))
    await this.getCheckoutList();
  },
  methods: {
    stepOne() {
      if (this.$refs.billingForm.validate()) {
        this.e1 = 2;
      }
    },
    stepTwo() {
      if (this.$refs.shippingForm.validate()) {
        this.e1 = 3;
      }
    },
    async getCheckoutList() {
      const resp = await utils.http.get(
        "/api/checkout?userCartId=" +
          this.shoppingCartId +
          "&userId=" +
          this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          //   console.log(data);
          this.userPaymentList = data.userPaymentMethodList;
          this.userShippingList = data.userShippingList;
          this.cartItemList = data.userCartItemList;

          this.stateList = data.stateList;
          this.grandTotal = data.userCart.grandTotal;
          const Tax = this.grandTotal * 0.06;
          this.estimatedTax = Tax.toFixed(2);
          const total = this.grandTotal + this.grandTotal * 0.06;
          this.orderTotal = total.toFixed(2);

          for (const list in data.userOrderAddressList) {
            // console.log(data.userOrderAddressList[list].shippingBill)
            if (data.userOrderAddressList[list].shippingBill == "shipping") {
              // console.log("user Shipping " + data.userOrderAddressList[list].addressStreet1)
              this.shippingAddressId =
                data.userOrderAddressList[list].addressId;
              this.shippingAddressName =
                data.userOrderAddressList[list].addressName;
              this.shippingAddressStreet1 =
                data.userOrderAddressList[list].addressStreet1;
              this.shippingAddressStreet2 =
                data.userOrderAddressList[list].addressStreet2;
              this.shippingAddressCity =
                data.userOrderAddressList[list].addressCity;
              this.shippingAddressState =
                data.userOrderAddressList[list].addressState;
              this.shippingAddressZipcode =
                data.userOrderAddressList[list].addressZipcode;
            } else {
              // console.log("user Billing " + data.userOrderAddressList[list].addressStreet1)
              this.billingAddressId = data.userOrderAddressList[list].addressId;
              this.billingAddressName =
                data.userOrderAddressList[list].addressName;
              this.billingAddressStreet1 =
                data.userOrderAddressList[list].addressStreet1;
              this.billingAddressStreet2 =
                data.userOrderAddressList[list].addressStreet2;
              this.billingAddressCity =
                data.userOrderAddressList[list].addressCity;
              this.billingAddressState =
                data.userOrderAddressList[list].addressState;
              this.billingAddressZipcode =
                data.userOrderAddressList[list].addressZipcode;

              this.cardName = data.userTransaction.cardName;
              this.cardType = data.userTransaction.cardType;
              this.holderName = data.userTransaction.holderName;
              this.cardNumber = data.userTransaction.cardNumber;
              this.expiryMonth = String(data.userTransaction.expiryMonth);
              this.expiryYear = String(data.userTransaction.expiryYear);
              this.cvc = data.userTransaction.cvc;
            }
          }
        }
      } else if (resp.status === 400) {
        this.$router.push({ path: "/shopping/cart" });
      }
    },
    async useThisBillingAddress(item) {
      //   console.log(item)
      const resp = await utils.http.get(
        "/api/payment/update?userPaymentId=" +
          item.id +
          "&userId=" +
          this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          //   console.log(data)
          this.billingAddressId = data.userOrderAddress.addressId;
          this.billingAddressName = data.userOrderAddress.addressName;
          this.billingAddressStreet1 = data.userOrderAddress.addressStreet1;
          this.billingAddressStreet2 = data.userOrderAddress.addressStreet2;
          this.billingAddressCity = data.userOrderAddress.addressCity;
          this.billingAddressState = data.userOrderAddress.addressState;
          this.billingAddressZipcode = data.userOrderAddress.addressZipcode;

          this.cardName = data.userTransaction.cardName;
          this.cardType = data.userTransaction.cardType;
          this.holderName = data.userTransaction.holderName;
          this.cardNumber = data.userTransaction.cardNumber;
          this.expiryMonth = String(data.userTransaction.expiryMonth);
          this.expiryYear = String(data.userTransaction.expiryYear);
          this.cvc = data.userTransaction.cvc;
        }
      }
    },
    async useThisShippingAddress(item) {
      // console.log(item)
      const resp = await utils.http.get(
        "/api/shipping/update?userShippingId=" +
          item.userShippingId +
          "&userId=" +
          this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          // console.log(data)
          this.shippingAddressId = data.addressId;
          this.shippingAddressName = data.addressName;
          this.shippingAddressStreet1 = data.addressStreet1;
          this.shippingAddressStreet2 = data.addressStreet2;
          this.shippingAddressCity = data.addressCity;
          this.shippingAddressState = data.addressState;
          this.shippingAddressZipcode = data.addressZipcode;
        }
      }
    },
    async checkout() {
      if (
        this.$refs.billingForm.validate() &&
        this.$refs.shippingForm.validate()
      ) {
        this.orderDisabled = true;
        this.loading = true;
        // console.log("checkout")
        const resp = await utils.http.post(
          "/api/checkout?shippingMethod=" +
            this.shippingMethod +
            "&userId=" +
            this.loginUser.id,
          {
            userOrderAddressList: [
              {
                addressId: this.shippingAddressId,
                addressName: this.shippingAddressName,
                addressStreet1: this.shippingAddressStreet1,
                addressStreet2: this.shippingAddressStreet2,
                addressCity: this.shippingAddressCity,
                addressState: this.shippingAddressState,
                addressZipcode: this.shippingAddressZipcode,
                shippingBill: "shipping",
              },
              {
                addressId: this.billingAddressId,
                addressName: this.billingAddressName,
                addressStreet1: this.billingAddressStreet1,
                addressStreet2: this.billingAddressStreet2,
                addressCity: this.billingAddressCity,
                addressState: this.billingAddressState,
                addressZipcode: this.billingAddressZipcode,
                shippingBill: "billing",
              },
            ],

            userTransaction: {
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
        if (resp && resp.status === 200) {
          this.orderDialog = true;
          const data = await resp.json();
          if (data) {
            //   console.log(data);
            this.estimatedDeliveryDate = data;
          }
        }
        this.loading = false;
      }
    },
    goToHome() {
      this.orderDialog = false;
      this.$router.push({ path: "/" });
    },
  },
};
</script>

<style></style>
