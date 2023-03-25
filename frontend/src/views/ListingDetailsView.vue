<template>
  <div v-if="!data && !error">
    <FullPageLoading />
  </div>
  <AlertBox v-else-if="error" type="error" :message="error" />
  <main v-else-if="data">
    <div id="images-section">
      <BackButton />
      <img id="main-image" :src="mainImage" />
      <div id="other-images">
        <img
          v-for="(image_url, index) in data.galleryUrls"
          :key="index"
          :src="image_url"
          loading="lazy"
          @click="mainImage = data.galleryUrls[index]"
        />
      </div>
    </div>

    <div id="details-section">
      <div class="top-bar">
        <h1>{{ data.title }}</h1>
        <div
          v-if="sessionStore.getUser()?.email === data.seller.email"
          id="edit-btn"
          class="button-slim button-green button-screaming"
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
        <h2>{{ data.price ? data.price / 100 : "-" }}kr</h2>
      </div>

      <div class="misc-info-bar">
        <div class="misc-bar-left">
          <h3>Kategori:</h3>
          <p id="category">{{ data.categoryName }}</p>
          <button class="button button-black button-screaming" @click="contactSeller">Kontakt Seller</button>
        </div>
        <div class="misc-bar-right">
          <h5>Selges av:</h5>
          <p id="seller">{{ data.seller!.firstName + " " + data.seller!.lastName }}</p>
          <h5 id="location-header">Sted:</h5>
          <p id="location">{{ data.locationName }}</p>
        </div>
      </div>

      <div class="description-bar">
        <h2>Beskrivelse</h2>
        <p id="description">{{ data.description }}</p>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref } from "vue";
import router from "@/router";

import { ChatApi, ListingsApi } from "@/services/index";
import type { ListingFull } from "@/services/index";
import { useSessionStore } from "@/stores/sessionStore";

import BookmarkIcon from "@/components/icons/BookmarkIcon.vue";
import FullPageLoading from "@/components/containers/FullPageLoading.vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";
import BackButton from "@/components/inputs/BackButton.vue";

// Define APIs
const listingsApi = new ListingsApi();
const chatApi = new ChatApi();

// Define variables
const id = Number(router.currentRoute.value.params.id);
const sessionStore = useSessionStore();

// Define refs
const isBookmarked = ref(false);
const mainImage = ref(null as string | null);
const data = ref(null as ListingFull | null);
const error = ref(null as Error | null);

// Define callbacks
function handleBookmarkClick() {
  if (isBookmarked.value) {
    listingsApi
      .removeFavorite(id)
      .then(() => {
        isBookmarked.value = false;
      })
      .catch((e) => {
        error.value = e;
      });
  } else {
    listingsApi
      .addFavorite(id)
      .then(() => {
        isBookmarked.value = true;
      })
      .catch((e) => {
        error.value = e;
      });
  }
}

function contactSeller() {
  chatApi.createChat({ listingId: id}).then(() => {
    router.push({ name: "chats" });
  });
}

// Other script logic
listingsApi
  .getListing(id)
  .then((response) => {
    data.value = response.data;
    mainImage.value = response.data.galleryUrls[0];
  })
  .catch((e) => {
    error.value = e;
  });

listingsApi
  .checkFavorite(id)
  .then((response) => {
    isBookmarked.value = response.data;
  })
  .catch((e) => {
    error.value = e;
  });
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

#main-image {
  height: 60vh;
  max-width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  object-fit: contain;
  margin: 0 auto;
}

#other-images {
  margin-top: 1rem;
  display: flex;
  white-space: nowrap;
  max-width: 100%;
  overflow-x: auto;
}

#other-images > img {
  width: 100px;
  height: 100px;
  margin-right: 0.5rem;
  object-position: center;
  object-fit: cover;
  cursor: pointer;
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
  margin: 0;
  margin-top: 5px;
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
