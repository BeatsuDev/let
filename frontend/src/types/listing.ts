import type { Location } from "@/services/models";

export class ListingFilter {
  category?: number | undefined = undefined;
  location: Location | undefined = undefined;
  search?: string | undefined = undefined;
  radius?: number | undefined = 30;
  page: number = 1;

  user: number | undefined = undefined;
}
