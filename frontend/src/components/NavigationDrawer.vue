<template>
  <div
      :class="{
      sidebar: true,
      'drawer-collapse': props.collapsed,
      'drawer-active': !props.collapsed,
    }"
  >
    <button style="margin-bottom: 1.5rem" @click="collapse" class="button"> <CollapseIcon class="button-icon" />Skjul</button>
    <h3 class="item">Kategori</h3>
    <div
        v-for="(category, index) in categories"
        class="category text-paragraph hide-overflow"
        :key="index"
        @click="updateCategory(category.name)"
    >
      {{ category.name }}
    </div>
    <h3 class="item">Søk</h3>
    <input type="search" v-model="value.search" class="input-text"/>
    <h3 class="item">Lokasjon</h3>
    <input type="search" class="input-text" v-model="value.search"/>
    <h3 class="item">Radius</h3>
    <input type="range" class="slider" v-model="value.radius" style="width: 100%"/>
    <div class="center-text">{{ props.modelValue.radius }}km</div>
  </div>
</template>
<script lang="ts" setup>
import {computed, PropType} from "vue";
import {ListingFilter} from "@/types/listing";
import type {Category} from "@/service/models";
import CollapseIcon from "@/components/icons/CollapseIcon.vue";

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
    const newFilter = {...props.modelValue, [value]: value};
    console.log(newFilter);
    emit("update:modelValue", newFilter);
  },
});

function collapse() {
  emit("update:collapsed", true);
}

function updateCategory(category: string) {
  const newFilter = {...props.modelValue, category};
  emit("update:modelValue", newFilter);
}
</script>
<style scoped>
.sidebar {
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  margin-top: 4rem;
  padding-top: 8rem;
  padding-left: 3rem;
  padding-right: 3rem;
  transition: 0.3s;
  overflow: hidden;
  background-color: var(--color-background);
}

.drawer-active {
  width: 20rem;
  border-right: 1px solid #e1e1e1;
}

.drawer-collapse {
  width: 0;
  padding-left: 0;
  padding-right: 0;
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

@media screen and (max-width: 600px) {
  .sidebar {
    margin-top: 0;
    padding-left: 1rem;
    padding-right: 1rem;
  }

  .drawer-collapse {
    width: 0;
    padding-left: 0;
    padding-right: 0;
  }
}
</style>
