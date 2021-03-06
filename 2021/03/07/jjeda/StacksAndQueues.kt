import java.util.*

// https://app.codility.com/programmers/lessons/7-stacks_and_queues/brackets/
fun brackets(S: String): Int {
  val bracketsMapping = mapOf(')' to '(', '}' to '{', ']' to '[')
  val stack = S.fold(Stack<Char>()) { stack, char ->
    if (char in arrayOf('(', '{', '[')) {
      stack.push(char)
      return@fold stack
    }
    if (stack.isEmpty() || bracketsMapping[char] != stack.pop()) return 0
    stack
  }
  return if (stack.isEmpty()) 1 else 0
}

// https://app.codility.com/programmers/lessons/7-stacks_and_queues/fish/
fun fish(A: IntArray, B: IntArray): Int {
  var liveInUpstreamCount = 0
  val downStream = A.foldIndexed(Stack<Int>()) { index, downStreamStack, size ->
    val isDownstreamFish = B[index] == 1
    if (isDownstreamFish) {
      downStreamStack.push(size)
      return@foldIndexed downStreamStack
    }
    if (downStreamStack.isEmpty()) {
      liveInUpstreamCount++
      return@foldIndexed downStreamStack
    }

    while (downStreamStack.isNotEmpty() && downStreamStack.peek() < size) {
      downStreamStack.pop()
      if (downStreamStack.isEmpty()) {
        liveInUpstreamCount++
      }
    }
    downStreamStack
  }
  return downStream.size + liveInUpstreamCount
}

// https://app.codility.com/programmers/lessons/7-stacks_and_queues/nesting/
fun nesting(S: String): Int {
  val remainBracketStack = S.fold(Stack<Char>()) { bracketStack, char ->
    if (char == '(') {
      bracketStack.push(char)
      return@fold bracketStack
    }
    if (bracketStack.isEmpty()) return 0
    bracketStack.pop()
    return@fold bracketStack
  }

  return if (remainBracketStack.isEmpty()) 1 else 0
}

// https://app.codility.com/programmers/lessons/7-stacks_and_queues/stone_wall/
fun stoneWall(H: IntArray): Int {
  data class Accumulator(val blockStack: Stack<Int>, var blockCount: Int)

  val accumulator = H.fold(Accumulator(Stack<Int>(), 0)) { acc, height ->
    if (acc.blockStack.isEmpty()) {
      acc.blockStack.push(height)
      acc.blockCount++
      return@fold acc
    }

    while (acc.blockStack.peek() > height) {
      acc.blockStack.pop()
      if (acc.blockStack.isEmpty()) {
        acc.blockStack.push(height)
        acc.blockCount++
      }
    }
    if (acc.blockStack.peek() == height) {
      return@fold acc
    }
    acc.blockStack.push(height)
    acc.blockCount++
    acc
  }
  return accumulator.blockCount
}