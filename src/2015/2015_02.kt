fun main() {
  fun part1(input: List<String>): Int {
    return input
      .map { row -> row.split("x").map { it.toInt() } }
      .sumOf { (l, w, h) ->
        2 * l * w + 2 * w * h + 2 * h * l + listOf(l * w, w * h, h * l).min()
      }
  }

  fun part2(input: List<String>): Int {
    return input
      .map { row -> row.split("x").map { it.toInt() } }
      .sumOf { (l, w, h) ->
        listOf(2 * (l + w), 2 * (w + h), 2 * (h + l)).min() + (l * w * h)
      }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2015/2015_02_test")
  check(part1(testInput) == 101)
  check(part2(testInput) == 48)

  val input = readInput("2015/2015_02")
  part1(input).println()
  part2(input).println()
}
