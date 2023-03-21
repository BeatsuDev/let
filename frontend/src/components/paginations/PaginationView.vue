<template>
  <div class="menu">
    <button v-if="collapse" @click="$emit('collapse')" class="button">
      <FilterIcon class="button-icon" />
      Meny
    </button>
    <div class="spacer" />
    <ListPagination
        v-model="page"
        :pages="totalPages"
    ></ListPagination>
  </div>
  <slot></slot>
  <div class="menu">
    <div class="spacer" />
    <ListPagination
        v-model="page"
        :pages="totalPages"
    ></ListPagination>
  </div>
</template>
<script setup lang="ts">
import ListPagination from "@/components/paginations/Pagination.vue";
import FilterIcon from "@/components/icons/FilterIcon.vue";
import {computed} from "vue";
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

const page = computed({
  get() {
    return props.modelValue;
  },
  set(value: number) {
    emit("update:modelValue", value);
  },
})

const emit = defineEmits(["update:modelValue", "collapse"]);
</script>
<style scoped>
.menu {
  padding: 2rem;
  display: flex;
}
</style>
