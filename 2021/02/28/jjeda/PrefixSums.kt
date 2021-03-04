// https://app.codility.com/programmers/lessons/5-prefix_sums/count_div
fun countDiv(A: Int, B: Int, K: Int): Int {
  val startNumber = (A..B).firstOrNull {
    it % K ==0
  } ?: return 0

  return (B - startNumber) / K + 1
}

// https://app.codility.com/programmers/lessons/5-prefix_sums/genomic_range_query
/*
// O(N * M)
fun solution(S: String, P: IntArray, Q: IntArray): IntArray {
  fun String.findMinValue(): Int {
    if (this.contains('A')) return 1
    if (this.contains('C')) return 2
    if (this.contains('G')) return 3
    return 4
  }

  return P.indices.map {
    S.substring(P[it], Q[it] + 1).findMinValue()
  }.toIntArray()
}
*/

// O(N + M)
fun genomicRangeQuery(S: String, P: IntArray, Q: IntArray): IntArray {

  fun generateDeltaList(char: Char): IntArray {
    val deltaArray = IntArray(S.length + 1)
    (0..S.length).fold(0) { acc, index ->
      deltaArray[index] = acc
      if (index == S.length) {
        return@fold 0
      }
      acc + if (S[index] == char) 1 else 0
    }
    return deltaArray
  }

  val deltaA = generateDeltaList('A')
  val deltaC = generateDeltaList('C')
  val deltaG = generateDeltaList('G')

  fun String.findMinValue(start: Int, end: Int): Int {
    if (start == end) {
      return when (S[start]) {
        'A' -> 1
        'C' -> 2
        'G' -> 3
        else -> 4
      }
    }
    if (deltaA[start] != deltaA[end]) return 1
    if (deltaC[start] != deltaC[end]) return 2
    if (deltaG[start] != deltaG[end]) return 3
    return 4
  }

  return P.indices.map {
    S.substring(P[it], Q[it] + 1).findMinValue(P[it], Q[it] + 1)
  }.toIntArray()
}



// TODO: https://app.codility.com/programmers/lessons/5-prefix_sums/min_avg_two_slice


// https://app.codility.com/programmers/lessons/5-prefix_sums/passing_cars
fun passingCars(A: IntArray): Int {
  val accumulateCount = IntArray(A.size)
  A.foldIndexed(0) { index, acc, number ->
    accumulateCount[index] = acc + number
    acc + number
  }
  val totalCount = accumulateCount[A.size - 1]
  var count = 0

  accumulateCount.forEachIndexed { index, number ->
    if (A[index] == 1) return@forEachIndexed
    if (count + totalCount - number > 1000000000) return -1

    count += totalCount - number
  }
  return count
}