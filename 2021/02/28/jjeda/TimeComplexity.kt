import kotlin.math.abs
import kotlin.math.min

// https://app.codility.com/programmers/lessons/3-time_complexity/frog_jmp
fun flogJmp(X: Int, Y: Int, D: Int): Int {
  val distance = Y - X
  if (distance % D == 0) return distance / D
  return distance / D + 1
}

// https://app.codility.com/programmers/lessons/3-time_complexity/perm_missing_elem
fun permMissingElem(A: IntArray): Int {
  if (A.isEmpty()) return 1
  val set = A.toSet()
  return (1..A.size + 1).first {
    !set.contains(it)
  }
}

// https://app.codility.com/programmers/lessons/3-time_complexity/tape_equilibrium
fun tapeEquilibrium(A: IntArray): Int {
  var sumA = A.sum() - A[0]
  var result = Int.MAX_VALUE

  A.reduce { acc, number ->
    result = min(result, abs(acc - sumA))
    sumA -= number
    acc + number
  }
  return result
}