<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="3">
          <user_info_sidebar></user_info_sidebar>
        </v-col>
        <v-col cols="9">
          <!-- <h2 class="text-center">Order Information</h2> -->
          <v-stepper v-model="e1">
            <v-stepper-items>
              <v-stepper-content step="1">
                <v-data-table :headers="headers" :items="orderList">
                  <template v-slot:item.orderDate="{ item }">
                    <a @click="showOrderDetail(item)" class="hover-affect">{{
                      item.orderDate
                    }}</a>
                  </template>
                  <template v-slot:item.orderTotal="{ item }">
                    ${{ item.orderTotal }}
                  </template>
                </v-data-table>
              </v-stepper-content>

              <v-stepper-content step="2">
                <v-card flat>
                  <v-row>
                    <v-col cols="6">
                      <v-card-title>Order Date - {{ orderDate }}</v-card-title>
                      <v-divider></v-divider>
                    </v-col>
                    <v-col cols="6">
                      <v-card-title
                        >Delivery Date - {{ deliveryDate }}</v-card-title
                      >
                      <v-divider></v-divider>
                    </v-col>
                    <v-col cols="4">
                      <v-card-title>Shipping Address</v-card-title>
                      <v-divider></v-divider>
                      <v-card-text
                        >Name - {{ shippingAddress.addressName }}</v-card-text
                      >
                      <v-card-text
                        >Street - {{ shippingAddress.addressStreet1 }}
                        {{ shippingAddress.addressStreet2 }}</v-card-text
                      >
                      <v-card-text
                        >City - {{ shippingAddress.addressCity }}</v-card-text
                      >
                      <v-card-text
                        >State - {{ shippingAddress.addressState }}</v-card-text
                      >
                      <v-card-text
                        >Zipcode -
                        {{ shippingAddress.addressZipcode }}</v-card-text
                      >
                    </v-col>
                    <v-col cols="4">
                      <v-card-title>Payment</v-card-title>
                      <v-divider></v-divider>
                      <v-card-text
                        >Card Name - {{ payment.cardName }}</v-card-text
                      >
                      <v-card-text
                        >Holder Name - {{ payment.holderName }}</v-card-text
                      >
                      <v-card-text
                        >Card Type - {{ payment.cardType }}</v-card-text
                      >
                      <!-- <v-card-text>Card Number - {{ payment.cardNumber }}</v-card-text> -->
                      <v-card-text
                        >Expiry Month - {{ payment.expiryMonth }}</v-card-text
                      >
                      <v-card-text
                        >Expiry Year - {{ payment.expiryYear }}</v-card-text
                      >
                      <!-- <v-card-text>CVC - {{ payment.cvc }}</v-card-text> -->
                    </v-col>
                    <v-col cols="4">
                      <v-card-title>Billing Address</v-card-title>
                      <v-divider></v-divider>
                      <v-card-text
                        >Name - {{ billingAddress.addressName }}</v-card-text
                      >
                      <v-card-text
                        >Street - {{ billingAddress.addressStreet1 }}
                        {{ billingAddress.addressStreet2 }}</v-card-text
                      >
                      <v-card-text
                        >City - {{ billingAddress.addressCity }}</v-card-text
                      >
                      <v-card-text
                        >State - {{ billingAddress.addressState }}</v-card-text
                      >
                      <v-card-text
                        >Zipcode -
                        {{ billingAddress.addressZipcode }}</v-card-text
                      >
                    </v-col>
                  </v-row>
                </v-card>
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
                      <router-link :to="'/book/details/' + cartItem.book.id">
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
                    </v-col>
                    <v-col cols="2"> ${{ cartItem.book.ourPrice }} </v-col>
                    <v-col cols="2">
                      {{ cartItem.qty }}
                    </v-col>
                    <v-col cols="2"> ${{ cartItem.subTotal }} </v-col>
                  </v-row>
                  <v-divider></v-divider>
                </div>
                <v-row class="text-end mr-6 mt-3">
                  <v-col cols="12">
                    <h5 class="mb-3">
                      Total Price ({{ cartItemList.length }} items):
                      <span style="font-size: 20px; color: rgb(221, 44, 34)"
                        >${{ grandTotal }}</span
                      >
                    </h5>
                    <h5 class="mb-3">
                      Estimated Tax :
                      <span style="font-size: 20px; color: rgb(221, 44, 34)"
                        >${{ estimatedTax }}</span
                      >
                    </h5>
                    <h4>
                      Order Total :
                      <span style="font-size: 25px; color: rgb(221, 44, 34)"
                        >${{ orderTotal }}</span
                      >
                    </h4>
                  </v-col>
                </v-row>

                <v-btn color="primary" @click="e1 = 1"> Go Back </v-btn>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import user_info_sidebar from "@/components/user_info_sidebar.vue";
import utils from "@/utils/utils";

export default {
  name: "orders",
  components: {
    user_info_sidebar,
  },
  data() {
    return {
      localDomain: utils.constant.localDomain,
      loginUser: {},
      orderList: [],
      e1: 1,
      headers: [
        { text: "Order Date", value: "orderDate", sortable: false },
        { text: "Order Number", value: "id", sortable: false },
        { text: "Order Status", value: "orderStatus", sortable: false },
        { text: "Order Total", value: "orderTotal", sortable: false },
      ],
      shippingAddress: {},
      billingAddress: {},
      payment: {},
      cartItemList: [],
      grandTotal: 0,
      estimatedTax: 0,
      orderTotal: 0,

      orderDate: "",
      deliveryDate: "",
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    await this.fetchOrderList();
  },
  methods: {
    async fetchOrderList() {
      const resp = await utils.http.get(
        "/api/user/order/list?userId=" + this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.orderList = data;
        }
      }
    },
    async showOrderDetail(item) {
      // console.log(item)
      const resp = await utils.http.get(
        "/api/user/order/detail?orderId=" +
          item.id +
          "&userId=" +
          this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          // console.log(data)
          this.cartItemList = data.userCartItemList;
          for (const list in data.userOrderAddressList) {
            if (data.userOrderAddressList[list].shippingBill == "shipping") {
              this.shippingAddress = data.userOrderAddressList[list];
              // console.log(data.userOrderAddressList[list])
            } else {
              this.billingAddress = data.userOrderAddressList[list];
            }
          }
          this.payment = data.userTransaction;
          this.grandTotal = data.order.orderTotal;
          const Tax = this.grandTotal * 0.06;
          this.estimatedTax = Tax.toFixed(2);
          const total = this.grandTotal + this.grandTotal * 0.06;
          this.orderTotal = total.toFixed(2);

          this.orderDate = data.order.orderDate;
          this.deliveryDate = data.order.shippingDate;
          this.e1 = 2;
        }
      }
    },
  },
};
</script>

<style scoped>
.hover-affect:hover {
  text-decoration: underline;
}
</style>
