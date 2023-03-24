<template>
  <MainContainer :collapsed="collapsed">
    <h1>Rediger profilen din</h1>
    <button class="button no" @click="$emit('update:collapsed', !collapsed)">Meny</button>
    <FullUserDetailsForm
      button-title="ENDRE"
      @input="clearMessages"
      v-model="userEdit"
      @submit="updateUser"
      :password-field="changePassword"
    />
    <button class="button" style="margin-top: 1rem" @click="changePassword = !changePassword">
      Vil du bytte passord også?
    </button>
    <AlertBox
      v-if="user.email !== userEdit.email"
      type="warning"
      message="Du må logge inn på nytt etter å ha endret eposten din."
    />
    <AlertBox v-if="errorMessage !== ''" :message="errorMessage" type="error" />
    <AlertBox v-if="success !== ''" :message="success" type="success" />
  </MainContainer>
</template>
<script setup lang="ts">
import FullUserDetailsForm from "@/components/FullUserDetailsForm.vue";
import MainContainer from "@/components/MainContainer.vue";
import { UserApi } from "@/service/apis/user-api";
import { useSessionStore } from "@/stores/sessionStore";
import type { UserBody } from "@/service";
import { ref } from "vue";
import AlertBox from "@/components/forms/AlertBox.vue";
import router from "@/router";

const success = ref("");
const errorMessage = ref("");
const sessionStore = useSessionStore();
const changePassword = ref(false);
const props = defineProps<{
  collapsed: boolean;
}>();
const emit = defineEmits(["update:collapsed"]);

const user = sessionStore.getUser() || ({} as UserBody);
const userEdit = ref({
  firstName: user.firstName,
  lastName: user.lastName,
  email: user.email,
  password: "",
  id: user.id,
} as UserBody);

function updateUser() {
  const userApi = new UserApi();
  userApi
    .updateUser(userEdit.value)
    .then((response) => {
      success.value = "Brukeren din ble oppdatert";
      if (user.email !== userEdit.value.email) {
        sessionStore.logOut();
        router.push("/login");
        return;
      }

      sessionStore.authenticate(response.data);
      changePassword.value = false;
    })
    .catch((error) => {
      if (error.response.status === 400) {
        errorMessage.value = "Noe gikk galt";
      } else if (error.response.status === 401) {
        errorMessage.value = "Du har ikke tilgang til å gjøre dette";
      } else if (error.response.status === 409) {
        errorMessage.value = "E-posten du endrer til er registrert på en annen bruker";
      } else {
        errorMessage.value = "En uventet feil oppstod. Prøv igjen senere";
      }
    });
}

function clearMessages() {
  console.log("hei");
  if (errorMessage.value !== "" || success.value !== "") {
    errorMessage.value = "";
    success.value = "";
  }
}
</script>
