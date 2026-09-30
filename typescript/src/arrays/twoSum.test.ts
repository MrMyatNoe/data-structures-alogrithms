import { describe, it, expect } from "vitest";
import { twoSum } from "./twoSum";

describe("twoSum", () => {
  it.each([
    { nums: [2, 7, 11, 15], target: 9, expected: [0, 1] },
    { nums: [3, 2, 4], target: 6, expected: [1, 2] },
    { nums: [3, 3], target: 6, expected: [0, 1] },
    { nums: [-3, 4, 3, 90], target: 0, expected: [0, 2] },
    { nums: [1, 2, 3], target: 100, expected: [] },
    { nums: [], target: 5, expected: [] },
  ])("nums=$nums target=$target -> $expected", ({ nums, target, expected }) => {
    expect(twoSum(nums, target)).toEqual(expected);
  });
});
