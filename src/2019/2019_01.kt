fun main() {
  fun part1(input: List<String>): Int {
    return input.map { (it.toInt() / 3) - 2 }.sum()
  }

  fun part2(input: List<String>): Int {
    fun process(init: Int): Int {
      var result = 0
      var mass = init

      while (mass > 0) {
        mass = (mass / 3) - 2

        if (mass > 0) {
          result += mass
        }
      }

      return result
    }

    return input.map { process(it.toInt()) }.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2019/Day01_test")
  check(part1(testInput) == 2 + 2 + 654 + 33_583)
  check(part2(testInput) == 2 + 2 + 966 + 50346)

  val input = readInput("2019/Day01")
  part1(input).println()
  part2(input).println()
}
