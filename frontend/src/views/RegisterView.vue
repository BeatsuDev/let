<template>
  <div class="wrapper">
    <h2 id="title">{{ randomResponse }}</h2>
    <div class="form-container">
      <FullUserDetailsForm
        v-model="user"
        buttonTitle="Registrer"
        password-field
        @submit="register"
      />
      <AlertBox v-if="errorMessage" :message="errorMessage" />
      <RouterLink to="/login">Jeg har en konto!</RouterLink>
    </div>
  </div>
</template>

<script lang="ts" setup>
import FullUserDetailsForm from "@/components/forms/FullUserDetailsForm.vue";
import type { UserBody } from "@/services/index";
import { UserApi } from "@/services/index";
import { useSessionStore } from "@/stores/sessionStore";
import router from "@/router";
import { ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

//Define API
const userApi = new UserApi();
const sessionStore = useSessionStore();

//Define refs
const user = ref({} as UserBody);
const errorMessage = ref("");

const responses = [
  "På tide å finne ting 🔍",
  "Klar til å finne drømmeplanten? 🌱",
  "En ny verden venter deg! 🌎",
  "Et steg nærmere å finne drømmehjemmet! 🤩",
];

const randomResponse = responses[Math.floor(Math.random() * responses.length)];

//Define callbacks
function register() {
  userApi
    .createUser(user.value)
    .then((response) => {
      sessionStore.authenticate(response.data);
      router.push("/");
    })
    .catch((error) => {
      if (error.response.status === 409) {
        errorMessage.value = "Denne eposten er allerede registrert";
      } else {
        errorMessage.value = "En uventet feil oppstod, har du internettilgang?";
      }
    });
}
</script>

<style scoped>
.wrapper {
  text-align: center;
}

h2 {
  margin: 2rem auto 3rem auto;
}

a {
  text-decoration: underline;
  color: #030303;
  font-style: italic;
  font-size: 0.8rem;
  align-self: flex-end;
  margin-right: 0.4rem;
}
</style>
