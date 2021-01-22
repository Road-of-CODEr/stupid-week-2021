/**
 * @param {number[]} deliciousness
 * @return {number}
 */
const countPairs = (D) => {
  const m = new Map();
  let ans = 0;
  for (let n of D) m.set(n, (m.get(n) || 0) + 1);
  for (let [k, v] of m) {
    for (let i = 1 << 21; i >= 2 * k && i; i >>= 1) {
      if (i === 2 * k) ans += (v * (v - 1)) / 2;
      else if (m.has(i - k)) ans += m.get(i - k) * v;
    }
  }
  return ans % 1000000007;
};
