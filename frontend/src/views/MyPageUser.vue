<template>
  <MainContainer :collapsed="collapsed">
    <h1>Rediger profilen din</h1>
    <button class="button no" @click="$emit('update:collapsed', !collapsed)">Meny</button>
    <FullUserDetailsForm
      :first-name="user.firstName"
      :last-name="user.lastName"
      :email="user.email"
      button-title="ENDRE"
      v-model="user"
      @submit="updateUser"
      :password-field="changePassword"
    />
    <button class="button" style="margin-top: 1rem" @click="changePassword = !changePassword">
      Vil du bytte passord også?
    </button>
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

const success = ref("");
const errorMessage = ref("");
const sessionStore = useSessionStore();
const changePassword = ref(false);
const props = defineProps<{
  collapsed: boolean;
}>();
const emit = defineEmits(["update:collapsed"]);

const user = sessionStore.getUser() || ({} as UserBody);

function updateUser(user: UserBody) {
  const userApi = new UserApi();
  userApi
    .updateUser(user)
    .then((response) => {
      success.value = "Brukeren din ble oppdatert";
      sessionStore.authenticate(response.data);
      emit("update:collapsed", true);
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
</script>
