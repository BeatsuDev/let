export interface pagination<T> {
  items: [T];
  next: string;
  prev: string;
  numberOfPages: number;
  pageNumber: number;
}
