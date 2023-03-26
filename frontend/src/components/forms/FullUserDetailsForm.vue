<template>
  <form @submit.prevent="submit">
    <ValidatedInput
      id="first-name-input"
      v-model="user.firstName"
      :error="validator.firstName.$errors[0]"
      placeholder="Ola"
      title="Fornavn"
    />
    <ValidatedInput
      id="last-name-input"
      v-model="user.lastName"
      :error="validator.lastName.$errors[0]"
      placeholder="Sørmann"
      title="Etternavn"
    />
    <ValidatedInput
      id="email-input"
      v-model="user.email"
      :error="validator.email.$errors[0]"
      input-type="email"
      placeholder="ola.sormann@gmail.com"
      title="Email"
    />
    <ValidatedInput
      id="password-input"
      v-if="passwordField"
      v-model="user.password"
      :error="validator.password.$errors[0]"
      class="transition"
      input-type="password"
      placeholder="sikker123"
      title="Passord"
    />
    <ValidatedInput
      id="password-repeat-input"
      v-if="passwordField"
      v-model="passwordRepeat"
      :error="validator.passwordRepeat.$errors[0]"
      class="transition"
      input-type="password"
      placeholder="sikker123"
      title="Gjenta passord"
    />

    <button id="register-button" class="button button-black">{{ props.buttonTitle }}</button>
  </form>
</template>

<script setup lang="ts">
import type { UserBody, UserFull } from "@/services";
import { computed, ref } from "vue";
import ValidatedInput from "../inputs/ValidatedInput.vue";
import { helpers, minLength, required } from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";

// Define props
const props = defineProps<{
  modelValue: UserBody;
  passwordField: boolean;
  buttonTitle: string;
}>();

// Define emits
const emit = defineEmits<{
  (event: "submit"): void;
  (event: "update:modelValue", value: UserBody): void;
}>();

// Define refs
const passwordRepeat = ref("" as string | undefined);

// Define computed values
const user = computed({
  get() {
    return props.modelValue;
  },
  set(value: any) {
    const newUser = { ...props.modelValue, [value]: value };
    emit("update:modelValue", newUser);
  },
});

const inputFields = computed(() => {
  return { ...(user.value as UserFull), passwordRepeat: passwordRepeat.value };
});

const rules = computed(() => {
  return {
    firstName: { required: helpers.withMessage("Fornavn er påkrevd", required) },
    lastName: { required: helpers.withMessage("Etternavn er påkrevd", required) },
    email: { required: helpers.withMessage("E-post er påkrevd", required) },
    password: passwordValidators,
    passwordRepeat: passwordRepeatValidators,
  };
});

const passwordValidators = computed(() => {
  if (props.passwordField) {
    return {
      required: helpers.withMessage("Passord er påkrevd", required),
      minLength: helpers.withMessage("Passord må være minst 8 tegn", minLength(8)),
    };
  }
  return {};
});

const passwordRepeatValidators = computed(() => {
  if (props.passwordField) {
    return {
      required,
      sameAsPassword: helpers.withMessage("Passordene må være like", () => {
        return user.value.password === passwordRepeat.value;
      }),
    };
  }
  return {};
});

// Define callback functions
async function submit() {
  let result = await validator.value.$validate();
  if (!result) return;
  if (!props.passwordField) {
    user.value.password = undefined;
    passwordRepeat.value = undefined;
  }
  emit("submit");
}

const validator = useVuelidate(rules.value, inputFields as any);
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin: 0 auto;
}

form:deep(.wrapper) {
  width: 100%;
  padding: 0;
}

form:deep(label, input) {
  display: block;
  width: 100%;
  margin: 0;
  font-family: Inter, sans-serif;
}

form:deep(label) {
  margin-top: 1rem;
}

form:deep(h3) {
  font-size: 0.9rem;
}

button {
  width: 100%;
  padding: 1.5rem 0;
  margin-top: 20px;
  font-size: 1rem;
}
</style>
