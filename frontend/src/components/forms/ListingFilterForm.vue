<template>
  <div
      v-for="(category, index) in categories"
      class="category text-paragraph hide-overflow"
      :key="index"
      :class="{ 'active-category': value.category === category.name }"
      @click="value.category = category.name"
  >
    {{ category.name }}
  </div>
  <h3 class="drawer-item">Søk</h3>
  <input type="search" v-model="value.search" class="input-text"/>
  <h3 class="drawer-item">Lokasjon</h3>
  <input type="search" class="input-text" v-model="value.search"/>
  <h3 class="drawer-item">Radius</h3>
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
import {computed, PropType, ref} from "vue";
import {ListingFilter} from "@/types/listing";
import type {Category} from "@/service/models";

const emit = defineEmits(["update:modelValue"]);

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
  get() {
    return props.modelValue;
  },
  set(value: string) {
    const newFilter = {...props.modelValue, [value]: value} as ListingFilter;
    console.log(newFilter);
    emit("update:modelValue", newFilter);
  },
});
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

.hide-overflow {
  overflow: hidden;
}
</style>
