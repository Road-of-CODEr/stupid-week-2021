import java.util.*

fun main() {
    val str = readLine()!!
    val t = readLine()!!.toInt()

    val st = Stack<Char>()
    val q = LinkedList<Char>()

    for (ch in str) {
        st.push(ch)
    }

    repeat(t) {
        when (val input = readLine()!!) {
            "L" -> if (!st.isEmpty()) q.addFirst(st.pop())
            "D" -> if (!q.isEmpty()) st.push(q.poll())
            "B" -> if (!st.isEmpty()) st.pop()
            else -> st.push(input.split(" ")[1][0])
        }
    }
    println("${st.joinToString("")}${q.joinToString("")}")
}