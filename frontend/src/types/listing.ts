export class Listing {
  id: number;
  title: string;
  sellerId: number;
  created: Date;
  sold: Date;
  summary: string;
  description: string;
  thumbnail: Image;
  gallery: Image[];
  location: Location;
  price: number;

  constructor(
    id: number,
    title: string,
    sellerId: number,
    created: Date,
    sold: Date,
    summary: string,
    description: string,
    thumbnail: Image,
    gallery: Image[],
    location: Location,
    price: number
  ) {
    this.id = id;
    this.title = title;
    this.sellerId = sellerId;
    this.created = created;
    this.sold = sold;
    this.summary = summary;
    this.description = description;
    this.thumbnail = thumbnail;
    this.gallery = gallery;
    this.location = location;
    this.price = price;
  }
}

export class ListingCard {
  id: number;
  title: string;
  summary: string;
  price: number;
  thumbnailUrl: string;
  locationName: string;
  categoryName: string;

  constructor(
    id: number,
    title: string,
    summary: string,
    price: number,
    thumbnailUrl: string,
    locationName: string,
    catgoryName: string
  ) {
    this.id = id;
    this.title = title;
    this.summary = summary;
    this.price = price;
    this.thumbnailUrl = thumbnailUrl;
    this.locationName = locationName;
    this.categoryName = catgoryName;
  }
}

export class Image {
  id: number;

  constructor(id: number) {
    this.id = id;
  }
}

export class Location {
  latitude: number;
  longitude: number;

  constructor(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

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
    this.radius = null;
    this.favorites = false;
  }
}
