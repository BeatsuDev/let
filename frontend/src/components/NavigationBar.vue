<script setup lang="ts">
import { RouterLink } from "vue-router";
import AdminIcon from "@/components/icons/AdminIcon.vue";
import ChatIcon from "@/components/icons/ChatIcon.vue";
import CreateListingIcon from "@/components/icons/CreateListingIcon.vue";
import ProfileIcon from "@/components/icons/ProfileIcon.vue";
import { useSessionStore } from "@/stores/sessionStore";
import { computed } from "vue";

// eslint-disable-next-line no-undef
const sessionStore = useSessionStore();
const isAuthenticated = computed(() => sessionStore.isAuthenticated());
const highestRole = computed(() => sessionStore.getHighestRole());
</script>

<template>
  <header class="column">
    <div class="row">
      <nav>
        <RouterLink class="nav-item" to="/">letno</RouterLink>
        <div class="spacer" />
        <RouterLink class="nav-item" v-if="isAuthenticated && highestRole === 'ADMIN'" to="/admin">
          <AdminIcon />
          <span>admin</span></RouterLink
        >
        <RouterLink class="nav-item" v-if="isAuthenticated" to="/chats">
          <ChatIcon class="nav-icon" />
          <span>chats</span></RouterLink
        >
        <RouterLink class="nav-item" to="/create-listing">
          <CreateListingIcon class="nav-icon" />
          <span>ny annonse</span></RouterLink
        >
        <RouterLink class="nav-item" v-if="!isAuthenticated" to="/login">
          <ProfileIcon class="nav-icon" />
          <span>logg inn</span></RouterLink
        >
        <RouterLink class="nav-item" v-if="isAuthenticated" to="/mypage">
          <ProfileIcon class="nav-icon" />
          <span>profil</span>
        </RouterLink>
      </nav>
    </div>
  </header>
</template>

<style scoped>
header {
  display: flex;
  line-height: 2.5rem;
  padding: 1rem 2rem;
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

@media screen and (max-width: 600px) {
  .nav-icon {
    height: 1.2rem !important;
  }

  span {
    display: none;
  }
}
</style>
