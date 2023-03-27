<template>
  <button class="button button-text" @click="router.back()">
    <BackIcon class="button-icon" />Tilbake
  </button>
</template>

<script setup lang="ts">
import BackIcon from "@/components/icons/BackIcon.vue";
import router from "@/router";
</script>
