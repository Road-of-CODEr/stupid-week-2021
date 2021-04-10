/**
 * @param {number[]} nums
 * @return {number}
 */
const maxAscendingSum = (nums) => {
  let currentSum = 0;
  let maxSum = -Infinity;

  for (let i = 0; i < nums.length; i++) {
    currentSum += nums[i];
    if (nums[i] < nums[i + 1]) {
      maxSum = Math.max(maxSum, currentSum);
    } else {
      maxSum = Math.max(maxSum, currentSum);
      currentSum = 0;
    }
  }

  return maxSum;
};
