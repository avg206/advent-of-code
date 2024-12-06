import helpers.point.Direction
import helpers.point.Point
import helpers.gridReader

fun main() {
  fun solve(input: List<String>): Pair<Int, Int> {
    val map = MutableList(input.size) {
      MutableList(input.first().length) { 0 }
    }

    var start = Point(0, 0)

    gridReader(input) { char, i, j ->
      when (char) {
        '#' -> map[i][j] = 1
        '^' -> start = Point(i, j)
      }
    }

    fun walk(current: Point, direction: Direction): Boolean {
      val visited = mutableSetOf<Pair<Point, Direction>>()

      val stack = mutableListOf(Pair(current, direction))

      while (stack.isNotEmpty()) {
        val (curr, dir) = stack.removeLast()
        val step = curr + dir.moveList()

        if (Pair(curr, dir) in visited) return true
        visited.add(Pair(curr, dir))

        if (!step.within2DArray(map)) return false

        if (map[step.x][step.y] != 1)
          stack.add(Pair(step, dir))
        else
          stack.add(Pair(curr, dir.toRight()))
      }

      return false
    }

    val visited = mutableSetOf<Pair<Point, Direction>>()
    var guard = start
    var direction = Direction.Up

    while (guard.within2DArrayString(input)) {
      visited.add(Pair(guard, direction))
      val nextStep = guard + direction.moveList()

      if (!nextStep.within2DArray(map)) break
      if (map[nextStep.x][nextStep.y] != 1) {
        guard = nextStep
        continue
      }

      direction = direction.toRight()
    }

    val overlaps = mutableSetOf<Point>()

    visited.forEach { (current, direction) ->
      val nextStep = current + direction.moveList()

      if (!nextStep.within2DArray(map)) return@forEach
      if (map[nextStep.x][nextStep.y] == 1) return@forEach
      if (nextStep == start) return@forEach

      map[nextStep.x][nextStep.y] = 1
      if (walk(start, Direction.Up)) overlaps.add(nextStep)
      map[nextStep.x][nextStep.y] = 0
    }

    return Pair(
      visited.map { it.first }.toSet().size,
      overlaps.size
    )
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("2024/2024_06_test")
  check(solve(testInput) == Pair(41, 6))

  val input = readInput("2024/2024_06")
  check(solve(input) == Pair(4_903, 1_911))
  solve(input).println()
}
