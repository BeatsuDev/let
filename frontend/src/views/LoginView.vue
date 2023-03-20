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

function login() {
  if (!validateEmail(email.value) || !validatePassword(password.value)) {
    // Better error handling
    alert("Invalid email or password");
    return;
  }

}

// v-models
const email = ref("");
const password = ref("");

const responses = [
  "Welcome back! 🤗",
  "Time to find some stuff 🔍",
  "You're back! 🎉",
  "We missed you! 🥺",
  "One step closer to finding your future home! 🤩",
  "What was that password again... 🤔",
];

const randomResponse = responses[Math.floor(Math.random() * responses.length)];
</script>

<template>
  <div class="wrapper">
    <h2 id="title">{{ randomResponse }}</h2>
    <form @submit.prevent="login">
      <label for="email">Email</label>
      <input type="email" id="email" v-model="email" placeholder="myman@gmail.com"/>
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" placeholder="verysecure123"/>
      <button type="submit">Become a leter</button>
      <RouterLink to="/register">Don't have an account?</RouterLink>
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
  margin-top: 30px;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  min-height: 400px;
  max-width: 400px;
  margin: 0 auto;
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

input {
  padding: 1.2rem;
  background-color: #eee;
  color: #030303;
  font-size: 1rem;
  border: 0;
}

input:focus {
  outline: 0;
}

button {
  width: 100%;
  border: 0;
  background-color: black;
  color: white;
  padding: 1.5rem 0;
  margin-top: 20px;
  cursor: pointer;
}

a {
  text-decoration: underline;
  color: #030303;
  font-style: italic;
  font-size: 0.8rem;
  align-self: flex-end;
}
</style>