<template>
  <div>
    <v-container>
    <v-row>
      <!-- Category Menu -->
      <v-col cols="2">
        <v-card class="mx-auto" >
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
              v-for="(cat,index) in categoryList"
              :key="index"
              link
              @click="onClickCategory(cat)"
            >
            <v-list-item-content>
              <v-list-item-title>{{ cat.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
          </v-navigation-drawer>
        </v-card>
      </v-col>

      <!-- Book List -->
      <v-col cols="10">
        <v-row >
          <v-col cols="3" v-for="(book, index) in bookList" :key="index">
            <v-card @click="goToBookDetails(book)" height="420">
              <v-card-text>
                <v-img
                  :src="localDomain + book.posterPath"
                  max-height="250"
                  cover
                ></v-img>
                <div class="text-h6">{{ book.title }}</div>
                <div class="text-body-1">{{ book.author }}</div>
                <div class="text-body-1">{{ book.publicationDate }}</div>
                <div class="text-body-1">{{ book.format }}&{{book.numberOfPages}}</div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    </v-container>
  </div>
  
</template>

<script>
import utils from "../utils/utils";
export default {
    name: "book_list",

    components: {},

    data() {
    return {
      localDomain: utils.constant.localDomain,
      categoryList: [
            { title: "Management", value:"management"},
            { title: "Fiction", value:"fiction"},
            { title: "Engineering", value:"engineering"},
            { title: "Programming", value:"programming"},
            { title: "Arts and Literature", value:"arts and literature"},
        ],
      bookList: [],
    };
  },
  
  async created() {
    await this.fetchBook();
  },

  methods:{
    async fetchBook() {
      const resp = await utils.http.get("/api/book/list");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data;
        }
      }
    },

    goToBookDetails(book) {
      this.$router.push({
        path: "/book_details/" + book.id,
      });
    },

    async onClickCategory(cat) {
      const resp = await utils.http.get("/api/category/search/" + cat.value);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data;
        }
      }
    },
    
  },
};
</script>

<style scoped>

</style>