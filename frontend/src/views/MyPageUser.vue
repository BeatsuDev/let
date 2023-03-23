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
    />
  </MainContainer>
</template>
<script setup lang="ts">
import FullUserDetailsForm from "@/components/FullUserDetailsForm.vue";
import MainContainer from "@/components/MainContainer.vue";
import { UserApi } from "@/service/apis/user-api";
import { useSessionStore } from "@/stores/sessionStore";
import type { UserBody } from "@/service";


const sessionStore = useSessionStore();
const props = defineProps<{
  collapsed: boolean;
}>();
const emit = defineEmits(["update:collapsed"]);

const user = sessionStore.getUser() || {} as UserBody;

function updateUser(user: UserBody) {
  const userApi = new UserApi();
  userApi
    .updateUser(user)
    .then((response) => {
      sessionStore.authenticate(response.data);
      emit("update:collapsed", true);
    })
    .catch((error) => {
      console.log(error);
    });
}

//const {data: user} = runAxios(userApi.getUserById(1));
</script>
