<template>
  <div class="chat-list-wrapper">
    <div class="chat">
      <h3>Chat 1</h3>
      <p>Latest message</p>
    </div>
    <div class="chat">
      <h3>Chat 2</h3>
      <p>Some really long latest message</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Chat } from "@/types/chat";

// Define APIs

// Define props

// Define emits
const emit = defineEmits<{
    (event: "chat-selected", chat: Chat) : void;
}>();

// Define refs

// Define computed values

// Define callback functions

// Vue hooks

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

}

.chat-list-wrapper > .chat:hover {
  background-color: #f1f1f1;
  cursor: pointer;
}

.chat-list-wrapper > .chat > p {
  margin: 0;
  color: #777;
  font-family: Inter;
  font-size: 0.9rem;
  font-weight: 300;
  font-style: italic;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>