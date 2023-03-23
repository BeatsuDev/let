import { computed, ref } from "vue";
import { defineStore } from "pinia";
import type { UserFull } from "@/service";
import { UserApi } from "@/service/apis/user-api";

export const useSessionStore = defineStore("sessionStore", () => {
  const user = ref(null as UserFull | null);
  const userApi = new UserApi();

  function getUser() {
    return user.value;
  }

  function fetchUser() {
    return userApi.getCurrentUser()
      .then((response) => {
        user.value = response.data;
      })
      .catch(() => {
        timeout();
      });
  }

  function authenticate(authentication: UserFull) {
    userApi.loginUser(authentication)
      .then(() => {
        user.value = authentication;
        localStorage.setItem("user", JSON.stringify(authentication));
      });
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
    return user.value!.admin ? "ADMIN" : "USER";
  }

  return { getHighestRole, timeout, getUser, fetchUser, authenticate, logOut };
});
