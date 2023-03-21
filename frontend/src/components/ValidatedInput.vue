<script setup lang="ts">
import type { InputType } from "@/types/input";
import { computed, toRefs } from "vue";

const props = defineProps<{
  title: string;
  submitButtonClicked: boolean;
  modelValue: string;  // Data will be updated here
  placeholder?: string;
  inputType?: InputType;
}>();
const emit = defineEmits(['update:modelValue']);

const inputData = computed({
  get() {
    return props.modelValue;
  },
  set(value) {
    if (value) {
      document.querySelector('input')?.classList.remove('red-border');
    }
    emit('update:modelValue', value);
  }
});

const { title, submitButtonClicked, placeholder, inputType } = toRefs(props);

const isEmpty = computed(() => submitButtonClicked.value && inputData.value == "");
</script>

<template>
  <div class="wrapper">
    <h3><label for="title">{{ title }}</label></h3>
    <textarea v-if="inputType=='textarea'" :class="{'red-border': isEmpty, 'input-text': true}" v-model="inputData"></textarea>
    <input v-else :class="{'red-border': isEmpty, 'input-text': true}" v-model="inputData" :type="inputType || 'text'" :placeholder="placeholder" />
  </div>
</template>

<style scoped>
.wrapper {
  padding: 0 0.4rem;
}

.red-border {
  border: 1px solid red;
}

input[type="file"] {
  cursor: pointer;
}

input,
textarea {
  width: 100%;
  resize: none;
}
</style>