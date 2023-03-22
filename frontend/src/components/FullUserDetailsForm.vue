<script setup lang="ts">
import type { UserBody } from "@/service";
import { reactive, type Ref } from "vue";
import ValidatedInput from "./ValidatedInput.vue";
import { required, email, minLength } from "@vuelidate/validators";
import useVuelidate from "@vuelidate/core";

const props = defineProps<{
  buttonTitle: string;
}>();

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
  password: { required, minLength: minLength(6) },
};

const validator = useVuelidate(rules, fullUserData);

const emit = defineEmits<{
  (event: "submit", fullUserData: UserBody): void;
}>();

async function submit() {
  let result = await validator.value.$validate();
  if (!result) return;
  emit("submit", fullUserData);
};
</script>

<template>
  <form @submit.prevent="submit">
    <ValidatedInput v-model="fullUserData.firstName" title="Fornavn" :error="validator.firstName.$errors[0]" placeholder="Ola"/>
    <ValidatedInput v-model="fullUserData.lastName" title="Etternavn" :error="validator.lastName.$errors[0]" placeholder="Sørmann"/>
    <ValidatedInput v-model="fullUserData.email" input-type="email" title="Email" :error="validator.email.$errors[0]" placeholder="ola.sormann@gmail.com"/>
    <ValidatedInput v-model="fullUserData.password" input-type="password" title="Passord" :error="validator.password.$errors[0]" placeholder="sikker123"/>
    <button class="button button-black" input-type="submit">{{ props.buttonTitle }}</button>
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

form >>> .wrapper {
  width: 100%;
  padding: 0;
}

form >>> label, input {
  display: block;
  width: 100%;
  margin: 0px 0;
  font-family: Inter;
}

form >>> label {
  margin-top: 1rem;
}

form >>> h3 {
  font-size: 0.9rem;
}

button {
  width: 100%;
  padding: 1.5rem 0;
  margin-top: 20px;
  font-size: 1rem;
}
</style>