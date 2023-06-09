<script lang="ts" setup>
import { onMounted, ref } from "vue";

// define refs
const loading = ref(true);

// Vue hooks
onMounted(() => {
  setTimeout(() => {
    loading.value = false;
  }, 50);
});

// Other script logic
function getCssClasses() {
  return {
    "lds-ellipsis": true,
    "true-color": loading,
  };
}
</script>

<template>
  <main>
    <div :class="getCssClasses">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
  </main>
</template>

<style scoped>
/* SETTINGS */
main {
  --color: #fff;
  --size: 250px;
  --speed: 1.6;
  --transition-duration: 0.5s;
}

.true-color {
  --color: #333;
}

main {
  overflow: hidden;
  position: fixed;
  width: 100%;
  height: 93%;
  justify-content: center;
  align-items: center;
}

/* Loading circle from https://loading.io/css/ */
.lds-ellipsis {
  display: inline-block;
  position: relative;
  width: var(--size);
  height: var(--size);
}

.lds-ellipsis div {
  position: absolute;
  top: calc(33 / 80 * var(--size));
  width: calc(13 / 80 * var(--size));
  height: calc(13 / 80 * var(--size));
  border-radius: 50%;
  background: var(--color);
  animation-timing-function: cubic-bezier(0, 1, 1, 0);
  transition-duration: var(--transition-duration);
}

.lds-ellipsis div:nth-child(1) {
  left: calc(8 / 80 * var(--size));
  animation: lds-ellipsis1 calc(0.6s / var(--speed)) infinite;
}

.lds-ellipsis div:nth-child(2) {
  left: calc(8 / 80 * var(--size));
  animation: lds-ellipsis2 calc(0.6s / var(--speed)) infinite;
}

.lds-ellipsis div:nth-child(3) {
  left: calc(32 / 80 * var(--size));
  animation: lds-ellipsis2 calc(0.6s / var(--speed)) infinite;
}

.lds-ellipsis div:nth-child(4) {
  left: calc(56 / 80 * var(--size));
  animation: lds-ellipsis3 calc(0.6s / var(--speed)) infinite;
}

@keyframes lds-ellipsis1 {
  0% {
    transform: scale(0);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes lds-ellipsis3 {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}

@keyframes lds-ellipsis2 {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(calc(24 / 80 * var(--size)), 0);
  }
}
</style>
