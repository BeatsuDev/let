<script setup lang="ts">
import { RouterView } from "vue-router";
import NavigationBar from "./components/NavigationBar.vue";
</script>

<template>
  <NavigationBar id="navigation-bar"/>

  <RouterView id="main-view"/>
</template>

<style scoped>
#navigation-bar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background-color: #fff;
  height: 65px;
}

#main-view {
  margin-top: 65px;
}
</style>
