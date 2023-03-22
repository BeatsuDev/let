<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ListingFilterForm v-model="listingFilter" :categories="categories" />
    </NavigationDrawer>
    <div :class="{ content: true, active: !collapsed, collapsed: collapsed }">
      <h1 class="text-one-line" style="height: 4rem">Hva leter du etter i dag?</h1>
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
import { computed, ref, watch } from "vue";
import { ListingFilter } from "@/types/listing";
import NavigationDrawer from "@/components/NavigationDrawer.vue";
import type { InlineResponse200 } from "@/service/models";
import ListingFilterForm from "@/components/forms/ListingFilterForm.vue";
import { ListingsApi } from "@/service/apis/listings-api";
import { CategoryApi } from "@/service/apis/category-api";
import PaginationView from "@/components/paginations/PaginationView.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import runAxios from "@/service/composable";

const listingRequest = ref({ listings: [] } as InlineResponse200);
const listings = computed(() => {
  return listingRequest.value.listings;
});
const collapsed = ref(false);
const totalPages = computed(() => {
  if (listingRequest.value.numberOfPages) {
    return listingRequest.value.numberOfPages;
  }
  return 1;
});
const listingFilter = ref(new ListingFilter());

const listingApi = new ListingsApi();
const categoryApi = new CategoryApi();

const { data } = runAxios(categoryApi.getCategories());
const categories = data;

fetchListings();

watch(
  listingFilter,
  () => {
    fetchListings();
  },
  { deep: true }
);

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
      listingRequest.value = response.data;
    });
}
</script>
<style scoped>
main {
  width: 100%;
}
</style>
