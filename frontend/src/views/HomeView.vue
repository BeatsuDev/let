<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ListingFilterForm v-model="listingFilter" :categories="categories" />
    </NavigationDrawer>
    <div :class="{ content: true, active: !collapsed, collapsed: collapsed }">
      <h1 class="one-line" style="height: 4rem">Hva leter du etter i dag?</h1>
      <PaginationView
        v-model="listingFilter.page"
        :total-pages="totalPages"
        @collapse="collapsed = !collapsed"
        collapse
      >
        <ListingScrollPane :listings="listings" />
      </PaginationView>
    </div>
  </main>
</template>
<script setup lang="ts">
import { ref, watch } from "vue";
import { ListingFilter } from "@/types/listing";
import NavigationDrawer from "@/components/NavigationDrawer.vue";
import type { Category, ListingMinimal } from "@/service/models";
import ListingFilterForm from "@/components/forms/ListingFilterForm.vue";
import { ListingsApi } from "@/service/apis/listings-api";
import { CategoryApi } from "@/service/apis/category-api";
import PaginationView from "@/components/paginations/PaginationView.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";

const listings = ref([] as ListingMinimal[] | undefined);
const categories = ref([] as Category[]);
const collapsed = ref(false);
const totalPages = ref(1);
const listingFilter = ref(new ListingFilter());

const listingApi = new ListingsApi();
const categoryApi = new CategoryApi();

fetchCategories();
fetchListings();

watch(
  listingFilter,
  () => {
    fetchListings();
  },
  { deep: true }
);

function fetchCategories() {
  categoryApi.getCategories().then((response) => {
    categories.value = response.data;
  });
}

function fetchListings() {
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
