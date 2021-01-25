class LeetCode1726 {
    fun tupleSameProduct(nums: IntArray): Int {
        val tupleMap = mutableMapOf<Int, Int>()
        var res = 0
        for (i in nums.indices) {
            for (j in 0 until i) {
                val product = nums[i] * nums[j]
                res += tupleMap[product] ?: 0
                tupleMap[product] = 1 + (tupleMap[product] ?: 0)
            }
        }
        return res * 8
    }
}