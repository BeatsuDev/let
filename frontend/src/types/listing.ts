export class ListingFilter {
  category?: string | null;
  longitute?: number | null;
  latitude?: number | null;
  search?: string | null;
  radius?: number | null;
  favorites: boolean;

  constructor() {
    this.category = null;
    this.longitute = null;
    this.latitude = null;
    this.latitude = null;
    this.search = null;
    this.radius = 30;
    this.favorites = false;
  }
}
