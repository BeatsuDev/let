<template>
  <button :disabled="props.disabled">{{ props.value }}</button>
</template>
<script lang="ts" setup>
const props = defineProps({
  value: {
    required: true,
  },
  disabled: {
    type: Boolean,
    required: false,
    default: false,
  },
});
</script>
