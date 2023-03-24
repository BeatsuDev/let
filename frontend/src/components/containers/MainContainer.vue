<template>
  <div :class="{ content: true, active: !props.collapsed, collapsed: props.collapsed }">
    <slot></slot>
  </div>
</template>
<script lang="ts" setup>
import { defineProps } from "vue";
const props = defineProps({
  collapsed: {
    type: Boolean,
    default: true,
  },
});
</script>
<style scoped>
main {
  width: 100%;
}
</style>
