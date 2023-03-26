<template>
  <div>
    <div id="row-1" class="row">
      <ValidatedInput
        v-model="listingDataInputRefs.title"
        :error="validator.title.$errors[0]"
        class="input-container"
        placeholder="Rød rose - snart døende"
        title="Tittel"
      />
      <ValidatedInput
        v-model="listingDataInputRefs.price"
        :error="validator.price.$errors[0]"
        class="input-container"
        placeholder="249.99"
        title="Pris (kr)"
      />
    </div>

    <div id="row-2" class="row">
      <div id="location-picker-wrapper" class="input-container">
        <h3><label for="location-picker">Sted</label></h3>
        <LocationPicker v-model="listingDataInputRefs.location" class="input-container" />
      </div>
      <CategoryPicker
        id="category-picker"
        v-model="listingDataInputRefs.category"
        v-model:text-input="listingDataInputRefs.categoryName"
        :validation-error="validator.category.$errors[0]"
        class="input-container"
        :placeholder="categoryInput"
        title="Kategori"
      />
    </div>

    <div id="row-3" class="row">
      <ValidatedInput
        v-model="listingDataInputRefs.summary"
        :error="validator.summary.$errors[0]"
        class="input-container"
        input-type="textarea"
        title="Kort beskrivelse (Maks 100 tegn)"
      />
    </div>

    <div id="row-4" class="row">
      <ValidatedInput
        id="description"
        v-model="listingDataInputRefs.description"
        :error="validator.description.$errors[0]"
        class="input-container"
        input-type="textarea"
        title="Detaljert beskrivelse"
      />
    </div>

    <div id="row-5" class="row">
      <div class="input-container">
        <h3><label for="images">Last opp bilder</label></h3>
        <input
          id="images"
          :class="{ 'input-text': true, 'red-border': validator.images.$error }"
          multiple
          type="file"
          accept="image/*"
          @change="imageFileHandler"
        />
        <div v-if="validator.images.$error" id="error">
          {{ validator.images.$errors[0].$message }}
        </div>
      </div>
    </div>
    <div v-if="images.length !== 0">
      <h3>Velg forsidebilde og behandle bilder</h3>
      <div class="form-container">
        <ImageContainer
          :images="images"
          v-model="listingDataInputRefs.thumbnailId"
          @delete-image="deleteImage"
          deletable
        ></ImageContainer>
      </div>
    </div>

    <div id="row-6" class="row">
      <div class="input-container" style="margin-top: 1rem">
        <button class="button button-black button-screaming" @click="submitData" type="submit">
          Publiser Annonse
        </button>
      </div>
    </div>
    <AlertBox v-if="errorMessage" :message="errorMessage" type="error"></AlertBox>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue";
import { useVuelidate } from "@vuelidate/core";
import { helpers, maxLength, numeric, required } from "@vuelidate/validators";
import ValidatedInput from "@/components/inputs/ValidatedInput.vue";
import type { CreateListing, UpdateListing } from "@/services";
import CategoryPicker from "../inputs/CategoryPicker.vue";
import LocationPicker from "@/components/inputs/LocationPicker.vue";
import AlertBox from "@/components/dialogs/AlertBox.vue";
import ImageContainer from "@/components/containers/ImageContainer.vue";
import { ImageApi, ListingsApi } from "@/services/index";

// Define pais
const imageApi = new ImageApi();
// Define props
const props = defineProps<{
  modelValue: UpdateListing | CreateListing;
}>();

// Emit form data to parent component
const emit = defineEmits<{
  (event: "createListing"): void;
  (event: "update:modelValue", value: UpdateListing | CreateListing): void;
}>();

// Define refs
const categoryInput = ref(props.modelValue.categoryName as string);
watch(
  () => props.modelValue.categoryName,
  (value) => {
    categoryInput.value = value as string;
  }
);

const listingDataInputRefs = computed({
  get() {
    return props.modelValue;
  },
  set(value: any) {
    const newListing = { ...props.modelValue, [value]: value };
    emit("update:modelValue", newListing);
  },
});

const images = computed(() => {
  let images = listingDataInputRefs.value.images.map((image: File) => URL.createObjectURL(image));
  if (listingDataInputRefs.value.galleryUrls) {
    images = [...images, ...listingDataInputRefs.value.galleryUrls];
  }
  return images;
});

const rules = {
  title: { required: helpers.withMessage("Tittel er påkrevd", required) },
  price: {
    required: helpers.withMessage("Pris er pekrevd", required),
    numeric: helpers.withMessage("Pris må være et tall", numeric),
  },
  location: { required: helpers.withMessage("Lokasjon er påkrevd", required) },
  category: { required: helpers.withMessage("Kategori er påkrevd", required) },
  summary: {
    required: helpers.withMessage("Sammendrag er påkrevd", required),
    maxLength: helpers.withMessage("Sammendrag kan ikke være lengre enn 100 tegn", maxLength(100)),
  },
  description: { required: helpers.withMessage("Beskrivelse er påkrevd", required) },
  images: {
    required: helpers.withMessage("Bilder er påkrevd", required),
    images: helpers.withMessage("Bilder må være mindre enn 1 MB", (images: File[]) =>
      images.every((image) => image.size < 1024 * 1024)
    ),
  },
};

const validator = useVuelidate(rules, listingDataInputRefs);

//Define callback functions
async function submitData() {
  // Check if inputs follow validation rules
  const result = await validator.value.$validate();
  if (!result) {
    return;
  }

  emit("createListing");
}

//Other script logic
function imageFileHandler(event: Event) {
  const target = event.target as HTMLInputElement;
  const files = target.files;
  if (files) {
    listingDataInputRefs.value.images = Array.from(files);
  }
}

function deleteImage(index: number) {
  if (images.value[index].startsWith("blob:")) {
    listingDataInputRefs.value.images.splice(index, 1);
    return;
  }
}
</script>

<style scoped>
label {
  display: block;
  margin-bottom: 0.2rem;
}

#category-picker,
#location-picker-wrapper {
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
