<template>
  <PrimaryButton value="Forrige" @click="previousPage" />
  <PrimaryButton :value="props.value" disabled />
  <PrimaryButton value="Neste" @click="nextPage" />
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
