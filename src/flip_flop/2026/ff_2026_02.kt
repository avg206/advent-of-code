fun main() {
  fun calculatePosition(position: Int, direction: Char) = when (direction) {
    '>' -> position + 1
    '<' -> position - 1
    else -> position
  }.let {
    when {
      it < 1 -> 100
      it > 100 -> 1
      else -> it
    }
  }

  fun part1(input: String): Int {
    val wall = MutableList(101) { 0 }
    var current = 1

    input.forEach { char ->
      current = calculatePosition(current, char)
      wall[current] += 1
    }

    return wall.indices.zip(wall).maxBy { it.second }.let { (a, b) -> a * b }
  }

  fun part2(input: String): Int {
    var result = 0

    var currentRobot = 1
    var currentSegment = 1

    for (i in input.indices) {
      val j = input.length - 1 - i

      currentRobot = calculatePosition(currentRobot, input[i])
      currentSegment = calculatePosition(currentSegment, input[j])

      if (currentRobot == currentSegment) {
        result += 1
      }
    }

    return result
  }

  fun part3BruteForce(input: String): Int {
    val n = 10_000

    var wall = List(n + 1) { index -> Triple(index + 1, index + 1, 0) }

    var current = 1

    for (i in input.indices) {
      val j = input.length - 1 - i

      current = calculatePosition(current, input[i])

      wall = wall.map { (index, position, heat) ->
        val newPosition = calculatePosition(position, input[j])

        Triple(
          index, newPosition, when {
            newPosition == current -> heat + 1
            else -> heat
          }
        )
      }
    }

    return wall.maxBy { it.third }.let { (a, _, c) -> a * c }
  }

  fun part3(input: String): Int {
    val wall = MutableList(101) { 0 }

    var current = 1
    var diff = 0

    for (i in input.indices) {
      val j = input.length - 1 - i

      when (input[j]) {
        '>' -> diff -= 1
        '<' -> diff += 1
      }

      current = calculatePosition(current, input[i])

      val wallPosition = (current + (diff % 100)).let {
        when {
          it < 1 -> it + 100
          it > 100 -> it - 100
          else -> it
        }
      }

      wall[wallPosition] += 1
    }

    return wall.indices.zip(wall).maxBy { it.second }.let { (a, b) -> a * b }
  }


  // test if implementation meets criteria from the description, like:
  check(part1("><>><<>><<<>>>>><><><><><>>>>>") == 12)
  check(part2("><>><<>><<<>>>>><><><><><>>>>>") == 3)
  check(part3BruteForce("><>><<>><<<>>>>><><><><><>>>>>") == 1_358)
  check(part3("><>><<>><<<>>>>><><><><><>>>>>") == 1_358)

  val input = readInput("flip_flop/2026/ff_2026_02")
  check(part1(input.first()) == 387)
  check(part2(input.first()) == 261)
  check(part3BruteForce(input.first()) == 29_100)
  check(part3(input.first()) == 29_100)
  part1(input.first()).println()
  part2(input.first()).println()
  part3(input.first()).println()
}
