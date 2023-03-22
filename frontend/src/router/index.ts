import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import { useSessionStore } from "@/stores/sessionStore";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/login",
      name: "login",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/RegisterView.vue"),
    },
    {
      path: "/create-listing",
      name: "create-listing",
      component: () => import("../views/CreateListingView.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/admin",
      name: "admin-panel",
      component: () => import("../views/AdminPanelView.vue"),
      meta: { requiresAuth: true, requiresRole: "ADMIN" },
    },
    {
      path: "/listing/:id",
      name: "listing-details",
      component: () => import("../views/ListingDetailsView.vue"),
    },
    {
      path: "/:pathMatch(.*)*",
      name: "not-found",
      component: () => import("../views/NotFoundView.vue"),
    },
  ],
});

router.beforeEach((to, from, next) => {
  const sessionStore = useSessionStore();
  if (to.meta.requiresAuth && !sessionStore.isAuthenticated) {
    next({ name: "login" });
  } else if (to.meta.requiresRole && sessionStore.getHighestRole() !== to.meta.requiresRole) {
    next({ name: "not-found" });
  } else {
    next();
  }
});

export default router;
