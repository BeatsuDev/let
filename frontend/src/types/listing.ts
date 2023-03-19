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
