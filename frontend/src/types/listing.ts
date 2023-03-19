export class Listing {
  id: number;
  sellerId: string;
  created: Date;
  sold: Date;

  summary: string;
  description: string;
  thumbnail: Image;
  gallery: Image[];
  location: Location;
  price: number;
}

export class Image {
  id: number;
}

export class Location {
  latitude: number;
  longitude: number;
}
