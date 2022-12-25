<template>
  <div class="mt-6">
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>
      <v-col cols="10">
        <template>
          <div>
            <v-data-table
              :headers="headers"
              :items="bookList"
              :items-per-page="5"
              item-key="id"
              class="elevation-2"
              :search="search"
            >
              <template v-slot:top>
                <v-text-field
                  v-model="search"
                  label="Search By Title"
                  class="mx-4"
                ></v-text-field>
              </template>
              <template v-slot:item.title="{ item }">
                <a @click="goToAdminBookDetails(item)">
                  {{ item.title }}
                </a>
              </template>
              <template v-slot:item.actions="{ item }">
                <v-btn
                  class="mr-2"
                  color="primary"
                  fab
                  x-small
                  elevation="2"
                  @click="onClickUpdateBook(item)"
                >
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-btn
                  color="red"
                  fab
                  dark
                  x-small
                  elevation="2"
                  @click="
                    deleteDialog = true;
                    toDeleteBook = item;
                  "
                >
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </template>
            </v-data-table>

            <v-dialog v-model="deleteDialog" width="400">
              <v-card>
                <v-toolbar color="error" dark>
                  <div>Delete This Book?</div>
                  <v-spacer></v-spacer>
                  <v-btn icon @click="deleteDialog = false">
                    <v-icon>mdi-close</v-icon>
                  </v-btn>
                </v-toolbar>
                <v-card-text class="pa-4">
                  <v-row dense>
                    <v-col cols="3" class="font-weight-bold">ID</v-col>
                    <v-col cols="7">{{ toDeleteBook.id }}</v-col>
                    <v-col cols="3" class="font-weight-bold">Title</v-col>
                    <v-col cols="7">{{ toDeleteBook.title }}</v-col>
                  </v-row>
                </v-card-text>
                <v-card-actions class="justify-end">
                  <v-btn color="red" dark @click="deleteBook()">Delete</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <v-dialog v-model="updateDialog" width="800">
              <v-card>
                <v-toolbar color="primary" dark>
                  <div>Update This Book?</div>
                  <v-spacer></v-spacer>
                  <v-btn
                    icon
                    @click="
                      updateDialog = false;
                      toUpdateBook.bookImage = null;
                    "
                  >
                    <v-icon>mdi-close</v-icon>
                  </v-btn>
                </v-toolbar>
                <v-card-text class="pa-4">
                  <v-form ref="bookForm" v-model="bookForm">
                    <v-row>
                      <v-col cols="6">
                        <v-text-field
                          v-model="toUpdateBook.id"
                          label="Id"
                          style="display: none"
                        >
                        </v-text-field>

                        <v-text-field
                          v-model="toUpdateBook.title"
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
                          v-model="toUpdateBook.author"
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
                          v-model="toUpdateBook.publisher"
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
                              v-model="toUpdateBook.date"
                              label="Publication Date"
                              prepend-icon="mdi-calendar"
                              readonly
                              v-bind="attrs"
                              v-on="on"
                            ></v-text-field>
                          </template>
                          <v-date-picker
                            v-model="toUpdateBook.date"
                            @input="menu = false"
                          ></v-date-picker>
                        </v-menu>

                        <v-select
                          label="Language"
                          :items="items"
                          v-model="toUpdateBook.language"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-select>

                        <v-select
                          label="Genre"
                          :items="categoryList"
                          v-model="toUpdateBook.category"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-select>

                        <v-text-field
                          label="Number Of Pages"
                          v-model="toUpdateBook.numberOfPages"
                          type="number"
                          min="1"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-text-field>

                        <v-select
                          label="Format"
                          :items="formatList"
                          v-model="toUpdateBook.format"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-select>
                      </v-col>

                      <v-col cols="6">
                        <v-text-field
                          label="ISBN"
                          v-model="toUpdateBook.isbn"
                          type="number"
                          min="0"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-text-field>

                        <v-text-field
                          label="Shipping Weight"
                          v-model="toUpdateBook.shippingWeight"
                          type="number"
                          min="0"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-text-field>

                        <v-text-field
                          label="List Price"
                          v-model="toUpdateBook.listPrice"
                          type="number"
                          min="0"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-text-field>

                        <v-text-field
                          label="Our Price"
                          v-model="toUpdateBook.ourPrice"
                          type="number"
                          min="0"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-text-field>

                        <v-text-field
                          label="Stock Number"
                          v-model="toUpdateBook.inStockNumber"
                          type="number"
                          min="0"
                          :rules="[(v) => !!v || 'Required']"
                        ></v-text-field>

                        <v-checkbox
                          label="active"
                          v-model="toUpdateBook.active"
                        ></v-checkbox>

                        <v-textarea
                          outlined
                          label="Description"
                          v-model="toUpdateBook.description"
                        ></v-textarea>

                        <v-file-input
                          v-model="toUpdateBook.bookImage"
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
                          v-if="bookPreviewPath == null"
                          :src="localDomain + toUpdateBook.posterPath"
                          width="200"
                          height="150"
                          contain
                        >
                        </v-img>

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
                          @click="updateBook"
                        >
                          <span v-if="!loading">Update</span>
                          <v-progress-circular
                            v-else
                            indeterminate
                            color="primary"
                          ></v-progress-circular>
                        </v-btn>

                        <v-alert
                          class="mt-3"
                          v-show="errorAlert"
                          dense
                          type="error"
                        >
                          Update Book Failed!
                        </v-alert>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-dialog>
          </div>
        </template>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import utils from "../utils/utils";
