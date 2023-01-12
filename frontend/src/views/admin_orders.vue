<template>
  <div class="mt-6">
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>
      <v-col cols="10">
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
                <template v-slot:item.action="{ item }">
                    <v-btn
                      color="red"
                      fab
                      dark
                      x-small
                      elevation="2"
                      @click="
                        deleteDialog = true;
                        toDeleteOrder = item;
                      "
                    >
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </template>
              </v-data-table>
              <v-dialog v-model="deleteDialog" width="400">
                  <v-card>
                    <v-toolbar color="error" dark>
                      <div>Delete This Order?</div>
                      <v-spacer></v-spacer>
                      <v-btn icon @click="deleteDialog = false">
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                    </v-toolbar>
                    <v-card-text class="pa-4">
                      <v-row dense>
                        <v-col cols="4" class="font-weight-bold">Order ID</v-col>
                        <v-col cols="6">{{ toDeleteOrder.id }}</v-col>
                        <v-col cols="4" class="font-weight-bold">Shipping Date</v-col>
                        <v-col cols="6">{{ toDeleteOrder.shippingDate }}</v-col>
                      </v-row>
                    </v-card-text>
                    <v-card-actions class="justify-end">
                      <v-btn color="red" dark @click="deleteOrder()">Delete</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
            </v-stepper-content>

            <v-stepper-content step="2">
              <v-card flat>
                <v-row>
                  <v-col cols="6">
                      <v-card-title>Order Date - {{ orderDate }}</v-card-title>
                    </v-col>
                    <v-col cols="6">
                      <v-card-title
                        >Delivery Date - {{ deliveryDate }}</v-card-title
                      >
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
                  <h3>
                    Order Total :
                    <span style="font-size: 30px; color: rgb(221, 44, 34)"
                      >${{ orderTotal }}</span
                    >
                  </h3>
                </v-col>
              </v-row>

              <v-btn color="primary" @click="e1 = 1"> Go Back </v-btn>
            </v-stepper-content>
          </v-stepper-items>
        </v-stepper>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "@/components/admin_sidebar.vue";
import utils from "@/utils/utils";

export default {
  name: "admin_orders",
  components: {
    admin_sidebar,
  },
  data() {
    return {
      localDomain: utils.constant.localDomain,
      loginUser: {},
      orderList: [],
      e1: 1,
      headers: [
        { text: "Order Date", value: "orderDate", sortable: true },
        { text: "Order Number", value: "id", sortable: true },
        { text: "Order Status", value: "orderStatus", sortable: false },
        { text: "Order Total", value: "orderTotal", sortable: false },
        { text: "Username", value: "user.username", sortable: true },
        { text: "Email", value: "user.email", sortable: false },
        { text: "Action", value: "action", sortable: false },
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

      deleteDialog: false,
      toDeleteOrder: {
        id: null,
      },
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    await this.fetchOrderList();
  },
  methods: {
    async fetchOrderList() {
      const resp = await utils.http.get("/api/admin/user/orders");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          // console.log(data)
          this.orderList = data;
        }
      }
    },
    async showOrderDetail(item) {
      // console.log(item)
      const resp = await utils.http.get(
        "/api/admin/user/order/detail?orderId=" + item.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          // console.log(data);
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

    async deleteOrder() {
      const resp = await utils.http.del("/api/admin/user/order/delete?orderId=" + this.toDeleteOrder.id);
      if (resp && resp.status === 200) {
        this.deleteDialog = false;
        await this.fetchOrderList();
      }
    }
  },
};
</script>

<style scoped>
.hover-affect {
  text-decoration: none;
}
.hover-affect:hover {
  text-decoration: underline;
}
</style>
