<template>
  <div class="wrapper-form">
    <h1>Admin Panel</h1>
    <div class="container">
      <div class="column">
        <div class="form-container">
          <h2>Lag Ny Kategori</h2>
          <form @submit.prevent="addCategory">
            <div class="form-group">
              <input
                class="input-text"
                type="text"
                v-model="newCategory"
                placeholder="Elektronikk"
              />
            </div>
            <div class="form-group">
              <button class="button button-black button-screaming" type="submit">
                Lag Kategori
              </button>
            </div>
          </form>
        </div>
        <AlertBox v-if="errorMessage" :message="errorMessage" type="error" />
      </div>

      <div class="column">
        <h2>Kategorier</h2>
        <div class="category-container">
          <div v-for="(category, index) in categories" :key="index" class="category">
            <div class="category-name">{{ category.name }}</div>
            <div class="category-actions">
              <button
                class="button button-screaming delete-category"
                @click="() => removeCategory(index)"
              >
                Slett
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { CategoryApi, type Category } from "@/services/index";
import { ref, computed, onMounted } from "vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";

// Define APIs
const categoryApi = new CategoryApi();

// Define refs
const categories = ref<Category[]>([]);
const newCategory = ref("");
const errorMessage = ref("");

const errorMessages = {
  401: "Du er ikke logget inn",
  403: "Du har ikke tilgang til denne siden",
  404: "Kunne ikke finne kategorien du ønsket å slette",
  409: "Kategorien du prøver å slette er i bruk",
  500: "Noe gikk galt på serveren",
};

// Define callback functions
function removeCategory(index: number): void {
  const category = categories.value[index];
  categoryApi
    .deleteCategory(category.id)
    .then(() => {
      errorMessage.value = "";
      categories.value.splice(index, 1);
    })
    .catch((error) => {
      if (error.response.status in errorMessages) {
        errorMessage.value = errorMessages[error.response.status];
      } else {
        errorMessage.value = "Noe gikk galt";
      }
    });
}

function addCategory(): void {
  if (newCategory.value === "") return;
  categoryApi
    .createCategory({ name: newCategory.value })
    .then((response) => {
      errorMessage.value = "";
      categories.value.push(response.data);
      newCategory.value = "";
    })
    .catch((error) => {
      if (error.response.status in errorMessages) {
        errorMessage.value = errorMessages[error.response.status];
      } else {
        errorMessage.value = "Noe gikk galt";
      }
    });
}

// Other script logic
categoryApi.getCategories().then((response) => {
  categories.value = response.data;
});
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 2rem;
}

.container {
  display: flex;
  flex-direction: row;
}

.column {
  padding: 1rem;
}

form button {
  margin-top: 0.4rem;
}

form input {
  width: 100%;
  margin-top: 1rem;
}

.category-container {
  overflow-y: scroll;
  margin-top: 1rem;
  border: 1px solid black;
  height: 300px;
}

.category {
  display: flex;
  padding: 0.4rem 1rem;
}

.category-name {
  flex: 1;
  font-size: 1.1rem;
}

.delete-category {
  transition-duration: 120ms;
}

.delete-category:hover {
  background-color: red;
  color: white;
}

@media (max-width: 900px) {
  .container {
    flex-direction: column;
    padding: 0;
  }
}
</style>
