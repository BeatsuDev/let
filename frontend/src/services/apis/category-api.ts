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
import globalAxios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";
import { Configuration } from "../configuration";
// Some imports not used depending on template conditions
// @ts-ignore
import { BASE_PATH, BaseAPI, COLLECTION_FORMATS, RequestArgs, RequiredError } from "../base";
import { Category, CreateCategory } from "../models";

/**
 * CategoryApi - axios parameter creator
 * @export
 */
export const CategoryApiAxiosParamCreator = function (configuration?: Configuration) {
  return {
    /**
     * Create a new category
     * @summary Create a new category
     * @param {CreateCategory} [body] A new category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    createCategory: async (
      body?: CreateCategory,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      const localVarPath = `/category`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, "https://example.com");
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }
      const localVarRequestOptions: AxiosRequestConfig = {
        method: "POST",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication bearerAuth required

      // authentication cookieAuth required
      if (configuration && configuration.apiKey) {
        const localVarApiKeyValue =
          typeof configuration.apiKey === "function"
            ? await configuration.apiKey("Authorization")
            : await configuration.apiKey;
        localVarQueryParameter["Authorization"] = localVarApiKeyValue;
      }

      localVarHeaderParameter["Content-Type"] = "application/json";

      const query = new URLSearchParams(localVarUrlObj.search);
      for (const key in localVarQueryParameter) {
        query.set(key, localVarQueryParameter[key]);
      }
      for (const key in options.params) {
        query.set(key, options.params[key]);
      }
      localVarUrlObj.search = new URLSearchParams(query).toString();
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };
      const needsSerialization =
        typeof body !== "string" ||
        localVarRequestOptions.headers["Content-Type"] === "application/json";
      localVarRequestOptions.data = needsSerialization
        ? JSON.stringify(body !== undefined ? body : {})
        : body || "";

      return {
        url: localVarUrlObj.pathname + localVarUrlObj.search + localVarUrlObj.hash,
        options: localVarRequestOptions,
      };
    },
    /**
     * Delete a category by ID
     * @summary Delete an existing category
     * @param {number} id The id of the category you are trying to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    deleteCategory: async (id: number, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      // verify required parameter 'id' is not null or undefined
      if (id === null || id === undefined) {
        throw new RequiredError(
          "id",
          "Required parameter id was null or undefined when calling deleteCategory."
        );
      }
      const localVarPath = `/category/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(id)));
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, "https://example.com");
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }
      const localVarRequestOptions: AxiosRequestConfig = {
        method: "DELETE",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication bearerAuth required

      // authentication cookieAuth required
      if (configuration && configuration.apiKey) {
        const localVarApiKeyValue =
          typeof configuration.apiKey === "function"
            ? await configuration.apiKey("Authorization")
            : await configuration.apiKey;
        localVarQueryParameter["Authorization"] = localVarApiKeyValue;
      }

      const query = new URLSearchParams(localVarUrlObj.search);
      for (const key in localVarQueryParameter) {
        query.set(key, localVarQueryParameter[key]);
      }
      for (const key in options.params) {
        query.set(key, options.params[key]);
      }
      localVarUrlObj.search = new URLSearchParams(query).toString();
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: localVarUrlObj.pathname + localVarUrlObj.search + localVarUrlObj.hash,
        options: localVarRequestOptions,
      };
    },
    /**
     * Retrieve all categories
     * @summary Retrieve all categories
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getCategories: async (options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/category`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, "https://example.com");
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }
      const localVarRequestOptions: AxiosRequestConfig = {
        method: "GET",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      const query = new URLSearchParams(localVarUrlObj.search);
      for (const key in localVarQueryParameter) {
        query.set(key, localVarQueryParameter[key]);
      }
      for (const key in options.params) {
        query.set(key, options.params[key]);
      }
      localVarUrlObj.search = new URLSearchParams(query).toString();
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: localVarUrlObj.pathname + localVarUrlObj.search + localVarUrlObj.hash,
        options: localVarRequestOptions,
      };
    },
    /**
     * Get a category by ID
     * @summary Retrieve an existing category
     * @param {number} id The id of the category you are trying to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getCategory: async (id: number, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      // verify required parameter 'id' is not null or undefined
      if (id === null || id === undefined) {
        throw new RequiredError(
          "id",
          "Required parameter id was null or undefined when calling getCategory."
        );
      }
      const localVarPath = `/category/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(id)));
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, "https://example.com");
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }
      const localVarRequestOptions: AxiosRequestConfig = {
        method: "GET",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      const query = new URLSearchParams(localVarUrlObj.search);
      for (const key in localVarQueryParameter) {
        query.set(key, localVarQueryParameter[key]);
      }
      for (const key in options.params) {
        query.set(key, options.params[key]);
      }
      localVarUrlObj.search = new URLSearchParams(query).toString();
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: localVarUrlObj.pathname + localVarUrlObj.search + localVarUrlObj.hash,
        options: localVarRequestOptions,
      };
    },
  };
};

/**
 * CategoryApi - functional programming interface
 * @export
 */
export const CategoryApiFp = function (configuration?: Configuration) {
  return {
    /**
     * Create a new category
     * @summary Create a new category
     * @param {CreateCategory} [body] A new category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async createCategory(
      body?: CreateCategory,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<Category>>> {
      const localVarAxiosArgs = await CategoryApiAxiosParamCreator(configuration).createCategory(
        body,
        options
      );
      return (axios: AxiosInstance = globalAxios, basePath: string = BASE_PATH) => {
        const axiosRequestArgs: AxiosRequestConfig = {
          ...localVarAxiosArgs.options,
          url: basePath + localVarAxiosArgs.url,
        };
        return axios.request(axiosRequestArgs);
      };
    },
    /**
     * Delete a category by ID
     * @summary Delete an existing category
     * @param {number} id The id of the category you are trying to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async deleteCategory(
      id: number,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<Category>>> {
      const localVarAxiosArgs = await CategoryApiAxiosParamCreator(configuration).deleteCategory(
        id,
        options
      );
      return (axios: AxiosInstance = globalAxios, basePath: string = BASE_PATH) => {
        const axiosRequestArgs: AxiosRequestConfig = {
          ...localVarAxiosArgs.options,
          url: basePath + localVarAxiosArgs.url,
        };
        return axios.request(axiosRequestArgs);
      };
    },
    /**
     * Retrieve all categories
     * @summary Retrieve all categories
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getCategories(
      options?: AxiosRequestConfig
    ): Promise<
      (axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<Array<Category>>>
    > {
      const localVarAxiosArgs = await CategoryApiAxiosParamCreator(configuration).getCategories(
        options
      );
      return (axios: AxiosInstance = globalAxios, basePath: string = BASE_PATH) => {
        const axiosRequestArgs: AxiosRequestConfig = {
          ...localVarAxiosArgs.options,
          url: basePath + localVarAxiosArgs.url,
        };
        return axios.request(axiosRequestArgs);
      };
    },
    /**
     * Get a category by ID
     * @summary Retrieve an existing category
     * @param {number} id The id of the category you are trying to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getCategory(
      id: number,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<Category>>> {
      const localVarAxiosArgs = await CategoryApiAxiosParamCreator(configuration).getCategory(
        id,
        options
      );
      return (axios: AxiosInstance = globalAxios, basePath: string = BASE_PATH) => {
        const axiosRequestArgs: AxiosRequestConfig = {
          ...localVarAxiosArgs.options,
          url: basePath + localVarAxiosArgs.url,
        };
        return axios.request(axiosRequestArgs);
      };
    },
  };
};

/**
 * CategoryApi - factory interface
 * @export
 */
export const CategoryApiFactory = function (
  configuration?: Configuration,
  basePath?: string,
  axios?: AxiosInstance
) {
  return {
    /**
     * Create a new category
     * @summary Create a new category
     * @param {CreateCategory} [body] A new category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async createCategory(
      body?: CreateCategory,
      options?: AxiosRequestConfig
    ): Promise<AxiosResponse<Category>> {
      return CategoryApiFp(configuration)
        .createCategory(body, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * Delete a category by ID
     * @summary Delete an existing category
     * @param {number} id The id of the category you are trying to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async deleteCategory(
      id: number,
      options?: AxiosRequestConfig
    ): Promise<AxiosResponse<Category>> {
      return CategoryApiFp(configuration)
        .deleteCategory(id, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * Retrieve all categories
     * @summary Retrieve all categories
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getCategories(options?: AxiosRequestConfig): Promise<AxiosResponse<Array<Category>>> {
      return CategoryApiFp(configuration)
        .getCategories(options)
        .then((request) => request(axios, basePath));
    },
    /**
     * Get a category by ID
     * @summary Retrieve an existing category
     * @param {number} id The id of the category you are trying to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getCategory(id: number, options?: AxiosRequestConfig): Promise<AxiosResponse<Category>> {
      return CategoryApiFp(configuration)
        .getCategory(id, options)
        .then((request) => request(axios, basePath));
    },
  };
};

/**
 * CategoryApi - object-oriented interface
 * @export
 * @class CategoryApi
 * @extends {BaseAPI}
 */
export class CategoryApi extends BaseAPI {
  /**
   * Create a new category
   * @summary Create a new category
   * @param {CreateCategory} [body] A new category
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof CategoryApi
   */
  public async createCategory(
    body?: CreateCategory,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<Category>> {
    return CategoryApiFp(this.configuration)
      .createCategory(body, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Delete a category by ID
   * @summary Delete an existing category
   * @param {number} id The id of the category you are trying to retrieve
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof CategoryApi
   */
  public async deleteCategory(
    id: number,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<Category>> {
    return CategoryApiFp(this.configuration)
      .deleteCategory(id, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Retrieve all categories
   * @summary Retrieve all categories
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof CategoryApi
   */
  public async getCategories(
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<Array<Category>>> {
    return CategoryApiFp(this.configuration)
      .getCategories(options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Get a category by ID
   * @summary Retrieve an existing category
   * @param {number} id The id of the category you are trying to retrieve
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof CategoryApi
   */
  public async getCategory(
    id: number,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<Category>> {
    return CategoryApiFp(this.configuration)
      .getCategory(id, options)
      .then((request) => request(this.axios, this.basePath));
  }
}
