/**
 * @param {string} sentence
 * @return {boolean}
 */
const checkIfPangram = (sentence) => {
  return [...new Set([...sentence])].length >= 26;
};
