<template>
  <main>
    <div id="chats-view-wrapper">
      <NavigationDrawer v-model="collapsed">
        <div v-if="chatList === null && !chatListErrorMessage">Henter chats...</div>
        <div v-else-if="chatListErrorMessage">
          <AlertBox :message="'Feil ved henting av chatter: ' + chatListErrorMessage" />
        </div>
        <ChatList v-else v-model="chatList" class="chat-list" @select-chat="loadChat" />
      </NavigationDrawer>
      <div class="toggle-sidebar" @click="collapsed = !collapsed">
        <FilterIcon class="button-icon" />
        Meny
      </div>
      <MainContainer :collapse="collapsed" class="chat-view-wrapper">
        <ChatContainer
          v-if="selectedChat !== undefined"
          v-model="selectedChat"
          :class="{ 'collapsed-margin': collapsed }"
          class="chat-container"
          @update:model-value="updateLastMessage"
        />
      </MainContainer>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref } from "vue";

import { ChatApi, ChatFull, ChatMinimal } from "@/services/index";
import router from "@/router";

import ChatList from "@/components/chat/ChatList.vue";
import FilterIcon from "@/components/icons/FilterIcon.vue";
import ChatContainer from "@/components/chat/ChatContainer.vue";
import MainContainer from "@/components/containers/MainContainer.vue";
import NavigationDrawer from "@/components/navigations/NavigationDrawer.vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

// Define APIs
const chatApi = new ChatApi();

// Define refs
const selectedChat = ref<ChatFull | null | undefined>(undefined);
const collapsed = ref(false);
const chatListErrorMessage = ref("");
const chatList = ref<Array<ChatMinimal> | null>(null);

// Define callback functions
function loadChat(chat: ChatMinimal) {
  router.push({ name: "chat", params: { chatId: chat.id } });
  chatApi
    .getChat(chat.id!)
    .then((response) => response.data)
    .then((chat) => {
      selectedChat.value = chat;
    })
    .catch(() => {
      selectedChat.value = null;
    });
}

// Load chats
chatApi
  .getChats()
  .then((response) => {
    chatList.value = response.data || ([] as ChatMinimal[]);
  })
  .then(() => {
    let chatId: number | undefined = parseInt(router.currentRoute.value.params.chatId as string);
    if (!chatId) chatId = chatList.value![0]?.id;
    if (chatId) {
      loadChat({ id: chatId } as ChatMinimal);
    }
  });

function updateLastMessage(chat: ChatFull) {
  const chatToUpdate = chatList.value?.find((chatCandidate) => chatCandidate.id == chat.id);
  if (!chatToUpdate) return;

  chatToUpdate.lastMessage = chat.messages ? chat.messages[0] : undefined;
}
</script>

<style scoped>
.toggle-sidebar {
  display: none;
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
  transition-duration: 500ms;
}

.chat-view-wrapper {
  height: 100%;
  display: flex;
  flex-direction: row;
}

#chats-view-wrapper {
  height: calc(100vh - 105px);
  margin: 0;
}

.chat-container {
  margin-left: 15rem; /** Margin on desktop **/
}

@media (max-width: 600px) {
  .chat-container {
    margin-left: 0 !important;
  }

  .toggle-sidebar {
    display: flex;
    left: 4rem;
  }
}
</style>
