import { computed, ref } from "vue";
import { defineStore } from "pinia";
import type { UserFull } from "@/service";

export const useSessionStore = defineStore("sessionStore", () => {
  const user = ref(null as UserFull | null);

  const isAuthenticated = computed(() => {
    return user.value == null;
  });

  function getUser() {
    return user.value;
  }

  function authenticate(authentication: UserFull) {
    user.value = authentication;
  }

  function logOut() {
    user.value = null;
  }

  function getHighestRole() {
    return "USER";
  }

  return { isAuthenticated, getHighestRole, logOut, getUser, authenticate };
});
