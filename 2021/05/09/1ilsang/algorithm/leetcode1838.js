/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number}
 */
const maxFrequency = (nums, k) => {
  nums.sort((a, b) => a - b);
  let left = 0;
  let answer = 0;
  let used = 0;

  for (let i = 0; i < nums.length; i++) {
    if (i > 0) used += (i - left) * (nums[i] - nums[i - 1]);

    while (used > k) {
      used -= nums[i] - nums[left];
      left++;
    }

    answer = Math.max(answer, i - left + 1);
  }

  return answer;
};
