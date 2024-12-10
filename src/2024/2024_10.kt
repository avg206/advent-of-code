import helpers.gridReader
import helpers.point.Point

fun main() {
  fun dfs(input: List<String>, curr: Point, visited: Set<Point>): Set<Set<Point>> {
    val elevation = input[curr.x][curr.y].digitToInt()

    if (elevation == 9) {
      return setOf(visited)
    }

    return curr.neighbours()
      .asSequence()
      .filter { it.within2DArrayString(input) }
      .filter { it !in visited }
      .flatMap { target ->
        val targetElevation = input[target.x][target.y].digitToInt()

        if (targetElevation - elevation == 1) {
          return@flatMap dfs(input, target, visited + target)
        }

        return@flatMap setOf()
      }
      .filter { it.isNotEmpty() }
      .toSet()
  }

  fun part1(input: List<String>): Int {
    var answer = 0

    gridReader(input) { char, i, j ->
      when (char) {
        '0' -> answer += dfs(input, Point(i, j), setOf(Point(i, j))).map { it.last() }.distinct().size
      }
    }

    return answer
  }

  fun part2(input: List<String>): Int {
    val paths = mutableSetOf<Set<Point>>()

    gridReader(input) { char, i, j ->
      when (char) {
        '0' -> paths += dfs(input, Point(i, j), setOf(Point(i, j)))
      }
    }

    return paths.size
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_10_test")
  check(part1(testInput) == 36)
  check(part2(testInput) == 81)

  val input = readInput("2024/2024_10")
  check(part1(input) == 737)
  check(part2(input) == 1_619)
  part1(input).println()
  part2(input).println()
}
