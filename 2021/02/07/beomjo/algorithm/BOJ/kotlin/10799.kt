import java.util.*

fun main() {
    val input = readLine()!!
    val stack = Stack<Char>()
    var t = 0
    var before = ' '
    input.forEach { c ->
        if (c == '(') stack.push(c)
        else {
            stack.pop()
            t += if (before == '(') stack.size else 1
        }
        before = c
    }
    println(t)
}