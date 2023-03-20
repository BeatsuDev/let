<script lang="ts" setup>
import { ref } from "vue";

// Validate email
const validateEmail = (email: string) => {
  const re = /\S+@\S+\.\S+/;
  return re.test(email);
};

// Validate password
const validatePassword = (password: string) => {
  const re = /^(?=.*[A-Za-z])[A-Za-z\d]{6,}$/;
  return re.test(password);
};

function register() {
  if (!firstName.value || !lastName.value) {
    alert("Navn kan ikke være tomme!");
    return;
  }

  if (!validateEmail(email.value) || !validatePassword(password.value)) {
    // TODO: Better error handling
    alert("Invalid email or password");
    return;
  }

  alert("Registrering vellykket! (ikke egt, det skal fikses)");
}

// v-models
const firstName = ref("");
const lastName = ref("");
const email = ref("");
const password = ref("");

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
    <form @submit.prevent="register">
      <label for="first-name">Fornavn</label>
      <input class="input-text" type="text" id="first-name" v-model="firstName" placeholder="Ola"/>
      <label for="last-name">Etternavn</label>
      <input class="input-text" type="text" id="last-name" v-model="lastName" placeholder="Sørmann"/>
      <label for="email">Email</label>
      <input class="input-text" type="email" id="email" v-model="email" placeholder="ola.sormann@gmail.com"/>
      <label for="password">Passord</label>
      <input class="input-text" type="password" id="password" v-model="password" placeholder="sikker123"/>
      <button class="button button-black" type="submit">Registrer</button>
      <RouterLink to="/login">Jeg har en konto!</RouterLink>
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

label, input {
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