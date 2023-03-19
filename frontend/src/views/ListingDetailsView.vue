<script setup lang="ts">
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
      <div id="main-image" :style="{ backgroundImage: `url(${item.imageUrls[0]})` }"></div>
      <div id="other-images">
        <div
          :key="'image-' + index"
          v-for="(image, index) in item.imageUrls"
          :style="{ backgroundImage: `url(${image})` }"
        ></div>
      </div>
    </div>

    <div id="details-section">
      <div class="top-bar">
        <h1>{{ item.title }}</h1>
        <div id="edit-btn">Edit</div>
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
          <button>Contact Seller</button>
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
#images-section {
  flex: 1;
  padding: 2rem;
  overflow-x: scroll;
}

#details-section {
  flex: 1;
  padding: 2rem;
}

#main-image {
  width: 300px;
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
}

#other-images {
  margin-top: 1rem;
  display: flex;
  flex-direction: row;
  overflow-x: auto;
}

#other-images > div {
  width: 100px;
  height: 100px;
  margin-right: 0.5rem;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
}
</style>
