<template>
  <main>
    <NavigationDrawer
      v-model="listingFilter"
      v-model:collapsed="collapsed"
      :categories="categories"
    ></NavigationDrawer>
    <div :class="{ content: true, active: !collapsed, collapsed: collapsed }">
      <div class="text-title one-line">Hva leter du etter i dag?</div>
      <div class="menu">
        <button @click="collapsed = !collapsed"></button>
        <div class="spacer" />
        <ListPagination :value="page" @next="nextPage" @previous="previousPage"></ListPagination>
      </div>
      <ListingScrollPane :listings="listings"></ListingScrollPane>
      <div class="menu">
        <div class="spacer" />
        <ListPagination :value="page" @next="nextPage" @previous="previousPage"></ListPagination>
      </div>
    </div>
  </main>
</template>
<script setup lang="ts">
import ListPagination from "@/components/paginations/ListPagination.vue";
import { ref } from "vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import { Category, ListingCard, ListingFilter } from "@/types/listing";
import NavigationDrawer from "@/components/NavigationDrawer.vue";

const page = ref(1);
const listings = ref([] as ListingCard[]);
const categories = ref([] as Category[]);
const collapsed = ref(false);

const listingFilter = ref(new ListingFilter());

const listing = new ListingCard(
  1,
  "Test",
  "Test men moren din er styggere ahahhah Lorem ipsum daler dat",
  1,
  "test",
  "test",
  "test"
);
const category = new Category("test");

for (let i = 0; i < 20; i++) {
  listings.value.push(listing);
}

for (let i = 0; i < 5; i++) {
  categories.value.push(category);
}

function nextPage() {
  page.value++;
}

function previousPage() {
  page.value--;
}
</script>
<style scoped>
.menu {
  padding: 2rem;
  display: flex;
}

.content {
  transition: margin-left 0.5s;
}

.active {
  margin-left: 15rem;
}

.collapsed {
  margin-left: 5rem;
}

@media screen and (max-width: 1000px) {
  .one-line {
    white-space: nowrap;
    font-size: 5vw;
  }
}

@media screen and (max-width: 600px) {
  .active {
    margin-left: 0;
  }
}

main {
  width: 100%;
}
</style>
