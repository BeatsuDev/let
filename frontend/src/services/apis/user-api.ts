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
import { CreateUser, LoginUser, UserBody, UserFull } from "../models";

/**
 * UserApi - axios parameter creator
 * @export
 */
export const UserApiAxiosParamCreator = function (configuration?: Configuration) {
  return {
    /**
     * This can only be done by the logged in user
     * @summary Create user
     * @param {CreateUser} [body] Created user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    createUser: async (
      body?: CreateUser,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      const localVarPath = `/user`;
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
     * This can only be done by the logged in user or admin
     * @summary Delete user
     * @param {string} id The id of the user to delete
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    deleteUser: async (id: string, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      // verify required parameter 'id' is not null or undefined
      if (id === null || id === undefined) {
        throw new RequiredError(
          "id",
          "Required parameter id was null or undefined when calling deleteUser."
        );
      }
      const localVarPath = `/user/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(id)));
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
     * Get the currently logged in user
     * @summary Get current user
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getCurrentUser: async (options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/user/session`;
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
     *
     * @summary Get user by id
     * @param {string} id The id of the user to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getUserById: async (id: string, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      // verify required parameter 'id' is not null or undefined
      if (id === null || id === undefined) {
        throw new RequiredError(
          "id",
          "Required parameter id was null or undefined when calling getUserById."
        );
      }
      const localVarPath = `/user/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(id)));
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
     *
     * @summary Logs user into the system
     * @param {LoginUser} [body] Created user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    loginUser: async (body?: LoginUser, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/user/session`;
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
     *
     * @summary Logs out current logged in user session
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    logoutUser: async (options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/user/session`;
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
     *
     * @summary Renew session
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    renewSession: async (options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/user/session`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, "https://example.com");
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }
      const localVarRequestOptions: AxiosRequestConfig = {
        method: "PUT",
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
     * This can only be done by the logged in user or admin
     * @summary Update user
     * @param {UserBody} [body] Updated user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    updateUser: async (body?: UserBody, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/user`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, "https://example.com");
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }
      const localVarRequestOptions: AxiosRequestConfig = {
        method: "PUT",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication bearerAuth required

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
  };
};

/**
 * UserApi - functional programming interface
 * @export
 */
export const UserApiFp = function (configuration?: Configuration) {
  return {
    /**
     * This can only be done by the logged in user
     * @summary Create user
     * @param {CreateUser} [body] Created user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async createUser(
      body?: CreateUser,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<UserFull>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).createUser(
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
     * This can only be done by the logged in user or admin
     * @summary Delete user
     * @param {string} id The id of the user to delete
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async deleteUser(
      id: string,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<void>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).deleteUser(
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
     * Get the currently logged in user
     * @summary Get current user
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getCurrentUser(
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<UserFull>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).getCurrentUser(
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
     *
     * @summary Get user by id
     * @param {string} id The id of the user to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getUserById(
      id: string,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<UserFull>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).getUserById(
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
     *
     * @summary Logs user into the system
     * @param {LoginUser} [body] Created user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async loginUser(
      body?: LoginUser,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<string>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).loginUser(
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
     *
     * @summary Logs out current logged in user session
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async logoutUser(
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<void>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).logoutUser(options);
      return (axios: AxiosInstance = globalAxios, basePath: string = BASE_PATH) => {
        const axiosRequestArgs: AxiosRequestConfig = {
          ...localVarAxiosArgs.options,
          url: basePath + localVarAxiosArgs.url,
        };
        return axios.request(axiosRequestArgs);
      };
    },
    /**
     *
     * @summary Renew session
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async renewSession(
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<string>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).renewSession(options);
      return (axios: AxiosInstance = globalAxios, basePath: string = BASE_PATH) => {
        const axiosRequestArgs: AxiosRequestConfig = {
          ...localVarAxiosArgs.options,
          url: basePath + localVarAxiosArgs.url,
        };
        return axios.request(axiosRequestArgs);
      };
    },
    /**
     * This can only be done by the logged in user or admin
     * @summary Update user
     * @param {UserBody} [body] Updated user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async updateUser(
      body?: UserBody,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => Promise<AxiosResponse<UserFull>>> {
      const localVarAxiosArgs = await UserApiAxiosParamCreator(configuration).updateUser(
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
  };
};

/**
 * UserApi - factory interface
 * @export
 */
export const UserApiFactory = function (
  configuration?: Configuration,
  basePath?: string,
  axios?: AxiosInstance
) {
  return {
    /**
     * This can only be done by the logged in user
     * @summary Create user
     * @param {CreateUser} [body] Created user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async createUser(
      body?: CreateUser,
      options?: AxiosRequestConfig
    ): Promise<AxiosResponse<UserFull>> {
      return UserApiFp(configuration)
        .createUser(body, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * This can only be done by the logged in user or admin
     * @summary Delete user
     * @param {string} id The id of the user to delete
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async deleteUser(id: string, options?: AxiosRequestConfig): Promise<AxiosResponse<void>> {
      return UserApiFp(configuration)
        .deleteUser(id, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * Get the currently logged in user
     * @summary Get current user
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getCurrentUser(options?: AxiosRequestConfig): Promise<AxiosResponse<UserFull>> {
      return UserApiFp(configuration)
        .getCurrentUser(options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @summary Get user by id
     * @param {string} id The id of the user to retrieve
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getUserById(id: string, options?: AxiosRequestConfig): Promise<AxiosResponse<UserFull>> {
      return UserApiFp(configuration)
        .getUserById(id, options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @summary Logs user into the system
     * @param {LoginUser} [body] Created user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async loginUser(
      body?: LoginUser,
      options?: AxiosRequestConfig
    ): Promise<AxiosResponse<string>> {
      return UserApiFp(configuration)
        .loginUser(body, options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @summary Logs out current logged in user session
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async logoutUser(options?: AxiosRequestConfig): Promise<AxiosResponse<void>> {
      return UserApiFp(configuration)
        .logoutUser(options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @summary Renew session
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async renewSession(options?: AxiosRequestConfig): Promise<AxiosResponse<string>> {
      return UserApiFp(configuration)
        .renewSession(options)
        .then((request) => request(axios, basePath));
    },
    /**
     * This can only be done by the logged in user or admin
     * @summary Update user
     * @param {UserBody} [body] Updated user object
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async updateUser(
      body?: UserBody,
      options?: AxiosRequestConfig
    ): Promise<AxiosResponse<UserFull>> {
      return UserApiFp(configuration)
        .updateUser(body, options)
        .then((request) => request(axios, basePath));
    },
  };
};

/**
 * UserApi - object-oriented interface
 * @export
 * @class UserApi
 * @extends {BaseAPI}
 */
export class UserApi extends BaseAPI {
  /**
   * This can only be done by the logged in user
   * @summary Create user
   * @param {CreateUser} [body] Created user object
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async createUser(
    body?: CreateUser,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<UserFull>> {
    return UserApiFp(this.configuration)
      .createUser(body, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * This can only be done by the logged in user or admin
   * @summary Delete user
   * @param {string} id The id of the user to delete
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async deleteUser(id: string, options?: AxiosRequestConfig): Promise<AxiosResponse<void>> {
    return UserApiFp(this.configuration)
      .deleteUser(id, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Get the currently logged in user
   * @summary Get current user
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async getCurrentUser(options?: AxiosRequestConfig): Promise<AxiosResponse<UserFull>> {
    return UserApiFp(this.configuration)
      .getCurrentUser(options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @summary Get user by id
   * @param {string} id The id of the user to retrieve
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async getUserById(
    id: string,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<UserFull>> {
    return UserApiFp(this.configuration)
      .getUserById(id, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @summary Logs user into the system
   * @param {LoginUser} [body] Created user object
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async loginUser(
    body?: LoginUser,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<string>> {
    return UserApiFp(this.configuration)
      .loginUser(body, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @summary Logs out current logged in user session
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async logoutUser(options?: AxiosRequestConfig): Promise<AxiosResponse<void>> {
    return UserApiFp(this.configuration)
      .logoutUser(options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @summary Renew session
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async renewSession(options?: AxiosRequestConfig): Promise<AxiosResponse<string>> {
    return UserApiFp(this.configuration)
      .renewSession(options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * This can only be done by the logged in user or admin
   * @summary Update user
   * @param {UserBody} [body] Updated user object
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof UserApi
   */
  public async updateUser(
    body?: UserBody,
    options?: AxiosRequestConfig
  ): Promise<AxiosResponse<UserFull>> {
    return UserApiFp(this.configuration)
      .updateUser(body, options)
      .then((request) => request(this.axios, this.basePath));
  }
}