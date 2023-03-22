<template>
  <MainContainer :collapsed="collapsed">
    <h1>Rediger profilen din</h1>
    <button class="button no" @click="$emit('update:collapsed', !collapsed)">Meny</button>
    <FullUserDetailsForm button-title="ENDRE" v-model="user"></FullUserDetailsForm>
  </MainContainer>
</template>
<script setup lang="ts">
import FullUserDetailsForm from "@/components/FullUserDetailsForm.vue";
import MainContainer from "@/components/MainContainer.vue";
import { UserApi } from "@/service/apis/user-api";
import { useSessionStore } from "@/stores/sessionStore";
import type { UserBody } from "@/service";
import { computed } from "vue";

const userApi = new UserApi();

const sessionStore = useSessionStore();
const props = defineProps<{
  collapsed: boolean;
}>();
const emit = defineEmits(["update:collapsed"]);

const user = {
  firstName: "Ola",
  lastName: "Nordmann",
  password: "",
  email: "",
} as UserBody;

//const {data: user} = runAxios(userApi.getUserById(1));
</script>
