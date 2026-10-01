// Time: O(n)  Space: O(n)
export function twoSum(nums: number[], target: number): number[] {
  const seen = new Map<number, number>(); // value -> index
  for (let i = 0; i < nums.length; i++) {
    const value = nums[i]!;
    const complementIndex = seen.get(target - value);

    if (complementIndex !== undefined) {
      console.log(
        `FOUND  i=${i} value=${value} need=${target - value} at index ${complementIndex}`,
      );
      return [complementIndex, i];
    }
    seen.set(value, i);
    console.log(
      `MISS   i=${i} value=${value} need=${target - value} seen=${JSON.stringify([...seen])}`,
    );
  }
  return [];
}
