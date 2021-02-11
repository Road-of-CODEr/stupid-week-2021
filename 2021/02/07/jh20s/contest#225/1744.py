class Solution:
    def canEat(self, candiesCount, queries):
        A = [0] + list(accumulate(candiesCount))
        return [A[t] // c <= d < A[t + 1] for t, d, c in queries]