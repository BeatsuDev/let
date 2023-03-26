<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ChatList class="chat-list" @chat-selected="loadChat"/>
    </NavigationDrawer>
    <div class="toggle-sidebar" @click="collapsed = false" :style="{opacity: Number(collapsed)}">
      <FilterIcon class="button-icon" /> Meny
    </div>
    <MainContainer class="chat-view-wrapper" :collapse="collapsed">
      <ChatContainer :class="{'chat-container': true, 'collapsed-margin': collapsed}" :chat="selectedChat"/>
    </MainContainer>
  </main>
</template>

<script setup lang="ts">
import { ref } from "vue";

import { ChatApi } from "@/services/index";
import router from "@/router";
import type { ChatOverview, Chat } from "@/types/chat";

import ChatList from "@/components/chat/ChatList.vue";
import FilterIcon from "@/components/icons/FilterIcon.vue";
import ChatContainer from "@/components/chat/ChatContainer.vue";
import MainContainer from "@/components/containers/MainContainer.vue";
import NavigationDrawer from "@/components/navigations/NavigationDrawer.vue";

// Define APIs
const chatApi = new ChatApi();

// Define refs
const selectedChat = ref<Chat | null>(null);
const collapsed = ref(false);

// Define callback functions
async function loadChat(chat: ChatOverview) {
  console.log("Loading chat with id: " + chat.id);

  const response = await chatApi.getChat(chat.id);
  selectedChat.value = response.data as Chat;

  router.push({ name: "chat", params: { chatId: chat.id } });
}

// Load chats
// Get the chat ID
let chatId = Number(router.currentRoute.value.params.chatId);
console.log("Chat ID: " + chatId);
chatApi.getChat(chatId)
  .then(response => response.data as Chat)
  .then(chat => {
    selectedChat.value = chat
  });
</script>

<style scoped>
.collapsed-margin {
  margin-left: 0 !important;
}

.toggle-sidebar {
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 6rem;
  left: 9rem;
  z-index: 100;
  padding: 0.6rem 1rem;
  font-size: 0.8rem;
  font-family: Inter;
  background-color: rgb(240, 240, 240);
  cursor: pointer;
  opacity: 0;
  transition-duration: 500ms;
}

.chat-view-wrapper {
  height: 100%;
  display: flex;
  flex-direction: row;
}

.chat-view-wrapper > .chat-list {
  max-width: 400px;
  width: 15rem;
  position: fixed;
  height: calc(100% - 6.6rem);
}

.chat-view-wrapper > .chat-container {
  margin-left: 15rem;
  height: calc(100vh - 7rem);
}

@media (max-width: 600px) {
  .toggle-sidebar {
    left: 4rem;
  }
}
</style>