<script lang="ts" setup>
import FullUserDetailsForm from "@/components/FullUserDetailsForm.vue";
import type { UserBody } from "@/service";
import { UserApi } from "@/service/index";
import { useSessionStore } from "@/stores/sessionStore";
import router from "@/router";
import { ref } from "vue";
import Alert from "@/components/forms/AlertBox.vue";
import AlertBox from "@/components/forms/AlertBox.vue";

const userApi = new UserApi();
const errorMessage = ref("");
const sessionStore = useSessionStore();

function register(fullUserData: UserBody) {
  userApi
    .createUser(fullUserData)
    .then((response) => {
      sessionStore.authenticate(response.data);
      console.log(response);
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

const responses = [
  "På tide å finne ting 🔍",
  "Klar til å finne drømmeplanten? 🌱",
  "En ny verden venter deg! 🌎",
  "Et steg nærmere å finne drømmehjemmet! 🤩",
];

const randomResponse = responses[Math.floor(Math.random() * responses.length)];
</script>

<template>
  <div class="wrapper">
    <h2 id="title">{{ randomResponse }}</h2>
    <div class="form-container">
      <FullUserDetailsForm buttonTitle="Registrer" @submit="register" />
      <AlertBox v-if="errorMessage" :error="errorMessage" />
      <RouterLink to="/login">Jeg har en konto!</RouterLink>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  text-align: start;
  max-width: 400px;
  margin: 0 auto;
}

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
