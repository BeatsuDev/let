<script lang="ts" setup>
import { ref } from "vue";
import { useSessionStore } from "@/stores/sessionStore";
import { UserApi } from "@/service/apis/user-api";
import router from "@/router";
import Alert from "@/components/forms/AlertBox.vue";

const userApi = new UserApi();
const errorMessage = ref("");
const sessionStore = useSessionStore();

function login() {
  userApi
    .loginUser({
      email: email.value,
      password: password.value,
    })
    .then((response) => {
      sessionStore.authenticate(response.data);
      router.push("/");
    })
    .catch((error) => {
      if (error.response.status === 401) {
        errorMessage.value = "Ugyldig mail eller passord";
      } else {
        errorMessage.value = "En uventet feil oppstod, har du internettilgang?";
      }
    });
}

// v-models
const email = ref("");
const password = ref("");

const responses = [
  "Velkommen tilbake! 🤗",
  "På tide å lete 🔍",
  "Du er tilbake! 🎉",
  "Vi har savna deg 🥺",
  "Et steg nærmere å finne drømmehjemmet! 🤩",
  "Hva var nå passordet igjen... 🤔",
];

const randomResponse = responses[Math.floor(Math.random() * responses.length)];

function clearError() {
  if (errorMessage.value) errorMessage.value = "";
}
</script>

<template>
  <div class="wrapper">
    <h2 id="title">{{ randomResponse }}</h2>
    <form @submit.prevent="login">
      <label for="email">Email</label>
      <input
        class="input-text"
        type="email"
        id="email"
        v-model="email"
        @input="clearError"
        placeholder="ola.sormann@gmail.com"
      />
      <label for="password">Passord</label>
      <input
        class="input-text"
        type="password"
        id="password"
        v-model="password"
        @input="clearError"
        placeholder="sikker123"
      />
      <button class="button button-black" type="submit">Logg in</button>
      <Alert type="error" :error="errorMessage" v-if="errorMessage !== ''"></Alert>
      <RouterLink to="/register">Har du ikke en konto?</RouterLink>
    </form>
  </div>
</template>

<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

h2 {
  margin: 2rem 0 3rem 0;
}

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
