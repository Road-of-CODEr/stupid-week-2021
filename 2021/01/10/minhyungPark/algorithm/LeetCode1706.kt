package leetcode

class LeetCode1706 {
    fun findBall(grid: Array<IntArray>): IntArray {
        val m = grid.size
        val n = grid[0].size
        val res = IntArray(n)
        for (i in 0 until n) {
            var col = i
            var nextCol: Int
            for (j in 0 until m) {
                nextCol = col + grid[j][col]
                if (nextCol < 0 || nextCol >= n || grid[j][nextCol] != grid[j][col]) {
                    col = -1
                    break
                }
                col = nextCol
            }
            res[i] = col
        }
        return res
    }
}