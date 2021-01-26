/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number[]}
 */
var topKFrequent = function (nums, k) {
  const map = new Map();
  nums.forEach(n => map.set(n, map.has(n) ? map.get(n) + 1 : 1));

  const elements = Array.from(map).sort(([, val1], [, val2]) => val2 - val1);
  return elements.slice(0, k).map(([key,]) => key);
};