/**
 * @param {number[][]} adjacentPairs
 * @return {number[]}
 */
const restoreArray = (adjacentPairs) => {
  if (adjacentPairs === null || adjacentPairs.length === 0) return [];
  if (adjacentPairs.length === 1) return adjacentPairs[0];

  // Map of { element: Set{adjacent elements} }
  const pairMap = new Map();
  for (const el of adjacentPairs) {
    if (!pairMap.has(el[0])) pairMap.set(el[0], new Set());
    if (!pairMap.has(el[1])) pairMap.set(el[1], new Set());

    const first = pairMap.get(el[0]),
      second = pairMap.get(el[1]);

    first.add(el[1]);
    second.add(el[0]);
  }

  const result = new Array(pairMap.size);
  let left = 0,
    right = pairMap.size - 1,
    sideSwitch = true; // true is left, false is right

  const q = [];
  for (const [value, set] of pairMap.entries()) {
    if (set.size === 1) {
      q.push(value);
    }
  }

  while (q.length > 0) {
    const curr = q.shift();

    if (sideSwitch) result[left++] = curr;
    else result[right--] = curr;

    sideSwitch = !sideSwitch;

    for (const el of pairMap.get(curr).values()) {
      const adjSet = pairMap.get(el);
      adjSet.delete(curr);
      if (adjSet.size === 1) q.push(el);
    }
  }

  return result;
};
