import kotlin.math.min

class LeetCode1725 {
    fun countGoodRectangles(rectangles: Array<IntArray>): Int {
        val squareLengths = rectangles.map { min(it[0], it[1]) }
        val maxLen = squareLengths.max()!!
        return squareLengths.filter { it == maxLen }.size
    }
}