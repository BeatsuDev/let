<template>
  <div class="card">
    <img
      class="thumbnail"
      src="https://assets.api.uizard.io/api/cdn/stream/c2f78838-2c8f-47b4-ad29-7547ae6e294e.jpg"
      alt="thumbnail"
    />
    <div class="information">
      <div class="column">
        <div class="row">
          <div class="text-sub-header">{{ props.value.title }}</div>
          <div class="spacer" />
          <div class="text-sub-header">{{ props.value.price }}kr</div>
        </div>
        <div class="row">
          <div class="text-paragraph">{{ props.value.summary }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ListingCard } from "@/types/listing";

const props = defineProps({
  value: {
    type: ListingCard,
    required: true,
  },
});
</script>
<style scoped>
.card {
  width: 100%;
  height: fit-content;
  background-color: #fff;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
}

.thumbnail {
  width: 100%;
  height: 80%;
  object-fit: cover;
}

.information {
  height: 20%;
  width: 100%;
}
</style>
