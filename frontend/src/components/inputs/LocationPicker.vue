<template>
  <div class="dropdown">
    <input
      v-model="input"
      class="input-text"
      placeholder="Search.."
      type="text"
      @keyup="searchWithDelay"
    />
    <div class="dropdown-content">
      <div
        v-for="location in locations"
        :key="location.id"
        @click="
          (event) => {
            event.stopPropagation();
            setValue(location);
          }
        "
      >
        {{ location.name }}
      </div>
      <div v-if="loading">Loading...</div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed, ref } from "vue";
import { lookUpLocation } from "@/services/location-api";
import { InputHandler } from "@/utils/input-delay";
import { Location } from "@/services/models/location";

// Define props
const props = defineProps({
  modelValue: {
    type: Object || undefined,
    required: true,
  },
});

// Define emits
const emit = defineEmits(["update:modelValue"]);

// Define refs
const input = ref("");
const locations = ref([]);
const loading = ref(false);

// eslint-disable-next-line no-undef
const inputDelay = new InputHandler(500);

let id = 0 as number;

// Define computed values
const location = computed({
  get() {
    return props.modelValue;
  },
  set(value: Object) {
    locations.value = [];
    emit("update:modelValue", value);
  },
});

// Other functions
function searchWithDelay() {
  inputDelay.searchWithDelay(search);
}

// Callback functions
function search() {
  if (input.value == "" || input.value.length < 2) {
    locations.value = [];
    emit("update:modelValue", undefined);
    return;
  }
  loading.value = true;
  lookUpLocation(input.value)
    .then((response) => {
      loading.value = false;
      locations.value = response;
    })
    .catch(() => {
      loading.value = false;
    });
}

function setValue(location: Location) {
  location.value = location;
  locations.value = [];
  input.value = location.name;
  emit("update:modelValue", {
    longitude: location.longitude,
    latitude: location.latitude,
    name: location.name,
  } as Location);
}
</script>
