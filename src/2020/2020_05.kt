fun main() {
  val codes = mapOf(
    'F' to 0,
    'B' to 1,
    'L' to 0,
    'R' to 1,
  )

  fun decode(code: String): Int {
    return code.map {
      when (codes.contains(it)) {
        true -> codes[it]
        else -> 0
      }
    }.joinToString("").toInt(2)
  }

  fun part1(input: List<String>): Int {
    return input.maxOf { decode(it.take(7)) * 8 + decode(it.takeLast(3)) }
  }

  fun part2(input: List<String>): Int {
    val seats = input.map { decode(it.take(7)) * 8 + decode(it.takeLast(3)) }

    for (i in 1..127) {
      for (j in 1..8) {
        val id = i * 8 + j

        if (seats.contains(id)) {
          continue
        }

        if (seats.contains(id - 1) && seats.contains(id + 1)) {
          return id
        }
      }
    }

    throw Exception("Not found")
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2020/2020_05_test")
  check(part1(testInput) == 820)

  val input = readInput("2020/2020_05")
  part1(input).println()
  part2(input).println()
}
