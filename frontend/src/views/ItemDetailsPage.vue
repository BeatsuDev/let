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
    "https://assets.api.uizard.io/api/cdn/stream/87658174-25e0-4002-ab8f-b4e7961200cd.jpg"
  ],
  title: "Plante",
  shortDescription: "En kul plante.",
  description: "Dette er en veldig kul plante. Du vil ha denne! Jeg vet du vil!!",
  price: 500,
  location: "Oslo, Høybråten",
  category: "Stygge planter",
  seller: {
    name: "Arunan Gnanasekaran",
  }
}
</script>

<template>
  <main>
    <div id="images-section">
      <div id="main-image">
        <img :src="item.imageUrls[0]" />
      </div>
      <div id="other-images">
        <img v-for="image in item.imageUrls" :src="image" />
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

</style>