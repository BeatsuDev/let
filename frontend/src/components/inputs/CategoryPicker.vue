<template>
  <div class="wrapper">
    <h3>
      <label for="category-picker">{{ title }}</label>
    </h3>
    <input
      v-model="inputData"
      :class="{ 'red-border': props.validationError }"
      class="input-text"
      :placeholder="props.placeholder"
      name="category-picker"
      ref="input"
      @focus="() => (showDropdown = true)"
      @blur="hide"
      autocomplete="off"
    />
    <div class="dropdown">
      <div v-if="!error && !categories">loading...</div>
      <div v-else-if="error" class="error">
        {{ error }}
      </div>
      <div v-else-if="matches" class="dropdown-content">
        <div
          v-for="category in categoriesToShow"
          :key="category.id"
          class="category"
          @click="() => (inputData = category.name)"
        >
          {{ category.name }}
        </div>
      </div>
    </div>
    <div v-if="props.validationError" id="error">
      {{ props.validationError.$message }}
    </div>
  </div>
</template>

<script lang="ts" setup>
import { type Category, CategoryApi } from "@/services/index";
import runAxios from "@/services/composable";
import { computed, ref } from "vue";

// Define apis
const categoryApi = new CategoryApi();

// Define props
const props = defineProps<{
  title: string;
  validationError?: any;
  modelValue?: Category; // The data that the user writes in the input will be updated here
  placeholder?: string;
  textInput: string;
}>();

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", value: number | undefined): void;
  (event: "update:textInput", value: string): void;
}>();

// Define refs;
const { data: categories, error } = runAxios(categoryApi.getCategories());
const showDropdown = ref(false);

const inputData = computed({
  get() {
    return props.textInput;
  },
  set(value) {
    // Always set the textInput
    emit("update:textInput", value);

    // Only update the v-model if the value is a valid category
    const category = categories.value?.find((c) => c.name === value);
    if (category) {
      emit("update:modelValue", category.id);
    } else {
      emit("update:modelValue", undefined);
    }
  },
});

const categoriesToShow = computed(() => {
  return (
    categories.value?.filter((c) => c.name != undefined && c.name?.indexOf(inputData.value) > -1) ||
    ([] as Category[])
  );
});

const matches = computed(() => {
  if (!showDropdown.value) return false;
  const show = categories.value?.find(
    (c) => c.name?.toLowerCase() === inputData.value?.toLowerCase()
  );
  console.log(show);
  return !show;
});

function hide() {
  setTimeout(() => {
    showDropdown.value = false;
  }, 200);
}
</script>

<style scoped>
.red-border {
  border: 1px solid red;
}
.dropdown-content {
  margin-top: -1.2rem;
}
#error {
  color: red;
  font-size: 0.9rem;
  font-style: italic;
}
</style>
