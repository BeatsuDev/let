<template>
  <div class="wrapper">
    <h2 id="title">{{ randomResponse }}</h2>
    <form @submit.prevent="login" class="form-container">
      <label for="email">Email</label>
      <input
        id="email"
        v-model="email"
        class="input-text"
        placeholder="ola.sormann@gmail.com"
        type="email"
        @input="clearError"
      />
      <label for="password">Passord</label>
      <input
        id="password"
        v-model="password"
        class="input-text"
        placeholder="sikker123"
        type="password"
        @input="clearError"
      />
      <button class="button button-black" type="submit">Logg in</button>
      <AlertBox v-if="errorMessage !== ''" :message="errorMessage" type="error"></AlertBox>
      <RouterLink to="/register">Har du ikke en konto?</RouterLink>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useSessionStore } from "@/stores/sessionStore";
import { UserApi } from "@/services/apis/user-api";
import router from "@/router";
import AlertBox from "@/components/dialogs/AlertBox.vue";

// Define API
const userApi = new UserApi();
const sessionStore = useSessionStore();

// Define refs
const errorMessage = ref("");
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

// Define callback functions
function login() {
  userApi
    .loginUser({
      email: email.value,
      password: password.value,
    })
    .then((response) => {
      sessionStore.authenticate(response.data);
    })
    .catch((error) => {
      if (error.response.status === 401) {
        errorMessage.value = "Ugyldig mail eller passord";
      } else {
        errorMessage.value = "En uventet feil oppstod, har du internettilgang?";
      }
    });
}

function clearError() {
  if (errorMessage.value) errorMessage.value = "";
}
</script>

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

label,
input {
  display: block;
  width: 100%;
  margin: 0;
  font-family: Inter, sans-serif;
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
