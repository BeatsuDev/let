<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ListingFilterForm v-model="listingFilter" :categories="categories" />
    </NavigationDrawer>
    <div :class="{ content: true, active: !collapsed, collapsed: collapsed }">
      <h1 class="one-line">Hva leter du etter i dag?</h1>
      <div class="menu">
        <button @click="collapsed = !collapsed" class="button">
          <FilterIcon class="button-icon" />
          Filtre
        </button>
        <div class="spacer" />
        <ListPagination
          :value="listingFilter.page"
          @next="nextPage"
          @previous="previousPage"
        ></ListPagination>
      </div>
      <ListingScrollPane :listings="listings"></ListingScrollPane>
      <div class="menu">
        <div class="spacer" />
        <ListPagination
          :value="listingFilter.page"
          @next="nextPage"
          @previous="previousPage"
        ></ListPagination>
      </div>
    </div>
  </main>
</template>
<script setup lang="ts">
import ListPagination from "@/components/paginations/ListPagination.vue";
import { ref, watch } from "vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import { ListingFilter } from "@/types/listing";
import NavigationDrawer from "@/components/NavigationDrawer.vue";
import type { Category, ListingMinimal } from "@/service/models";
import FilterIcon from "@/components/icons/FilterIcon.vue";
import ListingFilterForm from "@/components/forms/ListingFilterForm.vue";

const listings = ref([] as ListingMinimal[]);
const categories = ref([] as Category[]);
const collapsed = ref(false);

const listingFilter = ref(new ListingFilter());

watch(
  listingFilter,
  () => {
    fetchEvents();
  },
  { deep: true }
);

const listing = {
  id: 1,
  title: "Airpods",
  summary: "Test men moren din er styggere ahahhah Lorem ipsum daler dat",
  price: 1,
  thumbnailUrl: "test",
  locationName: "Oslo, Høybråten",
  categoryName: "test",
} as ListingMinimal;

for (let i = 0; i < 20; i++) {
  listings.value.push(listing);
}

for (let i = 0; i < 5; i++) {
  categories.value.push({ name: "test" + i } as Category);
}

function nextPage() {
  listingFilter.value.page++;
}

function previousPage() {
  listingFilter.value.page--;
}

function fetchEvents() {
  console.table(listingFilter.value);
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
  margin-left: 20rem;
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
