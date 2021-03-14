/**
 * @param {string[][]} items
 * @param {string} ruleKey
 * @param {string} ruleValue
 * @return {number}
 */
const getKeyIndex = (key) => (key === "type" ? 0 : key === "color" ? 1 : 2);

const countMatches = (items, ruleKey, ruleValue) => {
  return items.reduce((acc, cur) => {
    const keyIndex = getKeyIndex(ruleKey);
    if (ruleValue === cur[keyIndex]) acc += 1;
    return acc;
  }, 0);
};
