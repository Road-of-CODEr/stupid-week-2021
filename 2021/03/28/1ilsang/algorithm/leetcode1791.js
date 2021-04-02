/**
 * @param {number[][]} edges
 * @return {number}
 */
const findCenter = (edges) => {
  const hits = edges
    .flatMap((e) => e)
    .reduce((acc, cur) => {
      acc.set(cur, (acc.get(cur) ?? 0) + 1);
      return acc;
    }, new Map());

  const [answer, ...rest] = [...hits].reduce(
    (acc, cur) => {
      const [key, value] = cur;
      const maxValue = acc[1];
      if (value > maxValue) acc = cur;
      return acc;
    },
    ["key", 0]
  );

  return answer;
};
