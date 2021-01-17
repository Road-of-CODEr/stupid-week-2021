package com.huisam.kotlin.leetcode

/**
 * leetCode Problem
 * @see <a href="https://leetcode.com/problems/contains-duplicate/">문제링크</a>
 */
fun containsDuplicate(nums: IntArray): Boolean = when {
    nums.isNotEmpty() -> nums.toSet().size != nums.size
    else -> false
}

fun main() {
    println(containsDuplicate(intArrayOf(1, 2, 3, 4)))
    println(containsDuplicate(intArrayOf()))
    println(containsDuplicate(intArrayOf(1, 2, 3, 2)))
}