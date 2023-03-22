<template>
  <div
    v-for="(category, index) in categories"
    class="category text-paragraph hide-overflow"
    :key="index"
    :class="{ 'active-category': value.category === category.id }"
    @click="value.category = category.id"
  >
    {{ category.name }}
  </div>
  <h3 class="nav-title">Søk</h3>
  <input type="text" v-model="searchValue" @input="search" class="input-text" />
  <h3 class="nav-title">Lokasjon</h3>
  <LocationPicker />
  <h3 class="nav-title">Radius</h3>
  <input
    type="range"
    class="slider"
    v-model="radius"
    @mouseup="value.radius = radius"
    style="width: 100%"
  />
  <div class="center-text">{{ radius }}km</div>
</template>
<script lang="ts" setup>
import { computed, PropType, ref } from "vue";
import { ListingFilter } from "@/types/listing";
import type { Category } from "@/service/models";
import LocationPicker from "@/components/forms/LocationPicker.vue";
import { InputHandler } from "@/util/input-delay";

const emit = defineEmits(["update:modelValue"]);

const inputDelay = new InputHandler(500);

const searchValue = ref("");

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
const radius = ref(props.modelValue.radius);

const value = computed({
  get(): ListingFilter {
    return props.modelValue;
  },
  set(value: string) {
    const newFilter = { ...props.modelValue, [value]: value } as ListingFilter;
    console.log(newFilter);
    emit("update:modelValue", newFilter);
  },
});
function search() {
  inputDelay.searchWithDelay(function updateSearchField() {
    value.value.search = searchValue.value;
  });
}
</script>
<style>
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
