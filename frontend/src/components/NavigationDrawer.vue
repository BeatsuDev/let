<template>
  <div
    :class="{
      sidebar: true,
      'drawer-collapse': props.modelValue,
      'drawer-active': !props.modelValue,
    }"
  >
    <button style="margin-bottom: 1.5rem" @click="collapse" class="button close">
      <CollapseIcon class="button-icon" />
      Skjul
    </button>
    <h3 class="drawer-item">Kategori</h3>
    <slot />
  </div>
</template>
<script lang="ts" setup>
import CollapseIcon from "@/components/icons/CollapseIcon.vue";

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["update:modelValue"]);

function collapse() {
  emit("update:modelValue", true);
}
</script>
<style scoped>
.sidebar {
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  margin-top: 7rem;
  padding-top: 8rem;
  padding-left: 3rem;
  padding-right: 3rem;
  transition: 0.5s;
  overflow: hidden;
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
    margin-top: 0;
    padding-top: 11.5rem;
    padding-left: 1rem;
    padding-right: 1rem;
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
