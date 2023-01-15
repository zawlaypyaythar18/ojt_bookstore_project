<template>
  <div class="my-5 container">
    <v-alert dense text type="success" v-show="successAlert">
      Successfully Added <strong>"{{ book.title }}"</strong> To
      <router-link
        to="/shopping/cart"
        style="text-decoration: underline; color: green"
        >Shopping Cart</router-link
      >.
    </v-alert>
    <v-alert dense text type="error" v-show="errorAlert">
      Sorry, You have to Login First.
    </v-alert>
    <v-alert dense text type="warning" v-show="notEnoughStockAlert">
      Not Enough Stock!
    </v-alert>
    <v-btn @click="back" class="secondary">Back</v-btn>
    <v-row class="ma-0">
      <v-col cols="3" class="ma-0 pa-0">
        <v-img
          class="ma-0"
          :src="localDomain + book.posterPath"
          contain
        ></v-img>
      </v-col>
      <v-col cols="9">
        <v-row>
          <v-col cols="6" class="text-center">
            <div class="text-h4">{{ book.title }}</div>
            <div class="text-body-1 ml-2 my-3">Author : {{ book.author }}</div>
            <div class="text-body-1 ml-2 my-3">
              Publisher : {{ book.publisher }}
            </div>
            <div class="text-body-1 ml-2 my-3">
              Publication Date : {{ book.publicationDate }}
            </div>
            <div class="text-body-1 ml-2 my-3">
              Language : {{ book.language }}
            </div>
            <div class="text-body-1 ml-2 my-3">
              Category : {{ book.category }}
            </div>
            <div class="text-body-1 ml-2 my-3">
              {{ book.format }} : {{ book.numberOfPages }} pages
            </div>
            <div class="text-body-1 ml-2 my-3">ISBN : {{ book.isbn }}</div>
            <div class="text-body-1 ml-2 my-3">
              Shipping Weight : {{ book.shippingWeight }} ounces
            </div>
          </v-col>
          <v-col cols="6" class="text-center">
            <div class="text-body-1 ml-2 my-3 mt-12">
              <span style="font-size: 25px; font-weight: bold"
                >Our Price :
                <span style="color: rgb(221, 44, 34)"
                  >${{ book.ourPrice }}</span
                ></span
              >
            </div>
            <div class="text-body-1 ml-2 my-3">
              List Price :
              <span style="text-decoration: line-through"
                >${{ book.listPrice }}</span
              >
            </div>
            <div class="text-body-1 ml-2 my-3">You Save : ${{ saveMoney }}</div>
            <v-row>
              <v-col cols="4"></v-col>
              <v-col cols="4">
                <div class="text-body-1 ml-2 my-3">
                  <!-- <span>Qty : </span> -->
                  <v-select
                    label="Qty"
                    :items="items"
                    v-model="qty"
                    outlined
                    dense
                    class="select text-center"
                  ></v-select>
                </div>
              </v-col>
              <v-col cols="4"></v-col>
            </v-row>
            <div
              v-if="book.inStockNumber > 10"
              class="text-body-1 ml-2 my-3"
              style="color: green"
            >
              In Stock
            </div>
            <div
              v-if="book.inStockNumber <= 10 && book.inStockNumber > 0"
              class="text-body-1 ml-2 my-3"
              style="color: darkred"
            >
              Only {{ book.inStockNumber }} left
            </div>
            <div
              v-if="book.inStockNumber == 0"
              class="text-body-1 ml-2 my-3"
              style="color: red"
            >
              Unavailable
            </div>
            <v-btn
              color="amber lighten-2"
              elevation="3"
              @click="addToCart"
              :disabled="
                book.inStockNumber == 0 || loginUser.userRole == 'admin'
              "
              >Add To Cart</v-btn
            >
          </v-col>
          <v-col>
            <div class="text-body-1 ml-10 my-3">
              <h4 class="mb-3">Description :</h4>
              <p class="ms-5">{{ book.description }}</p>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "book_details",
  data() {
    return {
      localDomain: utils.constant.localDomain,
      book: {},
      id: this.$route.params.id,
      items: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      qty: 1,
      loginUser: {},
      isLogin: false,
      successAlert: false,
      errorAlert: false,
      notEnoughStockAlert: false,
      saveMoney: 0,
    };
  },
  async created() {
    this.loginUser = this.$store.state.loginUser;
    this.isLogin = this.$store.state.isLogin;
    await this.fetchBook();
  },
  methods: {
    async fetchBook() {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      const resp = await utils.http.get("/api/book/details/" + this.id);
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.book = data;
          const userSave = this.book.listPrice - this.book.ourPrice;
          this.saveMoney = userSave.toFixed(2);
        }
      }
    },
    async addToCart() {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      // console.log(this.book.id, " ",this.qty)
      if (this.isLogin) {
        const resp = await utils.http.post(
          "/api/cart/item/add?userId=" +
            this.loginUser.id +
            "&bookId=" +
            this.book.id +
            "&qty=" +
            this.qty
        );
        if (resp.status === 200) {
          this.successAlert = true;
          this.notEnoughStockAlert = false;
        } else {
          console.log("Stock is not Enough.");
          this.notEnoughStockAlert = true;
          this.successAlert = false;
        }
      } else {
        this.errorAlert = true;
      }
      window.scrollTo({ top: 0, behavior: "smooth" });
    },
    back() {
      this.$router.back();
    },
  },
};
</script>

<style scoped>
.select {
  min-width: 70px;
  /* margin-left: 160px; */
  height: 40px;
}
</style>
