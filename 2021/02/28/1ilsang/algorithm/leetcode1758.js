/**
 * @param {string} s
 * @return {number}
 * Explain: https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/discuss/1064542/O(n)-with-explanation
 */
const minOperations = (s) => {
  const odd = { 0: 0, 1: 0 };
  const even = { 0: 0, 1: 0 };

  for (let i = 0; i < s.length; i += 1) {
    i % 2 ? (odd[s[i]] += 1) : (even[s[i]] += 1);
  }

  return Math.min(odd["1"] + even["0"], odd["0"] + even["1"]);
};
