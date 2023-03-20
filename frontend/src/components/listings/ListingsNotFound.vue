<template>
  <div class="main">
    <div class="not-found">
      <SearchIcon style="width: 30%"/><h1>Vi fant ikke det du letet etter...</h1>
      <p>
        Vi fant dessverre ingen treff på søket ditt. Prøv å endre søkeordene eller
        søkeområdet.
      </p>
    </div>
  </div>
</template>
<style>
.main {
  display:flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>
<script setup lang="ts">
import SearchIcon from "@/components/icons/SearchIcon.vue";
</script>