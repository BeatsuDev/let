<template>
  <button @click="previousPage" class="button">Forrige</button>
  <button disabled class="button button-black mono">{{ props.modelValue }}</button>
  <button @click="nextPage" class="button">Neste</button
  ><span class="total" v-if="pages > 1">av {{ props.pages }}</span>
</template>

<script lang="ts" setup>
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

const emit = defineEmits(["update:modelValue"]);

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
