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
});
