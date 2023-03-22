import { ref } from "vue";
import { defineStore } from "pinia";
import type { UserFull } from "@/service";

export const useSessionStore = defineStore("sessionStore", () => {
  const user = ref({} as UserFull);

  function isAuthenticated() {
    return true;
  }

  function getHighestRole() {
    return "USER";
  }

  return { isAuthenticated, getHighestRole };
});
