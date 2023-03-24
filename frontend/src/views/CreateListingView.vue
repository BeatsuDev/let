<script setup lang="ts">
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { CreateListing } from "@/service/index";
import { ListingsApi } from "@/service/index";
import router from "@/router";

const api = new ListingsApi();

function createListing(listingData: CreateListing) {
  console.log(listingData);
  api
    .createListing(listingData)
    .then((response) => {
      router.push({ name: "listing-details", params: { id: response.data.id } });
    })
    .catch((error) => {
      alert(error.message);
    });
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
