# Two Sum

## Problem
Given an array `nums` and a `target`, return the indices of the two elements that add up to `target`, or `[]` if no such pair exists.

## Approach
One pass with a hash map (`Map` in TypeScript, `HashMap` in Java) from value to index. For each element at index `i`:
1. Compute the complement `target - value`.
2. If the complement is already in the map, return `[complementIndex, i]`.
3. Otherwise store `value -> i` and continue.

If the loop ends without a match, return an empty array.

## Why it works
Invariant: before processing index `i`, the map holds every value at indices `0..i-1`. If a valid pair `(j, i)` with `j < i` exists, then when we reach `i` the complement of `nums[i]` is `nums[j]`, which is already in the map, so we find it. Every pair is checked from its later element, so no pair is missed. Checking before inserting also means an element is never paired with itself.

## Complexity
- **Time: O(n).** One pass, and each map lookup and insert is O(1) on average.
- **Space: O(n).** In the worst case (no pair found) the map holds all n elements.

## Edge cases
- **Empty input** (`[]`, target 5): the loop never runs, so it returns `[]` without touching the map.
- **No answer** (`[1, 2, 3]`, target 100): every element misses, and the function falls through to `[]`.
- **Duplicates** (`[3, 3]`, target 6): the second `3` finds the first one already in the map. This works because we look up before inserting; inserting first would overwrite the earlier index.
- **Negatives** (`[-3, 4, 3, 90]`, target 0): the complement of `3` is `-3`. Plain subtraction handles negative values with no special case.
- **Pair not at the start** (`[3, 2, 4]`, target 6): the answer is `[1, 2]`, not `[0, 0]`. This checks that an element is not paired with itself (`3 + 3`).

## Alternative
Brute force with two nested loops checks every pair: O(n^2) time, O(1) space. It is simpler and uses no extra memory, which is fine for small inputs. Another option is to sort and use two pointers, which gives O(n log n) time and O(1) extra space, but sorting loses the original indices unless you keep them alongside the values.

## Real-world: business scenario
**Payment reconciliation (accounts receivable).** A customer sends one bank transfer of 350.00 with no invoice reference. The open invoices on their account are 120.00, 200.00, 75.50 and 150.00. Finance needs to know which two invoices the payment settles.

- `nums` = the open invoice amounts, `target` = the payment amount. Work in integer cents (`35000`, `20000`, `15000`) so floating point never makes `0.1 + 0.2` miss a match.
- The scan reaches 200.00, stores it, then reaches 150.00 and finds the complement (350.00 - 150.00 = 200.00) already in the map. It returns the two invoices in one pass instead of checking every pair.
- The map would be keyed by amount and hold the invoice ID (or index), exactly like `value -> index` here.

**Where this stops matching real life**
- Two Sum returns the first pair it finds. If two different pairs both sum to the payment, a real system has to list every candidate and let a person choose, so it needs more than this function.
- If a payment can cover three or more invoices, the problem becomes subset sum, which has no known polynomial-time solution in general. Two Sum only covers exactly two.
- Banks often deduct fees, so real matching adds a tolerance (for example within a few cents) that a plain hash lookup does not give you.

**Same shape elsewhere:** a gift card with a 50.00 balance and a shop that wants two items summing exactly to it; matching a debit and a credit in a ledger that net to zero (`target = 0`, which is the negatives test case).

## Real-world: technical / framework use
Two Sum itself is rarely shipped as a function. The idea underneath it is: **replace an inner search loop with one hash lookup.** That idea is used heavily:

- **Database hash joins.** In PostgreSQL's *Hash Join*, the planner builds an in-memory hash table from the smaller input (the build side), then scans the larger input once and probes the table per row. That is O(n + m) instead of the O(n * m) of a nested-loop join. It is the same trade as here: spend memory on a table so each row needs one lookup, not a scan of the other side. The "complement" is the join key.
- **Java `HashMap`.** `get`/`put` are O(1) on average because the key's `hashCode()` picks a bucket. Since Java 8, a bucket with many colliding keys becomes a balanced tree, so the worst case is O(log n) rather than O(n). Your Java solution relies on this average-case cost for its O(n) time.
- **JavaScript `Map`.** Used for the same lookup in your TypeScript version. It compares keys with SameValueZero (so `NaN` matches `NaN`, and `0` and `-0` are the same key) and keeps insertion order. A plain object would also turn numeric keys into strings, which is why the repo prefers `Map`.
- **Redis hashes and lookups.** `HGET` and `GET` are average O(1) key lookups. The pattern "look up the complement or the already-seen value by key" is what Redis-backed deduplication and caching do at larger scale.
- **Pandas / Spark `merge` and `join`.** These work like the hash join: index one side by key, then look up each row of the other side.
