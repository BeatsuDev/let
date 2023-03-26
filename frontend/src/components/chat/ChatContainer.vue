<template>
  <div class="chat-container-wrapper">
    <div class="chat-title-bar">
      <h3 v-if="!chat">Ingen chat valgt</h3>
      <!-- TODO: Check if the user viewing the page is the buyer or the seller -->
      <h3 v-else>
        Chat for "{{ chat.listing.title }}" med
        {{
          useSessionStore().getUser()?.id === chat.seller.id
            ? chat.buyer.firstName
            : chat.seller.firstName
        }}
      </h3>
    </div>

    <div class="chat-messages">
      <div class="no-chats" v-if="chat === null || chat.messages.length === 0">
        <h2>Ingen meldinger å vise...</h2>
      </div>
      <div
        v-else
        :class="{
          'chat-message': true,
          'received-message': !(message.sender === loggedInUser),
          'sent-message': message.sender === loggedInUser,
        }"
        v-for="(message, index) in chat.messages"
        :key="index"
      >
        <p>{{ message.content }}</p>
        <br v-if="!(message.sender === loggedInUser)" />
        <span>{{
          new Date(message.timestamp).toLocaleTimeString("no", { timeStyle: "short" })
        }}</span>
      </div>
    </div>

    <form @submit.prevent="sendMessage" class="chat-input-container">
      <input type="text" class="chat-input" v-model="chatMessageInput" />
      <button class="chat-send-button">Send</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import type {ComputedRef} from "vue";
import {computed, ref} from "vue";

import {useSessionStore} from "@/stores/sessionStore";

import {ChatApi, ChatFull, CreateMessage, Sender} from "@/services/index";

// Define APIs
const chatApi = new ChatApi();

// Define props
const props = defineProps<{
  modelValue: ChatFull;
}>();
const chat = computed({
  get: () => props.modelValue,
  set: (chat) => emit("update:modelValue", chat),
});

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", newChat: ChatFull): void;
}>();

// Define refs
const chatMessageInput = ref("");

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
      const newChat = response.data as ChatFull;
      console.table(newChat.messages);
      chat.value = newChat;
    })
    .catch(() => alert("Kunne ikke sende meldingen"));
  chatMessageInput.value = "";
}

// Vue hooks

// Other script logic
</script>

<style scoped>
.chat-messages > .no-chats {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.chat-container-wrapper {
  background-color: #fcfcfc;
  width: 100%;
  height: 100%;
  border: 1px solid #e1e1e1;
  border-bottom: none;
}

.chat-container-wrapper > * {
  width: 100%;
}

.chat-container-wrapper > .chat-title-bar {
  padding: 1rem;
  text-align: center;
  border-bottom: 1px solid #e1e1e1;
}

.chat-messages {
  display: flex;
  flex-wrap: nowrap;
  justify-content: end;
  flex-direction: column-reverse;
  height: calc(100% - 9.1rem);
  overflow-y: scroll;
  border-bottom: 1px solid #e1e1e1;
  background-color: #f1f1f1;
}

.chat-messages > .chat-message {
  padding: 0.2rem 1rem;
  border-radius: 1rem;
  max-width: 85%;
}

.chat-messages > .chat-message > p {
  padding: 0.4rem 1rem;
  margin: 0;
}

.chat-messages > .sent-message {
  align-self: flex-end;
  text-align: end;
}

.chat-messages > .received-message > p {
  background-color: rgb(212, 212, 212);
  display: inline;
}

.chat-messages > .sent-message > p {
  background-color: rgb(178, 209, 238);
}

/* Chat input */
.chat-input-container {
  display: flex;
  flex-direction: row;
  padding: 1rem;
  border-bottom: 1px solid #e1e1e1;
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
