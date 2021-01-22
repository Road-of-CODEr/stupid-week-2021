/**
 * @param {number[][]} boxTypes
 * @param {number} truckSize
 * @return {number}
 */
const maximumUnits = (boxTypes, truckSize) => {
  let noOfUnits = 0;
  let loadedBoxes = 0;
  let i = 0;

  boxTypes.sort((a, b) => b[1] - a[1]);

  while (loadedBoxes < truckSize && boxTypes[i]) {
    let currentBoxs = boxTypes[i][0];

    if (currentBoxs + loadedBoxes > truckSize) {
      currentBoxs = truckSize - loadedBoxes;
    }

    loadedBoxes += currentBoxs;
    noOfUnits += currentBoxs * boxTypes[i][1];

    if (loadedBoxes === truckSize) return noOfUnits;

    i++;
  }

  return noOfUnits;
};
