fun main() {
  fun part1(input: List<String>): Int {
    return input.map { it.toInt() }.filter { it < 60 }.sumOf { 60 - it }
  }

  fun part2(input: List<String>): Int {
    return input.map { it.toInt() }.sumOf {
      when {
        it < 60 -> 60 - it
        else -> (it - 60) * 5
      }
    }
  }

  fun part3(input: List<String>): Int {
    val numbers = input.map { it.toInt() }

    val currents = numbers.subList(0, numbers.size / 2 + 1)
    val targets = numbers.subList(numbers.size / 2, numbers.size)

    return currents.zip(targets)
      .sumOf { (current, target) ->
        when {
          current < target -> target - current
          else -> (current - target) * 5
        }
      }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2026/ff_2026_01_test")
  check(part1(testInput) == 76)
  check(part2(testInput) == 1_371)
  check(part3(testInput) == 1141)

  val input = readInput("flip_flop/2026/ff_2026_01")
  check(part1(input) == 1_187)
  check(part2(input) == 14_547)
  check(part3(input) == 8_423)
  part1(input).println()
  part2(input).println()
  part3(input).println()
}
