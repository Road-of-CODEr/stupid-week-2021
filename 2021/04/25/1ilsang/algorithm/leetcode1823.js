/**
 * @param {number} n
 * @param {number} k
 * @return {number}
 */
var findTheWinner = function (n, k) {
  const arr = Array(n)
    .fill(0)
    .map((e, i) => i + 1);

  let idx = 0;
  while (arr.length > 1) {
    idx = (idx + k - 1) % arr.length;
    arr.splice(idx, 1);
  }

  return arr[0];
};
