/**
 * @param {number[]} apples
 * @param {number[]} days
 * @return {number}
 */
var eatenApples = (apples, days) => {
  let time = new Array(40001);
  let ans = 0;
  let last = apples.length;
  for (let i = 0, j = Infinity; i <= last; i++) {
    if (j < i) j = i;
    if (apples[i]) {
      let exp = i + days[i] - 1;
      if (time[exp]) time[exp] += apples[i];
      else time[exp] = apples[i];
      if (exp < j) j = exp;
      if (exp > last) last = exp;
    }
    while (!time[j] && j < last) j++;
    if (time[j]) ans++, time[j]--;
  }
  return ans;
};
