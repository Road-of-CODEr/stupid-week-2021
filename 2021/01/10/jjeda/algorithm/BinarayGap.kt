package jan

fun solution(N: Int): Int {
  val binaryString = N.toString(2)
  var longestGap = 0
  var count = 0
  val isStart = binaryString[0] == '1'

  for (character in binaryString) {
    if (!isStart) {
      continue
    }

    if (character == '1') {
      longestGap = longestGap.coerceAtLeast(count)
      count = -1
    }
    count++
  }
  return longestGap
}