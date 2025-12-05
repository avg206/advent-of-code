fun main() {
  fun part1(input: List<String>): Int {
    val ranges = mutableListOf<LongRange>()

    val divider = input.indexOfFirst { it.isEmpty() }

    for (i in 0..<divider) {
      val (a, b) = input[i].split("-").map { it.toLong() }

      ranges.add(a..b)
    }

    var result = 0

    for (i in divider + 1..input.lastIndex) {
      val number = input[i].toLong()

      if (ranges.any { number in it }) {
        result += 1
      }
    }

    return result
  }

  fun part2(input: List<String>): Long {
    var result = 0L
    val divider = input.indexOfFirst { it.isEmpty() }

    val row = mutableListOf<Pair<Long, Int>>()

    for (i in 0..<divider) {
      val (a, b) = input[i].split("-").map { it.toLong() }

      row.add(a to 1)
      row.add(b to 2)
    }

    row.sortWith(compareBy({ it.first }, { it.second }))

    var depth = 0
    var last = row.first().first

    for ((number, type) in row) {
      when (type) {
        1 -> {
          depth += 1

          if (depth == 1) {
            last = number
          }
        }

        2 -> {
          depth -= 1

          if (depth == 0) {
            result += (number - last + 1)
          }
        }
      }
    }

    return result
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_05_test")
  check(part1(testInput) == 3)
  check(part2(testInput) == 14L)

  val input = readInput("2025/2025_05")
  check(part1(input) == 652)
  check(part2(input) == 341_753_674_214_273L)
  part1(input).println()
  part2(input).println()
  // - 341753674214281
  // + 341753674214273
}
