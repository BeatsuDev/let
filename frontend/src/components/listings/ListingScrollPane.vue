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

//Define props
const props = defineProps({
  listings: {
    type: ([Array] as PropType<ListingMinimal[]>) || undefined,
    default: undefined,
  },
  loading: {
    type: Boolean,
    default: false,
  },
});

//Callback functions
function goToListing(id: number) {
  router.push("/listing/" + id);
}
</script>
<style scoped>
.no-listing {
  display: flex;
  justify-content: center;
  height: 100%;
  width: 100%;
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
  text-align: center;
  justify-content: center;
}
</style>
