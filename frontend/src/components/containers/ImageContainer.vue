<template>
    <img id="main-image" :src="images[modelValue]"/>
    <div id="other-images">
      <img
          v-for="(image_url, index) in images"
          :key="index"
          :src="image_url"
          loading="lazy"
          @click="emit('update:modelValue', index)"
      />
    </div>
</template>
<script setup lang="ts">
import BackButton from "@/components/inputs/BackButton.vue";
import { ref, watch} from "vue";

const props = defineProps<{
  images: [string];
  modelValue: string;
}>();

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void;
}>();

const mainImage = ref(props.modelValue);

watch(() => props.modelValue, (newVal) => {
  mainImage.value = newVal;
})

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
  max-width: 100%;
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

</style>
