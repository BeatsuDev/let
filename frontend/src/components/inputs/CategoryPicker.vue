<template>
  <div class="wrapper">
    <h3>
      <label for="category-picker">{{ title }}</label>
    </h3>
    <input
      v-model="inputData"
      :class="{ 'input-text': true, 'red-border': props.validationError }"
      :placeholder="props.placeholder"
      name="category-picker"
      ref="input"
    />
    <div>
      <div v-if="!error && !categories">loading...</div>
      <div v-else-if="error" class="error">
        {{ error }}
      </div>
      <div v-else-if="showDropdown" class="categories" ref="dropdown">
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
import { computed, onMounted, ref } from "vue";

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
const input = ref<HTMLInputElement | null>(null);
const dropdown = ref<HTMLDivElement | null>(null);

const inputData = computed({
  get() {
    return props.textInput;
  },
  set(value) {
    // Always set the textInput
    console.log(value);
    emit("update:textInput", value);

    // Only update the v-model if the value is a valid category
    const category = categories.value?.find((c) => c.name === value);
    if (category) {
      emit("update:modelValue", category.id);
      console.log("Set category to " + category);
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

const showDropdown = computed(() => {
  const show = categories.value?.find(
    (c) => c.name?.toLowerCase() === inputData.value?.toLowerCase()
  );
  console.log(show);
  return !show;
});

// Code to ensure that the dropdown is the same width as the input element
function changeWidth() {
  if (!input.value || !dropdown.value) {
    return;
  }
  dropdown.value.style.width = input.value.offsetWidth + "px";
}

onMounted(() => {
  setInterval(changeWidth, 100);
  window.addEventListener("resize", changeWidth);
});
</script>

<style scoped>
input ~ .dropdown > .categories {
  display: none;
}

input:focus ~ .dropdown > .categories {
  display: block;
}

.categories {
  position: absolute;
  z-index: 100;
  background-color: white;
  border: 1px solid #aaa;
  box-sizing: border-box;
  max-height: 100px;
  overflow-y: scroll;
}

.red-border {
  border: 1px solid red;
}

#error {
  color: red;
  font-size: 0.9rem;
  font-style: italic;
}
</style>
