<template>
  <div class="card" @click="$emit('click', props.value.id)">
    <img
      class="thumbnail"
      src="https://assets.api.uizard.io/api/cdn/stream/c2f78838-2c8f-47b4-ad29-7547ae6e294e.jpg"
      alt="thumbnail"
    />
    <div class="information">
      <div class="column">
        <div class="row italic row-margin">
          {{ props.value.locationName }}
        </div>

        <div class="row row-margin" style="padding-right: 10px">
          <div class="text-paragraph">{{ props.value.title }}</div>
          <div class="spacer" />
          <div class="text-paragraph">{{ props.value.price }}kr</div>
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

const emit = defineEmits(["click"]);
</script>
<style scoped>
.card {
  width: 100%;
  font-family: Inter, sans-serif;
  height: fit-content;
  background-color: #fff;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
}

.card:hover {
  cursor: pointer;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  background-color: #f8f8f8;
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

.row-margin {
  margin-top: 0.5rem;
}

.italic {
  font-style: italic;
  color: #222222;
}
</style>
