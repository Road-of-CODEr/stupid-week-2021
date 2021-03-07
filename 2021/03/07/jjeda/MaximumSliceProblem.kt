
// TODO: https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_double_slice_sum/

// https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_profit/
fun maxProfit(A: IntArray): Int {
  if (A.isEmpty()) return 0
  var max = 0
  var min = A[0]

  A.forEach {
    max = max.coerceAtLeast(it - min)
    min = min.coerceAtMost(it)
  }

  return max
}

// https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_slice_sum/
fun maxSliceSum(A: IntArray): Int {
  var max = 0

  A.fold(0) { acc, number ->
    val sum = acc + number
    if (sum < 0) return@fold 0
    if (sum < max) return@fold sum
    max = sum
    max
  }
  return if (max == 0) A.maxOrNull()?: 0 else max
}