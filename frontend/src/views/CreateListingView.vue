<script setup lang="ts">
import ListingCreatorForm from "@/components/ListingCreatorForm.vue";
import type { CreateListing } from "@/service/index";

function createListing(listingData: CreateListing) {
  console.log(listingData);
}
</script>

<template>
  <div class="wrapper">
    <h1>Lag en ny annonse</h1>
    <ListingCreatorForm @create-listing="createListing" />
  </div>
</template>

<style scoped>
.wrapper {
  max-width: 1200px;
  margin: 0 auto;
  min-width: 400px;
  padding: 2rem 5rem;
}
</style>
