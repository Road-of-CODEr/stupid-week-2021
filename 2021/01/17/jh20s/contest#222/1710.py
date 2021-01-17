class Solution:
    def maximumUnits(self, boxTypes: List[List[int]], truckSize: int) -> int:
        res = 0
        for box in sorted(boxTypes, key=lambda x: x[1], reverse = True):
            res += min(box[0], truckSize) * box[1]
            truckSize = truckSize - box[0]
            if truckSize <=0:
                break
        
        return res