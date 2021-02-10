import java.util.*

fun getGCP(a: Int, b: Int): Int = if (b == 0) a else getGCP(b, a % b)
fun getLCM(a: Int, b: Int): Int = a * b / getGCP(a, b)

fun main() {
    val sc = Scanner(System.`in`)
    val a = sc.nextInt()
    val b = sc.nextInt()

    val gcp = getGCP(a, b)
    val lcm = getLCM(a, b)

    println(gcp)
    println(lcm)
}
