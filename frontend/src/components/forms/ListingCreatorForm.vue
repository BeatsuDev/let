<template>
  <div>
    <div id="row-1" class="row">
      <ValidatedInput
        v-model="listing.title"
        :error="validator.title.$errors[0]"
        class="input-container"
        placeholder="Rød rose - snart døende"
        title="Tittel"
      />
      <ValidatedInput
        v-model="nokPrice"
        :error="validator.price.$errors[0]"
        class="input-container"
        placeholder="249.99"
        title="Pris (kr)"
        input-type="number"
      />
    </div>

    <div id="row-2" class="row">
      <div id="location-picker-wrapper" class="input-container">
        <h3><label for="location-picker">Sted</label></h3>
        <LocationPicker
          v-model="listing.location"
          v-model:input="locationInput"
          class="input-container"
        />
      </div>
      <CategoryPicker
        id="category-picker"
        v-model="listing.category.id"
        v-model:text-input="listing.category.name"
        placeholder="Kategori"
        :validation-error="validator.category.$errors[0]"
        class="input-container"
        title="Kategori"
      />
    </div>

    <div id="row-3" class="row">
      <ValidatedInput
        v-model="listing.summary"
        :error="validator.summary.$errors[0]"
        class="input-container"
        input-type="textarea"
        title="Kort beskrivelse (Maks 100 tegn)"
      />
    </div>

    <div id="row-4" class="row">
      <ValidatedInput
        id="description"
        v-model="listing.description"
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
          :class="{
            'input-text': true,
            'red-border': validator.localImages.$error || validator.allImages.$error,
          }"
          accept="image/*"
          multiple
          type="file"
          @change="imageFileHandler"
        />
        <div v-if="validator.localImages.$error || validator.allImages.$error" id="error">
          {{
            validator.localImages.$errors[0]?.$message || validator.allImages.$errors[0]?.$message
          }}
        </div>
      </div>
    </div>
    <div v-if="imageUrls.length !== 0">
      <h3>Velg forsidebilde og behandle bilder</h3>
      <div class="form-container">
        <ImageContainer
          v-model="thumbnailIndex"
          :imageUrls="imageUrls"
          deletable
          @delete-image="deleteImage"
        ></ImageContainer>
      </div>
    </div>

    <div id="row-6" class="row">
      <div class="input-container" style="margin-top: 1rem">
        <button class="button button-black button-screaming" type="submit" @click="submitData">
          Publiser Annonse
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref, watch } from "vue";
import { useVuelidate } from "@vuelidate/core";
import { helpers, maxLength, numeric, required } from "@vuelidate/validators";
import ValidatedInput from "@/components/inputs/ValidatedInput.vue";
import type { Category, ListingFull, UpdateListing } from "@/services/index";
import { Image } from "@/services";
import CategoryPicker from "@/components/inputs/CategoryPicker.vue";
import LocationPicker from "@/components/inputs/LocationPicker.vue";
import ImageContainer from "@/components/containers/ImageContainer.vue";
import { uploadImage } from "@/utils/imageUpload";

// Define props
const props = defineProps<{
  modelValue: ListingFull;
  editable: {
    type: Boolean;
    required: false;
    default: false;
  };
}>();

// Emit form data to parent component
const emit = defineEmits<{
  (event: "createListing", value: UpdateListing): void;
  (event: "update:modelValue", value: ListingFull): void;
}>();

// Define refs
const locationInput = ref("");
const imagesToUpload = ref<File[]>([]);

// Define computed
const listing = computed({
  get() {
    return props.modelValue;
  },
  set(value: any) {
    const newListing = { ...props.modelValue, [value]: value };
    emit("update:modelValue", newListing);
  },
});

const nokPrice = computed({
  get() {
    return listing.value.price / 100;
  },
  set(value: number) {
    listing.value.price = value * 100;
  },
});

