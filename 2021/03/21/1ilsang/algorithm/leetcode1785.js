/**
 * @param {number[]} nums
 * @param {number} limit
 * @param {number} goal
 * @return {number}
 */
const minElements = (num, limit, goal) => {
  let add = Math.abs(goal - num.reduce((x, y) => x + y));
  return ((add + limit - 1) / limit) >> 0;
};
