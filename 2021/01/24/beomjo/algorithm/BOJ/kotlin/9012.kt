fun main() {
    repeat(readLine()?.toInt() ?: return) {
        var prev = readLine()!!
        var curr = prev.replace("()", "")

        while (prev != curr) {
            prev = curr
            curr = curr.replace("()", "")
        }
        println(if (curr.isEmpty()) "YES" else "NO")
    }
}