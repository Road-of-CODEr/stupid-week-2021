
// https://app.codility.com/programmers/lessons/6-sorting/distinct/
fun distinct(A: IntArray): Int {
  return A.toSet().size
}

// https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/
fun maxProductOfThree(A: IntArray): Int {
  val indexWithA = A.withIndex().sortedBy { it.value }
  val length = A.size
  val positiveMaxValue = indexWithA[length - 1].value * indexWithA[length - 2].value * indexWithA[length - 3].value
  val negativeMaxValue = indexWithA[0].value * indexWithA[1].value * indexWithA[length - 1].value

  return if (positiveMaxValue > negativeMaxValue) positiveMaxValue else negativeMaxValue
}

// https://app.codility.com/programmers/lessons/6-sorting/number_of_disc_intersections/
// TODO: Refactor this O(N^2) time complexity
fun numberOfDiscIntersections(A: IntArray): Int {
  val sortedA = A.withIndex().sortedBy { it.index - it.value }

  return sortedA.asSequence().mapIndexed { index, sortedValue ->
    val rightEnd = if (sortedValue.index + sortedValue.value >= Int.MAX_VALUE) {
      Int.MAX_VALUE
    } else {
      sortedValue.index + sortedValue.value
    }
    sortedA.subList(index + 1, A.size).count {
      indexedValue ->
      rightEnd >= indexedValue.index - indexedValue.value
    }
  }.reduce { acc, number ->
    if (acc > 10000000) return -1
    acc + number
  }
}

// https://app.codility.com/programmers/lessons/6-sorting/triangle/
fun triangle(A: IntArray): Int {
  val windowedA = A.sorted().reversed().windowed(3, 1)

  windowedA.forEach {
    if (it[0] < it[1] + it[2]) return 1
  }
  return 0
}