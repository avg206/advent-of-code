import helpers.point.Point

fun main() {
  fun part1(input: List<String>): Int {
    var result = 0

    for (i in input.indices) {
      for (j in input[i].indices) {
        if (input[i][j] != '@') continue

        var point = Point(i, j)

        if (point.extendedNeighbours().filter { it.x in input.indices && it.y in input[0].indices }
            .count { input[it.x][it.y] == '@' } < 4) {
          result += 1
        }
      }
    }

    return result
  }

  fun part2(input: List<String>): Int {
    var grid = input.toMutableList().map { it.toMutableList() }

    var result = 0

    while (true) {
      val toBeRemoved = mutableSetOf<Point>()

      for (i in grid.indices) {
        for (j in grid[i].indices) {
          if (grid[i][j] != '@') continue

          var point = Point(i, j)

          if (point.extendedNeighbours().filter { it.x in grid.indices && it.y in grid[0].indices }
              .count { grid[it.x][it.y] == '@' } < 4) {
            toBeRemoved += point
          }
        }
      }

      if (toBeRemoved.isNotEmpty()) {
        result += toBeRemoved.size
        toBeRemoved.forEach { point -> grid[point.x][point.y] = '.' }
      } else {
        break
      }
    }

    println("Result: $result")

    return result
  }

  fun solver(input: List<String>, singlePass: Boolean): Int {
    val grid = input.toMutableList().map { it.toMutableList() }
    var result = 0

    while (true) {
      val toBeRemoved = mutableSetOf<Point>()

      for (i in grid.indices) {
        for (j in grid[i].indices) {
          if (grid[i][j] != '@') continue

          val point = Point(i, j)

          val rollsAround = point.extendedNeighbours()
            .filter { it.x in grid.indices && it.y in grid[0].indices }
            .count { grid[it.x][it.y] == '@' }

          if (rollsAround < 4) {
            toBeRemoved += point
          }
        }
      }

      if (toBeRemoved.isNotEmpty()) {
        result += toBeRemoved.size
        toBeRemoved.forEach { point -> grid[point.x][point.y] = '.' }

        if (singlePass) break
      } else {
        break
      }
    }

    return result
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2025/2025_04_test")
  check(solver(testInput, true) == 13)
  check(solver(testInput, false) == 43)

  val input = readInput("2025/2025_04")
  check(solver(input, true) == 1_537)
  check(solver(input, false) == 8_707)
  solver(input, true).println()
  solver(input, false).println()
}
