/**
 * @param {number[]} costs
 * @param {number} coins
 * @return {number}
 */
const maxIceCream = (costs, coins) => {
  costs.sort((a, b) => a - b);
  for (let i = 0; i < costs.length; i++) {
    if ((coins -= costs[i]) < 0) return i;
  }
  return costs.length;
};
