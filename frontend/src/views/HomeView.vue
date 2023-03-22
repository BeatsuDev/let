<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ListingFilterForm v-model="listingFilter" :categories="categories" />
    </NavigationDrawer>
    <div :class="{ content: true, active: !collapsed, collapsed: collapsed }">
      <h1 class="one-line" style="height: 4rem">Hva leter du etter i dag?</h1>
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
          :pages="totalPages"
        ></ListPagination>
      </div>
      <ListingScrollPane :listings="listings"></ListingScrollPane>
      <div class="menu">
        <div class="spacer" />
        <ListPagination
          :value="listingFilter.page"
          :pages="totalPages"
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
import { ListingsApi } from "@/service/apis/listings-api";
import { CategoryApi } from "@/service/apis/category-api";

const listings = ref([] as ListingMinimal[] | undefined);
const categories = ref([] as Category[]);
const collapsed = ref(false);
const totalPages = ref(1);

const listingFilter = ref(new ListingFilter());

const listingApi = new ListingsApi();
const categoryApi = new CategoryApi();

categoryApi.getCategories().then((response) => {
  categories.value = response.data;
});

fetchEvents();

watch(
  listingFilter,
  () => {
    fetchEvents();
  },
  { deep: true }
);

function nextPage() {
  listingFilter.value.page++;
}

function previousPage() {
  listingFilter.value.page--;
}

function fetchEvents() {
  const filters = listingFilter.value;
  console.table(filters);
  listingApi
    .getListings(
      filters.search,
      filters.location,
      filters.location == undefined ? undefined : filters.radius,
      filters.category ? [filters.category] : undefined,
      undefined,
      undefined,
      undefined,
      filters.page,
      50
    )
    .then((response) => {
      listings.value = response.data.listings;
      if (!response.data.numberOfPages) {
        totalPages.value = 1;
      } else {
        totalPages.value = response.data.numberOfPages + 1;
      }
    });
}
</script>
<style scoped>
.menu {
  padding: 2rem;
  display: flex;
}

@media screen and (max-width: 1000px) {
  .one-line {
    white-space: nowrap;
    font-size: 5vw;
  }
}

main {
  width: 100%;
}
</style>
