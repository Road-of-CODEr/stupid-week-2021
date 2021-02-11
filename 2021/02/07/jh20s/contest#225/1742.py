class Solution:
    def restoreArray(self, adjacentPairs: List[List[int]]) -> List[int]:
        h = defaultdict(list)
        for pair in adjacentPairs:    # map each element to its neighbours
            h[pair[0]].append(pair[1])
            h[pair[1]].append(pair[0])
        arr = [0] * (len(adjacentPairs) + 1)
        extremes = []    # stores the values of the first and last elements
        for key, value in h.items():
            if len(value) == 1:    # extreme elements have only one neighbour
                extremes.append(key)
        arr[0], arr[-1] = extremes    
        curr = h[arr[0]][0] 
        for i in range(1, len(arr) - 1):
            # at every step, find the next neighbour and proceed
            arr[i] = curr
            first, second = h[curr]
            # check which neighbour is the previous element
            curr = second if first == arr[i - 1] else first    
        return arr