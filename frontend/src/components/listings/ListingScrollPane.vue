<template>
  <div class="main">
    <ListingsNotFound v-if="listings === undefined" />
    <ListingCard
      v-for="listing in props.listings"
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
import type { ListingMinimal } from "@/service/models";
import ListingsNotFound from "@/components/listings/ListingsNotFound.vue";

const props = defineProps({
  listings: {
    type: [Array] as PropType<ListingMinimal[]>,
    required: true,
  },
});

function goToListing(id: number) {
  router.push("/listing/" + id);
}
</script>
<style scoped>
.main {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  grid-gap: 20px;
  min-height: 40rem;
  transition: all ease-in-out 0.5s;
}
</style>
