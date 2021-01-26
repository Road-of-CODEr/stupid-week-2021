/**
 * @param {string} s
 * @return {number}
 */
var lengthOfLongestSubstring = function (s) {
  let left = 0;
  let result = [0, 0];
  while (left < s.length) {
    const map = new Map();
    map.set(s[left], 1);
    let right = left + 1;
    while (right < s.length && !map.has(s[right])) {
      map.set(s[right++], 1);
    }

    const substring = s.slice(left, right);
    const curMaxLength = result[1] - result[0];
    if (substring.length > curMaxLength) {
      result = [left, right];
    }
    left++;
  }
  return result[1] - result[0];
};