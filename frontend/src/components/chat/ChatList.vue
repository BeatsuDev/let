<template>
  <div v-if="chats === null && !chatFetchError">Henter chats...</div>
  <div v-else-if="chats?.length === 0">Ingen chats å vise...</div>
  <div v-else-if="chatFetchError">
    <AlertBox :message="'Feil ved henting av chatter: ' + chatFetchError.message"/>
  </div>
  <div v-else class="chat-list-wrapper">
    <div class="chat" v-for="chat in chats" @click="emit('chat-selected', chat)">
      <h3>[{{ chat.id }}]: {{ chat.listing.title }}</h3>
      <p>{{ chat.lastMessage?.content || "(ny chat)" }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import runAxios from "@/services/composable";
import { ChatApi } from "@/services/index";
import type { ChatOverview } from "@/types/chat";
import type { AxiosResponse } from "axios";
import AlertBox from "../dialogs/AlertBox.vue";

// Define APIs
const chatApi = new ChatApi();

// Define props
const { data: chats, error: chatFetchError } = runAxios<ChatOverview[]>(
  chatApi.getChats() as Promise<AxiosResponse<ChatOverview[]>>
);

// Define emits
const emit = defineEmits<{
  (event: "chat-selected", chat: ChatOverview) : void;
}>();

// Define refs

// Define computed values

// Define callback functions

// Other script logic
</script>

<style scoped>
.chat-list-wrapper {
  width: 100%;
}

.chat-list-wrapper > .chat:first-child {
  border-top: 1px solid #e1e1e1;
}

.chat-list-wrapper > .chat {
  border-bottom: 1px solid #e1e1e1;
  padding: 1rem;
  margin: 0;
  font-family: Inter;
}

.chat-list-wrapper > .chat:hover {
  background-color: #f1f1f1;
  cursor: pointer;
}

.chat-list-wrapper > .chat > p {
  margin: 0;
  color: #777;
  font-size: 0.9rem;
  font-weight: 300;
  font-style: italic;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>