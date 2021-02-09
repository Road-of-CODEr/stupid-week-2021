/**
 * @param {string} time
 * @return {string}
 */
const maximumTime = (time) => {
  const t = time.split("");
  if (t[0] === "?") t[0] = "0123?".includes(t[1]) ? "2" : "1";
  if (t[1] === "?") t[1] = t[0] === "2" ? "3" : "9";
  if (t[3] === "?") t[3] = "5";
  if (t[4] === "?") t[4] = "9";
  return t.join("");
};
