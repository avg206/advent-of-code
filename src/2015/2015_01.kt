fun main() {
  fun part1(input: String): Int {
    return input.toList().groupBy { it }.map {
      when (it.key) {
        ')' -> it.value.size * -1
        else -> it.value.size
      }
    }.sum()
  }

  fun part2(input: String): Int {
    var level = 0

    for (i in input.indices) {
      when (input[i]) {
        '(' -> level += 1
        ')' -> level -= 1
      }

      if (level < 0) return i + 1
    }

    throw Exception("Doesn't enter basement")
  }

  // test if implementation meets criteria from the description, like:
  check(part1("(())") == 0)
  check(part1("(()(()(") == 3)
  check(part1("))(((((") == 3)
  check(part1(")())())") == -3)

  val input = readInput("2015/2015_01")
  part1(input.first()).println()
  part2(input.first()).println()
}
