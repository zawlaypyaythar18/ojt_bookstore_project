<template>
  <div class="mt-6">
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>
      <v-col cols="10">
        <v-form class="" ref="bookForm" v-model="bookForm">
          <v-row>
            <v-col cols="6">
              <v-text-field
                v-model="title"
                :counter="255"
                label="Title"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 255) ||
                    'Title must be less than 255 characters.',
                ]"
              >
              </v-text-field>

              <v-text-field
                v-model="author"
                :counter="255"
                label="Author"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 255) ||
                    'Title must be less than 255 characters.',
                ]"
              >
              </v-text-field>

              <v-text-field
                v-model="publisher"
                :counter="255"
                label="Publisher"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 255) ||
                    'Title must be less than 255 characters.',
                ]"
              >
              </v-text-field>

              <v-menu
                v-model="menu"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                min-width="auto"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="date"
                    label="Publication Date"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  v-model="date"
                  @input="menu = false"
                ></v-date-picker>
              </v-menu>

              <v-select
                label="Language"
                :items="items"
                v-model="language"
                :rules="[(v) => !!v || 'Required']"
              ></v-select>

              <v-select
                label="Genre"
                :items="categoryList"
                v-model="category"
                :rules="[(v) => !!v || 'Required']"
              ></v-select>

              <v-text-field
                label="Number Of Pages"
                v-model="numberOfPages"
                type="number"
                min="1"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-select
                label="Format"
                :items="formatList"
                v-model="format"
                :rules="[(v) => !!v || 'Required']"
              ></v-select>
            </v-col>
            <v-col cols="6">
              <v-text-field
                label="ISBN"
                v-model="isbn"
                type="number"
                min="0"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-text-field
                label="Shipping Weight(ounces)"
                v-model="shippingWeight"
                type="number"
                min="0"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-text-field
                label="List Price"
                v-model="listPrice"
                type="number"
                min="0"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-text-field
                label="Our Price"
                v-model="ourPrice"
                type="number"
                min="0"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-text-field
                label="Stock Number"
                v-model="inStockNumber"
                type="number"
                min="0"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-checkbox label="active" v-model="active"></v-checkbox>

              <v-textarea
                outlined
                label="Description"
                v-model="description"
              ></v-textarea>

              <v-file-input
                v-model="bookImage"
                label="BookImage"
                show-size
                prepend-icon="mdi-camera"
                placeholder="Choose Book Image"
                accept="image/png, image/jpeg"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    !v ||
                    v.size < 10000000 ||
                    'Image size should be less than 10 MB!',
                ]"
                @change="onChangeBook"
              ></v-file-input>

              <v-img
                v-if="bookPreviewPath != null"
                :src="bookPreviewPath"
                width="200"
                height="150"
                contain
              >
              </v-img>

              <v-btn
                :disabled="!bookForm"
                color="success"
                class="mt-4 mr-4"
                @click="addBook"
              >
                <span v-if="!loading">Save</span>
                <v-progress-circular
                  v-else
                  indeterminate
                  color="primary"
                ></v-progress-circular>
              </v-btn>

              <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                Add Book Failed!
              </v-alert>
            </v-col>
          </v-row>
        </v-form>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "@/components/admin_sidebar.vue";
import utils from "../utils/utils";

export default {
  name: "admin_add_book",
  components: { admin_sidebar },
  data() {
    return {
      bookForm: false,
      menu: false,
      items: ["English", "Spanish", "France"],
      categoryList: [
        "Management",
        "Fiction",
        "Horror",
        "Engineering",
        "Programming",
        "Arts and Literature",
      ],
      formatList: ["Hardcover", "Paperback"],
      loading: false,
      errorAlert: false,
      title: "",
      author: "",
      publisher: "",
      date: "",
      language: "",
      category: "",
      numberOfPages: "",
      format: "",
      isbn: "",
      shippingWeight: "",
      listPrice: "",
      ourPrice: "",
      inStockNumber: "",
      active: true,
      description:
        "",
      bookImage: null,
      bookPreviewPath: null,
    };
  },
  methods: {
    async addBook() {
      if (this.$refs.bookForm.validate()) {
        this.errorAlert = false;
        this.loading = true;
        let respBookImageData = null;
        const respBookImage = await utils.http.postMedia(
          "/api/admin/book/file/create",
          this.bookImage,
          this.bookImage.type
        );
        if (respBookImage.status === 200) {
          respBookImageData = await respBookImage.text();
        } else {
          this.errorAlert = true;
        }
        if (respBookImageData) {
          const respBook = await utils.http.post("/api/admin/book/add", {
            title: this.title,
            author: this.author,
            publisher: this.publisher,
            publicationDate: this.date,
            language: this.language,
            category: this.category,
            numberOfPages: this.numberOfPages,
            format: this.format,
            isbn: this.isbn,
            shippingWeight: this.shippingWeight,
            listPrice: this.listPrice,
            ourPrice: this.ourPrice,
            inStockNumber: this.inStockNumber,
            active: this.active,
            description: this.description,
            posterPath: respBookImageData,
          });
          if (respBook.status === 200) {
            this.$router.push({ path: "/admin" });
          } else {
            this.errorAlert = true;
          }
        }
        // console.log(this.title,this.author,this.publisher,
        // this.date,this.language,this.category,this.numberOfPages,
        // this.format,this.isbn,this.shippingWeight,this.listPrice,
        // this.ourPrice,this.inStockNumber,this.active,this.description,this.bookImage)
        this.loading = false;
      }
    },
    onChangeBook(book) {
      this.bookPreviewPath = URL.createObjectURL(book);
    },
  },
};
</script>

<style scoped></style>
