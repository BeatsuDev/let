<template>
  <div class="chat-container-wrapper">
    <div class="chat-title-bar">
      <h3>Chat for listing 1 with Person Person</h3>
    </div>
    
    <div class="chat-messages">
      <div class="chat-message received-message">
        <p>A very long messsage that someone sent to me. It would be nice if this message was like really realy long so that I can see the text wrapping in place!</p>
      </div>
      <div class="chat-message sent-message">
        <p>Hi man! Thanks for the message. I've been waiting to hear from you.</p>
      </div>
    </div>

    <div class="chat-input-container">
      <input type="text" class="chat-input" />
      <button class="chat-send-button">Send</button>
    </div>
  </div>
</template>

<script setup lang="ts">
// Define APIs

// Define props

// Define emits

// Define refs

// Define computed values

// Define callback functions

// Vue hooks

// Other script logic

</script>

<style scoped>
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
  flex-wrap: wrap;
  justify-content: end;
  flex-direction: column;
  height: calc(100% - 9.1rem);
  overflow-y: scroll;
  border-bottom: 1px solid #e1e1e1;
  background-color: #f1f1f1;
}

.chat-messages  > .chat-message {
  padding: 1rem;
  border-radius: 1rem;
  max-width: 85%;
}

.chat-messages > .chat-message > p {
  padding: 0.4rem 1rem;
}

.chat-messages > .sent-message {
  align-self: flex-end;
}

.chat-messages > .received-message > p {
  background-color: rgb(212, 212, 212);
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