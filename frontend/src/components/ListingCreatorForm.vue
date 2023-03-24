<script setup lang="ts">
import { ref, reactive } from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, numeric } from "@vuelidate/validators"
import ValidatedInput from "@/components/ValidatedInput.vue";
import axios from "axios";
import type { Category, CreateListing } from "@/service/index";
import CategoryPicker from "./CategoryPicker.vue";
import LocationPicker from "@/components/forms/LocationPicker.vue";
import type { Location } from "@/service/index";

// Variables as they are from the inputs.
// 
// The price is a string, but it should be a number.
// Category is a string, but it should be a number.
// Images is an array of files, but it should be an array of URLs (strings).
// 
// These things need to be changed before the createListing event is emitted.
const listingDataInputRefs = reactive({
  title: "",
  price: "",
  location: {} as Location,
  category: {} as Category,
  summary: "",
  description: "",
  images: [] as File[],
});

const rules = {
  title: { required },
  price: { required, numeric },
  location: { required },
  category: { required },
  summary: { required },
  description: { required },
  images: { required },
};

const validator = useVuelidate(rules, listingDataInputRefs);

// Emit form data to parent component
const emit = defineEmits<{
  (event: "createListing", listingData: CreateListing): void;
}>();

async function submitData() {
  console.log("submitting data");

  // Check if inputs follow validation rules
  const result = await validator.value.$validate();
  if (!result) {
    console.error("Validation failed: ", validator.value.$errors);
    return;
  }

  console.log("Uploading images...");


  // Upload images to backend
  const imageResponses = await Promise.all(
    listingDataInputRefs.images.map((image) => {
      const formData = new FormData();
      formData.append("image", image);
      return axios.post("http://localhost:8080/image", formData);
    })
  );

  // Make sure all image responses are successful
  const imageSuccesses = imageResponses.map(
    (response) => response.status === 201 && response.data.url
  );

  if (imageSuccesses.includes(false)) {
    alert(
      "Noe gikk galt under bildeopplastningen... " +
        imageResponses.filter((response) => response.status !== 201)[0].data
    );
    return;
  }

  console.log("Images uploaded. Getting URLs... ");

  const imageIds = imageResponses.map((image) => image.data.id) as string[];
  const {price, location, images, category, ...data} = listingDataInputRefs;
  const thumbnailId = imageResponses[0].data.id;

  const listingDataWithImages = {
    ...data,
    categoryId: category.id,
    location: {...listingDataInputRefs.location},
    price: Number(listingDataInputRefs.price),
    thumbnailId,
    galleryIds: imageIds,
  } as CreateListing;

  console.log("Emitting createListing event...");
  

  emit("createListing", listingDataWithImages);
}

function imageFileHandler(event: Event) {
  const target = event.target as HTMLInputElement;
  const files = target.files;
  if (files) {
    listingDataInputRefs.images = Array.from(files);
  }
}
</script>

<template>
  <form @submit.prevent="submitData">
    <div class="row" id="row-1">
      <ValidatedInput
        class="input-container"
        v-model="listingDataInputRefs.title"
        title="Tittel"
        placeholder="Rød rose - snart døende"
        :error="validator.title.$errors[0]"
      />
      <ValidatedInput
        class="input-container"
        v-model="listingDataInputRefs.price"
        title="Pris (kr)"
        placeholder="249.99"
        :error="validator.price.$errors[0]"
      />
    </div>

    <div class="row" id="row-2">
      <div id="location-picker-wrapper" class="input-container">
        <h3><label for="location-picker">Sted</label></h3>
        <LocationPicker class="input-container" v-model="listingDataInputRefs.location"/>
      </div>
      <CategoryPicker class="input-container" id="category-picker" title="Kategori" placeholder="Planter" v-model="listingDataInputRefs.category" :validation-error="validator.category.$errors[0]" />
    </div>

    <div class="row" id="row-3">
      <ValidatedInput
        class="input-container"
        v-model="listingDataInputRefs.summary"
        title="Kort beskrivelse"
        input-type="textarea"
        :error="validator.summary.$errors[0]"
      />
    </div>

    <div class="row" id="row-4">
      <ValidatedInput
        id="description"
        class="input-container"
        v-model="listingDataInputRefs.description"
        title="Detaljert beskrivelse"
        input-type="textarea"
        :error="validator.description.$errors[0]"
      />
    </div>

    <div class="row" id="row-5">
      <div class="input-container">
        <h3><label for="images">Last opp bilder</label></h3>
        <input
          :class="{ 'input-text': true, 'red-border': validator.images.$error }"
          @change="imageFileHandler"
          type="file"
          id="images"
          multiple
        />
        <div v-if="validator.images.$error" id="error">
          {{ validator.images.$errors[0].$message }}
        </div>
      </div>
    </div>

    <div class="row" id="row-6">
      <div class="input-container">
        <button class="button button-black button-screaming" type="submit">Publiser Annonse</button>
      </div>
    </div>
  </form>
</template>

<style scoped>
label {
  display: block;
  margin-bottom: 0.2rem;
}

#category-picker, #location-picker-wrapper {
  padding: 0 0.4rem;
}

#location-picker-wrapper > h3 > label {
  margin-bottom: 0;
}

.row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  margin-bottom: 1rem;
}

.row > .input-container {
  grid-column: 1 / 6;
}

#row-1 > .input-container:first-child {
  grid-column: 1 / 5;
}

#row-1 > .input-container:last-child {
  grid-column: 5 / 5;
}

#location-picker-wrapper > .input-container {
  position: relative;
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

#description:deep(textarea) {
  height: 200px;
}

input {
  width: 100%;
}

.red-border {
  border: 1px solid red;
}

#error {
  color: red;
  font-size: 0.9rem;
  font-style: italic;
}
</style>
