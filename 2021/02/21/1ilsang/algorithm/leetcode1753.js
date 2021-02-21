/**
 * @param {number} a
 * @param {number} b
 * @param {number} c
 * @return {number}
 */
const maximumScore = (a, b, c) => {
  const stones = [a, b, c];
  let score = 0;

  while (stones.reduce((empty, val) => (empty += val === 0), 0) < 2) {
    stones.sort((a, b) => a - b);
    stones[0] > 0 ? (stones[0] -= 1) : (stones[1] -= 1);
    stones[2] -= 1;
    score += 1;
  }
  return score;
};
