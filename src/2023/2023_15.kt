fun main() {
  fun hashString(string: String): Int {
    val base = 256
    val multiplier = 17

    return string.fold(0) { acc: Int, c: Char ->
      ((acc + c.code) * multiplier) % base
    }
  }

  fun part1(input: List<String>): Int {
    return input[0]
      .split(',')
      .sumOf { hashString(it) }
  }

  fun part2(input: List<String>): Int {
    val box = List<LinkedHashMap<String, String>>(256) { LinkedHashMap() }

    input[0].split(",").forEach { lean ->
      when {
        lean.contains("=") -> {
          val (a, b) = lean.split("=")

          box[hashString(a)][a] = b
        }

        lean.contains("-") -> {
          val (a) = lean.split("-")

          box[hashString(a)].remove(a)
        }

        else -> throw Exception("Unknown lean")
      }
    }

    return box.foldIndexed(0) { boxIndex, boxSum, leans ->
      boxSum + leans.entries.foldIndexed(0) { index, acc, (_, value) ->
        acc + (boxIndex + 1) * (index + 1) * (value.toInt())
      }
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_15_test")
  check(part1(testInput) == 1320)
  check(part2(testInput) == 145)

  val input = readInput("2023/2023_15")
  check(part1(input) == 517315)
  check(part2(input) == 247763)
  part1(input).println()
  part2(input).println()
}
