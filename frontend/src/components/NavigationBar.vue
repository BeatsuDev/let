<script setup lang="ts">
import { RouterLink } from "vue-router";
import AdminIcon from "@/components/icons/AdminIcon.vue";
import ChatIcon from "@/components/icons/ChatIcon.vue";
import CreateListingIcon from "@/components/icons/CreateListingIcon.vue";
import ProfileIcon from "@/components/icons/ProfileIcon.vue";
import {useSessionStore} from "@/stores/sessionStore";
import {computed} from "vue";

// eslint-disable-next-line no-undef
const sessionStore = useSessionStore();
const isAuthenticated = computed(() => sessionStore.isAuthenticated());
const highestRole = computed(() => sessionStore.getHighestRole());
</script>

<template>
  <header>
    <RouterLink id="navbar-logo" to="/">letno</RouterLink>
    <nav>
      <RouterLink v-if="isAuthenticated && highestRole === 'ADMIN'" to="/admin" > <AdminIcon /> admin </RouterLink>
      <RouterLink v-if="isAuthenticated" to="/chats"> <ChatIcon /> chats </RouterLink>
      <RouterLink to="/create-listing"> <CreateListingIcon /> ny annonse </RouterLink>
      <RouterLink v-if="!isAuthenticated" to="/login"> <ProfileIcon /> logg inn </RouterLink>
      <RouterLink v-if="isAuthenticated" to="/mypage"> <ProfileIcon /> profil </RouterLink>
    </nav>
  </header>
</template>

<style scoped>
header {
  display: flex;
  line-height: 2.5rem;
  padding: 2rem;
  height: 2.5rem;
  font-size: 1.2rem;
  z-index: 100;
}

nav {
  display: flex;
  flex-grow: 1;
  justify-content: flex-end;
  align-items: center;
}

a {
  margin-right: 2rem;
  text-decoration: none;
  color: var(--color-text);
  transition-duration: 80ms;
  display: flex;
  align-items: center;
}

a:hover {
  color: var(--color-text-hover);
}

#navbar-logo {
  margin-left: 1rem;
  text-decoration: none;
}

svg {
  height: 1rem !important;
  margin: 0 0.3rem;
}
</style>
