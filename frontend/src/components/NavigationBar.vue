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
const highestRole = computed(() => sessionStore.getHighestRole());
</script>

<template>
  <header class="column">
    <div class="row">
      <nav>
        <RouterLink class="nav-item" style="margin-left: 1rem" to="/">letno</RouterLink>
        <div class="spacer"></div>
        <RouterLink
          class="nav-item"
          v-if="!!sessionStore.getUser && highestRole === 'ADMIN'"
          to="/admin"
        >
          <AdminIcon />
          <span>admin</span></RouterLink
        >
        <!-- Typescript complains if I only use sessionStore.getUser... -->
        <!-- It doesn't complain about () => session.getUser() or !!sessionStore.getUser (not not) -->
        <!-- But simply writing session.getUser works as well, TS just complains -->
        <RouterLink class="nav-item" v-if="!!sessionStore.getUser" to="/chats">
          <ChatIcon class="nav-icon" />
          <span>chats</span></RouterLink
        >
        <RouterLink v-if="!!sessionStore.getUser" class="nav-item" to="/create-listing">
          <CreateListingIcon class="nav-icon" />
          <span>ny annonse</span></RouterLink
        >
        <RouterLink class="nav-item" v-if="!sessionStore.getUser" to="/login">
          <ProfileIcon class="nav-icon" />
          <span>logg inn</span></RouterLink
        >
        <RouterLink class="nav-item" v-if="!!sessionStore.getUser" to="/my-page">
          <ProfileIcon class="nav-icon" />
          <span>profil</span>
        </RouterLink>
        <a class="nav-item" v-if="!!sessionStore.getUser" @click="sessionStore.logOut()">
          <ProfileIcon class="nav-icon" />
          <span>logg ut</span></a
        >
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
