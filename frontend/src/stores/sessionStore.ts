import { computed, ref } from "vue";
import { defineStore } from "pinia";
import type { UserFull } from "@/service";
import { UserApi } from "@/service/apis/user-api";

export const useSessionStore = defineStore("sessionStore", () => {
  const user = ref(null as UserFull | null);
  const userApi = new UserApi();

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

  function authenticate(authentication: UserFull) {
    user.value = authentication;
    localStorage.setItem("user", JSON.stringify(authentication));
  }

  function timeout() {
    localStorage.removeItem("user");
    user.value = null;
  }

  function logOut() {
    userApi.logoutUser().then(() => {
      timeout();
    });
  }

  function getHighestRole() {
    // TODO: Admin will be defined in the future. For now return USER as default
    return user.value?.admin ? "ADMIN" : "USER";
  }

  return { isAuthenticated, getHighestRole, timeout, getUser, authenticate, logOut };
});
