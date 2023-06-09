import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import { useSessionStore } from "@/stores/sessionStore";
import { UserApi } from "@/services/index";

let startup = true;
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
      path: "/chats",
      name: "chats",
      meta: { requiresAuth: true },
      component: () => import("../views/ChatsListView.vue"),
    },
    {
      path: "/chats/:chatId",
      name: "chat",
      meta: { requiresAuth: true },
      component: () => import("../views/ChatsListView.vue"),
    },
    {
      path: "/my-page",
      name: "my-page",
      meta: { requiresAuth: true },
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/my-page/MyPageView.vue"),
      children: [
        {
          path: "",
          name: "my-profile",
          component: () => import("../views/my-page/MyPageUserView.vue"),
        },
        {
          path: "listings",
          name: "my-listings",
          component: () => import("../views/my-page/MyPageListingsView.vue"),
        },
        {
          path: "bookmarked",
          name: "my-bookmarked-listings",
          component: () => import("../views/my-page/MyPageListingsView.vue"),
        },
      ],
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
      path: "/edit-listing/:id",
      name: "edit-listing",
      component: () => import("../views/EditListingpage.vue"),
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
  const userApi = new UserApi();
  if (startup) {
    userApi.getCurrentUser().then((data) => {
      if (data.status == 200) {
        sessionStore.authenticate(data.data);
      }
    });
    startup = false;
  }
  if (to.meta.requiresAuth && !sessionStore.isAuthenticated) {
    next({ name: "login" });
  } else if (to.meta.requiresRole && sessionStore.getHighestRole() !== to.meta.requiresRole) {
    next({ name: "not-found" });
  } else {
    next();
  }
});

export default router;
