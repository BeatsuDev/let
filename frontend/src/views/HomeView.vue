<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ListingFilterForm v-model="listingFilter" :categories="categories" />
    </NavigationDrawer>
    <MainContainer :collapsed="collapsed">
      <h1 class="text-one-line" style="height: 4rem">Hva leter du etter i dag?</h1>
      <PaginationView
        v-model="listingFilter.page"
        :total-pages="totalPages"
        collapse
        @collapse="collapsed = !collapsed"
      >
        <ListingScrollPane :listings="listings" :loading="loading">
          <ListingsNotFound></ListingsNotFound>
        </ListingScrollPane>
      </PaginationView>
    </MainContainer>
  </main>
</template>
<script lang="ts" setup>
import { computed, ref, watch } from "vue";
import { ListingFilter } from "@/types/listing";
import NavigationDrawer from "@/components/navigations/NavigationDrawer.vue";
import type { InlineResponse200 } from "@/services/models";
import ListingFilterForm from "@/components/forms/ListingFilterForm.vue";
import { ListingsApi } from "@/services/apis/listings-api";
import { CategoryApi } from "@/services/apis/category-api";
import PaginationView from "@/components/paginations/PaginationContainer.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import runAxios from "@/services/composable";
import MainContainer from "@/components/containers/MainContainer.vue";
import ListingsNotFound from "@/components/listings/ListingsNotFound.vue";

const listingRequest = ref({ listings: [] } as InlineResponse200);
const loading = ref(false);
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

const { data, error } = runAxios(categoryApi.getCategories());
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
  let id = setTimeout(() => {
    loading.value = true;
  }, 100);
  listingApi
    .getListings(
      filters.search,
      filters.location?.longitude,
      filters.location?.latitude,
      filters.location == undefined ? undefined : filters.radius,
      filters.category ? [filters.category] : undefined,
      undefined,
      undefined,
      undefined,
      filters.page,
      50
    )
    .then((response) => {
      clearTimeout(id);
      loading.value = false;
      listingRequest.value = response.data;
    });
}
</script>
