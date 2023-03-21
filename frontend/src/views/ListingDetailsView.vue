<script setup lang="ts">
import BookmarkIcon from "@/components/icons/BookmarkIcon.vue";
import { useRoute } from "vue-router";
import { ListingsApi } from "@/service/index";
import runAxios from "@/service/composable";

const route = useRoute();

const api = new ListingsApi();

const id = Number(route.params.id);

const { data, error } = runAxios(api.getListing(id));

const image_urls = [
  "https://media.discordapp.net/attachments/903975806227857409/1087280248007176282/20230320_081917.jpg",
  "https://media.discordapp.net/attachments/903975806227857409/1087280248300785724/20230320_081925.jpg",
  "https://media.discordapp.net/attachments/903975806227857409/1087280248539844628/20230320_081933.jpg",
  "https://media.discordapp.net/attachments/903975806227857409/1087280248804094022/20230320_081948.jpg",
  "https://media.discordapp.net/attachments/903975806227857409/1087280249085120512/20230320_082012.jpg",
  "https://media.discordapp.net/attachments/903975806227857409/1087280249353539635/20230320_082042.jpg",
  "https://media.discordapp.net/attachments/903975806227857409/1087280249588428800/20230320_082112.jpg",
];

const description = `Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ac tellus dictum, ultrices odio vitae,
  mattis magna. Nunc nec ultrices sapien. Nam sodales consectetur hendrerit. Morbi porttitor dolor urna, convallis semper
  tellus porta in. Donec id mauris id ligula iaculis iaculis eget eu risus. Curabitur malesuada, ante ut blandit maximus,
  purus eros ultrices enim, at convallis mi nibh ut dui. Phasellus feugiat lectus ut faucibus condimentum. Mauris molestie
  et justo et dignissim. Aliquam facilisis bibendum dolor, nec tempor est pretium et. Nulla vulputate hendrerit commodo.
  Nullam posuere magna nulla, a bibendum felis rutrum eget. Nunc mollis dignissim efficitur. Proin porta metus placerat
  arcu suscipit ullamcorper. Vivamus porta, mi eu iaculis finibus, odio dolor condimentum arcu, ac aliquam velit urna et
  enim. Maecenas at lacus a ex malesuada tristique. Curabitur tempus metus et dui ultricies tincidunt. Ut iaculis justo
  non orci consectetur dignissim. Suspendisse potenti. Nunc magna ipsum, consequat nec metus id, vulputate rutrum urna.
  Quisque augue nulla, tincidunt non dolor quis, viverra sagittis diam. Phasellus placerat mollis gravida. Ut nibh arcu,
  pulvinar quis elit et, sagittis consectetur purus. Sed a eros ante.`;

function handleImageClick(event: MouseEvent) {
  const image = event.target as HTMLImageElement;
  const mainImage = document.getElementById("main-image") as HTMLImageElement;
  mainImage.src = image.src;
}

const tempItem = {
  isBookmarked: false,
}

function handleBookmarkClick() {
  tempItem.isBookmarked = !tempItem.isBookmarked;
  console.log(tempItem.isBookmarked);
}
</script>

<template>
  <div v-if="!data && !error">
    Loading...
  </div>
  <div v-else-if="error">
    Error: {{ error }}
  </div>
  <main v-else-if="data">
    <div id="images-section">
      <img :src="data.galleryUrls![0]" id="main-image" />
      <div id="other-images">
        <img
          :id="'image-' + index"
          :key="'image-' + index"
          v-for="(image_url, index) in data.galleryUrls"
          :src="image_url"
          @click="handleImageClick"
        />
      </div>
    </div>

    <div id="details-section">
      <div class="top-bar">
        <h1>{{ data.title }}</h1>
        <div id="edit-btn" class="button-slim button-green button-screaming">Rediger</div>
        <div id="bookmark-btn">
          <BookmarkIcon
            :bookmarked="tempItem.isBookmarked"
            :class="{ filled: tempItem.isBookmarked }"
            @toggleBookmark="handleBookmarkClick"
          />
        </div>
      </div>

      <div class="price-bar">
        <h2>{{ data.price ? data.price / 100 : '-' }}kr</h2>
      </div>

      <div class="misc-info-bar">
        <div class="misc-bar-left">
          <h3>Kategori:</h3>
          <p id="category">{{ data.categoryName }}</p>
          <!-- TODO: Button component here -->
          <button class="button button-black button-screaming">Kontakt Seller</button>
        </div>
        <div class="misc-bar-right">
          <h5>Selges av:</h5>
          <p id="seller">{{ data.seller!.firstName + ' ' + data.seller!.lastName }}</p>
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
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  margin: 0 auto;
}

#other-images {
  margin-top: 1rem;
  display: flex;
  justify-content: center;
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
