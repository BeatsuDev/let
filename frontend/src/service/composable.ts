import { ref } from "vue";
import type { Ref } from "vue";

export default function runPromise<T>(promise: Promise<T>) {
  const data = ref<T | null>(null) as Ref<T | null>;
  const error = ref<Error | null>(null);

  promise
    .then((result) => {
      data.value = result;
    })
    .catch((err) => {
      error.value = err;
    });

  return { data, error };
}
