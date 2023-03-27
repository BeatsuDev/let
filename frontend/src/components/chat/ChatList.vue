<template>
  <div v-if="chats?.length === 0">Ingen chats å vise...</div>
  <div v-else class="chat-list-wrapper">
    <div
      v-for="(chat, index) in chats"
      :key="index"
      class="chat"
      @click="emit('select-chat', chat)"
    >
      <h3>[{{ chat.id }}]: {{ chat.listing?.title }}</h3>
      <p>{{ chat.lastMessage?.content || "(ny chat)" }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ChatMinimal } from "@/services/index";
import { computed } from "vue";

// Define APIs

// Define props
const props = defineProps<{
  modelValue: Array<ChatMinimal>;
}>();
const chats = computed({
  get: () => props.modelValue,
  set: (chats) => emit("update:modelValue", chats),
});

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", chats: Array<ChatMinimal>): void;
  (event: "select-chat", chat: ChatMinimal): void;
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
