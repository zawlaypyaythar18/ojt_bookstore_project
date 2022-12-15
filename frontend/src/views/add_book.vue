<template>
  <div>
    <v-container>
        <v-row>
            <v-col cols="2">
                <admin_sidebar></admin_sidebar>
            </v-col>
            <v-col cols="10">
                <h2>Add Book</h2>
            </v-col>
        </v-row>
    </v-container>
  </div>
</template>

<script>
import admin_sidebar from '@/components/admin_sidebar.vue';

export default {
 name: "add_book",
 components: {
    admin_sidebar
 }
}
</script>

<style>

</style>