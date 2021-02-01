class Solution:
    def kthLargestValue(self, matrix: List[List[int]], k: int) -> int:
        m, n = len(matrix), len(matrix[0]) # dimensions 
        
        ans = []
        for i in range(m): 
            for j in range(n): 
                if i: matrix[i][j] ^= matrix[i-1][j]
                if j: matrix[i][j] ^= matrix[i][j-1]
                if i and j: matrix[i][j] ^= matrix[i-1][j-1]
                ans.append(matrix[i][j])
        return sorted(ans)[-k] 