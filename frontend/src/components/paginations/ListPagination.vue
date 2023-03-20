<template>
  <button @click="previousPage" class="button">Forrige</button>
  <button disabled class="button button-black mono">{{ props.value }}</button>
  <button @click="nextPage" class="button">Neste</button>
</template>

<script lang="ts" setup>
import PrimaryButton from "@/components/buttons/PrimaryButton.vue";

const props = defineProps({
  value: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["next", "previous"]);

function nextPage() {
  emit("next");
}

function previousPage() {
  if (props.value > 1) {
    emit("previous");
  }
}
</script>
<style>
.mono {
  min-width: 3rem;
}
</style>
