package com.huisam.kotlin.leetcode

/**
 * LeetCode Problem
 * @see <a href="https://leetcode.com/problems/sum-root-to-leaf-numbers/submissions/">문제링크</a>
 */
data class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Solution3 {
    fun sumNumbers(root: TreeNode?): Int {
        return if (root?.left == null && root?.right == null) {
            root?.`val` ?: 0
        } else sumHelper(root, 0)
    }

    private fun sumHelper(node: TreeNode?, value: Int): Int {
        if (node?.left == null && node?.right == null) {
            return value + (node?.`val` ?: 0)
        }
        val nextValue = value + node.`val`
        var res = 0

        if (node.left != null) {
            res += sumHelper(node.left, nextValue * 10)
        }
        if (node.right != null) {
            res += sumHelper(node.right, nextValue * 10)
        }

        return res
    }
}

fun main() {
    val left = TreeNode(9)
    val right = TreeNode(0)
    val root = TreeNode(4)
    root.left = left
    root.right = right
    val leftLeft = TreeNode(5)
    val leftRight = TreeNode(1)
    left.left = leftLeft
    left.right = leftRight
    println(Solution3().sumNumbers(root))
}
