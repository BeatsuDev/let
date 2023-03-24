import { computed, ref } from "vue";
import { defineStore } from "pinia";
import type { UserFull } from "@/service";
import { UserApi } from "@/service/apis/user-api";
import router from "@/router";

export const useSessionStore = defineStore("sessionStore", () => {
  const user = ref(null as UserFull | null);
  const EXPIRY_TIME = 4 * 60 * 1000;
  const userApi = new UserApi();
  let id = 0 as number;

  const isAuthenticated = computed(() => {
    if (user.value == null) {
      const userFromStorage = localStorage.getItem("user");
      if (userFromStorage != null) {
        user.value = JSON.parse(userFromStorage);
      }
    }
    return user.value != null;
  });

  function getUser() {
    return user.value;
  }

  function refreshNotification() {
    if (!isAuthenticated.value) {
      return;
    }
    if (id !== null) {
      clearTimeout(id);
    }
    id = setTimeout(() => {
      if (confirm("Er du der? Trykk ok for å forbli logget inn")) {
        userApi
          .renewSession()
          .then(() => {
            refreshNotification();
          })
          .catch(() => {
            timeout();
            router.push("/login");
          });
        return;
      } else {
        logOut();
      }
    }, EXPIRY_TIME);
  }

  function authenticate(authentication: UserFull) {
    user.value = authentication;
    localStorage.setItem("user", JSON.stringify(authentication));
  }

  function timeout() {
    localStorage.removeItem("user");
    clearTimeout(id);
    user.value = null;
  }

  function logOut() {
    userApi.logoutUser().then(() => {
      timeout();
    });
  }

  function getHighestRole() {
    return user.value!.admin ? "ADMIN" : "USER";
  }

  return {
    isAuthenticated,
    getHighestRole,
    timeout,
    getUser,
    authenticate,
    logOut,
    refreshNotification,
  };
});
