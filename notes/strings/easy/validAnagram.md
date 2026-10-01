# Valid Anagram

## Problem
Given two strings `s` and `t` (lowercase letters), return `true` if `t` is a rearrangement of `s`, using every letter of `s` exactly once, and `false` otherwise.

## Approach
Count letters in a fixed 26-slot array, one slot per letter `a`-`z` (`char - 'a'` gives the slot).
1. If the lengths differ, return `false` right away.
2. Add 1 to the slot of each letter of `s`, and subtract 1 for each letter of `t`. The TypeScript version does both in one loop. The Java version does two loops and prints the array after each step.
3. If every slot is `0`, the strings are anagrams. TypeScript checks this with `count.every(...)`. Java loops over the slots and returns `false` at the first non-zero one, logging that letter and its difference.

## Why it works
Two strings are anagrams exactly when they contain each letter the same number of times. Adding for `s` and subtracting for `t` leaves, in each slot, `count in s - count in t`. All slots are zero if and only if every letter count matches. The length check matters too: without it, the counts could still balance by accident when the extra letters cancel out, so it also lets you stop early.

## Complexity
- **Time: O(n).** One pass over the strings (n is their length), plus a check of 26 slots, which is constant.
- **Space: O(1).** The count array has 26 slots no matter how long the input is. In Java, `toCharArray()` also makes a temporary copy of each string, so strictly the Java version uses O(n) extra memory unless you loop with `charAt(i)`.

## Edge cases
- **Basic and longer matches** (`listen`/`silent`, `anagram`/`nagaram`): the main case, with letters in a different order.
- **Duplicate letters** (`aabb`/`bbaa`): two of the same letter must be counted twice, not just seen once.
- **Same letters, different counts** (`aab`/`abb`): the lengths are equal and both strings use `a` and `b`, so only the counts tell them apart. This is why a set of letters is not enough.
- **Single character** (`a`/`a`): the smallest non-empty match.
- **Empty strings** (`""`/`""`): the loop never runs and every slot stays `0`, so the answer is `true`.
- **No answer** (`rat`/`car`): same length but different letters, so some slots end non-zero.
- **Length mismatch** (`a`/`ab`): rejected by the length check before any counting.
- **Not covered by the tests:** uppercase letters, digits and Unicode. Both versions assume `a`-`z`. In TypeScript, `"A"` vs `"B"` returns `true` by mistake, because the negative index never reaches the slots `every` checks. In Java it throws `ArrayIndexOutOfBoundsException`.

## Alternative
- **Sort both strings and compare:** O(n log n) time and O(n) space, because strings are immutable and you need a sorted copy of each. It is shorter to write and works for any characters, but it is slower for large inputs.
- **A hash map of counts** (`Map<string, number>` or `HashMap<Character, Integer>`) instead of a 26-slot array: still O(n) time, and it handles Unicode and mixed case. The cost is more overhead per character and space proportional to the number of distinct characters, not a fixed 26.

## Real-world: business scenario
**Goods-received check in a warehouse.** A purchase order says: 10 x SKU-A, 4 x SKU-B, 6 x SKU-C. The goods arrive in boxes and get scanned in whatever order they come off the pallet. Receiving needs to know whether what was scanned matches what was ordered, regardless of order.

- Add the ordered quantity per SKU, subtract each scanned item, and check whether every count is `0`. This is the same count-and-cancel idea as here, with SKUs in place of letters.
- A non-zero count points straight at the problem: `+2` for SKU-B means two units are missing, `-1` for SKU-C means one extra arrived. This is what the Java version's `MISMATCH` log line does for letters.

The difference from the exercise is that the alphabet is not a fixed 26, so a real system would use a map keyed by SKU.

## Real-world: technical / framework use
The underlying idea is **comparing two multisets by their counts**, which shows up in a few real places:
- **Counting sort** uses the same count array indexed by value, in O(n + k) time where k is the range of values, and is a building block of radix sort.
- **Python's `collections.Counter(a) == Counter(b)`** and **Guava's `Multiset.equals`** are the standard-library and library form of this check: two multisets are equal when every element has the same count.
- **Test assertions** like AssertJ's `containsExactlyInAnyOrder` and Hamcrest's `containsInAnyOrder` check the same property, equal contents ignoring order. I'm describing what they check, not claiming they use a count array internally.
- **Sliding-window anagram search** (find all anagrams of `p` in a longer string `s`) reuses the same count idea, adjusting counts as the window moves.
