<template>
  <div class="dropdown">
    <input
      class="input-text"
      type="text"
      placeholder="Search.."
      v-model="input"
      @keyup="searchWithDelay"
    />
    <div class="dropdown-content">
      <div @click="setValue(location)" v-for="location in locations" :key="location">
        {{ location.country }},
        {{ location.name }}
      </div>
      <div v-if="loading">Loading...</div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed, ref } from "vue";
import { lookUpLocation } from "@/services/location-api";
import { InputHandler } from "@/util/input-delay";
import { Location } from "@/services/models/location";

const locations = ref([]);

const location = computed({
  get() {
    return props.modelValue;
  },
  set(value: Object) {
    location.value = [];
    emit("update:modelValue", value);
  },
});

const props = defineProps({
  modelValue: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["update:modelValue"]);

const input = ref("");

const loading = ref(false);

// eslint-disable-next-line no-undef
const inputDelay = new InputHandler(500);

let id = 0 as number;

function searchWithDelay() {
  inputDelay.searchWithDelay(search);
}

function setValue(location: Location) {
  location.value = location;
  locations.value = [];
  input.value = location.country + ", " + location.name;
  emit("update:modelValue", {
    longitude: location.longitude,
    latitude: location.latitude,
    name: location.name,
  } as Location);
}

function search() {
  if (input.value == "" || input.value.length < 2) {
    console.log(input.value.length);
    locations.value = [];
    emit("update:modelValue", undefined);
    return;
  }
  loading.value = true;
  lookUpLocation("Norway", input.value)
    .then((response) => {
      loading.value = false;
      locations.value = response.data;
    })
    .catch(() => {
      loading.value = false;
    });
}
</script>
<style scoped>
.dropdown {
  box-sizing: border-box;
  display: inline-block;
  position: relative;
  width: 100%;
}

.dropdown-content {
  background-color: #f6f6f6;
  overflow: auto;
  max-height: 300px;
  width: 100%;
  position: absolute;
  border: 1px solid #ddd;
  z-index: 1;
}

.dropdown-content div {
  display: block;
  cursor: pointer;
  padding: 1rem;
}
</style>
