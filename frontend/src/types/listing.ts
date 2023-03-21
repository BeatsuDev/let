export class ListingFilter {
  category?: number | undefined = undefined;
  longitute?: number | undefined = undefined;
  latitude?: number | undefined = undefined;
  search?: string | undefined = undefined;
  radius?: number | undefined = 30;
  favorites: boolean = false;
  page: number = 1;
}
