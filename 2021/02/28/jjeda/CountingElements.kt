// https://app.codility.com/programmers/lessons/4-counting_elements/frog_river_one
fun frogRiverOne(X: Int, A: IntArray): Int {
  val set = (1..X).toMutableSet()
  return A.withIndex().firstOrNull {
    set.remove(it.value)
    set.size == 0
  }?.index ?: -1
}

// https://app.codility.com/programmers/lessons/4-counting_elements/max_counters
fun maxCounters(N: Int, A: IntArray): IntArray {
  var maxNumber = 0
  var result = IntArray(N)

  A.forEach {
    if (it == N +1) {
      result = IntArray(N) { maxNumber }
      return@forEach
    }
    maxNumber = (++result[it - 1]).coerceAtLeast(maxNumber)
  }
  return result
}

// https://app.codility.com/programmers/lessons/4-counting_elements/missing_integer
fun missingInteger(A: IntArray): Int {
  val set = (1..A.size + 1).toMutableSet()
  A.forEach {
    set.remove(it)
  }
  return set.minOrNull() ?: -1
}

// https://app.codility.com/programmers/lessons/4-counting_elements/perm_check
fun permCheck(A: IntArray): Int {
  val set = (1..A.size).toMutableSet()
  A.forEach {
    set.remove(it)
  }
  return if (set.isEmpty()) 1 else 0
}