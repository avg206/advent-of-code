fun main() {
  fun part1(input: List<String>): Int {
    return input.sumOf { it.length / 2 }
  }

  fun part2(input: List<String>): Int {
    return input.filter { it.length % 4 == 0 }.sumOf { it.length / 2 }
  }

  fun part3(input: List<String>): Int {
    return input.filter { !it.contains("ne") }.sumOf { it.length / 2 }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2025/ff_2025_01_test")
  check(part1(testInput) == 24)
  check(part2(testInput) == 16)
  check(part3(testInput) == 19)

  val input = readInput("flip_flop/2025/ff_2025_01")
  check(part1(input) == 10_518)
  check(part2(input) == 5_312)
  check(part3(input) == 3_023)
  part1(input).println()
  part2(input).println()
  part3(input).println()
}
