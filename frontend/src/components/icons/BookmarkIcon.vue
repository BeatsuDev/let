<script setup lang="ts">
import { toRefs } from "vue";

const props = defineProps<{
  bookmarked: boolean;
}>();

const emits = defineEmits<{
  (event: "toggleBookmark", newValue: boolean): void;
}>();

function bookmarkClickHandler(event: MouseEvent) {
  event.stopPropagation();
  emits("toggleBookmark", !props.bookmarked);
}
</script>

<template>
  <svg xmlns="http://www.w3.org/2000/svg" height="48" width="48" @click="bookmarkClickHandler">
    <path
      v-if="!bookmarked"
      d="M10 42V8.75q0-1.2.9-2.1.9-.9 2.1-.9h22q1.2 0 2.1.9.9.9.9 2.1V42l-14-6Zm3-4.55 11-4.65 11 4.65V8.75H13Zm0-28.7h22-11Z"
    />
    <path v-else d="M10 42V8.75q0-1.2.9-2.1.9-.9 2.1-.9h22q1.2 0 2.1.9.9.9.9 2.1V42l-14-6Z" />
  </svg>
</template>

<style scoped>
svg {
  cursor: pointer;
}
</style>
