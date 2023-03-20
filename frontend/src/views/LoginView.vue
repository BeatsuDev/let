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

</script>

<template>
  <div class="wrapper">
    <h2>Welcome back</h2>
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

input, label {
  display: block;
  width: 100%;
  margin: 10px 0;
  font-family: Inter;
}

input {
  padding: 20px;
  background-color: #eee;
  color: #030303;
  border: 0;
  line-height: 1.5rem;
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