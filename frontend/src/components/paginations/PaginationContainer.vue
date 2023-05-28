<template>
  <div class="menu">
    <button v-if="collapse" class="button" @click="$emit('collapse')">
      <FilterIcon class="button-icon" />
      Meny
    </button>
    <div class="spacer" />
    <ListPagination v-model="page" :pages="totalPages"></ListPagination>
  </div>
  <slot />
  <div class="menu">
    <div class="spacer" />
    <ListPagination v-model="page" :pages="totalPages"></ListPagination>
  </div>
</template>
<script lang="ts" setup>
import ListPagination from "@/components/navigations/PaginationNavigator.vue";
import FilterIcon from "@/components/icons/FilterIcon.vue";
import { computed } from "vue";

// Define props
const props = defineProps({
  totalPages: {
    type: Number,
    required: true,
  },
  collapse: {
    type: Boolean,
    default: false,
  },
  modelValue: {
    type: Number,
    required: true,
  },
});

// Define emits
const emit = defineEmits(["update:modelValue", "collapse"]);

// Define computed properties
const page = computed({
  get() {
    return props.modelValue;
  },
  set(value: number) {
    emit("update:modelValue", value);
  },
});
</script>
<style scoped>
.menu {
  padding: 2rem;
  display: flex;
}

@media (max-width: 768px) {
  .menu {
    padding: 0;
  }
}
</style>
