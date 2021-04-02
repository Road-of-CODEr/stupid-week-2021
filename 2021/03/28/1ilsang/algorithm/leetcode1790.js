/**
 * @param {string} s1
 * @param {string} s2
 * @return {boolean}
 */
const areAlmostEqual = (s1, s2) => {
  if (s1 === s2) return true;

  const diff = [];

  for (let i = 0; i < s1.length; i++) {
    if (s1[i] === s2[i]) continue;
    diff.push([s1[i], s2[i]]);
  }

  return diff.length === 2 && diff[0].join("") === diff[1].reverse().join("");
};
