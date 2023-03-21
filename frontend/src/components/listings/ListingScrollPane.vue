<template>
  <div class="main">
    <ListingCard v-for="listing in props.listings" :key="listing.id" :value="listing" />
  </div>
</template>
<script lang="ts" setup>
import ListingCard from "@/components/listings/ListingCard.vue";
import { Listing } from "@/types/listing";
import type { PropType } from "vue";

const props = defineProps({
  listings: {
    type: [Array] as PropType<ListingCard[]>,
    required: true,
  },
});
</script>
<style scoped>
.main {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  grid-gap: 20px;
  min-height: 40rem;
}
</style>
