<template>
  <div>
    <v-container>
      <v-row class="mt-5">
        <v-col cols="4"></v-col>
        <v-col cols="4">
          <v-text-field
            v-model="inputTitle"
            placeholder="Search By Book Title"
            prepend-icon="mdi-magnify"
            rounded
            filled
          ></v-text-field>
        </v-col>
        <!-- <v-col cols="1">
              <v-btn
              class="mx-2"
              fab
              dark
              color="primary"
              @click="searchByTitle"
              >
              <v-icon dark>
                  mdi-magnify
              </v-icon>
              </v-btn>
          </v-col> -->
        <v-col cols="4"></v-col>
      </v-row>
      <v-row>
        <v-col cols="2 mt-5">
          <v-card class="mx-auto">
            <v-navigation-drawer permanent>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-h10">
                    Category
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
              <v-divider></v-divider>
              <v-list dense nav>
                <v-list-item
                  v-for="(cat, index) in categoryList"
                  :key="index"
                  link
                  @click="onClickCategory(cat)"
                >
                  <v-list-item-content>
                    <v-list-item-title>{{ cat }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-navigation-drawer>
          </v-card>
        </v-col>
        <v-col cols="10">
          <v-alert dense text type="success" v-show="successAlert">
            Successfully Added <strong>"{{ addedTitle }}"</strong> To Shopping
            Cart.
          </v-alert>
          <v-alert dense text type="error" v-show="errorAlert">
            Sorry, You have to Login First.
          </v-alert>
          <v-alert dense text type="warning" v-show="notEnoughStockAlert">
            Not Enough Stock!
          </v-alert>
          <v-alert dense text type="error" v-show="noBookAlert">
            Sorry, Book is not found
          </v-alert>
          <v-row>
            <v-col cols="4 mt-4" v-for="book in bookList" :key="book.id">
              <v-card class="hover-effect">
                <v-img
                  style="cursor: pointer"
                  height="250"
                  :src="localDomain + book.posterPath"
                  @click="goToBookDetails(book)"
                ></v-img>

                <v-card-title
                  ><router-link
                    :to="'/book/details/' + book.id"
                    class="text-decoration-none d-inline-block text-truncate"
                    style="max-width: 300px"
                    >{{ book.title }}</router-link
                  ></v-card-title
                >
                <v-card-text>
                  <v-row class="mx-0">
                    <div
                      class="my-2 grey--text d-inline-block text-truncate"
                      style="font-size: 15px; max-width: 300px"
                    >
                      author - {{ book.author }}
                    </div>
                  </v-row>

                  <div class="my-2 text-subtitle-1">
                    {{ book.publicationDate }}
                  </div>

                  <div class="my-2 text-subtitle-1">
                    <router-link :to="'/book/details/' + book.id">{{
                      book.format
                    }}</router-link>
                    <span style="font-size: 15px">
                      {{ book.numberOfPages }}</span
                    >
                  </div>

                  <!-- <div>{{ book.description }}</div> -->
                </v-card-text>

                <v-divider class="mx-4"></v-divider>

                <!-- <v-card-title>Price</v-card-title> -->
                <v-card-text
                  ><span style="font-size: 23px; color: rgb(221, 44, 34)"
                    >${{ book.ourPrice }}</span
                  >
                  <span style="font-size: 18px; text-decoration: line-through"
                    >${{ book.listPrice }}</span
                  ></v-card-text
                >

                <v-card-actions>
                  <v-btn
                    color="amber lighten-2"
                    elevation="3"
                    @click="addToCart(book.id, book.title)"
                    :disabled="
                      book.inStockNumber == 0 || loginUser.userRole == 'admin'
                    "
                  >
                    {{
                      book.inStockNumber == 0 ? "Unavailable" : "Add To Cart"
                    }}
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-col>
        <v-row>
          <v-col>
            <div class="text-center">
              <v-pagination
                v-model="page"
                :length="totalPages"
                @input="next"
              ></v-pagination>
            </div>
          </v-col>
        </v-row>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "book_list",
  data() {
    return {
      localDomain: utils.constant.localDomain,
      loginUser: {},
      bookList: [],
      inputTitle: "",
      categoryList: [
        "All Books",
        "Fiction",
        "Horror",
        "Management",
        "Engineering",
        "Programming",
        "Arts and Literature",
      ],
      successAlert: false,
      errorAlert: false,
      notEnoughStockAlert: false,
      loginUser: {},
      isLogin: false,
      qty: 1,
      addedTitle: "",
      page: 1,
      totalPages: 0,
      cat: "",

      noBookAlert: false,
    };
  },
  async created() {
    // await this.fetchBooks();
    await this.onClickCategory("All Books");
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
    this.isLogin = this.$store.state.isLogin;
  },
  watch: {
    // page: {
    //   handler: function (newVal, oldVal) {
    //     if (newVal) {
    //       this.fetchBooksByPage();
    //     }
    //   },
    //   deep: true,
    // },
    inputTitle: {
      handler: function (newVal, oldVal) {
        if (newVal) {
          this.searchByTitle();
          // this.grandTotal = newVal[0].shoppingCart.grandTotal;
        }
        if (!this.inputTitle) {
        this.onClickCategory("All Books");
      }
      },
      deep: true,
    },
  },
  methods: {
    async fetchBooks() {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      this.noBookAlert = false;
      const resp = await utils.http.get("/api/book/list");
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data;
          // console.log(data)
        }
      }
    },
    async fetchBooksByPage() {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      this.noBookAlert = false;
      const pageNumber = this.page - 1;
      const resp = await utils.http.get(
        "/api/book/page/search?noPage=" + pageNumber + "&count=" + 9
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          // console.log(data)
        }
      }
    },
    goToBookDetails(book) {
      this.$router.push({
        path: "/book/details/" + book.id,
      });
    },
    async onClickCategory(cat) {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      this.noBookAlert = false;
      const pageNumber = this.page - 1;
      this.cat = cat;
      const resp = await utils.http.get(
        "/api/book/category/search?category=" +
          cat +
          "&noPage=" +
          pageNumber +
          "&count=" +
          9
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data.content;
          this.totalPages = data.totalPages;
          // console.log(data)
        }
      } else if (resp.status === 400) {
        const resp = await utils.http.get(
          "/api/book/category/search?category=" +
            cat +
            "&noPage=" +
            0 +
            "&count=" +
            9
        );
        if (resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.bookList = data.content;
            this.totalPages = data.totalPages;
            // console.log(data)
          }
        }
      } else {
        this.noBookAlert = true;
        this.bookList = [];
      }
    },
    async searchByTitle() {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      this.noBookAlert = false;
      // console.log(this.inputTitle)

      const resp = await utils.http.get(
        "/api/book/title/search?title=" + this.inputTitle
      );
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data;
        }
      }
    },
    async next() {
      await this.onClickCategory(this.cat);
    },
    async addToCart(id, title) {
      this.successAlert = false;
      this.errorAlert = false;
      this.notEnoughStockAlert = false;
      this.noBookAlert = false;
      if (this.isLogin) {
        const resp = await utils.http.post(
          "/api/cart/item/add?userId=" +
            this.loginUser.id +
            "&bookId=" +
            id +
            "&qty=" +
            this.qty
        );
        if (resp.status === 200) {
          const data = await resp.json();
          if (data) {
            // console.log(data)
            this.successAlert = true;
            this.addedTitle = title;
          }
        } else {
          this.notEnoughStockAlert = true;
        }
      } else {
        this.errorAlert = true;
      }
    },
  },
};
</script>

<style scoped>
.hover-effect {
  transition: all 0.3s linear;
}
.hover-effect:hover {
  box-shadow: -1px 3px 5px 10px rgb(218, 216, 216);
}
</style>
