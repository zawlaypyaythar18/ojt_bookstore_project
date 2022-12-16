<template>
  <div class="ma-5">
    <H1 class="ma-3">Book List</H1>

    <template>
      <v-data-table

      class="elevation-4"
        :headers="tableHeaders"
        :items="bookList"
        :items-per-page="5"
      
      ></v-data-table>
    </template>

  </div>

</template>

<script>

export default {
name:"HomeVue",

components: {},

data(){
  return{
    tableHeaders:
    [
          {
            text: 'ID',
            value: 'id',
            sortable: true,
          },
          
          {
            text: 'Title',
            value: 'title',
            sortable: true,
          },

          {
            text: 'Author',
            value: 'author',
            sortable: true,
          },


          {
            text: 'Category',
            value: 'category',
          },

         
          {
            text: 'Format',
            value: 'format',
            sortable: true,
          },

          {
            text: 'ISBN',
            value: 'isbn',
            sortable: true,
          },

        
          {
            text: 'Active',
            value: 'active',
          },

          {
            text: 'InStock Number',
            value: 'inStockNumber',
            sortable: true,
          },

        ],

    bookList: [
      
    ]

  };
},

methods:{},

}
</script>

<style>

</style>