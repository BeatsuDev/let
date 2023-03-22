import type { Location } from "@/service/models";

export class ListingFilter {
  category?: number | undefined = undefined;
  location: Location | undefined = undefined;
  search?: string | undefined = undefined;
  radius?: number | undefined = 30;
  favorites: boolean = false;
  page: number = 1;

  user: number | undefined = undefined;
}


export default interface NewListingWithImageUrls {
  title: string;
  price: string;
  place: string;
  category: string;
  summary: string;
  description: string;
  images: string[];
}