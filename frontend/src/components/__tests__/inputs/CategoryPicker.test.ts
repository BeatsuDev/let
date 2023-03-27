import { describe, it, expect, vi } from "vitest";
import { mount } from '@vue/test-utils'
import CategoryPicker from "@/components/inputs/CategoryPicker.vue";
import axios from "axios";

// Mock get categories
vi.mock("axios");

describe("CategoryPicker", () => {
  it("renders correct title", async () => {
    const title = 'Select a category';

    const wrapper = mount(CategoryPicker, {
      props: {
        title,
      },
    });
    expect(wrapper.find('label').text()).toMatch(title);
  });

  it('emits "update:modelValue" event when category is selected', async () => {
    const categories = [
      { id: 1, name: 'Category A' },
      { id: 2, name: 'Category B' },
      { id: 3, name: 'Category C' },
    ];
    const runAxios = vi.fn(() => ({ data: categories }));
    const wrapper = mount(CategoryPicker, {
      global: {
        provide: {
          runAxios,
        },
      },
    });
    await wrapper.vm.$nextTick();

    wrapper.find('input').setValue('Category A');
    expect(wrapper.emitted('update:modelValue')).toBeTruthy();
  });
});