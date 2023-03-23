<template>
  <input
    class="input-text"
    :modelValue="textInput"
    :placeholder="placeholder"
  />
</template>

<script setup lang="ts">
import { CategoryApi } from "@/service/index";
import runAxios from "@/service/composable";
import { ref, computed } from "vue";

// Define apis
const categoryApi = new CategoryApi();

// Define props
const props = defineProps<{
  title: string;
  modelValue?: string; // The data that the user writes in the input will be updated here
  placeholder?: string;
}>();

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", value: string): void;
}>();

// Define refs;
const textInput = ref("");
const { data: categories, error } = runAxios(categoryApi.getCategories());

const inputData = computed({
  get() {
    return textInput.value;
  },
  set(value) {
    if (!categories.value?.length) {
      // Do not update the v-model if there are no categories
      return;
    }

    // Only update the v-model if the value is a valid category
  },
});


emit("update:modelValue", value);
</script>

<style scoped>

</style>