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
import { ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";
import { uploadImage } from "@/utils/imageUpload";

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
  let imageIds = [] as string[];
  await uploadImage(listingData.value.images)
    .then((data) => (imageIds = data))
    .catch((error) => {
      errorMessage.value = error.message;
      return;
    });

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
      errorMessage.value = "Noe gikk galt... Prøv igjen senere";
    });
  router.push({ name: "listing-details", params: { id: 1 } });
}
</script>
