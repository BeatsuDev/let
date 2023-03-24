<script setup lang="ts">
import type { UserBody, UserFull } from "@/service";
import { computed, reactive, ref } from "vue";
import ValidatedInput from "./ValidatedInput.vue";
import { required, email, minLength, sameAs } from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";

import { helpers } from "@vuelidate/validators";
const passwordRepeat = ref("" as string | undefined);
const props = defineProps<{
  modelValue: UserBody;
  passwordField: boolean;
  buttonTitle: string;
}>();

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
const validatePassword = computed(() => {
  if (props.passwordField) {
    return {
      required: helpers.withMessage("Passord er påkrevd", required),
      minLength: helpers.withMessage("Passord må være minst 8 tegn", minLength(8)),
    };
  }
  return {};
});

const validatePasswordRepeat = computed(() => {
  console.log(user.value.password, passwordRepeat.value);
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

const rules = {
  firstName: { required: helpers.withMessage("Fornavn er påkrevd", required) },
  lastName: { required: helpers.withMessage("Etternavn er påkrevd", required) },
  email: { required: helpers.withMessage("E-post er påkrevd", required) },
  password: validatePassword,
  passwordRepeat: validatePasswordRepeat,
};

const validator = useVuelidate(rules, inputFields as any);

const emit = defineEmits<{
  (event: "submit"): void;
  (event: "update:modelValue", value: UserBody): void;
}>();

async function submit() {
  let result = await validator.value.$validate();
  if (!result) return;
  if (!props.passwordField) {
    user.value.password = undefined;
    passwordRepeat.value = undefined;
  }
  emit("submit");
}
</script>

<template>
  <form @submit.prevent="submit">
    <ValidatedInput
      v-model="user.firstName"
      title="Fornavn"
      :error="validator.firstName.$errors[0]"
      placeholder="Ola"
    />
    <ValidatedInput
      v-model="user.lastName"
      title="Etternavn"
      :error="validator.lastName.$errors[0]"
      placeholder="Sørmann"
    />
    <ValidatedInput
      v-model="user.email"
      input-type="email"
      title="Email"
      :error="validator.email.$errors[0]"
      placeholder="ola.sormann@gmail.com"
    />
    <ValidatedInput
      v-model="user.password"
      input-type="password"
      title="Passord"
      :error="validator.password.$errors[0]"
      placeholder="sikker123"
      class="transition"
      v-if="passwordField"
    />
    <ValidatedInput
      v-model="passwordRepeat"
      input-type="password"
      title="Gjenta passord"
      :error="validator.passwordRepeat.$errors[0]"
      placeholder="sikker123"
      class="transition"
      v-if="passwordField"
    />

    <button class="button button-black">{{ props.buttonTitle }}</button>
  </form>
</template>

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
  margin: 0px 0;
  font-family: Inter;
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
