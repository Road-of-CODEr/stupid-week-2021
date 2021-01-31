/**
 * @param {number[][]} rectangles
 * @return {number}
 */
const countGoodRectangles = (rectangles) => {
  const squareLenArr = []
  rectangles.forEach( rect => {
      squareLenArr.push(Math.min(...rect))
  });
  const largest = Math.max(...squareLenArr)
  return squareLenArr.filter( l => l == largest ).length
};