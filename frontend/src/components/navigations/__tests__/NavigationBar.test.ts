import { describe, beforeEach, it, vi, expect } from "vitest";
import { setActivePinia, createPinia } from 'pinia'
import { shallowMount } from '@vue/test-utils'
import NavigationBar from "@/components/navigations/NavigationBar.vue";

describe("NavigationBar", () => {
  beforeEach(() => {
    // creates a fresh pinia and make it active so it's automatically picked
    // up by any useStore() call without having to pass it to it:
    // `useStore(pinia)`
    setActivePinia(createPinia())
  })

  it("should contain only login button when not logged in", () => {
    const wrapper = shallowMount(NavigationBar);
    const loginButton = wrapper.find("#login-navbutton");
    expect(loginButton.exists()).toBe(true);
    expect(wrapper.find("#logout-navbutton").exists()).toBe(false);
  });
});