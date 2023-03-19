<script setup lang="ts">
import { RouterLink } from "vue-router";

// TODO: Check if the user is an admin and show the admin link if so
</script>

<template>
  <header>
    <nav>
<RouterLink to="/">Let.no</RouterLink>
      <RouterLink to="/admin">Admin</RouterLink>
      <RouterLink to="/chats">Chats</RouterLink>
      <RouterLink to="/create-listing">New Listing</RouterLink>
      <RouterLink to="/profile">My profile</RouterLink>
    </nav>
  </header>
</template>

<style scoped>
header {
  display: flex;
  line-height: 1.5;
  max-height: 2rem;
}
</style>
