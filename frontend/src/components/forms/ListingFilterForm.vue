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
  <h3 class="nav-title">Sted</h3>
  <LocationPicker v-model="value.location" @update:modelValue="value.page = 1" />
  <LocationMapPicker
    v-model="value.location"
    @update:modelValue="value.page = 1"
    :radius="parseInt(radiusValue)"
  ></LocationMapPicker>
  <h3 class="nav-title">Radius</h3>
  <input
    v-model="radiusValue"
    class="slider"
    style="width: 100%"
    type="range"
    @mouseup="
      value.radius = radiusValue;
      value.page = 1;
    "
  />
  <div class="center-text">{{ radiusValue }}km</div>
</template>

<script lang="ts" setup>
import { computed, type PropType, ref } from "vue";
import { ListingFilter } from "@/types/listing";
import type { Category } from "@/services/models";
import LocationPicker from "@/components/inputs/LocationPicker.vue";
import { InputHandler } from "@/utils/input-delay";
import LocationMapPicker from "@/components/inputs/LocationMapPicker.vue";

// Define props
const props = defineProps({
  modelValue: {
    type: ListingFilter,
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
const radiusValue = ref(10);

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
