<template>
  <header class="column">
    <div class="row">
      <nav>
        <RouterLink class="nav-item" style="margin-left: 1rem" to="/">letno</RouterLink>
        <div class="spacer"></div>
        <RouterLink
          v-if="sessionStore.isAuthenticated && sessionStore.getHighestRole() === 'ADMIN'"
          id="admin-panel-navbutton"
          class="nav-item"
          to="/admin"
        >
          <AdminIcon class="nav-icon" />
          <span>admin</span></RouterLink
        >
        <RouterLink v-if="sessionStore.getUser()" id="chats-navbutton" class="nav-item" to="/chats">
          <ChatIcon class="nav-icon" />
          <span>chats</span></RouterLink
        >
        <RouterLink v-if="sessionStore.getUser()" id="create-listing-navbutton" class="nav-item" to="/create-listing">
          <CreateListingIcon class="nav-icon" />
          <span>ny annonse</span></RouterLink
        >
        <RouterLink v-if="!sessionStore.getUser()" id="login-navbutton" class="nav-item" to="/login">
          <ProfileIcon class="nav-icon" />
          <span>logg inn</span></RouterLink
        >
        <RouterLink v-if="sessionStore.getUser()" id="mypage-navbutton" class="nav-item" to="/my-page">
          <ProfileIcon class="nav-icon" />
          <span>profil</span>
        </RouterLink>
        <a v-if="sessionStore.isAuthenticated"  id="logout-navbutton" class="nav-item" @click="logOut">
          <LogOutIcon class="nav-icon" />
          <span>logg ut</span></a
        >
      </nav>
    </div>
  </header>
</template>

<script setup lang="ts">
import { RouterLink } from "vue-router";
import AdminIcon from "@/components/icons/AdminIcon.vue";
import ChatIcon from "@/components/icons/ChatIcon.vue";
import CreateListingIcon from "@/components/icons/CreateListingIcon.vue";
import ProfileIcon from "@/components/icons/ProfileIcon.vue";
import { useSessionStore } from "@/stores/sessionStore";
import LogOutIcon from "@/components/icons/LogOutIcon.vue";
import router from "@/router";
import { computed } from "vue";

// eslint-disable-next-line no-undef
const sessionStore = useSessionStore();

// Define callback functions
function logOut() {
  sessionStore.logOut();
  router.push("/");
}
</script>

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

@media screen and (max-width: 700px) {
  .nav-icon {
    height: 1.2rem !important;
  }

  .column {
    margin: 0;
    padding-left: 0.5rem;
    padding-right: 0.5rem;
  }

  span {
    display: none;
  }
}
</style>
