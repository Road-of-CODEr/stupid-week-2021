import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var t = br.readLine().toInt()

    while(t-- > 0) {
        val input = br.readLine().split(" ")
        println(input.joinToString(" ") { it.reversed() })
    }
}