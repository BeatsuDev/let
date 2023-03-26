<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Lag en ny annonse</h1>
    <ListingCreatorForm v-model="listingData" @create-listing="createListing" />
    <AlertBox v-if="errorMessage" :message="errorMessage" type="error"></AlertBox>
  </div>
</template>

<script setup lang="ts">
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { CreateListing } from "@/services/index";
import { ListingsApi } from "@/services/index";
import router from "@/router";
import BackButton from "@/components/inputs/BackButton.vue";
import axios from "axios";
import { ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

// Define api
const api = new ListingsApi();

// Define refs
const listingData = ref({
  title: "",
  description: "",
  price: 0,
  category: "",
  images: [],
  thumbnailId: 0,
} as CreateListing);
const errorMessage = ref("" as string);

//Define callback functions
async function createListing() {
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

  const imageIds = imageResponses.map((image) => image.data.id) as string[];
  const { price, location, images, category, ...data } = listingData.value;

  const listingDataWithImages = {
    ...data,
    categoryId: category.id,
    thumbnailId: listingData.value.thumbnailId,
    location: { ...listingData.value.location },
    price: Math.round(Number(listingData.value.price) * 100),
    galleryIds: imageIds,
  } as unknown as CreateListing;
  console.table(listingDataWithImages);

  api
    .createListing(listingDataWithImages)
    .then((response) => {
      router.push({ name: "listing-details", params: { id: response.data.id } });
    })
    .catch((error) => {
      alert(error.message);
    });
  router.push({ name: "listing-details", params: { id: 1 } });
}
</script>
