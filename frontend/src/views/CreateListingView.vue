<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Lag en ny annonse</h1>
    <ListingCreatorForm v-model="listingData" @create-listing="createListing" editable="true" />
    <AlertBox v-if="errorMessage" :message="errorMessage" type="error"></AlertBox>
  </div>
</template>

<script setup lang="ts">
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { CreateListing, ListingFull } from "@/services/index";
import { ListingsApi } from "@/services/index";
import router from "@/router";
import BackButton from "@/components/inputs/BackButton.vue";
import { ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

// Define api
const api = new ListingsApi();

// Define refs
const listingData = ref({
  title: "",
  description: "",
  summary: "",
  price: 0,
  category: {
    id: undefined,
    name: "",
  },
} as ListingFull);
const errorMessage = ref("");

//Define callback functions
async function createListing(newListing: CreateListing) {
  api
    .createListing(newListing)
    .then((response) => {
      router.push({ name: "listing-details", params: { id: response.data.id } });
    })
    .catch(() => {
      errorMessage.value = "Noe gikk galt... Prøv igjen senere";
    });
  await router.push({ name: "listing-details", params: { id: 1 } });
}
</script>
