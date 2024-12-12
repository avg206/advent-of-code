import helpers.point.Point

enum class PerimeterSide {
  Top,
  Bottom,
  Left,
  Right
}

fun main() {
  val sides = mapOf(
    PerimeterSide.Bottom to Point(1, 0),
    PerimeterSide.Top to Point(-1, 0),
    PerimeterSide.Right to Point(0, 1),
    PerimeterSide.Left to Point(0, -1)
  )

  fun countPerimeter(perimeter: Map<PerimeterSide, List<Point>>) = perimeter.values.sumOf { it.count() }

  fun countSides(perimeter: Map<PerimeterSide, List<Point>>): Int {
    return perimeter.map { (name, dots) ->
      val sorted = dots.sortedWith() { a, b -> if (a.x == b.x) a.y - b.y else a.x - b.x }

      when (name) {
        PerimeterSide.Top, PerimeterSide.Bottom -> sorted.groupBy { it.x }.values.sumOf { level ->
          level.zipWithNext().count { (a, b) -> b.y - a.y != 1 } + 1
        }

        PerimeterSide.Left, PerimeterSide.Right -> sorted.groupBy { it.y }.values.sumOf { level ->
          level.zipWithNext().count { (a, b) -> b.x - a.x != 1 } + 1
        }
      }
    }.sum()
  }

  fun solve(input: List<String>, processPerimeter: (perimeter: Map<PerimeterSide, List<Point>>) -> Int): Int {
    val map = input.map { it.toMutableList() }.toMutableList()

    fun bfs(start: Point): Int {
      val perimeter = mutableMapOf<PerimeterSide, MutableList<Point>>(
        PerimeterSide.Bottom to mutableListOf(),
        PerimeterSide.Top to mutableListOf(),
        PerimeterSide.Right to mutableListOf(),
        PerimeterSide.Left to mutableListOf()
      )

      val targetChar = map[start.x][start.y]
      val visited = mutableSetOf<Point>()

      val query = mutableListOf(start)

      while (query.isNotEmpty()) {
        val current = query.removeFirst()

        if (current in visited) continue
        visited.add(current)

        sides.forEach { (sideName, side) ->
          val target = current + side

          if (!target.within2DArray(map) || map[target.x][target.y] != targetChar) {
            perimeter[sideName]!!.add(current)
            return@forEach
          }

          query.add(target)
        }
      }

      visited.forEach { point -> map[point.x][point.y] = '.' }

      return visited.size * processPerimeter(perimeter)
    }

    var answer = 0

    for (i in map.indices) {
      for (j in map[i].indices) {
        if (map[i][j] == '.') continue

        answer += bfs(Point(i, j))
      }
    }

    return answer
  }

  // test if implementation meets criteria from the description, like:
  check(solve(readInput("2024/2024_12_test")) { countPerimeter(it) } == 140)
  check(solve(readInput("2024/2024_12_test_2")) { countPerimeter(it) } == 772)
  check(solve(readInput("2024/2024_12_test_3")) { countPerimeter(it) } == 1930)

  check(solve(readInput("2024/2024_12_test")) { countSides(it) } == 80)
  check(solve(readInput("2024/2024_12_test_2")) { countSides(it) } == 436)
  check(solve(readInput("2024/2024_12_test_3")) { countSides(it) } == 1206)
  check(solve(readInput("2024/2024_12_test_4")) { countSides(it) } == 236)
  check(solve(readInput("2024/2024_12_test_5")) { countSides(it) } == 368)

  val input = readInput("2024/2024_12")
  check(solve(input) { countPerimeter(it) } == 1_473_408)
  check(solve(input) { countSides(it) } == 886_364)
  solve(input) { countPerimeter(it) }.println()
  solve(input) { countSides(it) }.println()
}
