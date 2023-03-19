<script setup lang="ts">
import { onMounted } from 'vue';
/*
import { toRefs } from 'vue';
import type { Item } from '@/types/Item.d.ts';

const props = defineProps<{
  item: string;
}>();

const { item } = toRefs(props);
*/
const item = {
  imageUrls: [
    "https://assets.api.uizard.io/api/cdn/stream/c0dfbf89-8d77-42ab-ad3b-155bd55178da.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/87658174-25e0-4002-ab8f-b4e7961200cd.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/4a1a87f6-3bed-4dfc-8301-a53777cf226f.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/c0dfbf89-8d77-42ab-ad3b-155bd55178da.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/87658174-25e0-4002-ab8f-b4e7961200cd.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/4a1a87f6-3bed-4dfc-8301-a53777cf226f.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/c0dfbf89-8d77-42ab-ad3b-155bd55178da.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/87658174-25e0-4002-ab8f-b4e7961200cd.jpg",
    "https://assets.api.uizard.io/api/cdn/stream/4a1a87f6-3bed-4dfc-8301-a53777cf226f.jpg",
  ],
  title: "Plante",
  shortDescription: "En kul plante.",
  description: `Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin ac tellus dictum, ultrices odio vitae, mattis magna. Nunc nec ultrices sapien. Nam sodales consectetur hendrerit. Morbi porttitor dolor urna, convallis semper tellus porta in. Donec id mauris id ligula iaculis iaculis eget eu risus. Curabitur malesuada, ante ut blandit maximus, purus eros ultrices enim, at convallis mi nibh ut dui. Phasellus feugiat lectus ut faucibus condimentum. Mauris molestie et justo et dignissim. Aliquam facilisis bibendum dolor, nec tempor est pretium et. Nulla vulputate hendrerit commodo. Nullam posuere magna nulla, a bibendum felis rutrum eget. Nunc mollis dignissim efficitur. Proin porta metus placerat arcu suscipit ullamcorper. Vivamus porta, mi eu iaculis finibus, odio dolor condimentum arcu, ac aliquam velit urna et enim. Maecenas at lacus a ex malesuada tristique. Curabitur tempus metus et dui ultricies tincidunt. Ut iaculis justo non orci consectetur dignissim. Suspendisse potenti. Nunc magna ipsum, consequat nec metus id, vulputate rutrum urna. Quisque augue nulla, tincidunt non dolor quis, viverra sagittis diam. Phasellus placerat mollis gravida. Ut nibh arcu, pulvinar quis elit et, sagittis consectetur purus. Sed a eros ante.`,
  price: 500,
  location: "Oslo, Høybråten",
  category: "Stygge planter",
  seller: {
    name: "Arunan Gnanasekaran",
  },
};
</script>

<template>
  <main>
    <div id="images-section">
      <img :src="item.imageUrls[0]" id="main-image" />
      <div id="other-images">
        <img
          :key="'image-' + index"
          v-for="(image_url, index) in item.imageUrls"
          :style="{ backgroundImage: `url(${image_url})` }"
        />
      </div>
    </div>

    <div id="details-section">
      <div class="top-bar">
        <h1>{{ item.title }}</h1>
        <div id="edit-btn">EDIT</div>
        <div id="bookmark-btn">Bookmark</div>
      </div>

      <div class="price-bar">
        <div id="price">{{ item.price }}kr</div>
      </div>

      <div class="misc-info-bar">
        <div class="misc-bar-left">
          <h2>Category:</h2>
          <p id="category">{{ item.category }}</p>
          <!-- TODO: Button component here -->
          <button>CONTACT SELLER</button>
        </div>
        <div class="misc-bar-right">
          <h3>Sold by:</h3>
          <p id="seller">{{ item.seller.name }}</p>
          <h3>Location:</h3>
          <p id="location">{{ item.location }}</p>
        </div>
      </div>

      <div class="description-bar">
        <h2>Description</h2>
        <p id="description">{{ item.description }}</p>
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
  display: inline-block;
  white-space: nowrap;
  max-width: 100%;
  overflow-x: auto;
}

#other-images > img {
  width: 100px;
  height: 100px;
  margin-right: 0.5rem;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
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
  font-size: 3rem;
  font-weight: 500;
  margin: 0;
}

.top-bar > #edit-btn {
  background-color: #3B8905;
  padding: 0.3rem 2rem;
  color: white;
  cursor: pointer;
  font-family: Inter;
  font-size: .8rem;
  font-weight: bold;
  margin-right: 1rem;
}

.price-bar {
  display: flex;
  margin-top: 10px;
  flex-direction: row;
  align-items: center;
}

.price-bar > #price {
  font-size: 2rem;
  font-weight: 500;
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
  border: 1px solid #C1C1C1;
  padding: 1rem;
  font-family: Inter;
}

.misc-info-bar > .misc-bar-right > h3 {
  font-size: 0.8rem;
  font-weight: normal;
  margin: 0;
}

.misc-info-bar > .misc-bar-right > p {
  font-size: 0.8rem;
  font-weight: 500;
  margin: 0;
}

.misc-info-bar > .misc-bar-left > h2 {
  font-size: 1.2rem;
  font-weight: normal;
  margin: 0;
  margin-top: 20px;
}

.misc-info-bar > .misc-bar-left > p {
  font-size: 0.7rem;
  margin: 0;
  margin-top: 5px;
}

.misc-info-bar > .misc-bar-left > button {
  background-color: black;
  border: 0;
  padding: .6rem 2rem;
  color: white;
  cursor: pointer;
  font-family: Inter;
  font-size: .6rem;
  font-weight: bold;
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
