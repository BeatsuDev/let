<template>
  <main class="chat-view-wrapper">
    <ChatList class="chat-list"/>
    <ChatContainer class="chat-container" />
  </main>
</template>

<script setup lang="ts">
import ChatList from "@/components/chat/ChatList.vue";
import ChatContainer from "@/components/chat/ChatContainer.vue";

// Define APIs
// const userApi = new UserApi();

// Define props

// Define emits

// Define refs

// Define computed values

// Define callback functions

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
  height: calc(100% - 7rem);
}

.chat-view-wrapper > .chat-container {
  margin-left: 15rem;
  height: calc(100vh - 7rem);
}
</style>