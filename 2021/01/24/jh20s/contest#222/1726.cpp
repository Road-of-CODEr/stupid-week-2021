class Solution(object):
    def largestSubmatrix(self, matrix):
        n, m = len(matrix), len(matrix[0])
        ans = 0
        for i in range(m):
            for j in range(1,n):
                if matrix[j][i] !=0:
                    matrix[j][i] += matrix[j-1][i]

        for  i in range(n):
            for j,k in enumerate(sorted(matrix[i],reverse=True)):
                ans = max(ans, k* (j+1))
        return ans