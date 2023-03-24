import type { Ref } from "vue";
import { ref } from "vue";
import { AxiosResponse } from "axios";

export default function runAxios<T>(promise: Promise<AxiosResponse<T>>) {
  const data = ref<T | null>(null) as Ref<T | null>;
  const error = ref<Error | null>(null);

  promise
    .then((result) => {
      data.value = result.data;
    })
    .catch((err) => {
      error.value = err;
    });

  return { data, error };
}
