<template>
  <div>
    <div class="my-5 container">
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
              <div class="text-body-1 ml-2 my-3">Publisher : {{ book.publisher }}</div>
              <div class="text-body-1 ml-2 my-3">Publication Date : {{ book.publicationDate }}</div>
              <div class="text-body-1 ml-2 my-3">Language : {{ book.language }}</div>
              <div class="text-body-1 ml-2 my-3">Category : {{ book.category }}</div>
              <div class="text-body-1 ml-2 my-3">{{ book.format }} : {{ book.numberOfPages }} pages</div>
              <div class="text-body-1 ml-2 my-3">ISBN : {{ book.isbn }}</div>
            </v-col>
            <v-col cols="6" class="text-center">
                <div class="text-body-1 ml-2 my-3 mt-16" style="color: black;font-size: 18px;">Shipping Weight: {{ book.shippingWeight }} ounces</div>
                <div class="text-body-1 ml-2 my-3" style="color: black;font-size: 18px;">List Price: ${{ book.listPrice }}</div>
                <div class="text-body-1 ml-2 my-3" style="color: black;font-size: 18px;">Our Price: ${{ book.ourPrice }}</div>
                <div class="text-body-1 ml-2 my-3" style="color: black;font-size: 18px;">Active?: {{ book.active }}</div>
                <div class="text-body-1 ml-2 my-3" style="color: black;font-size: 18px;">Number of Stocks: {{ book.inStockNumber }}</div>
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
  </div>
</template>

<script>
import utils from '@/utils/utils';
export default {
    name: "admin_book_details",
    data() {
        return {
            localDomain: utils.constant.localDomain,
            id: this.$route.params.id,
            book: {},
        }
    },
    async created() {
        await this.fetchBook();  
    },
    methods: {
        async fetchBook() {
            const resp = await utils.http.get("/api/admin/book/details/" + this.id);
            if (resp.status === 200) {
                const data = await resp.json();
                if (data) {
                    this.book = data;
                }
            }
        },
    },
    
}
</script>

<style scoped>
</style>