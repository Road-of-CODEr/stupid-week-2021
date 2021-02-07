package leetcode

class LeetCode1736 {

    fun maximumTime(time: String): String {
        val times = time.toCharArray()
        if (times[0] == '?') {
            times[0] = if (times[1] == '?') '2' else if (times[1] > '3') '1' else '2'
        }
        if (times[1] == '?') {
            times[1] = if (times[0] <= '1') '9' else '3'
        }
        if (times[3] == '?') {
            times[3] = '5'
        }
        if (times[4] == '?') {
            times[4] = '9'
        }
        return String(times)
    }
}