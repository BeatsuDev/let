/* tslint:disable */
/* eslint-disable */
/**
 * let API - OpenAPI 3.0
 * A simple API for listing items for sale.  Some useful links: - [The GitHub repository](https://github.com/BeatsuDev/let)
 *
 * OpenAPI spec version: 1.0.0
 * Contact: vkbugge@hotmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { Location } from "./location";

/**
 *
 * @export
 * @interface CreateListing
 */
export interface CreateListing {
  /**
   *
   * @type {string}
   * @memberof CreateListing
   */
  title?: string;
  /**
   *
   * @type {string}
   * @memberof CreateListing
   */
  summary?: string;
  /**
   *
   * @type {string}
   * @memberof CreateListing
   */
  description?: string;
  /**
   * Price in øre
   * @type {number}
   * @memberof CreateListing
   */
  price?: number;
  /**
   *
   * @type {number}
   * @memberof CreateListing
   */
  categoryId?: number;
  /**
   *
   * @type {Location}
   * @memberof CreateListing
   */
  location?: Location;
  /**
   *
   * @type {number}
   * @memberof CreateListing
   */
  thumbnailId?: number;
  /**
   *
   * @type {Array<number>}
   * @memberof CreateListing
   */
  galleryIds?: Array<number>;
}