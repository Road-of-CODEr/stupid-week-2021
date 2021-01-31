import java.util.*

fun main () {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val k = scanner.nextInt()

    val queue = LinkedList((1..n).toMutableList())

    print("<")
    while (queue.size > 1) {
        repeat((1 until k).count()) { queue.add(queue.pop()) }
        print("${queue.pop()}, ")
    }
    print("${queue.remove()}>")
}