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
**Changing a password: the new password must not be the old one in a different order.**

A user changes their password from `Tiger123` to `123Tiger`. It looks like a new password, but it uses exactly the same characters, just rearranged. Anyone who has seen the old one can guess it easily. A change-password form can block this:

1. **Same password:** if `new === old`, reject it ("must be different").
2. **Rearranged password:** if the lengths are equal and every character appears the same number of times, the new password is an anagram of the old one. This is the Valid Anagram check. Reject it ("too similar to your old password").

**Why this is possible.** The change-password form asks for the *current* password first, so at that moment the app has both the old and new password as plain text in memory. After that, the system stores only a hash of the password, and a hash cannot be compared this way. This also means the check only works against the current password. For older passwords in the history, the system can only catch exact repeats, by comparing hashes.

**Link to the edge case in this note.** Passwords contain uppercase letters, digits and symbols, so the 26-slot `a`-`z` array from this solution would fail (`"A"` vs `"B"` returned `true`). A real check needs a larger array (for example 256 slots for byte values) or a `Map` of counts.

**Caveat.** This is one cheap rule, not a full password policy. Current guidance (for example NIST SP 800-63B) puts more weight on checking new passwords against lists of breached and common passwords than on rules like this one.

## Real-world: technical / framework use
The part worth remembering is the **count array**: when the possible keys are a small fixed set (26 letters, 10 digits, 256 byte values), an array indexed by the key replaces a hash map.

- **Faster than a `HashMap`.** In Java, `HashMap<Character, Integer>` boxes every key and value into objects and hashes each key. `int[26]` indexes straight to the slot, with no boxing and no hashing. That is why the solution here uses `count[c - 'a']`.
- **Spell checkers.** Many typos are two swapped letters (`teh` for `the`), which makes the typed word an anagram of the right one. Suggestion lists can use that signal when ranking "did you mean" candidates.
- **Testing a sort function.** To check that `mySort(input)` is correct you need two things: the output is in order, and it has the same elements as the input (nothing lost, nothing invented). The second check is the anagram check, with counts of elements instead of letters.
