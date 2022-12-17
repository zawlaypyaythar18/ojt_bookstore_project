<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="2">
          <admin_sidebar></admin_sidebar>
        </v-col>
        <v-col cols="10">
          <h2>Book List</h2>
          
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import admin_sidebar from '@/components/admin_sidebar.vue';
export default {
    name: "admin",
    components: {
      admin_sidebar,
    }
}
</script>

<style>

</style>