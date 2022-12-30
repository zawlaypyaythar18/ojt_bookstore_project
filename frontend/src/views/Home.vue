<template>
<<<<<<< HEAD
  <div class="bg-color">
    <v-container>
      <v-row class="mt-3">
        <v-col cols="1">
          <div>
            <v-img
              height="50"
              width="100"
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDbtVEylH6FvepaR87nITchzRDazbWzX9cFQ&usqp=CAU"
              style="border-radius: 50%"
              class="text-center"
            ></v-img>
          </div>
        </v-col>
        <v-col cols="11">
          <h2 class="text-center font-de" style="font-size: 40px">
            Welcome To Champion's Bookstore
          </h2>
        </v-col>
        <v-col
          class="col-lg-6 col-md-12 col-sm-12 col-xs-12"
          style="margin-top: 80px"
          v-if="!isLogin"
        >
          <p
            class="text-center font-play animate__heartBeat"
            style="font-size: 25px"
          >
            Get Your New Book With The Best Price
          </p>
          <div class="text-center" v-if="!isLogin">
            <v-btn class="ma-2" color="primary" @click="goToRoute('/register')">
              Register
            </v-btn>
          </div>
          <p
            class="text-center mt-2 font-play"
            style="font-size: 15px"
            v-if="!isLogin"
          >
            If you Already Register, You can Login
          </p>
          <div class="text-center" v-if="!isLogin">
            <v-btn class="ma-2" color="primary" @click="goToRoute('/login')">
              Login
            </v-btn>
          </div>
        </v-col>
        <v-col class="col-lg-6 col-md-12 col-sm-12 col-xs-12" v-if="!isLogin">
          <!-- <v-img
            src="https://www.stoltze.com/wp-content/uploads/2018/09/RISD_Viewbooks_Cover.jpg"
          ></v-img> -->
          <v-carousel
            cycle
            height="400"
            hide-delimiter-background
            show-arrows-on-hover
            class="mt-3"
          >
            <v-carousel-item
              v-for="(item, i) in items"
              :key="i"
              :src="item.src"
            ></v-carousel-item>
          </v-carousel>
        </v-col>
        <v-col class="col-lg-12 col-md-12 col-sm-12 col-xs-12" v-if="isLogin">
          <!-- <v-img
            src="https://www.stoltze.com/wp-content/uploads/2018/09/RISD_Viewbooks_Cover.jpg"
          ></v-img> -->
          <v-carousel
            cycle
            height="400"
            hide-delimiter-background
            show-arrows-on-hover
            class="mt-3"
          >
            <v-carousel-item
              v-for="(item, i) in items"
              :key="i"
              :src="item.src"
            ></v-carousel-item>
          </v-carousel>
        </v-col>
      </v-row>
    </v-container>
=======
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

>>>>>>> a02a108b4c17875e79c5edd1b8d86399a1c28e78
  </div>

</template>

<script>

import utils from '@/utils/utils';

export default {
<<<<<<< HEAD
  name: "home",
  components: {},
  data() {
    return {
      isLogin: false,
      items: [
        {
          src: "https://foodtank.com/wp-content/uploads/2021/07/alfons-morales-YLSwjSy7stw-unsplash.jpg",
        },
        {
          src: "https://images.adsttc.com/media/images/573c/90c0/e58e/ce1e/1600/0007/large_jpg/Here_is_a_theater_to_unfold_an_outstanding_drama__and_the_characters_are_book_lovers_sitting_on_the_soft_couch_or_standing_beside_the_bookshelves._0004.jpg?1463587001",
        },
        {
          src: "https://miro.medium.com/max/1400/1*HY09Xt_KMPnk1CAaSuHX5g.jpeg",
        },
        {
          src: "https://media.istockphoto.com/id/1217338527/photo/open-book-close-up-photo-blurred-background.jpg?s=612x612&w=0&k=20&c=5MNgrD_A1YesGnyk1k4UJlvwyGsIwd5hJmyaVmTsub8=",
        },
      ],
    };
  },
  created() {
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal, oldVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );
  },
  methods: {
    goToRoute(path) {
      this.$router.push({ path: path });
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Sevillana&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Playfair+Display:ital@1&display=swap");
@import "animate.css";
.bg-color {
  background-color: rgb(192, 192, 192);
}
.font-de {
  font-family: "Sevillana", cursive;
}
.font-play {
  font-family: "Playfair Display", serif;
}
</style>
=======
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
>>>>>>> a02a108b4c17875e79c5edd1b8d86399a1c28e78
