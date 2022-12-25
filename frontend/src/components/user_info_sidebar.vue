<template>
  <div>
    <v-card permanent>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h10">
            User Information
          </v-list-item-title>
          <v-list-item-subtitle>
            billing info and shipping details
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item
          v-for="action in userActionList"
          :key="action.id"
          link
          @click="goToRoute(action.path)"
        >
          <v-list-item-icon>
            <v-icon>{{ action.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ action.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "user_info_sidebar",
  data() {
    return {
      userActionList: [
        { title: "Profile", icon: "mdi-account-circle", path: "/user/profile" },
        {
          title: "Orders",
          icon: "mdi-clipboard-list-outline",
          path: "/user/orders",
        },
        { title: "Billing", icon: "mdi-cash", path: "/user/billing" },
        {
          title: "Shipping",
          icon: "mdi-truck-cargo-container",
          path: "/user/shipping",
        },
      ],
    };
  },
  methods: {
    goToRoute(path) {
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    },
  },
};
</script>

<style></style>
