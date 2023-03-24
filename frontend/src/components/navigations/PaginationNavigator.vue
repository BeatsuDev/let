<template>
  <button class="button" @click="previousPage">Forrige</button>
  <button class="button button-black mono" disabled>{{ props.modelValue }}</button>
  <button class="button" @click="nextPage">Neste</button>
  <span v-if="pages > 1" class="total">av {{ props.pages }}</span>
</template>

<script setup lang="ts">
// Define props and emits
const props = defineProps({
  modelValue: {
    type: Number,
    required: true,
  },
  pages: {
    type: Number,
    required: true,
  },
});

// Define emits
const emit = defineEmits(["update:modelValue"]);

// Define callback functions
function nextPage() {
  if (props.modelValue < props.pages) emit("update:modelValue", props.modelValue + 1);
}

function previousPage() {
  if (props.modelValue > 1) emit("update:modelValue", props.modelValue - 1);
}
</script>

<style>
.mono {
  min-width: 3rem;
}

.total {
  margin-left: 1rem;
  margin-top: auto;
  margin-bottom: auto;
  color: gray;
  font-family: Inter, sans-serif;
}
</style>
