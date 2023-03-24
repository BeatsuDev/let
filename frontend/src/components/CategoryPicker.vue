<template>
  <div class="wrapper">
    <h3><label for="category-picker">{{ title }}</label></h3>
    <input
      name="category-picker"
      v-model="inputData"
      :placeholder="props.placeholder"
      :class="{ 'input-text': true, 'red-border': props.validationError }"
      @keyup="filterResults"
      @change="filterResults"
    />
    <div class="dropdown">
      <div v-if="!error && !categories">
        loading...
      </div>
      <div v-else-if="error" class="error">
        {{ error }}
      </div>
      <div v-else class="categories">
        <div
          v-for="category in categories"
          :key="category.id"
          class="category"
          @click="() => inputData = category.name!"
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

<script setup lang="ts">
import { CategoryApi, type Category } from "@/service/index";
import runAxios from "@/service/composable";
import { ref, computed, onMounted } from "vue";

// Define apis
const categoryApi = new CategoryApi();

// Define props
const props = defineProps<{
  title: string;
  validationError?: any;
  modelValue?: Category; // The data that the user writes in the input will be updated here
  placeholder?: string;
}>();

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", value: Category): void;
}>();

// Define refs;
const textInput = ref("");
const { data: categories, error } = runAxios(categoryApi.getCategories());

const inputData = computed({
  get() {
    return textInput.value;
  },
  set(value) {
    // Always set the textInput
    textInput.value = value;

    // Only update the v-model if the value is a valid category
    const category = categories.value?.find((c) => c.name === value);

    if (category) {
      emit("update:modelValue", { id: category.id, name: category.name });
    } else {
      emit("update:modelValue", {} as Category);
    }
  },
});

function changeWidth() {  
  const input = document.querySelector("input[name='category-picker']") as HTMLInputElement;
  const dropdown = document.querySelector(".categories") as HTMLDivElement;

  if (!input || !dropdown) {
    return;
  }

  dropdown.style.width = input.offsetWidth + "px";
}

function filterResults() {
  const input = document.querySelector("input[name='category-picker']") as HTMLInputElement;
  const dropdown = document.querySelector(".categories") as HTMLDivElement;

  if (!input || !dropdown) {
    return;
  }

  const filter = input.value.toUpperCase();
  const categoryDivs = dropdown.getElementsByTagName("div");

  for (let i = 0; i < categoryDivs.length; i++) {
    if (categoryDivs[i].innerText.toUpperCase().indexOf(filter) > -1) {
      categoryDivs[i].style.display = "";
    } else {
      categoryDivs[i].style.display = "none";
    }
  }

  // Hide the dropdown if the input matches the only category
  const categoryNames = categories.value?.map(c => c.name);

  if ([...categoryDivs].filter(c => c.style.display === "").length <= 1 && categoryNames?.includes(input.value)) {
    dropdown.style.display = "none";
  } else {
    if ([...categoryDivs].filter(c => c.style.display === "").length > 0) {
      dropdown.style.display = "";
    }
  }
}

// Code to ensure that the dropdown is the same width as the input element
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