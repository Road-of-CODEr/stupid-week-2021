package leetcode

import java.util.*
import java.util.PriorityQueue

class LeetCode1705 {
    fun eatenApples(apples: IntArray, days: IntArray): Int {
        var res = 0
        val n = apples.size
        val queue = PriorityQueue<Pair<Int, Int>> { a, b -> a.second - b.second }
        var i = 0
        while (i < n || !queue.isEmpty()) {
            if (i < n)
                queue.offer(apples[i] to i + days[i])

            while (queue.isNotEmpty() && queue.peek().second <= i) {
                queue.poll()
            }

            if (queue.isNotEmpty()) {
                ++res
                val current = queue.poll()
                if (current.first - 1 != 0) {
                    queue.offer(current.first -1 to current.second)
                }
            }
            ++i
        }
        return res
    }
}