const uploadIndex = ref<number | undefined>(undefined);
const thumbnailIndex = computed<number>({
  get() {
    if (uploadIndex.value !== undefined) return uploadIndex.value;
    const IndexOfThumbnail = listing.value.gallery
      .map((i: Image) => i.id)
      .indexOf(listing.value.thumbnail.id);
    if (IndexOfThumbnail === undefined || IndexOfThumbnail === -1) return 0;
    return imagesToUpload.value.length + IndexOfThumbnail;
  },
  set(value: number) {
    // Unset upload index
    uploadIndex.value = undefined;
    // Out of bounds (don't set)
    if (value < 0 || value >= imageUrls.value.length) return;

    // Thumbnail is in images to upload
    if (value < imagesToUpload.value.length) {
      uploadIndex.value = value;
      return;
    }

    // Thumbnail is in gallery - copy not to overwrite gallery images
    listing.value.thumbnail = { ...listing.value.gallery[value - imagesToUpload.value.length] };
  },
});
const newListing = computed<UpdateListing>({
  get() {
    return {
      id: listing.value.id,
      title: listing.value.title,
      summary: listing.value.summary,
      description: listing.value.summary,
      price: listing.value.price,
      categoryId: listing.value.category.id,
      location: listing.value.location,
      thumbnailIndex: thumbnailIndex.value,
      galleryIds: listing.value.gallery.map((image: Image) => image.id),
      state: listing.value.state,
    } as UpdateListing;
  },
  set(value: UpdateListing) {
    thumbnailIndex.value = value.thumbnailIndex ?? 0;
    listing.value = {
      ...listing.value,
      id: value.id,
      title: value.title,
      summary: value.summary,
      description: value.description,
      price: value.price,
      category: { id: value.categoryId, name: "" } as Category,
      location: value.location,
      gallery: listing.value.gallery,
      state: value.state,
    };
  },
});

const imageUrls = computed(() => {
  let images = imagesToUpload.value.map((image: File) => URL.createObjectURL(image));
  if (listing.value.gallery) {
    images = [...images, ...getImageUrls(listing.value.gallery)];
  }
  return images;
});

const toValidate = computed(() => {
  return {
    title: listing.value.title,
    price: listing.value.price,
    location: listing.value.location,
    category: listing.value.category,
    summary: listing.value.summary,
    description: listing.value.description,
    localImages: imagesToUpload,
    allImages: imageUrls.value,
  };
});

const rules = {
  title: { required: helpers.withMessage("Tittel er påkrevd", required) },
  price: {
    required: helpers.withMessage("Pris er pekrevd", required),
    numeric: helpers.withMessage("Pris må være et tall", numeric),
  },
  location: { required: helpers.withMessage("Lokasjon er påkrevd", required) },
  category: {
    required: helpers.withMessage(
      "Kategori er påkrevd",
      (category: Category) => category.id != undefined
    ),
  },
  summary: {
    required: helpers.withMessage("Sammendrag er påkrevd", required),
    maxLength: helpers.withMessage("Sammendrag kan ikke være lengre enn 100 tegn", maxLength(100)),
  },
  description: { required: helpers.withMessage("Beskrivelse er påkrevd", required) },
  localImages: {
    size: helpers.withMessage("Bilder må være mindre enn 1 MB", (images: File[]) =>
      images.every((image) => image.size < 1024 * 1024)
    ),
  },
  allImages: {
    required: helpers.withMessage("Bilder er påkrevd", (images: File[]) => images.length > 0),
  },
};

const validator = useVuelidate(rules, toValidate);

//Define callback functions
async function submitData() {
  // Check if inputs follow validation rules
  const result = await validator.value.$validate();
  if (!result) {
    return;
  }

  uploadImage(imagesToUpload.value)
    .then((data) => {
      listing.value.gallery.unshift(...data);
      console.table(listing.value.gallery);
      emit("createListing", newListing.value);
    })
    .catch((error) => {
      console.log(error);
    });
}

function deleteImage(index: number) {
  if (index < imagesToUpload.value.length) {
    imagesToUpload.value.splice(index, 1);
  } else {
    listing.value.gallery!.splice(index - imagesToUpload.value.length, 1);
  }

  if (index === imageUrls.value.length && index !== 0) thumbnailIndex.value = index - 1;
  else thumbnailIndex.value = index;
}

//Vue hooks
watch(
  () => props.modelValue.locationName,
  (value) => {
    locationInput!.value = value;
  }
);

//Other script logic
function imageFileHandler(event: Event) {
  const target = event.target as HTMLInputElement;
  const files = target.files;
  if (files) {
    const oldLength = imagesToUpload.value.length;
    imagesToUpload.value = Array.from(files);
    const diff = imagesToUpload.value.length - oldLength;
    thumbnailIndex.value = thumbnailIndex.value! + diff;
  }
}

function getImageUrls(images: [Image]): string[] {
  return images!.map((image) => image.url!);
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
