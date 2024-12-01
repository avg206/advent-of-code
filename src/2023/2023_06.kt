import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

fun main() {
  fun part1(input: List<String>): Int {
    val (_, line1) = input[0].split(":")
    val (_, line2) = input[1].split(":")

    val times = line1.split(" ").mapNotNull { it.toIntOrNull() }
    val distance = line2.split(" ").mapNotNull { it.toIntOrNull() }

    var result = 1

    for (i in times.indices) {
      var count = 0

      for (j in 0..times[i]) {
        if ((times[i] - j) * j > distance[i]) {
          count++
        }
      }

      result *= count
    }

    return result
  }

  fun part2(input: List<String>): Int {
    val (_, line1) = input[0].split(":")
    val (_, line2) = input[1].split(":")

    val time = line1.replace(" ", "").toLong()
    val distance = line2.replace(" ", "").toLong()

    val D = sqrt((time * time - 4 * distance).toDouble())
    val x1 = ((time + D) / 2).toLong()
    val x2 = ((time - D) / 2).toLong()

    val min = min(x1, x2)
    val max = max(x1, x2)

    var count = 0

    for (i in (min - 1)..max + 1) {
      if ((time - i) * i > distance) {
        count++
      }
    }

    return count
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_06_test")
  check(part1(testInput) == 288)
  check(part2(testInput) == 71_503)

  val input = readInput("2023/2023_06")
  check(part1(input) == 4_403_592)
  check(part2(input) == 38_017_587)
  part1(input).println()
  part2(input).println()
}
