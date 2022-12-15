<template>
  <div>
    <v-container>
        <v-row>
            <v-col cols="3">
                <user_info_sidebar></user_info_sidebar>
            </v-col>
            <v-col cols="9">
                <h2>User Billing</h2>
            </v-col>
        </v-row>
    </v-container>
  </div>
</template>

<script>
import user_info_sidebar from '@/components/user_info_sidebar.vue';

export default {
    name: "user_billing",
    components: {
        user_info_sidebar
    }

}
</script>

<style>

</style>