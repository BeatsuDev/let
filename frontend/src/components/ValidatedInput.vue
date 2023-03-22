<script setup lang="ts">
import { computed, toRefs } from "vue";

const props = defineProps<{
  title: string;
  modelValue: string; // The data that the user writes in the input will be updated here
  error: any;
  placeholder?: string;
  inputType?: "text" | "textarea" | "password" | "email";
}>();
const { title, modelValue, placeholder } = toRefs(props);
const emit = defineEmits(["update:modelValue"]);

const inputData = computed({
  get() {
    return modelValue.value;
  },
  set(value) {
    emit("update:modelValue", value);
  },
});
</script>

<template>
  <div class="wrapper">
    <h3>
      <label for="title">{{ title }}</label>
    </h3>
    <!-- Textarea if the props.inputType is textarea -->
    <textarea
      v-if="props.inputType === 'textarea'"
      :class="{ 'red-border': error }"
      class="input-text"
      v-model="inputData"
      :placeholder="placeholder"
    ></textarea>

    <!-- Otherwise it's just a normal input -->
    <input
      v-else
      :class="{ 'red-border': error }"
      class="input-text"
      v-model="inputData"
      :type="inputType"
      :placeholder="placeholder"
    />
    <div v-if="error" id="error">{{ error.$message }}</div>
  </div>
</template>

<style scoped>
.wrapper {
  padding: 0 0.4rem;
}

.red-border {
  animation: shake 0.2s ease-in-out 0s 2;
  border: 1px solid red;
}

input[type="file"] {
  cursor: pointer;
}

input,
textarea {
  width: 100%;
  resize: none;
  border: 1px solid white;
}

#error {
  color: red;
  font-size: 0.9rem;
  font-family: Inter, sans-serif;
}

@keyframes shake {
  0% {
    margin-left: 0;
  }
  25% {
    margin-left: 0.1rem;
  }
  75% {
    margin-left: -0.1rem;
  }
  100% {
    margin-left: 0;
  }
}
</style>
