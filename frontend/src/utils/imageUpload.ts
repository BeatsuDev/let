import axios from "axios";
import type { Image } from "@/services/index";

export async function uploadImage(images: File[]) {
  // Upload images to backend
  const imageResponses = await Promise.all(
    images.map((image) => {
      const formData = new FormData();
      formData.append("image", image);
      return axios.post("http://localhost:8080/image", formData);
    })
  );

  // Make sure all image responses are successful
  const imageSuccesses = imageResponses.map(
    (response) => response.status === 201 && response.data.url
  );

  if (imageSuccesses.includes(false)) {
    throw (
      "Noe gikk galt under bildeopplastningen... " +
      imageResponses.filter((response) => response.status !== 201)[0].data
    );
  }
  return imageResponses.map((r) => r.data) as Image[];
}
