/**
 * @param {string} s
 * @param {number} k
 * @return {string}
 */
const truncateSentence = (s, k) => {
  return s.split(" ").slice(0, k).join(" ");
};
