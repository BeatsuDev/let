<template>
  <MainContainer :collapsed="collapsed">
    <h1 class="text-one-line" style="height: 4rem">{{ title }}</h1>
    <PaginationView
      v-model="listingFilter.page"
      :total-pages="totalPages"
      collapse
      @collapse="$emit('update:collapsed', !props.collapsed)"
    >
      <ListingScrollPane :listings="listings">
        <NoBookmarkedListings v-if="isBookmarked" />
        <NoListingsCreated v-else />
      </ListingScrollPane>
    </PaginationView>
  </MainContainer>
</template>
<script lang="ts" setup>
import { computed, ref, watch } from "vue";
import { ListingFilter } from "@/types/listing";
import type { InlineResponse200 } from "@/services/models";
import { ListingsApi } from "@/services/apis/listings-api";
import PaginationView from "@/components/paginations/PaginationContainer.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import router from "@/router";
import MainContainer from "@/components/containers/MainContainer.vue";
import { useSessionStore } from "@/stores/sessionStore";
import NoBookmarkedListings from "@/components/listings/NoBookmarkedListings.vue";
import NoListingsCreated from "@/components/listings/NoListingsCreated.vue";

const listingApi = new ListingsApi();
const sessionStore = useSessionStore();
const isBookmarked = ref(false);

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

watch(router.currentRoute, () => {
  fetchListings();
  isBookmarked.value = router.currentRoute.value.name != "my-listings";
});

const title = computed(() => {
  if (router.currentRoute.value.name == "my-listings") {
    return "Mine annonser";
  } else if (router.currentRoute.value.name == "my-bookmarked-listings") {
    return "Bokmerkede annonser";
  }
  return "Feil URL...";
});

function fetchListings() {
  const filters = listingFilter.value;
  listingApi
    .getListings(
      undefined,
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
