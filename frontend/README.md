# let

This project was generated with [Vite](https://vitejs.dev/) and [Vue 3](https://v3.vuejs.org/), and is the frontend for the [let](letno.no). 

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

### Run Unit Tests with [Vitest](https://vitest.dev/)

```sh
npm run test:unit
```

### Run End-to-End Tests with [Cypress](https://www.cypress.io/)

```sh
npm run test:e2e:dev
```

This runs the end-to-end tests against the Vite development server.
It is much faster than the production build.

But it's still recommended to test the production build with `test:e2e` before deploying (e.g. in CI environments):

```sh
npm run build
npm run test:e2e
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

### Contributing

#####  Best practices

Composition api is used for all new code. To keep an uniform structure throughout the project, the following template should be used

```vue
<template>
  <div class="component-name-wrapper">
    Component content
  </div>
</template>

<script setup lang="ts">
import type { Ref } from "vue";
import { ref, computed, onMounted } from "vue";

// Define APIs
// const userApi = new UserApi();

// Define props
const props = defineProps<{
  prop1: string;
  prop2: number;
}>();

// Define emits
const emit = defineEmits<{
  (event: "some-event", ...args: any[]) : void;
}>();

// Define refs
const value = ref(null) as Ref<HTMLElement | null>;

// Define computed values
const computedValue = computed(() => {
  
});

// Define callback functions
function onClick() {
  
};

// Vue hooks
onMounted(() => {
  
});

// Other script logic

</script>

<style scoped>
  .component-name-wrapper {
    
  }
</style>
```



