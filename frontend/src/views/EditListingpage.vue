<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Rediger annonsen din</h1>
    <ListingCreatorForm v-model="listingData" @create-listing="createListing" />
    <AlertBox v-if="errorMessage" :message="errorMessage" type="error"></AlertBox>
    <div style="margin-top: 1rem">
      <button
        @click="deleteListing"
        class="button button-screaming button-danger"
        style="margin-right: 1rem"
      >
        Slett annonsen
      </button>
      <button @click="sellListing" class="button button-screaming button-warning">
        Marker som solgt
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { CreateListing, UpdateListing } from "@/services/index";
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
  price: undefined,
  category: "",
  images: [],
  thumbnailId: 0,
} as UpdateListing);
const errorMessage = ref("");

api
  .getListing(parseInt(router.currentRoute.value.params.id as string))
  .then((response) => {
    listingData.value.location = { name: response.data.locationName };
    listingData.value = { ...listingData.value, ...response.data };
  })
  .catch(() => {
    errorMessage.value = "Noe gikk galt...";
  });

// Callback functions
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

function deleteListing() {
  console.log(listingData.value);
  api
    .deleteListing(parseInt(router.currentRoute.value.params.id as string))
    .then(() => {
      router.push({ name: "home" });
    })
    .catch(() => {
      errorMessage.value = "Noe gikk galt under slettingen av annonsen...";
    });
}

function sellListing() {
  listingData.value.state = "SOLD";
  api
    .updateListing(listingData.value)
    .then(() => {
      router.back();
    })
    .catch(() => {
      errorMessage.value = "Kunne ikke markere annonsen som solgt... Prøv igjen senere";
    });
}

async function submitData() {
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
