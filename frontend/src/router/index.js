import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "../views/Home.vue"
import Homeview from "../views/HomeView.vue"

Vue.use(VueRouter)

const routes = [

  {
    path: "/",
    name: "home",
    component: Home,
  },

  {
    path: "/homeview",
    name: "homeview",
    component: Homeview,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
