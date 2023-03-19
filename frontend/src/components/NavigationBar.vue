<script setup lang="ts">
import { RouterLink } from "vue-router";
import AdminIcon from "@/components/icons/AdminIcon.vue";
import ChatIcon from "@/components/icons/ChatIcon.vue";
import CreateListingIcon from "@/components/icons/CreateListingIcon.vue";
import ProfileIcon from "@/components/icons/ProfileIcon.vue";

// TODO: Check if the user is an admin and show the admin link if so
</script>

<template>
  <header>
    <RouterLink id="navbar-logo" to="/">letno</RouterLink>
    <nav>
      <RouterLink to="/admin"> <AdminIcon /> Admin </RouterLink>
      <RouterLink to="/chats"> <ChatIcon /> Chats </RouterLink>
      <RouterLink to="/create-listing"> <CreateListingIcon /> New listing </RouterLink>
      <RouterLink to="/profile"> <ProfileIcon /> Profile </RouterLink>
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
