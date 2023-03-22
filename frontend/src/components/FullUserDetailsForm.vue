<script setup lang="ts">
import type { UserBody } from "@/service";
import { reactive, type Ref } from "vue";
import ValidatedInput from "./ValidatedInput.vue";
import { required, email } from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";

const fullUserData = reactive({
  firstName: "",
  lastName: "",
  email: "",
  password: "",
});

const rules = {
  firstName: { required },
  lastName: { required },
  email: { required, email },
  password: { required },
};

const validator = useVuelidate(rules, fullUserData);

const emit = defineEmits<{
  (event: "submit", fullUserData: UserBody): void;
}>();

const submit = () => {
  emit("submit", fullUserData);
};
</script>

<template>
  <form @submit.prevent="submit">
    <ValidatedInput v-model="fullUserData.email" title="Fornavn" :error="validator.firstName.$errors[0]" placeholder="Ola"/>
    <ValidatedInput v-model="fullUserData.email" title="Etternavn" :error="validator.lastName.$errors[0]" placeholder="Sørmann"/>
    <ValidatedInput v-model="fullUserData.email" title="Email" :error="validator.email.$errors[0]" placeholder="ola.sormann@gmail.com"/>
    <ValidatedInput v-model="fullUserData.password" title="Passord" :error="validator.password.$errors[0]" placeholder="sikker123"/>
  </form>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  margin-bottom: 4rem;
}

label,
input {
  display: block;
  width: 100%;
  margin: 0px 0;
  font-family: Inter;
}

label {
  margin-top: 1rem;
}

button {
  width: 100%;
  padding: 1.5rem 0;
  margin-top: 20px;
  font-size: 1rem;
}

a {
  text-decoration: underline;
  color: #030303;
  font-style: italic;
  font-size: 0.8rem;
  align-self: flex-end;
}
</style>