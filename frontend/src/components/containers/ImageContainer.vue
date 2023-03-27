<template>
  <div style="max-width: 100%; margin-top: 1rem">
    <div>
      <CloseIcon
        v-if="props.deletable"
        @click="$emit('delete-image', modelValue)"
        style="cursor: pointer; width: 1rem; height: 1rem"
      >
        slett
      </CloseIcon>
      <img id="main-image" :src="imageUrls[modelValue]" alt="Thumbnail image" />
    </div>
    <div id="other-images">
      <img
        v-for="(imageUrl, index) in imageUrls"
        :key="index"
        :src="imageUrl"
        loading="lazy"
        @click="emit('update:modelValue', index)"
        alt="Selectable gallery image"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import CloseIcon from "@/components/icons/CloseIcon.vue";

const props = defineProps<{
  imageUrls: string[];
  modelValue: number;
  deletable?: boolean;
}>();

const emit = defineEmits<{
  (e: "update:modelValue", value: number): void;
  (e: "delete-image", value: number): void;
}>();
</script>
<style scoped>
#main-image {
  height: 60vh;
  max-width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  object-fit: contain;
  margin: 0 auto;
}

#other-images {
  margin-top: 1rem;
  display: flex;
  white-space: nowrap;
  width: 100%;
  overflow-x: auto;
}

#other-images > img {
  width: 100px;
  height: 100px;
  margin-right: 0.5rem;
  object-position: center;
  object-fit: cover;
  cursor: pointer;
}

.delete {
  position: relative;
  top: 0;
  left: 0;
}
</style>
