<template>
  <div class="wrapper-form">
    <BackButton style="margin-left: -3rem" />
    <h1>Rediger annonsen din</h1>
    <ListingCreatorForm v-model="listingToUpdate" @create-listing="updateListing" editable="true" />
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
import type { ListingFull, UpdateListing } from "@/services/index";
import { ListingsApi } from "@/services/index";
import router from "@/router";
import BackButton from "@/components/inputs/BackButton.vue";
import { ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

// Define api
const api = new ListingsApi();

// Define refs
const listingToUpdate = ref<ListingFull>({
  title: "",
  summary: "",
  description: "",
  category: {
    id: undefined,
    name: "",
  },
});
const errorMessage = ref("");

api
  .getListing(parseInt(router.currentRoute.value.params.id as string))
  .then((response) => {
    listingToUpdate.value = { ...listingToUpdate.value, ...response.data };
  })
  .catch(() => {
    errorMessage.value = "Noe gikk galt...";
  });

// Callback functions
async function updateListing(newListing: UpdateListing) {
  api
    .updateListing(newListing)
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
    id: listingToUpdate.value.id,
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
