import { describe, it, expect, vi } from "vitest";
import { mount } from '@vue/test-utils'
import BackButton from "@/components/inputs/BackButton.vue";

describe("BackButton", () => {
  it("Back button should contain text 'tilbake'", async () => {
    const wrapper = mount(BackButton);
    const backButton = wrapper.find("button");
    expect(backButton.text().toLowerCase()).toContain("tilbake");
  });
});