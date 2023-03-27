<template>
  <div
    :class="{
      'received-message': !(message.sender === sender),
      'sent-message': message.sender === sender,
    }"
    class="chat-message"
  >
    <div class="row">
      <p>{{ message.content }}</p>
      <div class="spacer" />
    </div>
    <span>{{ new Date(message.timestamp).toLocaleTimeString("no", { timeStyle: "short" }) }}</span>
  </div>
</template>
<script setup lang="ts">
import type { Message } from "@/services/index";
import { useSessionStore } from "@/stores/sessionStore";

//Define apis
const sessionStore = useSessionStore();

// Define props
const props = defineProps<{
  message: Message;
  sender: String;
}>();
</script>
<style scoped>
.chat-message {
  padding: 0.2rem 1rem;
  border-radius: 1rem;
  width: 100%;
}

.chat-message p {
  padding: 0.4rem 1rem;
  margin: 0;
  max-width: 85%;
}

.sent-message {
  text-align: end;
}

.received-message p {
  background-color: rgb(212, 212, 212);
  display: inline;
}

.sent-message p {
  background-color: rgb(178, 209, 238);
}

.sent-message > .row {
  flex-direction: row-reverse !important;
}

.received-message > .row {
  flex-direction: row !important;
}
</style>
