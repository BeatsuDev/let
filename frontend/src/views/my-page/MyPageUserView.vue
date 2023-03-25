<template>
  <MainContainer :collapsed="collapsed">
    <h1>Rediger profilen din</h1>
    <button class="button no" @click="$emit('update:collapsed', !collapsed)">
      <FilterIcon class="button-icon" />Meny
    </button>
    <FullUserDetailsForm
      v-model="userEdit"
      :password-field="changePassword"
      button-title="ENDRE"
      @input="clearMessages"
      @submit="updateUser"
    />
    <button
      class="button button-text"
      style="margin-top: 1rem"
      @click="changePassword = !changePassword"
    >
      Jeg vil bytte passord
    </button>
    <AlertBox
      v-if="user.email !== userEdit.email"
      message="Du må logge inn på nytt etter å ha endret eposten din."
      type="warning"
    />
    <AlertBox v-if="errorMessage !== ''" :message="errorMessage" type="error" />
    <AlertBox v-if="success !== ''" :message="success" type="success" />
  </MainContainer>
</template>
<script lang="ts" setup>
import FullUserDetailsForm from "@/components/forms/FullUserDetailsForm.vue";
import MainContainer from "@/components/containers/MainContainer.vue";
import { UserApi } from "@/services/apis/user-api";
import { useSessionStore } from "@/stores/sessionStore";
import type { UserBody } from "@/services";
import { computed, ref } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";
import router from "@/router";
import FilterIcon from "@/components/icons/FilterIcon.vue";

const success = ref("");
const errorMessage = ref("");
const sessionStore = useSessionStore();
const changePassword = ref(false);
const props = defineProps<{
  collapsed: boolean;
}>();
const emit = defineEmits(["update:collapsed"]);

const user = computed(() => sessionStore.getUser() || ({} as UserBody));
const userEdit = ref({
  firstName: user.value.firstName,
  lastName: user.value.lastName,
  email: user.value.email,
  password: "",
  id: user.value.id,
} as UserBody);

function updateUser() {
  if (!confirm("Er du sikker på at du vil endre brukeren din?")) {
    userEdit.value.email = user.value.email;
    userEdit.value.firstName = user.value.firstName;
    userEdit.value.lastName = user.value.lastName;
    return;
  }
  const userApi = new UserApi();
  userApi
    .updateUser(userEdit.value)
    .then((response) => {
      success.value = "Brukeren din ble oppdatert";

      if (user.value.email !== userEdit.value.email) {
        sessionStore.logOut();
        router.push("/login");
        return;
      }

      sessionStore.authenticate(response.data);
      console.log(response.data);
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
  if (errorMessage.value !== "" || success.value !== "") {
    errorMessage.value = "";
    success.value = "";
  }
}
</script>
