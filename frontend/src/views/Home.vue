<template>
  <div class="ma-5">
    <v-row class="ma-0">
    <H1 class="ma-3">Book List</H1>
      <v-spacer></v-spacer>
        <v-btn icon @click="onClickCreateBtn()">
          <v-icon size="30">
            mdi-plus-box
          </v-icon>
        </v-btn>
    </v-row>

    <template>
      <v-data-table

      class="elevation-4"
        :headers="tableHeaders"
        :items="bookList"
        :items-per-page="5">

      <template v-slot:item.actions="{ item }">
       <!--Edit Book-->
       <v-btn class="mr-3" color="primary" fab x-small dark @click="onClickEditBtn(item)">
        <v-icon>mdi-pencil</v-icon>
       </v-btn>

       <!--Delete Book-->
       <v-btn color="red" fab x-small dark @click="deleteDialog =true;onClickDeleteBtn(item)">
        <v-icon>mdi-delete</v-icon>
       </v-btn>

      </template>

      </v-data-table>

       <!--Create Dialog-->
       <v-dialog v-model="createDialog" width="500">
        <v-card>
          <v-card-title >Create Book</v-card-title>
          <v-card-text>
            <v-form ref="createBookForm" v-model="createBookForm">
              <!--Title Text field -->
              <v-text-field  v-model="title"
                label="Title"
                placeholder="How To Make Fire"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Author Text field -->
              <v-text-field  v-model="author"
                label="Author"
                placeholder="PHYO"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Publisher Text field -->
              <v-text-field  v-model="publisher"
                label="Publisher"
                placeholder="Dream Home"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Publication Date Text field -->
              <v-text-field  v-model="publicationDate"
                label="Publication Date"
                placeholder="2022-12-18"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Language Text field -->
              <v-select  
                v-model="language"
                :items="['English', 'France']"
                label="Language"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

              <!--Category Text field -->
              <v-select  
                v-model="category"
                :items="['Fiction', 'Horror', 'Romance', 'Programming', 'Engineering']"
                label="Category"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

              <!--Number of Pages Text field -->
              <v-text-field  v-model="numberOfPages"
                label="Number of Pages"
                placeholder="78"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Format Text field -->
              <v-select  
                v-model="format"
                :items="['Hardcover', 'Paperback']"
                label="Format"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

              <!--Active Text field -->
              <v-select  
                v-model="active"
                :items="['True', 'False']"
                label="Active"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

               <!--ISBN Text field -->
              <v-text-field  v-model="isbn"
                label="ISBN"
                placeholder="1234567890123"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Shipping Weight Text field -->
              <v-text-field  v-model="shippingWeight"
                label="Shipping Weight"
                placeholder="12.5"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--List Price Text field -->
              <v-text-field  v-model="listPrice"
                label="List Price"
                placeholder="10"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

             <!--Our Price Text field -->
             <v-text-field  v-model="ourPrice"
               label="Our Price"
               placeholder="9.5"
               type="number"
               :rules="[(v) => !!v || 'required']" 
               required>
             </v-text-field>

              <!--Instock Number Text field -->
              <v-text-field  v-model="inStockNumber"
                label="Instock Number"
                placeholder="38"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Description Text field -->
              <v-text-field  v-model="description"
                label="Description"
                placeholder="vero eos et accusamus et iusto odio dignissimos ducimus qui"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Poster Path Text field -->
              <v-text-field  v-model="posterPath"
                label="Poster Path"
                placeholder="api/book/poster/path"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <v-btn 
                class="mt-5 width-100"
                color="success"
                 @click="createBook()" >
                 Create
              </v-btn>

            </v-form>
          </v-card-text>
        </v-card>
       </v-dialog>

       <!--Edit Dialog-->
       <v-dialog v-model="editDialog" width="500">
        <v-card>
          <v-card-title >Edit Book</v-card-title>
          <v-card-text>
            <v-form ref="editBookForm" v-model="editBookForm">
              <!--Title Text field -->
              <v-text-field  v-model="title"
                label="Title"
                placeholder="How To Make Fire"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Author Text field -->
              <v-text-field  v-model="author"
                label="Author"
                placeholder="PHYO"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Publisher Text field -->
              <v-text-field  v-model="publisher"
                label="Publisher"
                placeholder="Dream Home"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Publication Date Text field -->
              <v-text-field  v-model="publicationDate"
                label="Publication Date"
                placeholder="2022-12-18"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Language Text field -->
              <v-select  
                v-model="language"
                :items="['English', 'France']"
                label="Language"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

              <!--Category Text field -->
              <v-select  
                v-model="category"
                :items="['Fiction', 'Horror', 'Romance', 'Programming', 'Engineering']"
                label="Category"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

              <!--Number of Pages Text field -->
              <v-text-field  v-model="numberOfPages"
                label="Number of Pages"
                placeholder="78"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Format Text field -->
              <v-select  
                v-model="format"
                :items="['Hardcover', 'Paperback']"
                label="Format"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

              <!--Active Text field -->
              <v-select  
                v-model="active"
                :items="['True', 'False']"
                label="Active"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-select>

               <!--ISBN Text field -->
              <v-text-field  v-model="isbn"
                label="ISBN"
                placeholder="1234567890123"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Shipping Weight Text field -->
              <v-text-field  v-model="shippingWeight"
                label="Shipping Weight"
                placeholder="12.5"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--List Price Text field -->
              <v-text-field  v-model="listPrice"
                label="List Price"
                placeholder="10"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

             <!--Our Price Text field -->
             <v-text-field  v-model="ourPrice"
               label="Our Price"
               placeholder="9.5"
               type="number"
               :rules="[(v) => !!v || 'required']" 
               required>
             </v-text-field>

              <!--Instock Number Text field -->
              <v-text-field  v-model="inStockNumber"
                label="Instock Number"
                placeholder="38"
                type="number"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Description Text field -->
              <v-text-field  v-model="description"
                label="Description"
                placeholder="vero eos et accusamus et iusto odio dignissimos ducimus qui"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <!--Poster Path Text field -->
              <v-text-field  v-model="posterPath"
                label="Poster Path"
                placeholder="api/book/poster/path"
                :rules="[(v) => !!v || 'required']" 
                required>
              </v-text-field>

              <v-btn 
                class="mt-5 width-100"
                color="success"
                 @click="editBook()" >
                 Edit
              </v-btn>

            </v-form>
          </v-card-text>
        </v-card>
       </v-dialog>

      <!--Delete Dialog-->
      <v-dialog v-model="deleteDialog" width="500">
        <v-card>
          <v-card-title class>Delete Book</v-card-title>
          <v-card-text>
            You are going to delete this book
            <v-row>
              <v-col cols="4">ID :</v-col>
              <v-col cols="8">{{toDeleteBook.id}}</v-col>
              <v-col cols="4">title :</v-col>
              <v-col cols="8">{{toDeleteBook.title}}</v-col>
            </v-row>
          </v-card-text>
          
          <v-card-actions>
            <v-spacer></v-spacer>
           <v-btn @click="deleteDialog=false">Cancel</v-btn>
           <v-btn color="red" dark @click="deleteBook()">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </template>

  </div>

