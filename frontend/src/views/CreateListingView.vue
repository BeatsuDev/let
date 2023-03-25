<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Lag en ny annonse</h1>
    <ListingCreatorForm @create-listing="createListing" />
  </div>
</template>

<script setup lang="ts">
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { CreateListing } from "@/services/index";
import { ListingsApi } from "@/services/index";
import router from "@/router";
import BackButton from "@/components/inputs/BackButton.vue";

// Define api
const api = new ListingsApi();

// Callback funcitons
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
