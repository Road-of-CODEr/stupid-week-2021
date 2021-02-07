import java.util.*

fun main() {
    val N = readLine()!!.toInt()
    val input = readLine()!!.split(" ").map { it.toInt() }
    val stack = Stack<NGE>()
    val answer = MutableList(N) { -1 }

    repeat(N) { i ->
        val number = input[i]
        while (!stack.isEmpty() && number > stack.peek().element) {
            answer[stack.pop().position] = number
        }
        stack.push(NGE(number, i))
    }

    print(answer.joinToString(" "))
}

data class NGE(
    val element: Int,
    val position: Int,
)