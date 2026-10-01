import { describe, expect, it } from "vitest";

import { validAnagram } from "./validAnagram";

describe("validAnagram", () => {
  it.each([
    { s: "listen", t: "silent", expected: true },
    { s: "anagram", t: "nagaram", expected: true },
    { s: "aabb", t: "bbaa", expected: true },
    { s: "a", t: "a", expected: true },
    { s: "", t: "", expected: true },
    { s: "rat", t: "car", expected: false },
    { s: "aab", t: "abb", expected: false },
    { s: "a", t: "ab", expected: false },
  ])('s="$s" t="$t" -> $expected', ({ s, t, expected }) => {
    expect(validAnagram(s, t)).toBe(expected);
  });
});
