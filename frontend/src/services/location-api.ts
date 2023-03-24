import axios from "axios";

/**
 * @deprecated
 * DEVELOPMENT PURPOSES ONLY. DO NOT USE IN PRODUCTION CODE.
 */

const Api = () => {
  return axios.create({
    baseURL: "https://api.api-ninjas.com/v1/",
    headers: { "X-Api-Key": import.meta.env.VITE_API_NINJAS_API_KEY },
    withCredentials: false,
  });
};

/**
 * @deprecated
 * DEVELOPMENT PURPOSES ONLY. DO NOT USE IN PRODUCTION CODE.
 */
export async function getLocationsFromLongLat(long: number, lat: number) {
  console.log(import.meta.env.VITE_API_NINJAS_API_KEY);
  return await Api().get(`reversegeocoding`, { params: { lon: long, lat: lat } });
}

/**
 * @deprecated
 * DEVELOPMENT PURPOSES ONLY. DO NOT USE IN PRODUCTION CODE.
 * @param country
 * @param city
 */
export async function lookUpLocation(country: string | null, city: string | null) {
  return await Api().get("geocoding", { params: { city: city, country: country } });
}

export default Api;
