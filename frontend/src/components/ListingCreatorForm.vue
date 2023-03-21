<script setup lang="ts">
import { ref, computed } from "vue";
import type { Ref } from "vue";

const emit = defineEmits(["createListing"]);

// Emit form data to parent component
const title = ref("");
const price = ref("");
const place = ref("");
const category = ref("");
const summary = ref("");
const description = ref("");
const images = ref([]) as Ref<File[]>;

var submitButtonClicked = ref(false);

// Computed refs for checking if each input field is empty
const titleEmpty = computed(() => submitButtonClicked.value && title.value == "");
const priceEmpty = computed(() => submitButtonClicked.value && price.value == "");
const placeEmpty = computed(() => submitButtonClicked.value && place.value == "");
const categoryEmpty = computed(() => submitButtonClicked.value && category.value == "");
const summaryEmpty = computed(() => submitButtonClicked.value && summary.value == "");
const descriptionEmpty = computed(() => submitButtonClicked.value && description.value == "");
const imagesEmpty = computed(() => submitButtonClicked.value && images.value.length == 0);

function fileHandler() {
  const files = (document.getElementById("images") as HTMLInputElement).files;
  if (!files) return;
  images.value = Array.from(files);
}

function submitHandler() {
  // Check if any data is missing
  if (
    title.value == "" ||
    price.value == "" ||
    place.value == "" ||
    category.value == "" ||
    summary.value == "" ||
    description.value == "" ||
    images.value.length == 0
  ) {
    submitButtonClicked.value = true;
    return false;
  }
  let listingData = {
    title: title.value,
    price: price.value,
    place: place.value,
    category: category.value,
    summary: summary.value,
    description: description.value,
    images: images.value,
  };

  emit("createListing", listingData);
  return true;
}
</script>

<template>
  <form @submit.prevent="submitHandler">
    <div class="row" id="row-1">
      <div class="col">
        <h3><label for="title">Tittel</label></h3>
        <input
          class="input-text"
          v-model="title"
          type="text"
          id="title"
          :class="{ 'red-border': titleEmpty }"
          placeholder="Rød rose - snart døende"
        />
      </div>
      <div class="col">
        <h3><label for="price">Pris (kr)</label></h3>
        <input
          class="input-text"
          v-model="price"
          type="text"
          id="price"
          :class="{ 'red-border': priceEmpty }"
          placeholder="249.99"
        />
      </div>
    </div>

    <div class="row" id="row-2">
      <div class="col">
        <h3><label for="place">Sted</label></h3>
        <input
          class="input-text"
          v-model="place"
          type="text"
          id="place"
          :class="{ 'red-border': placeEmpty }"
          placeholder="Kardemomme By, Norge"
        />
      </div>
      <div class="col">
        <h3><label for="category">Kategori</label></h3>
        <input
          class="input-text"
          v-model="category"
          type="text"
          id="category"
          :class="{ 'red-border': categoryEmpty }"
          placeholder="Planter"
        />
      </div>
    </div>

    <div class="row" id="row-3">
      <div class="col">
        <h3><label for="summary">Kort beskrivelse</label></h3>
        <textarea
          class="input-text"
          v-model="summary"
          name="summary"
          id="summary"
          :class="{ 'red-border': summaryEmpty }"
        ></textarea>
      </div>
    </div>

    <div class="row" id="row-4">
      <div class="col">
        <h3><label for="description">Detaljert beskrivelse</label></h3>
        <textarea
          class="input-text"
          v-model="description"
          name="description"
          id="description"
          :class="{ 'red-border': descriptionEmpty }"
        ></textarea>
      </div>
    </div>

    <div class="row" id="row-5">
      <div class="col">
        <h3><label for="images">Last opp bilder</label></h3>
        <input
          class="input-text"
          @change="fileHandler"
          type="file"
          id="images"
          multiple
          :class="{ 'red-border': imagesEmpty }"
        />
      </div>
    </div>

    <div class="row" id="row-6">
      <div class="col">
        <button class="button button-black button-screaming" type="submit">Publiser annonse</button>
      </div>
    </div>
  </form>
</template>

<style scoped>
label {
  display: block;
  margin-bottom: 0.2rem;
}

input,
textarea {
  width: 100%;
  resize: none;
}

.col {
  padding: 0 0.4rem;
}

.row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  margin-bottom: 1rem;
}

.row > .col {
  grid-column: 1 / 6;
}

#row-1 > .col:first-child {
  grid-column: 1 / 5;
}

#row-1 > .col:last-child {
  grid-column: 5 / 5;
}

#row-2 > .col:first-child {
  grid-column: 1 / 4;
}

#row-2 > .col:last-child {
  grid-column: 4 / 6;
}

#row-6 > .col:first-child {
  grid-column: 6 / 6;
}

#description {
  height: 200px;
}

#images {
  cursor: pointer;
}

.red-border {
  border: 1px solid red;
}
</style>
