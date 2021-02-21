package leetcode

class LeetCode1753 {
    fun maximumScore(a: Int, b: Int, c: Int): Int {
        val list = listOf(a, b, c).sorted()
        val sum = list[0] + list[1]
        return if (sum >= list[2]) list[2] + (sum - list[2])/2 else sum
    }
}