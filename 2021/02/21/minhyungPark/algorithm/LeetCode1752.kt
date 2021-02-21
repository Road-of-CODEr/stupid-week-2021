package leetcode

import java.util.*

class LeetCode1752 {
    fun check(nums: IntArray): Boolean {
        val n = nums.size
        val sortedNums = nums.sorted().toIntArray()
        return (0..n).map { idx -> IntArray(n) { nums[(idx + it)% n] } }
            .any { Arrays.equals(it, sortedNums) }
    }
}