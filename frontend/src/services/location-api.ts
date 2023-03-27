import axios from "axios";

/**
 * @deprecated
 * DEVELOPMENT PURPOSES ONLY. DO NOT USE IN PRODUCTION CODE.
 */

const Api = () => {
  return axios.create({
    baseURL: "https://ws.geonorge.no/stedsnavn/v1/",
    withCredentials: false,
  });
};

/**
 * @deprecated
 * DEVELOPMENT PURPOSES ONLY. DO NOT USE IN PRODUCTION CODE.
 * @param country
 * @param city
 */
export async function lookUpLocation(sok: string) {
  const response = await Api().get("navn", {
    params: { sok: sok + "*", treffPerSide: 10, side: 1 },
  });
  const adresser = response.data.navn;
  console.log(adresser);

  return adresser.map((location: any) => {
    return {
      name: location.fylker[0].fylkesnavn + ", " + location.skrivemåte,
      latitude: location.representasjonspunkt.nord,
      longitude: location.representasjonspunkt.øst,
    };
  });
}

export default Api;
