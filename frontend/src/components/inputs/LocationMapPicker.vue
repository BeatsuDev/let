<template>
  <div id="map">
    <button
      class="button delete"
      v-if="!isVisible"
      @click="
        (event) => {
          event.stopPropagation();
          hide(true);
        }
      "
    >
      slett
    </button>
  </div>
</template>
<script setup lang="ts">
import leaflet from "leaflet";
import { onMounted, ref, watch } from "vue";

// Define props
const props = defineProps<{
  modelValue: { lat: number; lng: number };
  radius?: number;
}>();

// Define emits
const emit = defineEmits<{
  (event: "update:modelValue", location: { longitude: number; latitude: number }): void;
}>();

// Define refs
const isVisible = ref(true);

// Other variables
let marker: leaflet.Circle<any> = null;
let map: { setView: (arg0: any[], arg1: number) => void };

// Vue hooks
onMounted(() => {
  map = leaflet.map("map").setView([60, 10], 3);
  leaflet
    .tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
      maxZoom: 19,
      doubleClickZoom: "false",
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    })
    .addTo(map);
  map.on("click", (e) => {
    if (marker == null) {
      marker = leaflet.circle(e.latlng, props.radius * 1000);
      marker.addTo(map);
      hide(false);
      return;
    }
    marker.setLatLng(e.latlng);
    marker.addTo(map);
    if (!isVisible.value) {
      hide(false);
    }
    emit("update:modelValue", { latitude: e.latlng.lat, longitude: e.latlng.lng });
  });
});

watch(
  () => props.radius,
  (radius) => {
    if (marker && radius) {
      marker.setRadius(radius * 1000);
    }
  }
);

watch(
  () => props.modelValue,
  () => {
    const latitude = props.modelValue?.latitude;
    const longitude = props.modelValue?.longitude;
    console.log(latitude, longitude);
    if (latitude && longitude) {
      if (marker == null) {
        marker = leaflet.circle([latitude, longitude], props.radius * 1000);
        marker.addTo(map);
      } else {
        marker.setLatLng([latitude, longitude]);
        marker.addTo(map);
      }
    }
  }
);

// Other functions
function hide(hide: boolean) {
  isVisible.value = hide;
  if (hide) {
    emit("update:modelValue", { latitude: undefined, longitude: undefined });
    marker.removeFrom(map);
  } else {
    emit("update:modelValue", {
      latitude: marker.getLatLng().lat,
      longitude: marker.getLatLng().lng,
    });
  }
}
</script>
<style scoped>
#map {
  height: 20rem;
}

.delete {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1000;
}
</style>
