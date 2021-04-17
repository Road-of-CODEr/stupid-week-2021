/**
 * @param {string} word
 * @return {number}
 */
const numDifferentIntegers = (word) => {
  const matchedList = word.match(/[0-9]+/g);
  if (!matchedList) return 0;

  const refinedList = matchedList.map((e) => {
    const cur = [...e];

    while (cur[0] === "0") {
      cur.shift();
    }

    return cur.join("");
  });

  const answer = [...new Set(refinedList)].length;
  return answer;
};
