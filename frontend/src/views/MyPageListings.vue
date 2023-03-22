<template>
  <div :class="{ content: true, active: !props.collapsed, collapsed: props.collapsed }">
    <h1 class="text-one-line" style="height: 4rem">Dine annonser</h1>
    <PaginationView
      v-model="listingFilter.page"
      :total-pages="totalPages"
      @collapse="$emit('update:collapsed', !props.collapsed)"
      collapse
    >
      <ListingScrollPane :listings="listings" />
    </PaginationView>
  </div>
</template>
<script lang="ts" setup>
import { computed, ref, watch, watchEffect } from "vue";
import { ListingFilter } from "@/types/listing";
import type { InlineResponse200 } from "@/service/models";
import { ListingsApi } from "@/service/apis/listings-api";
import PaginationView from "@/components/paginations/PaginationView.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";

const listingRequest = ref({ listings: [] } as InlineResponse200);
const listings = computed(() => listingRequest.value.listings);
const totalPages = computed(() => {
  if (listingRequest.value.numberOfPages) {
    return listingRequest.value.numberOfPages;
  }
  return 1;
});
const listingFilter = ref(new ListingFilter());
const emit = defineEmits(["update:collapsed"]);
const props = defineProps({
  collapsed: {
    type: Boolean,
    required: true,
  },
});

fetchListings();
watch(
  listingRequest.value,
  () => {
    fetchListings();
  },
  { deep: true }
);

const listingApi = new ListingsApi();

function fetchListings() {
  const filters = listingFilter.value;
  listingApi
    .getListings(
      filters.search,
      undefined,
      undefined,
      undefined,
      1,
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
