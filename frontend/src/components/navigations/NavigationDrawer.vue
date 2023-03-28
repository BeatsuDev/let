<template>
  <div
    :class="{
      sidebar: true,
      'drawer-collapse': props.modelValue,
      'drawer-active': !props.modelValue,
    }"
  >
    <button
      class="button close"
      style="margin-bottom: 1.5rem"
      @click="$emit('update:modelValue', true)"
    >
      <CollapseIcon class="button-icon" />
      Skjul
    </button>
    <slot />
  </div>
</template>

<script setup lang="ts">
import CollapseIcon from "@/components/icons/CollapseIcon.vue";
import BackButton from "@/components/inputs/BackButton.vue";
import { onMounted } from "vue";

// Define props
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});

// Define emits
const emit = defineEmits(["update:modelValue"]);

if (window.innerWidth < 900) {
  emit("update:modelValue", true);
}

onMounted(() => {
  // Add event listener for window resize
  window.addEventListener("resize", () => {
    if (window.innerWidth < 700) {
      emit("update:modelValue", true);
    }
  });
});
</script>

<style scoped>
.sidebar {
  height: calc(100% - 7rem);
  position: fixed;
  left: 0;
  top: 0;
  margin-top: 7rem;
  padding: 7rem 2rem 2rem 2rem;
  transition: 0.5s;
  overflow: auto;
  background-color: var(--color-background);
}

.drawer-active {
  width: 20rem;
  border-right: 1px solid #e1e1e1;
}

.drawer-collapse {
  width: 0;
  padding-left: 0;
  padding-right: 0;
}

.close {
  display: none;
}

@media screen and (max-width: 600px) {
  .sidebar {
    padding-top: 11.5rem;
    height: 100%;
    margin-top: 0;
  }

  .close {
    display: block;
  }

  .drawer-collapse {
    width: 0;
    padding-left: 0;
    padding-right: 0;
  }
}
</style>
