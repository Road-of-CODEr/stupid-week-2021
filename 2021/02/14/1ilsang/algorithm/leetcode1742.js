/**
 * @param {number} lowLimit
 * @param {number} highLimit
 * @return {number}
 */
 const countBalls = (lowLimit, highLimit) => {
  const cnts = new Map();
  for (let i = lowLimit, sum = 0; i <= highLimit; i++, sum = 0) {
    for (let j = i; j; j = Math.trunc(j / 10)) sum += j % 10;
    cnts.set(sum, (cnts.get(sum) || 0) + 1);
  }
  return Math.max(...cnts.values());
};