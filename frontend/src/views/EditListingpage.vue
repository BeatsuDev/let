<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Rediger annonsen din</h1>
    <ListingCreatorForm v-model="listingData" @create-listing="createListing" />
    <AlertBox :message="errorMessage" type="error"></AlertBox>
  </div>
</template>

<script setup lang="ts">
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { CreateListing } from "@/services/index";
import { ListingsApi } from "@/services/index";
import router from "@/router";
import BackButton from "@/components/inputs/BackButton.vue";
import { ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";
import axios from "axios";

// Define api
const api = new ListingsApi();

// Define refs
const listingData = ref({
  title: "",
  description: "moren din",
  price: 0,
  category: "",
  images: [],
} as CreateListing);
const errorMessage = ref("");

api
  .getListing(parseInt(router.currentRoute.value.params.id as string))
  .then((response) => {
    response.data.location = { name: response.data.locationName };
    response.data.category = { name: response.data.categoryName };
    console.log(response.data);
    listingData.value = response.data;
  })
  .catch((error) => {
    alert(error.message);
  });

// Callback functions
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

async function submitData() {
  // Check if inputs follow validation rules
  const result = await validator.value.$validate();
  if (!result) {
    return;
  }

  // Upload images to backend
  const imageResponses = await Promise.all(
    listingData.value.images.map((image) => {
      const formData = new FormData();
      formData.append("image", image);
      return axios.post("http://localhost:8080/image", formData);
    })
  );

  // Make sure all image responses are successful
  const imageSuccesses = imageResponses.map(
    (response) => response.status === 201 && response.data.url
  );

  if (imageSuccesses.includes(false)) {
    errorMessage.value =
      "Noe gikk galt under bildeopplastningen... " +
      imageResponses.filter((response) => response.status !== 201)[0].data;
    return;
  }
}
</script>
