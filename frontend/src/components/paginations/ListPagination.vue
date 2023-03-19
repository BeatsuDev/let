<template>
  <PrimaryButton value="Forrige" @click="$emit('previous')" />
  <PrimaryButton :value="props.value" disabled />
  <PrimaryButton value="Neste" @click="$emit('next')" />
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
</script>
