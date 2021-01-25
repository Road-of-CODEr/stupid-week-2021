class LeetCode1727 {
    fun largestSubmatrix(matrix: Array<IntArray>): Int {
        for (i in matrix.indices) {
            if (i == 0) continue
            for (j in matrix[0].indices) {
                if (matrix[i][j] == 1) {
                    matrix[i][j] = matrix[i-1][j] + 1
                }
            }
        }

        var count = 0
        for (i in matrix.indices) {
            matrix[i].sort()
            for (j in 1..matrix[0].size) {
                count = max(count, j * matrix[i][matrix[0].size - j])
            }
        }
        return count
    }

}