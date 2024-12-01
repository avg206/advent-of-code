import helpers.point.Point

fun main() {
  fun calculateNewPosition(input: List<String>, positions: Set<Point>): Set<Point> {
    fun chatAtPosition(current: Point): Char {
      var x = current.x % input.size
      var y = current.y % input[0].length

      if (x < 0) x += input.size
      if (y < 0) y += input[0].length

      return input[x][y]
    }

    return positions
      .flatMap { it.neighbours() }
      .filter { chatAtPosition(it) != '#' }
      .toSet()
  }

  fun part1(input: List<String>, steps: Int): Int {
    var start = Point(0, 0)

    for (i in input.indices) {
      for (j in input[0].indices) {
        if (input[i][j] == 'S') {
          start = Point(i, j)
        }
      }
    }

    var positions = setOf(start)

    repeat(steps) {
      positions = calculateNewPosition(input, positions)
    }

    return positions.size
  }

  fun checker(input: List<String>, steps: Int) {
    var start = Point(0, 0)

    for (i in input.indices) {
      for (j in input[0].indices) {
        if (input[i][j] == 'S') {
          start = Point(i, j)
        }
      }
    }

    var positions = setOf(start)
    var pp0 = 0
    var pp1 = 0

    for (step in 1..steps) {
      positions = calculateNewPosition(input, positions)

      if (step % 131 == 65) {
        println("$step - a - ${(positions.size)}")
        println("$step - b - ${(positions.size - pp0)}")
        println("$step - c - ${(positions.size - pp0) - pp1}")
        println("")

        pp1 = (positions.size - pp0)
        pp0 = positions.size
      }
    }
  }

  fun part2(steps: Int): Long {
    val startStep = 327
    val move = 131

    var count = 90974L // a
    val countStepDiff = 29034L // c
    var countStep = 58166L + countStepDiff // b + c

    for (step in startStep until steps step move) {
      count += countStep

      countStep += countStepDiff
    }

    return count
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2023/2023_21_test")
  check(part1(testInput, 6) == 16)

  val input = readInput("2023/2023_21")
  check(part1(input, 64) == 3_578)
  check(part2(26_501_365) == 594_115_391_548_176L)
//    checker(input, 600)
  part1(input, 64).println()
  part2(26_501_365).println()
}
