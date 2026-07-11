import helpers.point.Point
import kotlin.math.max

fun main() {
  fun applyDirection(position: Point, direction: Char) = when (direction) {
    '>' -> position + Point(0, 1)
    '<' -> position + Point(0, -1)
    'v' -> position + Point(1, 0)
    '^' -> position + Point(-1, 0)
    else -> throw IllegalArgumentException()
  }

  fun rotateDirection(direction: Char) = when (direction) {
    '>' -> 'v'
    'v' -> '<'
    '<' -> '^'
    '^' -> '>'
    else -> throw IllegalArgumentException()
  }

  fun isNotOnTheEdge(position: Point, size: Int) = position.x in 1 until size - 1 && position.y in 1 until size - 1

  fun travel(matrix: List<List<Char>>, availableFines: Int = 0): Pair<Int, Int> {
    var maximumWithoutFines = 0

    fun dfs(current: Point, path: Set<Point>, remainingFines: Int): Int {
      if (current in path) {
        if (remainingFines == availableFines) maximumWithoutFines = max(maximumWithoutFines, path.size)

        if (remainingFines > 0 && isNotOnTheEdge(current, matrix.size)) {
          val newDirection = rotateDirection(matrix[current.x][current.y])

          return dfs(applyDirection(current, newDirection), path, remainingFines - 1)
        }

        return path.size
      }

      return dfs(applyDirection(current, matrix[current.x][current.y]), path + current, remainingFines)
    }

    val maximumWithFines = dfs(Point(0, 0), setOf(), availableFines)

    return maximumWithoutFines to maximumWithFines
  }

  fun part1(input: List<String>): Int {
    return travel(input.map { it.toList() }).first
  }

  fun part2_3(input: List<String>): Pair<Int, Int> {
    val matrix = input.map { it.toMutableList() }.toMutableList()

    var maximumWithoutFines = 0
    var maximumWithFines = 0

    for (x in 1 until input.size - 1) {
      for (y in 1 until input.first().length - 1) {
        val currentChar = matrix[x][y]

        val options = listOf('^', '>', 'v', '<').map {
          matrix[x][y] = it
          travel(matrix, 3)
        }

        matrix[x][y] = currentChar

        maximumWithoutFines = max(maximumWithoutFines, options.maxOf { it.first })
        maximumWithFines = max(maximumWithFines, options.maxOf { it.second })
      }
    }

    return maximumWithoutFines to maximumWithFines
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("flip_flop/2026/ff_2026_05_test")
  check(part1(testInput) == 45)
  check(part2_3(testInput) == (49 to 66))

  val input = readInput("flip_flop/2026/ff_2026_05")
  check(part1(input) == 255)
  check(part2_3(input) == (302 to 337))
  part1(input).println()
  part2_3(input).println()
}
