/**
 * @param {string} s
 * @return {boolean}
 */
const halvesAreAlike = (s) => {
  const half = s.length / 2;
  const vowels = new Set(['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']);
  const [left, right] = Array.from(s).reduce((acc, cur, index) => {
    if(!vowels.has(cur)) return acc;
    index < half ? acc[0]++ : acc[1]++;
      
    return acc;
  }, [0, 0]);
  
  return left === right;
};