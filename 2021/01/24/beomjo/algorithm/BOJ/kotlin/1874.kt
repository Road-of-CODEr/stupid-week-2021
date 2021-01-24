import java.lang.StringBuilder
import java.util.*

fun main() {
    val stack = Stack<Int>()
    val sb = StringBuilder()
    var i = 1;
    repeat(readLine()?.toInt() ?: return) {
        val input = readLine()!!.toInt()
        while (i <= input) {
            stack.push(i)
            sb.append("+\n")
            i++
        }
        if (stack.peek() == input) {
            stack.pop()
            sb.append("-\n")
        } else {
            print("NO")
            return
        }
    }
    println(sb)
    return
}