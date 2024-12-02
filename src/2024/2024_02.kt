fun main() {
  fun part1(input: List<String>): Int {
    fun check(report: List<Int>): Boolean {
      report.fold(-1) { prev, curr ->
        if (prev == -1) return@fold curr
        if (curr - prev !in 1..3) return false
        curr
      }

      return true
    }

    return input
      .map { row -> row.split(" ").map { it.toInt() } }
      .count { check(it) || check(it.reversed()) }
  }

  fun part2(input: List<String>): Int {
    fun check(report: List<Int>): Boolean {
      for (r in report.indices) {
        var good = true

        var previous = -1
        report.forEachIndexed { index, curr ->
          if (index == r) return@forEachIndexed
          if (previous == -1) {
            previous = curr
            return@forEachIndexed
          }
          if (curr - previous !in 1..3) good = false
          previous = curr
        }

        if (good) return true
      }

      return false
    }

    return input
      .map { row -> row.split(" ").map { it.toInt() } }
      .count { check(it) || check(it.reversed()) }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_02_test")
  check(part1(testInput) == 2)
  check(part2(testInput) == 4)

  val input = readInput("2024/2024_02")
  check(part1(input) == 220)
  check(part2(input) == 296)
  part1(input).println()
  part2(input).println()
}
