/**
 * @param {number[]} nums
 * @return {number}
 */
const tupleSameProduct = (nums) => {
  const map = new Map();
  let count = 0;
  for (let i = 0; i < nums.length; i++) {
    for (let j = i + 1; j < nums.length; j++)
      map.set(nums[i] * nums[j], map.get(nums[i] * nums[j]) + 1 || 1);
  }
  for (const freq of map.values())
    if (freq > 1) count += (freq * (freq - 1)) / 2;
  return count * 8;
};