import admin_sidebar from "@/components/admin_sidebar.vue";

export default {
  name: "admin_book_list",
  components: { admin_sidebar },
  data() {
    return {
      localDomain: utils.constant.localDomain,
      bookList: [],
      menu: false,
      search: "",
      headers: [
        { text: "Title", value: "title", sortable: true },
        { text: "Author", value: "author", sortable: true },
        { text: "ISBN", value: "isbn", sortable: true },
        { text: "Active", value: "active", sortable: true },
        { text: "Number Of Stocks", value: "inStockNumber", sortable: true },
        { text: "Actions", value: "actions", sortable: false },
      ],
      deleteDialog: false,
      toDeleteBook: {
        id: null,
      },

      updateDialog: false,
      bookForm: false,
      items: ["English", "Spanish"],
      categoryList: [
        "Management",
        "Fiction",
        "Engineering",
        "Programming",
        "Arts and Literature",
      ],
      formatList: ["Hardcover", "Paperback"],
      toUpdateBook: {
        id: null,
        title: null,
        author: null,
        publisher: null,
        date: null,
        language: null,
        category: null,
        numberOfPages: null,
        format: null,
        isbn: null,
        shippingWeight: null,
        listPrice: null,
        ourPrice: null,
        inStockNumber: null,
        active: false,
        description: null,
        bookImage: null,
        posterPath: "",
      },
      loading: false,
      errorAlert: false,
      bookPreviewPath: null,

      multiDeleteAlert: false,
    };
  },
  async created() {
    await this.fetchBooks();
  },
  methods: {
    async fetchBooks() {
      const resp = await utils.http.get("/api/admin/book/list");
      if (resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.bookList = data;
          // console.log(data)
        }
      }
    },
    async deleteBook() {
      // this.isDeleted = true;
      const resp = await utils.http.del(
        "/api/admin/book/delete/" + this.toDeleteBook.id
      );
      if (resp.status === 200) {
        this.deleteDialog = false;
        // location.reload();
        await this.fetchBooks();
      } else {
        this.errorAlert = true;
      }
    },
    onClickUpdateBook(item) {
      this.updateDialog = true;
      this.toUpdateBook = Object.assign({}, item);
      this.toUpdateBook.date = item.publicationDate;
      this.toUpdateBook.bookImage = null;
      this.bookPreviewPath = null;
    },

    async updateBook() {
      if (this.$refs.bookForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        let bookImagePath = this.toUpdateBook.posterPath;

        if (this.toUpdateBook.bookImage != null) {
          const respBookImage = await utils.http.putMedia(
            "/api/admin/book/file/update",
            this.toUpdateBook.bookImage,
            this.toUpdateBook.bookImage.type,
            this.toUpdateBook.posterPath
          );
          if (respBookImage.status === 200) {
            bookImagePath = await respBookImage.text();
          } else {
            console.debug("Book Image Update Failed");
          }
        }

        const respBook = await utils.http.put("/api/admin/book/update", {
          id: this.toUpdateBook.id,
          title: this.toUpdateBook.title,
          author: this.toUpdateBook.author,
          publisher: this.toUpdateBook.publisher,
          publicationDate: this.toUpdateBook.date,
          language: this.toUpdateBook.language,
          category: this.toUpdateBook.category,
          numberOfPages: this.toUpdateBook.numberOfPages,
          format: this.toUpdateBook.format,
          isbn: this.toUpdateBook.isbn,
          shippingWeight: this.toUpdateBook.shippingWeight,
          listPrice: this.toUpdateBook.listPrice,
          ourPrice: this.toUpdateBook.ourPrice,
          inStockNumber: this.toUpdateBook.inStockNumber,
          active: this.toUpdateBook.active,
          description: this.toUpdateBook.description,
          posterPath: bookImagePath,
        });
        if (respBook.status === 200) {
          this.toUpdateBook.bookImage = null;
          this.updateDialog = false;
          await this.fetchBooks();
        } else {
          this.errorAlert = true;
        }

        this.loading = false;
      }
    },
    goToAdminBookDetails(book) {
      this.$router.push({
        path: "/admin/book/details/" + book.id,
      });
    },
    onChangeBook(book) {
      this.bookPreviewPath = URL.createObjectURL(book);
    },
  },
};
</script>

<style scoped></style>
