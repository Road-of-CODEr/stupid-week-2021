/**
 * https://leetcode.com/problems/remove-duplicate-letters/
 * @param {string} s
 * @return {string}
 */
var removeDuplicateLetters = function (s) {
  const letters = [];
  const used = new Map();
  const map = new Map();

  for (let i = 0; i < s.length; i++) {
    map.set(s[i], i);
    used.set(s[i], false);
  }

  for (let i = 0; i < s.length; i++) {

    if (!used.get(s[i])) {
      while (letters.length > 0 && letters[letters.length - 1] > s[i] && map.get(letters[letters.length - 1]) > i) {
        used.set(letters[letters.length - 1], false);
        letters.pop();
      }
      letters.push(s[i]);
    }

    used.set(s[i], true);
  }
  return letters.join('');
};