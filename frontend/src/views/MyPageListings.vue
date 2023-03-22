<template>
  <MainContainer :collapsed="collapsed">
    <h1 class="text-one-line" style="height: 4rem">{{ title }}</h1>
    <PaginationView
      v-model="listingFilter.page"
      :total-pages="totalPages"
      @collapse="$emit('update:collapsed', !props.collapsed)"
      collapse
    >
      <ListingScrollPane :listings="listings" />
    </PaginationView>
  </MainContainer>
</template>
<script lang="ts" setup>
import { computed, ref, watch } from "vue";
import { ListingFilter } from "@/types/listing";
import type { InlineResponse200 } from "@/service/models";
import { ListingsApi } from "@/service/apis/listings-api";
import PaginationView from "@/components/paginations/PaginationView.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import router from "@/router";
import MainContainer from "@/components/MainContainer.vue";
import { useSessionStore } from "@/stores/sessionStore";

const listingApi = new ListingsApi();
const sessionStore = useSessionStore();

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
const title = ref("");
const props = defineProps({
  collapsed: {
    type: Boolean,
    required: true,
  },
});

getTitle();
fetchListings();

watch(router.currentRoute, () => {
  fetchListings();
  getTitle();
});

function getTitle() {
  if (router.currentRoute.value.name == "my-listings") {
    title.value = "Mine annonser";
  } else if (router.currentRoute.value.name == "my-bookmarked-listings") {
    title.value = "Bokmerkede annonser";
  }
}

function fetchListings() {
  const filters = listingFilter.value;
  listingApi
    .getListings(
      undefined,
      undefined,
      undefined,
      undefined,
      router.currentRoute.value.name == "my-listings" ? sessionStore.getUser().id : undefined,
      router.currentRoute.value.name == "my-bookmarked-listings" ? true : undefined,
      undefined,
      filters.page,
      50
    )
    .then((response) => {
      listingRequest.value = response.data;
    });
}
</script>
