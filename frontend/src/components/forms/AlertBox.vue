<template>
  <div :class="css">
    <h3>{{ props.message }}</h3>
  </div>
</template>
<script lang="ts" setup>
import { computed, defineProps } from "vue";

const props = defineProps({
  type: {
    type: String,
    default: "error",
  },
  message: {
    type: String,
  },
});

const css = computed(() => {
  return {
    main: true,
    success: props.type === "success",
    error: props.type === "error",
    warning: props.type === "warning",
    info: props.type === "info",
  };
});
</script>
<style scoped>
.main {
  width: 100%;
  padding: 1rem;
  margin-top: 1rem;
  font-family: Inter, sans-serif;
}

.success {
  color: white;
  background-color: #4caf50;
}

.error {
  color: white;
  background-color: #f44336;
}

.warning {
  color: white;
  background-color: #ff9800;
}

.info {
  color: white;
  background-color: #2196f3;
}
</style>
