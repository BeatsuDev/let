<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Rediger annonsen din</h1>
    <ListingCreatorForm v-model="listingData" @create-listing="updateListing" editable />
    <AlertBox v-if="errorMessage" :message="errorMessage" type="error"></AlertBox>
    <div style="margin-top: 1rem">
      <button
        class="button button-screaming button-danger"
        style="margin-right: 1rem"
        @click="deleteListing"
      >
        Slett annonsen
      </button>
      <button class="button button-screaming button-warning" @click="sellListing">
        Marker som solgt
      </button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import ListingCreatorForm from "@/components/forms/ListingCreatorForm.vue";
import type { UpdateListing } from "@/services/index";
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
    listingData.value.price = response.data.price / 100;
  })
  .catch(() => {
    errorMessage.value = "Noe gikk galt...";
  });

// Callback functions
async function updateListing() {
  // Upload images to backend
  let imageIds = [] as string[];
  await uploadImage(listingData.value.images)
    .then((data) => (imageIds = data))
    .catch((error) => {
      errorMessage.value = error.message;
      return;
    });

  const galleryIds = listingData.value.gallery.map((image) => image.id) as string[];

  const listingDataWithImages = {
    id: listingData.value.id,
    categoryId: listingData.value.category.id,
    thumbnailIndex: listingData.value.thumbnailId,
    summary: listingData.value.summary,
    description: listingData.value.description,
    location: { ...listingData.value.location },
    price: Math.round(Number(listingData.value.price) * 100),
    galleryIds: [...imageIds, ...galleryIds],
  } as unknown as UpdateListing;

  api
    .updateListing(listingDataWithImages)
    .then((response) => {
      router.push({ name: "listing-details", params: { id: response.data.id } });
    })
    .catch((error) => {
      alert(error.message);
    });
}

function deleteListing() {
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
  const update = {
    id: listingData.value.id,
    state: "SOLD",
  } as UpdateListing;
  api
    .updateListing(update)
    .then(() => {
      router.back();
    })
    .catch(() => {
      errorMessage.value = "Kunne ikke markere annonsen som solgt... Prøv igjen senere";
    });
}
</script>
