package leetcode

class LeetCode1737 {
    fun minCharacters(a: String, b: String): Int {
        return minOf(condition1(a,b) , condition1(b,a), condition2(a,b))
    }
    
    fun condition1(a: String, b: String) : Int{
        return ('b'..'z').map { c -> a.filter{ it >= c }.length + b.filter { it < c }.length }.min()!!
    }

    fun condition2(a: String, b: String) : Int {
        val charCounts = IntArray(26)
        val str = a + b
        str.forEach { charCounts[it - 'a']++ }
        return str.length - charCounts.max()!!
    }
}