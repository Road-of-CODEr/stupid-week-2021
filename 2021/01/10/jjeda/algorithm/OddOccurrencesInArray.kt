package jan

fun solution(A: IntArray): Int {
  val cache = mutableSetOf<Int>()

  for (number in A) {
    if (cache.contains(number)) {
      cache.remove(number)
    } else {
      cache.add(number)
    }
  }
  return cache.first()
  // return A.reduce { acc, number -> acc.xor(number) }
}
