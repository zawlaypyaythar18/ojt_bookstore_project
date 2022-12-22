<template>
  <div class="mt-6">
    <v-row>
        <v-col cols="2">
            <admin_sidebar></admin_sidebar>
        </v-col>
        <v-col cols="10">
            <h2>Admin Orders</h2>
        </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from '@/components/admin_sidebar.vue';
export default {
    name: "admin_orders",
    components: {
        admin_sidebar,
    }

}
</script>

<style>

</style>