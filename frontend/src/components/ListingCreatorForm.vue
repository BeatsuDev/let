<script setup lang="ts">
import { ref, computed } from "vue";
import type { Ref } from "vue";

import ValidatedInput from "@/components/ValidatedInput.vue";
import { InputType } from "@/types/input";

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
      <ValidatedInput class="input-container" v-model="title" title="Tittel" :submit-button-clicked="submitButtonClicked" placeholder="Rød rose - snart døende"/>
      <ValidatedInput class="input-container" v-model="price" title="Pris (kr)" :submit-button-clicked="submitButtonClicked" placeholder="249.99"/>
    </div>

    <div class="row" id="row-2">
      <ValidatedInput class="input-container" v-model="place" title="Sted" :submit-button-clicked="submitButtonClicked" placeholder="Kardemomme By, Norge"/>
      <ValidatedInput class="input-container" v-model="category" title="Kategori" :submit-button-clicked="submitButtonClicked" placeholder="Planter"/>
    </div>

    <div class="row" id="row-3">
      <ValidatedInput class="input-container" v-model="summary" title="Kort beskrivelse" :submit-button-clicked="submitButtonClicked" :inputType="InputType.TextArea"/>
    </div>

    <div class="row" id="row-4">
      <ValidatedInput class="input-container" v-model="description" title="Detaljert beskrivelse" :submit-button-clicked="submitButtonClicked" :inputType="InputType.TextArea"/>
    </div>

    <div class="row" id="row-5">
      <div  class="input-container">
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
      <div  class="input-container">
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

.row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  margin-bottom: 1rem;
}

.row >  .input-container {
  grid-column: 1 / 6;
}

#row-1 > .input-container:first-child {
  grid-column: 1 / 5;
}

#row-1 > .input-container:last-child {
  grid-column: 5 / 5;
}

#row-2 > .input-container:first-child {
  grid-column: 1 / 4;
}

#row-2 > .input-container:last-child {
  grid-column: 4 / 6;
}

#row-6 > .input-container:first-child {
  grid-column: 6 / 6;
}

#description {
  height: 200px;
}

input {
  width: 100%;
}
</style>
