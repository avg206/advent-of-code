import kotlin.math.abs

fun main() {
  fun part1(input: List<String>): Int {
    var left = listOf<Int>()
    var right = listOf<Int>()

    input
      .map { row -> row.split("   ").map { it.toInt() } }
      .forEach { (from, to) ->
        left = left + from
        right = right + to
      }

    left = left.sorted()
    right = right.sorted()

    return left.indices.sumOf { index -> abs(left[index] - right[index]) }
  }

  fun part2(input: List<String>): Int {
    val left = mutableListOf<Int>()
    val right = mutableMapOf<Int, Int>()

    input
      .map { row -> row.split("   ").map { it.toInt() } }
      .forEach { (from, to) ->
        left.add(from)
        right[to] = (right[to] ?: 0) + 1
      }

    return left.sumOf { it * (right[it] ?: 0) }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_01_test")
  check(part1(testInput) == 11)
  check(part2(testInput) == 31)

  val input = readInput("2024/2024_01")
  check(part1(input) == 1_889_772)
  check(part2(input) == 23_228_917)
  part1(input).println()
  part2(input).println()
}
