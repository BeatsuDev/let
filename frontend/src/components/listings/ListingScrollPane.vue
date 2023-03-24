<template>
  <slot v-if="listings === undefined && !loading" class="no-listing" />
  <div class="main">
    <h1 v-if="loading" class="loading center-text">Laster inn</h1>
    <ListingCard
      v-for="listing in props.listings"
      v-else
      :key="listing.id"
      :value="listing"
      @click="goToListing"
    />
  </div>
</template>
<script lang="ts" setup>
import ListingCard from "@/components/listings/ListingCard.vue";
import type { PropType } from "vue";
import router from "@/router";
import type { ListingMinimal } from "@/services/models";

const props = defineProps({
  listings: {
    type: [Array] as PropType<ListingMinimal[]>,
    required: true,
  },
  loading: {
    type: Boolean,
    default: false,
  },
});

function goToListing(id: number) {
  router.push("/listing/" + id);
}
</script>
<style scoped>
.no-listing {
  margin-top: 10rem;
  margin-bottom: auto;
}

.main {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  grid-gap: 20px;
  min-height: 40rem;
  width: 100%;
  transition: 2s;
}

.loading {
  margin: auto;
}
</style>
