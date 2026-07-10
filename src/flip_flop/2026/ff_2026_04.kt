fun main() {
  fun part1(input: List<String>, cutLevel: Int): Int {
    val result = input.reversed().subList(cutLevel, input.size).count { it == "  |-o" || it == "o-|" }

    return result
  }


  fun part2(input: List<String>): Int {
    var result = 0

    var side = -1

    for (i in input.indices.reversed()) {
      val newSide = when (input[i]) {
        "o-|" -> 1
        "  |-o" -> 2
        else -> side
      }

      if (newSide != side && side != -1) {
        result += 1
      }

      side = newSide
    }

    return result
  }


  fun part3(input: List<String>): Int {
    var result = 0
    val flower = input.reversed().drop(1).dropLast(3).toMutableList()

    while (true) {
      var last = -1
      var side = -1

      for (i in flower.indices) {
        if (flower[i] == "  |") continue

        val current = when (flower[i]) {
          "o-|" -> 1
          "  |-o" -> 2
          else -> side
        }

        if (current != side && side != -1) {
          flower[last] = "  |"
        }

        side = current

        last = i
      }

      if (last == -1) break

      flower[last] = "  |"
      result += 1
    }

    return result
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2026/ff_2026_04_test")
  check(part1(testInput, 8) == 8)
  check(part2(testInput) == 6)
  check(part3(testInput) == 5)

  val input = readInput("flip_flop/2026/ff_2026_04")
  check(part1(input, 401) == 214)
  check(part2(input) == 160)
  check(part3(input) == 29)
  part1(input, 401).println()
  part2(input).println()
  part3(input).println()
}
