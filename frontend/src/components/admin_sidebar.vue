<template>
  <v-card class="mx-auto" permanent>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="text-h10 text-center">
          Admin Sidebar
        </v-list-item-title>
        <v-list-item-subtitle class="text-center">
          action
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-divider></v-divider>

    <v-list dense nav>
      <v-list-item
        v-for="item in bookItemList"
        :key="item.id"
        link
        @click="goToRoute(item.path)"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "admin_book_sidebar",
  data() {
    return {
      bookItemList: [
        {
          title: "Add Book",
          icon: "mdi-book-edit",
          path: "/admin/book/addBook",
        },
        { title: "Book List", icon: "mdi-book-multiple", path: "/admin" },
        {
          title: "User List",
          icon: "mdi-account-multiple-plus",
          path: "/admin/user/list",
        },
        {
          title: "Order List",
          icon: "mdi-format-list-bulleted-square",
          path: "/admin/orders",
        },
      ],
      right: null,
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

<style scoped></style>
