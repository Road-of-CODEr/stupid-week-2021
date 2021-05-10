/**
 * @param {number} n
 * @param {number} k
 * @return {number}
 */
const sumBase = (n, k) => {
  return recursion(n, k, 0);
};

const recursion = (n, k, acc) => {
  if (n < k) return acc + n;
  return recursion(Math.floor(n / k), k, acc + (n % k));
};
