/**
 * @param {number[]} encoded
 * @param {number} first
 * @return {number[]}
 */
const decode = (encoded, first) => {
  return [first, ...encoded].map((cur, index, arr) => {
    return index === 0 ? cur : (arr[index] ^= arr[index - 1]);
  });
};
