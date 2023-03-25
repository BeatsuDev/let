<template>
  <main>
    <NavigationDrawer v-model="collapsed">
      <ChatList class="chat-list" @chat-selected="loadChat"/>
    </NavigationDrawer>
    <MainContainer class="chat-view-wrapper" :collapse="collapsed">
      <ChatContainer class="chat-container" :chat="selectedChat"/>
    </MainContainer>
  </main>
</template>

<script setup lang="ts">
import { ref } from "vue";

import { ChatApi } from "@/services/index";
import type { ChatOverview, Chat } from "@/types/chat";

import ChatList from "@/components/chat/ChatList.vue";
import ChatContainer from "@/components/chat/ChatContainer.vue";
import MainContainer from "@/components/containers/MainContainer.vue";
import NavigationDrawer from "@/components/navigations/NavigationDrawer.vue";

// Define APIs
const chatApi = new ChatApi();

// Define props

// Define emits

// Define refs
const selectedChat = ref<Chat | null>(null);
const collapsed = ref(false);

// Define computed values

// Define callback functions
async function loadChat(chat: ChatOverview) {
  console.log("Loading chat with id: " + chat.id);

  const response = await chatApi.getChat(chat.id);
  selectedChat.value = response.data as Chat;
}

// Other script logic

// Load chats
</script>

<style scoped>
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
</style>