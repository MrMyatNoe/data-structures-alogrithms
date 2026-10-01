// Time: O(n)  Space: O(1)  (n = s.length; fixed 26-slot count array, lowercase a-z only)
export function validAnagram(s: string, t: string): boolean {
  if (s.length !== t.length) {
    return false;
  }

  const count = new Array(26).fill(0);

  for (let i = 0; i < s.length; i++) {
    count[s.charCodeAt(i) - 97]++;
    count[t.charCodeAt(i) - 97]--;
  }

  return count.every((num) => num === 0);
}