</template>

<script>

import utils from '@/utils/utils';

export default {
name:"Home",

components: {},

data(){
  return{
    tableHeaders:
    [
          {
            text: "ID",
            value: "id",
            sortable: true,
          },
          
          {
            text: "Title",
            value: "title",
            sortable: true,
          },

          {
            text: "Author",
            value: "author",
            sortable: true,
          },


          {
            text: "Category",
            value: "category",
          },

         
          {
            text: "Format",
            value: "format",
            sortable: true,
          },

          {
            text: "ISBN",
            value: "isbn",
            sortable: true,
          },

        
          {
            text: "Active",
            value: "active",
          },

          {
            text: "InStock Number",
            value: "inStockNumber",
            sortable: true,
          },

          {
            text: "Actions", 
            value: "actions",
            sortable: false,
          },
        ],

    bookList: [
    ],
    deleteDialog :false,  
    deleteBookId :null,  
    toDeleteBook :{}, 
    createDialog :false,
    createBookForm: false,
    title :"",
    author :"",
    publisher :"",
    publicationDate:"",
    language:"",
    category:"",
    numberOfPages:"",
    format:"",
    shippingWeight:"",
    listPrice:"",
    ourPrice:"",
    active:"",
    isbn:"",
    inStockNumber:"",
    description:"",
    posterPath:"",

    editDialog: false,
    editBookForm: false,
    toEditID:0,
  };
},

async created() {
  await this.fetchBookList();
},

methods:{

  async fetchBookList(){
    const resp = await utils.http.get("/api/book");
    console.log(resp.status)
    if(resp && resp.status ===200){
    const data = await resp.json();
    console.log(data)
    if(data){
     this.bookList = data;
    }
   }
  },
  onClickCreateBtn() {
    this.createDialog = true;
  },
  onClickEditBtn(item){
    this.toEditID=item.id;
    this.title=item.title;
    this.author=item.author;
    this.publisher=item.publisher;
    this.publicationDate=item.publicationDate;
    this.language=item.language;
    this.category=item.category;
    this.numberOfPages=item.numberOfPages;
    this.format=item.format;
    this.shippingWeight=item.shippingWeight;
    this.listPrice=item.listPrice;
    this.ourPrice=item.ourPrice;
    this.active=item.active;
    this.isbn=item.isbn;
    this.inStockNumber=item.inStockNumber;
    this.description=item.description;
    this.posterPath=item.posterPath;
    this.editDialog=true;
  },
  onClickDeleteBtn(item){
    this.deleteBookId =item.id;
    this.toDeleteBook =item;
  },
  async createBook(){
     if(this.$refs.createBookForm.validate()){
      const resp = await utils.http.post("/api/book/add",{

      title:this.title,
      author:this.author,
      publisher:this.publisher,
      publicationDate:this.publicationDate,
      language:this.language,
      category:this.category,
      numberOfPages:this.numberOfPages,
      format:this.format,
      shippingWeight:this.shippingWeight,
      listPrice:this.listPrice,
      ourPrice:this.ourPrice,
      active:this.active,
      isbn:this.isbn,
      inStockNumber:this.inStockNumber,
      description:this.description,
      posterPath:this.posterPath,
     }); 
     if(resp && resp.status ===200){
      this.title=" ";
      this.author=" ";
      this.publisher=" ";
      this.publicationDate=" ";
      this.language=" ";
      this.category=" ";
      this.numberOfPages=" ";
      this.format=" ";
      this.shippingWeight=" ";
      this.listPrice=" ";
      this.ourPrice=" ";
      this.active=" ";
      this.isbn=" ";
      this.instockNumber=" ";
      this.description=" ";
      this.posterPath=" ";
    await this.fetchBookList();
    this.createDialog = false;
    }
   }
  },

  async editBook(){
    if(this.$refs.editBookForm.validate()){
      const resp = await utils.http.put("/api/book/update",{
      id : this.toEditID,
      title:this.title,
      author:this.author,
      publisher:this.publisher,
      publicationDate:this.publicationDate,
      language:this.language,
      category:this.category,
      numberOfPages:this.numberOfPages,
      format:this.format,
      shippingWeight:this.shippingWeight,
      listPrice:this.listPrice,
      ourPrice:this.ourPrice,
      active:this.active,
      isbn:this.isbn,
      inStockNumber:this.inStockNumber,
      description:this.description,
      posterPath:this.posterPath,
     }); 
     if(resp && resp.status ===200){
      this.title=" ";
      this.author=" ";
      this.publisher=" ";
      this.publicationDate=" ";
      this.language=" ";
      this.category=" ";
      this.numberOfPages=" ";
      this.format=" ";
      this.shippingWeight=" ";
      this.listPrice=" ";
      this.ourPrice=" ";
      this.active=" ";
      this.isbn=" ";
      this.instockNumber=" ";
      this.description=" ";
      this.posterPath=" ";
    await this.fetchBookList();
    this.editDialog = false;
    }
   }
  },

  async deleteBook(){

    const resp = await utils.http.del("/api/book/delete/"+this.deleteBookId)
    if(resp && resp.status ===200){
    await this.fetchBookList();
    this.deleteDialog = false;
  } 
},
 
},
};
</script>

<style>

</style>