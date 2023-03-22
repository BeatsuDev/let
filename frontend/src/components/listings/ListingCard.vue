<template>
  <div class="card" @click="$emit('click', props.value.id)">
    <img class="thumbnail" :src="props.value.thumbnailUrl" loading="lazy" alt="thumbnail" />
    <div class="information">
      <div class="column">
        <div class="row italic row-margin">
          {{ props.value.locationName }}
        </div>
        <div class="row row-margin" style="padding-right: 10px">
          <div>{{ props.value.title }}</div>
          <div class="spacer" />
          <div>{{ props.value.price }}kr</div>
        </div>
        <div class="row">
          <div>{{ props.value.summary }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import type { ListingMinimal } from "@/service/models";

const props = defineProps({
  value: {
    type: Object as () => ListingMinimal,
    required: true,
  },
});

const emit = defineEmits(["click"]);
</script>
<style scoped>
.card {
  max-width: 15rem;
  font-family: Inter, sans-serif;
  height: fit-content;
  background-color: #fff;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  transition: width 0.5s ease-in-out;
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

@media screen and (max-width: 60rem) {
  .card {
    max-width: 100%;
    width: 100%;
  }
}
</style>
