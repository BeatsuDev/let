<template>
  <div class="chat-container-wrapper">
    <div class="chat-title-bar">
      <h3 v-if="!chat">Ingen chat valgt</h3>
      <h3 v-else>
        Chat for "{{ chat.listing.title }}" med
        {{
          useSessionStore().getUser()?.id === chat.seller.id
            ? chat.buyer.firstName
            : chat.seller.firstName
        }}
      </h3>
    </div>

    <div ref="messages" class="chat-messages-container">
      <div class="chat-messages">
        <div v-if="chat === null || chat.messages.length === 0" class="no-chats">
          <h2>Ingen meldinger å vise...</h2>
        </div>
        <MessageBox
          class=".chat-message"
          v-else
          v-for="(message, index) in chat.messages"
          :key="index"
          :message="message"
          :chat="chat"
          :sender="loggedInUser"
        />
      </div>
    </div>

    <form @submit.prevent="sendMessage" class="chat-input-container">
      <input v-model="chatMessageInput" id="chat-input" class="chat-input" type="text" />
      <button id="send-chat-button" class="chat-send-button">Send</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import type { ComputedRef } from "vue";
import { computed, nextTick, onMounted, ref, watch } from "vue";

import { useSessionStore } from "@/stores/sessionStore";

import { ChatApi, ChatFull, CreateMessage, Sender } from "@/services/index";
import MessageBox from "@/components/chat/MessageBox.vue";

// Define APIs
const chatApi = new ChatApi();

// Define props
const props = defineProps<{
  modelValue: ChatFull;
}>();
const chat = computed({
  get: () => props.modelValue,
  set: (chat) => {
    emit("update:modelValue", chat);
  },
});

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", newChat: ChatFull): void;
}>();

// Define refs
const chatMessageInput = ref("");
const messages = ref<null | HTMLDivElement>(null);

// Define computed values
const loggedInUser: ComputedRef<Sender> = computed(() => {
  return useSessionStore().getUser()?.id === chat.value.buyer?.id ? Sender.BUYER : Sender.SELLER;
});

// Define callback functions
async function sendMessage() {
  if (chatMessageInput.value === "") {
    return;
  }

  await chatApi
    .sendMessage(chat.value.id!, {
      content: chatMessageInput.value,
    } as CreateMessage)
    .then((response) => {
      chat.value = response.data as ChatFull;
    })
    .catch(() => alert("Kunne ikke sende meldingen"));
  chatMessageInput.value = "";
}

// Vue hooks
function scrollDown() {
  if (messages.value) messages.value.scrollTop = messages.value.scrollHeight;
}

onMounted(scrollDown);

watch(chat, () => {
  nextTick(scrollDown);
});

// Other script logic
</script>

<style scoped>
.no-chats {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.chat-container-wrapper {
  display: flex;
  flex-direction: column;
  background-color: #fcfcfc;
  width: 100%;
  height: 100%;
  border: 1px solid #e1e1e1;
  border-bottom: none;
}

.chat-title-bar,
.chat-messages-container,
.chat-input-container {
  width: 100%;
}

.chat-messages-container {
  flex-grow: 1; /* Take up remaining space */
  overflow-y: auto; /* Enable vertical scrolling */
  background-color: #f1f1f1;
}

.chat-messages {
  display: flex;
  flex-direction: column-reverse; /* Stack child elements vertically */
  flex-wrap: wrap; /* Allow child elements to wrap to multiple rows */
  align-content: flex-start; /* Align child elements at the start of the container */
  justify-content: end;
}

.chat-title-bar {
  padding: 1rem;
  text-align: center;
  border-bottom: 1px solid #e1e1e1;
}

/* Chat input */
.chat-input-container {
  display: flex;
  flex-direction: row;
  padding: 1rem;
  border-bottom: 1px solid #e1e1e1;
  border-top: 1px solid #e1e1e1;
}

.chat-input-container > input {
  flex: 1;
  border: 1px solid #e1e1e1;
  padding: 1rem;
}

.chat-input-container > button {
  border: 1px solid #e1e1e1;
  padding: 1rem;
}

.chat-input-container > button:hover {
  cursor: pointer;
}
</style>
