package jan

fun solution(A: IntArray, K: Int): IntArray {
  val moveRightCount = if (A.isNotEmpty()) K % A.size else 0
  if (moveRightCount == 0) {
    return A
  }
  val list = A.toMutableList()

  val leftList = list.subList(A.size - moveRightCount, A.size)
  val rightList = list.subList(0, A.size - moveRightCount)

  return (leftList + rightList).toIntArray()
}
