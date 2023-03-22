<script setup lang="ts">
import { reactive } from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, numeric } from "@vuelidate/validators";

import ValidatedInput from "@/components/ValidatedInput.vue";

const listingData = reactive({
  title: "",
  price: "",
  place: "",
  category: "",
  summary: "",
  description: "",
});

const rules = {
  title: { required },
  price: { required, numeric },
  place: { required },
  category: { required },
  summary: { required },
  description: { required },
};

const validator = useVuelidate(rules, listingData);

// Emit form data to parent component
const emit = defineEmits(["createListing"]);

async function submitData() {
  const result = await validator.value.$validate();
  if (!result) {
    return;
  }

  // TODO: Image validation + upload

  alert("Success!");
  emit("createListing", listingData);
}
</script>

<template>
  <form @submit.prevent="submitData">
    <div class="row" id="row-1">
      <ValidatedInput
        class="input-container"
        v-model="listingData.title"
        title="Tittel"
        placeholder="Rød rose - snart døende"
        :error="validator.title.$errors[0]"
      />
      <ValidatedInput
        class="input-container"
        v-model="listingData.price"
        title="Pris (kr)"
        placeholder="249.99"
        :error="validator.price.$errors[0]"
      />
    </div>

    <div class="row" id="row-2">
      <ValidatedInput
        class="input-container"
        v-model="listingData.place"
        title="Sted"
        placeholder="Kardemomme By, Norge"
        :error="validator.place.$errors[0]"
      />
      <ValidatedInput
        class="input-container"
        v-model="listingData.category"
        title="Kategori"
        placeholder="Planter"
        :error="validator.category.$errors[0]"
      />
    </div>

    <div class="row" id="row-3">
      <ValidatedInput
        class="input-container"
        v-model="listingData.summary"
        title="Kort beskrivelse"
        input-type="textarea"
        :error="validator.summary.$errors[0]"
      />
    </div>

    <div class="row" id="row-4">
      <ValidatedInput
        id="description"
        class="input-container"
        v-model="listingData.description"
        title="Detaljert beskrivelse"
        input-type="textarea"
        :error="validator.description.$errors[0]"
      />
    </div>

    <div class="row" id="row-5">
      <div class="input-container">
        <h3><label for="images">Last opp bilder</label></h3>
        <input class="input-text" type="file" id="images" multiple />
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
</style>
