import java.lang.StringBuilder
import java.util.*

fun main() {
    val stack = Stack<Char>()
    val sb = StringBuilder()
    var isTag = false
    "${readLine()!!}\n".forEach { c ->
        if (c == '<') isTag = true
        if (isTag || c == ' ' || c == '\n') {
            while (!stack.isEmpty()) {
                sb.append(stack.pop())
            }
            if (c != '\n') sb.append(c)
        } else {
            stack.push(c);
        }
        if (c == '>') isTag = false
    }
    print(sb)
}