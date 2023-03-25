<template>
  <h3 class="drawer-item">Kategori</h3>
  <div
    v-for="(category, index) in categories"
    :key="index"
    :class="{ 'active-category': value.category === category.id }"
    class="category text-paragraph hide-overflow"
    @click="selectCategory(category)"
  >
    {{ category.name }}
  </div>
  <h3 class="nav-title">Søk</h3>
  <input v-model="searchValue" class="input-text" type="text" @input="search" />
  <h3 class="nav-title">Lokasjon</h3>
  <LocationPicker v-model="value.location" @update:modelValue="value.page = 1" />
  <h3 class="nav-title">Radius</h3>
  <input
    v-model="radius"
    class="slider"
    style="width: 100%"
    type="range"
    @mouseup="
      value.radius = radius;
      value.page = 1;
    "
  />
  <div class="center-text">{{ radius }}km</div>
</template>

<script lang="ts" setup>
import { computed, type PropType, ref } from "vue";
import { ListingFilter } from "@/types/listing";
import type { Category } from "@/services/models";
import LocationPicker from "@/components/inputs/LocationPicker.vue";
import { InputHandler } from "@/utils/input-delay";

// Define props
const props = defineProps({
  modelValue: {
    type: ListingFilter,
    required: true,
  },
  categories: {
    type: [Array] as PropType<Category[]>,
    required: true,
  },
});

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", newFilter: ListingFilter): void;
}>();

// Define refs
const inputDelay = new InputHandler(500);

const searchValue = ref("");
const radius = ref(props.modelValue.radius);

// Define computed values
const value = computed(() => props.modelValue);

// Define callback functions
function search() {
  inputDelay.searchWithDelay(function updateSearchField() {
    value.value.page = 1;
    value.value.search = searchValue.value;
  });
}
function selectCategory(category: Category) {
  value.value.page = 1;
  if (value.value.category === category.id) {
    value.value.category = undefined;
    return;
  }
  value.value.category = category.id;
}
</script>

<style scoped>
.category {
  transition: 0.3s;
  padding: 0.5rem;
  font-family: Inter, serif;
}

.category:hover {
  color: #282828;
  cursor: pointer;
  background-color: #f1f1f1;
}

.active-category {
  color: #282828;
  background-color: #f1f1f1;
}
</style>
