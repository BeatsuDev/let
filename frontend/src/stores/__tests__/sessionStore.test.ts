import { UserApi } from "@/services/apis/user-api";
import router from "@/router";
import { afterEach, beforeEach, vi, it, expect, describe } from "vitest";
import { createPinia, setActivePinia } from "pinia";
import { useSessionStore } from "../sessionStore";
import type { UserFull } from "@services/index";

vi.mock("@/services/apis/user-api");
vi.mock("@/router");

describe("Session store", () => {
  let store: ReturnType<typeof useSessionStore>;
  setActivePinia(createPinia());
  const user: UserFull = {
    id: "1",
    name: "Eksempel",
    email: "admin@example.org",
    admin: false,
  };

  beforeEach(() => {
    vi.clearAllMocks();
    store = useSessionStore();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it("initially has a null user", () => {
    expect(store.getUser()).toBeUndefined();
  });

  it("authenticates a user and saves it to session storage", () => {
    store.authenticate(user);
    expect(store.getUser()).toEqual(user);
    expect(sessionStorage.getItem("user")).toEqual(JSON.stringify(user));
  });

  it("logs out a user and removes it from session storage", async () => {
    sessionStorage.setItem("user", JSON.stringify(user));
    await store.logOut();
    expect(store.getUser()).toBeNull();
    expect(sessionStorage.getItem("user")).toBeNull();
    expect(UserApi.prototype.logoutUser).toHaveBeenCalled();
    expect(router.push).toHaveBeenCalledWith("/");
  });

  it("times out a user after a certain period of time", async () => {
    const renewSessionSpy = vi.spyOn(UserApi.prototype, "renewSession");
    store.authenticate(user);
    store.refreshNotification();
    vi.useFakeTimers();
    vi.advanceTimersByTime(9 * 60 * 1000);
    await new Promise((resolve) => setImmediate(resolve));
    expect(renewSessionSpy).toHaveBeenCalled();
    expect(store.getUser()).toEqual(user);
    vi.advanceTimersByTime(1 * 60 * 1000);
    await new Promise((resolve) => setImmediate(resolve));
    expect(store.getUser()).toBeNull();
    expect(sessionStorage.getItem("user")).toBeNull();
    expect(router.push).toHaveBeenCalledWith("/login");
  });
});
