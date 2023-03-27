<template>
  <div id="map">
    <button
      class="button delete"
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

//Other variables
let marker = leaflet.circle([0, 0], { radius: props.radius * 1000, color: "red" });
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
  marker.addTo(map);
  hide(true);
  map.on("click", (e) => {
    hide(false);
    marker.setLatLng(e.latlng);
    emit("update:modelValue", { latitude: e.latlng.lat, longitude: e.latlng.lng });
  });
});

watch(
  () => props.radius,
  (radius) => {
    marker.setRadius(radius * 1000);
  }
);

watch(
  () => props.modelValue,
  (location: { latitude: number; longitude: number }) => {
    if (location.latitude && location.longitude) {
      marker.setLatLng([location.latitude, location.longitude]);
      map.setView([location.latitude, location.longitude], 3);
    }
  }
);

// Other functions
function hide(hide: boolean) {
  if (hide) {
    emit("update:modelValue", { latitude: undefined, longitude: undefined });
    marker.removeFrom(map);
  } else {
    marker.addTo(map);
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
