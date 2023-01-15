<template>
  <div>
    <h1 class="text-center mt-4">Shopping Cart</h1>
    <v-container>
      <v-row class="mt-2 mb-3">
        <v-col cols="6">
          <v-btn color="warning" @click="goToRoute('/book/list')"
            >Continue Shopping</v-btn
          >
        </v-col>
        <v-col cols="6" class="text-end">
          <v-btn
            color="error"
            @click="checkout"
            :disabled="cartItemList.length == 0"
            >Checkout</v-btn
          >
        </v-col>
      </v-row>
      <v-alert class="mt-3 mx-1" v-show="qtyAlert" dense type="error">
        Not Enough Stock!
      </v-alert>
      <div v-if="cartItemList.length == 0">
        <h3 class="text-center">Your Shopping Cart is Empty.</h3>
        <div class="text-center mt-2">
          <v-icon x-large>mdi-emoticon-sad</v-icon>
        </div>
      </div>
      <div v-else>
        <v-row class="mt-3">
          <v-col cols="2">Book Image</v-col>
          <v-col cols="4">Title</v-col>
          <v-col cols="2">Price</v-col>
          <v-col cols="2">Quantity</v-col>
          <v-col cols="2">SubTotal</v-col>
        </v-row>
        <v-divider></v-divider>
        <div class="scroll-bar">
          <div v-for="cartItem in cartItemList" :key="cartItem.id">
            <v-row class="mt-3">
              <v-col cols="2">
                <router-link :to="'/book/details/' + cartItem.book.id">
                  <v-img
                    :src="localDomain + cartItem.book.posterPath"
                    width="80"
                    height="120"
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
                >
                  In Stock
                </p>
                <p
                  v-if="
                    cartItem.book.inStockNumber < 10 &&
                    cartItem.book.inStockNumber > 0
                  "
                  style="color: darkred"
                >
                  Only {{ cartItem.book.inStockNumber }} is left.
                </p>
                <p v-if="cartItem.book.inStockNumber == 0" style="color: red">
                  Product Unavaliable
                </p>
                <v-btn
                  color="error"
                  x-small
                  elevation="0"
                  @click="removeBook(cartItem.id)"
                  >Remove</v-btn
                >
              </v-col>
              <v-col cols="2"> ${{ cartItem.book.ourPrice }} </v-col>
              <v-col cols="2">
                <v-text-field
                  v-model="cartItem.qty"
                  type="number"
                  min="1"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) => !v || v.length > 0 || 'Qty has to be aleast 1 book!',
                  ]"
                  outlined
                  dense
                  class="my-qty-field"
                >
                </v-text-field>
              </v-col>
              <v-col cols="2"> ${{ cartItem.subTotal }} </v-col>
            </v-row>
            <v-divider></v-divider>
          </div>
        </div>
        <v-row class="text-end mr-6">
          <v-col cols="12">
            <h3>
              Total Price ({{ cartItemList.length }} items):
              <span style="font-size: 30px; color: rgb(221, 44, 34)"
                >${{ grandTotal }}</span
              >
            </h3>
          </v-col>
        </v-row>
      </div>
    </v-container>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "shopping_cart",
  data() {
    return {
      localDomain: utils.constant.localDomain,
      cartItemList: [],
      loginUser: {},
      grandTotal: "",
      shoppingCartId: null,
      // removeAlert: false,
      qtyAlert: false,
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    await this.getShoppingCart();
  },
  watch: {
    cartItemList: {
      handler: function (newVal, oldVal) {
        if (newVal) {
          this.updateCartItem();
          // this.grandTotal = newVal[0].shoppingCart.grandTotal;
        }
      },
      deep: true,
    },
  },
  methods: {
    async getShoppingCart() {
      const resp = await utils.http.get(
        "/api/cart/item/list?userId=" + this.loginUser.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.cartItemList = data;
          // console.log(this.cartItemList[0].userCart.id);
          // console.log(data)
          for (const list in data) {
            // this.grandTotal = this.cartItemList[list].userCart.grandTotal;
            // console.log(this.cartItemList[list].userCart.id)
            this.shoppingCartId = this.cartItemList[list].userCart.id;
            // console.log(this.shoppingCartId)
          }
        }
      }
    },
    async updateCartItem() {
      for (var cartItem in this.cartItemList) {
        // console.log(this.cartItemList[cartItem].qty)
        // console.log(this.cartItemList[cartItem].id)
        if (
          this.cartItemList[cartItem].qty <= 200 &&
          this.cartItemList[cartItem].qty >= 1
        ) {
          this.qtyAlert = false;
          const resp = await utils.http.put(
            "/api/cart/item/update?cartItemId=" +
              this.cartItemList[cartItem].id +
              "&qty=" +
              this.cartItemList[cartItem].qty
          );
          if (resp.status === 200) {
            // console.log("update successfully.");
            const data = await resp.json();
            if (data) {
              // console.log(this.cartItemList[cartItem].subTotal) 
              // console.log(data.shoppingCart.grandTotal);
              this.cartItemList[cartItem].subTotal = data.subTotal;
              this.grandTotal = data.userCart.grandTotal;
            }
          }
        } else {
          this.qtyAlert = true;
        }
      }
    },
    async removeBook(id) {
      // console.log(id);
      const resp = await utils.http.del(
        "/api/cart/item/remove?cartItemId=" + id
      );
      if (resp.status === 200) {
        await this.getShoppingCart();
      }
    },
    goToRoute(path) {
      this.$router.push({ path: path });
    },
    checkout() {
      if (this.qtyAlert == false) {
        this.$router.push({ path: "/checkout/" + this.shoppingCartId });
      }
      // console.log(this.shoppingCartId)
    },
  },
};
</script>

<style scoped>
.my-qty-field {
  /* padding-top: 20px;
  padding-left: 20px;
  padding-right: 20px; */
  width: 80px;
}
.scroll-bar {
  height: 500px;
  overflow: auto;
}
</style>
