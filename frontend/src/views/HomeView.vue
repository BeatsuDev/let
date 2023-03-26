<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ListingFilterForm :model-value="listingFilter" :categories="categories" />
    </NavigationDrawer>
    <MainContainer :collapsed="collapsed">
      <AlertBox v-if="errorMessage" :message="errorMessage" type="error" />
      <h1 class="text-one-line" style="height: 4rem; transition: 0.3s all">{{ message }}</h1>
      <PaginationView
        v-model="listingFilter.page"
        :total-pages="totalPages"
        collapse
        @collapse="collapsed = !collapsed"
      >
        <ListingScrollPane :listings="listings" :loading="loading">
          <ListingsNotFound />
        </ListingScrollPane>
      </PaginationView>
    </MainContainer>
  </main>
</template>
<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { ListingFilter } from "@/types/listing";
import NavigationDrawer from "@/components/navigations/NavigationDrawer.vue";
import type { Category, InlineResponse200 } from "@/services/models";
import ListingFilterForm from "@/components/forms/ListingFilterForm.vue";
import { ListingsApi } from "@/services/apis/listings-api";
import { CategoryApi } from "@/services/apis/category-api";
import PaginationView from "@/components/paginations/PaginationContainer.vue";
import ListingScrollPane from "@/components/listings/ListingScrollPane.vue";
import MainContainer from "@/components/containers/MainContainer.vue";
import ListingsNotFound from "@/components/listings/NoListingsFound.vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

//Define apis
const listingApi = new ListingsApi();
const categoryApi = new CategoryApi();

//Define refs
const listingFilter = ref(new ListingFilter());
const listingRequest = ref({ listings: [] } as InlineResponse200);
const loading = ref(false);
const errorMessage = ref("" as string);
const collapsed = ref(false);
const categories = ref([] as Category[]);
const message = ref("");

const messages = [
  "Hva leter du etter i dag? ☘️",
  "Velkommen til  ̶F̶I̶N̶N̶.̶n̶o̶  letno!",
  "På tide å finne drømmetingen! ",
  "Slår du gull i dag? 🪙",
  "Velkommen tilbake 👋",
];

fetchListings();
categoryApi.getCategories().then((response) => {
  categories.value = response.data;
});

// Define computed values
const listings = computed(() => {
  return listingRequest.value.listings;
});
const totalPages = computed(() => {
  if (listingRequest.value.totalPages) {
    return listingRequest.value.totalPages;
  }
  return 1;
});

// Callback functions
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
      filters.location?.latitude == undefined ? undefined : filters.radius,
      filters.category ? [filters.category] : undefined,
      undefined,
      undefined,
      undefined,
      filters.page,
      20
    )
    .then((response) => {
      if (errorMessage.value != "") errorMessage.value = "";
      listingRequest.value = response.data;
    })
    .catch(() => {
      errorMessage.value = "Kunne ikke hente nye annonser, prøv igjen senere";
    })
    .then(() => {
      loading.value = false;
      clearTimeout(id);
    });
}

// Vue hooks
onMounted(() => {
  message.value = messages[Math.floor(Math.random() * messages.length)];
});
watch(
  listingFilter,
  () => {
    fetchListings();
  },
  { deep: true }
);
</script>
