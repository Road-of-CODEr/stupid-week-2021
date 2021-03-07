/**
 * @param {string} s
 * @return {number}
 */
const countHomogenous = (s) => {
  const MOD = 1e9 + 7;
  let count = 1;
  return [...s].reduce((acc, cur, index) => {
    if (index === 0) return acc;
    const prev = s[index - 1];
    count = prev === cur ? count + 1 : 1;
    return (acc + count) % MOD;
  }, 1);
};
