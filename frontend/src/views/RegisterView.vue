<script lang="ts" setup>
import FullUserDetailsForm from "@/components/FullUserDetailsForm.vue";
import type { UserBody } from "@/service";

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

function register(fullUserData: UserBody) {
  if (!fullUserData.firstName || !fullUserData.lastName) {
    alert("Navn kan ikke være tomme!");
    return;
  }

  if (!validateEmail(fullUserData.email!) || !validatePassword(fullUserData.password!)) {
    // TODO: Better error handling
    alert("Invalid email or password");
    return;
  }

  alert("Registrering vellykket! (ikke egt, det skal fikses)");
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
      <FullUserDetailsForm buttonTitle="Registrer" @submit="register"/>
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
