<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <BackButton />
      <router-link class="nav-item" to="/my-page"><h3>Din bruker</h3></router-link>
      <router-link class="nav-item" to="/my-page/listings"><h3>Mine annonser</h3></router-link>
      <router-link class="nav-item" to="/my-page/bookmarked"><h3>Bokmerket</h3></router-link>
    </NavigationDrawer>
    <RouterView v-model:collapsed="collapsed"></RouterView>
  </main>
</template>
<script lang="ts" setup>
import NavigationDrawer from "@/components/navigations/NavigationDrawer.vue";
import { ref } from "vue";
import BackButton from "@/components/inputs/BackButton.vue";

const collapsed = ref(false);
</script>
<style></style>
