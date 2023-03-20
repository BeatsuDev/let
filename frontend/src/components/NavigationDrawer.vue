<template>
  <div
    :class="{
      sidebar: true,
      'drawer-collapse': props.collapsed,
      'drawer-active': !props.collapsed,
    }"
  >
    <button style="margin-bottom: 1.5rem" @click="collapse" class="button">Skjul</button>
    <div class="item text-sub-header">Kategori</div>
    <div
      v-for="(category, index) in categories"
      class="category text-paragraph hide-overflow"
      :key="index"
      @click="updateCategory(category.name)"
    >
      {{ category.name }}
    </div>
    <div class="item text-sub-header">Søk</div>
    <input type="search" v-model="value.search" />
    <div class="item text-sub-header">Lokasjon</div>
    <input type="search" v-model="value.search" />
    <div class="item text-sub-header">Radius</div>
    <input type="range" v-model="value.radius" style="width: 100%" />
    <div class="center">{{ props.modelValue.radius }}km</div>
  </div>
</template>
<script lang="ts" setup>
import { computed, PropType } from "vue";
import { Category, ListingFilter } from "@/types/listing";

const props = defineProps({
  collapsed: {
    type: Boolean,
    default: false,
  },
  modelValue: {
    type: ListingFilter,
    required: true,
  },
  categories: {
    type: [Array] as PropType<Category[]>,
    required: true,
  },
});

const emit = defineEmits(["update:modelValue", "update:collapsed"]);

const value = computed({
  get() {
    return props.modelValue;
  },
  set(value: string) {
    const newFilter = { ...props.modelValue, [value]: value };
    console.log(newFilter);
    emit("update:modelValue", newFilter);
  },
});

function collapse() {
  emit("update:collapsed", true);
}

function updateCategory(category: string) {
  const newFilter = { ...props.modelValue, category };
  emit("update:modelValue", newFilter);
}
</script>
<style scoped>
.sidebar {
  margin-top: 4rem;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  padding-top: 7.5rem;
  padding-left: 3rem;
  transition: width 0.5s;
  overflow: hidden;
  background-color: var(--color-background);
}

.drawer-active {
  width: 20rem;
}

.drawer-collapse {
  width: 0;
  padding-left: 0;
}

.hide-overflow {
  overflow: hidden;
}

.item {
  margin-top: 0.8rem;
  padding: 0.2rem;
}

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

.center {
  text-align: center;
  font-family: Inter, sans-serif;
  width: 100%;
}
</style>
