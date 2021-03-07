/**
 * @param {string} boxes
 * @return {number[]}
 */
const minOperations = (boxes) => {
  return boxes.split("").map((box1, index1, arr) => {
    return arr.reduce(
      (count, box2, index2) =>
        count + (box2 === "1" ? Math.abs(index1 - index2) : 0),
      0
    );
  });
};
