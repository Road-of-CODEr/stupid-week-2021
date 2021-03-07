/**
 * @param {string} word1
 * @param {string} word2
 * @return {string}
 */
const mergeAlternately = (word1, word2) => {
  let wordLength = 0;
  let mergeStr = "";
  if (word1.length >= word2.length) {
    wordLength = word1.length;
  } else {
    wordLength = word2.length;
  }
  for (let i = 0; i < wordLength; i++) {
    if (word1[i] == undefined) {
      mergeStr = mergeStr + word2[i];
    } else if (word2[i] == undefined) {
      mergeStr = mergeStr + word1[i];
    } else {
      mergeStr = mergeStr + word1[i] + word2[i];
    }
  }
  return mergeStr;
};
