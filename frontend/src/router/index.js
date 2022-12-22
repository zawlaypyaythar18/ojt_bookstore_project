import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";

import home from "../views/Home.vue";
import login from "../views/login.vue";
import register from "../views/register.vue";
import newUser from "../views/newUser.vue"

import book_list from "../views/book_list.vue"

import admin from "../views/admin.vue"
import admin_profile from "../views/admin_profile.vue"
import user_list from "../views/user_list.vue"
import add_book from "../views/add_book.vue"
import admin_orders from "../views/admin_orders.vue"
import admin_book_details from "../views/admin_book_details.vue"

import user_profile from "../views/user_profile.vue"
import user_billing from "../views/user_billing.vue"
import user_shipping from "../views/user_shipping.vue"
import user_orders from "../views/user_orders.vue"

import shopping_cart from "../views/shopping_cart.vue"

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: home,
  },

  // **********Security
  {
    path: "/login",
    name: "login",
    component: login,
    meta: {
      regiLogi: true,
    }
  },
  {
    path: "/register",
    name: "register",
    component: register,
    meta: {
      regiLogi: true,
    }
  },
  {
    path: "/newUser",
    name: "newUser",
    component: newUser,
  },

  // ********** Book
  {
    path: "/book_list",
    name: "book_list",
    component: book_list,
  },


  // **********Admin
  {
    path: "/admin",
    name: "admin",
    component: admin,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    }
  },
  {
    path: "/admin/profile",
    name: "admin_profile",
    component: admin_profile,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    }
  },
  {
    path: "/admin/user/list",
    name: "user_list",
    component: user_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    }
  },
  {
    path: "/admin/book/details/:id",
    name: "admin_book_details",
    component: admin_book_details,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    }
  },
  {
    path: "/admin/book/addBook",
    name: "add_book",
    component: add_book,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    }
  },
  {
    path: "/admin/orders",
    name: "admin_orders",
    component: admin_orders,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    }
  },

  // *********User
  {
    path: "/user/profile",
    name: "user_profile",
    component: user_profile,
    meta: {
      requiresAuth: true,
      requiresUser: true,
    }
  },
  {
    path: "/user/billing",
    name: "user_billing",
    component: user_billing,
    meta: {
      requiresAuth: true,
      requiresUser: true,
    }
  },
  {
    path: "/user/shipping",
    name: "user_shipping",
    component: user_shipping,
    meta: {
      requiresAuth: true,
      requiresUser: true,
    }
  },
  {
    path: "/user/orders",
    name: "user_orders",
    component: user_orders,
    meta: {
      requiresAuth: true,
      requiresUser: true,
    }
  },

  // *******Shopping_Cart
  {
    path: "/shopping/cart",
    name: "shopping_cart",
    component: shopping_cart,
    meta: {
      requiresAuth: true,
      requiresUser: true,
    }
  },

];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  let loginUser = router.app.$store.getters.loginUser;
  let isLogin = router.app.$store.getters.isLogin;
  if (to.meta.requiresAuth == true && !isLogin) {
    next({ path: "/" });
  } else if (
    to.meta.requiresAuth == true &&
    to.meta.requiresAdmin == true &&
    loginUser.userRole != "admin"
  ) {
    next({ path: "/" });
  } else if (
    to.meta.requiresAuth == true &&
    to.meta.requiresUser == true &&
    loginUser.userRole != "user"
  ) {
    next({ path: "/" });
  } else if (to.meta.regiLogi == true && isLogin) {
    next({ path: "/" });
  } else {
    next();
  }

});

export default router;
