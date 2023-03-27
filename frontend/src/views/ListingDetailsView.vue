<template>
  <div v-if="!listing && !errorMessage">
    <FullPageLoading />
  </div>
  <AlertBox
    v-if="listing?.state === 'SOLD' && !errorMessage"
    type="warning"
    message="Denne annonsen er solgt"
    style="margin-top: 5rem"
  />
  <AlertBox v-if="errorMessage" :message="errorMessage" type="error" style="margin-top: 5rem" />
  <main v-if="listing" style="padding-top: 3rem">
    <div id="images-section">
      <BackButton style="margin-left: -1rem" />
      <ImageContainer v-model="mainImage" :imageUrls="imageUrls(listing.gallery)"></ImageContainer>
    </div>
    <div id="details-section">
      <div class="top-bar">
        <h1 class="text-one-line">{{ listing.title }}</h1>
        <div
          v-if="sessionStore.getUser()?.email === listing.seller.email"
          id="edit-btn"
          class="button-slim button-green button-screaming"
          @click="router.push('/edit-listing/' + listing.id)"
        >
          Rediger
        </div>
        <div id="bookmark-btn">
          <BookmarkIcon
            :bookmarked="isBookmarked"
            :class="{ filled: isBookmarked }"
            @toggleBookmark="handleBookmarkClick"
          />
        </div>
      </div>

      <div class="price-bar">
        <h2>{{ listing.price ? listing.price / 100 : "-" }}kr</h2>
      </div>

      <div class="misc-info-bar">
        <div class="misc-bar-left">
          <h3>Kategori:</h3>
          <p id="category">{{ listing.category.name }}</p>
          <button
            class="button button-black button-screaming"
            v-if="
              listing.state !== 'SOLD' && sessionStore.getUser()?.email !== listing.seller.email
            "
            @click="contactSeller"
          >
            Kontakt selger
          </button>
        </div>
        <div class="misc-bar-right">
          <h5>Selges av:</h5>
          <p id="seller">{{ listing.seller.firstName + " " + listing.seller.lastName }}</p>
          <h5 id="location-header">Sted:</h5>
          <p id="location">{{ listing.locationName }}</p>
        </div>
      </div>

      <h3>Beskrivelse:</h3>
      <div class="description-bar">
        <p id="description">{{ listing.description }}</p>
      </div>
    </div>
  </main>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import router from "@/router";

import { ChatApi, Image, ListingsApi } from "@/services/index";
import type { ListingFull } from "@/services/index";
import { useSessionStore } from "@/stores/sessionStore";

import BookmarkIcon from "@/components/icons/BookmarkIcon.vue";
import FullPageLoading from "@/components/containers/FullPageLoading.vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";
import ImageContainer from "@/components/containers/ImageContainer.vue";
import BackButton from "@/components/inputs/BackButton.vue";

// Define APIs
const listingsApi = new ListingsApi();
const chatApi = new ChatApi();

// Define variables
const id = Number(router.currentRoute.value.params.id);
const sessionStore = useSessionStore();

// Define refs
const isBookmarked = ref(false);
const mainImage = ref(0);
const listing = ref<ListingFull | null>(null);
const errorMessage = ref("" as string);

// Define callbacks
function handleBookmarkClick() {
  if (!sessionStore.isAuthenticated) {
    router.push("/login");
    return;
  }
  if (isBookmarked.value) {
    listingsApi
      .removeFavorite(id)
      .then(() => {
        isBookmarked.value = false;
      })
      .catch((e) => {
        handleError(e);
      });
  } else {
    listingsApi
      .addFavorite(id)
      .then(() => {
        isBookmarked.value = true;
      })
      .catch((e) => {
        handleError(e);
      });
  }
}

function contactSeller() {
  if (!sessionStore.isAuthenticated) {
    router.push("/login");
    return;
  }
  chatApi.createChat({ listingId: id }).then((response) => {
    router.push({ name: "chat", params: { chatId: response.data.id } });
  });
}

// Other script logic
listingsApi
  .getListing(id)
  .then((response) => {
    listing.value = response.data;
  })
  .catch((e) => {
    if (e.response.status == 404) {
      errorMessage.value = "Denne annonsen er slettet, eller eksisterer ikke";
      return;
    }
    errorMessage.value = e;
  });

listingsApi
  .checkFavorite(id)
  .then((response) => {
    isBookmarked.value = response.data;
  })
  .catch((e) => {
    errorMessage.value = e;
  });

// Other script logic
function handleError(e: any) {
  if (e.response.status == 401) {
    router.push("/login");
    return;
  }
  errorMessage.value = "En uventet feil har oppstått, prøv igjen senere";
}

function imageUrls(images: Image[]): string[] {
  return images.map((image) => image?.url).filter((url) => !!url) as string[];
}
</script>

<style scoped>
main {
  display: flex;
  flex-direction: row;
}

/* ----- IMAGES SECTION ----- */
#images-section {
  flex: 1;
  padding: 2rem;
  max-width: 50%;
}

/* ----- DETAILS SECTION ----- */
#details-section {
  flex: 1;
  padding: 2rem;
  max-width: 50%;
}

.top-bar {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.top-bar > h1 {
  flex: 1;
}

.top-bar > #edit-btn {
  margin-right: 1rem;
}

.price-bar {
  display: flex;
  margin-top: 10px;
  flex-direction: row;
  align-items: center;
}

.price-bar > h2 {
  margin-right: 1rem;
}

/* ----- MISC INFO BAR ----- */
.misc-info-bar {
  display: flex;
  flex-direction: row;
  margin-top: 1rem;
}

.misc-info-bar > .misc-bar-left {
  flex: 1;
  max-width: 50%;
}

.misc-info-bar > .misc-bar-right {
  flex: 1;
  max-width: 50%;
  border: 1px solid #c1c1c1;
  padding: 1rem;
  font-family: Inter;
}

.misc-info-bar #location-header {
  margin-top: 1rem;
}

.misc-info-bar > .misc-bar-right > p {
  font-size: 0.8rem;
  font-weight: 500;
  margin: 0;
}

.misc-info-bar > .misc-bar-left > h3 {
  margin-top: 20px;
}

.misc-info-bar > .misc-bar-left > p {
  font-size: 0.7rem;
  margin: 5px 0 0;
}

.misc-info-bar > .misc-bar-left > button {
  font-size: 0.6rem;
  margin-top: 40px;
}

/* ----- DESCRIPTION BAR ----- */
.description-bar {
  margin-top: 1rem;
}

.description-bar > h2 {
  font-size: 1.2rem;
  font-weight: normal;
  margin: 20px 0 0 10px;
}

@media (max-width: 1100px) {
  main {
    flex-direction: column;
  }

  #images-section {
    max-width: 100%;
  }

  #details-section {
    max-width: 100%;
  }
}
</style>
