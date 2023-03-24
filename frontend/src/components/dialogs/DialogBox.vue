<template>
  <div id="myModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <p>{{ props.message }}</p>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { computed } from "vue";

// define props
const props = defineProps({
  type: {
    type: String,
    default: "error",
  },
  message: {
    type: String,
  },
  visible: {
    type: Boolean,
    default: false,
  },
});

// define computed values
const getCssClasses = computed(() => {
  return {
    main: true,
    success: props.type === "success",
    error: props.type === "error",
    warning: props.type === "warning",
    info: props.type === "info",
    "modal-content": props.visible,
  };
});
</script>
<style scoped>
.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 1);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto; /* 15% from the top and centered */
  padding: 20px;
  border: 1px solid #888;
  width: 80%; /* Could be more or less, depending on screen size */
}

/* The Close Button */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
