fun main() {
  fun part1(input: List<String>): Int {
    return input.size
  }

  fun part2(input: List<String>): Int {
    return input.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("<year>/<year>_<day>_test")
  check(part1(testInput) == 1)

  val input = readInput("<year>/<year>_<day>")
  part1(input).println()
  part2(input).println()
}
