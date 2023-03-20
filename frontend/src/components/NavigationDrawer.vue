<template>
  <div
    :class="{
      sidebar: true,
      'drawer-collapse': props.collapsed,
      'drawer-active': !props.collapsed,
    }"
  >
    <button style="margin-bottom: 1.5rem" @click="collapse">Skjul</button>
    <div class="text-sub-header">Kategori</div>
    <div v-for="(category, index) in categories" class="text-paragraph hide-overflow" :key="index">
      {{ category.name }}
    </div>
    <div class="text-sub-header">Søk</div>
    <input type="search" v-model="value.search" />
    <div class="text-sub-header">Lokasjon</div>
    <input type="search" v-model="value.search" />
    <div class="text-sub-header">radius: {{ value.radius }}</div>
    <input type="range" v-model="value.radius" style="width: 100%" />
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
    emit("update:modelValue", newFilter);
  },
});

function collapse() {
  emit("update:collapsed", true);
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
  width: 15rem;
}

.drawer-collapse {
  width: 0;
  padding-left: 0;
}

.hide-overflow {
  overflow: hidden;
}
</style>
