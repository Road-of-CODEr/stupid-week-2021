/**
 * @param {string} J
 * @param {string} S
 * @return {number}
 */
var numJewelsInStones = function (J, S) {
  let result = 0;
  const map = new Map();
  S.split('').forEach(s => {
    map.set(s, map.has(s) ? map.get(s) + 1 : 1);
  });

  return J.split('').reduce((acc, cur) => (map.has(cur) ? map.get(cur) : 0) + acc, 0);
};
