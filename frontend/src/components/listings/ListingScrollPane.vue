<template>
  <div class="main">
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

const props = defineProps({
  listings: {
    type: [Array] as PropType<ListingCard[]>,
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
}
</style>
